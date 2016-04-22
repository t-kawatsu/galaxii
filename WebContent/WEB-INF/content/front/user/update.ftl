<title>ユーザー情報編集</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<div class="clmn-left-main">
  <h1 class="icon-wrench">ユーザー情報編集</h1>
  <section>
	<form action="${url('/user/update', 'true', 'true')}" class="clearfix u-update-form" method="POST">
	  <div class="u-detail-left">
	    <#include '_update-nickname.ftl' /> 
 		<#include '_update-message.ftl' />
 		<#include '_update-mail-address.ftl' />
 		<#include '_update-password.ftl' />
	  </div>
	  <div class="u-detail-right">
	    <dl class="u-detail-item">
	      <dt class="fs-ss">Facebookアカウント</dt>
	      <dd><#include '../fb-user/_btn-private.ftl' /></dd>
	    </dl>   
	    <dl class="u-detail-item">
	      <dt class="fs-ss">Twitterアカウント</dt>
	      <dd><#include '../tw-user/_btn-private.ftl' /></dd>
	    </dl>
		<#include '_update-sex.ftl' />
		<#-- <#include '_update-birthday.ftl' /> -->
		<#include '_update-mail-notice-frequency.ftl' />
	  </div>
	</form>
  </section>
</div>

<div class="clmn-right-menu">
  <#if !isMyPage >
  <section>
	<div class="pb-6">
	  <#include '../user-watch/_btn-watch-state.ftl' />
	</div>
  </section>
  </#if>
  <#include '../user-watch/_watch.ftl' />
  <#include '../user-watch/_watched.ftl' />
</div>