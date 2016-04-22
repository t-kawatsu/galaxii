<section>
    <ul class="switch-menu v-left-list">
        <li class="selected"><a href="#">画像アップロード</a></li><li><a href="#">Amazonから取得</a></li><li><a href="#">Youtubeから取得</a></li>
    </ul>
    <div class="switch-item">
        <#include '../common/_image-upload-panel.ftl' />
    </div>
    <div class="switch-item">
    	<#include 'amazon-create.ftl' />
    </div>
    <div class="switch-item">
    	<#include 'youtube-create.ftl' />
    </div>
    <script type="text/javascript">
    	my.resources.get("app").Behavior.switchMenu.bind();
    </script>
</section>