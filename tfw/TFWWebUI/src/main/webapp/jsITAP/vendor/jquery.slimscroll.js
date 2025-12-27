(function($) 
{
	jQuery.fn.extend(
	{
		slimScroll: function(customOptions) 
		{
			var defaults = 
			{
				wheelStep : 20,
				width : 'auto',
				height : 'auto',
				size : '7px',
				color: '#000',
				type:'vertical',// vertical || horizontal
				position : 'right', // top || right || bottom || left
				distance : '1px',
				start : 'top', // top || right || bottom || left
				opacity : .4,
				alwaysVisible : false,
				disableFadeOut: false,
				railVisible : false,
				railColor : '#333',
				railOpacity : '0.2',
				railClass : 'scollRail',
				barClass : 'scrollBar',
				wrapperClass : 'scrollDiv',
				allowPageScroll : false,
				scroll : 0,
				touchScrollStep : 200
			};
			
			var userOptions = $.extend(defaults, customOptions);
			
			
			userOptions.type = (userOptions.type == 'vertical') ? "y" : "x";
			
			// do it for every element that matches selector
			this.each(function()
			{
				// used in event handlers and for better minification       
				var me = $(this);
				
				var isOverPanel, 
					isOverBar, 
					isDragg, 
					queueHide, 
					touchDif, 
					barHeight, 
					barWidth, 
					percentScroll, 
					lastScroll,
					plainDivs = '<div></div>',
					minBarHeight = 30,
					minBarWidth = 30,
					releaseScroll = false;
					
				
				
				// ensure we are not binding it again
				if (me.parent().hasClass('scrollDiv'))
				{
					if(userOptions.type=="y")
					{
						// start from last bar position
						var offset = me.scrollTop();
						
						// find bar and rail
						bar = me.parent().find('.'+userOptions.barClass);
						rail = me.parent().find('.'+userOptions.railClass);
						if(userOptions.type=="y")
							getBarHeight();
						if(userOptions.type=="x")
							getBarWidth();
						
						// check if we should scroll existing instance
						if (customOptions)
						{
							if ('scrollTo' in customOptions)
							{
								// jump to a static point
								offset = parseInt(userOptions.scrollTo);
							}
							else if ('scrollBy' in customOptions)
							{
								// jump by value pixels
								offset += parseInt(userOptions.scrollBy);
							}
							else if ('destroy' in customOptions)
							{
								// remove slimscroll elements
								bar.remove();
								rail.remove();
								//me.removeAttr('style');
								me.unwrap();
								return;
							}
							// scroll content by the given offset
							scrollContent(offset, false, true);
						}
					}
					if(userOptions.type=="x")
					{
						//check if we should scroll existing instance
						if (scroll)
						{
							//find bar and rail
							bar = me.parent().find('.scrollBar');
							rail = me.parent().find('.scrollRail');
							//scroll by given amount of pixels
							scrollContent( me.scrollLeft() + parseInt(scroll), false, true);
						}
					}
					return;
				}
				if(userOptions.type=="y")
				{
					// optionally set height to the parent's height
					userOptions.height = (userOptions.height == 'auto') ? me.parent().innerHeight() : userOptions.height;
				}
						
				// wrap content
				var wrapper = $(plainDivs).addClass(userOptions.wrapperClass).css(
				{
					position: 'relative',
					overflow: 'hidden',
					width: userOptions.width,
					height: userOptions.height
				});
				
				// update style for the div
				me.css(
				{
					overflow: 'hidden',
					width: userOptions.width,
					height: userOptions.height
				});
				
				// create vertical scrollbar rail
				var rail  = $(plainDivs).addClass(userOptions.railClass).css(
				{
					position: 'absolute',
					display: (userOptions.alwaysVisible && userOptions.railVisible) ? 'block' : 'none',
					'border-radius': userOptions.size,
					background: userOptions.railColor,
					opacity: userOptions.railOpacity,
					zIndex: 90
				});
						
				// create scrollbar
				var bar = $(plainDivs).addClass(userOptions.barClass).css(
				{
					background: userOptions.color,
					position: 'absolute',
					opacity: userOptions.opacity,
					display: userOptions.alwaysVisible ? 'block' : 'none',
					'border-radius' : userOptions.size,
					BorderRadius: userOptions.size,
					MozBorderRadius: userOptions.size,
					WebkitBorderRadius: userOptions.size,
					zIndex: 99
				});
				if(userOptions.type=="y")
				{
					rail.css({top:0,width: userOptions.size,height: '100%'});
					bar.css({top:0,width: userOptions.size});
		
					// set position
					var posCss = (userOptions.position == 'right') ? { right: userOptions.distance } : { left: userOptions.distance };
				}
				if(userOptions.type=="x")
				{
					rail.css({bottom:0,height: userOptions.size,width: '100%'});
					bar.css({bottom:0,height: userOptions.size});
					
					// set position
					var posCss = (userOptions.position == 'top') ? { top: userOptions.distance } : { bottom: userOptions.distance };
				}
				rail.css(posCss);
				bar.css(posCss);
						
				// wrap it
				me.wrap(wrapper);
				
				// append to parent div
				me.parent().append(bar);
				me.parent().append(rail);
				
				// make it draggable
				bar.draggable(
				{
					axis: userOptions.type,
					containment: 'parent',
					start: function() { isDragg = true; },
					stop: function() { isDragg = false; hideBar(); },
					drag: function(e)
					{
						if(userOptions.type=="y")
						{
							// scroll content
							scrollContent(0, $(this).position().top, false);
						}
						if(userOptions.type=="x")
						{
							// scroll content
							scrollContent(0, $(this).position().left, false);
						}
					}
				});
						
				// on rail over
				rail.hover(function(){ showBar(); }, function(){ hideBar(); });
				
				// on bar over
				bar.hover(function(){ isOverBar = true; }, function(){ isOverBar = false; });
				
				// show on parent mouseover
				me.hover(function()
				{
					isOverPanel = true;
					showBar();
					hideBar();
				}, function()
				{
					isOverPanel = false;
					hideBar();
				});	
				
				// support for mobile
				me.bind('touchstart', function(e,b)
				{
					if (e.originalEvent.touches.length)
					{
						if(userOptions.type=="y")
						{
							// record where touch started
							touchDif = e.originalEvent.touches[0].pageY;
						}
						if(userOptions.type=="x")
						{
							// record where touch started
							touchDif = e.originalEvent.touches[0].pageX;
						}
					}
				});
				me.bind('touchmove', function(e)
				{
					// prevent scrolling the page
					e.originalEvent.preventDefault();
					if (e.originalEvent.touches.length)
					{
						if(userOptions.type=="y")
						{
							// see how far user swiped
							var diff = (touchDif - e.originalEvent.touches[0].pageY) / userOptions.touchScrollStep;
						}
						if(userOptions.type=="x")
						{
							// see how far user swiped
							var diff = (touchDif - e.originalEvent.touches[0].pageX) / userOptions.touchScrollStep;
						}
						
						// scroll content
						scrollContent(diff, true);
					}
				});
				var _onWheel = function(e)
				{
					// use mouse wheel only when mouse is over
					if (!isOverPanel) { return; }
					var e = e || window.event;
					var delta = 0;
					if (e.wheelDelta) { delta = -e.wheelDelta/120; }
					if (e.detail) { delta = e.detail / 3; }
					
					// scroll content
					scrollContent(delta, true);
					
					// stop window scroll
					if (e.preventDefault && !releaseScroll) { e.preventDefault(); }
					if (!releaseScroll) { e.returnValue = false; }
				}
				function scrollContent(axis, isWheel, isJump)
				{
					var delta = axis;
					var maxTop = me.outerHeight() - bar.outerHeight();
					var maxLeft = me.outerWidth() - bar.outerWidth();
					if(userOptions.type=="x")
					{
						if (bar.css('left') == 'auto') 
						{
							bar.css('left', '0px');
						}
					}
					if (isWheel)
					{
						if(userOptions.type=="y")
						{
							// move bar with mouse wheel
							delta = parseInt(bar.css('top')) + axis * parseInt(userOptions.wheelStep) / 100 * bar.outerHeight();
						
							// move bar, make sure it doesn't go out
							delta = Math.min(Math.max(delta, 0), maxTop);
							
							// scroll the scrollbar
							bar.css({ top: delta + 'px' });
						}
						if(userOptions.type=="x")
						{
							// move bar with mouse wheel
							delta = parseInt(bar.css('left')) + axis * parseInt(userOptions.wheelStep) / 100 * bar.outerWidth();
								
							// move bar, make sure it doesn't go out
							delta = Math.min(Math.max(delta, 0), maxLeft);
							
							// scroll the scrollbar
							bar.css({ left: delta + 'px' });
						}
					}
					if(userOptions.type=="y")
					{
						// calculate actual scroll amount
						percentScroll = parseInt(bar.css('top')) / (me.outerHeight() - bar.outerHeight());
						delta = percentScroll * (me[0].scrollHeight - me.outerHeight());
					}
					if(userOptions.type=="x")
					{
						// calculate actual scroll amount
						percentScroll = parseInt(bar.css('left')) / (me.outerWidth() - bar.outerWidth());
						delta = percentScroll * (me[0].scrollWidth - me.outerWidth());
					}
					if (isJump)
					{
						delta = axis;
						if(userOptions.type=="y")
						{
							var offsetTop = delta / me[0].scrollHeight * me.outerHeight();
							offsetTop = Math.min(Math.max(offsetTop, 0), maxTop);
							bar.css({ top: offsetTop + 'px' });
						}
						if(userOptions.type=="x")
						{
							var offsetLeft = delta / me[0].scrollWidth * me.outerWidth();
							bar.css({ left: offsetLeft + 'px' });
						}
					}
					if(userOptions.type=="y")
					{
						// scroll content
						me.scrollTop(delta);
					}
					if(userOptions.type=="x")
					{
						// scroll content
						me.scrollLeft(delta);
					}
					// ensure bar is visible
					showBar();
					
					// trigger hide when scroll is stopped
					hideBar();
				}
						
				
				var attachWheel = function()
				{
					if (window.addEventListener)
					{
						this.addEventListener('DOMMouseScroll', _onWheel, false );
						this.addEventListener('mousewheel', _onWheel, false );
					}
					else
					{
						document.attachEvent("onmousewheel", _onWheel)
					}
				}
				
				// attach scroll events
				attachWheel();
				
				function getBarHeight()
				{
					// calculate scrollbar height and make sure it is not too small
					barHeight = Math.max((me.outerHeight() / me[0].scrollHeight) * me.outerHeight(), minBarHeight);
					bar.css({ height: barHeight + 'px' });
				}
				
				function getBarWidth()
				{
					// calculate scrollbar width and make sure it is not too small
					barWidth = Math.max((me.outerWidth() / me[0].scrollWidth) * me.outerWidth(), minBarWidth);
					bar.css({ width: barWidth + 'px' });
				}
				
				if(userOptions.type=="y")
				{
					// set up initial height
					getBarHeight();
				}
				if(userOptions.type=="x")
				{
					// set up initial width
					getBarWidth();
				}
				
				function showBar()
				{
					if(userOptions.type=="y")
					{
						// recalculate bar height
						getBarHeight();
					}
					if(userOptions.type=="x")
					{
						// recalculate bar width
						getBarWidth();
					}
					clearTimeout(queueHide);
					
					// when bar reached top or bottom
					if (percentScroll == ~~ percentScroll)
					{
						//release wheel
						releaseScroll = userOptions.allowPageScroll;
						// publish approporiate event
						if (lastScroll != percentScroll)
						{
							if(userOptions.type=="y")
							{
								var msg = (~~percentScroll == 0) ? 'top' : 'bottom';
							}
							if(userOptions.type=="x")
							{
								var msg = (~~percentScroll == 0) ? 'left' : 'right'; 
							}
							me.trigger('slimscroll', msg);
						}
					}
					lastScroll = percentScroll;
					
					if(userOptions.type=="y")
					{
						// show only when required
						if(barHeight >= me.outerHeight()) 
						{
							//allow window scroll
							releaseScroll = true;
							return;
						}
					}
					if(userOptions.type=="x")
					{
						// show only when required
						if(barWidth >= me.outerWidth()) 
						{
							//allow window scroll
							releaseScroll = true;
							return;
						}
					}
					bar.stop(true,true).fadeIn('fast');
					if (userOptions.railVisible) { rail.stop(true,true).fadeIn('fast'); }
				}
				
				function hideBar()
				{
					// only hide when options allow it
					if (!userOptions.alwaysVisible)
					{
						queueHide = setTimeout(function()
						{
							if (!(userOptions.disableFadeOut && isOverPanel) && !isOverBar && !isDragg)
							{
								bar.fadeOut('slow');
								rail.fadeOut('slow');
							}
						}, 1000);
					}
				}
				if(userOptions.type=="y")
				{
					// check start position
					if (userOptions.start == 'bottom')
					{
						// scroll content to bottom
						bar.css({ top: me.outerHeight() - bar.outerHeight() });
						scrollContent(0, true);
					}
					else if (typeof userOptions.start == 'object')
					{
						// scroll content
						scrollContent($(userOptions.start).position().top, null, true);
						
						// make sure bar stays hidden
						if (!userOptions.alwaysVisible) { bar.hide(); }
					}
				}
				else if(userOptions.type=="x")
				{
					// check start position
					if (userOptions.start == 'right')
					{
						// scroll content to right
						bar.css({ left: me.outerWidth() - bar.outerWidth() });
						scrollContent(0, true);
					}
					else if (typeof userOptions.start == 'object')
					{
						// scroll content
						scrollContent($(userOptions.start).position().left, null, true);
						
						// make sure bar stays hidden
						if (!userOptions.alwaysVisible) { bar.hide(); }
					}
				}
					
			});
			
			// maintain chainability
			return this;
		}
	});
	jQuery.fn.extend(
	{
		slimscroll: jQuery.fn.slimScroll
	});
})(jQuery);
