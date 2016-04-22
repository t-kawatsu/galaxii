  <section class="cm-main-section clearfix">
    <div class="sub-section-head">
      <hr/>
      <h3>コミュニティ</h3>
    </div>
    <div class="clearfix">
        <div class="icn-category-c icn-category-c-${community.categoryId} fl mr6"></div>
        <h1 class="cm-title"><a href="${url('/community/read/' + community.id)}">${community.title?html}</a>&nbsp;<span class="cm-category fs-ss">${languageSetting("C006", community.categoryId)}</span></h1>
    </div>
    <p>${emotion(cutStr(community.description, 30)?html)}</p>
  </section>