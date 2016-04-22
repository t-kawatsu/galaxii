<#assign contents = "language-help" />
<#include "../common/_contents-header.ftl" />

<#include '../common/_pager.ftl' />
<div class="table">
    <ol class="c-list-h tr">
        <li class="language-help-id th">id</li>
        <li class="language-help-title th">title</li>
        <li class="language-help-description th">description</li>
        <li class="language-help-language-code th">languageCode</li>
        <li class="c-delete th">&nbsp;</li>
    </ol>
	<#list pager.items as row>
    <ol class="c-list-b tr">
        <li class="language-help-id td"><a href="${url('/language-help/update/' + row.id)}">${row.id}</a></li>
        <li class="language-help-title td">${row.title?html}</li>
        <li class="language-help-description td">${row.description?html}</li>
        <li class="language-help-language-code td">${row.languageCode?html}</li>
        <li class="c-delete td"><a href="${url('/language-help/delete/' + row.id)}" class="js-confirm">削除</a></li>
    </ol>
	</#list>
</div>
