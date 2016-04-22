<#if currentUser.isProperUser()>
<dl id="u-update-password" class="u-detail-item">
  <dt class="fs-ss">パスワード<span class="bold">(非公開)</span></dt>
  <#if currentUser.password??>
  <dd>
  	<p><@s.password name="userForm.password" 
  			cssClass="form-text cc-input cancel-enter char-counter" placeholder="${maskPassword(currentUser.password)}" /></p>
  	<@s.fielderror><@s.param value="%{'userForm.password'}" /></@s.fielderror> 
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
    	$("#u-update-password").on("click", ".u-partial-update-btn", function(e) {
    		e.preventDefault();
    		var data = {};
    		$(this).parents("dl").find("input,textarea,select").each(function() {
    			data[$(this).attr("name")] = $(this).val();
    		});
    		my.utils.rewrite(
    				"${url('/user/update-password-ajax')}",
    				data,
    				"#u-update-password",
    				"POST");
    	});
    </script>
  </dd>
  </#if>
</dl> 
</#if>