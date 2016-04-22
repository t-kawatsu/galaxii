package com.galaxii.common.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dao.UserDao;
import com.galaxii.common.dao.UserWatchDao;
import com.galaxii.common.entity.LanguageCode;
import com.galaxii.common.entity.TmpUser;
import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserAccountType;
import com.galaxii.common.entity.UserSecession;
import com.galaxii.common.entity.UserStatus;
import com.galaxii.common.entity.UserWatch;
import com.galaxii.common.entity.UserWatchId;
import com.galaxii.common.hibernate.Association;
import com.galaxii.common.util.CollectionUtils;
import com.galaxii.common.util.DbSelectPaginator;
import com.galaxii.common.util.SimplePager;
import com.galaxii.common.util.crypt.BlowfishCrypt;

@Transactional
//@Scope("prototype")
@Service
public class UserService {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    
	private static final String KEEP_LOGIN_PARAM_NAME = "kl";
	private static final String TOKEN_PARAM_NAME = "t";
	private static final String IDENTIFIER_PARAM_NAME = "uid";
	
	private static final int keepLoginExpired = 60 * 60 * 24 * 7;

    @Resource
    protected UserDao userDao;
    @Resource
    protected UserWatchDao userWatchDao;
    
    public User login(User user, boolean keep) throws Exception {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	updateLoginState(user, request.getHeader("User-Agent"));
    	
    	if(!keep) {
    		return user;
    	}
    	
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String token = BlowfishCrypt.encrypt(RandomStringUtils.random(10));
		user.setToken(token);
		userDao.update(user);
		
		Cookie klc = new Cookie(KEEP_LOGIN_PARAM_NAME, String.valueOf(keep));
		klc.setMaxAge(keepLoginExpired);
		klc.setPath("/");
		response.addCookie(klc);
		 
		Cookie idc = new Cookie(IDENTIFIER_PARAM_NAME, 
			 BlowfishCrypt.encrypt(user.getId().toString()));
		idc.setMaxAge(keepLoginExpired);
		idc.setPath("/");
		response.addCookie(idc);
		 
		Cookie tc = new Cookie(TOKEN_PARAM_NAME, user.getToken());
		tc.setMaxAge(keepLoginExpired);
		tc.setPath("/");
		response.addCookie(tc);
		 
		return user;
    }
    
    public User login(String mailAddress, String password, boolean keep) throws Exception {
        User user = userDao.findByMailAddressAndPasswordAndStatus(
                mailAddress, password, UserStatus.LIVE);
        return login(user, keep);
    }
    
    public void logout() {
    	clearKeepLoginState();
    }
    
    public User reloginIfKeep() throws Exception {
    	// no login
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		
		boolean isKeepLogin = null != detectCookieValue(
				cookies, KEEP_LOGIN_PARAM_NAME);
		if(!isKeepLogin) {
			return null;
		}
		
		String uid = detectCookieValue(cookies, IDENTIFIER_PARAM_NAME);
		if(StringUtils.isEmpty(uid)) {
			return null;
		}
		
		String token = detectCookieValue(cookies, TOKEN_PARAM_NAME);
		
		User user = userDao.findById(Integer.valueOf(BlowfishCrypt.decrypt(uid)));
		if(user == null || !user.getToken().equals(token)) {
			clearKeepLoginState();
			return null;
		}
		
		return login(user, true);
    }
    
	private String detectCookieValue(Cookie[] cookies, String name) {
		for(Cookie c : cookies) {
			if(c.getName().equals(name)) {
				return c.getValue();
			}
		}
		return null;
	}
    
	public void clearKeepLoginState() {
		 HttpServletResponse response = ServletActionContext.getResponse();
		 
		 Cookie klc = new Cookie(KEEP_LOGIN_PARAM_NAME, "");
		 klc.setMaxAge(0);
		 klc.setPath("/");
		 response.addCookie(klc);
		 
		 Cookie idc = new Cookie(IDENTIFIER_PARAM_NAME, "");
		 idc.setMaxAge(0);
		 idc.setPath("/");
		 response.addCookie(idc);
		 
		 Cookie tc = new Cookie(TOKEN_PARAM_NAME, "");
		 tc.setMaxAge(0);
		 tc.setPath("/");
		 response.addCookie(tc);
	}

    public User create(TmpUser tmpUser, LanguageCode languageCode, 
    		String userAgent, UserAccountType userAccountType)
         throws Exception {
        return create(tmpUser, languageCode, userAgent, userAccountType, false);
    }

    public User create(TmpUser tmpUser, LanguageCode languageCode, 
    	String userAgent, UserAccountType userAccountType, boolean isTestUser) throws Exception {
    	
        User user = new User();
        Date defaultValue = null;
        ConvertUtils.register(new DateConverter(defaultValue), Date.class);
        BeanUtils.copyProperties(user, tmpUser);
        ConvertUtils.deregister();

        user.setLastLoginedAt(new Date());
        user.setLastMailNoticedAt(new Date());
        user.setMailNoticeFrequencyCode(2);
        user.setStatus(UserStatus.LIVE);
        user.setIsTestUser(false);
        user.setLanguageCode(new Locale(languageCode.toString()));
        user.setUseragent(userAgent);
        user.addAccountType(userAccountType);
        String token = BlowfishCrypt.encrypt(RandomStringUtils.random(10));
        user.setToken(token);
        userDao.save(user);
        return user;
    }
    
    public User updateLoginState(User user, String userAgent) {
        user.setLastLoginedAt(new Date());
        user.setUseragent(userAgent);
        userDao.getSession().update(user);
        return user;
    }
    
    public void secession(User user, Integer reasonCode, String description) {
        user.setStatus(UserStatus.SECESSIONED);
        userDao.getSession().update(user);

        UserSecession userSecession = new UserSecession();
        userSecession.setId(user.getId());
        userSecession.setReasonCode(reasonCode);
        userSecession.setDescription(description);
        userDao.save(userSecession);

        //deleteUserRegistData(user.getId());
    }
    
	public List<User> fetchSiteUsers(int limit) {
		List<User> users = userDao.resultList(userDao.getSession()
		.createQuery("FROM User u ORDER BY u.id DESC")
		.setMaxResults(limit * 2));
		
		return CollectionUtils.detectRandom(users, limit);
	}
	
	public SimplePager<UserWatch> paginateUserWatches(Integer userId, int limit, Integer page) {
		DbSelectPaginator<UserWatch> p = 
				new DbSelectPaginator<UserWatch>(userWatchDao);
		p.setLimit(limit);
		p.addAssociation( Association.join("toUser", JoinType.INNER_JOIN) );
		p.addCriterion(Restrictions.eq("id.fromUserId", userId));
		p.addOrder(Order.desc("createdAt"));
		return p.paginate();
	}
	
	public SimplePager<UserWatch> paginateUserWatchedes(Integer userId, int limit, Integer page) {
		DbSelectPaginator<UserWatch> p = 
				new DbSelectPaginator<UserWatch>(userWatchDao);
		p.setLimit(limit);
		p.addAssociation( Association.join("fromUser", JoinType.INNER_JOIN) );
		p.addCriterion(Restrictions.eq("id.toUserId", userId));
		p.addOrder(Order.desc("createdAt"));
		return p.paginate();
	}
	
	public boolean isWatchUser(Integer fromUserId, Integer toUserId) {
		Integer cnt = userWatchDao.detectCountValue(userWatchDao.getSession().createQuery(
				"SELECT count(*) FROM UserWatch uw WHERE" +
				" uw.id.fromUserId = :fromUserId AND uw.id.toUserId = :toUserId ")
				.setInteger("fromUserId", fromUserId)
				.setInteger("toUserId", toUserId));
		return 0 < cnt;
	}
	
	public void watchUser(Integer fromUserId, Integer toUserId) {
		userWatchDao.create(fromUserId, toUserId);
	}
	
	public void unWatchUser(Integer fromUserId, Integer toUserId) {
		UserWatchId id = new UserWatchId();
		id.setFromUserId(fromUserId);
		id.setToUserId(toUserId);
		UserWatch userWatch = userWatchDao.findById(id);
		if(userWatch == null) {
			return;
		}
		userWatchDao.delete(userWatch);
	}
	
}