<#macro resourceIcon resourceType>
	<#local iconFile = "icn_" + resourceType?lower_case >
	<#if resourceType == 'Twitter'>
		<#local iconFile = iconFile + ".ico">
	<#else>
		<#local iconFile = iconFile + ".png">
	</#if>
	<img src="${assets(iconFile, 'images', 'common')}" alt="${resourceType}" width="16" height="16" />
</#macro>

<#macro date date>
	<span class="c-i-date icon-time c-color-g">${date?string("yyyy年MM月dd日 H時")}</span>
</#macro>

<#macro communityImg communityId imageId=-1 size="s">
<#local w = communityImageSize(size).width />
<#local h = communityImageSize(size).height />
<#if imageId <= 0 >
  <img src="${assets('c-noimage-'+size+'.png', 'images', 'common')}" class="radius-3" width="${w?c}" height="${h?c}" />
<#else>
  <img src="${communityImageSrc(communityId, imageId, size)}" class="radius-3" width="${w?c}" height="${h?c}" />
</#if>
</#macro>

<#macro userImg user size>
<#if size == "ml">
<#local w = 88 />
<#local h = 88 />
<#local size = "m" />
<#else>
<#local w = userImageSize(size).width />
<#local h = userImageSize(size).height />
</#if>
<#if !user?? || !user.userImageId?? >
  <img src="${assets('u-noimage-'+size+'.png', 'images', 'common')}" class="radius-3 b-shadow" width="${w?c}" height="${h?c}" alt="${user.nickname!""?html}" />
<#else>
  <img src="${userImageSrc(user.id, user.userImageId, size)}" class="radius-3 b-shadow" width="${w?c}" height="${h?c}" alt="${user.nickname!""?html}" />
</#if>
</#macro>

<#macro userBgImg user size>
<#local w = userBgImageSize(size).width />
<#local h = userBgImageSize(size).height />
<#if !user?? || !user.userBgImageId?? >
  <#switch size>
    <#case "ss">
  <div class="c-u-bgimg-noimage-ss">&nbsp;</div>
    <#break/>
    <#case "m">
  <div class="u-bgimg-noimage">&nbsp;</div>
    <#break/>
  </#switch>
<#else>
  <img src="${userBgImageSrc(user.id, user.userBgImageId, size)}" class="radius-3 b-shadow" width="${w?c}" height="${h?c}" />
</#if>
</#macro>

<#macro communityTopicImg communityTopicId communityTopicImageId=-1 size="s">
  <#local w = communityImageSize(size).width />
  <#local h = communityImageSize(size).height />
  <#if !communityTopicImageId?? || communityTopicImageId == -1>
    <img src="${assets('c-noimage-'+size+'.png', 'images', 'common')}" class="radius-3" width="${w?c}" height="${h?c}" />
  <#else>
    <img src="${communityTopicImageSrc(communityTopicId, communityTopicImageId, size)}" class="radius-3" width="${w?c}" height="${h?c}" />
  </#if>
</#macro>

<#macro likeBtn targetName targetId cnt=0 isUserLiked=false >
<div class="like-con clearfix">
<a class="like-cnt icon-thumbs-up mr6 fl like-users-modal" href="${url('/' +targetName+ '-like/index-ajax/' + targetId)}" >${cnt}</a>
<span class="like-btn-con fl <#if isUserLiked!false >hide</#if>"><a href="${url('/' +targetName+ '-like/create-json/' + targetId)}" class="like-btn">いいね!</a></span>
<span class="unlike-btn-con fl <#if !isUserLiked!false >hide</#if>"><a href="${url('/' +targetName+ '-like/delete-json/' + targetId)}" class="like-btn">いいね取り消す</a></span>
</div>
</#macro>

<#macro communityRecommendImg communityRecommend size="s">
  <#local w = communityImageSize(size).width />
  <#local h = communityImageSize(size).height />
  <#if !communityRecommend.imagePath?? >
  <img src="${assets('c-noimage-'+size+'.png', 'images', 'common')}" class="radius-3" width="${w?c}" height="${h?c}" />
  <#else>
  <img src="${communityRecommendImageSrc(communityRecommend.id, communityRecommend.id, size)}" class="radius-3" width="${w?c}" height="${h?c}" />
  </#if>
</#macro>

<#macro errorInputClass fieldname ><#if fieldErrors[fieldname]?exists >error-item</#if></#macro>

<#macro activityIcon type >
  <#if !type?? >
    <#return/>
  </#if>
  <img src="${assets('cc-category-icons/' + type.getName() + '-36.png', 'images', 'common')}" width="36" height="36" class="radius-3 c-activities-img" />
</#macro>

<#macro birthdayInput name birthday={} >
<#local month = 1..31 />
<@s.select list="month" list="${name}" key="${name}Year" cssClass="form-select" />
</#macro>

<#macro communityCategoryName categoryId >
${communityCategoryCodes[categoryId]}
</#macro>