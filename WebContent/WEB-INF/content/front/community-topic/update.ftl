<title>トピック/レビュー編集</title>

<link href="${assets('community.css', 'css')}" media="screen" rel="stylesheet" type="text/css" />
  
<div class="cc-info-left">

<section>
  <div class="sub-section-head">
    <hr/>
    <h3>トピック/レビュー編集</h3>
  </div>
  <ul class="v-left-list pb-12">
    <li><a href="${url('/community-image/create-tmp-ajax')}" class="btn-a modal btn-middle">画像アップロード</a></li>
  </ul>

  <#assign hasSuspendFile = communityTopicForm.imageIds?? && 0 < communityTopicForm.imageIds?size />
  <form action="${url('/community-topic/update/' + communityTopic.id )}" method="POST" class="ct-form">
    <div class="ct-image-con pb-12">
      <ul class="ct-images clearfix">
      <#if hasSuspendFile >
        <#list communityTopicForm.imageIds as row >
          <li class="fl pr-4" data-file-id="${row}"><img src="${url('/community-image/read-tmp/' + row + '/s')}" alt="" class="radius-3 b-shadow" /><a class="fancybox-close ct-btn-remove-image"></a></li>
        </#list>
      </#if>
      </ul>
      <@s.hidden name="communityTopicForm.imageIdsCsv" cssClass="c-images-csv-input" />
      <@s.hidden name="communityTopicForm.imageTypesCsv" cssClass="c-image-types-csv-input" />
      <@s.hidden name="communityTopicForm.imageVendorDataCsv" cssClass="c-image-vendor-data-input" />
    </div>

    <h2 class="ct-title clearfix"><@my.activityIcon communityContentsCategory('review') />${communityTopic.title?html}</h2>
    
    <div class="<@my.errorInputClass 'communityTopicForm.description'/> pt-12">
      <@s.textarea id="ci-desctiption-input" name="communityTopicForm.description" rows="1" cssClass="auto-resize-textbox form-textarea char-counter cc-input" placeholder="詳細" rows="6" />
      <@s.fielderror><@s.param value="%{'communityTopicForm.description'}" /></@s.fielderror> 
    </div>
	<div class="pt-12">
      <a href="#" class="js-emotion-palet-trigger" data-dest-selector="#ci-desctiption-input"><img src="${assets('icn-like.png', 'images', 'common')}" alt="絵文字" height="27" width="27"/></a>
	</div>
    
    <@s.token />
    <ul class="center-list pt-12" >
      <li><a href="${url('/community-topic/read/' + communityTopic.id?c)}"><button type="button" class="form-button btn-a btn-small" >キャンセル</button></a></li>
      <li><@s.submit cssClass="form-submit btn-b btn-small" value="編集" /><li>
    </ul>

    <script type="text/javascript">
    $(document).ready(function() {
		var app = my.resources.get("app");
 		$(document).on('upload.complete', 
 			 app.image.settings.uploadCompleteTriggerSelector, 
 			 function(e, src, fileId, type, vendorData) {
 			$('.ct-image-con').find('.ct-images').append(
 	    			$('<li class="fl pr-4" data-file-id="'+fileId+'"><img src="'+src.sSrc+'" class="radius-3 b-shadow"><a class="fancybox-close ct-btn-remove-image"></a></li>'));
 	    	app.image.appendImageInfo(fileId, type, vendorData, '.ct-image-con');
 			$.fancybox.close();
		});
		$(document).on('click', '.ct-btn-remove-image', function(e) {
 			e.preventDefault();
 			var fileId = $(this).parents('li').remove().attr("data-file-id");
 			app.image.removeImageInfo(fileId, '.ct-image-con');
 		});
    });
    </script>
  </form>
</section>
</div>

<div class="cc-info-right">
   <#include '../common/_sub-community-info.ftl' />
</div>