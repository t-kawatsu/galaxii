<dl id="u-update-mail-notice-frequency-code" class="u-detail-item">
   <dt class="fs-ss">メール送信頻度</dt>
   <dd><@s.radio listKey="key" listValue="value" list="userForm.mailNoticeFrequencyCodes" key="userForm.mailNoticeFrequencyCode" value="userForm.mailNoticeFrequencyCode"/></dd>
   <dd class="p-4 clearfix">
    <#if updated >
    <span class="fl icon-ok-sign u-update-partial-success-text">更新しました。</span>
    <script type="text/javascript">
      $(".u-update-partial-success-text").animate({opacity: 0}, {duration:2500} );
    </script>
    </#if>
    <a href="#" class="fr simple-btn-small u-partial-update-btn">変更する</a>
    <script type="text/javascript">
    	$("#u-update-mail-notice-frequency-code")
    	.on("click", ".u-partial-update-btn", function(e) {
    		e.preventDefault();
    		var data = {};
    		$(this).parents("dl").find("input,textarea,select").each(function() {
    			if($(this).attr("type") == "radio") {
    				data[$(this).attr("name")] = $("input:radio[name='"+$(this).attr("name")+"']:checked").val();
    			} else {
    				data[$(this).attr("name")] = $(this).val();
    			}
    		});
    		my.utils.rewrite(
    				"${url('/user/update-mail-notice-frequency-ajax')}",
    				data,
    				"#u-update-mail-notice-frequency-code",
    				"POST");
    	});
    </script>
   </dd>
</dl> 