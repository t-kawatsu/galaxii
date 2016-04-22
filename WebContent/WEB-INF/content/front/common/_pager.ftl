<div class="c-pager-ctrl clearfix">
      <div class="fl">
        <span class="bold">${pager.total}</span>件中 <span class="bold">${pager.firstIndice}</span>件〜<span class="bold">${pager.lastIndice}</span>件まで表示
      </div>
      <ul class="c-pager fl">
      	<#list pager.pages as row>
		<#if row == pager.page>
		<li>${row}</li>
		<#else>
		<li><a href="${url()}?page=${row}" <#if pjaxPage>class="js-pjax"</#if> >${row}</a></li>
		</#if>
        </#list>
      </ul>
      <div class="fr">
        <span class="sub-text">並べ替え:</span>
        <select>
          <option>メンバー降順</option>
        </select>
      </div>
</div>