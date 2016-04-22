<dl id="u-update-message" class="u-detail-item">
  <dt class="fs-ss">自己紹介/メッセージ</dt>
  <dd>
  	<p><@s.textarea name="userForm.message" 
  			cssClass="form-textarea cc-input auto-resize-textbox char-counter"
  			placeholder="自己紹介/メッセージを入力する"/></p>
  	<@s.fielderror><@s.param value="%{'userForm.message'}" /></@s.fielderror> 
  </dd>
  <dd class="p-4 clearfix">
    <#if updated >
    <span class="fl icon-ok-sign u-update-partial-success-text">更新しました。</span>
    <script type="text/javascript">
      $(".u-update-partial-success-text").animate({opacity: 0}, {duration:2500} );
    </script>
    </#if>
    <a href="#" class="fr simple-btn-small u-partial-update-btn">変更する</a>
    <script type="text/javascript">
    	$("#u-update-message").on("click", ".u-partial-update-btn", function(e) {
    		e.preventDefault();
    		var data = {};
    		$(this).parents("dl").find("input,textarea,select").each(function() {
    			data[$(this).attr("name")] = $(this).val();
    		});
    		my.utils.rewrite(
    				"${url('/user/update-message-ajax')}",
    				data,
    				"#u-update-message",
    				"POST");
    	});
    </script>
  </dd>
</dl> 