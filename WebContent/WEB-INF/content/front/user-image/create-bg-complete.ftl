<div id="js-u-img-replace">
<#include '_u-bg-img.ftl' />
</div>

<script type="text/javascript">
$('.u-img-con .u-bgimg').replaceWith($('#js-u-img-replace').find('.u-bgimg'));
$.fancybox.close();
</script>