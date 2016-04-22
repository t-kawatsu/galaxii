<#if communityTopics?? && communityTopics.items??>
<ul class="c-c-2-list ct-list clearfix">
  <#list communityTopics.items as row>
  <li class="clearfix js-container-link">
    <div class="ct-list-img">
      <#if row.status == 'LIVE'>
      <a href="${url('/community-topic/read/' + row.id?c)}"><@my.communityTopicImg row.id row.communityTopicImageId "ss" /></a>
      <div class="ci-row-detail">
        <span class="icon-thumbs-up">${row.likeCnt}</span>
        <span class="pl-4 icon-comment-alt">${row.commentCnt}</span>
      </div>
      <#elseif row.status == 'DELETED'>
      <@my.communityTopicImg communityTopicId=row.id  size="ss" />
      </#if>
    </div>
    <div class="ct-list-text">
      <#if row.status == 'LIVE'>
      <h4><a href="${url('/community-topic/read/' + row.id?c)}">${cutStr(row.title, 10)?html}</a></h4>
      <p class="fs-ss">${emotion(cutStr(row.description, 30)?html)}</p>
      <#elseif row.status == 'DELETED'>
      <div class="pb-12">削除されました。</div> 
      </#if>
      <div class="fs-ss">
          by <a href="${url('/user/read/' + row.user.id?c)}" class="js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + row.user.id?c)}">${cutStr(row.user.nickname, 7)?html}</a>
          <span class="created-at icon-time sub-text">${fTime(row.createdAt)}</span>
      </div>
    </div>
  </li>
  </#list>
</ul>
</#if>

<div class="cc-btn-create-sub-contents pt-12">
  <a href="${url('/community-topic/create?communityId=' + community.id )}" class="btn-o btn-large">トピック/レビューを追加する</a>
</div>