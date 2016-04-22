<#assign contents = "language-help" />
<#include "../common/_contents-header.ftl" />

<#if isUpdate!false>
<#assign formAction=url('/language-help/update/' + id) />
<#else>
<#assign formAction=url('/language-help/create') />
</#if>
<form action="${formAction}" method="POST">
  <dl class="c-input-con clearfix">
	<dt>title</dt>
	<dd>
		<@s.textfield name="languageHelpForm.title" readonly="isUpdate" />
		<@s.fielderror><@s.param value="%{'languageHelpForm.title'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>description</dt>
	<dd>
		<@s.textarea name="languageHelpForm.description" />
		<@s.fielderror><@s.param value="%{'languageHelpForm.description'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>language_code</dt>
	<dd>
		<#if isUpdate!false >
		<@s.hidden name="languageHelpForm.languageCode" value="${languageHelpForm.languageCode}" />
		${languageHelpForm.languageCode}
		<#else>
		<@s.select key="languagehelpForm.languageCode" list="languageHelpForm.languageSelectList" readonly="readonly" />
		</#if>
		<@s.fielderror><@s.param value="%{'languageHelpForm.languageCode'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>&nbsp;</dt>
	<dd>
		<@s.submit value="送信する" cssClass="btn btn-submit" />
	</dd>
  </dl>
</form>

