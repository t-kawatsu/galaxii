<title>ユーザー登録</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" />

<section>
	<h1 class="icon-plus">ユーザー登録</h1>
	<div class="clearfix">
		<section class="u-create-left">
			<div class="sub-section-head">
				<hr />
				<h2>アカウント情報入力</h2>
			</div>
			
			<form class="u-create-form" action="${url('/user/create', 'true', 'true')}" method="POST">
				<dl class="input-item clearfix js-input-balloon-con <@my.errorInputClass 'userForm.nickname'/>">
				  <dt>
				  	<span class="input-item-name"><label for="userForm_nickname">名前(ニックネーム)</label></span>
				  	<span class="input-item-description">2~20文字</span>
				  </dt>
				  <dd>
				  	<@s.textfield name="userForm.nickname" cssClass="form-text input-shadow" autocomplete="off" placeholder="ニックネーム" />
				    <@s.fielderror><@s.param value="%{'userForm.nickname'}" /></@s.fielderror> 
				    <div class="js-input-balloon-d">
				  	  このサイトでのあなたの表示名です。<br/>2文字以上20文字以内で入力して下さい。
					</div>
				  </dd>
				</dl>

				<dl class="input-item clearfix js-input-balloon-con <@my.errorInputClass 'userForm.mailAddress'/>">
				  <dt>
				  	<span class="input-item-name"><label for="userForm_mailAddress">メールアドレス</label></span>
				  </dt>
				  <dd>
				  	<@s.textfield name="userForm.mailAddress" cssClass="form-text input-shadow" autocomplete="off" placeholder="メールアドレス" maxlength="100" size="50" />
				    <@s.fielderror><@s.param value="%{'userForm.mailAddress'}" /></@s.fielderror> 
				  	<div class="js-input-balloon-d">
				  	  このメールアドレスに確認メールを送信します。
				  	  迷惑メール防止設定をされている場合は @<@s.text name="app.site.domain" />からの
				  	  メールを受信出来ることをご確認ください。
					</div>
				  </dd>
				</dl>
			 
				<dl class="input-item clearfix js-input-balloon-con <@my.errorInputClass 'userForm.password'/>">
				  <dt>
				  	<span class="input-item-name"><label for="userForm_password">パスワード</label></span>
				  	<span class="input-item-description">6~12文字 半角英数字</span>
				  </dt>
				  <dd>
				  	<@s.password name="userForm.password" cssClass="form-text input-shadow" autocomplete="off" />
				    <@s.fielderror><@s.param value="%{'userForm.password'}" /></@s.fielderror> 
				  	<div class="js-input-balloon-d">
				  	ログイン時のパスワードを設定します。<br/>
				  	半角英数字が使用できます。<br />
				  	安全性を得るため、あなた以外の人がすぐに推測できる単語や文字列は避けてください。<br />
					</div>
				  </dd>
				</dl>
			  
				<dl class="input-item clearfix js-input-balloon-con <@my.errorInputClass 'userForm.passwordCon'/>">
				  <dt>
				  	<span class="input-item-name"><label for="userForm_passwordCon">パスワード(確認)</label></span>
				  	<span class="input-item-description">確認用に同じパスワードを入力</span>
				  </dt>
				  <dd>
				  	<@s.password name="userForm.passwordCon" cssClass="form-text input-shadow" autocomplete="off" />
				    <@s.fielderror><@s.param value="%{'userForm.passwordCon'}" /></@s.fielderror>
				  	<div class="js-input-balloon-d">
				  	パスワード入力に誤りがないかの確認です。
				  	同じパスワードを再度入力してください。
					</div>
				  </dd>
				</dl>
			  
			    <dl class="input-item clearfix">
				  <dt><span class="input-item-name"><label for="userForm_sex">性別</label></span></dt>
				  <dd><@s.radio listKey="key" listValue="value" list="userForm.sexes" key="userForm.sex"/></dd>
				</dl>

				<dl class="input-item clearfix js-input-balloon-con">
				  <dt><span class="input-item-name">利用規約の同意</span></dt>
				  <dd>
				  	<@s.checkbox name="userForm.agree" cssClass="form-checkbox" /><label for="userForm_agree"><a href="/terms" target="_blank">Galaxii利用規約</a>に同意する</label>
				  	<@s.fielderror><@s.param value="%{'userForm.agree'}" /></@s.fielderror>
				  </dd>
				</dl>
				
			  	<dl class="input-item clearfix item-submit">
					<dt>&nbsp;<@s.token /></dt>
					<dd><@s.submit value="次へ" cssClass="btn-o btn-large form-submit" /></dd>
			  	</dl>
			</form>
		</section>

		<section class="u-create-right vr-l">
			<div class="sub-section-head u-create-sep fs-ss">または</div>
			<div class="sub-section-head">
				<hr />
				<h2>外部サービスのアカウントを使って登録</h2>
			</div>
			<div>
				<div class="pt-12">
				  <@s.checkbox name="facebookUserForm.agree" cssClass="form-checkbox" />
				  <a href="${url('/terms')}" target="_blank"><@s.text name="app.site.name" />利用規約</a>に同意する
				</div>
				<div class="mt-12">
				  <a class="btn-fb icon-facebook-sign" href="${url('/fb-user/create')}">Facebookアカウントで登録</a>
				</div>
				<div class="mt-12">
				  <a class="btn-tw icon-twitter-sign" href="${url('/tw-user/create')}">Twitterアカウントで登録</a>
				</div>
			</div>
		</section>
	</div>
</section>

