<#assign contents = "language-mail" />
<#include "../common/_contents-header.ftl" />

<#include '../common/_pager.ftl' />
<div class="table">
    <ol class="c-list-h tr">
        <li class="language-mail-code th">code</li>
        <li class="language-mail-subject th">subject</li>
        <li class="language-mail-body th">body</li>
        <li class="language-mail-language-code th">languageCode</li>
    </ol>
	<#list pager.items as row>
    <ol class="c-list-b tr">
        <li class="language-mail-code td"><a href="${url('/language-mail/update')}?languageMailForm.code=${row.id.code?html}&languageMailForm.languageCode=${row.id.languageCode?html}">${row.id.code?html}</a></li>
        <li class="language-mail-subject td">${row.subject?html}</li>
        <li class="language-mail-body td">${row.body?html}</li>
        <li class="language-mail-language-code td">${row.id.languageCode?html}</li>
    </ol>
	</#list>
</div>
