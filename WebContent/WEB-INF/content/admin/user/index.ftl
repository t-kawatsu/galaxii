
<#include '../common/_pager.ftl' />
<div class="table">
    <ol class="c-list-h tr">
        <li class="user-id th">id</li>
        <li class="user-nickname th">nickname</li>
        <li class="user-last-logined-at th">last-logined-at</li>
        <li class="user-last-mail-noticed-at th">last-mail-noticed-at</li>
        <li class="user-mail-notice-frequency-code th">mail-notice-frequency-code</li>
        <li class="user-useragent th">useragent</li>
        <li class="user-account-types th">account-types</li>
        <li class="user-status th">status</li>
        <li class="user-created-at th">created-at</li>
    </ol>
    <#if pager.items??>
	<#list pager.items as row>
    <ol class="c-list-b tr">
        <li class="user-id td">${row.id}</li>
        <li class="user-nickname td">${row.nickname?html}</li>
        <li class="user-last-logined-at td">${row.lastLoginedAt?string("yyyy年MM月dd日 H時")}</li>
        <li class="user-last-mail-noticed-at td">${row.lastMailNoticedAt?string("yyyy年MM月dd日 H時")}</li>
        <li class="user-mail-notice-frequency-code td">${row.mailNoticeFrequencyCode}</li>
        <li class="user-useragent td">${row.useragent}</li>
        <li class="user-account-types td">${row.accountTypes}</li>
        <li class="user-status td">${row.status}</li>
        <li class="user-created-at td">${row.createdAt?string("yyyy年MM月dd日 H時")}</li>
    </ol>
	</#list>
	</#if>
</div>
