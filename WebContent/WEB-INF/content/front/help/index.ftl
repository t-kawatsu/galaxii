<title>ヘルプ</title>

<link href="${assets('help.css', 'css')}?_=20130511" media="screen, print" rel="stylesheet" type="text/css" >

<section class="clearfix">
  <div class="clmn-left-menu">
    <nav class="b-shadow h-nav">
      <div class="h-nav-title">使いかた</div>
      <ul class="table-of-contents">
        <#assign useAppendix = [
        	'コミュニティを作成する',
        	'アカウントを作成する',
        	'ログインする',
        	'ユーザー情報を変更する',
        	'コミュニティに参加する',
        	'コミュニティにオススメする',
        	'他のユーザーをウォッチする',
        	'その他お問い合わせ'
        ] />
        <#list useAppendix as row>
	    <li><a class="smooth-site-link" href="#use-section-${row_index+1}">${row}</a></li>
	    </#list>
      </ul>
      <div class="h-nav-title">よくある質問</div>
      <ul class="table-of-contents">
        <#list languageHelps as row>
	    <li><a class="smooth-site-link" href="#section-${row_index}">${row.title}</a></li>
        </#list>
        <li><a class="smooth-site-link" href="#section-99">その他ご質問</a></li>
      </ul>
    </nav>
  </div>

    <div class="clmn-right-main">
      <section class="pb-12">
        <h1 class="icon-question">使いかた</h1>
        <div id="use-section-1" data-role="collapsible">
          <h3>コミュニティを作成する</h3>
          <p>
            コミュニティを作成するには<a class="icon-link" href="${url('/community/create')}">コミュニティ作成画面</a>からコミュニティ情報を入力し、登録して下さい。<br/>
            <span class="sub-text">※作成の際には<a class="icon-resize-vertical" href="#use-section-2">ユーザーアカウント作成</a>の上、<a class="icon-resize-vertical" href="#use-section-3">ログイン</a>をしている必要があります。</span><br/>
            作成時にそのコミュニティにひもづいたワードを<a class="icon-resize-vertical" href="">タグ登録する</a>ことで、他のユーザーが作成したコミュニティに気づきやすくなります。<br/>
          </p>
        </div>
        <div id="use-section-2" data-role="collapsible">
          <h3>アカウントを作成する</h3>
          <p>
            アカウントを作成するには<a class="icon-link" href="${url('/user/create')}">新規登録画面</a>からメールアドレスやログイン時のパスワードなど、簡単なユーザー情報を登録し画面にそってアカウント作成を完了させて下さい。<br/>
          	※入力して頂いたメールアドレス宛にメールアドレス保有者確認のメールを本サイトからお送り致します。メールの内容にそって、ユーザー登録を完了させて下さい。(<a href="#section-1">サイトからのメールを受信出来ない場合はこちら</a>)<br/>
            <br/>
            また、<a href="http://facebook.com/" class="icon-external-link" target="_blank">Facebook</a>か<a href="http://twitter.com/" class="icon-external-link" target="_blank">Twitter</a>アカウントをお持ちであれば
            そのアカウントを使って簡単にアカウント登録をする事が出来ます。これらの外部アカウントを使用しアカウント登録される際には、メールアドレスやアカウント名などの簡単な公開情報を求めますがパスワードやその他重要な情報は本サイトに保持いたしません。<br/>
            <br/>
            ※入力された情報はアカウント作成後、<a href="${url('/user/update')}" class="icon-link">ユーザー情報編集画面</a>から出来ます。<br/>
          </p>
        </div>
        <div id="use-section-3" data-role="collapsible">
          <h3>ログインする</h3>
          <p>
            ログインは<a href="${url('/user/login')}" class="icon-link">ログイン画面</a>から行うことが出来ます。<br/>
            <span class="bold red">FacebookやTwitterの外部アカウントを使用しアカウントを作成した際は、その外部アカウントでログインをして下さい。</span><br/>
            メールアドレスなどを入力し本サイトのアカウントを作成した際は、登録されたメールアドレスとパスワードを入力しログインをして下さい。
          </p>
        </div>
        <div id="use-section-4" data-role="collapsible">
          <h3>ユーザー情報を変更する</h3>
          <p>
            ユーザー情報の変更は、ログイン後<a href="${url('/user/update')}" class="icon-link">ユーザー情報編集画面</a>から出来ます。
          </p>
        </div>
        <div id="use-section-5" data-role="collapsible">
          <h3>コミュニティに参加する</h3>
          <p>
            コミュニティに参加するには、ログイン後、参加したいコミュニティ画面から<br/>「参加する」ボタンを押して下さい。<br/>
            また、参加をやめたい場合には同画面から「参加しない」ボタンを押して下さい。
          </p>
        </div>
        <div id="use-section-6" data-role="collapsible">
          <h3>コミュニティにオススメする</h3>
          <p>
            本サービスには外部サイトのページや他のコミュニティ、動画・画像、レビューなど、様々な方法でオススメをすることが出来ます。<br/>
            オススメしたいコミュニティ画面からそれぞれのオススメしたい項目を開き、画面にそって追加をして下さい。
          </p>
        </div>
        <div id="use-section-7" data-role="collapsible">
          <h3>他のユーザーをウォッチする</h3>
          <p>
            他のユーザーをウォッチすることで、<a href="${url('/user/mypage')}" class="icon-link">マイページ</a>からそのユーザーの投稿したオススメを確認することができます。<br/>
            同じ趣味や興味を持ったユーザーの投稿を知りたければ、そのユーザーをウォッチしましょう！<br/>
            ウォッチするには、対象のユーザーの画面から「ウォッチする」ボタンを押して下さい。<br/>
            ウォッチやめるには、同ユーザーの画面から「ウォッチをやめる」ボタンを押して下さい。
          </p>
        </div>
        <div id="use-section-8" data-role="collapsible">
          <h3>その他お問い合わせ</h3>
          <p>
            その他使いかたにご質問があれば、<a href="${url('/inquiry/create')}" class="icon-link">お問い合わせ画面</a>からご質問内容をお問い合わせ下さい。
          </p>
        </div>
      </section>
      
      <section class="pt-12">
        <h1>よくある質問</h1>
        <#list languageHelps as row>
        <div id="section-${row_index}" data-role="collapsible">
          <h3>${row.title}</h3>
          <p>${row.description?replace("\n", "<br/>")}</p>
        </div>
      </#list>
        <div id="section-99" data-role="collapsible">
          <h3>その他ご質問</h3>
          <p>
            その他ご質問があれば、<a href="${url('/inquiry/create')}" class="icon-link">お問い合わせ画面</a>からご質問内容をお問い合わせ下さい。
          </p>
        </div>
      </section>
      
    </div>
    
    <script type="text/javascript">
        $(document).ready(function(){
          $('.clmn-left-menu').stickyMojo({footerID: '#footer', contentID: '.clmn-right-main'});
        });
    </script>
</section>

<script type="text/javascript" src="${assets('stickyMojo.js', 'js', 'common')}" ></script>
