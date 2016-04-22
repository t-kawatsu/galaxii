<script type="text/javascript" src="${assets('jquery.upload-1.0.2.min.js', 'js', 'common')}" ></script>

<div class="image-upload-panel btn-a" style="padding:0px; <#if hasSuspendFile?? && hasSuspendFile>display:none;</#if>" >
    <i class="icn-upload icon-upload-alt"></i>
    <div class="uploadable-image-format">jpeg / gif / png</div>
    <div class="btn-select-pc-file">パソコンからファイルを選択</div>
    <p class="lbl-image-drag-notice">ここにドラッグ＆ドロップしてファイルをアップロードすることも出来ます</p>
    <form action="${url('/community-image/upload-ajax')}" enctype="multipart/form-data" class="ajax-file-upload" method="POST">
      <@s.file name="imageForm.file" label="Image" cssClass="form-file" />
      <@s.fielderror><@s.param value="%{'imageForm.file'}" /></@s.fielderror>
    </form>
</div>

<script type="text/javascript">
$('.ajax-file-upload').on('change', "input[type='file']", function() {
    var app = my.resources.get("app");
    var form = $(this).parents('form');
    $(app.image.settings.msgSelector).html('アップロードしています...');
    $(this).upload(
        form.attr('action'), 
        { /* no_csrf:form.find('#no_csrf').val() */ },
        function(res) {
        	app.image.processCreatedCallback(res);
        }
    );
});
</script>