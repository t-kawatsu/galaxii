<div class="c-u-ajax-detail-panel">
  <div class="c-u-ajax-detail-bgimg"><@my.userBgImg user "ss" /></div>
  <div class="c-u-ajax-detail-panel-wrap">
  <div class="clearfix">
    <div class="c-u-ajax-detail-img"><@my.userImg user "ml" /></div>
    <div class="c-u-ajax-detail-meta">
      <!-- 
      <ul class="pt-12 clearfix">
        <li class="fl"><#include '../user-watch/_btn-small-watch-state.ftl'></li>
      </ul>
       -->
      <div class="c-u-ajax-detail-title"><a href="${url('/user/read/' + user.id)}">${user.nickname?html}</a></div>
    </div>
  </div>
  <div class="pt-12">${user.message!""?html}</div>
  </div>
</div>