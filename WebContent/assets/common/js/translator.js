var MyTranslator = function(translate_strategy){
    this.translate_strategy = translate_strategy;
}

MyTranslator.prototype = {

    translate: function(text, from, to, callback){
        window.callbackTranslate = callback;
        this.translate_strategy.translate(text, from, to, 'callbackTranslate');
    },

    translateCallback: function(response){
        //alert(response);
    },

    translateEnToJa: function(text, callback){
        this.translate(text, 'en', 'ja', callback);
    },

    translateJaToEn: function(text, callback){
        this.translate(text, 'ja', 'en', callback);
    }
};

MyTranslator.AjaxTranslateStrategy = function(appId){
    this.appId = appId;
}

MyTranslator.AjaxTranslateStrategy.prototype = {

    TRANSLATE_QUERY_URL: 'http://api.microsofttranslator.com/V2/Ajax.svc/Translate',

    translate: function(text, from, to, callback){
                var s_elem = document.createElement("script");
                s_elem.src = this.TRANSLATE_QUERY_URL + '?'
                        + 'oncomplete=' + callback
                        + '&appId=' + this.appId
                        + '&from=' + from
                        + '&to=' + to
                        + '&text=' + encodeURIComponent(text);
                document.getElementsByTagName("head")[0].appendChild(s_elem);
    }
};
