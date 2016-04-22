<section id="ci-create-con">
	<#assign hasSuspendFile = communityImageForm.imageIds?? && 0 < communityImageForm.imageIds?size />
	<#include '../common/_image-upload-panel.ftl' />
    <form class="ajax-form" action="${url('/community-image/create-ajax?communityId=' + communityId + '#ci-create-con')}" method="POST">
        <div class="cm-large-image-con <#if !hasSuspendFile>hide</#if>">
            <#if hasSuspendFile >
              <#list communityImageForm.imageIds as row >
                <img src="${url('/community-image/read-tmp/' + row + '/l')}" alt="" width="512" height="384" class="radius-3" />
              </#list>
            </#if>
            <div class="cc-no-image <#if hasSuspendFile > hide</#if>" >
              <a class="modal cm-btn-img-upload" href="${url('/community-image/create-tmp-ajax')}">
              <i class="icn-upload icon-upload-alt"></i><span class="sub-text">画像をアップロード</span>
              </a>
            </div>
            <div class="cc-remove-image-con"><a class="btn-a <#if !hasSuspendFile > hide</#if> cc-remove-image radius-3">× キャンセル</a></div>
            <@s.hidden name="communityImageForm.imageIdsCsv" cssClass="c-images-csv-input" />
            <@s.hidden name="communityImageForm.imageTypesCsv" cssClass="c-image-types-csv-input" />
            <@s.hidden name="communityImageForm.imageVendorDataCsv" cssClass="c-image-vendor-data-input" />
        </div>

        <div class="<@my.errorInputClass 'communityImageForm.title'/>">
        	<@s.textarea id="ci-title-input" name="communityImageForm.title" cssClass="form-text cancel-enter char-counter cc-input auto-resize-textbox" placeholder="タイトルを入力して下さい" />    
       		<@s.fielderror><@s.param value="%{'communityImageForm.title'}" /></@s.fielderror> 
        </div>
        <@s.token />
        <ul class="center-list pt-12" >
            <li><button type="button" class="form-button btn-a js-cancel-modal btn-small" >キャンセル</button></li>
            <li><@s.submit cssClass="form-submit btn-b btn-small" value="追加" /><li>
        </ul>
    </form>
    
    <script type="text/javascript">
		var app = my.resources.get("app");
		var input = my.resources.get("input");
		input.Behavior.autoResize.bind();
	
        var con_selector = '.cm-large-image-con';
        $(app.image.settings.uploadCompleteTriggerSelector).on('upload.complete',
        		function(e, src, fileId, type, vendorData) {
        	app.image.clear(con_selector);
  	      	app.image.add(src.lSrc, fileId, type, vendorData, con_selector);
  	    	$('.image-upload-panel').hide();
            $(con_selector).show();
        });

        $(con_selector).on('click', '.cc-remove-image', function() {
        	app.image.clear(con_selector);
            $('.image-upload-panel').show();
            $(con_selector).hide();
        });

    </script>
</section>
