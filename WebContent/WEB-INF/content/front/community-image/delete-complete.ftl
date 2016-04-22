<script type="text/javascript">
    $.fancybox.close();
    $(window).qtip('destroy');
    <#if communityImage.type == 'YOUTUBE'>
    location.href = $('.link-community-movie').attr('href');
    <#else>
    location.href = $('.link-community-image').attr('href'); 
    </#if>
</script>