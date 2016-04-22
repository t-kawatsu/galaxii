<div id="cr-com-search-section" class="pt-12" >
  
<#if communityUsers?? && communityUsers.items?? && 1 < communityUsers.items?size>
  <#assign pager = communityUsers >
  <#include '../common/_pager.ftl' />

  <div class="pt-12 pb-12">オススメリンクに追加するコミュニティを選択して下さい。</div>
  <ul class="c-c-list clearfix">
    <#list communityUsers.items as row >
    <li class="clearfix js-tooltip" title="${row.community.title?html}" >
      <div class="c-c-icon icn-category-c icn-category-c-${row.community.categoryId}"></div>
      <@my.communityImg row.community.id row.community.communityImageId "ss"/>
      <div class="c-c-meta">
        <h3 class="c-c-t"><a class="ajax-get" href="${url('/community-recommend/community-create-ajax/' + communityId?c + '/' + row.community.id?c)}#cr-com-search-section" >${cutStr(row.community.title, 16)?html}</a></h3>
      </div>
	</li>
    </#list>
  </ul>
<#else>
  <p class="s-no-result">
    参加しているコミュニティはありません。<br />
    参加しているコミュニティでなければオススメできません。
  </p>
</#if>

<script type="text/javascript">
  $.fancybox.update();
</script>
</div>