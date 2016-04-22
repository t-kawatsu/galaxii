<div id="js-request-login-message" class="hide">
  <div class="c-ng-label icon-exclamation-sign">
    操作に失敗しました。
  </div>
  <div class="pt-12" style="line-height: 20px;">
    この操作にはアカウントログインが必要です。<br/>
    新規登録画面からユーザーを作成するか、既に<br/>
    ログインアカウントをお持ちであればログインし直して下さい。
  </div>
</div>

<script type="text/javascript">
var isOverlayPresent = 0 < $('.fancybox-overlay').length;
if(isOverlayPresent) {
  $('.fancybox-inner').html($('#js-request-login-message').clone().removeClass('hide'));
  $.fancybox.update();
  $(window).qtip('hide').qtip('destroy');
} else {
  $.fancybox.open($('#js-request-login-message').clone().removeClass('hide'));
  $(window).qtip('hide').qtip('destroy');
}
</script>