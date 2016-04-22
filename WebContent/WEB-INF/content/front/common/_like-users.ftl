<#list likedUsers.items as row>
  <li class="clearfix">
	<div class="c-like-user-img"><a href="${url('/user/read/' + row.user.id)}"><@my.userImg row.user "s" /></a></div>
	<div class="c-like-user-detail">
		<div>${row.user.nickname?html}</div>
		<div><span class="created-at icon-time fs-ss sub-text">${fTime(row.createdAt)}</span><a href="${url('/user/read/' + row.user.id)}" class="fs-ss icon-caret-right">詳しく見る</a></div>
	</div>
  </li>
</#list>