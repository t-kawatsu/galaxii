<div id="cc-btn-watch-state">
  <#if isUserWatched >
    <a href="${url('/user-watch/delete-ajax/' + user.id)}#cc-btn-watch-state" class="btn-o ajax-get">ウォッチをやめる</a>
  <#else>
    <a href="${url('/user-watch/create-ajax/' + user.id)}#cc-btn-watch-state" class="btn-o ajax-get">ウォッチする</a>
  </#if>
  
  <#if updateComplete??>
    <div class="icon-ok-sign c-update-complete-text ta-c pt-12">
    <#if isUserWatched >ウォッチしました。<#else>ウォッチをやめました。</#if>
    </div>
    <script type="text/javascript">
        $(".c-update-complete-text").animate({opacity: 0}, {duration:2500} );
    </script>
  </#if>
</div>