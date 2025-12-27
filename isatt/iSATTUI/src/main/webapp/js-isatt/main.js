$(document)
.ready(
		function() {
			$(".dropdown ").addClass("flagvisibility");
			$(".dropdown dt a").click(function() {
				// $(".dropdown dd ul").toggle();
				$(this).parents().find('dd ul').hide();
				$(this).parent().parent().find('dd ul').toggle();
			});
			$(document).click(function(e) {
				var $clicked = $(e.target);
				if (!$clicked.parents().hasClass("dropdown"))
					$(".dropdown dd ul").hide();
			});

			$(".flipFunctionLink").click(function() {
				$(".flipFunctionContainer").slideDown("slow");
				$(".function_proceed").show();
				$(this).removeClass('countGray');
				$(this).addClass("countBlueRnd");
			});

			$(
			'#selectall_plans,.plans_checklist,#selectall_suites,.suites_checklist,#selectall_scenarios,.scenario_checklist,#selectall_cases,.case_checklist,#selectall_functions,.function_checklist,#selectall_features,.feature_checklist,#selectall_screens,.screen_checklist')
			.prop('checked', false);
			// select all / deselect all---Plan
			$("#selectall_plans").change(
					function() {
						$(".plans_checklist").prop('checked',
								$(this).prop('checked'));
					});

			// find if all the checkboxes are checked, if then select
			// the top one too.
			$(document)
			.on(
					"change",
					".plans_checklist",
					function() {
						if (($(document).find(
						".plans_checklist").length) == ($(
								document).find(
								".plans_checklist:checked").length))
							$("#selectall_plans").attr(
									"checked", "checked");
						else
							$("#selectall_plans").removeAttr(
							"checked");
					});

			// select all / deselect all---Suite
			$("#selectall_suites").change(
					function() {
						$(".suites_checklist").prop('checked',
								$(this).prop('checked'));
					});

			// find if all the checkboxes are checked, if then select
			// the top one too.
			$(document)
			.on(
					"change",
					".suites_checklist",
					function() {
						if ($(".suites_checklist").length == $(".suites_checklist:checked").length)
							$("#selectall_suites").attr(
									"checked", "checked");
						else
							$("#selectall_suites").removeAttr(
							"checked");
					});

			// select all / deselect all---Scenario
			$("#selectall_scenarios").change(
					function() {
						$(".scenario_checklist").prop('checked',
								$(this).prop('checked'));
					});

			// find if all the checkboxes are checked, if then select
			// the top one too.
			$(document)
			.on(
					"change",
					".scenario_checklist",
					function() {
						if ($(".scenario_checklist").length == $(".scenario_checklist:checked").length)
							$("#selectall_scenarios").attr(
									"checked", "checked");
						else
							$("#selectall_scenarios")
							.removeAttr("checked");
					});

			// select all / deselect all---Case
			$("#selectall_cases").change(
					function() {
						$(".case_checklist").prop('checked',
								$(this).prop('checked'));
					});

			// find if all the checkboxes are checked, if then select
			// the top one too.
			$(document)
			.on(
					"change",
					".case_checklist",
					function() {
						if ($(".case_checklist").length == $(".case_checklist:checked").length)
							$("#selectall_cases").attr(
									"checked", "checked");
						else
							$("#selectall_cases").removeAttr(
							"checked");
					});

			// select all / deselect all---Functions
			$("#selectall_functions").change(
					function() {
						$(".function_checklist").prop('checked',
								$(this).prop('checked'));
					});

			// find if all the checkboxes are checked, if then select
			// the top one too.
			$(document)
			.on(
					"change",
					".function_checklist",
					function() {
						if ($(".function_checklist").length == $(".function_checklist:checked").length)
							$("#selectall_functions").attr(
									"checked", "checked");
						else
							$("#selectall_functions")
							.removeAttr("checked");
					});

			// select all / deselect all---Features
			$("#selectall_features").change(
					function() {
						$(".feature_checklist").prop('checked',
								$(this).prop('checked'));
					});

			// find if all the checkboxes are checked, if then select
			// the top one too.
			$(document)
			.on(
					"change",
					".feature_checklist",
					function() {
						if ($(".feature_checklist").length == $(".feature_checklist:checked").length)
							$("#selectall_features").attr(
									"checked", "checked");
						else
							$("#selectall_features")
							.removeAttr("checked");
					});

			// select all / deselect all---Screens
			$("#selectall_screens").change(
					function() {
						$(".screen_checklist").prop('checked',
								$(this).prop('checked'));
					});

			// find if all the checkboxes are checked, if then select
			// the top one too.
			$(document)
			.on(
					"change",
					".screen_checklist",
					function() {
						if ($(".screen_checklist").length == $(".screen_checklist:checked").length)
							$("#selectall_screens").attr(
									"checked", "checked");
						else
							$("#selectall_screens").removeAttr(
							"checked");
					});

			/* Functions and Features */

			var DELAY = 300, clicks = 0, timer = null;

			$(document)
			.on(
					"click",
					"table#functions_table tr",
					function() {
						// $("table#functions_table tbody
						// tr").on("click", function(e){
						// alert('function');
						var _this = $(this);

						clicks++; // count clicks

						if (clicks === 1) {

							timer = setTimeout(
									function() {

										_this
										.parent()
										.find('tr')
										.removeClass(
										"active")
										.end();
										_this
										.addClass("active");
										_this
										.parents()
										.find(
										'.palebutton:eq(0)')
										.css(
												{
													'background-color' : '#0099FF',
													'color' : '#fff'
												}); // perform
										// single-click
										// action

										$(
										'.flipFunctionContainer .palebutton')
										.bind(
												"click",
												function() {
													// $(this).each(function(){
													$(
													"#function_proceed")
													.html(
															$(
																	this)
																	.find(
																	".table_desc")
																	.text())
																	.css(
																			{
																				'color' : '#000'
																			});
													// });
													$(
													".flipFeatureContainer")
													.slideDown(
													"slow");
													$(
													"#feature_proceed")
													.show();
													$(
													".flipFeatureLink")
													.toggleClass(
													"countBlueRnd");
													$(
													".flipFunctionLink")
													.removeClass(
													"countBlueRnd");
													$(
													".flipFunctionLink")
													.addClass(
													"countWhite");
													$(
													".flipFunctionContainer")
													.slideUp(
													"slow");
												});

										clicks = 0; // after
										// action
										// performed,
										// reset
										// counter

									}, DELAY, _this);

						} else {

							clearTimeout(timer); // prevent
							// single-click
							// action

							$('.addingTests').hide();
							$('.editTest').hide();
							var _funcUpDiv = $("#funcUp")
							.show();
							_funcUpDiv.insertAfter(_this); // perform
							// double-click
							// action

							clicks = 0; // after action
							// performed, reset
							// counter
						}

					}).on("dblclick", function(e) {
						e.preventDefault(); // cancel system
						// double-click event
					});

			// $('.flipFunctionContainer
			// .palebutton').bind("click",function(){
			// // $(this).each(function(){
			// $("#function_proceed").html($(this).find(".table_desc").text()).css({'color':'#000'});
			// // });
			// $(".flipFeatureContainer").slideDown("slow");
			// $("#feature_proceed").show();
			// $(".flipFeatureLink").toggleClass("countBlueRnd");
			// $(".flipFunctionLink").removeClass("countBlueRnd");
			// $(".flipFunctionLink").addClass("countWhite");
			// $(".flipFunctionContainer").slideUp("slow");
			// });

			$(document)
			.on(
					"click",
					"table#features_table tr",
					function() {
						// $("#features_table tbody
						// tr").on("click", function(e){
						var _this = $(this);

						clicks++; // count clicks

						if (clicks === 1) {

							timer = setTimeout(
									function() {

										_this
										.parent()
										.find('tr')
										.removeClass(
										"active")
										.end();
										_this
										.addClass("active");
										_this
										.parents()
										.find(
										'.palebutton:eq(0)')
										.css(
												{
													'background-color' : '#0099FF',
													'color' : '#fff'
												}); // perform
										// single-click
										// action

										$(
										'.flipFeatureContainer .palebutton')
										.bind(
												"click",
												function() {
													// $(that).each(function(){
													$(
													"#feature_proceed")
													.html(
															$(
																	this)
																	.find(
																	".table_desc")
																	.text())
																	.css(
																			{
																				'color' : '#000'
																			});
													// });
													$(
													".flipScreenContainer")
													.slideDown(
													"slow");
													$(
													"#screen_proceed")
													.show();
													$(
													".flipScreenLink")
													.toggleClass(
													"countBlueRnd");
													$(
													".flipFeatureLink")
													.removeClass(
													'countBlueRnd');
													$(
													".flipFeatureLink")
													.addClass(
													"countWhite");
													$(
													".flipFeatureContainer")
													.slideUp(
													"slow");
												});
										clicks = 0; // after
										// action
										// performed,
										// reset
										// counter

									}, DELAY, _this);

						} else {

							clearTimeout(timer); // prevent
							// single-click
							// action

							$('.addingTests').hide();
							$('.editTest').hide();
							var _featureUpDiv = $("#featureUp")
							.show();
							_featureUpDiv.insertAfter(_this); // perform
							// double-click
							// action

							clicks = 0; // after action
							// performed, reset
							// counter
						}

					}).on("dblclick", function(e) {
						e.preventDefault(); // cancel system
						// double-click event
					});

			// $('.flipFeatureContainer
			// .palebutton').bind("click",function(){
			// // $(that).each(function(){
			// $("#feature_proceed").html($(this).find(".table_desc").text()).css({'color':'#000'});
			// // });
			// $(".flipScreenContainer").slideDown("slow");
			// $("#screen_proceed").show();
			// $(".flipScreenLink").toggleClass("countBlueRnd");
			// $(".flipFeatureLink").removeClass('countBlueRnd');
			// $(".flipFeatureLink").addClass("countWhite");
			// $(".flipFeatureContainer").slideUp("slow");
			// });

			$(document)
			.on(
					"click",
					"table#screens_table tr",
					function() {
						// $("#screens_table tbody
						// tr").on("click", function(e){
						var _this = $(this);

						clicks++; // count clicks

						if (clicks === 1) {

							timer = setTimeout(
									function() {

										_this
										.parent()
										.find('tr')
										.removeClass(
										"active")
										.end();
										_this
										.addClass("active");
										_this
										.parents()
										.find(
										'.palebutton:eq(0)')
										.css(
												{
													'background-color' : '#0099FF',
													'color' : '#fff'
												}); // perform
										// single-click
										// action

										$(
										'.flipScreenContainer .palebutton')
										.bind(
												"click",
												function() {
													// $(that).each(function(){
													$(
													"#screen_proceed")
													.html(
															$(
																	this)
																	.find(
																	".table_desc")
																	.text())
																	.css(
																			{
																				'color' : '#000'
																			});
													// });
													$(
													".flipScreenLink")
													.removeClass(
													"countBlueRnd");
													$(
													".flipScreenLink")
													.addClass(
													"countWhite");
													$(
													".flipScreenContainer")
													.slideUp(
													"slow");
													$(
													"#functionfeaturesLink")
													.removeClass(
													"selectedTab")
													.parent()
													.find(
													".arrow-top")
													.remove();
													$(
													"#functionfeaturesContent")
													.hide();
													$(
													"#objParamLink")
													.addClass(
													"selectedTab")
													.after(
															$("<div class='arrow-top'></div>"));
													$(
													"#objParamContent")
													.show();
												});
										clicks = 0; // after
										// action
										// performed,
										// reset
										// counter

									}, DELAY, _this);

						} else {

							clearTimeout(timer); // prevent
							// single-click
							// action
							$('.addingTests').hide();
							$('.editTest').hide();
							var _screenUpDiv = $("#screenUp")
							.show();
							_screenUpDiv.insertAfter(_this); // perform
							// double-click
							// action

							clicks = 0; // after action
							// performed, reset
							// counter
						}

					}).on("dblclick", function(e) {
						e.preventDefault(); // cancel system
						// double-click event
					});

			// $('.flipScreenContainer
			// .palebutton').bind("click",function(){
			// // $(that).each(function(){
			// $("#screen_proceed").html($(this).find(".table_desc").text()).css({'color':'#000'});
			// // });
			// $(".flipScreenLink").removeClass("countBlueRnd");
			// $(".flipScreenLink").addClass("countWhite");
			// $(".flipScreenContainer").slideUp("slow");
			// });

			$("#iTap_menus nav ul li")
			.bind(
					"click",
					function() {
						var prev_db = $(
						".menu_link.selectedTab").attr(
						"id");
						prev_db = prev_db.replace('Link',
						'Content');
						$(".menu_link.selectedTab").siblings()
						.remove();
						// $(".menu_link.selectedTab
						// .arrow-top").remove();
						$(".menu_link.selectedTab")
						.removeClass("selectedTab");
						$(this).find('.menu_link').addClass(
						'selectedTab');
						var tab_db = $(this).find('.menu_link')
						.attr('id');
						$("#" + tab_db)
						.after(
								$("<div class='arrow-top'></div>"));
						tab_db = tab_db.replace('Link',
						'Content');
						$("#" + prev_db).hide();
						$("#" + tab_db).show();
						$("#import_popUp").hide();
					});

			$(document).on("click", ".import_link", function() {
				$("#import_popUp").show();
			});

			$('#nav ul li a').append('<span></span>');
			$('#nav ul li a').hover(function() {
				$(this).find('span').animate({
					opacity : 'show',
					top : '-70'
				}, 'slow');
				var hoverTexts = $(this).attr('title');
				$(this).find('span').text(hoverTexts);
			}, function() {
				$(this).find('span').animate({
					opacity : 'hide',
					top : '-90'
				}, 'fast');
			});

			$(".filterchosen").chosen({
				disable_search_threshold : 10
			});

			// $('#rightContents').slimScroll();
			var icons1 = {
					header : "arrow-left",
					activeHeader : "arrow-bottom"
			};

			$("#accordion").accordion({
				header : "h3",
				icons : icons1,
				heightStyle : "content",
				collapsible : true,
				active : false
			});

			$(".addingTestLink").bind(
					"click",
					function() {
						$(".addingTests").hide();
						$(".editTest").hide();
						$(this).parents().eq(2).find(
						".addingTests .update").hide();
						$(this).parents().eq(2).find(
						".addingTests .create").show();
						var newrow = $(this).parents().eq(2).find(
						".addingTests").show();
						$(this).parents().eq(2).find('table tr:first')
						.after(newrow);
						if ((this.id != "addPlans")
								&& (this.id != "addSuites")) {
							// $("#rightContents").slimScroll({scrollTo
							// :
							// $(this).parents().find(".tableCase").height()
							// * 2});
						}
					});

			$(".avail_cancel").bind(
					"click",
					function() {
						$(this).parents().eq(2).find("ul li.selected")
						.removeClass("selected");
						$(this).parents().eq(2).hide();
						$("#import_popUp").find('input:text').val('');
						$("#import_popUp").find('input:file').val('');
					});

			$("#importButton").bind(
					"click",
					function() {
						$(this).parents().eq(2).find("ul li.selected")
						.removeClass("selected");
						$("#import_popUp").hide();
						$("#import_popUp").find('input:text').val('');
						$("#import_popUp").find('input:file').val('');
					});

			$(".tableFunction .cancel").bind(
					"click",
					function() {
						$(document).find(".addingTests ul").children()
						.remove();
						$(document).find('input:radio[name="action"]')
						.prop('checked', false);
						$(document).find('.edit_multiselect').hide();
						$(document).find(".addingTests").hide();
					});

			$(".tableFeature .cancel").bind(
					"click",
					function() {
						$(document).find(".addingTests ul").children()
						.remove();
						$(document).find('input:radio[name="action"]')
						.prop('checked', false);
						$(document).find('.edit_multiselect').hide();
						$(document).find(".addingTests").hide();
					});

			$(".tableScreen .cancel").bind(
					"click",
					function() {
						$(document).find(".addingTests ul").children()
						.remove();
						$(document).find('input:radio[name="action"]')
						.prop('checked', false);
						$(document).find('.edit_multiselect').hide();
						$(document).find(".addingTests").hide();
					});

			$(".tableSchedule .cancel").bind(
					"click",
					function() {
						$(document).find(".addingTests ul").children()
						.remove();
						$(document).find('input:radio[name="action"]')
						.prop('checked', false);
						$(document).find('.edit_multiselect').hide();
						$(document).find(".addingTests").hide();
					});

			// $(document).on("click",".cancel",function(){
			// //$(document).find(".addingTests
			// ul").children().remove();
			// /*$('.tableSchedule').find('form').find('div.singleEdit').find('div.formLeftContent').each(function(){
			// $(this).find('input').not('input["type=button"]').val('');
			// $(this).find('select').val('');
			// $(this).find('input:radio').attr('checked','false');
			// });*/
			// clearForm($('#addTestExectionForm'));
			//		
			// $(".addingTests").hide();
			// });
			//	
			function clearForm(formName) {
				formName.find("input, select, textarea").not(
				'[type=button]').not(".defaultValue").val("")
				.removeAttr("checked");
				formName.find("input, select, textarea").removeClass(
				"notValid");
			}

			// $(document).on("click",".update_plan",function(){
			// $(".editTest").remove();
			// });

			// $(document).on("click",".createPlan",function(){
			// $(".toBeLinked > div:not(.clear) ul").html("");
			// $(".create_New_Plan").show();
			// });

			$(document)
			.on(
					"click",
					".createSuite",
					function() {
						$(".toBeLinked > div:not(.clear) ul")
						.html("");
						$(document)
						.find(
						".create_New_Suite .suites_already_present ul li")
						.remove();
						$('.create_New_Suite').find(
						'.avail_test li').show();
						$('#planUp .linkedSuites ul li')
						.each(
								function(index, value) {
									$(
									'.create_New_Suite')
									.find(
									'div.suites_already_present ul')
									.append(
											'<li>'
											+ value.innerHTML
											+ '</li>');
								});
						$(".create_New_Suite").show();
					});

			$(document)
			.on(
					"click",
					".createSuiteInScenario",
					function() {
						$(".toBeLinked > div:not(.clear) ul")
						.html("");
						$('.create_New_Suite').find(
						'.avail_test li').show();
						$(document)
						.find(
						".create_New_Suite .suites_already_present ul li")
						.remove();
						$('#scenarioUp .linkedSuites ul li')
						.each(
								function(index, value) {
									$(
									'.create_New_Suite')
									.find(
									'div.suites_already_present ul')
									.append(
											'<li>'
											+ value.innerHTML
											+ '</li>');
								});
						$(".create_New_Suite").show();
					});

			$(document)
			.on(
					"click",
					".createPlan",
					function() {
						$(".toBeLinked > div:not(.clear) ul")
						.html("");
						$('.create_New_Plan').find(
						'.avail_test li').show();
						$('#suiteUp .linkedPlans ul li')
						.each(
								function(index, value) {
									$(
									'.create_New_Plan')
									.find(
									'div.plans_already_present ul')
									.append(
											'<li>'
											+ value.innerHTML
											+ '</li>');
								});
						$(".create_New_Plan").show();
					});

			$(document)
			.on(
					"click",
					".createScenario",
					function() {
						$(".toBeLinked > div:not(.clear) ul")
						.html("");
						$('.create_New_Scenario').find(
						'.avail_test li').show();
						$(document)
						.find(
						".create_New_Scenario .scenario_already_present ul li")
						.remove();
						$('#caseUp .linkedScenarios ul li')
						.each(
								function(index, value) {
									$(
									'.create_New_Scenario')
									.find(
									'div.scenario_already_present ul')
									.append(
											'<li>'
											+ value.innerHTML
											+ '</li>');
								});

						$(".create_New_Scenario").fadeIn();
					});

			$(document)
			.on(
					"click",
					".createScenarioInSuite",
					function() {
						$(".toBeLinked > div:not(.clear) ul")
						.html("");
						$('.create_New_Scenario').find(
						'.avail_test li').show();
						$(document)
						.find(
						".create_New_Scenario .scenario_already_present ul li")
						.remove();
						$('#suiteUp .linkedScenarios ul li')
						.each(
								function(index, value) {
									$(
									'.create_New_Scenario')
									.find(
									'div.scenario_already_present ul')
									.append(
											'<li>'
											+ value.innerHTML
											+ '</li>');
								});
						$(".create_New_Scenario").fadeIn();
					});

			$(document)
			.on(
					"click",
					".createCase",
					function() {
						// $(".toBeLinked > div:not(.clear)
						// ul").html("");
						$(document)
						.find(
						".create_New_Case .cases_already_present ul li")
						.remove();
						$('.create_New_Case').find(
						'.avail_test li').show();
						$('#scenarioUp .linkedCases ul li')
						.each(
								function(index, value) {
									$(
									'.create_New_Case')
									.find(
									'div.cases_already_present ul')
									.append(
											'<li>'
											+ value.innerHTML
											+ '</li>');
								});
						$(".create_New_Case").show();
					});

			$(document).on(
					"click",
					".createSteps",
					function() {
						var selectedRunner = '';
						if ($('.tableCase').is(':visible')) {
							selectedRunner = $(this).parents(
							'.tableCase').find(
							'#runnerName_chosen span').text();
						} else if ($('#caseUp').is(':visible')) {
							selectedRunner = $(this).parents('#caseUp')
							.find('#runner_name_chosen span')
							.text();
						}

						// alert(selectedRunner);
						if (selectedRunner === 'JUNIT') {
							$('.testStep_JUnit').show();
						} else if (selectedRunner === 'JAVA') {
							$('.testStep_Java').show();
						} else {
							$('.testStep_HTTP').show();
						}
						$(".toBeLinked > div:not(.clear) ul").html("");
						// $(".create_New_Steps").show();
					});
			
			$(document).on('click', '.chkoutCode', function() { 
				if($(this).val() === "yes"){
					$(this).parent().find('.noSelect').show();
					$(this).parent().find('.noSelectProj').hide();
				}else {
					$(this).parent().find('.noSelectProj').show();
					$(this).parent().find('.noSelect').hide();
				}
				});
			

			$(document).on("click", ".createFeature", function() {
				$(".toBeLinked > div:not(.clear) ul").html("");
				$(".create_New_Feature").show();
			});

			$(document).on("click", ".createScreen", function() {
				$(".toBeLinked > div:not(.clear) ul").html("");
				$(".create_New_Screen").show();
			});

			$(document).on("click", ".createObject", function() {
				$(".toBeLinked > div:not(.clear) ul").html("");
				$(".create_New_Object").show();
			});

			// $("#sidebar,#rightContents,#rightContentWrapper >
			// .scrollDiv").height($(window).height());

			$(window).resize(function() {
				// $("#sidebar,#rightContents,#rightContentWrapper >
				// .scrollDiv").height($(window).height()*0.89);
				// $("#updatingTests").width($("#rightContents").width()*0.97);
				// $('#rightContents').slimScroll("destroy").slimScroll();
			});

			// function oddEvenColor(){
			// $("table").each(function(){
			// $(this).children().find("tr:even").css({
			// 'background': '#efefef'
			// });
			// $(this).children().find("tr:odd").attr("style","background:
			// #efefef;background: -moz-linear-gradient(left, #efefef
			// 0%, #DBDBDB 50%,#efefef 100%);background:
			// -webkit-gradient(linear, left top, right top,
			// color-stop(0%,#efefef), color-stop(50%,#DBDBDB),
			// color-stop(100%,#7db9e8));background:
			// -webkit-linear-gradient(left, #efefef 0%,#DBDBDB
			// 50%,#efefef 100%);background: -o-linear-gradient(left,
			// #efefef 0%,#DBDBDB 50%,#efefef 100%);background:
			// -ms-linear-gradient(left, #efefef 0%,#DBDBDB 50%,#efefef
			// 100%);background: linear-gradient(to right, #efefef
			// 0%,#DBDBDB 50%,#efefef 100%);filter:
			// progid:DXImageTransform.Microsoft.gradient(
			// startColorstr='#efefef',
			// endColorstr='#DBDBDB',GradientType=1 );");
			// });
			// }

			function oddEvenColor() {
				$(".scrollTable table")
				.each(
						function() {
							$(this)
							.children()
							.find("tr:odd")
							.css(
									{
										'background' : '#efefef'
									});
							$(this)
							.children()
							.find("tr:even")
							.attr(
									"style",
							"background: #efefef;background: -moz-linear-gradient(left, #efefef 0%, #DBDBDB 50%,#efefef 100%);background: -webkit-gradient(linear, left top, right top, color-stop(0%,#efefef), color-stop(50%,#DBDBDB), color-stop(100%,#7db9e8));background: -webkit-linear-gradient(left, #efefef 0%,#DBDBDB 50%,#efefef 100%);background: -o-linear-gradient(left, #efefef 0%,#DBDBDB 50%,#efefef 100%);background: -ms-linear-gradient(left, #efefef 0%,#DBDBDB 50%,#efefef 100%);background: linear-gradient(to right, #efefef 0%,#DBDBDB 50%,#efefef 100%);filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#efefef', endColorstr='#DBDBDB',GradientType=1 );");
						});
			}

			$(document).on(
					"click",
					".search",
					function() {
						var currentWidth = $(this).width();

						if (currentWidth !== 150) {
							$(this).parents().find('.activeSearch')
							.removeClass('activeSearch').css(
									"width", "0").end().find(
									'.searchField').hide();
							$(this).animate(
									{
										width : '150px'
									},
									function() {
										$(this)
										.addClass(
										"activeSearch")
										.children("input")
										.show();
									});
						} else if (currentWidth === 150) {
							$(this).animate(
									{
										width : '0px'
									},
									function() {
										$(this).removeClass(
										"activeSearch")
										.children("input")
										.hide();
									});
						}
					});

			$(document).click(function() {
				if ($(".activeSearch").width() == 150) {
					$(".activeSearch").animate({
						width : '0'
					}, function() {
						$(".search").children("input").hide();
						$(".search").removeClass("activeSearch");
					});
				}
			});

			$(document)
			.on(
					"click",
					"#removePlans",
					function() {
						console.log('plan');
						$(this)
						.parents()
						.eq(3)
						.find(
						".test_plans_panel table tr:gt(0) input[type='checkbox']:checked")
						.parent().parent().remove();
						// $(".editTest").remove();
						oddEvenColor();
					});

			$(document)
			.on(
					"click",
					"#removeSuites",
					function() {
						console.log('suite');
						$(this)
						.parents()
						.eq(3)
						.find(
						".test_suites_panel table tr:gt(0) input[type='checkbox']:checked")
						.parent().parent().remove();
						// $(".editTest").remove();
						oddEvenColor();
					});

			$(document)
			.on(
					"click",
					"#removeScenario",
					function() {
						$(this)
						.parents()
						.eq(3)
						.find(
						".test_scenarios_panel table tr:gt(0) input[type='checkbox']:checked")
						.parent().parent().remove();
						// $(".editTest").remove();
						oddEvenColor();
					});

			$(document)
			.on(
					"click",
					"#removeCase",
					function() {
						$(this)
						.parents()
						.eq(3)
						.find(
						".test_case_panel table tr:gt(0) input[type='checkbox']:checked")
						.parent().parent().remove();
						// $(".editTest").remove();
						oddEvenColor();
					});

			$(document)
			.on(
					"click",
					"#removeFunction",
					function() {
						$(this)
						.parents()
						.eq(3)
						.find(
						".test_functions_panel table tr:gt(0) input[type='checkbox']:checked")
						.parent().parent().remove();
						$(".editTest").remove();
						oddEvenColor();
					});

			$(document)
			.on(
					"click",
					"#removeFeature",
					function() {
						$(this)
						.parents()
						.eq(3)
						.find(
						".test_features_panel table tr:gt(0) input[type='checkbox']:checked")
						.parent().parent().remove();
						$(".editTest").remove();
						oddEvenColor();
					});

			$(document)
			.on(
					"click",
					"#removeScreen",
					function() {
						$(this)
						.parents()
						.eq(3)
						.find(
						".test_screens_panel table tr:gt(0) input[type='checkbox']:checked")
						.parent().parent().remove();
						$(".editTest").remove();
						oddEvenColor();
					});

			$(document).on("click", ".searchField", function(e) {
				e.stopPropagation();
			});

			$(".searchField").hide();

			$(document).on("click", ".available ul > li", function() {
				$(this).toggleClass("selected");
			});

			$(document).on("click", ".toBeLinked ul > li", function() {
				$(this).toggleClass("selected");
			});

			$(document).on("click", "countGray", function() {
				$(this).removeClass("countGray");
				$(this).addClass("countBlue");
			});

			$(document)
			.on(
					"click",
					".arrowLeft",
					function() {
						if ($(".available ul > li").hasClass(
						"selected") >= 1) {
							$(".available ul > li.selected")
							.each(
									function(index) {
										var leftText = $("<li>"
												+ $(
														this)
														.text()
														+ "</li>");
										$(
										".toBeLinked ul ")
										.append(
												leftText);
										$(this)
										.parents()
										.eq(2)
										.find(
										"ul li.selected")
										.hide();
										$(this)
										.parents()
										.eq(2)
										.find(
										"ul li.selected")
										.removeClass(
										"selected");
									});
						}
					});

			// $(document).on("click",".arrowLeft",function(){
			//	 		
			// if($(".available ul > li").hasClass("selected") >= 1){
			//				
			// $(".available ul > li.selected" ).each(function( index )
			// {
			// var leftText = $("<li>"+$(this).text()+ "</li>");
			// var cnt=0;
			// for (var i = 0; i < duplicate.length; i++) {
			// if (duplicate[i] == $(this).text())
			// {
			// cnt =1;
			// };
			// }
			// if(cnt == 0){
			// duplicate.push($(this).text());
			// $(".toBeLinked ul ").append(leftText);
			// }
			//	     	
			// $(this).parents().eq(2).find("ul
			// li.selected").removeClass("selected");
			// });
			// }
			// });

			$(document).on(
					"click",
					".create_New_Plan .Link_New_Plan",
					function() {

						if ($("#New_Test_Plan").val().length >= 1) {
							var rightText = $("<li>"
									+ $("#New_Test_Plan").val()
									+ "<span></span></li>");
							$(".toBeLinked ul ").append(rightText);
						}
					});

			$(document).on(
					"click",
					".create_New_Suite .Link_New_Suite",
					function() {
						if ($("#New_Test_Suite").val().length >= 1) {
							var rightText = $("<li>"
									+ $("#New_Test_Suite").val()
									+ "<span></span></li>");
							$(".toBeLinked ul ").append(rightText);
						}
					});

			$(document).on(
					"click",
					".create_New_Scenario .Link_New_Scenario",
					function() {
						if ($("#New_Test_Scenario").val().length >= 1) {
							var rightText = $("<li>"
									+ $("#New_Test_Scenario").val()
									+ "<span></span></li>");
							$(".toBeLinked ul ").append(rightText);
						}
					});

			$(document).on(
					"click",
					".create_New_Case .Link_New_Case",
					function() {
						if ($("#New_Test_Case").val().length >= 1) {
							var rightText = $("<li>"
									+ $("#New_Test_Case").val()
									+ "<span></span></li>");
							$(".toBeLinked ul ").append(rightText);
						}
					});

			$(document).on(
					"click",
					".create_New_Steps .Link_New_Steps",
					function() {
						if ($("#New_Test_Steps").val().length >= 1) {
							var rightText = $("<li>"
									+ $("#New_Test_Steps").val()
									+ "<span></span></li>");
							$(".toBeLinked ul ").append(rightText);
						}
					});

			// $(document).on("click",".arrowRight",function(){
			// if($(".toBeLinked ul > li").hasClass("selected") >= 1){
			// $(".toBeLinked ul > li.selected").each(function( index )
			// {
			// $(".toBeLinked ul > li.selected").remove();
			// });
			// }
			// });
			$(document)
			.on(
					"click",
					".arrowRight",
					function() {
						if ($(".toBeLinked ul > li").hasClass(
						"selected") >= 1) {
							$(".toBeLinked ul > li.selected")
							.each(
									function(index) {
										var rightText = $("<li>"
												+ $(
														this)
														.text()
														+ "</li>");
										$(
										".available ul ")
										.append(
												rightText);
										$(
										".toBeLinked ul > li.selected")
										.remove();
									});
						}
					});

			$(document).on(
					"click",
					".Link_New_Feature",
					function() {
						if ($("#New_Feature_Name").val().length >= 1) {
							var rightText = $("<li>"
									+ $("#New_Feature_Name").val()
									+ "<span></span></li>");
							$(".linkedFeatures ul ").append(rightText)
							.find("span")
							.addClass("floatright").parent();
						}
						$(".linkNewFeatureForm").find('input:text')
						.val('');
						$(".linkNewFeatureForm").find('textarea').val(
						'');

					});

			$(document).on(
					"click",
					".Link_New_Screen",
					function() {
						if ($("#New_Screen_Name").val().length >= 1) {
							var rightText = $("<li>"
									+ $("#New_Screen_Name").val()
									+ "<span></span></li>");
							$(".linkedScreens ul ").append(rightText)
							.find("span")
							.addClass("floatright").parent();
						}
						$(".linkNewScreenForm").find('input:text').val(
						'');
						$(".linkNewScreenForm").find('textarea')
						.val('');
					});

			$(document).on(
					"click",
					".Link_New_Object_Group",
					function() {
						if ($("#New_Object_Name").val().length >= 1) {
							var rightText = $("<li>"
									+ $("#New_Object_Name").val()
									+ "<span></span></li>");
							$(".linkedObjects ul ").append(rightText)
							.find("span")
							.addClass("floatright").parent();
						}
					});

			// $(document).on("click",".addplanData",function(){
			// if($(".create_New_Plan .toBeLinked > div:not(.clear) ul
			// li").text()){
			// var new_plan=$("#New_Test_Plan").val();
			// $("#New_Test_Plan").val("");
			// $(".tableSuite .linkedPlans ul").html("");
			// $(".create_New_Plan .toBeLinked > div:not(.clear) ul
			// li").each(function(){
			// $(this).clone().appendTo(".linkedPlans
			// ul").find("span").addClass("blueBubble
			// floatright").parent().addClass("active");
			// });
			// $('.create_New_Plan ul
			// li.selected').removeClass('selected');
			// $(this).parents().find(".create_New_Plan").hide();
			// $(".toBeLinked ul li").remove();
			// }
			// });

			// $(document).on("click","#addsuiteData",function(){
			// if($(".create_New_Suite .toBeLinked > div:not(.clear) ul
			// li").text()){
			// var new_suite=$("#New_Test_Suite").val();
			// $("#New_Test_Suite").val("");
			// //$(".tableScenario .linkedSuites ul").html("");
			// $(".create_New_Suite .toBeLinked > div:not(.clear) ul
			// li").each(function(){
			// $(this).clone().appendTo(".linkedSuites
			// ul").find("span").addClass("blueBubble
			// floatright").parent().addClass("active");
			// });
			// //$('.create_New_Suite ul
			// li.selected').removeClass('selected');
			// $(this).parents().find(".create_New_Suite").hide();
			// //$(".toBeLinked ul li").remove();
			// }
			// });
			$(document)
			.on(
					"click",
					".addsuiteData",
					function() {
						// if($(".create_New_Suite .toBeLinked >
						// div:not(.clear) ul li").text()){
						$(document).find(".linkedSuites ul li")
						.remove();
						var new_suite = $("#New_Test_Suite")
						.val();
						$("#New_Test_Suite").val("");
						$(
						".create_New_Suite .toBeLinked > div:not(.clear) ul li")
						.each(
								function() {
									// $(this).clone().appendTo(".linkedSuites
									// ul").find("span").addClass("blueBubble
									// floatright").parent().addClass("active");
									$(this)
									.clone()
									.appendTo(
									".linkedSuites ul");
								});

						$(this).parents().find(
						".create_New_Suite").hide();
						// }
					});

			// $(document).on("click",".addplanData",function(){
			// $('#Add_To_Linked_Plans li').each(function(index) {
			// var current = $(this).text();
			// var new_suite_info = $("<li>"+ current + "<span
			// class='floatright' style='display:block;'></span>" +
			// "</li>");
			// $(".tableSuite .linkedPlans ul").append(new_suite_info);
			// });
			// $('.create_New_Plan ul
			// li.selected').removeClass('selected');
			// $(this).parents().find(".create_New_Plan").hide();
			// $(this).parents().find(".placeholder").hide();
			// $(".toBeLinked ul li").remove();
			// });
			/*
			 * $(document).on("click",".addscenarioData",function(){
			 * if($(".create_New_Scenario .toBeLinked > div:not(.clear)
			 * ul li").text()){ var
			 * new_scenario=$("#New_Test_Scenario").val();
			 * $("#New_Test_Scenario").val(""); $(".tableScenario
			 * .linkedScenarios ul").html(""); $(".create_New_Suite
			 * .toBeLinked > div:not(.clear) ul li").each(function(){
			 * $(this).clone().appendTo(".linkedScenarios
			 * ul").find("span").addClass("blueBubble
			 * floatright").parent().addClass("active"); });
			 * $('.create_New_Scenario ul
			 * li.selected').removeClass('selected');
			 * $(this).parents().find(".create_New_Scenario").hide();
			 * $(this).parents().find(".placeholder").hide();
			 * $(".toBeLinked ul li").remove(); } });
			 */
			$(document)
			.on(
					"click",
					".addscenarioData",
					function() {

						$(document).find(
						".linkedScenarios ul li")
						.remove();
						var new_scenario = $(
						"#New_Test_Scenario").val();
						$("#New_Test_Scenario").val("");
						$(
						".create_New_Scenario .toBeLinked > div:not(.clear) ul li")
						.each(
								function() {
									// $(this).clone().appendTo(".linkedSuites
									// ul").find("span").addClass("blueBubble
									// floatright").parent().addClass("active");
									$(this)
									.clone()
									.appendTo(
									".linkedScenarios ul");
								});

						$(this).parents().find(
						".create_New_Scenario").hide();

					});

			/*
			 * $(document).on("click",".addcaseData",function(){
			 * if($(".create_New_Case .toBeLinked > div:not(.clear) ul
			 * li").text()){ var new_case=$("#New_Test_Case").val();
			 * $("#New_Test_Case").val(""); $(".tableCase .linkedCases
			 * ul").html(""); $(".create_New_Case .toBeLinked >
			 * div:not(.clear) ul li").each(function(){
			 * $(this).clone().appendTo(".linkedCases
			 * ul").find("span").addClass("blueBubble
			 * floatright").parent().addClass("active"); });
			 * $('.create_New_Case ul
			 * li.selected').removeClass('selected');
			 * $(this).parents().find(".create_New_Case").hide();
			 * $(".toBeLinked ul li").remove(); } });
			 */
			$(document)
			.on(
					"click",
					".addcaseData",
					function() {
						// if($(".create_New_Suite .toBeLinked >
						// div:not(.clear) ul li").text()){
						$(document).find(".linkedCases ul li")
						.remove();
						var new_case = $("#New_Test_Case")
						.val();
						$("#New_Test_Case").val("");
						$(
						".create_New_Case .toBeLinked > div:not(.clear) ul li")
						.each(
								function() {

									$(this)
									.clone()
									.appendTo(
									".linkedCases ul");
								});

						$(this).parents().find(
						".create_New_Case").hide();
						// }
					});

			$(document)
			.on(
					"click",
					"#addStepData",
					function() {
						if ($(
						".create_New_Steps .toBeLinked > div:not(.clear) ul li")
						.text()) {
							var new_case = $("#New_Test_Steps")
							.val();
							$("#New_Test_Steps").val("");
							$(".tableCase .linkedSteps ul")
							.html("");
							$(
							".create_New_Steps .toBeLinked > div:not(.clear) ul li")
							.each(
									function() {
										$(this)
										.clone()
										.appendTo(
										".linkedSteps ul")
										.find(
										"span")
										.addClass(
										"blueBubble floatright")
										.parent()
										.addClass(
												"active");
									});
							$(
							'.create_New_Steps ul li.selected')
							.removeClass('selected');
							$(this).parents().find(
							".create_New_Steps").hide();
							$(".toBeLinked ul li").remove();
						}
					});

			// $(document).on("click","#addfeatureData",function(){
			// if($(".create_New_Feature .toBeLinked > div:not(.clear)
			// ul li").text()){
			// var new_scenario=$("#New_Feature_Name").val();
			// $("#New_Feature_Name").val("");
			// //$(".tableFunction .linkedFeatures ul").html("");
			// $(".create_New_Feature .toBeLinked > div:not(.clear) ul
			// li").each(function(){
			// $(this).clone().appendTo(".linkedFeatures
			// ul").find("span").addClass("blueBubble
			// floatright").parent().addClass("active");
			// });
			// $('.create_New_Feature ul
			// li.selected').removeClass('selected');
			// $(this).parents().find(".create_New_Feature").hide();
			// $(".toBeLinked ul li").remove();
			// }
			// });

			$(document)
			.on(
					"click",
					"#addfeatureData",
					function() {
						if ($(
						".create_New_Feature .toBeLinked > div:not(.clear) ul li")
						.text()) {
							// $(".tableFunction .linkedFeatures
							// ul").html("");
							$(
							".create_New_Feature .toBeLinked > div:not(.clear) ul li")
							.each(
									function() {
										$(this)
										.clone()
										.appendTo(
										".linkedFeatures ul")
										.find(
										"span")
										.addClass(
										"blueBubble floatright")
										.parent()
										.addClass(
												"active");
									});
							$(
							'.create_New_Feature ul li.selected')
							.removeClass('selected');
							$(this).parents().find(
							".create_New_Feature")
							.hide();
							$(".toBeLinked ul li").remove();
						}
					});

			$(document)
			.on(
					"click",
					"#addscreenData",
					function() {
						if ($(
						".create_New_Screen .toBeLinked > div:not(.clear) ul li")
						.text()) {
							$(".tableFeature .linkedScreens ul")
							.html("");
							$(
							".create_New_Screen .toBeLinked > div:not(.clear) ul li")
							.each(
									function() {
										$(this)
										.clone()
										.appendTo(
										".linkedScreens ul")
										.find(
										"span")
										.addClass(
										"blueBubble floatright")
										.parent()
										.addClass(
												"active");
									});
							$(
							'.create_New_Screen ul li.selected')
							.removeClass('selected');
							$(this).parents().find(
							".create_New_Screen")
							.hide();
							$(".toBeLinked ul li").remove();
						}
					});

			$(document)
			.on(
					"click",
					"#addobjectData",
					function() {
						if ($(
						".create_New_Object .toBeLinked > div:not(.clear) ul li")
						.text()) {
							$(".tableScreen .linkedObjects ul")
							.html("");
							$(
							".create_New_Object .toBeLinked > div:not(.clear) ul li")
							.each(
									function() {
										$(this)
										.clone()
										.appendTo(
										".linkedObjects ul")
										.find(
										"span")
										.addClass(
										"blueBubble floatright")
										.parent()
										.addClass(
												"active");
									});
							$(
							'.create_New_Object ul li.selected')
							.removeClass('selected');
							$(this).parents().find(
							".create_New_Object")
							.hide();
							$(".toBeLinked ul li").remove();
						}
					});

			$(document).on("click", ".create_plan", function() {
				// if((($(".tablePlan input[type=text]").val().length) >
				// 0) && (($(".tablePlan textarea").val().length) > 0)){
				// $(".test_plans_panel table
				// tr").removeClass("active");
				// var newRow= $("<tr class='active'><td
				// class='table_name'><span class='blueBubble
				// floatleft'></span>"+ $(".tablePlan
				// input[type=text]").val() + "</td><td
				// class='table_desc'>"+$(".tablePlan
				// textarea").val()+"</td><td
				// class='table_suite'>"+$(".tablePlan .linkedSuites
				// ul").children().length+"</td>"+"<td
				// class='table_check'><input type='checkbox'
				// name='plan_checklist'
				// class='plans_checklist'/></td></tr>");
				$(this).parents().find(".tablePlan").hide();
				// $(".test_plans_panel table tr:first").after(newRow);
				oddEvenColor();
				$(document).find(".linkedSuites ul li").remove();
				$(".linkedSuites ul li").html('');
				// $(".tablePlan input[type=text]").val("");
				// $(".tablePlan textarea").val("");
				// $(document).find(".linkedSuites ul li").remove();
				// }
			});

			$(document).on("click", ".create_suite", function() {

				// if((($(".tableSuite input[type=text]").val().length)
				// > 0) && (($(".tableSuite textarea").val().length) >
				// 0)){
				// $(".test_suites_panel table
				// tr").removeClass("active");
				// var newRow= $("<tr class='active'><td
				// class='table_name'><span class='blueBubble
				// floatleft'></span>"+ $(".tableSuite
				// input[type=text]").val() + "</td><td
				// class='table_desc'>"+$(".tableSuite
				// textarea").val()+"</td><td
				// class='table_suite'>"+$(".tableSuite .linkedSuites
				// ul").children().length+"</td>"+"<td
				// class='table_check'><input type='checkbox'
				// name='suite_checklist'
				// class='suite_checklist'/></td></tr>");

				$(this).parents().find(".tableSuite").hide();
				// $(".test_suites_panel table tr:first").after(newRow);
				oddEvenColor();
				// $(".tableSuite input[type=text]").val("");
				// $(".tableSuite textarea").val("");
				// }
				$(document).find(".linkedPlans ul li").remove();
				$(document).find(".linkedScenarios ul li").remove();
			});

			$(document).on("click",
					"#testCaseLink, .flipFunctionLink, .palebutton",
					function() {
				oddEvenColor();
			});

			$(document).on(
					"click",
					".Link_New_Suite",
					function() {
						if ($("#New_Test_Plan").val().length >= 1) {
							var rightText = $("<li>"
									+ $("#New_Test_Plan").val()
									+ "</li>");
							$(".toBeLinked ul ").append(rightText);
						}
					});

			// $(document).on("click","#addsuiteData",function(){
			// $('#Add_To_Linked_Suites li').each(function(index) {
			// var current = $(this).text();
			// var new_plan_info = $("<li>"+ current + "<span
			// class='floatright' style='display:block;'></span>" +
			// "</li>");
			// $(".tablePlan .linkedSuites ul").append(new_plan_info);
			// });
			// $(this).parents().find(".create_New_Suite").hide();
			// $(this).parents().find(".placeholder").hide();
			// $(".toBeLinked ul li").remove();
			// });
			$(document)
			.on(
					"click",
					".addplanData",
					function() {
						$(document).find(".linkedPlans ul li")
						.remove();
						$('.Add_To_Linked_Plans li')
						.each(
								function(index) {
									var current = $(
											this)
											.text();
									var new_suite_info = $("<li>"
											+ current
											+ "<span class='floatright' style='display:block;'></span>"
											+ "</li>");
									$(".linkedPlans ul")
									.append(
											new_suite_info);
								});
						$('.create_New_Plan ul li.selected')
						.removeClass('selected');
						$(this).parents().find(
						".create_New_Plan").hide();
						$(this).parents().find(".placeholder")
						.hide();
						$(".toBeLinked ul li").remove();
					});

			// $(document).on("click","#addscenarioData",function(){
			// $('#Add_To_Linked_Scenarios li').each(function(index) {
			// var current = $(this).text();
			// var new_scenario_info = $("<li>"+ current + "<span
			// class='floatright' style='display:block;'></span>" +
			// "</li>");
			// $(".tableScenario .linkedScenarios
			// ul").append(new_scenario_info);
			// });
			// $('.create_New_Scenario ul
			// li.selected').removeClass('selected');
			// $(this).parents().find(".create_New_Scenario").hide();
			// $(".toBeLinked ul li").remove();
			// });

			$(document)
			.on(
					"click",
					".create_scenario",
					function() {
						if ((($(
						".tableScenario input[type=text]")
						.val().length) > 0)
						&& (($(
						".tableScenario textarea")
						.val().length) > 0)) {
							// $(".test_scenarios_panel table
							// tr").removeClass("active");
							// var newRow= $("<tr
							// class='active'><td
							// class='table_name'><span
							// class='blueBubble
							// floatleft'></span>"+
							// $(".tableScenario
							// input[type=text]").val() +
							// "</td><td
							// class='table_desc'>"+$(".tableScenario
							// textarea").val()+"</td><td
							// class='table_plan'>"+$(".tableScenario
							// .linkedSuites
							// ul").children().length+"</td>"+"<td
							// class='table_scenario'>"+$(".tableScenario
							// .linkedCases
							// ul").children().length+"</td>"+"<td
							// class='table_check'><input
							// type='checkbox'
							// name='scenario_checklist'
							// class='scenario_checklist'/></td></tr>");
							$(this).parents().find(
							".tableScenario").hide();
							// $(".test_scenarios_panel table
							// tr:first").after(newRow);
							oddEvenColor();
							// $(".tableScenario
							// input[type=text]").val("");
							// $(".tableScenario
							// textarea").val("");
							$(document).find(
							".linkedSuites ul li")
							.remove();
							$(document).find(
							".linkedCases ul li")
							.remove();
						}
					});

			$(document)
			.on(
					"click",
					".create_case",
					function() {
						console.log('case');
						if ((($(".tableCase input[type=text]")
								.val().length) > 0)
								&& (($(".tableCase textarea")
										.val().length) > 0)) {
							// $(".test_case_panel table
							// tr").removeClass("active");
							// var newRow= $("<tr
							// class='active'><td
							// class='table_name'><span
							// class='blueBubble
							// floatleft'></span>"+
							// $(".tableCase
							// input[type=text]").val() +
							// "</td><td
							// class='table_desc'>"+$(".tableCase
							// textarea").val()+"</td><td
							// class='table_plan'>"+$(".tableCase
							// .linkedScenarios
							// ul").children().length+"</td>"+"<td
							// class='table_scenario'>"+$(".tableCase
							// .linkedSteps
							// ul").children().length+"</td>"+"<td
							// class='table_check'><input
							// type='checkbox'
							// name='case_checklist'
							// class='case_checklist'/></td></tr>");
							$(this).parents()
							.find(".tableCase").hide();
							// $(".test_case_panel table
							// tr:first").after(newRow);
							oddEvenColor();
							// $(".tableCase
							// input[type=text]").val("");
							// $(".tableCase textarea").val("");
							$(document).find(
							".linkedScenarios ul li")
							.remove();
							$(document).find(
							".linkedSteps ul li")
							.remove();
						}
					});

			$(document)
			.on(
					"click",
					".create_function",
					function() {
						if ((($(
						".tableFunction input[type=text]")
						.val().length) > 0)
						&& (($(
						".tableFunction textarea")
						.val().length) > 0)) {
							// $(".test_functions_panel table
							// tr").removeClass("active");
							// var newRow= $("<tr
							// class='active'><td
							// class='table_name'><span
							// class='blueBubble
							// floatleft'></span>"+
							// $(".tableFunction
							// input[type=text]").val() +
							// "</td><td
							// class='table_desc'>"+$(".tableFunction
							// textarea").val()+"</td><td
							// class='table_suite'>"+$(".tableFunction
							// .linkedFeatures
							// ul").children().length+"</td>"+"<td
							// class='table_check'><input
							// type='checkbox'
							// name='function_checklist'
							// class='function_checklist'/></td></tr>");
							$(this).parents().find(
							".tableFunction").hide();
							// $(".test_functions_panel table
							// tr:first").after(newRow);
							oddEvenColor();
							// $(".tableFunction
							// input[type=text]").val("");
							// $(".tableFunction
							// textarea").val("");
							$(".listOfLinkedFeatures").find(
							'ul').html('');
						}
					});

			$(document)
			.on(
					"click",
					".create_feature",
					function() {
						if ((($(
						".tableFeature input[type=text]")
						.val().length) > 0)
						&& (($(".tableFeature textarea")
								.val().length) > 0)) {
							// $(".test_features_panel table
							// tr").removeClass("active");
							// var newRow= $("<tr
							// class='active'><td
							// class='table_name'><span
							// class='blueBubble
							// floatleft'></span>"+
							// $(".tableFeature
							// input[type=text]").val() +
							// "</td><td
							// class='table_desc'>"+$(".tableFeature
							// textarea").val()+"</td><td
							// class='table_suite'>"+$(".tableFeature
							// .linkedScreens
							// ul").children().length+"</td>"+"<td
							// class='table_check'><input
							// type='checkbox'
							// name='feature_checklist'
							// class='feature_checklist'/></td></tr>");
							$(this).parents().find(
							".tableFeature").hide();
							// $(".test_features_panel table
							// tr:first").after(newRow);
							oddEvenColor();
							// $(".tableFeature
							// input[type=text]").val("");
							// $(".tableFeature
							// textarea").val("");
							$(".listOfLinkedScreens")
							.find('ul').html('');
						}
					});

			$(document).on("click", "#createNewTestSteps", function(e) {
				$(".testStep_HTTP").hide();

			});
			
			$(document).on("click", "#createNewTestStepsJunit", function(e) {
				$(".testStep_JUnit").hide();

			});
			
			$(document).on("click", "#createNewTestStepsJava", function(e) {
				$(".testStep_Java").hide();

			});

			$(document)
			.on(
					"click",
					".create_screen",
					function() {
						if ((($(".tableScreen input[type=text]")
								.val().length) > 0)
								&& (($(".tableScreen textarea")
										.val().length) > 0)) {
							$(".test_screens_panel table tr")
							.removeClass("active");
							// var newRow= $("<tr
							// class='active'><td
							// class='table_name'><span
							// class='blueBubble
							// floatleft'></span>"+
							// $(".tableScreen
							// input[type=text]").val() +
							// "</td><td
							// class='table_desc'>"+$(".tableScreen
							// textarea").val()+"</td><td
							// class='table_screen'>"+$(".tableScreen
							// .linkedObjects
							// ul").children().length+"</td>"+"<td
							// class='table_check'><input
							// type='checkbox'
							// name='screen_checklist'
							// class='screen_checklist'/></td></tr>");
							$(this).parents().find(
							".tableScreen").hide();
							// $(".test_screens_panel table
							// tr:first").after(newRow);
							oddEvenColor();
							$(".tableScreen input[type=text]")
							.val("");
							$(".tableScreen textarea").val("");
						}
					});

			$(document).on(
					"dblclick",
					"table#test_plan_table_contents tr",
					function() {
						$('.editTest').hide();
						var _testCaseUpDiv = $('#planUp').show();
						var initial_table_height = $(
						'table#test_plan_table_contents')
						.height()
						- $('#planUp').outerHeight(); // resetting
						// the
						// height
						// of
						// the
						// table
						// tbody
						$('table#test_plan_table_contents tbody').css(
								'max-height', initial_table_height);
						$(document).find(".linkedSuites ul li")
						.remove();
						$(".addingTests").hide();
						var expanded_table_height = $(
						'table#test_plan_table_contents')
						.height()
						+ $('#planUp').outerHeight(); // Adding
						// the
						// planUp's
						// div
						// height
						// to
						// accomodate
						// the
						// div
						// in
						// the
						// table.
						$('table#test_plan_table_contents tbody').css(
								'max-height', expanded_table_height);
						$(this).after(_testCaseUpDiv);
						// _testCaseUpDiv.append($(this));
					});

			/*
			 * Dbl click event for clicking the test plans to show up
			 * Update flow container
			 */
			$(document)
			.on(
					"dblclick",
					"#testPlanSideUp li",
					function() {
						$('.editTest').hide();
						var _testPlanSideUpDiv = $('#planUp')
						.show();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper .test_plans_panel #test_plan_table_contents")
						.hide();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper .test_plans_panel #test_plan_table_contents")
						.before(_testPlanSideUpDiv);
					});

			$(document)
			.on(
					"dblclick",
					"table#test_suite_table_contents tr",
					function() {
						$('.editTest').hide();
						var _testSuiteUpDiv = $('#suiteUp')
						.show();
						var initial_table_height = $(
						'table#test_suite_table_contents')
						.height()
						- $('#suiteUp').outerHeight(); // resetting
						// the
						// height
						// of
						// the
						// table
						// tbody
						$(
						'table#test_suite_table_contents tbody')
						.css('max-height',
								initial_table_height);
						$(document).find(".linkedPlans ul li")
						.remove();
						$(document).find(
						".linkedScenarios ul li")
						.remove();
						$(".addingTests").hide();
						var expanded_table_height = $(
						'table#test_suite_table_contents')
						.height()
						+ $('#suiteUp').outerHeight(); // Adding
						// the
						// suiteUp's
						// div
						// height
						// to
						// accomodate
						// the
						// div
						// in
						// the
						// table.
						$(
						'table#test_suite_table_contents tbody')
						.css('max-height',
								expanded_table_height);
						$(this).after(_testSuiteUpDiv);

						// _testSuiteUpDiv.insertAfter($(this));
					});

			/*
			 * Dbl click event for clicking the test Suite to show up
			 * Update flow container
			 */
			$(document)
			.on(
					"dblclick",
					"#testSuiteSideUp li",
					function() {
						$('.editTest').hide();
						var _testSuiteSideUpDiv = $('#suiteUp')
						.show();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper .test_suites_panel #test_suite_table_contents")
						.hide();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper .test_suites_panel #test_suite_table_contents")
						.before(_testSuiteSideUpDiv);
					});

			$(document)
			.on(
					"dblclick",
					"table#test_scenarios_table_contents tr",
					function() {
						$('.editTest').hide();
						var _testScenariosUpDiv = $(
						'#scenarioUp').show();
						var initial_table_height = $(
						'table#test_scenarios_table_contents')
						.height()
						- $('#scenarioUp')
						.outerHeight(); // resetting
						// the
						// height
						// of
						// the
						// table
						// tbody
						$(
						'table#test_scenarios_table_contents tbody')
						.css('max-height',
								initial_table_height);
						$(document).find(".linkedSuites ul li")
						.remove();
						$(document).find(".linkedCases ul li")
						.remove();
						$(".addingTests").hide();
						var expanded_table_height = $(
						'table#test_scenarios_table_contents')
						.height()
						+ $('#scenarioUp')
						.outerHeight(); // Adding
						// the
						// scenarioUp's
						// div
						// height
						// to
						// accomodate
						// the
						// div
						// in
						// the
						// table.
						$(
						'table#test_scenarios_table_contents tbody')
						.css('max-height',
								expanded_table_height);
						$(this).after(_testScenariosUpDiv);

						// _testScenariosUpDiv.insertAfter($(this));
					});

			/*
			 * Dbl click event for clicking the test Scenario to show up
			 * Update flow container
			 */
			$(document)
			.on(
					"dblclick",
					"#testScenarioSideUp li",
					function() {
						$('.editTest').hide();
						var _testScenarioSideUpDiv = $(
						'#scenarioUp').show();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper .test_scenarios_panel #test_scenarios_table_contents")
						.hide();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper .test_scenarios_panel #test_scenarios_table_contents")
						.before(_testScenarioSideUpDiv);
					});

			$(document).on(
					"dblclick",
					"table#test_cases_table_contents tr",
					function() {
						$('.editTest').hide();
						var _testCasesUpDiv = $('#caseUp').show();
						var initial_table_height = $(
						'table#test_cases_table_contents')
						.height()
						- $('#caseUp').outerHeight(); // resetting
						// the
						// height
						// of
						// the
						// table
						// tbody
						$('table#test_cases_table_contents tbody').css(
								'max-height', initial_table_height);
						$(document).find(".linkedScenarios ul li")
						.remove();
						$(".addingTests").hide();
						var expanded_table_height = $(
						'table#test_cases_table_contents')
						.height()
						+ $('#caseUp').outerHeight();
						; // Adding the caseUp's div height to
						// accomodate the div in the table.
						$('table#test_cases_table_contents tbody').css(
								'max-height', expanded_table_height);
						$(this).after(_testCasesUpDiv);

						// _testCasesUpDiv.insertAfter($(this));
					});

			/*
			 * Dbl click event for clicking the test Cases to show up
			 * Update flow container
			 */
			$(document)
			.on(
					"dblclick",
					"#testCaseSideUp li",
					function() {
						$('.editTest').hide();
						var _testCaseSideUpDiv = $('#caseUp')
						.show();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper #test_cases_table_contents")
						.hide();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper #test_cases_table_contents")
						.before(_testCaseSideUpDiv);
					});

			/* Update for TestPlan,TestSuite,TestScenario and TestCases */

			// $(document).on("click",".test_plans_panel table tbody
			// tr:not('.editTestPlan')", function(e){
			// if ( $(this).data('dblclicked') ) {
			// // double click
			// e.preventDefault();
			//			
			// var
			// _div=$(document).find(this).parents().eq(5).find('#planUp').show();
			//
			// $("<tr class='editTestPlan'><td
			// colspan='3'></td></tr>").insertAfter($(this));
			// $(".editTestPlan > td").html(_div);
			// // $(".editTestPlan > td > div
			// input[type=text]").val($(this).find("td").eq(0).text());
			// // $(".editTestPlan > td > div
			// textarea").val($(this).find("td").eq(1).text());
			//			
			// clearTimeout( $(this).data('clicked'));
			// $(this).data('dblclicked', false);
			// }else{
			// var self = this;
			// $(this).data('dblclicked', true).data('clicked',
			// setTimeout(function(){
			// // single click
			// $(self).data('dblclicked', false);
			// },300));
			// }
			// });
			//	
			// $(document).on("click",".test_suites_panel table tbody
			// tr:not('.editTestSuite')", function(e){
			// if ( $(this).data('dblclicked') ) {
			// // double click
			// e.preventDefault();
			//			
			// var
			// _div=$(document).find(this).parents().eq(5).find('#suiteUp').show();
			//
			// $("<tr class='editTestSuite'><td
			// colspan='3'></td></tr>").insertAfter($(this));
			// $(".editTestSuite > td").html(_div);
			// // $(".editTestSuite > td > div
			// input[type=text]").val($(this).find("td").eq(0).text());
			// // $(".editTestSuite > td > div
			// textarea").val($(this).find("td").eq(1).text());
			//			
			// clearTimeout( $(this).data('clicked'));
			// $(this).data('dblclicked', false);
			// }else{
			// var self = this;
			// $(this).data('dblclicked', true).data('clicked',
			// setTimeout(function(){
			// // single click
			// $(self).data('dblclicked', false);
			// },300));
			// }
			// });
			//	
			// $(document).on("click",".test_scenarios_panel table tbody
			// tr:not('.editTestScenario')", function(e){
			// if ( $(this).data('dblclicked') ) {
			// // double click
			// e.preventDefault();
			//			
			// var
			// _div=$(document).find(this).parents().eq(5).find('#scenarioUp').show();
			//
			// $("<tr class='editTestScenario'><td
			// colspan='3'></td></tr>").insertAfter($(this));
			// $(".editTestScenario > td").html(_div);
			// //$(".editTestScenario > td > div
			// input[type=text]").val($(this).find("td").eq(0).text());
			// //$(".editTestScenario > td > div
			// textarea").val($(this).find("td").eq(1).text());
			//			
			// clearTimeout( $(this).data('clicked'));
			// $(this).data('dblclicked', false);
			// }else{
			// var self = this;
			// $(this).data('dblclicked', true).data('clicked',
			// setTimeout(function(){
			// // single click
			// $(self).data('dblclicked', false);
			// },300));
			// }
			// });
			//	
			// $(document).on("click",".test_case_panel table tbody
			// tr:not('.editTestCase')", function(e){
			// if ( $(this).data('dblclicked') ) {
			// // double click
			// e.preventDefault();
			//			
			// var
			// _div=$(document).find(this).parents().eq(5).find('#caseUp').show();
			//
			// $("<tr class='editTestCase'><td
			// colspan='3'></td></tr>").insertAfter($(this));
			// $(".editTestCase > td").html(_div);
			// //$(".editTestCase > td > div
			// input[type=text]").val($(this).find("td").eq(0).text());
			// //$(".editTestCase > td > div
			// textarea").val($(this).find("td").eq(1).text());
			//			
			// clearTimeout( $(this).data('clicked'));
			// $(this).data('dblclicked', false);
			// }else{
			// var self = this;
			// $(this).data('dblclicked', true).data('clicked',
			// setTimeout(function(){
			// // single click
			// $(self).data('dblclicked', false);
			// },300));
			// }
			// });
			//	
			// /*End of Update for TestPlan,TestSuite,TestScenario and
			// TestCases */
			//	
			// /* Start of update of Funtion , Feature and Screen */
			//	
			// $(document).on("click",".test_functions_panel table tbody
			// tr:not('.editTestFunction')", function(e){
			// if ( $(this).data('dblclicked') ) {
			// // double click
			// e.preventDefault();
			//			
			// var
			// _div=$(document).find(this).parents().eq(5).find('#funcUp').show();
			//
			// $("<tr class='editTestFunction'><td
			// colspan='3'></td></tr>").insertAfter($(this));
			// $(".editTestFunction > td").html(_div);
			// //$(".editTestFunction > td > div
			// input[type=text]").val($(this).find("td").eq(0).text());
			// //$(".editTestFunction > td > div
			// textarea").val($(this).find("td").eq(1).text());
			//			
			// clearTimeout( $(this).data('clicked'));
			// $(this).data('dblclicked', false);
			// }else{
			// var self = this;
			// $(this).data('dblclicked', true).data('clicked',
			// setTimeout(function(){
			// // single click
			// $(self).data('dblclicked', false);
			// },300));
			// $(this).parent().find("tr.active").removeClass("active");
			// $(this).addClass("active");
			// var that = this;
			// $(this).parents().eq(5).find(".palebutton").css({'background-color':'#0099FF','color':'#fff'});
			// $('.flipFunctionContainer
			// .palebutton').bind("click",function(){
			// $(that).each(function(){
			// $("#function_proceed").html($(this).find(".table_desc").text()).css({'color':'#000'});
			// });
			// $(".flipFeatureContainer").slideDown("slow");
			// $("#feature_proceed").show();
			// $(".flipFeatureLink").toggleClass("countBlueRnd");
			// $(".flipFunctionLink").removeClass("countBlueRnd");
			// $(".flipFunctionLink").addClass("countWhite");
			// $(".flipFunctionContainer").slideUp("slow");
			// });
			// }
			// });
			//	
			// $(document).on("click",".test_features_panel table tbody
			// tr:not('.editTestFeature')", function(e){
			// if ( $(this).data('dblclicked') ) {
			// // double click
			// e.preventDefault();
			// var
			// _div=$(document).find(this).parents().eq(5).find('#featureUp').show();
			//
			// $("<tr class='editTestFeature'><td
			// colspan='3'></td></tr>").insertAfter($(this));
			// $(".editTestFeature > td").html(_div);
			// //$(".editTestFeature > td > div
			// input[type=text]").val($(this).find("td").eq(0).text());
			// //$(".editTestFeature > td > div
			// textarea").val($(this).find("td").eq(1).text());
			//			
			// clearTimeout( $(this).data('clicked'));
			// $(this).data('dblclicked', false);
			// }else{
			// var self = this;
			// $(this).data('dblclicked', true).data('clicked',
			// setTimeout(function(){
			// // single click
			// $(self).data('dblclicked', false);
			// },300));
			// $(this).parent().find("tr.active").removeClass("active");
			// $(this).addClass("active");
			// var that = this;
			// $(this).parents().eq(5).find(".palebutton").css({'background-color':'#0099FF','color':'#fff'});
			// $('.flipFeatureContainer
			// .palebutton').bind("click",function(){
			// $(that).each(function(){
			// $("#feature_proceed").html($(this).find(".table_desc").text()).css({'color':'#000'});
			// });
			// $(".flipScreenContainer").slideDown("slow");
			// $("#screen_proceed").show();
			// $(".flipScreenLink").toggleClass("countBlueRnd");
			// $(".flipFeatureLink").removeClass('countBlueRnd');
			// $(".flipFeatureLink").addClass("countWhite");
			// $(".flipFeatureContainer").slideUp("slow");
			// });
			// }
			// });
			//	
			// $(document).on("click",".test_screens_panel table tbody
			// tr:not('.editTestScreen')", function(e){
			// if ( $(this).data('dblclicked') ) {
			// // double click
			// e.preventDefault();
			//			
			// var
			// _div=$(document).find(this).parents().eq(5).find('#screenUp').show();
			//
			// $("<tr class='editTestScreen'><td
			// colspan='3'></td></tr>").insertAfter($(this));
			// $(".editTestScreen > td").html(_div);
			// //$(".editTestScreen > td > div
			// input[type=text]").val($(this).find("td").eq(0).text());
			// //$(".editTestScreen > td > div
			// textarea").val($(this).find("td").eq(1).text());
			//			
			// clearTimeout( $(this).data('clicked'));
			// $(this).data('dblclicked', false);
			// }else{
			// var self = this;
			// $(this).data('dblclicked', true).data('clicked',
			// setTimeout(function(){
			// // single click
			// $(self).data('dblclicked', false);
			// },300));
			// $(this).parent().find("tr.active").removeClass("active");
			// $(this).addClass("active");
			// var that = this;
			// $(".flipScreenLink
			// .countGray").css({'background-color':'#0099FF','color':'#fff'});
			// $(this).parents().eq(5).find(".palebutton").css({'background-color':'#0099FF','color':'#fff'});
			// $('.flipScreenContainer
			// .palebutton').bind("click",function(){
			// $(that).each(function(){
			// $("#screen_proceed").html($(this).find(".table_desc").text()).css({'color':'#000'});
			// });
			// $(".flipScreenLink").removeClass("countBlueRnd");
			// $(".flipScreenLink").addClass("countWhite");
			// $(".flipScreenContainer").slideUp("slow");
			// });
			// }
			// });
			//	
			/* End of update of Funtion , Feature and Screen */

			/* Scheduler Script */

			$(document).on("dblclick",
					".scrollTable_schedule table tr", function() {
				$(".addingTests").hide();
				$('.editTest').hide();
				var _scheduleUpDiv = $('#scheduleUp').show();
				_scheduleUpDiv.insertAfter($(this));
			});

			$(".navBgSchedule ul li").bind(
					"click",
					function() {
						var prev_db1 = $(
						".navBgSchedule ul li.currentTab")
						.attr("id");
						prev_db1 = prev_db1
						.replace('Link', 'Container');
						$(".navBgSchedule ul li.currentTab .blueBar")
						.remove();
						$(".navBgSchedule ul li.currentTab")
						.removeClass("currentTab");
						$(this).addClass("currentTab");
						var tab_db1 = $(this).attr("id");
						$("<div class='blueBar'></div>").appendTo(
								$("#" + tab_db1));
						tab_db1 = tab_db1.replace('Link', 'Container');
						$("#" + prev_db1).hide();
						$("#" + tab_db1).show();
						oddEvenColor();
						oddEvenColor1();
					});

			/*--[Table Scrolling]----*/
			$('.scrollTable_schedule').slimScroll({
				height : '450px',
				width : '100%',
				position : 'right',
				color : '#000',
				size : '2px',
			});

			function oddEvenColor1() {
				$(".scrollTable_schedule table")
				.each(
						function() {
							$(this)
							.children()
							.find("tr:odd")
							.css(
									{
										'background' : '#efefef'
									});
							$(this)
							.children()
							.find("tr:even")
							.attr(
									"style",
							"background: #efefef;background: -moz-linear-gradient(left, #efefef 0%, #DBDBDB 50%,#efefef 100%);background: -webkit-gradient(linear, left top, right top, color-stop(0%,#efefef), color-stop(50%,#DBDBDB), color-stop(100%,#7db9e8));background: -webkit-linear-gradient(left, #efefef 0%,#DBDBDB 50%,#efefef 100%);background: -o-linear-gradient(left, #efefef 0%,#DBDBDB 50%,#efefef 100%);background: -ms-linear-gradient(left, #efefef 0%,#DBDBDB 50%,#efefef 100%);background: linear-gradient(to right, #efefef 0%,#DBDBDB 50%,#efefef 100%);filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#efefef', endColorstr='#DBDBDB',GradientType=1 );");
						});
			}

			$("#datepicker").datepicker();
			$("#datepicker").attr("readonly", "true");
			$(".datepicker").datepicker();

			/* Update Flow of Scheduler start here */

			// $(document).on("click",".scheduleContent table tbody
			// tr:not('.editTest')", function(e){
			// if ( $(this).data('dblclicked') ) {
			// // double click
			// e.preventDefault();
			//			
			// var
			// _div=$(document).find(this).parents().eq(5).find('#scheduleUp').show();
			//
			// $("<tr class='editTest'><td
			// colspan='3'></td></tr>").insertAfter($(this));
			// $(".editTest > td").html(_div);
			// //$(".editTest > td > div
			// input[type=text]").val($(this).find("td").eq(0).text());
			// //$(".editTest > td > div
			// textarea").val($(this).find("td").eq(1).text());
			//			
			// clearTimeout( $(this).data('clicked'));
			// $(this).data('dblclicked', false);
			// }else{
			// var self = this;
			// $(this).data('dblclicked', true).data('clicked',
			// setTimeout(function(){
			// // single click
			// $(self).data('dblclicked', false);
			// },300));
			// }
			// });
			//	
			// $(document).on("click","#scheduleUp .cancel",function(){
			// $(".editTest").remove();
			// });
			/* Update Flow of Scheduler ends here */

			$(document).find('input:radio[name="action"]').prop(
					'checked', false);
			$(document).find('input:radio[name="action"]').prop(
					'checked', false);
			$(document).on(
					"change",
					"input:radio[name='action']",
					function() {
						if ($(document).find(
						'input:radio[name=action]:checked')
						.val() == 'no') {
							$(document).find('.edit_multiselect')
							.hide();
						} else {
							$(document).find('.edit_multiselect')
							.show();
						}
					});
			/*
			 * Add a schedule only in All Tab of the Schedular Nav ('+'
			 * click)
			 */
			$("#addSchedule").bind(
					"click",
					function() {
						if ($(this).parent().parent().find(
						'#allScheduleLink').hasClass(
						'currentTab')) {
							$(".addingTests").hide();
							$("#scheduleUp").hide();
							var newrow = $(this).parents().eq(3).find(
							".addingTests").show();
							$(this).parents().eq(3).find(
							'table tr:first').after(newrow);
							$('.radioCnt').find('input:radio').eq(1)
							.prop("checked", "checked");
						} else {
							return false;
						}
					});

			// $("#addSchedule").bind("click",function(){
			// $(".addingTests").hide();
			// $(".editTest").hide();
			// $(this).parents().eq(3).find(".addingTests
			// .save").hide();
			// $(this).parents().eq(3).find(".addingTests
			// .create_job").show();
			// var
			// newrow=$(this).parents().eq(3).find(".addingTests").show();
			// $(this).parents().eq(3).find('table
			// tr:first').after(newrow);
			// $('.radioCnt').find('input:radio').eq(1).prop("checked","checked");
			// });

			$(document).on("click", ".create_job", function() {
				// var newRow= $("<tr class='active'><td
				// class='schedule_name'><span class='blueBubble
				// floatleft'></span>"+ $(".tableSchedule
				// input[type=text]").val() + "</td><td
				// class='schedule_plan'>"+$("#schplan_name_chosen >a
				// >span").text()+"</td><td
				// class='schedule_agent'>"+$(".tableSchedule
				// #agentName").val()+"</td><td
				// class='schedule_frequency'>"+$("#frequency_name_chosen
				// >a >span").text()+"</td><td
				// class='schedule_status'>"+$(".schedule_status").val()+"</td><td
				// class='schedule_start'>"+$("#datepicker").datepicker("getDate")+"</td><td
				// class='schedule_end'>"+$("#datepicker").datepicker("getDate")+"</td><td
				// class='schedule_run'>"+$(".schedule_run").val()+"</td><td
				// class='schedule_Nextrun'>"+$("#datepicker").datepicker("getDate")+"</td></tr>");

				$(this).parents().find(".tableSchedule").hide();
				clearFormForCreate($('#addTestExectionForm'));

				// $(".allScheduleContainer table
				// tr:first").after(newRow);
				oddEvenColor1();
			});

			function clearFormForCreate(formName) {
				formName.find("input, select, textarea").not(
				'[type=button]').not(".defaultValue").val("")
				.removeAttr("checked");
				formName.find("input, select, textarea").removeClass(
				"notValid");
			}

			$("#application > div > div > a").click(
					function(e) {
						$(document).find("input[type=text]")
						.removeAttr("readonly");
						e.stopPropagation();
					});

			/** * Dashboard ** */
			$(document).on("click", ".applDiv_wrapper", function() {
				$("#landingPage").show();
				$('#menu').show();
				$("#dashboard").hide();
			});

			$(document)
			.on(
					"click",
					"#multiLanesChecked",
					function() {
						var arrGlobal = [];
						var schedulesID = window.scheduleIds;
						$
						.ajax({
							type : "GET",
							url : "views/getAgentData",
							async : false,
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {
								var arr = [];

								$
								.each(
										data,
										function(
												i,
												data) {
											var div_data = "<option value="
												+ data.agentID
												+ ">"
												+ data.agentName
												+ "</option>";
											arr
											.push(div_data);

										});
								arrGlobal.push(arr);

							}
						});

						$
						.ajax({
							type : "POST",
							url : "views/getSchedulerLaneDetailsByScheduleId",
							data : JSON
							.stringify(schedulesID),
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {

								for (var i = 0; i < data.length; i++) {

									var newRow = $(
											'<tr><td class="agentName" id="agentName">'
											+ data[i]["laneUserName"]
											+ '<input type="hidden" id="scheduleLaneID" class="scheduleLaneID" name="scheduleLaneID" data-ng-model="scheduleLaneID" value='
											+ data[i]["scheduleLaneID"]
											+ '></input></td>\
											<td class="laneName" id="laneName">'
											+ data[i]["laneType"]
											+ '</td>\
											<td class="runnerName" id="runnerName"><select class="filterchosen" data-placeholder="Select">'
											+ arrGlobal
											+ '</select></td>\
											<td class="clone" id="clone">'
											+ data[i]["clones"]
											+ '</td>\
											<td class="iterations" id="iterations">'
											+ data[i]["iterations"]
											+ '</td>\
											<td class="ramp" id="ramp">'
											+ data[i]["rampUpDelay"]
											+ '</td>\
											<td class="duration" id="duration">'
											+ data[i]["duration"]
											+ '</td><td class="actions"><span class="removeRow">-</span><span class="addRow">+</span><a href="#" class="edit_test_data_row">Edit</a></td></tr>')
											.attr(
													'type',
											'text');
									$(
									'.edit_multiselect table')
									.append(
											newRow);
									$(".filterchosen")
									.chosen();
								}

							}
						});

					});

			$(document)
			.on(
					"click",
					".addRow",
					function() {
						var arrGlobal = [];

						$
						.ajax({
							type : "GET",
							url : "views/getAgentData",
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {
								var arr = [];

								$
								.each(
										data,
										function(
												i,
												data) {
											var div_data = "<option value="
												+ data.agentID
												+ ">"
												+ data.agentName
												+ "</option>";
											arr
											.push(div_data);

										});
								arrGlobal.push(arr);

								var newRow = $("<tr><td class='agentName'><input type='text' name='laneUser' placeholder='Lane User Name' class='agent' id='laneUser' data-ng-model='laneUser'/></td><td class='laneName'><input type='text' name='laneType' data-ng-model='laneType' class='agent' placeholder='laneType'/></td>\
										<td class='runnerName'><select class='filterchosen' chosen='agentDataList' data-placeholder='Select' data-ng-model='agentID' name='agentID' id='agentID' ng-options='agentData.agentID as agentData.agentName for agentData in agentDataList'>"
										+ arrGlobal
										+ "</select></td>\
										<td class='clone'><input type='text' name='noOfClones' class='agent1' id='cloneName' placeholder='Clones' data-ng-model='noOfClones'/></td>\
										<td class='iterations'><input type='text' name='noOfIterations' placeholder='Iterations' id='iterationName' class='agent1' data-ng-model='noOfIterations'/></td>\
										</td><td class='ramp'><input type='text' name='rampDelay' placeholder='Ramp up Delay' class='agent1' id='rampName' data-ng-model='rampDelay'/></td>\
								<td class='duration'><input type='text' name='duration' placeholder='Duration' class='agent1' id='durationName' data-ng-model='duration'/></td><td class='actions'><span class='removeRow'>-</span><span class='addRow'>+</span></td></tr>");
								$(
								'.edit_multiselect table')
								.append(newRow);
								$(".filterchosen")
								.chosen();

							}
						});
					});

			$(document).on("click", ".removeRow", function() {
				$(this).closest('tr').remove();
			});

			/*--[Table Scrolling]----*/
			$('.scrollTable').slimScroll({
				height : '250px',
				width : '100%',
				position : 'right',
				color : '#000',
				size : '2px'
			});

			/* Obj Grps & Param Grps Landing Page */

			$('.scroll_param').slimScroll({
				height : '550px',
				width : '100%',
				position : 'left',
				color : '#000',
				size : '2px'
			});

			$(".navBgParams ul li").bind(
					"click",
					function() {
						var prev_db2 = $(
						".navBgParams ul li.currentTab").attr(
						"id");
						prev_db2 = prev_db2
						.replace('Link', 'Container');
						$(".navBgParams ul li.currentTab .blueBar")
						.remove();
						$(".navBgParams ul li.currentTab").removeClass(
						"currentTab");
						$(this).addClass("currentTab");
						var tab_db2 = $(this).attr("id");
						$("<div class='blueBar'></div>").appendTo(
								$("#" + tab_db2));
						tab_db2 = tab_db2.replace('Link', 'Container');
						$("#" + prev_db2).hide();
						$("#" + tab_db2).show();
					});

			/** Click on home button to land in the dashboard screen * */
			$(document).on("click", "#home", function() {
				$("#landingPage").hide();
				$('#menu').hide();
				$("#dashboard").show();
				location.reload();
			});

			// $("table.evenodd tr:not([th]):odd").addClass("oddrow");
			// $("table.evenodd tr:not([th]):even").addClass("evenrow");

			/* TEST CASES SCREEN */
			// $('td.table_desc').on({
			// mouseenter: function () {
			// //stuff to do on mouse enter
			// $(this).addClass('description_hovered');
			// },
			// mouseleave: function () {
			// //stuff to do on mouse leave
			// $(this).removeClass('description_hovered');
			// }
			// });
			/* Adding a new Obj/Param Field */
			$("#addObject")
			.bind(
					"click",
					function() {
						// if($(this).parents().eq(2).find(".navBgParams
						// ul li").hasClass("currentTab")){
						$(".editTest").hide();
						$(this).parents()
						.find(".pointing_here")
						.removeClass(".pointing_here")
						.css({
							"margin-bottom" : "0px"
						})
						var currDiv = $(
						".navBgParams ul li.currentTab")
						.attr("id");
						currDiv = currDiv.replace('Link',
						'Container');
						if ($("#" + currDiv).hasClass(
						"windowOpenedAlready")) {
							return false;
						} else {
							var newDiv = $(
							"<div class='panel'><div class='objHeader'></div>")
							.css(
									{
										border : "2px dashed #CBCBCB",
										display : "inline-block",
										margin : "0.5% 0.3% 0.3%",
										width : "24%",
										height : "100px"
									});
							$("#" + currDiv).addClass(
							"windowOpenedAlready")
							.find(".panels").append(
									newDiv);
							$('.panel .objHeader').parent()
							.css({
								"margin-bottom" : 25
							});
							$(".addingObjects > div.arrowdiv")
							.remove();
							$("#" + currDiv).find(
							".addingObjects").show();
							$("<div class='arrowdiv'></div>")
							.css(
									{
										top : $(
										'.panel .objHeader')
										.parent()
										.position().top
										+ $(
												document)
												.find(
														'.panel .objHeader')
														.parent()
														.height()
														+ 25,
														left : $(
																document)
																.find(
																		'.panel .objHeader')
																		.parent()
																		.position().left
																		+ $(
																				document)
																				.find(
																						'.panel .objHeader')
																						.parent()
																						.width()
																						/ 2
																						- 20
									}).prependTo(
									".addingObjects");
						}
						// }
					});

			// $("#addObject").bind("click",function(){
			// //if($(this).parents().eq(2).find(".navBgParams ul
			// li").hasClass("currentTab")){
			// $(".editTest").hide();
			// $(this).parents().find(".pointing_here").removeClass(".pointing_here").css({"margin-bottom":"0px"})
			// var currDiv = $(".navBgParams ul
			// li.currentTab").attr("id");
			// currDiv = currDiv.replace('Link','Container');
			// var newDiv=$("<div class='panel'><div
			// class='objHeader'></div>").css({
			// border: "2px dashed #CBCBCB",
			// display: "inline-block",
			// margin: "0.5% 0.3% 0.3%",
			// width: "24%",
			// height: "100px"
			// });
			// $("#" + currDiv).find(".panels").append(newDiv);
			// $('.panel .objHeader').parent().css(
			// {
			// "margin-bottom": 25
			// });
			// $(".addingObjects > div.arrowdiv").remove();
			// $(".addingObjects").css(
			// {
			// left: $('.panel .objHeader').parent().position().left +
			// $('.panel .objHeader').parent().width()/2 - 20
			// }).show();
			// $("<div class='arrowdiv'></div>").css(
			// {
			// top: $('.panel .objHeader').parent().position().top +
			// $(document).find('.panel .objHeader').parent().height() +
			// 25 ,
			// left: $(document).find('.panel
			// .objHeader').parent().position().left +
			// $(document).find('.panel .objHeader').parent().width()/2
			// - 20
			// }).prependTo(".addingObjects");
			// //}
			// });

			// $(".cancel").bind("click",function(){
			// $(this).parents().eq(2).hide();
			// $(document).find(".panel").remove();
			// });

			$(".cancel").bind(
					"click",
					function() {
						// $(this).parents().eq(2).hide();
						$(this).parents().eq(2).parent().removeClass(
						"windowOpenedAlready");
						$(this).parents().eq(2).parent().find('.panel')
						.remove();
					});

			$(document)
			.on(
					"click",
					".identifierPanel .objHeader",
					function() {
						$(".identifierContent").hide();
						$(this).parent().parent()
						.find(".panel").hide();
						$(this).parents().find(
						'.windowOpenedAlready')
						.removeClass(
						"windowOpenedAlready");
						$(".identifierPanel").removeAttr(
						"style");
						$(this).parent().addClass(
						'pointing_here').css(
								{
									"margin-bottom" : $(
									"#identifierUp")
									.height() + 75
								});
						$("#identifierUp > div.arrowdiv")
						.remove();
						$("#identifierUp").css(
								{
									top : $(this).parent()
									.position().top
									+ $(this).parent()
									.height()
									+ 35
								}).show();
						$("<div class='arrowdiv'></div>").css(
								{
									left : $(this).parent()
									.position().left
									+ $(this).parent()
									.width()
									/ 2 - 20
								}).prependTo("#identifierUp");

					});

			$(document)
			.on(
					"click",
					".objPanel .objHeader",
					function() {
						$(".objectContent").hide();
						$(this).parent().parent()
						.find(".panel").hide();
						$(this).parents().find(
						'.windowOpenedAlready')
						.removeClass(
						"windowOpenedAlready");
						$(".objPanel").removeAttr("style");
						$(this).parent().addClass(
						'pointing_here').css(
								{
									"margin-bottom" : $(
									"#objectUp")
									.height() + 75
								});
						$("#objectUp > div.arrowdiv").remove();
						$("#objectUp").css(
								{
									top : $(this).parent()
									.position().top
									+ $(this).parent()
									.height()
									+ 35
								}).show();
						$("<div class='arrowdiv'></div>").css(
								{
									left : $(this).parent()
									.position().left
									+ $(this).parent()
									.width()
									/ 2 - 20
								}).prependTo("#objectUp");

					});

			$(document)
			.on(
					"click",
					".paramPanel .objHeader",
					function() {
						$(".paramContent").hide();
						$(this).parent().parent()
						.find(".panel").hide();
						$(this).parents().find(
						'.windowOpenedAlready')
						.removeClass(
						"windowOpenedAlready");
						$(".paramPanel").removeAttr("style");
						$(this).parent().addClass(
						'pointing_here').css(
								{
									"margin-bottom" : $(
									"#paramUp")
									.height() + 75
								});
						$("#paramUp > div.arrowdiv").remove();
						$("#paramUp").css(
								{
									top : $(this).parent()
									.position().top
									+ $(this).parent()
									.height()
									+ 35
								}).show();
						$("<div class='arrowdiv'></div>").css(
								{
									left : $(this).parent()
									.position().left
									+ $(this).parent()
									.width()
									/ 2 - 20
								}).prependTo("#paramUp");

					});

			$(document)
			.on(
					"click",
					".conditionPanel .objHeader",
					function() {
						$(".conditionContent").hide();
						$(this).parent().parent()
						.find(".panel").hide();
						$(this).parents().find(
						'.windowOpenedAlready')
						.removeClass(
						"windowOpenedAlready");
						$(".conditionPanel")
						.removeAttr("style");
						$(this).parent().addClass(
						'pointing_here').css(
								{
									"margin-bottom" : $(
									"#conditionUp")
									.height() + 75
								});
						$("#conditionUp > div.arrowdiv")
						.remove();
						$("#conditionUp").css(
								{
									top : $(this).parent()
									.position().top
									+ $(this).parent()
									.height()
									+ 35
								}).show();
						$("<div class='arrowdiv'></div>").css(
								{
									left : $(this).parent()
									.position().left
									+ $(this).parent()
									.width()
									/ 2 - 20
								}).prependTo("#conditionUp");

					});

			/* Adding a row in the Objects Nav */
			$(document)
			.on(
					"click",
					"#editObjectGroup",
					function() {

						$(document).find('.editObjRow')
						.remove();

						var objGrpID = window.objectGroupIDs;
						var appIdForIdentifier = window.appIDs;
						var arrGlobal = [];
						var arrIdenGlobal = [];

						$
						.ajax({
							type : "POST",
							url : "views/getIdentifierTypeByAppIDITAP",
							data : JSON
							.stringify(appIdForIdentifier),
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {

								var arrIden = [];
								$
								.each(
										data,
										function(
												i,
												data) {

											var div_data = "<option value="
												+ data.identifierTypeID
												+ ">"
												+ data.identifierTypeName
												+ "</option>";

											arrIden
											.push(div_data);

										});
								arrIdenGlobal
								.push(arrIden);

							}
						});

						$
						.ajax({
							type : "GET",
							url : "views/getObjTypesITAP",
							async : false,
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {
								var arr = [];

								$
								.each(
										data,
										function(
												i,
												data) {
											var div_data = "<option value="
												+ data.objectTypeID
												+ ">"
												+ data.objectTypeName
												+ "</option>";
											arr
											.push(div_data);

										});
								arrGlobal.push(arr);

							}
						});

						$
						.ajax({
							type : "POST",
							url : "views/editObjectITAP",
							data : JSON
							.stringify(objGrpID),
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {

								for (var i = 0; i < data.length; i++) {

									var editObjGroupRow = '<tr data-ng-controller="addApplicationControllerITAP" class="editObjRow">'
										+

										'<td style="width: 31.7%" id="new_obj_name" >'
										+ data[i]["objectName"]
									+ ' <input type="hidden" id="new_obj_id" class="new_obj_id" name="objectID" data-ng-model="objectID" value='
									+ data[i]["objectID"]
									+ '></input></td>'
									+ '<td style="width: 23%" id="new_obj_desc">'
									+ data[i]["description"]
									+ '</td>'
									+ '<td style="width: 23%"><select class="objectTypeName" data-placeholder="Choose a objectTypeName" chosen="ObjectListITAP" name="objectTypeID" data-ng-model="objectTypeID" ng-options="contact.objectTypeID as contact.objectTypeName for contact in ObjectListITAP">'
									+ arrGlobal
									+ '</select></td>'
									+ '<td style="width: 23%"><select class="identifierTypeName" data-placeholder="Choose a identifierTypeName" chosen="IdentifierTypesBoxList" name="identifierTypeID" data-ng-model="identifierTypeID" ng-options="contact.identifierTypeID as contact.identifierTypeName for contact in IdentifierTypesBoxList">'
									+ arrIdenGlobal
									+ '</select></td>'
									+ '<td><a href="#" class="edit_test_data_row">Edit</a></td>'
									+ '<td><a href="#" class="icon_lightBG minus floatright deleteObjRow"><span>Remove</span></a></td>'
									+ '</tr>';

									$(this)
									.parents(
									'#objectUp')
									.find(
									'.addOldObjectsRowConatiner')
									.hide();
									$(
									'.oldObjGroupTable tr:last')
									.after(
											editObjGroupRow)
											.focus();
									$('.objectTypeName')
									.chosen();
									$(
									'.identifierTypeName')
									.chosen();
								}

							}
						});

					});

			$(document)
			.on(
					"click",
					"#identifier_create",
					function() {
						if ($('#Identifier_Type_Name').val().length > 0) {
							var divContent = $("<div class='identifierPanel floatleft'><div class='objHeader'>"
									+ $('#Identifier_Type_Name')
									.val()
									+ "</div>\
									<div class='baseline'></div><div class='objInfo'><p>"
									+ $('#Identifier_desc')
									.val()
									+ "</p></div></div>");
							// $('#identifierContainer
							// .panels').append(divContent);
							$(document).find('.panel').remove();
							$(
							'#identifierContainer .addingObjects')
							.hide();
							$('#identifierContainer')
							.removeClass(
							"windowOpenedAlready");
							$("#Identifier_Type_Name").val("");
							$("#Identifier_desc").val("");
						}
					});

			$(document)
			.on(
					"click",
					"#object_create",
					function() {
						if ($('#ObjGrpName').val().length > 0) {
							var divContent1 = $("<div class='objPanel floatleft'><div class='objHeader'>"
									+ $('#Object_Group_Name')
									.val()
									+ "</div>\
									<div class='baseline'></div><div class='objInfo'><p>"
									+ $('#Object_desc').val()
									+ "</p></div></div>");
							// $('#objContainer
							// .panels').append(divContent1);
							$(document).find('.panel').remove();
							$('#objContainer .addingObjects')
							.hide();
							$('#objContainer').removeClass(
							"windowOpenedAlready");
							$("#ObjGrpName").val("");
							$("#Object_Group_desc").val("");
						}
					});

			$(document)
			.on(
					"click",
					"#param_create",
					function() {
						if ($('#Param_Group_Name').val().length > 0) {
							var divContent = $("<div class='paramPanel floatleft'><div class='objHeader'>"
									+ $('#Param_Group_Name')
									.val()
									+ "</div>\
									<div class='baseline'></div><div class='objInfo'><p>"
									+ $('#Param_desc').val()
									+ "</p></div></div>");
							// $('#paramContainer
							// .panels').append(divContent);
							$(document).find('.panel').remove();
							$('#paramContainer .addingObjects')
							.hide();
							$('#paramContainer').removeClass(
							"windowOpenedAlready");
							$("#Param_Group_Name").val("");
							$("#Param_Group_desc").val("");
						}
					});

			$(document)
			.on(
					"click",
					"#condition_create",
					function() {
						if ($('#Condition_Group_Name').val().length > 0) {
							var divContent = $("<div class='conditionPanel floatleft'><div class='objHeader'>"
									+ $('#Condition_Group_Name')
									.val()
									+ "</div>\
									<div class='baseline'></div><div class='objInfo'><p>"
									+ $('#Condition_desc')
									.val()
									+ "</p></div></div>");
							// $('#conditionContainer
							// .panels').append(divContent);
							$(document).find('.panel').remove();
							$(
							'#conditionContainer .addingObjects')
							.hide();
							$('#conditionContainer')
							.removeClass(
							"windowOpenedAlready");
							$("#Condition_Group_Name").val("");
							$("#Condition_Group_desc").val("");
						}
					});

			$(document)
			.on(
					"click",
					".addObj",
					function() {

						var arrIdenGlobal = [];
						var arrGlobal = [];

						var appIdForIdentifier = window.appIDs;

						$
						.ajax({
							type : "POST",
							url : "views/getIdentifierTypeByAppIDITAP",
							data : JSON
							.stringify(appIdForIdentifier),
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {
								// console.log("addNewObjectsRowConatiner",data);

								var arrIden = [];
								$
								.each(
										data,
										function(
												i,
												data) {

											var div_data = "<option value="
												+ data.identifierTypeID
												+ ">"
												+ data.identifierTypeName
												+ "</option>";

											arrIden
											.push(div_data);

										});
								arrIdenGlobal
								.push(arrIden);

							}
						});

						$
						.ajax({
							type : "GET",
							url : "views/getObjTypesITAP",

							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {
								var arr = [];

								$
								.each(
										data,
										function(
												i,
												data) {
											var div_data = "<option value="
												+ data.objectTypeID
												+ ">"
												+ data.objectTypeName
												+ "</option>";
											arr
											.push(div_data);

										});
								arrGlobal.push(arr);

								var newObjGroupRow = '<tr data-ng-controller="addApplicationControllerITAP" class="editObjRow">'
									+ '<td><input type="text" id="new_obj_name" class="new_obj_name" placeholder="Enter Object Name" name="objectName" data-ng-model="objectName"></td>'
									+ '<td><input type="text" id="new_obj_desc" class="new_obj_desc" placeholder="Enter Object Description" name="description" data-ng-model="description" ></td>'
									+ '<td><select class="objectTypeName" data-placeholder="Choose a objectTypeName" chosen="ObjectListITAP" name="objectTypeID" data-ng-model="objectTypeID" ng-options="contact.objectTypeID as contact.objectTypeName for contact in ObjectListITAP">'
									+ arr
									+ '</select></td>'
									+ '<td><select class="identifierTypeName" data-placeholder="Choose a identifierTypeName" chosen="IdentifierTypesBoxList" name="identifierTypeID" data-ng-model="identifierTypeID" ng-options="contact.identifierTypeID as contact.identifierTypeName for contact in IdentifierTypesBoxList">'
									+ arrIdenGlobal
									+ '</select></td>'
									+ '<td><a href="#" class="icon_lightBG minus floatright deleteObjRow"><span>Remove</span></a></td>'
									+ '</tr>';

								$(this)
								.parents(
								'#objectUp')
								.find(
								'.addOldObjectsRowConatiner')
								.hide();
								$(
								'.oldObjGroupTable tr:last')
								.after(
										newObjGroupRow)
										.focus();
								$('.identifierTypeName')
								.chosen();
								$('.objectTypeName')
								.chosen();

							}
						});

					});

			var objectNames = [];

			$(document).on(
					"dblclick",
					".editParam",
					function() {
						// $(document).ready(editParam,function() {
						// function editParam(){

						var objGrpId = window["objGrpIDs"];

						$.ajax({
							type : "POST",
							url : "views/getObjsITAP",
							data : JSON.stringify(objGrpId),
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {

								var arr = [];
								$.each(data, function(i, data) {

									var div_data = "<option value="
										+ data.objectID + ">"
										+ data.objectName
										+ "</option>";

									arr.push(div_data);

								});
								objectNames.push(arr);
								editParamName();
							}
						});
					});

			function editParamName() {
				$(".oldParamsGroupTable tr:gt(0)").remove();
				var paramGrpId = window.paramGrpID;
				$
				.ajax({
					type : "POST",
					url : "views/getParamNamesByParamGroupIDITAP",
					data : JSON.stringify(paramGrpId),
					headers : {
						'Content-Type' : 'application/json'
					},
					success : function(data) {

						for (var i = 0; i < data.length; i++) {

							var editParamGroupRow = '<tr>'
								+

								'<td style="width: 31.7%" id="new_param_name" >'
								+ data[i]["paramName"]
							+ '<input type="hidden" class="new_obj_id" value='
							+ data[i]["paramID"]
							+ '></input></td>'
							+ '<td style="width: 23%" id="new_param_desc">'
							+ data[i]["description"]
							+ '</td>'
							+ '<td style="width: 23%"><select class="objectTypeName" data-placeholder="Choose a objectTypeName" "chosen="ObjectListITAP" name="objectTypeID" data-ng-model="objectTypeID" ng-options="contact.objectTypeID as contact.objectTypeName for contact in ObjectListITAP">'
							+ objectNames
							+ '</select></td>'
							+ '<td style="width: 23%" id="new_param_sort">'
							+ data[i]["sortOrder"]
							+ '</td>'
							+ '<td><a href="#" class="edit_test_data_row">Edit</a></td>'
							+ '<td><a href="#" class="icon_lightBG minus floatright deleteObjRow"><span>Remove</span></a></td>'
							+ '</tr>';

							$(this).parents('#paramUp').find(
							'.addOldParamRowConatiner')
							.hide();
							$('.oldParamsGroupTable tr:last')
							.after(editParamGroupRow)
							.focus();
							$('.objectTypeName').chosen();
						}
					}
				});

			}
			/* Adding a row in the Parameters Nav */
			$(document)
			.on(
					"click",
					".addParamNewRow",
					function() {

						var objGrpId = window["objGrpIDs"];

						$
						.ajax({
							type : "POST",
							url : "views/getObjsITAP",
							data : JSON
							.stringify(objGrpId),
							headers : {
								'Content-Type' : 'application/json'
							},

							success : function(data) {

								var object = [];
								$
								.each(
										data,
										function(
												i,
												data) {
											var div_data = "<option value="
												+ data.objectID
												+ ">"
												+ data.objectName
												+ "</option>";
											object
											.push(div_data);

										});
								var newParamGroupRow = '<tr>'
									+ '<td><input type="text" class="new_param_name" placeholder="Username"></td>'
									+ '<td class="paramDesc"><input type="text" class="new_param_desc" placeholder="User name of user attempting login"></td>'
									+ '<td><select class="filterchosen" id="ObjNambyGrp" class="new_param_obj_name">'
									+ object
									+ '</select></td>'
									+ '<td><input type="number" min="0" max="10" step="1" value=" "  class="sort_order" /></td>'
									+ '<td>'
									+ '<a href="#" class="" style="display:inline-block;margin-right:10px">Edit</a>'
									+ '<a href="#" class="deleteParamsRow" style="display="inline-block;">Remove</a>'
									+ '</td>'
									+ '</tr>';
								$(this)
								.parents(
								'#paramUp')
								.find(
								'.addOldParamRowConatiner')
								.hide();
								$(
								'.oldParamsGroupTable tr:last')
								.after(
										newParamGroupRow)
										.focus();
								$('.filterchosen')
								.chosen();
							}
						});

					});

			$(document)
			.on(
					"click",
					".editConditionRow",
					function() {
						// alert("fbgf");
						$(".oldConditionGroupTable tr:gt(0)")
						.remove();

						var cndGrpId = window["cndGrpIDs"];

						$
						.ajax({
							type : "POST",
							url : "views/getConditionsByConditionGrpIDITAP",
							data : JSON
							.stringify(cndGrpId),
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {

								for (var i = 0; i < data.length; i++) {

									var newConditionGroupRow = '<tr>'
										+

										'<td style="width: 31.7%" id="new_cnd_name" >'
										+ data[i]["conditionName"]
									+ '<input type="hidden" class="new_cnd_id" value='
									+ data[i]["conditionID"]
									+ '></input></td>'
									+ '<td style="width: 23%" id="new_cnd_desc">'
									+ data[i]["description"]
									+ '</td>'
									+ '<td style="width: 23%" id="new_cnd_expr">'
									+ data[i]["expression"]
									+ '</td>'
									+ '<td>'
									+ '<a href="#" class="edit_test_data_row" style="display:inline-block;margin-right:10px">Edit</a>'
									+ '<a href="#" class="deleteConditionsRow" style="display="inline-block;">Remove</a>'
									+ '</td>'
									+ '</tr>';
									$(this)
									.parents(
									'#conditionUp')
									.find(
									'.addOldConditionRowConatiner')
									.hide();
									$(
									'.oldConditionGroupTable tr:last')
									.after(
											newConditionGroupRow)
											.focus();
									$('.filterchosen')
									.chosen();
								}
							}
						});

					});
			/* Adding a row in the Condition Nav */
			$(document)
			.on(
					"click",
					".addConditionRow",
					function() {
						var newConditionGroupRow = '<tr>'
							+ '<td><input type="text" class="new_condition_name"></td>'
							+ '<td><input type="text" class="new_condition_desc"></td>'
							+ '<td><input type="text" class="new_condition_expr"></td>'
							+ '<td>'
							+ '<a href="#" class="" style="display:inline-block;margin-right:10px">Edit</a>'
							+ '<a href="#" class="deleteConditionsRow" style="display="inline-block;">Remove</a>'
							+ '</td>' + '</tr>';
						$(this).parents('#conditionUp').find(
						'.addOldConditionRowConatiner')
						.hide();
						$('.oldConditionGroupTable tr:last')
						.after(newConditionGroupRow)
						.focus();
						$('.filterchosen').chosen();
					});

			/*
			 * Removing ALL the rows in the Objects Nav for older
			 * Objects
			 */
			$(document)
			.on(
					"click",
					"#removeObjFunction",
					function() {
						$(this).parents('#objectUp').find(
						'.oldObjGroupTable tr').not(
						':first').remove();
						$(this)
						.parents('#objectUp')
						.find(
						'#demoTable .addOldObjectsRowConatiner')
						.show();
					});

			/* Removing ALL the rows in the Objects Nav for New Objets */
			$(document)
			.on(
					"click",
					".removeNewObjectsRow",
					function() {
						$(this).parents('.objectContent').find(
						'#newObjGroupTable tr').not(
						':first').remove();
						$(this)
						.parents('.objectContent')
						.find(
						'#demoTable .addNewObjectsRowConatiner')
						.show();
					});

			/* Removing a single row in the Attached Objects */
			$(document).on("click", '.deleteObjRow', function() {
				$(this).parent().parent().remove();
			});

			/*
			 * Removing ALL the rows in the Parameters Nav for older
			 * Params
			 */
			$(document).on(
					"click",
					"#removeParamFunction",
					function() {
						$(this).parents('#paramUp').find(
						'.oldParamsGroupTable tr')
						.not(':first').remove();
						$(this).parents('#paramUp').find(
						'#demoTable .addOldParamRowConatiner')
						.show();
					});
			/*
			 * Removing ALL the rows in the Parameters Nav for New
			 * Params
			 */
			$(document).on(
					"click",
					".removeNewParamsRow",
					function() {
						$(this).parents('.paramContent').find(
						'#newParamsGroupTable tr')
						.not(':first').remove();
						$(this).parents('.paramContent').find(
						'#demoTable .addNewParamsRowContainer')
						.show();
					});

			/* Removing a single row in the Attached Objects */
			$(document).on("click", '.deleteParamsRow', function() {
				$(this).parent().parent().remove();
			});

			/*
			 * Removing ALL the rows in the Condition Nav for old
			 * Conditions
			 */
			$(document)
			.on(
					"click",
					"#removeConditionFunction",
					function() {
						$(this).parents('#conditionUp').find(
						'.oldConditionGroupTable tr')
						.not(':first').remove();
						$(this)
						.parents('#conditionUp')
						.find(
						'#demoTable .addOldConditionRowConatiner')
						.show();
					});

			/* Removing a single row in the Attached Condition */
			$(document).on("click", '.deleteConditionsRow', function() {
				$(this).parent().parent().remove();
			});

			$("#objectUp .cancel").bind("click", function() {
				$(".objPanel").removeAttr("style");
				$("#objectUp > div.arrowdiv").remove();
			});

			$("#identifierUp .cancel").bind("click", function() {
				$(".identifierPanel").removeAttr("style");
				$("#identifierUp > div.arrowdiv").remove();
			});

			$("#paramUp .cancel").bind("click", function() {
				$(".paramPanel").removeAttr("style");
				$("#paramUp > div.arrowdiv").remove();
				$(".oldParamsGroupTable tr:gt(0)").remove();
			});

			$("#conditionUp .cancel").bind("click", function() {
				$(".conditionPanel").removeAttr("style");
				$("#conditionUp > div.arrowdiv").remove();
			});

			// $(".cancel").bind("click",function(){
			// $(this).parents().eq(2).hide();
			// $(document).find(".panel").remove();
			// });

			$("#objParamContent .cancel").bind(
					"click",
					function() {
						$(this).parents().eq(2).hide();
						$(this).parents().eq(2).parent().removeClass(
						"windowOpenedAlready");
						$(this).parents().eq(2).parent().find('.panel')
						.remove();
					});

			$("#identifier_update").bind("click", function() {
				$(".identifierPanel").removeAttr("style");
				$("#identifierUp").hide();
			});

			$("#param_update").bind("click", function() {
				$(".paramPanel").removeAttr("style");
				$("#paramUp").hide();
			});

			$("#condition_update").bind("click", function() {
				$(".conditionPanel").removeAttr("style");
				$("#conditionUp").hide();
			});

			$("#object_update").bind("click", function() {
				alert("object_update pop up ");
				$(".objPanel").removeAttr("style");
				$("#objectUp").hide();
				// $("#objectUp > div.arrowdiv").remove();
			});

			$("#funcUp .cancel").bind("click", function() {
				$("#funcUp").hide();
			});

			$("#featureUp .cancel").bind("click", function() {
				$("#featureUp").hide();
			});

			$("#screenUp .cancel").bind("click", function() {
				$("#screenUp").hide();
			});

			$("#planUp .cancel")
			.bind(
					"click",
					function() {
						var table_height_now = $(
						'table#test_plan_table_contents')
						.height()
						- $('#planUp').outerHeight();
						$(
						'table#test_plan_table_contents tbody')
						.css('max-height',
								table_height_now); // setting
						// back
						// the
						// height
						// without
						// planUp
						// div
						$("#planUp").hide();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper .test_plans_panel #test_plan_table_contents")
						.show();
					});

			$("#suiteUp .cancel")
			.bind(
					"click",
					function() {
						var table_height_now = $(
						'table#test_suite_table_contents')
						.height()
						- $('#suiteUp').outerHeight();
						$(
						'table#test_suite_table_contents tbody')
						.css('max-height',
								table_height_now); // setting
						// back
						// the
						// height
						// without
						// suiteUp
						// div
						$("#suiteUp").hide();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper .test_suites_panel #test_suite_table_contents")
						.show();
					});

			$("#scenarioUp .cancel")
			.bind(
					"click",
					function() {
						var table_height_now = $(
						'table#test_scenarios_table_contents')
						.height()
						- $('#scenarioUp')
						.outerHeight();
						$(
						'table#test_scenarios_table_contents tbody')
						.css('max-height',
								table_height_now); // setting
						// back
						// the
						// height
						// without
						// scenarioUp
						// div
						$("#scenarioUp").hide();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper .test_scenarios_panel #test_scenarios_table_contents")
						.show();
					});

			$("#caseUp .cancel")
			.bind(
					"click",
					function() {
						var table_height_now = $(
						'table#test_cases_table_contents')
						.height()
						- $('#caseUp').outerHeight();
						;
						$(
						'table#test_cases_table_contents tbody')
						.css('max-height',
								table_height_now); // setting
						// back
						// the
						// height
						// without
						// caseUp
						// div
						$("#caseUp").hide();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper #test_cases_table_contents")
						.show();
					});

			$("#scheduleUp .cancel").bind("click", function() {
				$("#scheduleUp").hide();
			});

			$(".tablePlan .cancel").bind("click", function() {
				$(".tablePlan").hide();
			});

			$(".tableSuite .cancel").bind("click", function() {
				$(".tableSuite").hide();
			});

			$(".tableScenario .cancel").bind("click", function() {
				$(".tableScenario").hide();
			});

			$(".tableCase .cancel").bind("click", function() {
				$(".tableCase").hide();
			});

			$(".tableFunction .formRightContent .cancel").bind("click",
					function() {
				$(".tableFunction").hide();
			});

			$(".tableFeature .cancel").bind("click", function() {
				$(".tableFeature").hide();
			});

			$(".tableScreen .cancel").bind("click", function() {
				$(".tableScreen").hide();
			});

			/* Add New Object Row */
			$(document)
			.on(
					"click",
					'.addNewObjectsRow',
					function() {

						var arrIdenGlobal = [];
						var appIdForIdentifier = window.appIDs;

						$
						.ajax({
							type : "POST",
							url : "views/getIdentifierTypeByAppIDITAP",
							data : JSON
							.stringify(appIdForIdentifier),
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {
								// console.log("addNewObjectsRowConatiner",data);

								var arrIden = [];
								$
								.each(
										data,
										function(
												i,
												data) {

											var div_data = "<option value="
												+ data.identifierTypeID
												+ ">"
												+ data.identifierTypeName
												+ "</option>";

											arrIden
											.push(div_data);

										});
								arrIdenGlobal
								.push(arrIden);

							}
						});

						$
						.ajax({
							type : "GET",
							url : "views/getObjTypesITAP",

							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {
								var arr = [];

								$
								.each(
										data,
										function(
												i,
												data) {
											var div_data = "<option value="
												+ data.objectTypeID
												+ ">"
												+ data.objectTypeName
												+ "</option>";
											arr
											.push(div_data);

										});

								var newObjGroupRow = '<tr data-ng-controller="addApplicationControllerITAP">'
									+ '<td><input type="text" class="new_obj_name" placeholder="Enter Object Name" name="objectName" data-ng-model="objectName" ></td>'
									+ '<td><input type="text" class="new_obj_desc" placeholder="Enter Object Description" name="description" data-ng-model="description"></td>'
									+ '<td><select class="objectTypeName" data-placeholder="Choose a objectTypeName" chosen="ObjectListITAP" name="objectTypeID" data-ng-model="objectTypeID" ng-options="contact.objectTypeID as contact.objectTypeName for contact in ObjectListITAP">'
									+ arr
									+ '</select></td>'
									+ '<td><select class="identifierTypeName" data-placeholder="Choose a identifierTypeName" chosen="IdentifierTypesBoxList" name="identifierTypeID" data-ng-model="identifierTypeID" ng-options="contact.identifierTypeID as contact.identifierTypeName for contact in IdentifierTypesBoxList">'
									+ arrIdenGlobal
									+ '</select></td>'
									+ '</tr>';

								$(this)
								.parents()
								.find(
								'.addNewObjectsRowConatiner')
								.hide();
								$(
								'#newObjGroupTable tr:last')
								.after(
										newObjGroupRow)
										.focus();
								$('.identifierTypeName')
								.chosen();
								$('.objectTypeName')
								.chosen();

							}
						});

					});

			/* Add New Parameters Row */
			$(document)
			.on(
					"click",
					'.addNewParamsRow',
					function() {
						var objGrpId = window.objGrpID;
						$
						.ajax({
							type : "POST",
							url : "views/getObjsITAP",
							data : JSON
							.stringify(objGrpId),
							headers : {
								'Content-Type' : 'application/json'
							},

							success : function(data) {
								var arr = [];
								$
								.each(
										data,
										function(
												i,
												data) {
											var div_data = "<option value="
												+ data.objectID
												+ ">"
												+ data.objectName
												+ "</option>";
											arr
											.push(div_data);

										});
								var newParamGroupRow = '<tr">'
									+ '<td><input type="text" class="new_param_name" name="paramName" data-ng-model="paramName" placeholder="param name"></td>'
									+ '<td><input type="text" class="new_obj_desc" name="paramDesc" data-ng-model="paramDesc" placeholder="description"></td>'
									+ '<td><select class="filterchosen" class="new_param_obj_name" name="objectNameId" chosen="ObjectITAP" data-ng-model="objectNameId" ng-options="contact.objectID as contact.objectName for contact in ObjectITAP">'
									+ arr
									+ '</select></td>'
									+ '<td><input type="number" class="sort_order" id="sortOrder" name="sortOrder" placeholder="enter sort order number" min="1" max="1000" required data-ng-model="sortOrder"></select></td>'
									+ '</tr>';
								$(this)
								.parents()
								.find(
								'.addNewParamsRowContainer')
								.hide();
								$(
								'#newParamsGroupTable tr:last')
								.after(
										newParamGroupRow)
										.focus();
								$('.filterchosen')
								.chosen();
							}
						});
					});

			/* Add New Conditions Row */
			$(document)
			.on(
					"click",
					'.addNewConditionRow',
					function() {
						var newConditionGroupRow = '<tr>'
							+ '<td><input type="text" class="new_condition_name"></td>'
							+ '<td><input type="text" class="new_condition_desc"></td>'
							+ '<td><input type="text" class="new_condition_expr"></td>'
							+ '</tr>';
						$(this).parents().find(
						'.addNewConditionRowContainer')
						.hide();
						$('#newConditionGroupTable tr:last')
						.after(newConditionGroupRow)
						.focus();
						$('.filterchosen').chosen();
					});

			/***********************************************************
			 * 
			 * TEST DATA
			 * 
			 **********************************************************/

			$(".navTestData ul li").bind(
					"click",
					function() {
						var prev_db2 = $(
						".navTestData ul li.currentTab").attr(
						"id");
						prev_db2 = prev_db2
						.replace('Link', 'Container');
						$(".navTestData ul li.currentTab .blueBar")
						.remove();
						$(".navTestData ul li.currentTab").removeClass(
						"currentTab");
						$(this).addClass("currentTab");
						var tab_db2 = $(this).attr("id");
						$("<div class='blueBar'></div>").appendTo(
								$("#" + tab_db2));
						tab_db2 = tab_db2.replace('Link', 'Container');
						$("#" + prev_db2).hide();
						$("#" + tab_db2).show();
					});

			/* Add New Test Data */
			$(document).on("click", ".addNewtestData", function() {
				$('#editableTestDataContainer').hide();
				$('.new_test_data_desc').val('');
				$('.newTestData').fadeIn();
				$(".new_test_data_select").chosen();
			});

			var paramGrpId = [];

			/* Add new Param Row */
			$(document)
			.on(
					"click",
					".addNewParamRow",
					function() {
						var appId = window.appIDs;

						$
						.ajax({
							type : "POST",
							url : "views/getParamGroupNamesByAppIdITAP",
							data : JSON
							.stringify(appId),
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {
								var arr = [];
								$
								.each(
										data,
										function(
												i,
												data) {
											var div_data = "<option value="
												+ data.paramGroupID
												+ ">"
												+ data.paramGroupName
												+ "</option>";
											arr
											.push(div_data);

										});

								paramGrpId.push(arr);
								var newParamRow = '<tr id="paramTableRow">'
									+ '<td class="para_group_name">'
									+ '<select class="filterchosen paramGroupCls" data-placeholder="Select">'
									+ arr
									+ '</select>'
									+
									// '<option></option>'
									// +

									'</td>'
									+ '<td class="para_name">'
									+ '<select class="parameterNames" id="parameterNames" data-placeholder="Select">'
									+ '<option></option>'
									+ '</select>'
									+ '</td>'
									+ '<td><input type="text" class="para_value_new" placeholder="Value" /></td>'
									+ '<td><input type="text" placeholder="Value Big" class="value_big_new"/></td>'
									+ '<td style="width:23%">'
									+ '<a href="#" class="edit_test_data_row">Edit</a>'
									+ '<a href="#" class="minus remove_test_data_row">Remove</a></td>'
									+ '</tr>';

								$(this)
								.parents()
								.find(
								'.addNewParamTestDataRow')
								.hide();
								$(
								'#newTestDataParamTable tr:last')
								.before(
										newParamRow)
										.focus();
								$('.filterchosen')
								.chosen();
								$('.parameterNames')
								.chosen();

							}
						});

					});

			var tempRow;
			var temp = [];
			$(document)
			.on(
					"change",
					".paramGroupCls",
					function() {
						tempRow = $(this);
						var pgID = $(this).val();

						$
						.ajax({
							type : "POST",
							url : "views/getParamNamesByParamGroupIDITAP",
							data : JSON.stringify(pgID),
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {

								var arr1 = [];
								$
								.each(
										data,
										function(
												i,
												data) {
											var div_data = "<option value="
												+ data.paramID
												+ ">"
												+ data.paramName
												+ "</option>";
											arr1
											.push(div_data);

										});
								temp = arr1;
								$(tempRow)
								.parent()
								.parent()
								.find(
								'td.para_name')
								.find('select')
								.find('option')
								.remove();

								$(tempRow)
								.parent()
								.parent()
								.find(
								'td.para_name')
								.find('select')
								.html(arr1);
								// $(tempRow).parent().parent().find('td.para_name').find('select').show();
								$(tempRow)
								.parents()
								.find(
								'.addNewParamTestDataRow')
								.hide();

								$('.filterchosen')
								.chosen();
								$('.parameterNames')
								.chosen();
								$(".parameterNames")
								.trigger(
								"chosen:updated");

							}
						});
					});

			/* Test Data */

			$(document).on("click", ".addNewtestData", function() {
				$('#editableTestDataContainer').hide();
				$('.new_test_data_desc').val('');
				$('.newTestData').fadeIn();
			});

			$(document).on("click", "#testDataLink", function() {
				$('#activeTestDataContainer').hide();
				$('#inactiveTestDataContainer').hide();
				$('#activeBlueBar').hide();
				$('#inactiveBlueBar').hide();
				$('#allTestDataContainer').show();
				$('#allBlueBar').show();
			});

			$(document).on("click", "#allTestLink", function() {
				$('#allTestDataContainer').show();
				$('#activeTestDataContainer').hide();
				$('#inactiveTestDataContainer').hide();
				$('#activeBlueBar').hide();
				$('#inactiveBlueBar').hide();
				$('#allBlueBar').show();
			});

			$(document).on("click", "#activeTestLink", function() {
				$('#allTestDataContainer').hide();
				$('#activeTestDataContainer').show();
				$('#inactiveTestDataContainer').hide();
				$('#activeBlueBar').show();
				$('#inactiveBlueBar').hide();
				$('#allBlueBar').hide();

			});

			$(document).on("click", "#inactiveTestLink", function() {
				$('#allTestDataContainer').hide();
				$('#activeTestDataContainer').hide();
				$('#inactiveTestDataContainer').show();
				$('#activeBlueBar').hide();
				$('#inactiveBlueBar').show();
				$('#allBlueBar').hide();

			});

			$(document)
			.on(
					"click",
					".save_new_test_data",
					function() {
						var new_data_desc = $(
						'.new_test_data_desc').val();
						var new_data_status = $(
						'.new_test_data_select option:selected')
						.val();
						if (new_data_desc.length > 0) {
							var newtestDataRow = '<div class="testData floatleft">'
								+ '<div><span class="indicator gray"></span><span>'
								+ new_data_desc
								+ '</span></div>'
								+ '</div>';
							dataTestObject = $('<div/>').html(
									newtestDataRow).contents();

							if (new_data_status === "Active") {
								dataTestObject.find(
								'.indicator')
								.removeClass('gray')
								.addClass('green');
							} else if (new_data_status === "Inactive") {
								dataTestObject
								.find('indicator')
								.removeClass('green')
								.addClass('gray');
							}

							$('.newTestData').hide();
							$('.new_test_data_desc').val(' ');
							// $('#testDataContainer').fadeIn().append(dataTestObject);
						}
					});

			/* Cancel New Test Data */
			$(document).on("click", ".cancel_new_test_data",
					function() {
				$('.newTestData').fadeOut();
			});

			$(document).on("click", "#home", function() {
				$("#landingPage").fadeOut();
				$("#dashboard").show();
			});
			/* On Test Data double click event */

			// $(document).on("click",".testData", function (e) {
			// e.preventDefault();
			// //$('#testStepName').val(' ');
			// $('.newTestData').hide();
			// $(".filterchosen").chosen();
			// $('.para_name select').chosen();
			// $('#editableTestDataContainer').fadeIn();
			// });
			$(document)
			.on(
					"click",
					".testData > div",
					function(e) {
						e.preventDefault();
						$('.newTestData').hide();
						$(".testData > div").parent()
						.removeAttr("style");
						$(this)
						.parent()
						.addClass('pointing_here')
						.css(
								{
									"margin-bottom" : $(
									"#editableTestDataContainer")
									.height() + 75
								});
						$(
						"#editableTestDataContainer > div.arrowdiv")
						.remove();
						$("#editableTestDataContainer").css(
								{
									top : $(this).parent()
									.position().top
									+ $(this).parent()
									.height()
									+ 35
								}).fadeIn();
						if ($(this).find(".indicator")
								.hasClass("gray")) {
							$(
							"#editableTestDataContainer .inactiveStatus")
							.prop("checked", true);
						} else if ($(this).find(".indicator")
								.hasClass("green")) {
							$(
							"#editableTestDataContainer .activeStatus")
							.prop("checked", true);
						}
						$("<div class='arrowdiv'></div>").css(
								{
									left : $(this).parent()
									.position().left
									+ $(this).parent()
									.width()
									/ 2 - 20
								}).prependTo(
								"#editableTestDataContainer");
					});

			$(document).on("click", ".cancel_test_data", function() {
				// $(".objPanel").removeAttr("style");
				$('#editableTestDataContainer').fadeOut();
			});

			$(".filterchosen").chosen();
			// $('.para_group_name select').chosen();
			$('.para_name select').chosen();

			/* Edit test Data Defination based on Test Param Data */
			$(document)
			.on(
					"click",
					"#editTestDataDefnRow",
					function() {

						$(document).find('.editTestDataRow')
						.remove();

						var appId = window.appIDs;
						var globalParamGrp = [];
						$
						.ajax({
							type : "POST",
							url : "views/getParamGroupNamesByAppIdITAP",
							data : JSON
							.stringify(appId),
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {
								var arr = [];
								$
								.each(
										data,
										function(
												i,
												data) {
											var div_data = "<option value="
												+ data.paramGroupID
												+ ">"
												+ data.paramGroupName
												+ "</option>";
											arr
											.push(div_data);

										});

								globalParamGrp
								.push(arr);

							}
						});

						var testDataIds = window.testDataIDsGlb;
						$
						.ajax({
							type : "POST",
							url : "views/getParameterForTestDataITAP",
							data : JSON
							.stringify(testDataIds),
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {

								for (var i = 0; i < data.length; i++) {

									var newTestDataTableRow = '<tr class="editTestDataRow">'
										+ '<td class="para_group_name">'
										+ '<select class="filterchosen paramGroupCls" data-placeholder="Select">'
										+ globalParamGrp
										+ '</select>'
										+ '</td>'
										+ '<td class="para_name">'
										+ '<select class="parameterNames" data-placeholder="Select">'
										+ '<option></option>'
										+ '</select>'
										+ '</td>'
										+ '<td class="para_value" id="edit_para_value">'
										+ data[i]["paramValue"]
									+ ' <input type="hidden" id="testParamDataID" class="testParamDataID" name="testParamDataID" data-ng-model="testParamDataID" value='
									+ data[i]["testParamDataID"]
									+ '></input></td>'
									+ '<td class="value_big" id="edit_value_big">'
									+ data[i]["valueBig"]
									+ '</td>'
									+ '<td style="width:23%">'
									+ '<a href="#" class="edit_test_data_row">Edit</a>'
									+ '<a href="#" class="minus remove_test_data_row">Remove</a></td>'
									+ '</tr>';
									$(
									'#editTestData tr:last')
									.after(
											newTestDataTableRow)
											.focus();
									$('.filterchosen')
									.chosen();
									$('.parameterNames')
									.chosen();

								}

							}
						});

					});

			/* Adding a new Test Data Row */

			$(document)
			.on(
					"click",
					"#addNewTestDataRow",
					function() {
						var appId = window.appIDs;

						$
						.ajax({
							type : "POST",
							url : "views/getParamGroupNamesByAppIdITAP",
							data : JSON
							.stringify(appId),
							headers : {
								'Content-Type' : 'application/json'
							},
							success : function(data) {
								var arr = [];
								$
								.each(
										data,
										function(
												i,
												data) {
											var div_data = "<option value="
												+ data.paramGroupID
												+ ">"
												+ data.paramGroupName
												+ "</option>";
											arr
											.push(div_data);

										});

								paramGrpId.push(arr);

								var newTestDataTableRow = '<tr class="editTestDataRow">'
									+ '<td class="para_group_name">'
									+ '<select class="filterchosen paramGroupCls" data-placeholder="Select">'
									+ arr
									+ '</select>'
									+ '</td>'
									+ '<td class="para_name">'
									+ '<select class="editParamName" id="editParamName" data-placeholder="Select">'
									+ '<option></option>'
									+ '</select>'
									+ '</td>'
									+ '<td class="para_value"><input type="text" class="para_value_new" placeholder="Value" /></td>'
									+ '<td class="value_big"><input type="text" placeholder="Value Big" class="value_big_new"/></td>'
									+ '<td style="width:23%">'
									+ '<a href="#" class="edit_test_data_row">Edit</a>'
									+ '<a href="#" class="minus remove_test_data_row">Remove</a></td>'
									+ '</tr>';
								$(
								'#editTestData tr:last')
								.after(
										newTestDataTableRow)
										.focus();
								$('.filterchosen')
								.chosen();
								// $('#editParamName').chosen();

							}
						});

					});

			/* Edit a row */
			$(document).on(
					"click",
					".edit_test_data_row",
					function() {
						$(this).parent().parent().find(
						'td:eq(2), td:eq(3)').attr(
								"contenteditable", "true").addClass(
								'contentEdit');
						$(".edit_test_data_row").not(this).parent()
						.parent().find('td:eq(2), td:eq(3)')
						.removeAttr("contenteditable")
						.removeClass('contentEdit');
					});

			/* Delete Test Data Table row */
			$(document).on("click", ".remove_test_data_row",
					function() {
				$(this).parent().parent().remove();
			});

			/* Delete entire Test Data Table contents */
			$(document).on("click", "#removeEntireTestData",
					function() {
				$('#editTestData > tbody').remove();
			});

			/***********************************************************
			 * 
			 * REPORTS
			 * 
			 **********************************************************/

			$(".navBgReport ul li").bind(
					"click",
					function() {
						var prev_db3 = $(
						".navBgReport ul li.currentTab").attr(
						"id");
						prev_db3 = prev_db3
						.replace('Link', 'Container');
						$(".navBgReport ul li.currentTab .blueBar")
						.remove();
						$(".navBgReport ul li.currentTab").removeClass(
						"currentTab");
						$(this).addClass("currentTab");
						var tab_db3 = $(this).attr("id");
						$("<div class='blueBar'></div>").appendTo(
								$("#" + tab_db3));
						tab_db3 = tab_db3.replace('Link', 'Container');
						$("#" + prev_db3).hide();
						$("#" + tab_db3).show();
					});

			// $(document).on("click",".update_plan",function(){
			// $(".editTest").remove();
			// });

			$("#planUp .update_plan")
			.bind(
					"click",
					function() {
						var table_height_now = $(
						'table#test_plan_table_contents')
						.height()
						- $('#planUp').outerHeight();
						$(
						'table#test_plan_table_contents tbody')
						.css('max-height',
								table_height_now); // setting
						// back
						// the
						// height
						// without
						// planUp
						// div
						$("#planUp").hide();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper .test_plans_panel #test_plan_table_contents")
						.show();
					});

			$("#suiteUp .update_suite")
			.bind(
					"click",
					function() {
						var table_height_now = $(
						'table#test_suite_table_contents')
						.height()
						- $('#suiteUp').outerHeight();
						$(
						'table#test_suite_table_contents tbody')
						.css('max-height',
								table_height_now); // setting
						// back
						// the
						// height
						// without
						// suiteUp
						// div
						$("#suiteUp").hide();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper .test_suites_panel #test_suite_table_contents")
						.show();
					});

			$("#scenarioUp .update_scenario")
			.bind(
					"click",
					function() {
						var table_height_now = $(
						'table#test_scenarios_table_contents')
						.height()
						- $('#scenarioUp')
						.outerHeight();
						$(
						'table#test_scenarios_table_contents tbody')
						.css('max-height',
								table_height_now); // setting
						// back
						// the
						// height
						// without
						// scenarioUp
						// div
						$("#scenarioUp").hide();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper .test_scenarios_panel #test_scenarios_table_contents")
						.show();
					});

			$("#caseUp .update_case")
			.bind(
					"click",
					function() {
						var table_height_now = $(
						'table#test_cases_table_contents')
						.height()
						- $('#caseUp').outerHeight();
						$(
						'table#test_cases_table_contents tbody')
						.css('max-height',
								table_height_now); // setting
						// back
						// the
						// height
						// without
						// caseUp
						// div
						$("#caseUp").hide();
						$(this)
						.parents()
						.find(
						"#rightContentWrapper #test_cases_table_contents")
						.show();
					});

			function myFunc(inp) {
				if (globalObj != undefined && globalObj != null
						&& globalObj != '') {
					globalObj.updateTestSuite(inp);
				}
			}

			/** ********** USER MANAGEMENT SCREEN ************* */

			/* Last Run Container */
			/* Current Week Container */
			/*
			 * Date Container $(document).on("click",".search_reports",
			 * function () {
			 * $(this).parents('#dateContainer').children('.panels').show();
			 * }); $(document).on("click","#dateContainer .row",
			 * function () { $('.testRunSummary').fadeIn(); });
			 * $(document).on("click",".testRunSummary .cancel_btn",
			 * function () { $('.testRunSummary').fadeOut(); });
			 */

			/* All Container */
			oddEvenColor();
			// $(window).trigger("resize");

		});
