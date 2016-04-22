<title>ログイン</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" />

<section>
	<h1 class="icon-plus">ログイン</h1>
	<div class="clearfix">
	  <section class="u-create-left">
		<div class="sub-section-head">
		  <hr />
		  <h2><@s.text name="app.site.name" />アカウント</h2>
		</div>
		<form class="u-create-form" action="${url('/user/login')}" method="POST">
			<dl class="input-item clearfix js-input-balloon-con <@my.errorInputClass 'loginForm.mailAddress'/>">
			  <dt><span class="input-item-name"><label for="loginForm_mailAddress">メールアドレス</label></span></dt>
			  <dd>
				<@s.textfield name="loginForm.mailAddress" cssClass="form-text input-shadow" autocomplete="off" placeholder="メールアドレス" />
				<@s.fielderror><@s.param value="%{'loginForm.mailAddress'}" /></@s.fielderror> 
				<div class="js-input-balloon-d">アカウントに登録しているメールアドレスを入力して下さい。</div>
			  </dd>
			</dl>
			  
			<dl class="input-item clearfix <@my.errorInputClass 'loginForm.password'/>">
			  <dt><span class="input-item-name"><label for="loginForm_password">パスワード</label></span></dt>
			  <dd>
				<@s.password name="loginForm.password" cssClass="form-text input-shadow" autocomplete="off" />
				<@s.fielderror><@s.param value="%{'loginForm.password'}" /></@s.fielderror> 
				<div class="link-remember-password icon-caret-right"><a href="${('/user/send-password')}">パスワードを忘れた方はこちら</a></div>
			  </dd>
			</dl>
			
			<dl class="input-item clearfix">
			  <dt><span class="input-item-name">ログイン状態を維持</span></dt>
			  <dd class="p-4">
			    <@s.checkbox name="loginForm.keep" cssClass="form-checkbox" /><label for="loginForm_keep">ログイン状態を維持する</label>
				<@s.fielderror><@s.param value="%{'loginForm.keep'}" /></@s.fielderror>
			  </dd>
			</dl>
		  
		  <@s.token />
		  <dl class="input-item clearfix item-submit">
			<dt>&nbsp;</dt>
			<dd><@s.submit value="送信" cssClass="btn-o btn-large form-submit" /></dd>
		  </dl>
		</form>
	  </section>

	  <section class="u-create-right vr-l">
		<div class="sub-section-head u-create-sep fs-ss">または</div>
		<div class="sub-section-head">
		  <hr />
		  <h2>外部サービスのアカウントを使ってログイン</h2>
		</div>
		<div>
		  <div class="mt-12">
			<a class="btn-fb icon-facebook-sign" href="${url('/fb-user/login')}">Facebookアカウントでログイン</a>
		  </div>
		  <div class="mt-12">
			<a class="btn-tw icon-twitter-sign" href="${url('/tw-user/login')}">Twitterアカウントでログイン</a>
		  </div>
		</div>
	  </section>
	</div>
</section>
