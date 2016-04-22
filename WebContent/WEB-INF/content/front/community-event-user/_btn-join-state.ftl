<div id="ce-btn-join-state">
  <#if isUserJoined >
  <a href="${url('/community-event-user/delete-ajax/' + communityEvent.id)}#ce-btn-join-state" class="btn-o ajax-get btn-small">参加をやめる</a>
  <#else>
  <a href="${url('/community-event-user/create-ajax/' + communityEvent.id)}#ce-btn-join-state" class="btn-o ajax-get btn-small">参加する</a>
  </#if>
  
  <#if updateComplete??>
    <div class="icon-ok-sign c-update-complete-text ta-c pt-12">
    <#if isUserJoined >参加しました。<#else>参加をやめました。</#if>
    </div>
    <script type="text/javascript">
        $(".c-update-complete-text").animate({opacity: 0}, {duration:2500} );
    </script>
  </#if>
</div>