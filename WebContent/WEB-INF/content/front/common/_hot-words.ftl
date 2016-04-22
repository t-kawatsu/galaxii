<section>
  <div class="sub-section-head">
	<hr/>
	<h2>注目ワード</h2>
  </div>
  <#if hotWords?? >
  <#assign _wordsCnt = 0 />
  <#list hotWords as row>
  	<#assign _wordsCnt = row.count + _wordsCnt />
  </#list>
  <#assign _wordCntAvg = _wordsCnt / hotWords?size />
  <ul class="hot-words">
      <#list hotWords as row>
      <#assign _hotWordR  = (row.count / _wordCntAvg) />
      <#if 1.3 < _hotWordR  >
      	 <#assign _hotWordLv = 3 />
      <#elseif 0.6 < _hotWordR >
         <#assign _hotWordLv = 2 />
      <#else>
         <#assign _hotWordLv = 1 />
      </#if>
	  <li class="hot-word-lv-${_hotWordLv}"><a href="${url('/search/community?word=' + row.name?url)}">${row.name?html}</a></li>
	  </#list>
  </ul>
  </#if>
</section>