<section id="cr-create-section" >
  <div class="sub-section-head">
	<hr/>
	<h3>おすすめ追加</h3>
  </div>
  <ul class="switch-menu v-left-list">
	<li class="selected"><a href="#" class="btn-cr-web">Webサイト</a></li><li><a class="btn-cr-com ajax-get" href="${url('/community-recommend/search-user-community-ajax/' + communityId)}#cr-com-search-section">コミュニティ</a></li>
  </ul>
  <div class="switch-item">
	<#include '_web-search-url.ftl' />
  </div>
  <div class="switch-item">
    <#include '_search-user-community.ftl' />
  </div>
  
  <script type="text/javascript">
	my.resources.get("app").Behavior.switchMenu.bind();
  </script>
</section>
