/**
 * jquery.caretpos.js 0.1 - jQuery Plugin
 * http://d.hatena.ne.jp/tubureteru/
 * Copyright (c) 2011 tubureteru
 * Licensed under the MIT license
 * http://d.hatena.ne.jp/tubureteru/20110101/p1
 */
(function(c){var b=function(g){var f="character";var e=this.get(0);if(g==null){return a(e,f)}if(g=="first"){g=0}if(g=="last"){g=this.val().length}d(e,g,f);return this};var a=function(g,h){var f=0;if(document.selection){g.focus();var e=document.selection.createRange();e.moveStart(h,-g.value.length);f=e.text.length}else{if(g.selectionStart||g.selectionStart=="0"){f=g.selectionStart}}return(f)};var d=function(f,h,g){if(f.setSelectionRange){f.focus();f.setSelectionRange(h,h)}else{if(f.createTextRange){var e=f.createTextRange();e.collapse(true);e.moveEnd(g,h);e.moveStart(g,h);e.select()}}};c.fn.extend({caretPos:b})})(jQuery);
