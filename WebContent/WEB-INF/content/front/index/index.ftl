<title>趣味・興味でつながろう！</title>

<link href="${assets('index.css', 'css')}?_=20130511" media="screen, print" rel="stylesheet" type="text/css" >
<section>
  <div class="t-banner">
	<img src="${assets('banner-top.jpg', 'images')}" width="950" height="200" alt="興味でつながりオススメしよう！オススメ共有コミュニティサービス galaxii (ギャラグジー)" />
	<a class="t-btn-regist-user btn-b" href="${url('/user/create')}">ユーザー登録</a>
	<a class="t-btn-about-site btn-o" href="${url('/about')}">galaxii (ギャラグジー)とは？</a>
  </div>
</section>
<section>
  <#if pickupCommunities?? >
  <div class="t-pickups">
    <ul class="clearfix t-pickups-wrap c-c-list">
      <#list pickupCommunities as row>
	  <li class="fl js-container-link radius-3">
		<div class="c-c-icon icn-category-c icn-category-c-${row.categoryId}"></div>
		<@my.communityImg row.id row.communityImageId "s" />
		<div class="t-pickups-meta">
		  <div class="t-pickups-meta-wrap">
			<h4 class="t-pickups-t"><a href="${url('/community/read/' + row.id?c)}">${cutStr(row.title, 17)?html}</a></h4>
			<aside class="t-pickups-u clearfix fs-ss sub-text"><div class="fl">by <a href="${url('/user/read/' + row.user.id?c)}">${cutStr(row.user.nickname, 5)?html}</a></div><div class="fr">${languageSetting("C006", row.categoryId)}(${row.communityUsersCnt?c}人)</div></aside>
		  </div>
		</div>
	  </li>
	  </#list>
    </ul>
    <!-- 
    <div class="t-pickups-pager">
      <a href="#" class="prev"><img src="${assets('btn-left.png', 'images', 'common')}" width="26" height="26" /></a>
      <a href="#" class="next"><img src="${assets('btn-right.png', 'images', 'common')}" width="26" height="26" /></a>
    </div>
     -->
  </div>
  <script type="text/javascript">
    $(document).ready(function() {
		var topSlider = new my.IHSlider(
				".t-pickups", ".t-pickups-wrap", ".t-pickups-wrap li", ".t-pickups-pager", 1, 5);
    });
  </script>
  </#if>
</section>

<div class="clearfix">
  <div class="clmn-left-main">
  <#if newCommunities?? >
  <section>
    <div class="sub-section-head">
	  <hr/>
	  <h2>新着</h2>
    </div>
    <ul class="c-h-5-list c-c-list clearfix">
      <#list newCommunities as row>
      <li class="js-container-link radius-3">
        <div class="c-c-icon icn-book-c"></div>
        <@my.communityImg row.id row.communityImageId "ss" />
        <div class="c-c-meta">
         <h3 class="c-c-t"><a href="${url('/community/read/' + row.id?c)}">${row.title?html}(${row.communityUsersCnt?c})</a></h3>
        </div>
      </li>
      </#list>
    </ul>
  </section>
  </#if>
  
  <section>
    <div class="sub-section-head">
	  <hr/>
	  <h2>カテゴリ</h2>
    </div>
    <#if categorizedCommunities?? >
	<ul class="t-categories clearfix">
	  <#list categorizedCommunities as row >
	  <li class="t-category t-category-${row.categoryId} radius-3">
		<div class="t-category-h"><h3><a href="${url('/search/community-category/' + row.categoryId.getName())}">${languageSetting("C006", row.categoryId)}</a></h3></div>
		<div class="clearfix">
		  <div class="t-category-icon-wrap">
			<a href="${url('/search/community-category/' + row.categoryId.getName())}"><img src="${assets('category-icons/' +row.categoryId.getName()+ '-66.png', 'images', 'common')}" class="t-category-icon c-om-scale" alt="${languageSetting("C006", row.categoryId)}" /></a>
			<div class="ta-c t-category-total-num sub-text">( ${row.count} )</div>
		  </div>
		  <#if row.communities?? >
		  <ul class="t-category-items">
		    <#list row.communities as com>
			<li class="icon-caret-right"><a href="${url('/community/read/' + com.id?c)}">${cutStr(com.title, 15)?html}</a>&nbsp;<span class="sub-text fs-ss">(${com.communityUsersCnt})</span></li>
			</#list>
		  </ul>
		  </#if>
		</div>
	  </li>
	  </#list>
	</ul>
	</#if>
  </section>
  </div>
  
  <div class="clmn-right-menu">
    <section>
      <div class="pb-6">
        <a href="${url('/community/create')}" class="btn-o">コミュニティを作る</a>
      </div>
    </section>
    
    <section>
      <ul class="clearfix">
        <li class="fl pr-4"><#include '../common/_twitter-tweet.ftl' /></li>
        <li class="fl"><#include '../common/_facebook-like.ftl' /></li>
      </ul>
    </section>
  
    <#include '../common/_hot-words.ftl' />
    
    <section>
      <#include '../common/_advertise-1.ftl' />
    </section>

  </div>
  
</div>
