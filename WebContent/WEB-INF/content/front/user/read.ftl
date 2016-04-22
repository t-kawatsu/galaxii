<title>${user.nickname}</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<div class="clmn-left-main">
  <h1 class="icon-home">マイページ</h1>
  <section>
    <div class="u-img-con">
	  <#include '../user-image/_u-bg-img.ftl' />
	  <#include '../user-image/_u-img.ftl' />
	  <h2 class="u-nickname">${user.nickname!""?html}</h2>
	</div>
	
    <#if isMyPage >
    <script type="text/javascript">
	  $('.u-img-con').on({
		'mouseenter' : function(e) {
			$(this).find('.u-img-update-menu')
				.css('opacity', 0)
				.removeClass('hide')
				.animate({opacity : 1}, "fast");
		},
		'mouseleave' : function(e) {
			$(this).find('.u-img-update-menu')
				.css('opacity', 1)
				.animate({opacity : 0}, "fast");
		}
	  }, '.u-img,.u-bgimg');
    </script>
    </#if>
	
	<nav class="u-menu">
	  <ul class="switch-menu v-left-list">
	    <#assign menu = {
	    	'watch':'ウォッチ',
	    	'activity':'アクティビティ',
	    	'community':'コミュニティ',
	    	'detail':'プロフィール'
	    }/>
	    <#list menu?keys as row>
		<li <#if contents.getName() == row>class="selected"</#if>><a href="${url('/user/read/' + user.id?c + '/'+row)}" class="js-pjax">${menu[row]}</a>
		</#list>
	  </ul>
	  <script type="text/javascript">
	  $(document).ready(function() {
		  var sMenuContext = my.resources.get("app").Behavior.switchMenu;
		  sMenuContext.unbind();
		  sMenuContext.bindVis();
	  });
	  </script>
	</nav>
	<div class="u-sub-contents js-pjax-con">
		<#include '_u-sub-contents.ftl' />
	</div>
  </section>
</div>
<div class="clmn-right-menu">
  <#if !isMyPage >
  <section>
	<div class="pb-6">
	  <#include '../user-watch/_btn-watch-state.ftl' />
	</div>
  </section>
  <#else>
  </#if>
  
  <section>
    <#include '../common/_advertise-1.ftl' />
  </section>
  
  <#include '../user-watch/_watch.ftl' />
  <#include '../user-watch/_watched.ftl' />
</div>
