<div class="c-like-users-panel">
  <h3 class="icon-thumbs-up">いいね！と言っている人 <span class="sub-text fs-ss">(${likedUsers.total})</span></h3>
  <#if likedUsers?? && likedUsers.items?? >
  <div class="c-like-users-con">
    <ul class="c-like-users">
      <#include '_like-users.ftl' />
    </ul>
  </div>
  <#if likedUsers.limit < likedUsers.total >
  <div class="list-more-con loading">
    <a data-dest-con-selector=".c-like-users" href="${url('/' + pathinfo('controller') + '/more-ajax/' + id )}" data-par-page-limit="${likedUsers.limit}"></a>
  </div>
  <script type="text/javascript">
    $('.c-like-users-con').scroll(function() {
      if ($(this).scrollTop() >= $(this).children().outerHeight() - $(this).innerHeight() - 20) {
   		$(this).find('.list-more-con a').click();
      }
    });
  </script>
  </#if>
  <#else>
  <p class="c-no-result"></p>
  </#if>
  <div class="p-4">
    <a href="#" class="js-cancel-modal btn-a">閉じる</a>
  </div>
</div>