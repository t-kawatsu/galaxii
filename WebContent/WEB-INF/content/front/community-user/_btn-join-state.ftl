<div id="cc-btn-join-state">
  <#if isMember >
  <a href="${url('/community-user/delete-ajax/' + community.id)}#cc-btn-join-state" class="btn-o ajax-get ">参加をやめる</a>
  <#else>
  <a href="${url('/community-user/create-ajax/' + community.id)}#cc-btn-join-state" class="btn-o ajax-get ">参加する</a>
  </#if>

  <#if updateComplete??>
    <div class="icon-ok-sign c-update-complete-text ta-c pt-12">
    <#if isMember >参加しました。<#else>参加をやめました。</#if>
    </div>
    <script type="text/javascript">
        $(".c-update-complete-text").animate({opacity: 0}, {duration:2500} );
    </script>
  </#if>
</div>