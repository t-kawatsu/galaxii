<section id="ci-update-con">
	<form class="ajax-form" action="${url('/community-image/update-ajax/' + communityImage.id?c + '#ci-update-con')}" method="POST">
        <div class="cm-large-image-con">
			<@my.communityImg communityImage.communityId communityImage.id "l" />
        </div>

        <div class="<@my.errorInputClass 'communityImageForm.title'/>">
        	<@s.textfield id="ci-title-input" name="communityImageForm.title" cssClass="form-text cancel-enter char-counter cc-input" placeholder="タイトルを入力して下さい" />    
       		<@s.fielderror><@s.param value="%{'communityImageForm.title'}" /></@s.fielderror> 
        </div>
        <@s.token />
        <ul class="center-list pt-12" >
            <li><button type="button" class="form-button btn-a js-cancel-modal btn-small" >キャンセル</button></li>
            <li><@s.submit cssClass="form-submit btn-b btn-small" value="編集" /><li>
        </ul>
    </form>
    
    <script type="text/javascript">
    	$('.auto-resize-textbox').autosize({ });
    </script>
</section>
