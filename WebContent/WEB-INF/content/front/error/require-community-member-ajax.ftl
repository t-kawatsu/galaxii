<div id="js-request-require-member-message" class="hide">
  <div class="c-ng-label icon-exclamation-sign">
    操作に失敗しました。
  </div>
  <div class="pt-12" style="line-height: 20px;">
    コミュニティメンバーでなければこの操作は出来ません。
  </div>
</div>

<script type="text/javascript">
var isOverlayPresent = 0 < $('.fancybox-overlay').length;
if(isOverlayPresent) {
  $('.fancybox-inner').html($('#js-request-require-member-message').clone().removeClass('hide'));
  $.fancybox.update();
  $(window).qtip('hide').qtip('destroy');
} else {
  $.fancybox.open($('#js-request-require-member-message').clone().removeClass('hide'));
  $(window).qtip('hide').qtip('destroy');
}
</script>