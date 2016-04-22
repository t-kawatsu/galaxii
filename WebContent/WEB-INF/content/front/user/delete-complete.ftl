<title>退会完了</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<div class="clmn-left-main">
<section>
    <h1 class="c-pt icon-minus">退会完了</h1>
    <p class="icon-ok-sign">退会処理が完了しました。</p>
    <p class="create_user_complete_description" style="padding-bottom:24px; padding-top:6px;">
		<@s.text name="app.site.name" />をご利用いただきありがとうございました。<br/>
    </p>
  </section>
</div>

<div class="clmn-right-menu">
  <#include '../request/create.ftl' />
</div>