<title>ユーザー登録</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" />

<section class="clearfix">
  <div>
    <h1 class="icon-plus">仮登録完了</h1>
    <p class="c-ok-label icon-ok-sign">仮登録が完了しました。</p>
    <p class="u-create-tmp-user-complete-description">
<span class="bold">${tmpUser.mailAddress}</span>宛に本登録に必要なURLを記載したメールを送信しました。<br />
メールの本文に記載されている本登録のためのURLをクリックするとユーザー登録が完了します。<br />
    </p>
    <p class="error-description">
仮登録後24時間を過ぎると本登録が出来なくなるのでご注意ください。<br/>
本登録をせずに24時間を過ぎた場合は、再度仮登録からやり直してください。
    </p>
  </div>
</section>
