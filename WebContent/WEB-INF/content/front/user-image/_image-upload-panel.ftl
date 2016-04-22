<script type="text/javascript" src="${assets('jquery.upload-1.0.2.min.js', 'js', 'common')}" ></script>

<div class="image-upload-panel btn-a" style="padding:0px">
    <i class="icn-upload icon-upload-alt"></i>
    <div class="uploadable-image-format">jpeg / gif / png</div>
    <div class="btn-select-pc-file">パソコンからファイルを選択</div>
    <p class="lbl-image-drag-notice">ここにドラッグ＆ドロップしてファイルをアップロードすることも出来ます</p>
    <form action="${imageUploadAction}" enctype="multipart/form-data" class="ajax-file-upload" method="POST">
      <@s.file name="imageForm.file" label="Image" cssClass="form-file" />
      <@s.fielderror><@s.param value="%{'imageForm.file'}" /></@s.fielderror>
    </form>
</div>

<script type="text/javascript">

var upTriggerSelector = '.ajax-file-upload';
var pSelector         = '.btn-select-pc-file';

$(upTriggerSelector).on('change', "input[type='file']", function() {
    $(pSelector).html('アップロードしています...');
    var form = $(this).parents('form');
    $(this).upload(
    	$(this).parents('form').attr('action'), 
        { /* no_csrf:form.find('#no_csrf').val() */ },
    	function(res) {
            res = jQuery.parseJSON(res);
            if(!res || res.fileId == undefined) {
            	var eMsg = '<ul class="errorMessage">';
                jQuery.each(res.messages, function() {
                	eMsg += '<li><span>' + this + '</span></li>';
                });
                eMsg += '</ul>';
                $(pSelector).html(eMsg);
                return false;
            }
            $(upTriggerSelector).trigger('upload.complete', 
            		[res, res.fileId, res.type, res.vendorData]); 
    	}
    );
});
</script>