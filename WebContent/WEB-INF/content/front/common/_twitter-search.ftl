<div id="tweets" style="height:320px"></div>
<script>
// http://www.greenspace.info/mt/2010/08/31/twitter_12.html
new TWTR.Widget({
  id: "tweets",
  version: 2,
  type: 'search',
  search: '${tag.name?html}',
  interval: 2000,
  title: '${tag.name?html}',
  //subject: '空一面に',
//  width: 250,
  height: 300,
  theme: {
    shell: {
      background: '#ffffff',
      color: '#ffffff'
    },
    tweets: {
      background: '#ffffff',
      color: '#222',
      links: '#1985b5'
    }
  },
  features: {
    scrollbar: false,
    loop: false,
    live: true,
    behavior: 'default'
  }
}).render().start();
</script>
