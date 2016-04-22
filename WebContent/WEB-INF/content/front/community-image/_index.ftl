<#if communityImages?? && communityImages.items??>
<ul class="ci-list clearfix c-c-list">
  <#list communityImages.items as row>
  <li>
  <#if row.status == 'LIVE'>
  	<a class="modal js-tooltip" href="${url('/community-image/read-ajax/' + row.id)}" title="${row.title!""?html}">
  	  <@my.communityImg row.communityId row.id "ss" />
  	</a>
  	<div class="ci-row-detail">
  	  <span class="icon-thumbs-up">${row.likeCnt}</span>
      <span class="pl-4 icon-comment-alt">${row.commentCnt}</span>
	  <!-- <span class="icon-time">${fTime(row.createdAt)}</span> -->
	</div>
  <#else>
    <div class="ta-c c-no-result sub-text" style="padding-top: 36px;">このオススメは削除されました。</div>
  </#if>
  </li>
  </#list>
</ul>
</#if>
    
<script>
  $('.ci-list').on({
    'mouseenter':function(e){
        $(this).find('.ci-row-detail').animate({height: "hide"}, "fast");
    },
    'mouseleave':function(e){
        $(this).find('.ci-row-detail').animate({height: "show"}, "fast");
    }
  }, 'li');
  
  
  <#if contentsId??>
  $(window).on('load', function() {
  	$.fancybox.open("${url('/community-image/read-ajax/' + contentsId)}", {
		width:'auto', height:'auto',
		autoSize:true, type:'ajax', title:null
	});
  });
  </#if>
</script>

<div class="cc-btn-create-sub-contents pt-12">
  <#if imageType == 'IMAGE'>
    <a href="${url('/community-image/create-ajax?communityId=' + community.id )}" class="btn-o btn-large modal">画像を投稿する</a>
  <#else>
	<a href="${url('/community-image/movie-create-ajax?communityId=' + community.id)}" class="btn-o btn-large  modal">動画を投稿する</a>
  </#if>
</div>
