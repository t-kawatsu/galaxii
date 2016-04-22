<div id="cr-web-section">
<form action="${url('/community-recommend/web-search-url-ajax/' + communityId)}#cr-web-section" method="POST" class="ajax-form cr-web-form cr-web-search-form">
  <div style="min-width:500px">
    <@s.textfield id="recommend-web-url" name="communityRecommendWebForm.url" cssClass="form-text input-shadow cc-input" placeholder="サイトのURLを入力して下さい" />
	<button class="btn-o form-submit c-inline-btn">検索</button>
	<@s.fielderror><@s.param value="%{'communityRecommendWebForm.url'}" /></@s.fielderror>
  </div>
  <#if siteData??>
  <div class="cr-web-res p-4 clearfix">
  	<#if siteData.imageURLs?? && 0 < siteData.imageURLs?size>
  	<div class="fl" style="position:relative;">
  	  <div class="cr-web-images-con">
  	    <ul class="clearfix">
  	    <#list siteData.imageURLs as row>
  	      <li class="fl ta-c"><img src="${row}" /></li>
        </#list>
        </ul>
      </div>
      <ul class="center-list cr-web-image-pager">
        <li><a href="#" class="prev"><img src="${assets('btn-left.png', 'images', 'common')}" width="20" height="20" /></a></li>
        <li><a href="#" class="next"><img src="${assets('btn-right.png', 'images', 'common')}" width="20" height="20" /></a></li>
      </ul>
    </div>
  	</#if>
  	<div class="fl" style="padding-left:8px;">
  	  <h3>${siteData.title!""}</h3>
  	  <p>${siteData.description!""}</p>
  	</div>
  </div>
  </#if>
  <@s.token />
</form>

<form action="${url('/community-recommend/web-create-ajax/' + communityId)}#cr-web-section" method="POST" class="ajax-form cr-web-create-form hide">
	  <@s.token />
	  <ul class="center-list pt-12">
        <li><button type="button" class="form-button btn-a js-cancel-modal btn-small" >キャンセル</button></li>
        <li><@s.submit cssClass="form-submit btn-b btn-small" value="追加" /><li>
	  </ul>
	  <@s.hidden name="communityRecommendWebForm.url" id="recommend-web-url" />
	  <@s.hidden name="communityRecommendWebForm.imageIndex" id="recommend-web-image-index" />
</form>
    
<script type="text/javascript">
$.fancybox.update();
<#if siteData?? >
	var url = $('.cr-web-search-form')
		.find('#recommend-web-url').val();
 	$('.cr-web-create-form').removeClass('hide')
 		.find('#recommend-web-url').val(url);
 	
 	var crSlider = new my.Slider(
 			'.cr-web-images-con', 
 			'.cr-web-images-con ul', 
 			'.cr-web-images-con li', 
 			'.cr-web-image-pager');
 	crSlider.items_con.on('item.change', function() {
 		$('.cr-web-create-form')
 			.find('#recommend-web-image-index').val(crSlider.current_item-1);
 	});
<#else>
	$('.cr-web-create-form').addClass('hide');
</#if>
</script>
    
</div>
 