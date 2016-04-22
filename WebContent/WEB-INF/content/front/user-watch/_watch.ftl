  <section>
    <div class="sub-section-head">
	  <hr/>
	  <h2>ウォッチした人</h2>
    </div>
    <#if userWatches?? && userWatches.items??>
	<ul class="c-m-list clearfix">
	  <#list userWatches.items as row>
	  <li class="b-shadow radius-3 js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + row.toUser.id?c)}"><a href="${url('/user/read/' + row.toUser.id?c)}"><@my.userImg row.toUser "ss" /></a></li>
	  </#list>
	</ul>
	<div class="clearfix sub-section-more pt-12">
	  <a class="fr fs-ss" href="${url('/user-watch/index-watch/' + user.id)}" >すべて見る</a>
	</div>
	</#if>
  </section>