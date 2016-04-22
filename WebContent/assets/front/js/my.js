
my = {
	bootstrap: function() {
		/* resources */
		var resources = {
			input: this.Input,
     		link : this.Link,
     		modal : this.Modal,
     		tooltip : this.Tooltip,
     		app : this.Application
		};
		for (var i in resources) {
			this.resources.push(i, resources[i]);
		}
	},
	resources: {
		_registry: {},
		push: function(name, resource) {
			this._registry[name] = new resource();
		},
		get: function(name) {
			return this._registry[name];
		},
		remove : function(name) {
			this._registry[name] = null;
		},
		clear : function() {
			this._registry = {};
		}
	}
};

my.utils = {
	Loading : {
		settings : {
			loadingClazz : "loading"
		},
		set : function(selector) {
			$(selector).addClass(this.settings.loadingClazz)
			   .height($(selector).height())
			   .data('loading', true);
		},
		remove : function(selector) {
			$(selector).removeClass(this.settings.loadingClazz)
			   .height('auto')
			   .data('loading', false);
		},
		state : function(selector) {
			return $(selector).data('loading');
		}
	},
	
	rewrite: function(url, data, destSelector, method) {
		if(my.utils.Loading.state(destSelector)){
			return true;
		}
		my.utils.Loading.set(destSelector);
		$(destSelector).html(null);
		$.ajax({
			url:  url, 
			type: method || "GET",
			data: data || {}
		}).done(function(data) {
			my.utils.Loading.remove(destSelector);
			if (destSelector.match("^#") 
					&& ("#"+$(data).attr('id') == destSelector)) {
				$(destSelector).replaceWith(data);
				return;
			} else {
				$(destSelector).html(data);
			}
		});
		return true;
	}
};

my.Link = function() {
	this.initialize.apply(this, arguments);
};
my.Link.prototype = {
	initialize: function() {
		var behaviors = [
		    "smoothScroll", "ajaxLink", "ajaxPost", "containerLink", "pjax"
		];
		for(k in behaviors) { this.Behavior[behaviors[k]].bind(); }
	},
	Behavior : {
		smoothScroll : {
			settings : {
				clazz : "smooth-site-link",
				speed : 400 // スクロールの速度(ミリ秒)
			},
			bind : function() {
				var settings = this.settings;
				$("."+settings.clazz).on('click', function() {
					var href= $(this).attr("href");
					var target = $(href == "#" || href == "" ? 'html' : href);
					var position = target.offset().top;
					var webkit = !document.uniqueID && !window.opera && !window.globalStorage && window.localStorage;
					$(webkit ? 'body' : 'html').animate({scrollTop:position}, settings.speed, 'swing');
					return false;
				});
				// TOPへいくやつ
				var topBtn = $('#page-top');
				topBtn.hide();
				$(window).scroll(function () {
					$(this).scrollTop() > 100 ? topBtn.fadeIn() : topBtn.fadeOut();
				});
			}
		},
		ajaxLink : {
			settings : {
				container : "body",
				clazz : "ajax-get",
				loadingClazz : "loading"
			},
			bind : function() {
				var settings = this.settings;
				var context = this;
				$(settings.container).on("click", "."+settings.clazz, function(e) {
					e.preventDefault();
					var perse = $(this).attr('href').split('#');
					var renderElemId = perse[1];
					return my.utils.rewrite(
							$(this).attr('href'), null, '#'+renderElemId, "GET");
				});
			}
		},
		ajaxPost: {
			settings : {
				container : "body",
				clazz : "ajax-form"
			},
			bind : function() {
				var settings = this.settings;
				var context = this;
				$(settings.container).on('submit', "."+settings.clazz, function(e) {
					var perse = $(this).attr('action').split('#');
					var render_elem_id = perse[1];
					var render_elem_selector = '#'+render_elem_id;
					var postData = {};
					$(this).find(':input').each(function() {
						if($(this).attr('type') == 'checkbox') {
							if($(this).is(':checked')) {
								postData[$(this).attr('name')] = $(this).val();
							}
						} else {
							postData[$(this).attr('name')] = $(this).val();
						}
					});
					my.utils.rewrite(
							$(this).attr('action'), 
							postData, 
							render_elem_selector, 
							$(this).attr('method'));
					return false;
				});
			}
		},
		containerLink: {
			settings : {
				container : "body",
				clazz : "js-container-link"
			},
			bind : function() {
				var settings = this.settings;
				$(settings.container).on("click", "."+settings.clazz, function(e) {
					// TODO 
					var target = $(this).find('a:first');
					if(target.hasClass("modal"))
						return false;
					if(target.attr('href'))
						location.href = target.attr('href');
				});
			}
		},
		pjax: {
			settings : {
				container : "body",
				clazz : "js-pjax",
				destClazz : "js-pjax-con"
			},
			bind : function(destSelector) {
				$.pjax.defaults.scrollTo = false;
				var trgSelector = trgSelector || "."+this.settings.clazz;
				var destSelector = destSelector || "."+this.settings.destClazz;
				var settings = this.settings;
				$(settings.container).pjax(trgSelector, destSelector)
				.on('pjax:send', function() {
					my.utils.Loading.set(destSelector);
					$(destSelector).html(null);
				})
				.on("pjax:success", function() {
					my.utils.Loading.remove(destSelector);
				})
				.on('pjax:error', function() {
					return false;
				})
				.on('pjax:timeout', function() {
					return false;
				});
			}
		}
	}
};

my.Input = function() {
	this.initialize.apply(this, arguments);
};
my.Input.prototype = {
	initialize: function() {
		var behaviors = [
		    "supplement", "error", "cancelEnter", "autoResize"
		];
		for(k in behaviors) { this.Behavior[behaviors[k]].bind(); }
	},
	
	Behavior : {
		/* input focus時の補足バルーンの振る舞い */
		supplement : {
			settings : {
				inputContainer : ".js-input-balloon-con",
				descContainer  : ".js-input-balloon-d",
				posmyAttr : "data-balloon-pos-my",
				posAtArrr : "data-balloon-pos-at",
				posmyDefault : "center left",
				posAtDefault : "center right",
				blnAdjust : { x: 4, y: 0 }
			},
			bind : function() {
				var settings = this.settings;
				$(settings.inputContainer).on('focus', 
						'input, textarea', function(event) {
					var desc = $(this)
						.parents(settings.inputContainer)
						.find(settings.descContainer);
					if(desc.length < 1) {
						return;
					}

					$(this).qtip( "destroy" ).qtip({
						overwrite: true,
						content: { text: desc },
						position: {
							my: $(this).attr(settings.posmyAttr) || settings.posmyDefault, 
							at: $(this).attr(settings.posAtArrr) || settings.posAtDefault,
							adjust: settings.blnAdjust
						},
						style: {
							classes: 'input-balloon', def: false, widget:false
						},
						show: {
							event: event.type, ready: true 
						},
						hide: { event:'blur', delay:0 }
					}, event);
				});
			}
		},
		error : {
			settings : {
					container : "body",
					clazz : "error-item"
			},
			bind : function() {
				var settings = this.settings;
				$(settings.container)
				.off('focus', "."+settings.clazz)
				.on('focus',  "."+settings.clazz, {}, function(event) {
					$(this).removeClass(settings.clazz)
						.find("."+settings.clazz).remove();
				});
			}
		},
		cancelEnter : {
			settings : {
				container : "body",
				clazz : "cancel-enter"
			},
			bind : function() {
				var settings = this.settings;
				var selector = "."+settings.clazz;
				$(settings.container)
				.off('keydown', selector)
				.off('blur', selector)
				.on('keydown', selector, function(e) {
				    if (e.which == 13)
						return false;
				}).on('blur', selector, function(e) {
					text = $(this).val();
					new_text = text.replace(/\n/g, "");
					if (new_text != text) {
						$(this).val(new_text);
					}
				});
			}
		},
		autoResize : {
			settings : {
				clazz : "auto-resize-textbox"
			},
			bind : function() {
				// http://d.hatena.ne.jp/xyk/20110217/1297910791
				$("."+this.settings.clazz).autosize({});
			}
		}
	}
};

my.Modal = function() {
	this.initialize.apply(this, arguments);
};
my.Modal.prototype = {
	initialize: function() {
		var behaviors = [
		    "modal"
		];
		for(k in behaviors) { this.Behavior[behaviors[k]].bind(); }
	},
	Behavior : {
		modal : {
			settings : {
				container : "body",
				selector : ".modal",
				imgSelector  : ".image-modal",
				cancelSelector : ".js-cancel-modal"
			},
			bind : function() {
				$(this.settings.selector).fancybox({
					width:'auto', height:'auto',
					autoSize:true, type:'ajax', title:null
				});
				$(this.settings.imgSelector).fancybox({
					width:'auto', height:'auto',
					autoSize:true, title:null
				});
				$(this.settings.container)
				.on('click', this.settings.cancelSelector, function(e) {
					e.preventDefault();
					$.fancybox.close();
				});
				$('.like-users-modal').fancybox({
					width:'auto', height:'auto',
					autoSize:true, type:'ajax', title:null, 
					margin:0, padding:0, closeBtn:false
				});
			}
		}
	}
};

my.Tooltip = function() {
	this.initialize.apply(this, arguments);
};
my.Tooltip.prototype = {
	initialize: function() {
		var behaviors = [
		    "titleDetail", "modal", "confirm", "ajaxContents"
		];
		for(k in behaviors) { this.Behavior[behaviors[k]].bind(); }
	},
	Behavior : {
		titleDetail : {
			settings : {
				container : "body",
				selector : ".js-tooltip",
				posmyDefault : "top center",
				posAtDefault : "bottom center",
				tooltipClasses : "c-my-tooltip",
				blnAdjust : { y: 4 }
			},
			bind : function() {
				var settings = this.settings;
				$(settings.container).on('mouseover', settings.selector, function(event) {
					$(this).qtip({
					  id: 'qtip',
					  overwrite: false, attr:'title',
					  position: {
						my: settings.posmyDefault, 
						at: settings.posAtDefault, 
						adjust: settings.blnAdjust
					  },
					  style: {
						classes: settings.tooltipClasses
					  },
					  show: { event: event.type, ready: true, delay:0, solo:true }
					}, event);
				});
			}
		},
		modal : {
			settings : {
				container : window,
				selector : ".js-qt-modal",
				cancelSelector : ".js-cancel-qt-modal",
				posmyDefault : "center",
				posAtDefault : "center",
				tooltipClasses : "c-my-tooltip-modal",
				blnAdjust : { y: 4 }
			},
			bind : function() {
				var settings = this.settings;
				$(settings.container).on('click', settings.selector, function(e) {
					e.preventDefault();
					$(settings.container).qtip({
						id: 'modal', // Since we're only creating one modal, give it an ID so we can style it
						content: {
							text: '<img src="/assets/common/images/fancybox_loading.gif" />',
							ajax: {
								url: $(this).attr('href')
							}
						},
						position: {
							my: settings.posmyDefault, 
							at: settings.posAtDefault, 
							target: $(settings.container), 
							effect:false
						},
						show: {
							solo: true, modal: true, effect: false
						},
						events: {
							hide:function(event, api) { 
								$(settings.container).qtip('destroy'); 
							}
						},
						hide: false,
					    style: {
							classes: settings.tooltipClasses
						},
					}).qtip("show");
				});
				$(settings.container).on('click', settings.cancelSelector, function(e) {
					e.preventDefault();
					$(settings.container).qtip('destroy');
				});
			}
		},
		confirm : {
			settings : {
				container : "body",
				selector : ".js-confirm-contents",
				titleAttr : "data-confirm-title",
				boxContainerId : "delete-confirm-box",
				posmyDefault : "center",
				posAtDefault : "center",
				tooltipClasses : "c-my-tooltip-modal",
				blnAdjust : { y: 4 }
			},
			bind : function() {
				var settings = this.settings;
				$(settings.container).on('click', settings.selector, function(e) {
					e.preventDefault();
					var title = $(this).attr(settings.titleAttr);
					var yesHref = $(this).attr('href');
					var containerId = settings.boxContainerId;
					var deleteConfirmBox = 
					  '<div id="'+containerId+'">'+
						'<p>' + title + '</p>'+
						'<ul class="center-list pt-12">'+
				          '<li><a class="confirm-no btn-small btn-a js-cancel-qt-modal" href="#">いいえ</a></li>'+
				          '<li class="pl-4"><a class="confirm-yes btn-small btn-b ajax-get" href="' + yesHref + '#'+containerId+'">はい</a></li>'+
						'</ul>'+
					  '</div>';
					$(window).qtip({
						id: 'modal', 
						content: {
							text: deleteConfirmBox
						},
						position: {
							my: settings.posmyDefault, 
							at: settings.posAtDefault, 
							target: $(window), effect:false
						},
						show: {
							solo: true, modal: true
						},
						events: {
							hide:function(event, api) { $(window).qtip('destroy'); }
						},
						hide: false,
					    style: {
							classes: settings.tooltipClasses
						},
					}).qtip("show");
				});
			}
		},
		ajaxContents : {
			settings : {
				container : "body",
				selector : ".js-tooltip-onmouse-ajax",
				hrefAttr : "data-onmouse-href",
				posmyDefault : "top center",
				posAtDefault : "bottom center",
				tooltipClasses : "c-my-tooltip-detail",
				blnAdjust : { y: 4 }
			},
			bind : function() {
				var settings = this.settings;
				$(settings.container).on('mouseover', settings.selector, function(e) {
					$(this).qtip('destroy').qtip({
						overwrite: true,
						id: 'user-profile',
						content: {
							text: /*$(this).attr('title') || */'<img src="/assets/common/images/loading.gif" />',
							ajax: {
								cache:true,
								url: $(this).attr(settings.hrefAttr)
							}
						},
						position: {
							my: settings.posmyDefault, 
							at: settings.posAtDefault, 
							target:$(this), effect:false
						},
						show: {
							solo: true, event: e.type, ready: false
						},
						events: {},
						hide: false,
					    style: {
							classes: settings.tooltipClasses, def:false
						},
					}, e).qtip("show");
				});
				$(document).on('mouseleave', settings.selector, function(e) {
					$(this).qtip('destroy');
				});
			}
		}
	}
};

my.IHSlider = function(items_con_selector, items_wrap_selector, items_selector, pager_con_selector, pagingStepNum, dispNumParPage, scrollTimerSpan, duration) {
  this.items_con = $(items_con_selector).css('position','relative');
  this.items_wrap = $(items_wrap_selector).css('position','absolute').addClass('clearfix');
  this.items = $(items_selector).css('float', 'left');
  this.pager_con = $(pager_con_selector);
  this.current_item = 1;
  this.panel_width = null;
  this.diffX = 0;
  this.pagingStepNum = pagingStepNum || 1;
  this.dispNumParPage = dispNumParPage || 1;
  this.scrollTimer;
  this.scrollTimerSpan = scrollTimerSpan == undefined ? 2500 : scrollTimerSpan;
  this.duration = duration == undefined ? "1.2s" : duration;
  
  this.init();
};
my.IHSlider.prototype = { 

  init: function() { 
	this.items.show();
	this.reflectPager();
	this.bindPager();
	this.bindAutoScroll();
  },  

  next: function() {
	if(this.items.length < (this.current_item+this.dispNumParPage)) {
	  this.current_item = 0;
	}
	this.current_item += this.pagingStepNum;
	this.moveToItem(this.current_item);
  },
  
  prev: function() {
    if(this.current_item <= this.dispNumParPage) {
	  return false;
	}
	this.current_item -= this.pagingStepNum;
	this.moveToItem(this.current_item);
  },

  moveToItem: function(i) {
    var x = -1 * this.items.outerWidth(true) * (i -1);
	this.moveX(x);
	this.reflectPager();
	this.items_con.trigger('item.change');
  },

  moveX: function(x) {
	this.items_wrap.css("-webkit-transition-duration", this.duration);
	this.items_wrap.css("-webkit-transition-timing-function", "ease-in-out");
	this.items_wrap.css("-webkit-transform", "translate3d(" +x + "px, 0, 0)");
	this.items_wrap.css("-moz-transition-duration", this.duration);
	this.items_wrap.css("-moz-transition-timing-function", "ease-in-out");
	this.items_wrap.css("-moz-transform", "translate3d(" +x + "px, 0, 0)");
	this.items_wrap.css("-o-transition-duration", this.duration);
	this.items_wrap.css("-o-transition-timing-function", "ease-in-out");
	this.items_wrap.css("-o-transform", "translate3d(" +x + "px, 0, 0)");
	this.items_wrap.css("-ms-transition-duration", this.duration);
	this.items_wrap.css("-ms-transition-timing-function", "ease-in-out");
	this.items_wrap.css("-ms-transform", "translate3d(" +x + "px, 0, 0)");
  },

  setToItem: function(i) {
    var x = this.getItemBaseX(i);
	this.setX(x);
	this.items_con.trigger('item.change');
  },

  setX: function(x) {
    this.items_wrap.css("-webkit-transition-duration", "0s");
    this.items_wrap.css("-webkit-transition-timing-function", "default");
    this.items_wrap.css("-webkit-transform", "translate3d(" +x + "px, 0, 0)");
    this.items_wrap.css("-moz-transition-duration", "0s");
    this.items_wrap.css("-moz-transition-timing-function", "default");
    this.items_wrap.css("-moz-transform", "translate3d(" +x + "px, 0, 0)");
	this.items_wrap.css("-o-transition-duration", "0s");
	this.items_wrap.css("-o-transition-timing-function", "default");
	this.items_wrap.css("-o-transform", "translate3d(" +x + "px, 0, 0)");
	this.items_wrap.css("-ms-transition-duration", "0s");
	this.items_wrap.css("-ms-transition-timing-function", "default");
	this.items_wrap.css("-ms-transform", "translate3d(" +x + "px, 0, 0)");
  },
	  
  reflectPager: function() {
    if(this.current_item <= this.dispNumParPage) {
	  this.pager_con.find('.prev').css('opacity','.3');
    } else {
	  this.pager_con.find('.prev').css('opacity','1');
    }
    if(Math.ceil(this.items.length / this.dispNumParPage)
    	<= Math.ceil(this.current_item / this.dispNumParPage)) {
      this.pager_con.find('.next').css('opacity','.3');
    } else {
	  this.pager_con.find('.next').css('opacity','1');
    }

    this.pager_con.find('.page').addClass('off').filter("[class*='page-"+this.current_item+"']").removeClass('off');
  },

  getItemBaseX: function(i) {
    return -1 * this.panel_width * (i -1);
  },
  
  bindPager: function() {
	  this.pager_con.find('.prev').on('click', { _this:this}, function(event) {
		  event.preventDefault();
		  var _this = event.data._this;
		  _this.prev();
	  });
	  this.pager_con.find('.next').on('click', { _this:this}, function(event) {
		  event.preventDefault();
		  var _this = event.data._this;
		  _this.next();
	  });
	  this.pager_con.find('.page').each(function(event) {
		  event.preventDefault();
	  });
  },
  
  bindAutoScroll: function() {
	var obj = this;
	this._bindScrollTimer();
	this.items_con.on({
		'mouseenter': function(e) {
			clearInterval(obj.scrollTimer);
		},
		'mouseleave': function(e) {
			obj._bindScrollTimer();
		}
	});
  },
  
  _bindScrollTimer: function() {
	var obj = this;
	clearInterval(this.scrollTimer);
	this.scrollTimer = setInterval(function() {
		obj.next();
	}, this.scrollTimerSpan);
  }
};



my.VSlider = function(itemsConSelector, itemsWrapSelector, scrollTimerSpan, scrollSpeed) {
  this.itemCon = $(itemsConSelector).css('overflow-x', 'hidden').css('overflow-y', 'scroll');
  this.itemsWrap = $(itemsWrapSelector);
  this.items = this.itemsWrap.children();
  this.currentItemIdx = 0;
  this.heightParItem = this.items.outerHeight(true);
  this.scrollTimer;
  this.scrollTimerSpan = scrollTimerSpan == undefined ? 3000 : scrollTimerSpan;
  this.scrollSpeed = scrollSpeed == undefined ? 800 : scrollSpeed;
  
  this.init();
};
my.VSlider.prototype = {
  init: function() { 
	  this.bindAutoScroll();
  },
  
  next: function() {
	this.moveToItem(this.detectPresentTopItemIdx()+1);
  },
	  
  prev: function() {
	this.moveToItem(this.detectPresentTopItemIdx()-1);
  },
  
  detectPresentTopItemIdx: function() {
	return Math.floor(this.itemCon.scrollTop() / this.heightParItem);
  },
  
  traversalItems: function() {
	this.items = this.itemsWrap.children();
  },
  
  moveToItem: function(i) {
	  if(i <= 0) {
		  i = 0;
	  } else if(this.itemsWrap.children().length <= i) {
		  i = this.itemsWrap.children().length -1;
	  }
	  var x = i * this.heightParItem;
	  this.currentItemIdx = i;
	  this.moveX(x);
  },
  
  moveX: function(x) {
	this.itemCon.animate({
	  scrollTop: x
	}, this.scrollSpeed);
  },
  
  bindAutoScroll: function() {
	var obj = this;
	this._bindScrollTimer();
	this.itemCon.on({
		'mouseenter': function(e) {
			clearInterval(obj.scrollTimer);
		},
		'mouseleave': function(e) {
			obj._bindScrollTimer();
		}
	});
  },
  
  _bindScrollTimer: function() {
	var obj = this;
	clearInterval(this.scrollTimer);
	this.scrollTimer = setInterval(function() {
		obj.next();
	}, this.scrollTimerSpan);
  }
};

my.Slider = function(items_con_selector, items_wrap_selector, items_selector, pager_con_selector, disp_num_par_page) {
  this.items_con = $(items_con_selector).css('position','relative');
  this.items_wrap = $(items_wrap_selector).css('position','absolute').addClass('clearfix');
  this.items = $(items_selector).css('float', 'left');
  this.pager_con = $(pager_con_selector);
  this.current_item = 1;
  this.panel_width = null;
  this.diffX = 0;
  this.disp_num_par_page = disp_num_par_page || 1;
  this.init();
};
my.Slider.prototype = { 

  init: function() { 
	this.resize();
	this.items.show();
	this.reflectPager();
	this.bindPager();
  },  

  resize: function() {
	var itemsWidth = 0;
	this.items.each(function() {
		itemsWidth += $(this).outerWidth(true);
	});
	this.panel_width = (itemsWidth / this.items.length) * this.disp_num_par_page;
	this.items_wrap.width(itemsWidth);
	this.setToItem(this.current_item);
  },

  next: function() {
	if(this.items.length < (this.current_item+this.disp_num_par_page)) {
	  return false;
	}
	this.current_item += this.disp_num_par_page;
	this.moveToItem(this.current_item);
  },
  
  prev: function() {
    if(this.current_item <= this.disp_num_par_page) {
	  return false;
	}
	this.current_item -= this.disp_num_par_page;
	this.moveToItem(this.current_item);
  },

  moveToItem: function(i) {
    var x = -1 * this.items.outerWidth(true) * (i -1);
	this.moveX(x);
	this.reflectPager();
	this.items_con.trigger('item.change');
  },

  moveX: function(x) {
	this.items_wrap.css("-webkit-transition-duration", "0.3s");
	this.items_wrap.css("-webkit-transition-timing-function", "ease-in-out");
	this.items_wrap.css("-webkit-transform", "translate3d(" +x + "px, 0, 0)");
	this.items_wrap.css("-moz-transition-duration", "0.3s");
	this.items_wrap.css("-moz-transition-timing-function", "ease-in-out");
	this.items_wrap.css("-moz-transform", "translate3d(" +x + "px, 0, 0)");
	this.items_wrap.css("-o-transition-duration", "0.3s");
	this.items_wrap.css("-o-transition-timing-function", "ease-in-out");
	this.items_wrap.css("-o-transform", "translate3d(" +x + "px, 0, 0)");
	this.items_wrap.css("-ms-transition-duration", "0.3s");
	this.items_wrap.css("-ms-transition-timing-function", "ease-in-out");
	this.items_wrap.css("-ms-transform", "translate3d(" +x + "px, 0, 0)");
	
  },

  setToItem: function(i) {
    var x = this.getItemBaseX(i);
	this.setX(x);
	this.items_con.trigger('item.change');
  },

  setX: function(x) {
    this.items_wrap.css("-webkit-transition-duration", "0s");
    this.items_wrap.css("-webkit-transition-timing-function", "default");
    this.items_wrap.css("-webkit-transform", "translate3d(" +x + "px, 0, 0)");
    this.items_wrap.css("-moz-transition-duration", "0s");
    this.items_wrap.css("-moz-transition-timing-function", "default");
    this.items_wrap.css("-moz-transform", "translate3d(" +x + "px, 0, 0)");
	this.items_wrap.css("-o-transition-duration", "0s");
	this.items_wrap.css("-o-transition-timing-function", "default");
	this.items_wrap.css("-o-transform", "translate3d(" +x + "px, 0, 0)");
	this.items_wrap.css("-ms-transition-duration", "0s");
	this.items_wrap.css("-ms-transition-timing-function", "default");
	this.items_wrap.css("-ms-transform", "translate3d(" +x + "px, 0, 0)");
  },
	  
  reflectPager: function() {
    if(this.current_item <= this.disp_num_par_page) {
	  this.pager_con.find('.prev').css('opacity','.3');
    } else {
	  this.pager_con.find('.prev').css('opacity','1');
    }
    if(Math.ceil(this.items.length / this.disp_num_par_page)
    	<= Math.ceil(this.current_item / this.disp_num_par_page)) {
      this.pager_con.find('.next').css('opacity','.3');
    } else {
	  this.pager_con.find('.next').css('opacity','1');
    }

    this.pager_con.find('.page').addClass('off').filter("[class*='page-"+this.current_item+"']").removeClass('off');
  },

  getItemBaseX: function(i) {
    return -1 * this.panel_width * (i -1);
  },
  
  bindPager: function() {
	  this.pager_con.find('.prev').on('click', { _this:this}, function(event) {
		  event.preventDefault();
		  var _this = event.data._this;
		  _this.prev();
	  });
	  this.pager_con.find('.next').on('click', { _this:this}, function(event) {
		  event.preventDefault();
		  var _this = event.data._this;
		  _this.next();
	  });
	  this.pager_con.find('.page').each(function(event) {
		  event.preventDefault();
	  });
  }
};

my.Application = function() {
	this.initialize.apply(this, arguments);
	this.community = new my.Application.Community();
	this.comment   = new my.Application.Comment();
	this.image     = new my.Application.Image();
	this.tag       = new my.Application.Tag();
};
my.Application.prototype = {
	initialize: function() {
		var behaviors = [
		    "toggleMenu", "like", "emotionPlate", "switchMenu", "userInfo"
		];
		for(k in behaviors) { this.Behavior[behaviors[k]].bind(); }
	},
	Behavior : {
		toggleMenu : {
			settings : {
				container : document,
				selector : ".js-toggle-menu",
				btnSelector : ".js-btn-toggle-menu",
				menuContainerSelector : ".js-toggle-menu-con"
			},
			bind : function() {
				var settings = this.settings;
				$(settings.container).on('click', settings.btnSelector, function(e) {
					e.preventDefault();
					var menu = $(this).parents(settings.menuContainerSelector)
									  .find(settings.selector);
	    			if(menu.css("opacity") <= 0 || menu.css('display') == 'none') {
	    				menu.css("opacity", 0).show().animate({opacity: 1}, "fast" );
		    		} else {
		    			menu.animate({opacity: 0}, {duration:"fast",complete: function(){
		    					$(this).hide();
		    				}
		    			});
		    		}
				});
				$(settings.container).on('mouseleave', settings.selector, function(e) {
					$(this).parents(settings.menuContainerSelector)
					  .find(settings.selector)
					  .animate({opacity: 0}, {duration:"fast",complete: function(e) {
							$(this).hide();
						}
					});
				});
			}
		},
		like : {
			settings : {
				container : "body",
				selector : ".like-btn",
				likeBtnConSelector : ".like-btn-con",
				unlikeBtnConSelector : ".unlike-btn-con",
				conSelector : ".like-con",
				cntSelector : ".like-cnt"
			},
			bind : function() {
				var settings = this.settings;
				$(settings.container).on('click', settings.selector, function(e) {
					e.preventDefault();
					var elem = $(this);
					if(elem.data('sending')) {
						return false;
					}
					elem.data('sending', true);
					var con = $(this).parents(settings.conSelector);
					$.ajax({
						url:$(this).attr('href'), 
						type:'GET'
					}).done(function(res, status) {
						elem.data('sending', false);
						con.find(settings.cntSelector).text(res.count);
						if(res.liked) {
							con.find(settings.likeBtnConSelector).hide();
							con.find(settings.unlikeBtnConSelector).removeClass('hide').show();
						} else {
							con.find(settings.unlikeBtnConSelector).hide();
							con.find(settings.likeBtnConSelector).removeClass('hide').show();
						}
					}).fail(function(xhr, status) {
						if(xhr.status == 401) {
							// require login
							my.Application.Common.dispRequireLoginModal();
						} else {
							// error
						}
					});
					return false;
				});
			}
		},
		emotionPlate : {
			settings : {
				container : document,
				btnSelector : ".js-emotion-palet-trigger",
				loadingText : "Loading...",
				url : "/common/emotion-ajax"
			},
			bind : function() {
				var settings = this.settings;
				$(settings.container).on('click', settings.btnSelector, function(e) {
					e.preventDefault();
					var elem = $(this);
					$(this).qtip('destroy').qtip({
						id: 'emo-tooltip',
						content: {
						  text: settings.loadingText,
						  ajax: {
							url: settings.url,
							type: 'GET',
							data: {},
							success: function(data, status) {
								this.set('content.text', data);
								$('.emotion-palet').off('click', 'a').on('click', 'a', function(e) {
									e.preventDefault();
									var emo_name = $(this).attr('data-emotion-name');
									$(elem).trigger('emotion.click', [emo_name]);
									var input = $($(elem).attr('data-dest-selector'));
									var range = {
										start: input.caretPos(),
										end:   input.caretPos()
									};
									var value = input.val();
									var text  = "[["+emo_name+"]]";
									input.val(value.substr(0, range.start) + text + value.substr(range.end, value.length));
								});
							}
						  }
						},
						position: {
							my: 'left center', at: 'right top', target:$(this), effect:false
						},
						show: { event:false, delay:0 },
						hide: { event:false, delay:0 },
						style: {
							classes:'c-emo-tooltip ui-tooltip-bootstrap', def:false
						},
						evnets: {
							render:function(e, api){}
						}
					}).qtip('show',e);
				});
			}
		},
		switchMenu : {
			settings : {
				container : document,
				selector : ".switch-menu",
				selectedClazz : "selected",
				itemSelector : ".switch-item"
			},
			bind : function() {
				var settings = this.settings;
				$(settings.container)
				.off('click', settings.selector + ' a')
				.on('click',  settings.selector + ' a', function(e) {
					e.preventDefault();
					var li = $(this).parent("li");
					var i = $(this).parents(settings.selector)
								.find('li')
								.removeClass(settings.selectedClazz)
								.index(li);
					li.addClass(settings.selectedClazz);
					$(this).parents('section').find(settings.itemSelector).hide().eq(i).show();
					$.fancybox.update();
				});
				$(settings.container).find("."+settings.selectedClazz + ' a').click();
			},
			bindVis : function() {
				var settings = this.settings;
				$(settings.container)
				.off('click', settings.selector + ' a')
				.on('click',  settings.selector + ' a', function(e) {
					var li = $(this).parent("li");
					var i = $(this).parents(settings.selector)
								.find('li')
								.removeClass(settings.selectedClazz)
								.index(li);
					li.addClass(settings.selectedClazz);
				});
			},
			unbind : function() {
				$(this.settings.container).off('click', this.settings.selector + ' a');
			}
		},
		userInfo : {
			settings : {
				container : document,
				selector : ".js-u-info",
				conSelector : "#c-u-infos-con"
			},
			bind : function() {
				var settings = this.settings;
				// ユーザーおしらせ
				$(settings.selector).on('click', function(e) {
					e.preventDefault();
					if($(settings.conSelector).css('display') == 'none') {
						$(settings.conSelector).show();
						if($(this).attr('data-isloaded')) {
							return false;
						}
						var perse = $(this).attr('href').split('#');
						my.utils.rewrite(
							$(this).attr('href'), null, '#'+perse[1], "GET");
						$(this).attr('data-isloaded', 'true');
					} else {
						$(settings.conSelector).hide();
					}
			  	});
			}
		}
	}
};

my.Application.Common = {
    dispRequireLoginModal: function() {
		$.ajax({
			url:'/error/401-ajax',
			cache:true,
			type:'GET'
		}).done(function(data){
			$.fancybox(data);
		});
    }	
};

my.Application.Comment = function() {
	this.initialize.apply(this, arguments);
};
my.Application.Comment.prototype = {
	initialize: function() {
		var behaviors = [
		    "replay", "input"
		];
		for(k in behaviors) { this.Behavior[behaviors[k]].bind(); }
	},
	Behavior : {
		replay : {
			settings : {
				container : document,
				selector : ".js-comment-reply",
				nicknameAttr : "data-js-user-nickname"
			},
			bind : function() {
				var settings = this.settings;
		     	$(settings.container).on('click', settings.selector, function(e) {
		            var target = $(this).attr(settings.nicknameAttr);
		            $(this).parents('section').find('textarea').val('' + target + 'さん '+"> ")
		                .focus().caretPos('last');
		            return true;
		        });
			}
		},
		input : {
			settings : {
				container : document,
				selector : ".js-comment-input",
				loginRequireClass : "js-input-require-login"
			},
			bind : function() {
				var settings = this.settings;
				$(settings.container).on('focus', settings.selector, function(e) {
					if($(this).hasClass(settings.loginRequireClass)) {
						return false;
					}
					$(this).removeClass('input-shadow').parents('.cc-comment').removeClass('btn-a-2')
						.parents('form').find('.cc-comment-submit-menu').animate(
								{height: "show"},
								"fast");
				});
			}
		}
	}
};

my.Application.Community = function() {
	this.initialize.apply(this, arguments);
};
my.Application.Community.prototype = {
	initialize: function() {
		var behaviors = [
		    "requireLogin", "more", "textHold"
		];
		for(k in behaviors) { this.Behavior[behaviors[k]].bind(); }
	},
	Behavior : {
		requireLogin : {
			settings : {
				container : document,
				selector : ".js-input-require-login"
			},
			bind : function() {
				$(this.settings.container).on('focus', this.settings.selector, function(e) {
					$(this).blur();
					my.Application.Common.dispRequireLoginModal();
				});
			}
		},
		bindAutoMorePaginate : function(containerSelector, padding) {
			var padding = padding || 5;
			var context = this;
			$(containerSelector).scroll(function() {
			    if ($(this).scrollTop() >= $(this).children().outerHeight() - $(this).innerHeight() -padding) {
			    	$(this).find(context.more.settings.selector).click();
			    }
			});
		},
		bindInputCategory : function() {
			var defClass = $('.js-icn-category-c-view').attr('class');
    	    $('.js-category-select').on('change', function() {
    	    	$('.js-icn-category-c-view').attr('class', defClass)
    	    		.addClass("icn-category-c-" + $(this).val());
    	    }).trigger('change');
		},
		more : {
			settings : {
				container : document,
				conSelector : ".list-more-con",
				selector : ".list-more-con a",
				limitAttr : "data-par-page-limit",
				destAttr : "data-dest-con-selector"
			},
			bind : function() {
				var settings = this.settings;
				$(settings.container).off('click', settings.selector)
				  .on('click', settings.selector, function(e) {
					var elem = $(this);
					var con = elem.parents(settings.conSelector);
					con.html(null);
					my.utils.Loading.set(con);
					$.ajax({
						url:$(this).attr('href'),
						type:'GET'
					}).done(function(data) {
						$(elem.attr(settings.destAttr)).append(data);
						my.utils.Loading.remove(con);
						if($(data).length < elem.attr(settings.limitAttr)) {
						} else {
							con.append(elem);
						}
					});
					return false;
				});
			}
		},
		textHold : {
			settings : {
				container : document,
				btnSelector : ".js-open-full-text",
				partialSelector : ".js-partial-text",
				fullSelector : ".js-full-text"
			},
			bind : function() {
				var settings = this.settings;
				$(settings.container).on("click", settings.btnSelector, function(e) {
					e.preventDefault();
					var con = $(this).parents("p")
					con.find(settings.partialSelector).hide();
					con.find(settings.fullSelector).show();
				});
			}
		}
	}
};

my.Application.Image = function() {
	this.initialize.apply(this, arguments);
};
my.Application.Image.prototype = {
	settings : {
		container : document,
		conSelector : ".cm-image-con",
		btnRemoveSelector : ".cc-remove-image",
		uploadCompleteTriggerSelector : ".ajax-file-upload",
		msgSelector : ".btn-select-pc-file"
	},
	
	initialize: function() {},
	
	bindRemoveImage: function () {
		var context = this;
		$(this.settings.conSelector).on('click', this.settings.btnRemoveSelector, function() {
      	  context.clear();
  	    });
	},

	processCreatedCallback: function(res) {
        res = jQuery.parseJSON(res);
        if(!res || res.fileId == undefined){
        	var eMsg = '<ul class="errorMessage">';
            jQuery.each(res.messages, function(){
            	eMsg += '<li><span>' + this + '</span></li>';
            });
            eMsg += '</ul>';
            $(this.settings.msgSelector).html(eMsg);
            return false;
        }
        $(this.settings.uploadCompleteTriggerSelector).trigger(
        		'upload.complete', [res, res.fileId, res.type, res.vendorData]); 
	},

	clear: function(conSelector) {
		var con = $(conSelector || this.settings.conSelector);
		con.find('.cc-remove-image').hide();
		con.find('.cc-no-image').removeClass("hide").show();
		con.find('img').remove();
		con.find('input').val("");
		con.css('background-color', 'transparent');
	},
	
	add: function(src, fileId, type, vendorData, conSelector) {
		var con = $(conSelector || this.settings.conSelector);
		con.removeClass("hide");
		con.append('<img src="'+src+'" class="radius-3" />');
		this.appendImageInfo(fileId, type, vendorData, conSelector)
		con.find('.cc-no-image').hide();
		con.find('.cc-remove-image').removeClass("hide").show();
		if(type == 'YOUTUBE'){
			con.css('background-color', '#000');
		}
	},
	
	appendImageInfo: function(fileId, type, vendorData, conSelector) {
		var con = $(conSelector || this.settings.conSelector);
  		con.find(".c-images-csv-input").val(con.find(".c-images-csv-input").val() +","+ fileId);
		con.find(".c-image-types-csv-input").val(con.find(".c-image-types-csv-input").val() +","+ type);
		con.find(".c-image-vendor-data-input").val(con.find(".c-image-vendor-data-input").val() +","+ vendorData);
	},
	
	removeImageInfo: function(fileId, conSelector) {
		var con = $(conSelector || this.settings.conSelector);
		var fileIds = con.find(".c-images-csv-input").val().split(",");
		var types = con.find(".c-image-types-csv-input").val().split(",");
		var vendors = con.find(".c-image-vendor-data-input").val().split(",");
		var idx = jQuery.inArray(fileId, fileIds);
		if(idx < 0) {
			return;
		}
		fileIds.splice(idx, 1);
		types.splice(idx, 1);
		vendors.splice(idx, 1);
		con.find(".c-images-csv-input").val( fileIds.join(",") );
		con.find(".c-image-types-csv-input").val( types.join(",") );
		con.find(".c-image-vendor-data-input").val( vendors.join(",") );
	}
};

my.Application.Tag = function() {
	this.initialize.apply(this, arguments);
};

my.Application.Tag.prototype = {
	settings : {
		separator: ',',
		inputSelector: ".c-community-tags-input",
		btnAddSelector : "#btn-add-community-tag",
		btnRmClass : "cm-remove-tag"
	},
	
	initialize: function() {},

	bindInputBox : function() {
		var context = this;
		var settings = this.settings;
		$(settings.inputSelector).on('keydown', function(e) {
	    	if (e.which == 13) {
	    		context.add($(this).val());
            }
        });
		$(settings.btnAddSelector).on('click', function(e) {
			context.add($(settings.inputSelector).val());
  	    });
		$("."+settings.btnRmClass).on('click', function(e) {
			e.preventDefault();
			context.remove(this);
		});
	},
	add: function(tagName) {
		if(!tagName || tagName == this.settings.separator)
			return false;
		
		var context = this;
		var li = $('<li/>');
		var tagNameA = $('<a href="#" class="cm-tag-name" onclick="return false;"></a>')
			.text(tagName);
		var removeSign = $('<a href="#" class="'+this.settings.btnRmClass+' icon-remove-sign"></a>')
			.on('click', function(e) {
				e.preventDefault();
				context.remove(this);
			});
		$('.cm-tags').append(li.append(tagNameA).append(removeSign));
		
		$('.c-community-tags-input').val(null);
		var tagNames = $('.c-community-tags-csv-input').val().split(this.settings.separator);
		tagNames.push(tagName);
		$('.c-community-tags-csv-input').val( tagNames.join(this.settings.separator) );
	},
	remove: function(tag) {
		var li = $(tag).parents('li').remove();
		var tagName = li.find('.cm-tag-name').text();
		var tagNames = $('.c-community-tags-csv-input').val()
			.split(this.separator);
		tagNames = jQuery.grep(tagNames, function(value) {
	        return value != tagName;
	    });
		$('.c-community-tags-csv-input').val( tagNames.join(this.settings.separator) );
	}
};

my.Map = function(mapElemId, latElemSelector, lonElemSelector, lat, lon, zoom) {
	this.mapElemId = mapElemId || "map_canvas";
	this.latElem = $(latElemSelector);
	this.lonElem = $(lonElemSelector);
	this.lat = lat || 35.6894875;
	this.lon = lon || 139.69170639999993;
	this.zoom = zoom || 15;
	this.map = null;
	this.markers = [];
};

my.Map.prototype = {
	create: function() {
		$('#'+this.mapElemId).show();
		var myLatlng = new google.maps.LatLng(this.lat, this.lon);
		var myOptions = {
			zoom: this.zoom,
			center: myLatlng,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		this.map = new google.maps.Map(document.getElementById(this.mapElemId),myOptions);
	},

	geoFromAddress: function(address, callback) {
		var geo = new google.maps.Geocoder();
		var geoRequest = {
			address:address
		};
		geo.geocode(geoRequest, function(arrGeocoderResult, geocoderStatus) {
			if(geocoderStatus != 'OK') {
				return false;
			}
			var geocoderResult = arrGeocoderResult[0];
			var location = geocoderResult.geometry.location;
			callback(location.lat(), location.lng());
		});
	},

	putMarker: function(lat, lon, draggable) {
		if(lat <= 0 || lon <= 0) {
			return false;
		}
		var myLatlng = new google.maps.LatLng(lat, lon);
		this.map.panTo(myLatlng);
		var marker = new google.maps.Marker({
			map: this.map,
			position: myLatlng
		});
		if(draggable) {
			marker.setDraggable(draggable);
		}
		this.markers.push(marker);
		var _this = this;
		google.maps.event.addListener(marker, 'dragend', function(event) {
			_this.setLatLon(event.latLng.lat(),event.latLng.lng());
		});
		return marker;
	},
	
	removeMarker: function(idx) {
		if(idx in this.markers && this.markers[idx] != null) {
		  this.markers[idx].setMap(null);
		  this.markers[idx] = null;
		}
	},
	
	removeAllMarkers: function() {
		for(var i=0; i < this.markers.length; i++) {
			this.removeMarker(i);
		}
	},

	setLatLon :function(lat, lon) {
		this.latElem.val(lat);
		this.lonElem.val(lon);
	},
	
	clearLatLon: function() {
		this.latElem.val(null);
		this.lonElem.val(null);
	},
	
	putMarkerByAddress :function(address, draggable) {
		if(address == null && address == "") {
			return;
		}
		this.removeAllMarkers();
		this.clearLatLon();
		var _this = this;
		this.geoFromAddress(address, function(lat, lon) {
			_this.putMarker(lat, lon, draggable);
			_this.setLatLon(lat, lon);
		});
	},
	
	clear: function() {
		this.clearLatLon();
		this.removeAllMarkers();
	}
};

my.bootstrap();

