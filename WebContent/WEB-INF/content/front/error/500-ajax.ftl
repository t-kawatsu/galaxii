<div id="js-request-login-message" class="hide">
  <div class="c-ng-label icon-exclamation-sign">
    エラーが発生しました！
  </div>
  <div class="pt-12" style="line-height: 20px;">
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