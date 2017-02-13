/**
 * Created by Administrator on 2015/6/5.
 */
var Event = (function() {

    var openInNewTab = function(url) {
        window.open(url, "_blank");
    };
    
    var openInCurrentTab = function(url){
        location.href = App.getUrlPath() + url;
    };

    var forwardAboutUsPage = function() {
        location.href = App.getUrlPath() + '/aboutUs.html';
    };

    var forwardContactUsPage = function() {
        location.href = App.getUrlPath() + '/contactUs.html';
    };

    var forwardHomePage = function() {
        location.href = App.getUrlPath() + '/';
    };

    var forwardDesignerJoinPage = function() {
        openInNewTab(App.getUrlPath() + '/designerJoin.html');
    };

    var forwardCustomerJoinPage = function() {
        openInNewTab(App.getUrlPath() + '/customerJoin.html');
    };

    var forwardDesignCaseListPage = function() {
        location.href = App.getUrlPath() + '/designCaseList.html';
    };
    
    var forwardFrockCaseListPage = function(){
        location.href = App.getUrlPath() + '/frockCaseList.html';
    };
    
    var forwardDesignCaseListPageWithParams = function(caseStyleId, homeTypeId, sizeTypeId){
        //location.href = App.getUrlPath() + '/designCaseList.html?caseStyleId='+caseStyleId+'&homeTypeId='+homeTypeId+'&sizeTypeId='+sizeTypeId;
    	location.href = App.getUrlPath() + '/designCaseList.html';
    };
    
    var forwardDesignCaseDetailPage = function(caseId) {
        openInNewTab(App.getUrlPath() + '/designCaseDetail.html?caseId='+caseId);
    };
    
    var forwardFrockCaseDetailPage = function(caseId) {
        openInNewTab(App.getUrlPath() + '/frockCaseDetail.html?caseId='+caseId);
    };

    var forwardNewsListPage = function() {
        location.href = App.getUrlPath() + '/newsList.html';
    };

    var forwardNewsDetailPage = function(newsId) {
        location.href = App.getUrlPath() + '/newsDetail.html?newsId=' + newsId;
    };

    var forwardNewsDetailPageInNewPage = function(newsId) {
        openInNewTab(App.getUrlPath() + '/newsDetail.html?newsId=' + newsId);
    };
    
    var forwardDecorateDiaryDetailPageInNewPage = function(diaryId) {
        openInNewTab(App.getUrlPath() + '/decorateDiaryDetail.html?diaryId=' + diaryId);
    };
    
    var forwardDesignerDetailPageInNewPage = function(designerId) {
        openInNewTab(App.getUrlPath() + '/teamDetail.html?designerId=' + designerId);
    };
    
    var forwardBackendEditDesignCase = function(designCaseId){
        location.href = App.getUrlPath() + '/adminEditDesignCase.html?designCaseId='+designCaseId;
    };
    
    var forwardBackendEditFrockCase = function(frockCaseId){
        location.href = App.getUrlPath() + '/adminEditFrockCase.html?frockCaseId='+frockCaseId;
    }
    
    var forwardDecorateKnowledgeDetailPageInNewPage = function(knowledgeId) {
        openInNewTab(App.getUrlPath() + '/decorateKnowledgeDetail.html?knowledgeId=' + knowledgeId);
    };

    var forwardDecorateDiaryListPage = function() {
        location.href = App.getUrlPath() + '/decorateDiaryList.html';
    };

    var forwardDecorateDiaryDetailPage = function(diaryId) {
        location.href = App.getUrlPath() + '/decorateDiaryDetail.html?diaryId=' + diaryId;
    };

    var forwardDecorateKnowledgeListPage = function() {
        location.href = App.getUrlPath() + '/decorateKnowledgeList.html';
    }

    var forwardDecorateKnowledgeDetailPage = function(knowledgeId) {
        location.href = App.getUrlPath() + '/decorateKnowledgeDetail.html?knowledgeId=' + knowledgeId;
    };

    var forwardTeamListPage = function() {
        location.href = App.getUrlPath() + '/teamList.html';
    };

    var forwardActionServicePage = function() {
        location.href = App.getUrlPath() + '/actionService.html';
    };

    var forwardServiceProcessPage = function() {
        location.href = App.getUrlPath() + '/serviceProcess.html';
    };

    var forwardMaterialServicePage = function() {
        location.href = App.getUrlPath() + '/materialService.html';
    };

    var forwardCompanyHonourPage = function() {
        location.href = App.getUrlPath() + '/companyHonour.html';
    };

    var forwardTeamDetailPage = function(designerId) {
        openInNewTab(App.getUrlPath() + '/teamDetail.html?designerId=' + designerId);
    };

    var forwardBackendNewsEditPage = function(newsId) {
        location.href = App.getUrlPath() + '/newsEdit.html?newsId=' + newsId;
    };
    
    var forwardBackendDesignerEditPage = function(designerId) {
        location.href = App.getUrlPath() + '/adminEditDesigner.html?designerId=' + designerId;
    };
    
    var forwardBackendDecorateDiaryEditPage = function(diaryId) {
        location.href = App.getUrlPath() + '/decorateDiaryEdit.html?diaryId=' + diaryId;
    };
    
    var forwardBackendDecorateKnowledgeEditPage = function(knowledgeId) {
        location.href = App.getUrlPath() + '/decorateKnowledgeEdit.html?knowledgeId=' + knowledgeId;
    };

    var clickUploadFile = function(id) {
        $('#' + id).click();
    };

    var displayUploadFileName = function(selfId, id) {
        var fileName = $('#' + selfId).val();
        $('#' + id).val(getFileName(fileName));
    };

    var getFileName = function(str) {
        if (str) { return str.replace(/C:\\fakepath\\/i, ''); }
        return "";
    };
    
    var shareSina = function(){
        var shareUrl = location.href;
        var openUrl = "http://www.jiathis.com/send/?webid=tsina&url="+shareUrl+"&title=西安最好的装修设计公司_陕西美季建筑装饰工程设计有限公司";
        openInNewTab(openUrl);
    };
    
    var shareQQWeibo = function(){
        var shareUrl = location.href;
        var openUrl = "http://www.jiathis.com/send/?webid=tqq&url="+shareUrl+"&title=西安最好的装修设计公司_陕西美季建筑装饰工程设计有限公司";
        openInNewTab(openUrl);
    };
    
    var shareWeixin = function(){
        var shareUrl = location.href;
        var openUrl = "http://www.jiathis.com/send/?webid=weixin&url="+shareUrl+"&title=西安最好的装修设计公司_陕西美季建筑装饰工程设计有限公司";
        openInNewTab(openUrl);
    };
    
    return {
        openInNewTab: openInNewTab,
        openInCurrentTab: openInCurrentTab,
        getFileName: getFileName,
        forwardAboutUsPage: forwardAboutUsPage,
        forwardHomePage: forwardHomePage,
        forwardDesignerJoinPage: forwardDesignerJoinPage,
        forwardCustomerJoinPage: forwardCustomerJoinPage,
        forwardDesignCaseListPage: forwardDesignCaseListPage,
        forwardFrockCaseListPage: forwardFrockCaseListPage,
        forwardDesignCaseDetailPage: forwardDesignCaseDetailPage,
        forwardFrockCaseDetailPage: forwardFrockCaseDetailPage,
        forwardNewsListPage: forwardNewsListPage,
        forwardNewsDetailPage: forwardNewsDetailPage,
        clickUploadFile: clickUploadFile,
        displayUploadFileName: displayUploadFileName,
        forwardTeamListPage: forwardTeamListPage,
        forwardTeamDetailPage: forwardTeamDetailPage,
        forwardContactUsPage: forwardContactUsPage,
        forwardActionServicePage: forwardActionServicePage,
        forwardServiceProcessPage: forwardServiceProcessPage,
        forwardMaterialServicePage: forwardMaterialServicePage,
        forwardCompanyHonourPage: forwardCompanyHonourPage,
        forwardDecorateDiaryListPage: forwardDecorateDiaryListPage,
        forwardDecorateDiaryDetailPage: forwardDecorateDiaryDetailPage,
        forwardDecorateKnowledgeListPage: forwardDecorateKnowledgeListPage,
        forwardDecorateKnowledgeDetailPage: forwardDecorateKnowledgeDetailPage,
        forwardNewsDetailPageInNewPage: forwardNewsDetailPageInNewPage,
        forwardDecorateDiaryDetailPageInNewPage: forwardDecorateDiaryDetailPageInNewPage,
        forwardDesignerDetailPageInNewPage: forwardDesignerDetailPageInNewPage,
        forwardDecorateKnowledgeDetailPageInNewPage: forwardDecorateKnowledgeDetailPageInNewPage,
        forwardBackendNewsEditPage: forwardBackendNewsEditPage,
        forwardBackendDecorateDiaryEditPage: forwardBackendDecorateDiaryEditPage,
        forwardBackendDecorateKnowledgeEditPage: forwardBackendDecorateKnowledgeEditPage,
        forwardBackendEditDesignCase: forwardBackendEditDesignCase,
        forwardBackendEditFrockCase: forwardBackendEditFrockCase,
        forwardBackendDesignerEditPage: forwardBackendDesignerEditPage,
        forwardDesignCaseListPageWithParams: forwardDesignCaseListPageWithParams,
        shareSina: shareSina,
        shareQQWeibo: shareQQWeibo,
        shareWeixin: shareWeixin
    };
})();
