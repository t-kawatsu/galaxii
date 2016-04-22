<#assign contentsId = userViolationForm.category + "-" + userViolationForm.categoryDataId?c />
<section class="violation-section" id="${contentsId}">
    <div class="sub-section-head">
        <hr/>
        <h4 class="icon-minus-sign">違反申告</h4>
    </div>
    <form action="${url('/user-violation/create-ajax/' + userViolationForm.category.getName() + '/' + userViolationForm.categoryDataId)}#${contentsId}" method="POST" class="ajax-form">
      <p>利用規約に違反している可能性がある投稿についてご報告いただきありがとうございます。</p>
      <p class="bold pt-4">この投稿についてご報告いただいた理由をお知らせください。</p>
      <div class="violation-types pt-12">
        <@s.radio listKey="key" listValue="value" list="userViolationForm.types" key="userViolationForm.typeId"/>
      </div>
      <div class="<@my.errorInputClass 'userViolationForm.description'/> pt-12">
        <@s.textarea name="userViolationForm.description" cssClass="form-textarea auto-resize-textbox cc-input" placeholder="詳細(任意)" style="font-size:13px; width:94%;" />
        <@s.fielderror><@s.param value="%{'userViolationForm.description'}" /></@s.fielderror>
      </div>
      <@s.token />
      <ul class="center-list pt-12" >
		<li><button type="button" class="form-button btn-a js-cancel-qt-modal btn-small" >キャンセル</button></li>
		<li><@s.submit cssClass="form-submit btn-b btn-small" value="送信" /><li>
      </ul>
      <script type="text/javascript">
        $('.auto-resize-textbox').autosize({ });
      </script>
    </form>
</section>