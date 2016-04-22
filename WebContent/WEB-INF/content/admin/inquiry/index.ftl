<#include '../common/_pager.ftl' />
<div class="table">
    <ol class="c-list-h clearfix tr">
        <li class="inquiry-id th">id</li>
        <li class="inquiry-user-id th">user-id</li>
        <li class="inquiry-type-id th">type-id</li>
        <li class="inquiry-mail-address th">mail-address</li>
        <li class="inquiry-description th">description</li>
        <li class="inquiry-useragent th">useragent</li>
        <li class="inquiry-created-at th">created-at</li>
    </ol>
    <#if pager.items??>
	<#list pager.items as row>
    <ol class="c-list-b clearfix tr">
        <li class="inquiry-id td">${row.id}</li>
        <li class="inquiry-user-id td">${row.userId!""}</li>
        <li class="inquiry-type-id td">${row.typeId}</li>
        <li class="inquiry-mail-address td">${row.mailAddress?html}</li>
        <li class="inquiry-description td">${row.description?html}</li>
        <li class="inquiry-useragent td">${row.useragent}</li>
        <li class="inquiry-created-at td">${row.createdAt?string("yyyy年MM月dd日 H時")}</li>
    </ol>
	</#list>
	</#if>
</div>
