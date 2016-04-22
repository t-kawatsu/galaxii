<#if communityCategory??>
<title>${languageSetting("C006",communityCategory)}一覧</title>
<#else>
<title>${word!""?html}の検索結果</title>
</#if>

<link href="${assets('search.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<div class="clmn-left-main">
  <section>
    <h1>
      <#if communityCategory??>
      <div class="clearfix">
        <div class="fl icn-category-c icn-category-c-${communityCategory} pl-4"></div>
        <div class="fl" style="line-height:36px;">${languageSetting("C006",communityCategory)}</div>
      </div>
      <#else>
      <img src="${assets('icn-search.png', 'images', 'common')}" alt="" />
      "${word!""?html}"
      </#if>
    </h1>
    <div class="sub-section-head">
      <hr/>
      <h2>検索結果</h2>
    </div>
    <#include '../common/_pager.ftl' />
    
    <#if pager.items?? && 0 < pager.items?size>
    <ul class="c-h-5-list c-c-list clearfix">
      <#list pager.items as row>
      <li class="js-container-link radius-3">
        <div class="c-c-icon icn-book-c"></div>
        <@my.communityImg row.id row.communityImageId "ss" />
        <div class="c-c-meta">
         <h3 class="c-c-t"><a href="${url('/community/read/' + row.id?c)}">${row.title?html}(${row.communityUsersCnt?c})</a></h3>
        </div>
      </li>
      </#list>
    </ul>
    <#else>
    <div class="s-no-result">
      <#if communityCategory??>
      <p>${languageSetting("C006",communityCategory)}に合致する結果はありません。</p>
      <#else>
      <p>"${word?html}"に合致する結果はありません。</p>
      </#if>
      <div class="pt-12">
        <a href="${url('/community/create')}" class="btn-o">コミュニティを作る</a>
      </div>
    </div>
    </#if>
  </section>
</div>
<div class="clmn-right-menu">
  <section class="pb-6">
    <div class="pb-6">
      <a href="${url('/community/create')}" class="btn-o">コミュニティを作る</a>
    </div>
  </section>
  <#include '../common/_hot-words.ftl' />
  
  <section>
    <#include '../common/_advertise-1.ftl' />
  </section>
</div>