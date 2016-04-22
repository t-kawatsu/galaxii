<div id="js-u-img-replace">
<#include '_u-img.ftl' />
</div>

<script type="text/javascript">
$('.u-img-con .u-img').replaceWith($('#js-u-img-replace').find('.u-img'));
$.fancybox.close();
</script>