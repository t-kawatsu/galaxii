<#switch contents>
      <#case "HOME">
        <#include '../common/_community-activities.ftl' />
        <#break>
      <#case "COMMENT">
		<#include '_community-comments.ftl' />
		<#break>
	  <#case "IMAGE">
	    <#assign imageType = 'IMAGE' />
	    <#include '../community-image/_index.ftl' />
	    <#break>
	  <#case "MOVIE">
	    <#assign imageType = 'MOVIE' />
		<#include '../community-image/_index.ftl' />
		<#break>
	  <#case "REVIEW">
		<#include '../community-topic/_index.ftl' />
		<#break>
	  <#case "EVENT">
		<#include '../community-event/_index.ftl' />
		<#break>
	  <#case "RECOMMEND">
	    <#include '../community-recommend/_index.ftl' />
	    <#break>
</#switch>