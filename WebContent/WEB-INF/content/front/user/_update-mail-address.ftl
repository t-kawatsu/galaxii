<#if currentUser.mailAddress??>
<dl id="u-update-mail-address" class="u-detail-item">
  <dt class="fs-ss">メールアドレス<span class="bold">(非公開)</span></dt>
  <dd>
  	<p><@s.textfield name="userForm.mailAddress" 
  			cssClass="form-text cc-input cancel-enter char-counter" /></p>
  	<@s.fielderror><@s.param value="%{'userForm.mailAddress'}" /></@s.fielderror> 
  </dd>
  <dd class="p-4 clearfix">
    <#if updated >
    <div id="u-mail-update-send-mail-complete-message" class="hide" style="line-height:27px;">
		<span class="bold">${userForm.mailAddress?html}宛に確認メールをお送りしました。</span><br/>
		メールの内容にそいメールアドレスの変更を完了させて下さい。
		<ul class="center-list pt-12" >
            <li><button type="button" class="form-button btn-a js-cancel-modal btn-small" >閉じる</button></li>
        </ul>
    </div>
    <script type="text/javascript">
    	$.fancybox.open($('#u-mail-update-send-mail-complete-message').clone().removeClass('hide'));
    </script>
    </#if>
    <a href="#" class="fr simple-btn-small u-partial-update-btn">変更する</a>
    <script type="text/javascript">
    	$("#u-update-mail-address").on("click", ".u-partial-update-btn", function(e) {
    		e.preventDefault();
    		var data = {};
    		$(this).parents("dl").find("input,textarea,select").each(function() {
    			data[$(this).attr("name")] = $(this).val();
    		});
    		my.utils.rewrite(
    				"${url('/user/update-mail-address-ajax')}",
    				data,
    				"#u-update-mail-address",
    				"POST");
    	});
    </script>
  </dd>
</dl> 
</#if>