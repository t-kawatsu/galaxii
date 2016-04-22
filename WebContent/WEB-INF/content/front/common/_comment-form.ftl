<li id="community-comment-con" class="clearfix">
  <div class="cc-comment-user">
    <@my.userImg currentUser!{} "s" />
  </div>
  <div class="cc-comment-wrap">
    <#if !commentFormAction?? >
      <#assign commentFormAction = url('/community-comment/create-ajax') />
    </#if>
    <form action="${commentFormAction}#community-comment-con" class="ajax-form cc-comment-form radius-3 b-shadow" method="POST">
      <div class="cc-comment radius-3 b-shadow <@my.errorInputClass 'commentForm.description'/>">
        <#if isLogined>
        <@s.textarea name="commentForm.description" id="description" maxlength="140" cssClass="form-textarea auto-resize-textbox js-comment-input cc-input" rows="2" placeholder="コメントする(140文字以内)" cols="80" />
        <#else>
        <@s.textarea name="commentForm.description" id="description" maxlength="140" cssClass="js-input-require-member form-textarea auto-resize-textbox js-comment-input cc-input" rows="2" placeholder="コメントする(140文字以内)" cols="80" />
        </#if>
        <@s.token />
        <@s.hidden name="commentForm.baseId" />
        <@s.fielderror><@s.param value="%{'commentForm.description'}" /></@s.fielderror>
      </div>
      <div class="clearfix cc-comment-submit-menu">
        <div class="fl">
          <a href="#" class="js-emotion-palet-trigger" data-dest-selector="#description"><img src="${assets('icn-like.png', 'images', 'common')}" alt="絵文字" height="27"></a>
        </div>
        <div class="clearfix fr">
          <!-- 
          <div class="cc-comment-share-twitter js-tooltip" title="Twitterに連携する">
            <@s.checkbox name="commentForm.shareTwitter" cssClass="form-checkbox"  />
            <label for="commentForm_shareTwitter"><img src="${assets('icn-twitter.png', 'images', 'common')}" alt="twitter" height="27"></label>
          </div>
          <div class="cc-comment-share-facebook js-tooltip" title="Facebookに連携する">
            <@s.checkbox name="commentForm.shareFacebook" cssClass="form-checkbox" fieldValue="false" />
            <label for="commentForm_shareFacebook"><img src="${assets('icn-facebook.png', 'images', 'common')}" alt="facebook" height="27"></label>
          </div>
           -->
          <@s.submit value="コメントする" cssClass="btn-b form-submit fl" style="width:100px;" />
        </div>
      </div>
      <script type="text/javascript">
        $(document).ready(function(){
          /* http://d.hatena.ne.jp/xyk/20110217/1297910791 */
          $('.auto-resize-textbox').autosize({});
        });
      </script>
    </form>
  </div>
</li>
<#if createdComment?? >
   <#assign comments = [createdComment] />
   <#include '_comments.ftl' />
   <script type="text/javascript">
     $.fancybox.update();
   </script>
</#if>