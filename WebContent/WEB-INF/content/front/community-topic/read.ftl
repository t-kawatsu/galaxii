<title>${communityTopic.title}</title>

<link href="${assets('community.css', 'css')}" media="screen" rel="stylesheet" type="text/css" />
  
<div class="cc-info-left">
<section>
  <div class="sub-section-head">
    <hr/>
    <h3>${community.title?html}のトピック/レビュー</h3>
  </div>
  <div class="pos-r">
  <#if communityTopic.status == 'LIVE'>
    <div class="cc-sub-menu-con js-toggle-menu-con">
      <ul class="cc-sub-menu js-toggle-menu b-shadow">
      	<#if isLogined && communityTopic.user.id == currentUser.id>
      	<li class="icon-caret-right"><a href="${url('/community-topic/update/' + communityTopic.id?c )}">編集する</a></li>
      	<li class="icon-caret-right"><a class="js-confirm-contents" data-confirm-title="削除します。よろしいですか?" href="${url('/community-topic/delete-ajax/' + communityTopic.id?c )}">削除する</a></li>
      	<#else>
      	<li class="icon-caret-right"><a href="${url('/user-violation/create-ajax/community-topic/' + communityTopic.id?c)}" class="js-qt-modal">違反申告をする</a></li>
      	</#if>
      </ul>
      <a class="cc-sub-menu-switch js-btn-toggle-menu" href="#"><img src="${assets('icons/circle-arrow-down.png', 'images', 'common')}" alt="" /></a>
    </div>
  </#if>
  <h2 class="ct-title clearfix"><@my.activityIcon communityContentsCategory('review') />${communityTopic.title?html}</h2>
  <div class="cc-detail-menu fs-ss clearfix pt-12">
    <div class="fl clearfix">
      <a href="${url('/user/read/' + communityTopic.user.id?c)}" class="fl mr6 js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + communityTopic.user.id?c)}">${communityTopic.user.nickname?html}</a>
      <span class="created-at icon-time fl sub-text">${fTime(communityTopic.createdAt?string('yyyy/MM/dd HH:mm:ss'))}</span>
    </div>
    <ul class="v-right-list fr">
      <li><@my.likeBtn camelCase2dash(communityTopic.class.simpleName) communityTopic.id communityTopic.likeCnt communityTopic.isUserLiked /></li>
    </ul>
  </div>
    
  <#if communityTopic.communityTopicImages??>
    <div class="ct-image-con">
      <ul class="ct-images clearfix">
        <#list communityTopic.communityTopicImages as row>
        <li class="fl pr-4"><@my.communityTopicImg communityTopic.id row.id "ss" /></li>
        </#list>
      </ul>
    </div>
  </#if>
  <p class="ct-description pt-12">${emotion(communityTopic.description?html?replace("\n", "<br/>"))}</p>
  </div>
</section>
</div>

<div class="cc-info-right">
  <#include '../common/_sub-community-info.ftl' />
  
  <section>
    <ul id="ct-comments" class="cc-comments">
      <#assign commentFormAction = url('/community-topic-comment/create-ajax') />
      <#include '../common/_comment-form.ftl' />
      <#if comments??>
	  	<#include '../common/_comments.ftl' />
      </#if>
    </ul>

	<#if communityTopicComments.limit < communityTopicComments.total >
      <div class="list-more-con loading">
        <a class="icon-caret-down btn-more" data-dest-con-selector="#ct-comments" href="${url('/community-topic-comment/more-ajax/' + communityTopic.id?c )}" data-par-page-limit="${communityTopicComments.limit}">もっと見る</a>
      </div>
    </#if>
  </section>
</div>