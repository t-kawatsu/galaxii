<section id="user-request-section">
  <div class="sub-section-head">
	  <hr/>
	  <h3 class="icon-bullhorn">ご意見・ご要望</h3>
  </div>
  <p>Galaxiiをより良くするため、ご意見・ご要望をお聞かせください</p>
  <form class="request-form ajax-form" action="${url('/request/create-ajax#user-request-section')}" method="POST">
	<@s.textarea cssClass="form-textarea input-shadow radius-1" name="requestForm.description" placeholder="〇〇の機能を追加してほしい！〇〇をもっとわかりやすく！" style="resize:none" />
	<@s.fielderror><@s.param value="%{'requestForm.description'}" /></@s.fielderror>
	<@s.token />
	<@s.submit value="送信する" cssClass="btn-a form-submit" />
  </form>
</section>