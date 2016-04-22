<div id="c-u-btn-watch-state">
<#if isUserWatched >
  <a href="${url('/user-watch/delete-small-ajax/' + user.id)}#c-u-btn-watch-state" class="ajax-get simple-btn-small">ウォッチを止める</a>
<#else>
  <a href="${url('/user-watch/create-small-ajax/' + user.id)}#c-u-btn-watch-state" class="ajax-get simple-btn-small">ウォッチ</a>
</#if>
</div>