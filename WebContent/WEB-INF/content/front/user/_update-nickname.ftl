<dl id="u-update-nickname" class="u-detail-item">
  <dt class="fs-ss">ニックネーム</dt>
  <dd>
  	<p><@s.textfield name="userForm.nickname" 
  			cssClass="form-text cc-input cancel-enter char-counter" /></p>
  	<@s.fielderror><@s.param value="%{'userForm.nickname'}" /></@s.fielderror> 
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
    	$("#u-update-nickname").on("click", ".u-partial-update-btn", function(e) {
    		e.preventDefault();
    		var data = {};
    		$(this).parents("dl").find("input,textarea,select").each(function() {
    			data[$(this).attr("name")] = $(this).val();
    		});
    		my.utils.rewrite(
    				"${url('/user/update-nickname-ajax')}",
    				data,
    				"#u-update-nickname",
    				"POST");
    	});
    </script>
  </dd>
</dl> 