<title>${communityEvent.title}</title>

<link href="${assets('community.css', 'css')}" media="screen" rel="stylesheet" type="text/css" />

<#assign isElapsed = (communityEvent.endAt?? && communityEvent.endAt.time <= today.time) || (communityEvent.startAt.time <= today.time) />
<#assign hasMap = communityEvent.lat?? />

<section class="cc-info-left">
  <div class="sub-section-head">
    <hr/>
    <h3>${community.title?html}のイベント</h3>
  </div>
  <div class="pos-r">
  <#if communityEvent.status == 'LIVE'>
    <div class="cc-sub-menu-con js-toggle-menu-con">
      <ul class="cc-sub-menu js-toggle-menu b-shadow">
      	<#if isLogined && communityEvent.user.id == currentUser.id>
      	<li class="icon-caret-right"><a href="${url('/community-event/update/' + communityEvent.id?c )}">編集する</a></li>
      	<li class="icon-caret-right"><a class="js-confirm-contents" data-confirm-title="削除します。よろしいですか?" href="${url('/community-event/delete-ajax/' + communityEvent.id?c )}">削除する</a></li>
      	<#else>
      	<li class="icon-caret-right"><a href="${url('/user-violation/create-ajax/community-event/' + communityEvent.id?c)}" class="js-qt-modal">違反申告をする</a></li>
      	</#if>
      </ul>
      <a class="cc-sub-menu-switch js-btn-toggle-menu" href="#"><img src="${assets('icons/circle-arrow-down.png', 'images', 'common')}" alt="" /></a>
    </div>
  </#if>
  <h2 class="cc-title clearfix">
    <@my.activityIcon communityContentsCategory('event') />${communityEvent.title?html}
  </h2>
 
  <div class="cc-detail-menu fs-ss clearfix pt-12">
      <div class="fl clearfix">
        <a href="${url('/user/read/' + communityEvent.user.id?c)}" class="fl mr6 js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + communityEvent.user.id?c)}">${communityEvent.user.nickname?html}</a>
        <span class="created-at icon-time fl sub-text">${fTime(communityEvent.createdAt?string('yyyy/MM/dd HH:mm:ss'))}</span>
      </div>
      <ul class="v-right-list fr">
        <li><@my.likeBtn camelCase2dash(communityEvent.class.simpleName) communityEvent.id communityEvent.likeCnt communityEvent.isUserLiked /></li>
      </ul>
  </div>
 
  <div class="clearfix pt-12">
    <div class="fl">
	  <dl class="clearfix pb-12">
		  <dt class="fl">開催時刻&nbsp;</dt>
		  <dd class="fl icon-time">
			${communityEvent.startAt?string("M月dd日 H時mm分")}
			<#if communityEvent.endAt?? >
			〜 ${communityEvent.endAt?string("M月dd日 H時mm分")}
			</#if>
		  </dd>
	  </dl>
	  <dl class="clearfix pb-12">
		  <dt class="fl">参加者数&nbsp;</dt>
		  <dd class="fl">${communityEvent.joinUsersCnt?c}人</dd>
	  </dl>
    </div>
    
	<div class="fr">
	<#if isElapsed >
	  <span style="color:red">このイベントは終了しました。</span>
    <#else>
	  <#include '../community-event-user/_btn-join-state.ftl' />
	</#if>
	</div>
  </div>
  
  <#if communityEventUsers?? && communityEventUsers.items?? >
  <div class="pb-12">
    <ul class="c-m-list clearfix">
      <#list communityEventUsers.items as row>
      <li class="b-shadow radius-3 js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + row.user.id?c)}"><a href="${url('/uesr/read/' + row.user.id)}"><@my.userImg row.user "ss" /></a></li>
      </#list>
    </ul>
    <div class="clearfix pt-12">
	  <a class="fr fs-ss" href="${url('/community-event-user/index/' + communityEvent.id)}" >すべて見る</a>
    </div>
  </div>
  </#if>
  
  <#if communityEvent.place?? && communityEvent.place != "">
  <dl class="clearfix pb-12">
	<dt class="fl">場所&nbsp;</dt>
	<dd class="fl">${communityEvent.place?html}&nbsp;&nbsp;</dd>
	<#if hasMap>
	<dd class="fl icon-caret-right"><a href="${url('/community-event/read-map-ajax/' + communityEvent.id)}" class="modal">地図を見る</a></dd>
	</#if>
  </dl>
  </#if>
  
  <p>${emotion(communityEvent.description?html)?replace("\n", "<br/>")}</p>
  </div>
</section>

<div class="cc-info-right">
  <#include '../common/_sub-community-info.ftl' />
  
  <section>
    <ul id="ce-comments" class="cc-comments">
      <#assign commentFormAction = url('/community-event-comment/create-ajax') />
      <#include '../common/_comment-form.ftl' />
      <#if comments??>
	  	<#include '../common/_comments.ftl' />
      </#if>
    </ul>
    
	<#if communityEventComments.limit < communityEventComments.total >
      <div class="list-more-con loading">
        <a class="icon-caret-down btn-more" data-dest-con-selector="#ce-comments" href="${url('/community-event-comment/more-ajax/' + communityEvent.id )}" data-par-page-limit="${communityEventComments.limit}">もっと見る</a>
      </div>
    </#if>
  </section>
</div>

