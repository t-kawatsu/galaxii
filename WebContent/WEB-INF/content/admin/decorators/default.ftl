<!DOCTYPE html>
<html lang="ja" xmlns="http://www.w3.org/1999/xhtml" xml:lang="ja">
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<meta http-equiv="Content-Style-Type" content="text/css" >
<meta http-equiv="Content-Script-Type" content="text/javascript" >
<meta name="keywords" content="<@s.text name="app.site.name"/>,管理画面" >
<meta name="description" content="<@s.text name="app.site.name"/>管理画面" >
<meta name="author" content="jogoj Inc." >
<meta name="copyright" content="Copyright &amp;copy;jogoj Inc." >
<link href="${assets('font-awesome.css', 'css', 'common')}" media="screen" rel="stylesheet" type="text/css" >
<link href="${assets('default.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >
<link href="${assets('common.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >
<link href="${assets('favicon.gif', 'images', 'common')}" rel="icon" type="image/gif" >
<link href="${assets('favicon.gif', 'images', 'common')}" rel="shortcut icon" type="image/gif" >
<link href="/admin" rel="index" title="トップ" >
<script type="text/javascript" src="${assets('jquery-1.8.2.min.js', 'js', 'common')}"></script>
<title><@s.text name="app.site.name"/> 管理</title>
</head>

<body>
  <div id="container">
    <div id="wrapper">
      <header id="header" class="clearfix">
        <h1 id="c-site-id"><@s.text name="app.site.name"/>管理画面</h1>
      </header>
      <div class="clearfix">
        <nav id="c-site-nav">
          <ul class="c-h-menu clearfix">
		    <#if isLogined>
		      <#assign menu = {
		        "LanguageHelp":url('/language-help/index'),
		        "LanguageMail":url('/language-mail/index'),
			    "LanguageSetting":url('/language-setting/index'),
			    "Inquiry":url('/inquiry/index'),
			    "Request":url('/request/index'),
			    "User":url('/user/index'),
			    "SiteInformation":url('/site-information/index'),
			    "UserSecession":url('/user-secession/index')
		      }>
		      <#list menu?keys as key>
                <li><a href="${menu[key]}" >${key}</a></li>
		      </#list>
		    </#if>
          </ul>
        </nav>
      
        <div id="contents">
          <div id="${pathinfo('controller')}-${pathinfo('action')}" class="${pathinfo('controller')}-con">
			${body}
          </div>
        </div>
      </div>
      
      <footer id="footer" class="clearfix">
		<address>Copyright &copy;jogoj inc.</address>
      </footer>
    </div>
  </div>
  <script type="text/javascript">
	$(document).ready(function() {
		$('.js-confirm').on('click', function(e){
			return window.confirm('本当にいいんですね？');
		});
	});
  </script>
</body>
</html>
