<title>galaxii (ギャラグジー) とは？</title>

<style type="text/css">
#contents {
  background-color:#fff;
}
h3 {
  font-weight: bold;
  font-size: 15px;
}
.a-search-form {
  position:relative;
}
.a-input-search-word {
  padding-left:34px;
  width:300px;
  margin-right:4px;
}
.a-icn-search {
  position: absolute;
  top: 5px;
  left: 5px;
}
</style>
<div class="clmn-left-main">
  <section>
    <div>
  	  <h1 class="page-title pb-12 pt-12"><@s.text name="app.site.name" />とは？</h1>
  	</div>
  	<p style="line-height:27px; font-size:14px;">
  		<@s.text name="app.site.name" />(ギャラグジー)はオススメ情報を共有するコミュニティサービスです。<br/>
  		あなたが興味のあるアーティストや本、映画、商品などのコミュニティに参加し<br/>
  		同じものに興味をもったメンバー同士でオススメ情報の共有や意見交換などをすることで<br/>
  		趣味、興味を広げていくことができます。<br/>
  		<br/>
  		あのアーティストのこの曲に興味を持ったけど、同じような曲を知っている人にオススメを教えてほしい！<br/>
  		この商品が好きならこの商品も好きだろうというのをオススメしてほしい！<br/>
  		など、あなたの興味からさらにいろいろなものにツナガリ興味や趣味を広げていきましょう！<br/>
  		<br/>
  	</p>
  </section>
  <section>
    <div class="pb-12">
  	  <h3 class="pb-12">カテゴリから探す</h3>
  	  <ul class="clearfix">
  	    <#list categories as row>
  	    <#if row != "UNDEFINED">
  	    <li class="fl p-4 clearfix">
  	      <div class="fl icn-category-c icn-category-c-${row} pl-4"></div>
  	      <div class="fl" style="line-height:36px;">
		    <a href="${url('/search/community-category/' + row.getName())}">${languageSetting("C006",row)}</a>
	      </div>
	    </li>
  	    </#if>
  	    </#list>
  	  </ul>
  	</div>
  	
  	<div class="pt-12">
  	  <h3 class="pb-12">キーワードから探す</h3>
  	  <div class="clearfix pl-4">
  	    <#if hotWords?? && 0 < hotWords?size>
  	  	<ul class="clearfix">
  	  	  <#list hotWords as row>
  	  	  <li class="fl p-4"><a href="${url('/search/community?word=' + row.name?url)}">${row.name?html}</a></li>
  	  	  </#list>
  	  	</ul>
  	  	</#if>
		<div class="pt-12">
		    <form action="${url('/search/community')}" method="GET" class="clearfix a-search-form">
		      <img class="a-icn-search" src="${assets('icn-search.png', 'images', 'common')}" alt="検索" />
		      <@s.textfield name="word" cssClass="a-input-search-word form-text fl input-shadow radius-3" placeholder=""/>
		      <@s.token />
		      <@s.submit value="検索" cssClass="fl form-submit btn-o c-inline-btn" />
		    </form>
		</div>
  	  </div>
  	</div>
  </section>
</div>

<div class="clmn-right-menu pt-12">
  <section class="pb-6">
    <div class="pb-6">
      <a href="${url('/community/create')}" class="btn-o">コミュニティを作る</a>
    </div>
  </section>
  <section class="pb-6">
    <div class="pb-6">
      <a href="${url('/user/create')}" class="btn-b">アカウントを作成する</a>
    </div>
  </section>
  <section class="pb-6">
    <div class="pb-6">
      <a href="${url('/help')}" class="btn-a">使い方を見る</a>
    </div>
  </section>
</div>