<#if comments?? >
<#list comments as row>
<li id="${camelCase2dash(row.class.simpleName)}-${row.id?c}" class="clearfix">
  <div class="cc-comment-user">
    <a class="js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + row.user.id?c)}" href="${url('/user/read/' + row.user.id?c)}" ><@my.userImg row.user "s" /></a>
  </div>
  <div class="cc-comment-wrap">
    <div class="cc-comment radius-3 b-shadow">
      <#if row.status == 'LIVE'>
      <div class="cc-sub-menu-con js-toggle-menu-con">
      	<ul class="cc-sub-menu js-toggle-menu b-shadow">
      	  <li class="icon-caret-right"><a href="${url('/user-violation/create-ajax/' + camelCase2dash(row.class.simpleName) + '/' + row.id?c)}" class="js-qt-modal">違反申告をする</a></li>
      	  <#if isLogined && row.user.id == currentUser.id>
      	  <li class="icon-caret-right"><a class="js-confirm-contents" data-confirm-title="削除します。よろしいですか?" href="${url('/' +camelCase2dash(row.class.simpleName)+ '/delete-ajax/' + row.id?c )}">削除する</a></li>
      	  </#if>
      	</ul>
      	<a class="cc-sub-menu-switch js-btn-toggle-menu" href="#"><img src="${assets('icons/circle-arrow-down.png', 'images', 'common')}" alt="" /></a>
      </div>
      </#if>
      <#if row.status == 'LIVE'>
      <p>${emotion(row.description?html)}</p>
      <#elseif row.status == 'DELETED'>
      <p class="sub-text">このコメントは削除されました。</p>
      </#if>
      <div class="cc-detail-menu fs-ss clearfix">
        <div class="fl clearfix">
          <a href="${url('/user/read/' + row.user.id?c)}" class="fl mr6 js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + row.user.id?c)}">${cutStr(row.user.nickname, 6)?html}</a>
          <span class="created-at icon-time fl sub-text">${fTime(row.createdAt?string('yyyy/MM/dd HH:mm:ss'))}</span>
        </div>
        <#if row.status == 'LIVE'>
        <ul class="v-right-list fr clearfix">
          <li class="clearfix"><a href="#description" class="icon-reply icon-share-alt js-comment-reply fl" data-js-user-nickname="${row.user.nickname}">返信する</a></li>
          <li><@my.likeBtn camelCase2dash(row.class.simpleName) row.id row.likeCnt row.isUserLiked /></li>
        </ul>
        </#if>
      </div>
    </div>
  </div>
</li>
</#list>
</#if>