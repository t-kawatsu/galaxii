<section>
    <#if communityActivities?? && communityActivities.items??>
    <div class="c-activities cm-activities" >
    <ul>
		<#include '../community-activity/_community-activities.ftl' />
    </ul>
    <#if communityActivities.limit < communityActivities.total >
      <div class="list-more-con loading">
        <a data-dest-con-selector=".c-activities ul" href="${url('/community-activity/more-community-ajax?id=' + community.id?c )}" data-par-page-limit="${communityActivities.limit}"></a>
      </div>
    </#if>
    </div>
    <script type="text/javascript">
	  $(document).ready(function() {
		var uaSlider = new my.VSlider('.c-activities', '.c-activities ul');
	    $('.c-activities').scroll(function() {
		    if ($(this).scrollTop() >= $(this).find('ul').outerHeight() - $(this).innerHeight() -5) {
		    	$(this).find('.list-more-con a').click();
		    }
		});
  	  });
    </script>
    </#if>
</section>