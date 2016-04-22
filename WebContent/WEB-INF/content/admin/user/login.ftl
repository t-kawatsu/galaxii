<section>
	<h1 class="icon-plus">ログイン</h1>
	<form action="${url('/user/login', 'true', 'true')}" method="POST">
		<dl class="c-input-con clearfix">
			<dt><label for="adminLoginForm_name">アカウント名</label></dt>
			<dd>
				<@s.textfield name="adminLoginForm.name" autocomplete="off" placeholder="アカウント名" />
				<@s.fielderror><@s.param value="%{'adminLoginForm.name'}" /></@s.fielderror> 
			</dd>
		</dl>
		
		<dl class="c-input-con clearfix">
			<dt><label for="adminLoginForm_password">パスワード</label></dt>
			<dd>
				<@s.password name="adminLoginForm.password" autocomplete="off" />
				<@s.fielderror><@s.param value="%{'adminLoginForm.password'}" /></@s.fielderror> 
			</dd>
		</dl>
		
		<dl class="c-input-con clearfix">
			<dt><@s.token />&nbsp;</dt>
			<dd><@s.submit value="送信" cssClass="btn btn-submit" /></dd>
		</dl>
	</form>
</section>
