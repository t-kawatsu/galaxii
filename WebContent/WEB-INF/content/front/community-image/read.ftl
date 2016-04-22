<section class="ci-section clearfix" >
  <div class="sub-section-head">
    <hr/>
    <h3>${community.title?html}の投稿画像</h3>
  </div>
  <div class="cc-info-left" >
    <#if communityImage.type == 'YOUTUBE'>
      <div class="c-large-image-con ta-c pos-r" style="height:auto; overflow:hidden">
        <iframe width="512" height="384" src="http://www.youtube.com/embed/${communityImage.vendorData}" frameborder="0" allowfullscreen></iframe>
      </div>
    <#else>
      <div class="c-large-image-con ta-c pos-r">
        <@my.communityImg communityImage.communityId communityImage.id 'l' />
        <#if communityImage.type == 'YOUTUBE'>
        <a href="http://www.youtube.com/watch?v=${communityImage.vendorData}" target="_blank" class="cm-yt-link-lbl js-tooltip" title="Youtubeで見る"><img src="${assets('lbl-yt-logo-95x40.png', 'images', 'common')}" width="72" height="30" /></a>
        <#elseif communityImage.type == 'AMAZON'>
        <a href="http://www.amazon.co.jp/o/ASIN/${communityImage.vendorData}" target="_blank" class="cm-az-link-lbl js-tooltip" title="Amazonで商品を見る"><img src="${assets('lbl-az-logo.jpg', 'images', 'common')}" width="102" height="30" /></a>
        </#if>
      </div>
    </#if>
    
    <div class=" pos-r">
    <#if communityImage.status == 'LIVE'>
      <div class="cc-sub-menu-con js-toggle-menu-con">
        <ul class="cc-sub-menu js-toggle-menu b-shadow">
      	  <#if isLogined && communityImage.user.id == currentUser.id>
      	  <li class="icon-caret-right"><a href="${url('/community-image/update-ajax/' + communityImage.id?c )}" class="modal">編集する</a></li>
      	  <li class="icon-caret-right"><a class="js-confirm-contents" data-confirm-title="削除します。よろしいですか?" href="${url('/community-image/delete-ajax/' + communityImage.id?c )}">削除する</a></li>
      	  <#else>
          <li class="icon-caret-right"><a href="${url('/user-violation/create-ajax/community-image/' + communityImage.id?c)}" class="js-qt-modal">違反申告をする</a></li>   	
      	  </#if>
        </ul>
        <a class="cc-sub-menu-switch js-btn-toggle-menu" href="#"><img src="${assets('icons/circle-arrow-down.png', 'images', 'common')}" alt="" /></a>
      </div>
    </#if>
    
    <h2 class="cc-title" style="padding-top:12px">${communityImage.title!""?html}&nbsp;</h2>
    <div class="cc-detail-menu fs-ss clearfix pt-12">
      <div class="fl clearfix">
        <a href="${url('/user/read/' + communityImage.user.id?c)}" class="fl mr6 js-user-nickname">${communityImage.user.nickname?html}</a>
        <span class="created-at icon-time fl sub-text">${fTime(communityImage.createdAt?string('yyyy/MM/dd HH:mm:ss'))}</span>
      </div>
      <ul class="v-right-list fr">
        <li><@my.likeBtn camelCase2dash(communityImage.class.simpleName) communityImage.id communityImage.likeCnt communityImage.isUserLiked /></li>
      </ul>
    </div>
    </div>
    
  </div>  
  
  <div class="cc-info-right">
    <ul id="ci-comments" class="cc-comments">
      <#assign commentFormAction = url('/community-image-comment/create-ajax') />
      <#include '../common/_comment-form.ftl' />
      <#if comments??>
	  	<#include '../common/_comments.ftl' />
      </#if>
    </ul>

	<#if communityImageComments.limit < communityImageComments.total >
      <div class="list-more-con loading">
        <a data-dest-con-selector="#ci-comments" href="${url('/community-image-comment/more-ajax/' + communityImage.id )}" data-par-page-limit="${communityImageComments.limit}"></a>
      </div>
    </#if>
  </div>

  <script type="text/javascript">
  my.resources.get("app").community.Behavior.bindAutoMorePaginate(".fancybox-inner", 20);
  </script>
</section>