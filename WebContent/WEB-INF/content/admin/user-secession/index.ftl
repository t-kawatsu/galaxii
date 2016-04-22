
<#include '../common/_pager.ftl' />
<div class="table">
    <ol class="c-list-h tr">
        <li class="user-secession-user-id th">user-id</li>
        <li class="user-secession-reason-code th">reason-code</li>
        <li class="user-secession-description th">description</li>
        <li class="user-secession-created-at th">created-at</li>
    </ol>
    <#if pager.items??>
	<#list pager.items as row>
    <ol class="c-list-b tr">
        <li class="user-secession-user-id td">${row.id}</li>
        <li class="user-secession-reason-code td">${row.reasonCode}</li>
        <li class="user-secession-description td">${row.description}</li>
        <li class="resource-last-builded-at td">${row.createdAt?string("yyyy年MM月dd日 H時")}</li>
    </ol>
	</#list>
	</#if>
</div>
