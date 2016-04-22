<section id="youtube-create">
    <div class="sub-section-head">
        <hr/>
        <h3>Youtubeの動画を設定</h3>
    </div>
    <form action="${url('/community-image/youtube-search-url-ajax#youtube-create')}" method="post" class="youtube-search-url-form ajax-form">
        <dl>
            <dt >動画のURLから設定する</dt>
            <dd>
              <@s.textfield name="youtubeSearchUrlForm.url" cssClass="form-text input-shadow" />
              <@s.token />
              <@s.submit value="送信する" cssClass="btn-a form-submit c-inline-btn" />
            </dd>
            <dd>
                <@s.fielderror><@s.param value="%{'youtubeSearchUrlForm.url'}" /></@s.fielderror>
            </dd>
        </dl>
    </form>
    
    <form action="${url('/community-image/youtube-search-word-ajax#youtube-create')}" method="post" class="youtube-search-word-form ajax-form">
        <dl>
            <dt>動画を検索する</dt>
            <dd>
              <@s.textfield name="youtubeSearchWordForm.word" cssClass="form-text input-shadow" />
              <@s.token />
              <@s.submit value="送信する" cssClass="btn-a form-submit c-inline-btn" />
            </dd>
            <dd>
              <@s.fielderror><@s.param value="%{'youtubeSearchWordForm.word'}" /></@s.fielderror>
            </dd>
        </dl>
    </form>

    <#if youtubeItems?? >
    	<#include '_youtube-search-entries.ftl' />
    </#if>

<script type="text/javascript">
$.fancybox.update();

$('.youtube-video-entries').on({
    'mouseenter': function(e) {
        $(this).addClass('on');
        $(this).find('.youtube-video-entry-menu').animate(
                    {height: "show"}, "fast");
    },
    'mouseleave':function(e) {
        $(this).removeClass('on');
        $(this).find('.youtube-video-entry-menu').animate(
                    {height: "hide"}, "fast");
    },
    'click': function(e) {
    	var app = my.resources.get("app");
    	app.image.settings.uploadCompleteTriggerSelector = '#youtube-create';
    	var con = $(this).parents('ul').height(30);
    	my.utils.Loading.set(con);
    	con.html(null);
    	$.fancybox.update();
        $.ajax({
            url:"${url('/community-image/youtube-upload-ajax')}", 
            type:'POST',
            dataType:'html',
            data:{
            	entryId:$(this).find('.youtube-video-id').val()
            }
        }).done(function(res, code, meta) {
        	con.remove();
        	app.image.processCreatedCallback(res);
        });
        return false;
    }
}, 'li');

$('.youtube-video-entries').on('click', '.btn-stop-video', function(e){
    e.preventDefault();
    e.stopPropagation();

    var con = $(this).parents('li');
    con.find('.youtube-video-entry-menu').find('a').toggle();
    con.find('iframe').remove();
    con.find('.youtube-video-entry-img').show();
    con.find('.youtube-video-entry-detail').show();
    con.find('.btn-play-video').show();
    con.find('.btn-stop-video').hide();
    
});

$('.youtube-video-entries').on('click', '.btn-play-video', function(e) {
    e.preventDefault();
    e.stopPropagation();

    $('.youtube-video-entries iframe').remove();
    $('.youtube-video-entries .youtube-video-entry-img').show();
    $('.youtube-video-entries .youtube-video-entry-detail').show();

    var con = $(this).parents('li');
    con.find('.youtube-video-entry-img').hide();
    con.find('.youtube-video-entry-detail').hide();
    con.find('.btn-play-video').hide();
    con.find('.btn-stop-video').show();
    var video_id = con.find('#youtube_video_id').val();
    con.append(
        '<iframe width="172" height="130" src="http://www.youtube.com/embed/'+video_id+'?autoplay=1&showinfo=0" frameborder="0" allowfullscreen></iframe>');

});
</script>
</section>