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
		$('#typeId').val(data.typeId);
		if (data.result == false) {
			$('#resultMsg').html('<div class="alert alert-danger" role="alert">'+data.msg+'</div>');
		} else {
			$('#resultMsg').html('<div class="alert alert-success" role="alert">' + data.msg + '</div>');
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
				$('#resultMsg').html('<div class="alert alert-danger" role="alert">' + data.msg + '</div>');
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
			$('#resultMsg').html('<div class="alert alert-success" role="alert">' + data.msg + '</div>');
			//setTimeout("top.location.href='" + App.getUrlPath()+ "/admin/newsList.html" + "'", 1000);
		}
		$('form button').removeAttr('disabled');
	};
	
	var deleteCurrentPhoto = function(thisObj, photoId){
		$.post(urlPath + '/admin/photo/delete', {
			photoId : photoId
		}, function(data) {
			var data = jQuery.parseJSON(data);
			if (data.result == true) {
				$(thisObj).parents('.item').remove();
			}else{
				alert(data.msg);
			}
		});
		
	};
	
	var deleteArticleTypeById = function(thisObj, typeId){
		$.post(urlPath + '/admin/article/delete-type', {
			typeId : typeId
		}, function(data) {
			var data = jQuery.parseJSON(data);
			if (data.result == true) {
				$(thisObj).parents('tr').remove();
			}else{
				alert(data.msg);
			}
		});
	};
	
	var deletePhotoTypeById = function(thisObj, typeId){
		$.post(urlPath + '/admin/photo/delete-type', {
			typeId : typeId
		}, function(data) {
			var data = jQuery.parseJSON(data);
			if (data.result == true) {
				$(thisObj).parents('tr').remove();
			}else{
				alert(data.msg);
			}
		});
	};
	
	/**
     * 打开浏览图片的popup.
     */
    var openWorkDetailPopupPage = function(albumId) {
        var url = '/photo/detail/'+albumId;
        Common.openPopup(url, '90%', '90%', function() {
            //修改colorbox的样式
            $('#cboxLoadedContent').css('border-color','black');
            $('#cboxLoadedContent').css('border','none');
            var height = $('#cboxContent').height();
            var width = $('#cboxContent').width();
            $('#cboxLoadedContent').css('height',height+'px');
            $('#cboxLoadedContent').css('width',width+'px');
            $('.work-detail-left').css('background-color','black');
            
            //App.initResizePopupImg();
            //calucateButtonLocation();
            Common.togglePopupBorder();
            Common.resizePopup();
        });
    };
    
    /**
     * 计算展示图片的大小和位置.
     */
    var initResizePopupImg = function() {
        var $img = $('.p-right-area .img-p img');
        var width = $('#cboxLoadedContent').width() * 0.6;
        var height = $('#cboxLoadedContent').height() - 160;
        $('.work-detail-popup-container').css('height',($('#cboxLoadedContent').height())+'px');
        //AutoResizeImage($img[0], width, height);
        $img.css('margin-top', '20px');
    };
    
    /**
     * 计算展示图片的大小.
     */
    var AutoResizeImage = function(objImg, maxWidth, maxHeight) {
        var img = new Image();
        img.src = objImg.src;
        var hRatio;
        var wRatio;
        var Ratio = 1;
        var w = img.width;
        var h = img.height;
        wRatio = maxWidth / w;
        hRatio = maxHeight / h;
        if (maxWidth == 0 && maxHeight == 0) {
            Ratio = 1;
        } else if (maxWidth == 0) {
            if (hRatio < 1) Ratio = hRatio;
        } else if (maxHeight == 0) {
            if (wRatio < 1) Ratio = wRatio;
        } else if (wRatio < 1 || hRatio < 1) {
            Ratio = (wRatio <= hRatio ? wRatio : hRatio);
        }
        if (Ratio < 1) {
            w = w * Ratio;
            h = h * Ratio;
        }
        objImg.height = h;
        objImg.width = w;
    };
    
    
    var prevPhoto = function(){
    	var firstResult = $('.center-content').data('first-result');
    	var albumId = $('.center-content').data('album-id');
    	firstResult = firstResult-1;
    	$.post(urlPath + '/photo/index/'+albumId, {
    		firstResult : firstResult
		}, function(data) {
			$('.photo-navigate-body-wapper').html(data);
		});
    };
    
    var nextPhoto = function(){
    	var firstResult = $('.center-content').data('first-result');
    	var albumId = $('.center-content').data('album-id');
    	firstResult = firstResult+1;
    	$.post(urlPath + '/photo/index/'+albumId, {
    		firstResult : firstResult
		}, function(data) {
			$('.photo-navigate-body-wapper').html(data);
		});
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
		deleteCurrentPhoto: deleteCurrentPhoto,
		deleteArticleTypeById: deleteArticleTypeById,
		deletePhotoTypeById: deletePhotoTypeById,
		openWorkDetailPopupPage: openWorkDetailPopupPage,
		initResizePopupImg: initResizePopupImg,
		AutoResizeImage: AutoResizeImage,
		prevPhoto: prevPhoto,
		nextPhoto: nextPhoto
	};
})();
