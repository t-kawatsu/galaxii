<#if communityRecommends?? && communityRecommends.items?? >
<ul class="c-c-list clearfix">
  <#list communityRecommends.items as row>
  <li class="clearfix js-tooltip " title="${row.title?html}">
  <#if row.status == 'LIVE'>
    <#if row.type == 'COMMUNITY'>
    <div class="c-c-icon icn-category-c icn-category-c-${row.recommendCommunity.categoryId}"></div>
    <@my.communityImg row.recommendCommunity.id row.recommendCommunity.communityImageId "ss" />
    <div class="c-c-meta">
      <h3 class="c-c-t"><a href="${url('/community-recommend/read-ajax/' + row.id?c)}" class="modal">${cutStr(row.title, 16)?html}</a></h3>
    </div>
    <#else>
    <@my.communityRecommendImg row "ss"/>
    <div class="c-c-meta">
      <h3 class="c-c-t"><a href="${url('/community-recommend/read-ajax/' + row.id?c)}" class="modal">${cutStr(row.title, 16)?html}(外部サイト)</a></h3>
    </div>
    </#if>
  <#else>
    <div class="ta-c c-no-result sub-text" style="padding-top: 36px;">このオススメは削除されました。</div>
  </#if>
  </li>
  </#list>
</ul>
</#if>

<script type="text/javascript">
<#if contentsId??>
$(window).on('load', function() {
	$.fancybox.open("${url('/community-recommend/read-ajax/' + contentsId)}", {
		width:'auto', height:'auto',
		autoSize:true, type:'ajax', title:null
	});
});
</#if>
</script>

<div class="cc-btn-create-sub-contents pt-12">
  <a href="${url('/community-recommend/create-ajax/' + community.id )}" class="btn-o btn-large modal" >オススメを作成する</a>
</div>
