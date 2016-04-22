<title>お問い合わせ</title>

<link href="${assets('inquiry.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<div class="clmn-left-main">
<section>
  <h1 class="icon-envelope">お問い合わせ</h1>
    <form method="post" action="${url('/inquiry/create')}">
	  <dl class="input-item clearfix">
		<dt><span class="input-item-name"><label for="inquiryForm_typeId">お問い合わせ項目</label></span></dt>
		<dd>
		  <@s.select listKey="key" listValue="value" list="inquiryForm.types" key="inquiryForm.typeId" cssClass="form-select" />
		</dd>
	  </dl>

	  <dl class="input-item clearfix <@my.errorInputClass 'inquiryForm.mailAddress'/>">
		<dt><span class="input-item-name"><label for="inquiryForm_mailAddress">メールアドレス</label></span></dt>
		<dd>
		  <@s.textfield name="inquiryForm.mailAddress" cssClass="form-text input-shadow" autocomplete="off" placeholder="メールアドレス" maxlength="100" size="50" />
		  <@s.fielderror><@s.param value="%{'inquiryForm.mailAddress'}" /></@s.fielderror> 
		</dd>
	  </dl>

	  <dl class="input-item clearfix <@my.errorInputClass 'inquiryForm.description'/>">
		<dt><span class="input-item-name"><label for="inquiryForm_description">お問い合わせ内容</label></span></dt>
		<dd>
		  <@s.textarea name="inquiryForm.description" cssClass="form-textarea input-shadow" rows="6" />
		  <@s.fielderror><@s.param value="%{'inquiryForm.description'}" /></@s.fielderror> 
		</dd>
	  </dl>

	  <@s.token />
	  <dl class="input-item clearfix">
		<dt>&nbsp;</dt>
		<dd><@s.submit value="送信する" cssClass="btn-o btn-large form-submit" /></dd>
	  </dl>  
	</form>
  </section>
</div>