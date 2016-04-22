<title>イベント編集</title>

<link type="text/css" href="${assets('jquery-ui-1.10.2.custom.min.css', 'css', 'common')}" rel="stylesheet" />
<link href="${assets('jquery.timepicker.css', 'css', 'common')}" media="screen, print" rel="stylesheet" type="text/css" >
<link href="${assets('community.css', 'css')}" media="screen" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${assets('jquery.timepicker.min.js', 'js', 'common')}" ></script>

<div class="cc-info-left">
<section>
  <div class="sub-section-head">
    <hr/>
    <h3>イベント編集</h3>
  </div>
  <form action="${url('/community-event/update/' + communityEvent.id)}" method="POST" class="ce-form">
	<div class="table">
	  <h2 class="cc-title clearfix">
	    <@my.activityIcon communityContentsCategory('event') />${communityEvent.title?html}
	  </h2>
	</div>
	<div class="table ce-datetimepicker-table">
	  <div class="table-row">
		<div class="table-cell">
			<@s.textfield name="communityEventForm.startAtDate" cssClass="form-text input-shadow datepicker" placeholder="${.now?string('yyyy/MM/dd')}"/>
		</div>
		<div class="table-cell">
		    <@s.textfield name="communityEventForm.startAtTime" cssClass="form-text input-shadow timepicker" placeholder="${.now?string('H:30')}" />
		</div>
		<div class="table-cell ce-end-at-items" <#if !communityEventForm.endAt??>style="display:none"</#if> ><span style="width:100%">~</span></div>
		<div class="table-cell ce-end-at-items" <#if !communityEventForm.endAt??>style="display:none"</#if> >
			<@s.textfield name="communityEventForm.endAtDate" cssClass="form-text input-shadow datepicker" placeholder="${.now?string('yyyy/MM/dd')}"/>
		</div>
		<div class="table-cell ce-end-at-items" <#if !communityEventForm.endAt??>style="display:none"</#if> >
		    <@s.textfield name="communityEventForm.endAtTime" cssClass="form-text input-shadow timepicker" placeholder="${.now?string('H:30')}" />
		</div>
		<div class="table-cell community-event-add-end-at">
		  <button type="button" class="form-button btn-community-event-add-end-at btn-a btn-small" style="margin:0px 6px;">
		  	<span class="icon-caret-left" <#if communityEventForm.endAt??>style="display:none"</#if> >終了時間を追加</span>
		  	<span <#if !communityEventForm.endAt??>style="display:none"</#if> >×</span>
		  </button>
		</div>
	  </div>
	  <div class="table-row">
	    <@s.fielderror><@s.param value="%{'communityEventForm.startAt'}" /></@s.fielderror>
	  </div>
	</div>
	<div class="table">
	  <div class="table-row">
		<div class="table-cell <@my.errorInputClass 'communityEventForm.place'/>" style="position:relative;">
	      <@s.textarea name="communityEventForm.place" cssClass="form-text input-shadow cancel-enter auto-resize-textbox" placeholder="場所(任意)" />
	      <@s.hidden name="communityEventForm.lat" />
	      <@s.hidden name="communityEventForm.lon" />
	      <a href="#" class="btn-a btn-community-event-disp-map btn-small fs-ss" ><span class="icon-caret-down" <#if communityEventForm.lat??>style="display:none;"</#if> >地図を使う</span><span <#if !communityEventForm.lat??>style="display:none;"</#if> >× 地図を使わない</span></a>
		</div>
	  </div>
	  <div class="table-row">
	    <@s.fielderror><@s.param value="%{'communityEventForm.place'}" /></@s.fielderror>
	  </div>
	  <div class="table-row">
	    <div id="map_canvas" style="<#if !communityEventForm.lat??>display:none;</#if>"></div>
	  </div>
	</div>
	<div class="table">
	  <div class="table-row <@my.errorInputClass 'communityEventForm.description'/>">
	    <@s.textarea id="description" name="communityEventForm.description" cssClass="form-textarea input-shadow auto-resize-textbox" placeholder="詳細" rows="6"/>
	  </div>
	  <div class="table-row">
	    <@s.fielderror><@s.param value="%{'communityEventForm.description'}" /></@s.fielderror>
	  </div>
	  <div class="table-row">
	    <div class="pt-12">
	    <a href="#" class="js-emotion-palet-trigger" data-dest-selector="#description"><img src="${assets('icn-like.png','images', 'common')}" alt="絵文字" height="27" width="27"/></a>
	    </div>
	  </div>
	</div>
	<ul class="center-list pt-12">
	  <li><a href="${url('/community-event/read/' + communityEvent.id?c)}"><button type="button" class="form-button btn-a btn-small" >キャンセル</button></a></li>
	  <li><@s.submit cssClass="form-submit btn-b btn-small" value="追加" /><li>
	</ul>

	<script type="text/javascript">
	$(document).ready(function() {
		
		$.datepicker.setDefaults({
		  	dateFormat: 'yy/mm/dd',
		  	dayNames:[
		  	],
		  	dayNamesMin:[ "日", "月", "火", "水", "木", "金", "土" ],
		  	dayNamesShort:[ "日", "月", "火", "水", "木", "金", "土" ],
		  	monthNames:[ 
		  		"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月" 
		  	],
		  	monthNamesShort:[
		  		1,2,3,4,5,6,7,8,9,10,11,12
		  	],
		  	gotoCurrentType:true
		});
		$(".datepicker").datepicker();
		$('.timepicker').timepicker({ 'timeFormat': 'H:i' });

		$('.btn-community-event-add-end-at').on('click', function(e) {
			e.preventDefault();
			$(this).find('span').toggle();
			if($('.ce-end-at-items').toggle().css('display') == 'none') {
				$('.ce-end-at-items').find('input').val(null);
			}
		});

		var map = null;
		$('.btn-community-event-disp-map').on('click', function(e) {
			e.preventDefault();
			$(this).find('span').toggle();

			var canvas = $('#map_canvas');
			if(canvas.css('display') != 'none') {
				canvas.hide();
				map.clear();
				return;
			} else {
				canvas.show();
			}

			if(map == null) {	
				map = new my.Map("map_canvas", 
						"#communityEventForm_lat", 
						"#communityEventForm_lon");
				map.create();
				$('#communityEventForm_place').on('keydown', function(e) {
				    if (e.which == 13) {
				    	map.putMarkerByAddress($(this).val(), true);
				    	return false;
				    }
				});
			}
			map.putMarkerByAddress(
					$('#communityEventForm_place').val(), true);
		});
		
		<#if communityEventForm.lat??>
		(function() {
			map = new my.Map("map_canvas", 
					"#communityEventForm_lat", 
					"#communityEventForm_lon");
			map.create();
			map.putMarker(${communityEventForm.lat}, ${communityEventForm.lon});
			
			$('#communityEventForm_place').on('keydown', function(e) {
			    if (e.which == 13) {
			    	map.putMarkerByAddress($(this).val(), true);
			    	return false;
			    }
			});
		})();
		</#if>
	});
	</script>
  </form>
</section>
</div>

<div class="cc-info-right">
   <#include '../common/_sub-community-info.ftl' />
</div>

<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=ja"></script>

