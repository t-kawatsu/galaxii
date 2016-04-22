<form action="${url('/community-image/amazon-upload-ajax')}" method="POST">
    <ul class="cc-amazon-items clearfix">
    <#list amazonItems as row >
      <#if row.mediumImage?? >
        <li class="js-tooltip" title="${row.title?html}">
            <img src="${row.mediumImage.url}" width="${row.mediumImage.width}" height="${row.mediumImage.height}"/>
            <@s.hidden name="asin" value="${row.asin}" />
        </li>
      </#if>
    </#list>
    </ul>
</form>

<script type="text/javascript">
$('.cc-amazon-items li').on({
    'mouseover': function() {
        var on_class_name = 'on';
        $(this).addClass(on_class_name);
    },
    'mouseout': function() {
        var on_class_name = 'on';
        $(this).removeClass(on_class_name);
    },
    'click': function() {
    	var app = my.resources.get("app");
    	var con =$(this).parents('form').height(30);
    	my.utils.Loading.set(con);
    	con.html(null);
    	$.fancybox.update();
        $.ajax({
            url:con.attr('action'), 
            type:con.attr('method'),
            dataType:'html',
            data:{
                asin:$(this).find('#asin').val()
            }
        }).done(function(res, code, meta) {
        	con.remove();
        	app.image.processCreatedCallback(res);
        });
    }
});
</script>

