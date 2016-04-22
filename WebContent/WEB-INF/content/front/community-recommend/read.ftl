<section class="cr-section clearfix" >
  <div class="sub-section-head">
    <hr/>
    <h3>${community.title?html}のオススメリンク</h3>
  </div>
  <div class="cc-info-left pos-r">
    <#if communityRecommend.status == 'LIVE'>
    <div class="cc-sub-menu-con js-toggle-menu-con">
      <ul class="cc-sub-menu js-toggle-menu b-shadow">
        <li class="icon-caret-right"><a href="${url('/user-violation/create-ajax/community-recommend/' + communityRecommend.id?c)}" class="js-qt-modal">違反申告をする</a></li>
      	<#if isLogined && communityRecommend.user.id == currentUser.id>
      	<li class="icon-caret-right"><a class="js-confirm-contents" data-confirm-title="削除します。よろしいですか?" href="${url('/community-recommend/delete-ajax/' + communityRecommend.id?c )}">削除する</a></li>
      	</#if>
      </ul>
      <a class="cc-sub-menu-switch js-btn-toggle-menu" href="#"><img src="${assets('icons/circle-arrow-down.png', 'images', 'common')}" alt="" /></a>
    </div>
    </#if>

	<h2 class="cc-title clearfix">
      <@my.activityIcon communityContentsCategory('recommend') />${communityRecommend.title?html}
      <span class="sub-text">
      <#switch communityRecommend.type>
      <#case "SITE">
        (外部サイト)
        <#break/>
      <#case "COMMUNITY">
        (コミュニティ)
        <#break/>
      </#switch>
      </span>
	</h2>
    <div class="cc-detail-menu fs-ss clearfix pt-12">
      <div class="fl clearfix">
        <a href="${url('/user/read/' + communityRecommend.user.id?c)}" class="fl mr6 js-user-nickname">${communityRecommend.user.nickname?html}</a>
        <span class="created-at icon-time fl sub-text">${fTime(communityRecommend.createdAt?string('yyyy/MM/dd HH:mm:ss'))}</span>
      </div>
      <ul class="v-right-list fr">
        <li><@my.likeBtn camelCase2dash(communityRecommend.class.simpleName) communityRecommend.id communityRecommend.likeCnt isUserLiked /></li>
      </ul>
    </div>
	
	<#if communityRecommend.type == 'SITE'>
	<div class="pt-12 clearfix">
	  <div class="fl" style="width: 350px;">
	  	サイトURL: <a href="${communityRecommend.url?html}" target="_blank" class="icon-external-link">${communityRecommend.url?html}</a>
	    <p class="pt-12">${communityRecommend.description!""?html}</p>
	  </div>
	  <div class="fr">
	  	<@my.communityRecommendImg communityRecommend "s" />
	  </div>
	</div>
	<#else>
	<div class="pt-12 clearfix">
	  <div class="fl pr-4">
	  	<@my.communityImg communityRecommend.recommendCommunity.id communityRecommend.recommendCommunity.communityImageId "ss" />
	  </div>
	  <div class="fl pl-4" style="width: 350px;">
	  	<div class="pt-12">${communityRecommend.recommendCommunity.title!""?html}</div>
	    <p class="pt-12">${emotion(communityRecommend.recommendCommunity.description!""?html)}</p>
	  </div>
	</div>
	<div class="pt-12">
	  <a href="${url('/community/read/' + communityRecommend.recommendCommunityId?c)}" class="btn-o">コミュニティを見る</a>
	</div>
	</#if>
	
  </div>
  
  <div class="cc-info-right">
    <ul id="cr-comments" class="cc-comments">
      <#assign commentFormAction = url('/community-recommend-comment/create-ajax') />
      <#include '../common/_comment-form.ftl' />
      <#if comments??>
	  	<#include '../common/_comments.ftl' />
      </#if>
    </ul>

	<#if communityRecommendComments.limit < communityRecommendComments.total >
      <div class="list-more-con loading">
        <a data-dest-con-selector="#cr-comments" href="${url('/community-recommend-comment/more-ajax/' + communityRecommend.id )}" data-par-page-limit="${communityRecommendComments.limit}"></a>
      </div>
    </#if>
  </div>

  <script type="text/javascript">
  	my.resources.get("app").community.Behavior.bindAutoMorePaginate(".fancybox-inner", 20);
  </script>
</section>