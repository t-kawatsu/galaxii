<div class="sub-section-head">
  <hr/>
  <h2 class="icon-wrench">アクティビティ</h2>
</div>
<#if communityActivities?? && communityActivities.items??>
<div class="c-activities cm-activities" >
  <ul>
	<#include '../community-activity/_user-activities.ftl' />
  </ul>
  <#if communityActivities.limit < communityActivities.total >
    <div class="list-more-con loading">
      <a data-dest-con-selector=".c-activities ul" href="${url('/community-activity/more-user-ajax?id=' + user.id?c )}" data-par-page-limit="${communityActivities.limit}"></a>
    </div>
  </#if>
</div>
    
<script type="text/javascript">
  $(document).ready(function() {
	var uaSlider = new my.VSlider('.c-activities', '.c-activities ul');
	my.resources.get("app").community.Behavior.bindAutoMorePaginate(".c-activities");
  });
</script>
</#if>