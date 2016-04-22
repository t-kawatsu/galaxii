<div id="u-img" class="u-img b-shadow radius-3">
  <#if user.userImageId?? >
    <a href="${userImageSrc(user.id?c, user.userImageId?c, "l")}" class="image-modal"><@my.userImg user "m" /></a>
  	<ul class="u-img-update-menu hide clearfix">
  	  <li class="fl"><a href="${url('/user-image/create-ajax')}" class="simple-btn-small modal icon-wrench">編集</a></li>
  	  <li class="fl pl-4"><a href="${url('/user-image/delete-ajax/' + user.userImageId?c)}#u-img" class="simple-btn-small ajax-get icon-minus-sign">削除</a></li>
  	</ul>
  <#else>
    <@my.userImg user "m" />
  	<ul class="u-img-update-menu hide clearfix">
  	  <li class="fl"><a href="${url('/user-image/create-ajax')}" class="simple-btn-small modal icon-wrench">編集</a></li>
  	</ul>
  </#if>
</div>