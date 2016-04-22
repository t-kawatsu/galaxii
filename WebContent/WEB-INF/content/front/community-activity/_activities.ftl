<#list communityActivities.items as row >
    <li class="clearfix">
        <@my.activityIcon row.communityContentsCategoryId />
        <div class="c-activities-text">
          <div>
          <#switch row.communityContentsCategoryId>
          <#case "COMMENT">
            「${emotion(cutStr(row.name, 15)?html)}」
            <#break>
          <#case "IMAGE">
            「<a href="${url('/community/read/' + row.communityId + '/image?contentsId=' + row.communityContentsId)}">${cutStr(row.name, 15)?html}</a>」が追加されました。
            <#break>
          <#case "MOVIE">
            「<a href="${url('/community/read/' + row.communityId + '/movie?contentsId=' + row.communityContentsId)}">${cutStr(row.name, 15)?html}</a>」が追加されました。
            <#break>
          <#case "EVENT">
            「<a href="${url('/community-event/read/' + row.communityContentsId)}">${cutStr(row.name, 15)?html}</a>」が追加されました。
            <#break>
          <#case "REVIEW">
            「<a href="${url('/community-topic/read/' + row.communityContentsId)}">${cutStr(row.name, 15)?html}</a>」が追加されました。
            <#break>
          <#case "HOME">
            コミュニティが作られました
            <#break>
          <#case "RECOMMEND">
            オススメ「<a href="${url('/community/read/' + row.communityId + '/recommend?contentsId=' + row.communityContentsId)}">${cutStr(row.name, 15)?html}</a>」が追加されました。
            <#break>
          <#default>
            <#break>
          </#switch>
            <a href="${url('/community/read/' + row.communityId)}">${cutStr(row.community.title, 15)?html}</a>
          </div>
          <div class="icon-time c-activities-time sub-text">${fTime(row.createdAt)}  by <a class="js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + row.user.id?c)}" href="${url('/user/read/' + row.user.id )}">${row.user.nickname}</a></div>
        </div>
    </li>
</#list>