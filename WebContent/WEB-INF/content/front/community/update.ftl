<title>コミュニティ編集</title>

<link href="${assets('community.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<div class="clmn-left-main">
  <h1 class="icon-plus">コミュニティ編集</h1>
    <form class="community-create-form" action="${url('/community/update/' + community.id)}" method="POST">
      <section class="cm-main-section clearfix">
        <div class="cm-info-left">
          <div class="cm-image-con">
            <#assign hasSuspendFile = communityForm.imageIds?? && 0 < communityForm.imageIds?size />
            <#if hasSuspendFile >
              <#list communityForm.imageIds as row >
                <img src="${url('/community-image/read-tmp/' + row + '/m')}" alt="" width="256" height="192" class="radius-3" />
              </#list>
            </#if>
            <div class="cc-no-image <#if hasSuspendFile > hide</#if>" >
              <a class="modal cm-btn-img-upload" href="${url('/community-image/create-tmp-ajax')}">
              <i class="icn-upload icon-upload-alt"></i><span class="sub-text">画像をアップロード</span>
              </a>
            </div>
            <div class="cc-remove-image-con"><a class="btn-a <#if !hasSuspendFile > hide</#if> cc-remove-image radius-3">× キャンセル</a></div>
            <@s.hidden name="communityForm.imageIdsCsv" cssClass="c-images-csv-input" />
            <@s.hidden name="communityForm.imageTypesCsv" cssClass="c-image-types-csv-input" />
            <@s.hidden name="communityForm.imageVendorDataCsv" cssClass="c-image-vendor-data-input" />
          </div>
          <div class="sub-section-head cm-tags-section">
			<h3 class="icon-tags">タグ</h3>
			<hr>
		  </div>
          <ul class="cm-tags v-left-list">
            <#if communityForm.communityTags?? >
            <#list communityForm.communityTags as row>
				<li><a href="#" class="cm-tag-name" onclick="return false;">${row?html}</a><a href="#" class="cm-remove-tag icon-remove-sign"></a></li>
            </#list>
            </#if>
          </ul>
          <div class="js-input-balloon-con <@my.errorInputClass 'communityForm.communityTags'/>" >
            <textarea id="communityForm_communityTagsInput" class="c-community-tags-input form-textarea cancel-enter auto-resize-textbox char-counter cc-input" placeholder="タグを登録してください" maxlength="10" style="padding:0px;" data-balloon-pos-my="top center"  data-balloon-pos-at="bottom center" ></textarea>
            <input type="button" value="追加" id="btn-add-community-tag" class="form-button btn-a " />
            <@s.hidden name="communityForm.communityTagsCsv" cssClass="c-community-tags-csv-input" />
            <@s.fielderror><@s.param value="%{'communityForm.communityTags'}" /></@s.fielderror> 
            <p class="js-input-balloon-d">
タグを1個以上8個以内で登録して下さい。<br />
1つのタグにつき登録できる最大文字数は10文字です。
            </p>
          </div>
        </div>
        <div class="cm-info-right">
          <div class="clearfix">
		    <div class="icn-category-c fl js-icn-category-c-view" style="margin-right:6px;"></div>
	        <h1 class="cm-title">${community.title?html}</h1>
	        <div class="cm-category"><@s.select listKey="key" listValue="value" list="communityForm.categories" key="communityForm.categoryId" cssClass="form-select js-category-select" /></div>
		  </div>
		  <@s.fielderror><@s.param value="%{'communityForm.title'}" /></@s.fielderror> 
          <p class="<@my.errorInputClass 'communityForm.description'/>"><@s.textarea name="communityForm.description" cssClass="form-textarea cc-input auto-resize-textbox char-counter" style="padding:0px;" placeholder="説明文を入力して下さい"/></p>
          <@s.fielderror><@s.param value="%{'communityForm.description'}" /></@s.fielderror> 
         <div style="padding-top:6px">
           <a href="#" class="js-emotion-palet-trigger" data-dest-selector="#communityForm_description"><img src="${assets('icn-like.png', 'images', 'common')}" alt="絵文字" height="27" /></a>
         </div>
        </div>
    
	    <script type="text/javascript">
		  $(document).ready(function() {
			var app = my.resources.get("app");
			app.tag.bindInputBox();
    	    app.community.Behavior.bindInputCategory();
				
			app.image.bindRemoveImage();
	    	    
    	    $(document).on('upload.complete', 
    	    		app.image.uploadCompleteTriggerSelector, 
    	    		function(e, src, fileId, type, vendorData) {
    	      app.image.clear();
    	      app.image.add(src.mSrc, fileId, type, vendorData);
    	      $.fancybox.close();
    	    });
		  });
		</script>
	  </section>
 	  <@s.token />
	<ul class="center-list">
      <li><@s.submit value="送信する" cssClass="btn-o btn-large form-submit" /></li>
	</ul>
  </form>
</div>

<div class="clmn-right-menu">
</div>