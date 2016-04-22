<@compress single_line=true>
<!DOCTYPE html>
<html lang="ja"
	xmlns="http://www.w3.org/1999/xhtml"
	xml:lang="ja"
	xmlns:og="http://ogp.me/ns#"
	xmlns:fb="http://www.facebook.com/2008/fbml">
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<meta http-equiv="Content-Style-Type" content="text/css" >
<meta http-equiv="Content-Script-Type" content="text/javascript" >
<meta name="keywords" content="Galaxii,ギャラグジー,ギャラクシー,趣味,興味,おすすめ,つながる,コミュニティ,共有,SNS,jogoj" >
<meta name="description" content="興味でつながりオススメしよう！オススメ共有コミュニティサービス Galaxii (ギャラグジー)" >
<meta name="author" content="jogoj Inc." >
<meta name="copyright" content="Copyright &amp;copy;jogoj Inc." >

<link href="${assets('jquery.fancybox.css', 'css', 'common')}" media="screen, print" rel="stylesheet" type="text/css" >
<link href="${assets('jquery.qtip.min.css', 'css', 'common')}" media="screen, print" rel="stylesheet" type="text/css" >
<link href="${assets('font-awesome.css', 'css', 'common')}" media="screen" rel="stylesheet" type="text/css" >
<link href="${assets('default.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >
<link href="${assets('common.css', 'css')}?_=20130511" media="screen, print" rel="stylesheet" type="text/css" >
<link href="${assets('emotion.css', 'css', 'common')}?_=20130421" media="screen" rel="stylesheet" type="text/css" >
<link href="${assets('site-id-fb.png', 'images', 'common')}" rel="icon" type="image/gif" >
<link href="${assets('site-id-fb.png', 'images', 'common')}" rel="shortcut icon" type="image/gif" >

<script type="text/javascript" src="/assets/common/js/jquery-1.8.2.min.js"></script>

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

<title>${cutStr(title, 18)?html} | オススメ共有コミュニティ <@s.text name="app.site.name"/> (ギャラグジー)</title>

<meta property="og:title" content="${cutStr(title, 18)?html} | オススメ共有コミュニティ"/>
<meta property="og:image" content="${url('/', 'true', 'true')}${assets('site-id-fb.png', 'images', 'common')}"/>
<meta property="og:url" content="${url('', 'true', 'true')}"/>
<meta property="og:site_name" content="<@s.text name="app.site.name"/> (ギャラグジー)"/>
<meta property="og:type" content="website"/>
<meta property="og:app_id" content="<@s.text name="app.facebook.appId"/>"/>

<meta name="google-site-verification" content="<@s.text name='app.google.siteVerificationMeta' />" />
</head>

<body>
  <div id="container">
	<div id="wrapper">
	  <header id="header">
	    <div class="gframe clearfix">
		  <h1 id="site-id"><a href="${url('/', 'true')}"><img src="${assets('site-id.png', 'images', 'common')}" alt="galaxii (ギャラグジー)" width="103" height="36" /></a></h1>
		  <div class="c-u-info">
		  	<#if isLogined == true >
		      <a class="js-u-info" href="${url('/user-information/index-ajax')}#c-u-infos" onclick="return false;"><img src="${assets('icn-user-info.png', 'images')}" alt="お知らせ" width="35" height="28" /></a>
		  	  <div id="c-u-infos-con" style="display:none;">
		  	  <#include '../user-information/index-ajax.ftl' />
		  	  </div>
		  	</#if>
		  </div>
		  <div class="c-search-con">
		    <form action="${url('/search/community', 'true')}" method="GET" class="clearfix c-search-form pos-r">
		      <img class="c-icn-search" src="${assets('icn-search.png', 'images', 'common')}" alt="検索" />
		      <@s.textfield name="word" cssClass="c-search-word form-text" placeholder="ジョジョの奇妙な冒険"/>
		    </form>
		  </div>
		  <ul class="header-menu clearfix">
		    <li><a class="icon-caret-right" href="${url('/about', 'true')}">Galaxiiとは?</a></li>
		    <#if isLogined == true >
		    <li class="js-hover-menu-con pos-r">
		    	<a class="icon-caret-right" href="${url('/user/read/' + currentUser.id?c, 'true', 'true')}">マイページ</a>
		    	<@my.userImg currentUser "ss" />
	    		<ul id="my-menu-con" class="js-hover-menu radius-1">
	    		  <li><a href="${url('/user/read/' + currentUser.id?c + '/watch', 'true', 'true')}">ウォッチ</a></li>
	    		  <li><a href="${url('/user/read/' + currentUser.id?c + '/activity', 'true', 'true')}">アクティビティ</a></li>
	    		  <li><a href="${url('/user/read/' + currentUser.id?c + '/community', 'true', 'true')}">コミュニティ</a></li>
	    		  <li><a href="${url('/user/read/' + currentUser.id?c + '/detail', 'true', 'true')}">基本情報</a></li>
	    		  <li><a href="${url('/user/logout', 'true')}">ログアウト</a></li>
	    		</ul>
		    </li>
		    <#else>
		    <li><a class="icon-caret-right" href="${url('/user/create', 'true', 'true')}">ユーザー登録</a></li>
		    <li class="js-hover-menu-con pos-r">
		    	<a class="icon-caret-right" href="${url('/user/login', 'true', 'true')}">ログイン</a>
	    		<div id="login-form-con" class="js-hover-menu radius-1">
	      			<#include "../common/_login-form.ftl">
	      			<div class="c-or-sep">
	      			  <hr/>
	      			  <span>または</span>
	      			</div>
	      			<ul>
	      			  <li><a href="${url('/fb-user/login')}" class="icon-facebook-sign btn-fb">Facebookでログイン</a></li>
	      			  <li class="pt-12"><a href="${url('/tw-user/login')}" class="icon-twitter-sign btn-tw">Twitterでログイン</a></li>
	      			</ul>
	    		</div>
		    </li>
		    </#if>
		  </ul>
		  <script type="text/javascript">
			    $(function() {
			    	var dispAreaSelector = '.js-hover-menu';
			    	var switchAreaSelector = '.js-hover-menu-con';
			    	$(switchAreaSelector).find(dispAreaSelector).css("opacity", "0");
			    	$(switchAreaSelector).on({
			    		'mouseenter': function(e) {
			    			var dispArea = $(this).find(dispAreaSelector);
			    			if(dispArea.css("opacity") <= 0) {
			    			  dispArea.show().animate({opacity: 1}, "fast" );
			    			}
			    		},
			    		'mouseleave': function(e) {
			    			$(this).find(dispAreaSelector)
			    			.animate({opacity: 0}, "fast" , function(){
			    				$(this).hide();
			    			});
			    		}
			    	});
			    });
		  </script>
	    </div>
	  </header>
	  <div class="gframe">
	    <div id="contents" class="radius-3">
		  <div id="${pathinfo('controller')}-${pathinfo('action')}" class="${pathinfo('controller')}-container clearfix">
			${body}
		  </div>
	    </div>
	  </div>
	</div>
	<footer id="footer" class="fs-ss">
	  <nav>
	    <div class="gframe clearfix">
		<ul class="footer-menu clearfix fl">
		  <li><a href="${url('/about', 'true')}">Galaxiiとは</a></li>
		  <#if isLogined == true >
		  <li><a href="${url('/user/logout', 'true')}">ログアウト</a></li>
		  <#else>
		  <li><a href="${url('/user/create', 'true', 'true')}">新規登録</a></li>
		  </#if>
		  <li><a href="${url('/help', 'true')}">ヘルプ</a></li>
		  <li><a href="${url('/inquiry/create', 'true', 'true')}">お問い合わせ</a></li>
		  <li><a href="${url('/terms', 'true')}">利用規約</a></li>
		  <li><a href="${url('/terms/privacy', 'true')}">プライバシー</a></li>
		</ul>
		<div class="fr ta-r">
		  <address class="site-domain pb-12">http://<@s.text name="app.site.domain" /></address>
		  <div class="copyright pb-12">Copyright ©jogoj Inc. All rights reserved.</div>
		  <a href="<@s.text name="app.company.url" />" target="_blank" style="text-decoration:underline;">株式会社jogoj</a>
		</div>
		</div>
	  </nav>
	</footer>
  </div>

  <a id="page-top" class="smooth-site-link" href="#"><i class="icon-arrow-up"></i><br/><span>PAGE TOP</span></a>

<script type="text/javascript" src="${assets('jquery-libs.pack.js', 'js', 'common')}" ></script>
<script type="text/javascript" src="${assets('my.js', 'js')}?_=20130502" ></script>

<#include "../common/_twitter-root.ftl">
<#include "../common/_facebook-root.ftl">
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-31427166-3']);
  _gaq.push(['_trackPageview']);
  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>

</body>
</html>
</@compress>