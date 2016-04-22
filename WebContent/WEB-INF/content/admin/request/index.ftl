<#include '../common/_pager.ftl' />
<div class="table">
    <ol class="c-list-h clearfix tr">
        <li class="request-id th">id</li>
        <li class="request-user-id th">user-id</li>
        <li class="request-description th">description</li>
        <li class="request-useragent th">useragent</li>
        <li class="request-created-at th">created-at</li>
    </ol>
    <#if pager.items??>
	<#list pager.items as row>
    <ol class="c-list-b clearfix tr">
        <li class="request-id td">${row.id}</li>
        <li class="request-user-id td">${row.userId!""}</li>
        <li class="request-description td">${row.description?html}</li>
        <li class="request-useragent td">${row.useragent}</li>
        <li class="request-created-at td">${row.createdAt?string("yyyy年MM月dd日 H時")}</li>
    </ol>
	</#list>
	</#if>
</div>
