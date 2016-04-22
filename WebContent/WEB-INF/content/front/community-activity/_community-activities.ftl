<#assign textLen = 40 />
<#list communityActivities.items as row >
<li class="clearfix">
  <@my.activityIcon row.communityContentsCategoryId />
  <div class="c-activities-text">
  <#switch row.communityContentsCategoryId>
    <#case "COMMENT">
      <div>${emotion(cutStr(row.name, textLen)?html)}</div>
      <#break>
    <#case "IMAGE">
      <div><a href="${url('/community-image/read-ajax/' + row.communityContentsId)}" class="modal">${cutStr(row.name, textLen)?html}</a></div>
      <#break>
    <#case "MOVIE">
      <div><a href="${url('/community-image/read-ajax/' + row.communityContentsId)}" class="modal">${cutStr(row.name, textLen)?html}</a></div>
      <#break>
    <#case "EVENT">
      <div><a href="${url('/community-event/read/' + row.communityContentsId)}">${cutStr(row.name, textLen)?html}</a></div>
      <#break>
    <#case "REVIEW">
      <div><a href="${url('/community-topic/read/' + row.communityContentsId)}">${cutStr(row.name, textLen)?html}</a></div>
      <#break>
    <#case "HOME">
      <div>コミュニティが作られました</div>
      <#break>
    <#case "RECOMMEND">
      <div><a href="${url('/community-recommend/read-ajax/' + row.communityContentsId)}" class="modal">${cutStr(row.name, textLen)?html}</a></div>
      <#break>
    <#default>
      <#break>
  </#switch>
    <div class="icon-time c-activities-time fs-ss sub-text">${fTime(row.createdAt)}  by <a href="${url('/user/read/' + row.user.id )}" class="js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + row.user.id?c)}">${row.user.nickname?html}</a></div>
  </div>
</li>
</#list>