<div class="c-pager clearfix">
	<div class="c-pager-detail">全${pager.total}件中 ${pager.firstIndice}件〜${pager.lastIndice}件表示</div>
	<ul class="c-pages">
		<li><a href="${url()}?page=${pager.firstPage}">First</a></li>
		<#if pager.hasPrevPage>
		<li><a href="${url()}?page=${pager.prevPage}">Prev</a></li>
		</#if>

		<#list pager.pages as row>
		<#if row == pager.page>
		<li>${row}</li>
		<#else>
		<li><a href="${url()}?page=${row}">${row}</a></li>
		</#if>
		</#list>

		<#if pager.hasNextPage>
		<li><a href="${url()}?page=${pager.nextPage}">Next</a></li>
		</#if>
		<li><a href="${url()}?page=${pager.lastPage}">Last</a></li>
	</ul>
</div>
