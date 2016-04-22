<div id="u-fb-private-state" class="clearfix">
	<#if user.isFacebookUser()>
	<div class="fl bold" style="padding-right:18px;"><#if user.fbUser.isPrivate>非公開<#else>公開</#if></div>
	<a href="${url('/fb-user/update-private-ajax')}#u-fb-private-state" class="ajax-get icon-caret-right"><#if user.fbUser.isPrivate>公開する<#else>非公開にする</#if></a>
	<#else>
	<a href="${url('/fb-user/merge')}" class="icon-caret-right">Facebookアカウントを登録する</a>
	</#if>
</div>