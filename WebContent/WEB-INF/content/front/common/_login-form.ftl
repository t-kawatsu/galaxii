<form id="login-form" enctype="application/x-www-form-urlencoded" method="post" 
  class="ajax-form login-form" action="${url('/user/login-ajax#login-form')}">
  <dl>
	<dt><label for="login-mail-address">メールアドレス</label></dt>
    <dd>
	  <@s.textfield name="loginForm.mailAddress" placeholder="メールアドレス" 
			maxlength="100" size="20" autocomplete="off" cssClass="input-shadow form-text"/>
	  <@s.fielderror><@s.param value="%{'loginForm.mailAddress'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl>
	<dt><label for="login-password">パスワード</label></dt>
	<dd>
	  <@s.password name="loginForm.password" maxlength="12" size="20" autocomplete="off" cssClass="input-shadow form-password" />
	  <@s.fielderror><@s.param value="%{'loginForm.password'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl>
    <dt></dt>
    <dd><@s.checkbox id="loginFormAjax_keep" name="loginForm.keep" cssClass="form-checkbox" /><label for="loginFormAjax_keep">ログイン状態を維持する</label></dd>
  </dl>
  <div class="item-login_submit"><@s.submit value="ログイン" cssClass=" btn-o form-submit" /></div>
  <@s.token />
  <div class="link-remember-password icon-caret-right"><a href="${url('/user/send-password', 'true', 'true')}">パスワードを忘れた方</a></div>
</form>
