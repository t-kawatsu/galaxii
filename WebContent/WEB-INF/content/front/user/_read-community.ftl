<section>
  <div class="sub-section-head">
    <hr/>
    <h2>参加コミュニティ一覧</h2>
  </div>
  <#assign pager = communityUsers >
  <#assign pjaxPage = true >
  <#include '../common/_pager.ftl' />
    
  <#if pager.items?? && 0 < pager.items?size>
  <ul class="c-h-5-list c-c-list clearfix">
    <#list pager.items as row>
    <li class="js-container-link">
      <div class="c-c-icon icn-book-c"></div>
      <@my.communityImg row.community.id row.community.communityImageId "ss" />
      <div class="c-c-meta">
       <h3 class="c-c-t"><a href="${url('/community/read/' + row.community.id?c)}">${row.community.title?html}(${row.community.communityUsersCnt?c})</a></h3>
      </div>
    </li>
    </#list>
  </ul>
  <#else>
  <div class="s-no-result">
    <p>参加しているコミュニティはありません。</p>
  </div>
  </#if>
</section>