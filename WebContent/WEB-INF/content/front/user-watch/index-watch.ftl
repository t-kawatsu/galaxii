<title>${user.nickname?html}がウォッチしているユーザー一覧</title>

<link href="${assets('user.css', 'css')}" media="screen" rel="stylesheet" type="text/css" />
  
<div class="clmn-left-main">
  <section>
    <div class="sub-section-head">
      <hr/>
      <h2>${user.nickname?html}がウォッチしているユーザー一覧</h2>
    </div>
    <#assign pager = userWatches />
    <#include '../common/_pager.ftl' />
    
    <#if pager.items?? && 0 < pager.items?size>
    <ul class="clearfix cm-u-list">
      <#list pager.items as row>
      <li class="fl">
        <a class="js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read/' + row.toUser.id?c)}" href="${url('/user/read/' + row.toUser.id)}" ><@my.userImg row.toUser "ml" /></a>
      </li>
      </#list>
    </ul>
    <#else>
    <div class="s-no-result">
      <p></p>
    </div>
    </#if>
  </section>
</div>

<div class="clmn-right-menu">
  <div class="clearfix">
    <div class="fl">
	  <a class="js-tooltip" href="${url('/user/read/' + user.id)}" title="${user.nickname?html}"><@my.userImg user "ml" /></a>
    </div>
    <div class="fl p-4">${user.nickname?html}</div>
  </div>
</div>