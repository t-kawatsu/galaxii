<div>
  <#if isMyPage >
  <div class="u-mypage-menu">
    <a href="${url('/user/update', 'true', 'true')}" class="icon-wrench">プロフィールを編集</a>
  </div>
  </#if>
</div>

<div class="sub-section-head">
  <hr/>
  <h2>ユーザープロフィール</h2>
</div>
<div class="clearfix">
  <div class="fl u-detail-left">
    <dl class="u-detail-item">
      <dt class="fs-ss">自己紹介/メッセージ</dt>
      <dd><p>${user.message!""?html}</p></dd>
    </dl>  
  </div>
  <div class="fl">
    <#if user.isFacebookUser() && !user.fbUser.isPrivate >
    <dl class="u-detail-item">
      <dt class="fs-ss">Facebookアカウント</dt>
      <dd><a href="https://www.facebook.com/${user.fbUser.fbId}" target="_blank" class="icon-facebook">${user.fbUser.accountName?html}</a></dd>
    </dl> 
    </#if>
    <#if user.isTwitterUser() && !user.twUser.isPrivate >
    <dl class="u-detail-item">
      <dt class="fs-ss">Twitterアカウント</dt>
      <dd><a href="https://twitter.com/${user.twUser.accountName}">${user.twUser.accountName?html}</a></dd>
    </dl>
    </#if>
    <#if user.sex??>
    <dl class="u-detail-item">
      <dt class="fs-ss">性別</dt>
      <dd>${languageSetting("C008", user.sex)}</dd>
    </dl>
    </#if>
    <#if user.birthday?? >
    <dl class="u-detail-item">
      <dt class="fs-ss">誕生日</dt>
      <dd>${user.birthday?string('yyyy年MM月dd日')}</dd>
    </dl>
    </#if>
  </div>
</div>