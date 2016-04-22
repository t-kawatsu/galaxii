<div id="u-bgimg" class="u-bgimg b-shadow">
	<@my.userBgImg user "m" />
	<ul class="u-img-update-menu hide clearfix">
	<#if user.userBgImageId?? >
		<li class="fl"><a href="${url('/user-image/create-bg-ajax')}" class="simple-btn-small modal icon-wrench">編集</a></li>
		<li class="fl pl-4"><a href="${url('/user-image/delete-bg-ajax/' + user.userBgImageId?c)}#u-bgimg" class="simple-btn-small ajax-get icon-minus-sign">削除</a></li>
	<#else>
		<li class="fl"><a href="${url('/user-image/create-bg-ajax')}" class="simple-btn-small modal icon-wrench">編集</a></li>
	</#if>
	</ul>
</div>