<#assign imageUploadAction = url('/user-image/upload-ajax') />
<#include '_image-upload-panel.ftl' />

<form id="js-u-img-dest-con" action="${url('/user-image/create-ajax')}#js-u-img-dest-con" class="hide ajax-form u-img-dest-con" method="POST">
    <@s.hidden name="userImageForm.imageIdsCsv" cssClass="c-images-csv-input" />
    <ul class="center-list u-img-create-ctrl" >
        <li class="c-btn-wrap"><button type="button" class="form-button btn-a js-cancel-modal btn-small" >キャンセル</button></li>
        <li class="c-btn-wrap"><@s.submit cssClass="form-submit btn-b btn-small" value="追加" /><li>
    </ul>
</form>

<script type="text/javascript">
	$(document).off('upload.complete', '.ajax-file-upload')
			.on('upload.complete', '.ajax-file-upload', 
			function(e, src, fileId, type, vendorData) {
		var con = $('#js-u-img-dest-con').removeClass("hide");
		con.append('<img src="'+src.lSrc+'" class="radius-3" width="512" height="512" />');
		con.find(".c-images-csv-input").val(fileId);
		$(".image-upload-panel").hide();
		$.fancybox.update();
	});
</script>