<ul class="youtube-video-entries clearfix">
  <#list youtubeItems as row>
    <li class="radius-3 js-tooltip" title="${row.title?html}">
      <img class="youtube-video-entry-img radius-3" src="${row.image.url}" height="130" />
      <div class="youtube-video-entry-detail">
        <div class="youtube-video-entry-title">${cutStr(row.title, 32)?html}</div>
      </div>
      <div class="clearfix youtube-video-entry-menu">
        <a href="#" class="btn-play-video"><span>動画を再生</span></a>
        <a href="#" class="btn-stop-video" style="display:none">戻る</a>
      </div>
      <@s.hidden name="youtubeVideoId" value="${row.entryId}" cssClass="youtube-video-id" />
    </li>
  </#list>
</ul>