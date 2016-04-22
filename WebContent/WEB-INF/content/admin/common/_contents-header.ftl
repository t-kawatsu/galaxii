<nav class="c-c-nav">
    <ul class="clearfix">
        <li><a href="${url('/' + contents + '/index')}">一覧</a></li>
		<#if create!true>
        <li><a href="${url('/' + contents + '/create')}">新規登録</a></li>
		</#if>
    </ul>
</nav>
