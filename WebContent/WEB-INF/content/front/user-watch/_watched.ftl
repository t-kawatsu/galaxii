  <section>
    <div class="sub-section-head">
	  <hr/>
	  <h2>ウォッチされた人</h2>
    </div>
    <#if userWatchedes?? && userWatchedes.items??>
	<ul class="c-m-list clearfix">
	  <#list userWatchedes.items as row>
	  <li class="b-shadow radius-3 js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + row.fromUser.id?c)}"><a href="${url('/user/read/' + row.fromUser.id?c)}"><@my.userImg row.fromUser "ss" /></a></li>
	  </#list>
	</ul>
	<div class="clearfix sub-section-more pt-12">
	  <a class="fr fs-ss" href="${url('/user-watch/index-watched/' + user.id)}" >すべて見る</a>
	</div>
	<#else>
	<p class="c-no-results"></p>
	</#if>
  </section>