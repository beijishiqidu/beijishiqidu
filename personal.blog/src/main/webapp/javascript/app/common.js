/**
 * Created by Administrator on 2015/6/5.
 */
var Common = (function() {
    
    var pagination = function(url,param,targetId){
        $.get(url, param, function(data){
            $('#'+targetId).html(data);
        });
    };
    
    var sortJsonDataByKey = function(array, key){
        return array.sort(function(a, b) {
            var x = a[key]; var y = b[key];
            return ((x < y) ? -1 : ((x > y) ? 1 : 0));
        });
    };
    
    var gotoTop = function(min_height){
    	//预定义返回顶部的html代码，它的css样式默认为不显示
        var gotoTop_html = '<div id="gotoTop"></div>';
        //将返回顶部的html代码插入页面上id为page的元素的末尾 
        $("#page").append(gotoTop_html);
        
        var screenWidth = screen.width;
        if(screenWidth<1680&&screenWidth>=1440){
            $('#gotoTop').css('top','90%');
            $('#onlineCustom').css('top','82.5%');
        }else if(screenWidth>=1366&&screenWidth<1440){
            $('#gotoTop').css('top','90%');
            $('#onlineCustom').css('top','81%');
        }
        
        $("#gotoTop").click(//定义返回顶部点击向上滚动的动画
            function(){$('html,body').animate({scrollTop:0},700);
        }).hover(//为返回顶部增加鼠标进入的反馈效果，用添加删除css类实现
            function(){$(this).addClass("hover");},
            function(){$(this).removeClass("hover");
        });
        //获取页面的最小高度，无传入值则默认为600像素
        min_height ? min_height = min_height : min_height = 600;
        //为窗口的scroll事件绑定处理函数
        $(window).scroll(function(){
            //获取窗口的滚动条的垂直位置
            var s = $(window).scrollTop();
            //当窗口的滚动条的垂直位置大于页面的最小高度时，让返回顶部元素渐现，否则渐隐
            if( s > min_height){
                $("#gotoTop").fadeIn(100);
            }else{
                $("#gotoTop").fadeOut(200);
            };
        });
    };
    
    /**
     * url 需要打开的url.<br/><br/>
     * width 宽度，如果不指定，则默认为443px.<br/><br/>
     * height 高度，如果不指定，则默认未300px.<br/><br/>
     * completeCallback 弹出框口加载完成之后调用函数，如果不传入，则默认执行弹出窗口的resize方法.<br/><br/>
     * closeCallback 弹出窗口关闭时调用的函数，可以不传入.<br/><br/>
     */
    var openPopup = function(url, width, height, completeCallback, closeCallback) {
        var tmpWidth = width || '443';
        var tmpHeight = height || '300';
        var tmpCompleteCallback = completeCallback || function() {
        	togglePopupBorder();
            Common.resizePopup();
            /*打开弹出窗口之后，需要加载的页面*/
            
        };
        var tmpCloseCallback = closeCallback || function() {};
        var path = App.getUrlPath();
        $.colorbox({
            href: path + url,
            innerWidth: tmpWidth,
            innerHeight: tmpHeight,
            overlayClose: false,
            onOpen: function() {
                togglePopupBorder();
            },
            onComplete: tmpCompleteCallback,
            onClosed: tmpCloseCallback
        });
    };
    
    var togglePopupBorder = function() {
        if ($('#cboxTopLeft').is(':hidden')) {
            $('#cboxTopLeft').show();
            $('#cboxTopRight').show();
            $('#cboxBottomLeft').show();
            $('#cboxBottomRight').show();
            $('#cboxMiddleLeft').show();
            $('#cboxMiddleRight').show();
            $('#cboxTopCenter').show();
            $('#cboxBottomCenter').show();
            $('#cboxClose').show();
        } else {
            $('#cboxTopLeft').hide();
            $('#cboxTopRight').hide();
            $('#cboxBottomLeft').hide();
            $('#cboxBottomRight').hide();
            $('#cboxMiddleLeft').hide();
            $('#cboxMiddleRight').hide();
            $('#cboxTopCenter').hide();
            $('#cboxBottomCenter').hide();
            $('#cboxClose').hide();
        }
    };
    
    var closePopup = function() {
        $.colorbox.close();
    };
    var resizePopup = function() {
        $.colorbox.resize();
    };

    return {
        pagination: pagination,
        sortJsonDataByKey: sortJsonDataByKey,
        gotoTop: gotoTop,
        openPopup: openPopup,
        togglePopupBorder: togglePopupBorder,
        closePopup: closePopup,
        resizePopup: resizePopup
    };
})();

