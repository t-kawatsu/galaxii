<dl id="u-update-birthday" class="u-detail-item">
  <dt class="fs-ss">誕生日</dt>
  <dd>
    <@my.birthdayInput "userForm.birthday" userForm.birthday />
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
    	$("#u-update-birthday").on("click", ".u-partial-update-btn", function(e) {
    		e.preventDefault();
    		var data = {};
    		$(this).parents("dl").find("input,textarea,select").each(function() {
    			data[$(this).attr("name")] = $(this).val();
    		});
    		my.utils.rewrite(
    				"${url('/user/update-birthday-ajax')}",
    				data,
    				"#u-update-birthday",
    				"POST");
    	});
    </script>
  </dd>
</dl>