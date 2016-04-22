<section id="amazon-create">
    <div class="sub-section-head">
        <hr/>
        <h3>Amazonから商品画像を取得</h3>
    </div>
    <#comment>
    <form action="${url('/community-image/amazon-search-url-ajax#amazon-create')}" method="POST" class="amazon-search-url-form ajax-form">
        <dl>
            <dt>URLから取得する</dt>
            <dd>
              <@s.textfield name="amazonSearchUrlForm.url" cssClass="form-text input-shadow" />
              <@s.token />
              <@s.submit value="送信する" cssClass="btn-a form-submit c-inline-btn" />
            </dd>
            <dd>
                <@s.fielderror><@s.param value="%{'amazonSearchUrlForm.url'}" /></@s.fielderror>
            </dd>
        </dl>
    </form>
    </#comment>

    <form action="${url('/community-image/amazon-search-word-ajax#amazon-create')}" method="POST" class="amazon-search-word-form ajax-form">
        <dl>
            <dt>商品画像を検索して取得する</dt>
            <dd>
              <@s.textfield name="amazonSearchWordForm.word" cssClass="form-text input-shadow" />
              <@s.token />
              <@s.submit value="送信する" cssClass="btn-a form-submit c-inline-btn" />
            </dd>
            <dd>
               <@s.fielderror><@s.param value="%{'amazonSearchWordForm.word'}" /></@s.fielderror>
            </dd>
        </dl>
    </form>

	<#if amazonItems?? && 0 < amazonItems?size >
      <#include '_amazon-search-entries.ftl' />
    </#if>

	<script type="text/javascript">
      $.fancybox.update();
	</script>
</section>