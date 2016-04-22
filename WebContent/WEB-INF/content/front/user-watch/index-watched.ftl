<title>${user.nickname?html}をウォッチしているユーザー一覧</title>

<link href="${assets('user.css', 'css')}" media="screen" rel="stylesheet" type="text/css" />
  
<div class="clmn-left-main">
  <section>
    <div class="sub-section-head">
      <hr/>
      <h2>${user.nickname?html}をウォッチしているユーザー一覧</h2>
    </div>
    <#assign pager = userWatches />
    <#include '../common/_pager.ftl' />
    
    <#if pager.items?? && 0 < pager.items?size>
    <ul class="clearfix cm-u-list">
      <#list pager.items as row>
      <li class="fl">
        <a class="js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + row.fromUser.id?c)}" href="${url('/user/read/' + row.fromUser.id)}" ><@my.userImg row.fromUser "ml" /></a>
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

</div>