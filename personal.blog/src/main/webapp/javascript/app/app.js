/**
 * Created by Administrator on 2015/6/5.
 */
var App = (function() {

	var urlPath = "";

	var setUrlPath = function(path) {
		urlPath = path;
	};

	var getUrlPath = function() {
		return urlPath;
	}

	var Login = function() {
		var params = $('#loginForm').serialize();
		$.post(urlPath + '/login.do', params, function(data) {
			if (data.result == false) {
				$('#resultMsg').html(data.msg);
			} else {
				location.href = urlPath + '/admin/home.html';
			}
		}, 'json');
	};

	var submitArticleInfo = function() {
        // 清除错误提示信息
        clearErrorMsg();
        $('#articleAddForm .edui-default.edui-editor').css('border', '1px solid #d4d4d4');
		$('#articleAddForm').submit();
	};

	var submitArticleInfoCallback = function(data) {
		var data = jQuery.parseJSON(data);
		$('#articleId').val(data.articleId);
		if (data.result == false) {
			if (data.dbSave == true) {
				$('#resultMsg').html('<div class="error">' + data.msg + '</div>');
			} else {
				data.msg = Common.sortJsonDataByKey(data.msg, 'value');
				for (var i = 0; i < data.msg.length; i++) {
					$('#articleAddForm input[name="' + data.msg[i].name + '"]').css('border', '1px solid red');
					if ('content' == data.msg[i].name) {
						$('#articleAddForm .edui-default.edui-editor').css('border', '1px solid red');
						$('#articleAddForm input[name="'+ data.msg[i].name + '"]').css('border', '1px solid red');
					}
					$('#articleAddForm select[name="'+ data.msg[i].name + '"]').css('border', '1px solid red');
					$('#articleAddForm span[data-name=' + data.msg[i].name + ']').html(data.msg[i].value);
				}
			}
		} else {
			$('#resultMsg').html('<div class="success">' + data.msg + '</div>');
			//setTimeout("top.location.href='" + App.getUrlPath()+ "/admin/newsList.html" + "'", 1000);
		}
		$('form button').removeAttr('disabled');
	};
	
	
	var deleteArticleById = function(articleId, firstResult, maxResults) {
		$.post(urlPath + '/admin/article/deleteArticle', {
			articleId : articleId,
			firstResult : firstResult,
			maxResults : maxResults
		}, function(data) {
			$('#main-content-wrapper').html(data);
		});
	};
	
	var submitArticleTypeInfo = function() {
        // 清除错误提示信息
        clearErrorMsg();
        $('#articleTypeAddForm .edui-default.edui-editor').css('border', '1px solid #d4d4d4');
		$('#articleTypeAddForm').submit();
	};

	var submitArticleTypeInfoCallback = function(data) {
		var data = jQuery.parseJSON(data);
		$('#typeId').val(data.articleTypeId);
		if (data.result == false) {
			if (data.dbSave == true) {
				$('#resultMsg').html('<div class="error">' + data.msg + '</div>');
			}
		} else {
			$('#resultMsg').html('<div class="success">' + data.msg + '</div>');
		}
		$('form button').removeAttr('disabled');
	};
	
	
	var submitPhotoInfo = function() {
        // 清除错误提示信息
        clearErrorMsg();
        $('#uploader').css('border', '1px solid #d4d4d4');
        console.log($("#articleAddForm").serialize());
		$('#articleAddForm').submit();
	};

	var submitPhotoInfoCallback = function(data) {
		var data = jQuery.parseJSON(data);
		$('#photoId').val(data.photoId);
		if (data.result == false) {
			if (data.dbSave == true) {
				$('#resultMsg').html('<div class="error">' + data.msg + '</div>');
			} else {
				data.msg = Common.sortJsonDataByKey(data.msg, 'value');
				for (var i = 0; i < data.msg.length; i++) {
					$('#articleAddForm input[name="' + data.msg[i].name + '"]').css('border', '1px solid red');
					$('#articleAddForm select[name="'+ data.msg[i].name + '"]').css('border', '1px solid red');
					$('#articleAddForm span[data-name=' + data.msg[i].name + ']').html(data.msg[i].value);
					if ('content' == data.msg[i].name) {
						$('#uploader').css('border', '1px solid red');
					}
				}
			}
		} else {
			$('#resultMsg').html('<div class="success">' + data.msg + '</div>');
			//setTimeout("top.location.href='" + App.getUrlPath()+ "/admin/newsList.html" + "'", 1000);
		}
		$('form button').removeAttr('disabled');
	};
	

	/** ***************************************************************************************************************** */
	var InitProvinceList = function() {
		$.post(urlPath + '/provinceList.do', {}, function(data) {
			for (var i = 0; i < data.length; i++) {
				$("#province").append(
						$("<option></option>").val(data[i].id).html(
								data[i].name));
				$("#province").selectmenu("refresh");
			}
		}, 'json');
	};

	var clearErrorMsg = function() {
		$('form input[name]').css('border', '1px solid #b3b2b2');
		$('form select[name="type"]').css('border', '1px solid #b3b2b2');
		$('form span[aria-owns$=menu]').css('border', '1px solid #b3b2b2');
		// $('form div[class*="field"]').css('padding-bottom', '27px');
		$('span[data-name]').html('');
		$('#resultMsg').html('');
		$('form button').attr('disabled', 'disabled');
	};

	var submitCustomerInfo = function() {
		// 清除错误提示信息
		$('form button').attr('disabled', 'disabled');
		$('form input[name]').css('border', '1px solid #b3b2b2');
		$('form span[aria-owns$=menu]').css('border', '1px solid #b3b2b2');
		$('span[data-name]').html('');
		var params = $('#customerInfo').serialize();
		$
				.post(
						urlPath + '/submitCustomerInfo.do',
						params,
						function(data) {
							if (data.result == false) {
								if (data.dbSave == true) {
									$('#resultMsg').html(
											'<div class="error">' + data.msg
													+ '</div>');
								} else {
									data.msg = Common.sortJsonDataByKey(
											data.msg, 'value');
									for (var i = 0; i < data.msg.length; i++) {
										$(
												'form div[class="field '
														+ data.msg[i].name
														+ '"]').css(
												'padding-bottom', '20px');
										$(
												'form input[name="'
														+ data.msg[i].name
														+ '"]').css('border',
												'1px solid red');
										$(
												'form span[aria-owns="'
														+ data.msg[i].name
														+ '-menu"]').css(
												'border', '1px solid red');

										if ('province' == data.msg[i].name
												|| 'city' == data.msg[i].name) {
											if ('' != $(
													'span[data-name=province-city]')
													.html()) {
												$(
														'span[data-name=province-city]')
														.html(
																$(
																		'span[data-name=province-city]')
																		.html()
																		+ "， "
																		+ data.msg[i].value);
											} else {
												$(
														'span[data-name=province-city]')
														.html(data.msg[i].value);
											}
										} else {
											$(
													'span[data-name='
															+ data.msg[i].name
															+ ']').html(
													data.msg[i].value);
										}
									}
								}
							} else {
								$
										.colorbox({
											href : urlPath
													+ '/reqDetailCustomerInfoPage.html?customerInfoId='
													+ data.customerInfoId,
											innerWidth : 600,
											overlayClose : false,
											onComplete : function() {
												$("#houseStyle").selectmenu();
												$("#decorateCost").selectmenu();
												$("#decorateTime").selectmenu();
												$.colorbox.resize();
											}
										});
							}

							$('form button').removeAttr('disabled');

						}, 'json');

		return false;
	};

	var submitCustomerDetailInfo = function() {
		$('form button').attr('disabled', 'disabled');
		// 清除错误提示信息
		$('form input[name]').css('border', '1px solid #b3b2b2');
		$('form span[aria-owns$=menu]').css('border', '1px solid #b3b2b2');
		$('form div[class*="field"]').css('padding-bottom', '17px');
		$('span[data-name]').html('');
		$('#popupResultMsg').html('');
		$('#customerDetailInfo').find('br').remove();
		var params = $('#customerDetailForm').serialize();
		$
				.post(urlPath + '/submitCustomerDetailInfo.do', params,
						function(data) {
							if (data.result == false) {
								if (data.dbSave == true) {
									$('#popupResultMsg').html(
											'<div class="error">' + data.msg
													+ '</div>');
									$.colorbox.resize();
								} else {
									data.msg = Common.sortJsonDataByKey(
											data.msg, 'value');
									for (var i = 0; i < data.msg.length; i++) {
										$(
												'form div[class="field '
														+ data.msg[i].name
														+ '"]').css(
												'padding-bottom', '0px');
										$(
												'form input[name="'
														+ data.msg[i].name
														+ '"]').css('border',
												'1px solid red');
										$(
												'form span[aria-owns="'
														+ data.msg[i].name
														+ '-menu"]').css(
												'border', '1px solid red');
										$(
												'span[data-name='
														+ data.msg[i].name
														+ ']').html(
												data.msg[i].value);
									}
								}
							} else {
								$.colorbox({
									href : urlPath + '/weixin_picture.html',
									innerWidth : 400,
									overlayClose : false,
									onComplete : function() {
										$.colorbox.resize();
									}
								});
							}
							$('form button').removeAttr('disabled');

						}, 'json');

		return false;
	};

	var submitDesignerInfo = function() {
		// 清除错误提示信息
		clearErrorMsg();
		$('#designerInfoForm').submit();
	};

	var submitDesignerInfoCallback = function(data) {
		$('form button').removeAttr('disabled');
		var data = jQuery.parseJSON(data);
		$('#token').val(data.token);
		if (data.result == false) {
			if ((data.dbSave == true) || (data.repeat == true)) {
				$('#resultMsg').html(
						'<div class="error">' + data.msg + '</div>');
			} else {
				data.msg = Common.sortJsonDataByKey(data.msg, 'value');
				for (var i = 0; i < data.msg.length; i++) {
					// $('#designerInfoForm div[class="field ' +
					// data.msg[i].name + '"]').css('padding-bottom', '27px');
					$(
							'#designerInfoForm input[name="' + data.msg[i].name
									+ '"]').css('border', '1px solid red');
					$(
							'#designerInfoForm span[aria-owns="'
									+ data.msg[i].name + '-menu"]').css(
							'border', '1px solid red');

					if ('province' == data.msg[i].name
							|| 'city' == data.msg[i].name) {
						if ('' != $(
								'#designerInfoForm span[data-name=province-city]')
								.html()) {
							$('#designerInfoForm span[data-name=province-city]')
									.html(
											$('span[data-name=province-city]')
													.html()
													+ "， " + data.msg[i].value);
						} else {
							$('#designerInfoForm span[data-name=province-city]')
									.html(data.msg[i].value);
						}
					} else {
						$(
								'#designerInfoForm span[data-name='
										+ data.msg[i].name + ']').html(
								data.msg[i].value);
					}
				}
			}
		} else {
			$('#resultMsg').html('<div class="success">' + data.msg + '</div>');
		}
	};

	var submitDesignCaseCallback = function(data) {
		$('form button').removeAttr('disabled');
		var data = jQuery.parseJSON(data);
		if (data.result == false) {
			if (data.dbSave == true) {
				$('#resultMsg').html(
						'<div class="error">' + data.msg + '</div>');
			} else {
				data.msg = Common.sortJsonDataByKey(data.msg, 'value');
				for (var i = 0; i < data.msg.length; i++) {
					$(
							'#designCaseAddForm div[class="field-container '
									+ data.msg[i].name + '"]').css(
							'padding-bottom', '27px');
					$(
							'#designCaseAddForm input[name="'
									+ data.msg[i].name + '"]').css('border',
							'1px solid red');
					$(
							'#designCaseAddForm span[aria-owns="'
									+ data.msg[i].name + '-menu"]').css(
							'border', '1px solid red');
					$(
							'#designCaseAddForm span[data-name='
									+ data.msg[i].name + ']').html(
							data.msg[i].value);
				}
			}
		} else {
			$('#resultMsg').html('<div class="success">' + data.msg + '</div>');
			$('#designCaseId').val(data.caseId);
			setTimeout("top.location.href='" + App.getUrlPath()
					+ "/admin/designCaseList.html" + "'", 1000);
		}
	};

	var submitFrockCaseCallback = function(data) {
		$('form button').removeAttr('disabled');
		var data = jQuery.parseJSON(data);
		if (data.result == false) {
			if (data.dbSave == true) {
				$('#resultMsg').html(
						'<div class="error">' + data.msg + '</div>');
			} else {
				data.msg = Common.sortJsonDataByKey(data.msg, 'value');
				for (var i = 0; i < data.msg.length; i++) {
					$(
							'#frockCaseAddForm div[class="field-container '
									+ data.msg[i].name + '"]').css(
							'padding-bottom', '27px');
					$(
							'#frockCaseAddForm input[name="' + data.msg[i].name
									+ '"]').css('border', '1px solid red');
					$(
							'#frockCaseAddForm span[aria-owns="'
									+ data.msg[i].name + '-menu"]').css(
							'border', '1px solid red');
					$(
							'#frockCaseAddForm span[data-name='
									+ data.msg[i].name + ']').html(
							data.msg[i].value);
				}
			}
		} else {
			$('#resultMsg').html('<div class="success">' + data.msg + '</div>');
			$('#frockCaseId').val(data.caseId);
			setTimeout("top.location.href='" + App.getUrlPath()
					+ "/admin/frockCaseList.html" + "'", 1000);
		}
	};

	var submitBackendAddDesigner = function(data) {
		// 清除错误提示信息
		clearErrorMsg();
		$('#designerAddForm').submit();
	};

	var submitBackendAddDesignerCallback = function(data) {
		$('form button').removeAttr('disabled');
		var data = jQuery.parseJSON(data);
		if (data.result == false) {
			if (data.dbSave == true) {
				$('#resultMsg').html(
						'<div class="error">' + data.msg + '</div>');
				$.colorbox.resize();
			} else {
				data.msg = Common.sortJsonDataByKey(data.msg, 'value');
				for (var i = 0; i < data.msg.length; i++) {
					$('#designerAddForm input[name="' + data.msg[i].name + '"]')
							.css('border', '1px solid red');
					$(
							'#designerAddForm span[aria-owns="'
									+ data.msg[i].name + '-menu"]').css(
							'border', '1px solid red');
					$(
							'#designerAddForm span[data-name='
									+ data.msg[i].name + ']').html(
							data.msg[i].value);
				}
			}
		} else {
			$('#resultMsg').html('<div class="success">' + data.msg + '</div>');
			$('#designerId').val(data.designerId);
			// 自动跳转到列表页面
			setTimeout("top.location.href='" + App.getUrlPath()
					+ "/admin/designerList.html" + "'", 1000);
		}
	};

	var submitCustomerJoinInfo = function() {
		// 清除错误提示信息
		clearErrorMsg();
		var params = $('#customerJoinForm').serialize();
		$
				.post(
						urlPath + '/customerJoin.do',
						params,
						function(data) {
							$('#token').val(data.token);
							if (data.result == false) {
								if ((data.dbSave == true)
										|| (data.repeat == true)) {
									$('#resultMsg').html(
											'<div class="error">' + data.msg
													+ '</div>');
									$.colorbox.resize();
								} else {
									data.msg = Common.sortJsonDataByKey(
											data.msg, 'value');
									for (var i = 0; i < data.msg.length; i++) {
										$(
												'#customerJoinForm div[class="field '
														+ data.msg[i].name
														+ '"]').css(
												'padding-bottom', '27px');
										$(
												'#customerJoinForm input[name="'
														+ data.msg[i].name
														+ '"]').css('border',
												'1px solid red');
										$(
												'#customerJoinForm span[aria-owns="'
														+ data.msg[i].name
														+ '-menu"]').css(
												'border', '1px solid red');

										if ('province' == data.msg[i].name
												|| 'city' == data.msg[i].name) {
											if ('' != $(
													'span[data-name=province-city]')
													.html()) {
												$(
														'#customerJoinForm span[data-name=province-city]')
														.html(
																$(
																		'span[data-name=province-city]')
																		.html()
																		+ "， "
																		+ data.msg[i].value);
											} else {
												$(
														'#customerJoinForm span[data-name=province-city]')
														.html(data.msg[i].value);
											}
										} else {
											$(
													'#customerJoinForm span[data-name='
															+ data.msg[i].name
															+ ']').html(
													data.msg[i].value);
										}
									}
								}
							} else {
								$('#resultMsg').html(
										'<div class="success">' + data.msg
												+ '</div>');
							}

							$('form button').removeAttr('disabled');

						}, 'json');
	};

	var submitCustomerSummaryInfo = function() {
		// 清除错误提示信息
		clearErrorMsg();
		$('#summaryResultMsg').html('');
		var params = $('#customerSummaryInfoForm').serialize();
		$.post(urlPath + '/submitCustomerSummaryInfo.do', params,
				function(data) {
					if (data.result == false) {
						if (data.dbSave == true) {
							$('#summaryResultMsg')
									.html(
											'<div class="error">' + data.msg
													+ '</div>');
							$.colorbox.resize();
						} else {
							data.msg = Common.sortJsonDataByKey(data.msg,
									'value');
							for (var i = 0; i < data.msg.length; i++) {
								// $('.bottom-register-form form
								// div[class="field ' + data.msg[i].name +
								// '"]').css('padding-bottom', '27px');
								$(
										'.bottom-register-form form input[name="'
												+ data.msg[i].name + '"]').css(
										'border', '1px solid red');
								$(
										'.bottom-register-form form span[aria-owns="'
												+ data.msg[i].name + '-menu"]')
										.css('border', '1px solid red');
								$(
										'.bottom-register-form span[data-name='
												+ data.msg[i].name + ']').html(
										data.msg[i].value);
							}
						}
					} else {
						$('#summaryResultMsg').html(
								'<div class="success">' + data.msg + '</div>');
					}

					$('form button').removeAttr('disabled');

				}, 'json');
	};

	var ajaxRefreshTeamList = function(jobTitleId) {
		$.post(urlPath + '/teamList.do', {
			jobTitleId : jobTitleId
		}, function(data) {
			$('.team-list-result').html(data);
		});
	};

	var ajaxRefreshDesignCaseList = function(caseStyleId, designerId) {
		$.post(urlPath + '/designCaseList.do', {
			caseStyleId : caseStyleId,
			designerId : designerId
		}, function(data) {
			$('.design-list-result').html(data);
		});
	};

	var submitDesignCase = function() {
		// 清除错误提示信息
		clearErrorMsg();
		$('#designCaseAddForm').submit();
	};

	var submitFrockCase = function() {
		// 清除错误提示信息
		clearErrorMsg();
		$('#frockCaseAddForm').submit();
	};

	var submitDecorateDiaryInfo = function() {
		// 清除错误提示信息
		clearErrorMsg();
		$('#decorateDiaryAddForm .edui-default.edui-editor').css('border',
				'1px solid #d4d4d4');
		$('#decorateDiaryAddForm').submit();
	};

	var submitDecorateDiaryInfoCallback = function(data) {
		var data = jQuery.parseJSON(data);
		$('#decorateDiaryId').val(data.decorateDiaryId);
		if (data.result == false) {
			if (data.dbSave == true) {
				$('#resultMsg').html(
						'<div class="error">' + data.msg + '</div>');
			} else {
				data.msg = Common.sortJsonDataByKey(data.msg, 'value');
				for (var i = 0; i < data.msg.length; i++) {
					$(
							'#decorateDiaryAddForm input[name="'
									+ data.msg[i].name + '"]').css('border',
							'1px solid red');
					if ('content' == data.msg[i].name) {
						$('#decorateDiaryAddForm .edui-default.edui-editor')
								.css('border', '1px solid red');
					}

					$(
							'#decorateDiaryAddForm span[data-name='
									+ data.msg[i].name + ']').html(
							data.msg[i].value);
				}
			}
		} else {
			$('#resultMsg').html('<div class="success">' + data.msg + '</div>');
			setTimeout("top.location.href='" + App.getUrlPath()
					+ "/admin/decorateDiaryList.html" + "'", 1000);
		}

		$('form button').removeAttr('disabled');

	};

	var submitDecorateKnowledgeInfo = function() {
		// 清除错误提示信息
		clearErrorMsg();
		$('#decorateKnowledgeAddForm .edui-default.edui-editor').css('border',
				'1px solid #d4d4d4');
		$('#decorateKnowledgeAddForm').submit();
	};

	var submitDecorateKnowledgeInfoCallback = function(data) {
		var data = jQuery.parseJSON(data);
		$('#decorateKnowledgeId').val(data.decorateKnowledgeId);
		if (data.result == false) {
			if (data.dbSave == true) {
				$('#resultMsg').html(
						'<div class="error">' + data.msg + '</div>');
			} else {
				data.msg = Common.sortJsonDataByKey(data.msg, 'value');
				for (var i = 0; i < data.msg.length; i++) {
					$(
							'#decorateKnowledgeAddForm input[name="'
									+ data.msg[i].name + '"]').css('border',
							'1px solid red');
					if ('content' == data.msg[i].name) {
						$('#decorateDiaryAddForm .edui-default.edui-editor')
								.css('border', '1px solid red');
					}

					$(
							'#decorateKnowledgeAddForm span[data-name='
									+ data.msg[i].name + ']').html(
							data.msg[i].value);
				}
			}
		} else {
			$('#resultMsg').html('<div class="success">' + data.msg + '</div>');
			setTimeout("top.location.href='" + App.getUrlPath()
					+ "/admin/decorateKnowledgeList.html" + "'", 1000);
		}

		$('form button').removeAttr('disabled');

	};

	var backSubmitSameClassInteface = function(thisObj) {
		$(thisObj).attr('disabled', 'disabled');
		var $form = $(thisObj).parents('form');
		var params = $form.serialize();
		var url = $form.data('url');
		$.post(urlPath + url, params, function(data) {
			if (data.result == false) {
				$('#resultMsg').html(
						'<div class="error">' + data.msg + '</div>');
			} else {
				$('#resultMsg').html(
						'<div class="success">' + data.msg + '</div>');
			}
			$('form button').removeAttr('disabled');
		}, 'json');
	};



	var deleteDecorateDiaryById = function(decorateDiaryId, firstResult,
			maxResults) {
		$.post(urlPath + '/deleteDecorateDiary.do', {
			decorateDiaryId : decorateDiaryId,
			firstResult : firstResult,
			maxResults : maxResults
		}, function(data) {
			$('#main-content-wrapper').html(data);
		});
	};

	var deleteDesignCaseById = function(designCaseId, firstResult, maxResults) {
		$.post(urlPath + '/deleteDesignCase.do', {
			designCaseId : designCaseId,
			firstResult : firstResult,
			maxResults : maxResults
		}, function(data) {
			$('#main-content-wrapper').html(data);
		});
	};

	var deleteFrockCaseById = function(frockCaseId, firstResult, maxResults) {
		$.post(urlPath + '/deleteFrockCase.do', {
			frockCaseId : frockCaseId,
			firstResult : firstResult,
			maxResults : maxResults
		}, function(data) {
			$('#main-content-wrapper').html(data);
		});
	};

	var deleteDesignerById = function(designerId, firstResult, maxResults) {
		$.post(urlPath + '/deleteDesigner.do', {
			designerId : designerId,
			firstResult : firstResult,
			maxResults : maxResults
		}, function(data) {
			$('#main-content-wrapper').html(data);
		});
	};

	var deleteDesignerJoinById = function(designerJoinId, firstResult,
			maxResults) {
		$.post(urlPath + '/deleteDesignerJoin.do', {
			designerJoinId : designerJoinId,
			firstResult : firstResult,
			maxResults : maxResults
		}, function(data) {
			$('#main-content-wrapper').html(data);
		});
	};

	var deleteCustomerJoinById = function(customerJoinId, firstResult,
			maxResults) {
		$.post(urlPath + '/deleteCustomerJoin.do', {
			customerJoinId : customerJoinId,
			firstResult : firstResult,
			maxResults : maxResults
		}, function(data) {
			$('#main-content-wrapper').html(data);
		});
	};

	var deleteCustomerInfoById = function(customerInfoId, firstResult,
			maxResults) {
		$.post(urlPath + '/deleteCustomerInfo.do', {
			customerInfoId : customerInfoId,
			firstResult : firstResult,
			maxResults : maxResults
		}, function(data) {
			$('#main-content-wrapper').html(data);
		});
	};

	var deleteDecorateKnowledgeById = function(decorateKnowledgeId,
			firstResult, maxResults) {
		$.post(urlPath + '/deleteDecorateKnowledge.do', {
			decorateKnowledgeId : decorateKnowledgeId,
			firstResult : firstResult,
			maxResults : maxResults
		}, function(data) {
			$('#main-content-wrapper').html(data);
		});
	};

	var submitChangePassword = function(thisObj) {
		// 清除错误提示信息
		clearErrorMsg();
		var $form = $(thisObj).parents('form');
		var params = $form.serialize();
		var url = $form.data('url');
		$.post(urlPath + url, params, function(data) {
			if (data.result == false) {
				if (data.dbSave == true) {
					$('#resultMsg').html(
							'<div class="error">' + data.msg + '</div>');
				} else {
					data.msg = Common.sortJsonDataByKey(data.msg, 'value');
					for (var i = 0; i < data.msg.length; i++) {
						$form.find('input[name="' + data.msg[i].name + '"]')
								.css('border', '1px solid red');
						if ('content' == data.msg[i].name) {
							$form.find('.edui-default.edui-editor').css(
									'border', '1px solid red');
						}

						$form.find('span[data-name=' + data.msg[i].name + ']')
								.html(data.msg[i].value);
					}
				}
			} else {
				$('#resultMsg').html(
						'<div class="success">' + data.msg + '</div>');
			}

			$('form button').removeAttr('disabled');

		}, 'json');
	}

	var changeColorForFilter = function(thisObj) {
		$(thisObj).siblings().each(function() {
			$(this).css('color', '#555555').css('font-weight', 'normal');
		});
		$(thisObj).css('color', '#dc1515').css('font-weight', 'bold');
	};

	var initFilterAreaSelected = function() {
		var caseStyleId = $('#caseStyleId').val();
		var homeTypeId = $('#homeTypeId').val();
		var sizeTypeId = $('#sizeTypeId').val();
		$('.style.style-list').find('span[data-id="' + caseStyleId + '"]')
				.each(function() {
					$(this).css('color', '#dc1515').css('font-weight', 'bold');
				});
		$('.home.style-list').find('span[data-id="' + homeTypeId + '"]').each(
				function() {
					$(this).css('color', '#dc1515').css('font-weight', 'bold');
				});
		$('.area.style-list').find('span[data-id="' + sizeTypeId + '"]').each(
				function() {
					$(this).css('color', '#dc1515').css('font-weight', 'bold');
				});
	};

	var execFilterDesignCaseList = function() {
		var params = $('#filterDesignListForm').serialize();
		$.post(urlPath + '/ajaxRefreshDesignCaseList.html', params, function(
				data) {
			$('#main-content-wrapper').html(data);
		});
	};

	var filterDesignCaseListByCaseStyle = function(thisObj) {
		changeColorForFilter(thisObj);
		$('#caseStyleId').val($(thisObj).data('id'));
		execFilterDesignCaseList();
	};

	var filterDesignCaseListByHomeType = function(thisObj) {
		changeColorForFilter(thisObj);
		$('#homeTypeId').val($(thisObj).data('id'));
		execFilterDesignCaseList();
	};

	var filterDesignCaseListBySizeType = function(thisObj) {
		changeColorForFilter(thisObj);
		$('#sizeTypeId').val($(thisObj).data('id'));
		execFilterDesignCaseList();
	};

	var addNewDesignCaseImage = function() {
		var addHtml = '<div><input type="file" name="uploadCases" class="case-image"/><button type="button" onclick="App.deleteThisFileArea(this);" class="add-case-image-button">删除</button></div>';
		$('#addCaseImageArea').append(addHtml);
	};

	var addNewFrockCaseImage = function() {
		var addHtml = '<div><input type="file" name="uploadCases" class="case-image"/><button type="button" onclick="App.deleteThisFileArea(this);" class="add-case-image-button">删除</button></div>';
		$('#addCaseImageArea').append(addHtml);
	};

	var addNewDesignRecord = function() {
		var addHtml = '<div><input type="text" name="designRecordArea" class="case-image"/><button type="button" onclick="App.deleteThisFileArea(this);" class="add-case-image-button">删除</button></div>';
		$('#addDesignRecordArea').append(addHtml);
	};

	var deleteThisFileArea = function(thisObj) {
		$(thisObj).parent().remove();
	};

	return {
		setUrlPath : setUrlPath,
		getUrlPath : getUrlPath,
		Login : Login,
		submitArticleInfo : submitArticleInfo,
		submitArticleInfoCallback : submitArticleInfoCallback,
		deleteArticleById : deleteArticleById,
		submitArticleTypeInfo : submitArticleTypeInfo,
		submitArticleTypeInfoCallback : submitArticleTypeInfoCallback,
		submitPhotoInfo : submitPhotoInfo,
		submitPhotoInfoCallback : submitPhotoInfoCallback,
		
		InitProvinceList : InitProvinceList,
		submitCustomerInfo : submitCustomerInfo,
		submitCustomerDetailInfo : submitCustomerDetailInfo,
		submitDesignerInfo : submitDesignerInfo,
		submitDesignerInfoCallback : submitDesignerInfoCallback,
		submitCustomerJoinInfo : submitCustomerJoinInfo,
		submitCustomerSummaryInfo : submitCustomerSummaryInfo,
		ajaxRefreshTeamList : ajaxRefreshTeamList,
		ajaxRefreshDesignCaseList : ajaxRefreshDesignCaseList,
		backSubmitSameClassInteface : backSubmitSameClassInteface,
		submitDecorateDiaryInfo : submitDecorateDiaryInfo,
		submitDecorateDiaryInfoCallback : submitDecorateDiaryInfoCallback,
		submitDecorateKnowledgeInfo : submitDecorateKnowledgeInfo,
		submitDecorateKnowledgeInfoCallback : submitDecorateKnowledgeInfoCallback,
		deleteDecorateDiaryById : deleteDecorateDiaryById,
		deleteDesignCaseById : deleteDesignCaseById,
		deleteFrockCaseById : deleteFrockCaseById,
		deleteDecorateKnowledgeById : deleteDecorateKnowledgeById,
		filterDesignCaseListByCaseStyle : filterDesignCaseListByCaseStyle,
		filterDesignCaseListByHomeType : filterDesignCaseListByHomeType,
		filterDesignCaseListBySizeType : filterDesignCaseListBySizeType,
		initFilterAreaSelected : initFilterAreaSelected,
		deleteThisFileArea : deleteThisFileArea,
		addNewDesignCaseImage : addNewDesignCaseImage,
		addNewFrockCaseImage : addNewFrockCaseImage,
		submitDesignCaseCallback : submitDesignCaseCallback,
		submitFrockCaseCallback : submitFrockCaseCallback,
		submitDesignCase : submitDesignCase,
		submitFrockCase : submitFrockCase,
		deleteDesignerById : deleteDesignerById,
		deleteDesignerJoinById : deleteDesignerJoinById,
		deleteCustomerJoinById : deleteCustomerJoinById,
		deleteCustomerInfoById : deleteCustomerInfoById,
		submitBackendAddDesigner : submitBackendAddDesigner,
		submitBackendAddDesignerCallback : submitBackendAddDesignerCallback,
		addNewDesignRecord : addNewDesignRecord,
		submitChangePassword : submitChangePassword
	};
})();
