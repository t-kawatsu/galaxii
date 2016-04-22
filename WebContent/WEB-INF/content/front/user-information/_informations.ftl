<#if userInformations?? && userInformations.items?? >
<ul class="u-informations">
<#list userInformations.items as row>
  <li>
	<a href="${url('/user/read/' + row.fromUser.id)}" class="js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + row.fromUser.id?c)}">${cutStr(row.fromUser.nickname, 10)?html}さん</a>が
	<#switch row.userInformationContentsCategoryId>
	<#case 'COMMUNITY_JOIN'>
	  コミュニティ'${cutStr(row.name,10)?html}'に参加しました。
	  <#break/>
	<#case 'COMMUNITY_COMMENT'>
	  コミュニティ'${cutStr(row.name,10)?html}'にコメントしました。
	  <#break/>
	<#case 'COMMUNITY_COMMENT_LIKE'>
	<#case 'COMMUNITY_IMAGE_COMMENT_LIKE'>
	<#case 'COMMUNITY_RECOMMEND_COMMENT_LIKE'>
	<#case 'COMMUNITY_TOPIC_COMMENT_LIKE'>
	<#case 'COMMUNITY_EVENT_COMMENT_LIKE'>
	  コメント'${cutStr(row.name,10)?html}'にいいね！と言っています。
	  <#break/>
	<#case 'COMMUNITY_IMAGE'>
	  コミュニティ'${cutStr(row.name,10)?html}'に画像をオススメしました。
	  <#break/>
	<#case 'COMMUNITY_IMAGE_LIKE'>
	  画像'${cutStr(row.name,10)?html}'にいいね！と言っています。
	  <#break/>
	<#case 'COMMUNITY_IMAGE_COMMENT'>
	  画像'${cutStr(row.name,10)?html}'にコメントしました。
	  <#break/>
	<#case 'COMMUNITY_RECOMMEND'>
	  コミュニティ'${cutStr(row.name,10)?html}'にオススメリンクを登録しました。
	  <#break/>
	<#case 'COMMUNITY_RECOMMEND_LIKE'>
	  オススメリンク'${cutStr(row.name,10)?html}'にいいね！と言っています。
	  <#break/>
	<#case 'COMMUNITY_RECOMMEND_COMMENT'>
	  オススメリンク'${cutStr(row.name,10)?html}'にコメントしました。
	  <#break/>
	<#case 'COMMUNITY_TOPIC'>
	  コミュニティ'${cutStr(row.name,10)?html}'にトピック/レビューを登録しました。
	  <#break/>
	<#case 'COMMUNITY_TOPIC_LIKE'>
	  トピック/レビュー'${cutStr(row.name,10)?html}'にいいね！と言っています。
	  <#break/>
	<#case 'COMMUNITY_TOPIC_COMMENT'>
	  トピック/レビュー'${cutStr(row.name,10)?html}'にコメントしました。
	  <#break/>
	<#case 'COMMUNITY_EVENT'>
	  コミュニティ'${cutStr(row.name,10)?html}'にイベントを登録しました。
	  <#break/>
	<#case 'COMMUNITY_EVENT_LIKE'>
	  イベント'${cutStr(row.name,10)?html}'にいいね！と言っています。
	  <#break/>
	<#case 'COMMUNITY_EVENT_COMMENT'>
	  イベント'${cutStr(row.name,10)?html}'にコメントしました。
	  <#break/>
	<#case 'COMMUNITY_EVENT_USER'>
	  イベント'${cutStr(row.name,10)?html}'に参加しました。
	  <#break/>
	<#case 'WATCH'>
	  あなたをウォッチしました。
	  <#break/>
	<#case 'USER_MESSAGE'>
	  あなたにメッセージを送りました。
	  <#break/>
	</#switch>
  </li>
</#list>
</ul>
</#if>