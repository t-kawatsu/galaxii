<div id="ce-calendar">
<div class="clearfix">
  <h3 class="fl cc-e-current-month-text">${eventCalendar.targetMonth?string("yyyy年MM月")}</h3>
  <ul class="fl switch-menu v-left-list">
    <li><a class="ajax-get" href="${url('/community-event/index-ajax/' + community.id + '/' + eventCalendar.prevMonth?string("yyyy-MM") )}#ce-calendar">前月</a></li><li>
    <a class="ajax-get" href="${url('/community-event/index-ajax/' + community.id + '/' + eventCalendar.currentMonth?string("yyyy-MM") )}#ce-calendar">今月</a></li><li>
    <a class="ajax-get" href="${url('/community-event/index-ajax/' + community.id + '/' + eventCalendar.nextMonth?string("yyyy-MM") )}#ce-calendar">次月</a></li>
  </ul>
  <ul class="fr switch-menu v-left-list">
    <li><a class="ajax-get" href="${url('/community-event/index-ajax/' + community.id + '/' + eventCalendar.targetMonth?string("yyyy-MM") )}#ce-calendar">リスト</a></li><li>
     <a class="ajax-get selected" href="${url('/community-event/index-ajax/' + community.id + '/' + eventCalendar.targetMonth?string("yyyy-MM") )}#ce-calendar">カレンダー</a></li>
  </ul>
</div>
<div class="cc-e-cal fs-ss">
  <ol class="t-head">
    <#list eventCalendar.weekLabels as row>
    <li>
      <div class="p-4">${row}</div>
    </li>
    </#list>
  </ol>
  <ol class="t-body">
  <#assign currentDate = eventCalendar.current />
  <#assign hasEvents = communityEvents?? && communityEvents.items?? >
  <#list eventCalendar.monthCals as row>
  <#if row_index != 0 && (row_index)%eventCalendar.weekLabels?size == 0>
  </ol>
  <ol class="t-body">
  </#if>
    <#assign d = row.time />
    <#assign dStr = d?string("yyyy-MM-dd") />
  	<#if d.time < eventCalendar.monthFirstDay.time || eventCalendar.monthLastDay.time < d.time >
    <li class="cc-e-out-day">
      <div class="p-4 cc-e-day-head clearfix">
        <span class="fl">${row.time?string("M月dd日")}</span>
      </div>
    </li>
    <#else>
    <#assign isToday = eventCalendar.current?string("yyyy-MM-dd") == dStr />
    <li class="<#if isToday> cc-e-today</#if>">
      <div class="p-4 cc-e-day-head clearfix">
        <span class="fl">${row.time?string("d")}</span>
        <#if isToday || eventCalendar.current.time  <= d.time>
        <a class="icon-plus fr hide" href="${url('/community-event/create/' + community.id + '?date=' + d?string('yyyy-MM-dd') )}">作成</a>
        </#if>
      </div>
      <#if hasEvents>
      <ul class="pl-4">
        <#list communityEvents.items as event >
          <#if event.startAt?string("yyyy-MM-dd") == dStr 
            || (event.endAt?? && event.endAt?string("yyyy-MM-dd") == dStr )
            || (event.endAt?? && event.startAt.time < d.time && d.time < event.endAt.time)>
	      <li><a href="${url('/community-event/read/' + event.id)}" class="icon-flag js-tooltip" title="${event.title?html}">${cutStr(event.title, 6)?html}</a></li>
	      </#if>
	    </#list>
	  </ul>
	  </#if>
    </li>
    </#if>
  </#list>
  </ol>
</div>
<script type="text/javascript">
    $('.cc-e-cal .t-body').on({
    	'mouseenter':function(e) {
    		$(this).find('.cc-e-day-head a').removeClass('hide');
    	},
		'mouseleave': function(e) {
			$(this).find('.cc-e-day-head a').addClass('hide');
		}
    }, 'li');
</script>

  <div class="cc-btn-create-sub-contents pt-12">
    <a href="${url('/community-event/create/' + community.id )}" class="btn-o btn-large" >イベントを作成する</a>
  </div>
</div>