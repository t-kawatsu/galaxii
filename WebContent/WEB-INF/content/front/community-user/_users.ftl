  <section>
    <div class="sub-section-head">
      <hr/>
      <h2>メンバー</h2>
    </div>
    <#if communityUsers?? && communityUsers.items?? >
    <ul class="c-m-list clearfix">
      <#list communityUsers.items as row>
      <li class="b-shadow radius-3 js-tooltip-onmouse-ajax" data-onmouse-href="${url('/user/read-ajax/' + row.user.id?c)}"><a href="${url('/user/read/' + row.user.id)}"><@my.userImg row.user "ss" /></a></li>
      </#list>
    </ul>
    <div class="clearfix sub-section-more pt-12">
      <a class="fr fs-ss" href="${url('/community-user/index/' + community.id)}" >すべて見る</a>
    </div>
    </#if>
  </section>