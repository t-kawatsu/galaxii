<section id="cm-comment-section">
  <ul class="cc-comments">
	<#include '../common/_comment-form.ftl' />
	<#if comments??>
	  <#assign ctrlBaseName = 'community-comment'/>
	  <#include '../common/_comments.ftl' />
    </#if>
  </ul>
  <#if communityComments.limit < communityComments.total >
  <div class="list-more-con loading">
    <a class="icon-caret-down btn-more" href="${url('/community-comment/more-ajax?id=' + community.id)}"
         data-par-page-limit="${communityComments.limit}" 
         data-dest-con-selector=".cc-comments" >もっと見る</a>
  </div>
  </#if>
</section>