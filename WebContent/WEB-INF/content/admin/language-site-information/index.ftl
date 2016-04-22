<#assign contents = "language-site-information" />
<#include "../common/_contents-header.ftl" />

<#include '../common/_pager.ftl' />
<div class="table">
    <ol class="c-list-h tr">
        <li class="site-information-id th">id</li>
        <li class="site-information-title th">title</li>
        <li class="site-information-description th">description</li>
        <li class="site-information-language-code th">language-code</li>
        <li class="site-information-start-at th">start-at</li>
        <li class="site-information-end-at th">end-at</li>
    </ol>
    <#if pager.items??>
	<#list pager.items as row>
    <ol class="c-list-b tr">
        <li class="site-information-id td"><a href="${url('/language-site-information/update/' + row.id)}">${row.id}</a></li>
        <li class="site-information-title td">${row.title?html}</li>
        <li class="site-information-description td">${row.description?html}</li>
        <li class="site-information-language-code td">${row.languageCode}</li>
        <li class="site-information-start-at td">${row.startAt?string("yyyy年MM月dd日 H時")}</li>
        <li class="site-information-end-at td">${row.endAt?string("yyyy年MM月dd日 H時")}</li>
    </ol>
	</#list>
	</#if>
</div>
