	<#switch contents>
	  <#case 'WATCH'>
	    <#include '_read-watch-activity.ftl' />
	    <#break />
	  <#case 'ACTIVITY'>
	    <#include '_read-activity.ftl' />
	    <#break />
	  <#case 'COMMUNITY'>
	    <#include '_read-community.ftl' />
	    <#break/>
	  <#case 'DETAIL'>
	    <#include '_read-detail.ftl' />
	    <#break />
	</#switch>