<#assign contents = "language-setting" />
<#include "../common/_contents-header.ftl" />

<#include '../common/_pager.ftl' />
<div class="table">
    <ol class="c-list-h tr">
        <li class="language-setting-code th">code</li>
        <li class="language-setting-language-code th">languageCode</li>
        <li class="language-setting-contents th">contents</li>
        <li class="c-delete th">&nbsp;</li>
    </ol>
	<#list pager.items as row>
    <ol class="c-list-b tr">
        <li class="language-setting-code td"><a href="${url('/language-setting/update')}?languageSettingForm.code=${row.id.code?html}&languageSettingForm.languageCode=${row.id.languageCode?html}">${row.id.code?html}</a></li>
        <li class="language-setting-language-code td">${row.id.languageCode?html}</li>
        <li class="language-setting-contents td">${row.contents?html}</li>
        <li class="c-delete td"><a href="${url('/language-setting/delete')}?id.code=${row.id.code?html}" class="js-confirm">削除</a></li>
    </ol>
	</#list>
</div>
