<#assign contents = "language-setting" />
<#include "../common/_contents-header.ftl" />

<#if isUpdate!false>
<#assign formAction=url('/language-setting/update') />
<#else>
<#assign formAction=url('/language-setting/create') />
</#if>
<form action="${formAction}" method="POST">
  <dl class="c-input-con clearfix">
	<dt>code</dt>
	<dd>
		<@s.textfield name="languageSettingForm.code" readonly="isUpdate" />
		<@s.fielderror><@s.param value="%{'languageSettingForm.code'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>contents</dt>
	<dd>
		<@s.textarea name="languageSettingForm.contents" />
		<@s.fielderror><@s.param value="%{'languageSettingForm.contents'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>language_code</dt>
	<dd>
		<#if isUpdate!false >
		<@s.hidden name="languageSettingForm.languageCode" value="${languageSettingForm.languageCode}" />
		${languageSettingForm.languageCode}
		<#else>
		<@s.select key="languageSettingForm.languageCode" list="languageSettingForm.languageSelectList" readonly="readonly" />
		</#if>
		<@s.fielderror><@s.param value="%{'languageSettingForm.languageCode'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>&nbsp;</dt>
	<dd>
		<@s.submit value="送信する" cssClass="btn btn-submit" />
	</dd>
  </dl>
</form>
