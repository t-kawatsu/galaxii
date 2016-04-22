<title>${community.title}</title>

<link href="${assets('community.css', 'css')}?_=20130511" media="screen" rel="stylesheet" type="text/css" />

<div class="clmn-left-main">
  <section class="cm-main-section clearfix">
    <div class="cm-info-left">
      <div class="cm-image-con cm-image-frame radius-3 b-shadow">
        <#if community.communityImage??>
        <a href="${communityImageSrc(community.id?c, community.communityImageId?c, "l")}" class="image-modal">
        	<@my.communityImg community.id community.communityImageId "m" />
        </a>
        <#else>
        	<@my.communityImg community.id community.communityImageId "m" />
        </#if>
        <#if community.communityImage?? && community.communityImage.type == 'YOUTUBE'>
        <a href="http://www.youtube.com/watch?v=${community.communityImage.vendorData}" target="_blank" class="cm-yt-link-lbl js-tooltip" title="Youtubeで見る"><img src="${assets('lbl-yt-logo-95x40.png', 'images', 'common')}" width="72" height="30" /></a>
        <#elseif community.communityImage?? && community.communityImage.type == 'AMAZON'>
        <a href="http://www.amazon.co.jp/o/ASIN/${community.communityImage.vendorData}" target="_blank" class="cm-az-link-lbl js-tooltip" title="Amazonで商品を見る"><img src="${assets('lbl-az-logo.jpg', 'images', 'common')}" width="102" height="30" /></a>
        </#if>
      </div>
      <div class="sub-section-head cm-tags-section">
        <h3 class="icon-tags">タグ</h3>
        <hr />
      </div>
      <ul class="cm-tags v-left-list">
      <#if community.communityTags?? && 0 < community.communityTags?size>
        <#list community.communityTags as row>
		  <li><a href="${url('/search/community?word=' + row.id.name?url)}" class="cm-tag-name">${row.id.name?html}</a></li>
        </#list>
      </#if>
      </ul>
    </div>
    <div class="cm-info-right pos-r">
      <#if community.status == 'LIVE'>
      <div class="cc-sub-menu-con js-toggle-menu-con">
        <ul class="cc-sub-menu js-toggle-menu b-shadow">
      	  <#if isLogined && community.user.id == currentUser.id>
      	  <li class="icon-caret-right"><a href="${url('/community/update/' + community.id?c )}">編集する</a></li>
      	  <li class="icon-caret-right"><a class="js-confirm-contents" data-confirm-title="削除します。よろしいですか?" href="${url('/community/delete/' + community.id?c )}">削除する</a></li>
      	  <#else>
      	  <li class="icon-caret-right"><a href="${url('/user-violation/create-ajax/community/' + community.id?c)}" class="js-qt-modal">違反申告をする</a></li>
      	  </#if>
        </ul>
        <a class="cc-sub-menu-switch js-btn-toggle-menu" href="#"><img src="${assets('icons/circle-arrow-down.png', 'images', 'common')}" alt="" /></a>
      </div>
      </#if>
    
      <div class="clearfix">
        <div class="icn-category-c icn-category-c-${community.categoryId} fl" style="margin-right:6px;"></div>
        <h1 class="cm-title">${community.title?html}</h1>
        <div class="cm-category">${languageSetting("C006", community.categoryId)}</div>
      </div>
      <#if community.description?length < 160 >
      <p>${emotion(community.description?html?replace("\n", "<br/>"))}</p>
      <#else>
      <p><span class="js-partial-text">${emotion(cutStr(community.description, 160)?html)} <a href="#" class="js-open-full-text">...続きを読む</a></span><span class="js-full-text">${emotion(community.description?html?replace("\n", "<br/>"))}</span></p>
      </#if>
    </div>
  </section>

  <div class="clearfix">
    <nav class="fl pos-r" style="width: 504px;">
      <div class="sub-text fs-ss pb-12">オススメコンテンツ</div>
      <#assign menu = {
		  'home':'ホーム', 
		  'comment':'コメント',
		  'recommend':'オススメリンク', 
		  'movie': 'オススメ動画',
		  'image': 'オススメ画像',
		  'review':'トピック/レビュー',
		  'event': 'イベント'} />
	  <#assign currentContents = contents?lower_case />
      <ul class="cm-sub-contents-menu clearfix" data-before-contents="${currentContents}">
	    <#list menu?keys as row>
        <li>
          <a title="${menu[row]}" data-contents="${row}" class="link-community-${row} js-tooltip js-pjax ${row}" href="${url('/community/read/' + community.id + '/' + row)}"><img src="${assets('cc-category-icons/' +row+ '-36.png', 'images', 'common')}?_=20130428" width="36" height="36" class="t-activities-image"/></a>
        </li>
        </#list>
      </ul>
      <div class="cm-sub-contents-on b-shadow cm-on-${currentContents}">${menu[currentContents]}</div>
    </nav>
    <ul class="cm-sns-shares clearfix fr">
      <li><#include '../common/_twitter-tweet.ftl' /></li>
      <li><#include '../common/_facebook-like.ftl' /></li>
    </ul>
  </div>
  
  <section class="cm-sub-contents js-pjax-con">
    <#include '_cm-sub-contents.ftl' />
  </section>
</div>

<div class="clmn-right-menu">
  <section class="pb-6">
    <#include '../community-user/_btn-join-state.ftl' />
  </section>

  <#include '../common/_recommend-news.ftl' />
  
  <section>
    <#include '../common/_advertise-1.ftl' />
  </section>
  
  <#include '../community-user/_users.ftl' />
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$(".cm-sub-contents-menu").on('click', 'a', function() {
			var con = $(".cm-sub-contents-menu");
			var b = con.attr("data-before-contents");
			var c = $(this).attr("data-contents");
			var t = $(this).attr("title");
			
			con.find("a").removeClass("on");
			$(this).addClass("on");

			$(".cm-sub-contents-on")
				.removeClass("cm-on-"+b)
				.addClass("cm-on-"+c)
				.html(t);
			con.attr("data-before-contents", c);
		});
	});
</script>
