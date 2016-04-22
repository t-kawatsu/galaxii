<title>ユーザー一覧 | ${community.title}</title>

<link href="${assets('community.css', 'css')}" media="screen" rel="stylesheet" type="text/css" />
  
<div class="clmn-left-main">
  <section>
    <div class="clearfix">
      <div class="icn-category-c icn-category-c-${community.categoryId} fl mr6"></div>
      <h1 class="cm-title"><a href="${url('/community/read/' + community.id)}">${community.title?html}</a></h1>
    </div>
  </section>
  <section class="pt-12">
    <div class="sub-section-head">
      <hr/>
      <h2>コミュニティユーザー一覧</h2>
    </div>
    <#assign pager = communityUsers />
    <#include '../common/_pager.ftl' />
    
    <#if pager.items?? && 0 < pager.items?size>
    <ul class="clearfix cm-u-list">
      <#list pager.items as row>
      <li class="js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + row.user.id?c)}">
        <a href="${url('/user/read/' + row.user.id?c)}"><@my.userImg row.user "ml" /></a>
      </li>
      </#list>
    </ul>
    <#else>
    <div class="s-no-result">
      <p>参加ユーザーはいません。</p>
    </div>
    </#if>
  </section>
</div>

<div class="clmn-right-menu">
  <#include '../common/_community-activities.ftl' />
  <#include '../common/_recommend-news.ftl' />
</div>