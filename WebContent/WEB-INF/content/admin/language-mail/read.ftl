<#assign contents = "language-mail" />
<#include "../common/_contents-header.ftl" />

<dl class="c-input-con">
	<dt>code</dt>
	<dd>
		${languageMail.id.code?html}
	</dd>
	<dt>subject</dt>
	<dd>
		${languageMail.subject?html}
	</dd>
	<dt>body</dt>
	<dd>
		${languageMail.body?html}
	</dd>
	<dt>language_code</dt>
	<dd>
		${languageMail.id.code?html}
	</dd>
</dl>

