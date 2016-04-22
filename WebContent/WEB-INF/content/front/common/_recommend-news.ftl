<section>
    <div class="sub-section-head">
      <hr/>
      <h2>おすすめニュース</h2>
    </div>
    <#if recommendNews?? >
    <ul class="c-rn">
      <#list recommendNews as row>
      <li class="clearfix">
        <div class="c-rn-img">
          <#if row.imageUrl??>
          <img src="${row.imageUrl}" alt="" class="radius-3 b-shadow" width="48" height="48"/>
          <#else>
          <img src="http://dig-i.com/assets/sp/images/no-image.png" alt="no image" width="48" height="48"/>
          </#if>
        </div>
        <div class="c-rn-text">
          <div><a href="${row.url}" target="_blank">${cutStr(row.title, 20)?html}</a></div>
          <img src="http://dig-i.com/assets/common/images/icn_${row.resourceType?lower_case}.png" alt="${row.resourceName?html}" width="16" height="16" / >
          <span class="fs-ss">${cutStr(row.resourceName, 10)?html}</span>
        </div>
      </li>
      </#list>
    </ul>
    </#if>
</section>