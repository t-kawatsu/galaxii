 <dl id="u-update-sex" class="u-detail-item">
   <dt class="fs-ss">性別</dt>
   <dd><@s.radio listKey="key" listValue="value" list="userForm.sexes" key="userForm.sex" value="userForm.sex"/></dd>
   <dd class="p-4 clearfix">
    <#if updated >
    <span class="fl icon-ok-sign u-update-partial-success-text">更新しました。</span>
    <script type="text/javascript">
      $(".u-update-partial-success-text").animate({opacity: 0}, {duration:2500} );
    </script>
    </#if>
    <a href="#" class="fr simple-btn-small u-partial-update-btn">変更する</a>
    <script type="text/javascript">
    	$("#u-update-sex").on("click", ".u-partial-update-btn", function(e) {
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
    				"${url('/user/update-sex-ajax')}",
    				data,
    				"#u-update-sex",
    				"POST");
    	});
    </script>
   </dd>
</dl> 