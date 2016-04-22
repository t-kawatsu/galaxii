<#assign contents = "language-mail" />
<#include "../common/_contents-header.ftl" />

<#if isUpdate!false>
<#assign formAction=url('/language-mail/update') />
<#else>
<#assign formAction=url('/language-mail/create') />
</#if>
<form action="${formAction}" method="POST">
  <dl class="c-input-con clearfix">
	<dt>code</dt>
	<dd>
		<@s.textfield name="languageMailForm.code" readonly="isUpdate" />
		<@s.fielderror><@s.param value="%{'languageMailForm.code'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>subject</dt>
	<dd>
		<@s.textfield name="languageMailForm.subject" />
		<@s.fielderror><@s.param value="%{'languageMailForm.subject'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>body</dt>
	<dd>
		<@s.textarea name="languageMailForm.body" cols="50" rows="6" />
		<@s.fielderror><@s.param value="%{'languageMailForm.body'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>language_code</dt>
	<dd>
		<#if isUpdate!false >
		<@s.hidden name="languageMailForm.languageCode" value="${languageMailForm.languageCode}" />
		${languageMailForm.languageCode}
		<#else>
		<@s.select key="languageMailForm.languageCode" list="languageMailForm.languageSelectList" readonly="readonly" />
		</#if>
		<@s.fielderror><@s.param value="%{'languageMailForm.languageCode'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>&nbsp;</dt>
	<dd>
		<@s.submit value="送信する" cssClass="btn btn-submit"/>
	</dd>
  </dl>
</form>

