<title>退会</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<div class="clmn-left-main">
<section>
  <h1 class="icon-envelope">退会</h1>
    <form method="post" action="${url('/user/delete')}">
	  <dl class="input-item clearfix">
		<dt><span class="input-item-name"><label for="userSecessionForm_reasonCode">退会理由</label></span></dt>
		<dd>
			<@s.select key="userSecessionForm.reasonCode" list="userSecessionForm.reasonCodes" cssClass="form-select" />
			<@s.fielderror><@s.param value="%{'userSecessionForm.reasonCode'}" /></@s.fielderror>
		</dd>
	  </dl>

	  <dl class="input-item clearfix">
		<dt><span class="input-item-name"><label for="userSecessionForm_description">退会理由内容</label></span></dt>
		<dd>
		  <@s.textarea name="userSecessionForm.description" label="自由入力" rows="6" cssClass="form-textarea input-shadow" />
		  <@s.fielderror><@s.param value="%{'userSecessionForm.description'}" /></@s.fielderror>
		</dd>
	  </dl>

	  <@s.token />
	  <dl class="input-item clearfix">
		<dt>&nbsp;</dt>
		<dd><@s.submit value="退会する" cssClass="btn-b btn-large form-submit" /></dd>
	  </dl>  
	</form>
  </section>
</div>

<div class="clmn-right-menu">
  <#include '../request/create.ftl' />
</div>
