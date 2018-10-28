/**
 * Created by Tunji Longe on 7/5/2017.
 */
var DEFAULT_DOMAIN="/headstart/"
$(document).ready(function () {

    init();

    function init(){
        initNodeDetectors();
        loadAjaxSections();
    }


    function initNodeDetectors(){

        insertionQ('.insertion').every(function(insertion){
            initParsley(insertion);
            initPagination(insertion);
            initTableSorting(insertion);
        });

        insertionQ('.ajax-loaded').every(function(element){
            loadAjaxSectionToDom($(element));
        });

    }

    // processess loading of '.ajax-loaded' html sections
    function loadAjaxSections() {
        $('.ajax-loaded').each(function() {
            var element = $(this);
            loadAjaxSectionToDom(element);
        });
    }

    function loadAjaxSectionToDom(element){
        var url = element.data('load');
        var data = {};
        data.target = "#"+element.attr('id');
        data.targetAction = 'replace';
        element.showBusyAnimation();
        handleAjaxGet(url, $.toDataArray(data));
    }

    //ajax submit handler
    $(document).on('submit', '.ajax-form', function(e){
        e.preventDefault();
        $(this).ajaxFormSubmit();
    });

    $(document).on('click', '.btn-request', function(e){
        e.preventDefault();
        var clickSource = $(this);
        processBtnRequestClick(clickSource, {});
    });

    $(document).on('change', '.selective-display', function(){
        $('[select-ref]').hide();
        $('.select-target').html('');
        var index = $(this).prop('selectedIndex');
        var selectedOption = $(this).find('option:selected');
        if(index > 0){
            $('.select-target').html($('[select-ref='+$(selectedOption).attr("select-id")+']').html());
            //$('[select-ref='+$(selectedOption).attr("select-id")+']').show();
        }
    });


});

//this was affecting my ajax call to load agents and groups
//adds defined extra global parameter(s) to all ajax requests just before sending
$.ajaxPrefilter(function (options, originalOptions, jqXHR) {

    // Uncomment this if you do not want to send the global data for POST/PUT/DELETE
    /*if(originalOptions.type !== 'GET' || options.type !== 'GET') {
     return;
     }*/

    if(!$.isBlank(originalOptions.data)){
        if(!$.isBlank(originalOptions.enctype) && originalOptions.enctype === 'multipart/form-data'){
            originalOptions.data.append("ajax", true);
            options.data = originalOptions.data;
        }else{
            var params = [];
            $.each(originalOptions.data, function(index, value){
                params.push(value);
            });
            params.push({name:"ajax", value:true});
            options.data = $.param(params);
        }
    }else{
        var params = [];
        params.push({name:"ajax", value:true});
        options.data = $.param(params);
    }
});

//handles global ajax requests
var ajaxSource = null;
$(document).ajaxSend(function(event, request, options) {

    //injects csrf token to header before all ajax post requests
    if(!$.isBlank(options.type) && options.type.toLowerCase() === 'post'){
        var token = $("meta[name='csrf-token']").attr("content");
        var header = $("meta[name='csrf-header']").attr("content");
        if(!$.isBlank(token) && !$.isBlank(header)){
            request.setRequestHeader(header, token);
        }
    }

}).ajaxStart(function (event) {

    ajaxSource = $(event.target.activeElement);
    if (ajaxSource.hasClass('has-spinner')){
        ajaxSource.prependSpinner();
    }else{
        $('body').showLoadingAnimation();
    }

}).ajaxSuccess(function(event, request, settings) {
    //console.log('success');
}).ajaxComplete(function(event, request, settings) {
    //console.log('complete');

    //Handles Session expiry ajax requests
    //The text "Access Forbidden" is used here because it is
    // contained in the 403 page that gets returned whenever session is expired
    if (request.responseText.indexOf("Access Forbidden") > 0) {
        //Session has Expired,redirect to login page
        window.location.href = "login";
    }
}).ajaxStop(function (event) {

    if (ajaxSource != null && ajaxSource.hasClass('has-spinner')){
        ajaxSource.removeSpinner();
        ajaxSource = null;
    }
    $('body').removeSpinner();
    $('body').hideLoadingAnimation();

}).ajaxError(function (event, request, settings, error) {
    var reason = request.statusText;
    var response = request.responseText;
    var json = extractJson(response);
    if (json != null) {
        var status = json.status;
        var result = json.result;

        swal({
            html: true,
            type: "error",
            title: result.title,
            text: "<p>Please copy the contents of the following and send to " +
            "<a href='mailto:" + SUPPORT_EMAIL + "' class='text-blue'>" + SUPPORT_EMAIL + "</a></p>" +
            "<div class='col-xs-12 col-md-12 img-thumbnail'><p>"+reason+"</p><hr>"+result.content+"</div>"
        });
    }else{
        swal({
            html: true,
            type: "error",
            title: "Error!",
            text: "<p>Please copy the contents of the following and send to " +
            "<a href='mailto:" + SUPPORT_EMAIL + "' class='text-blue'>" + SUPPORT_EMAIL + "</a></p>" +
            "<div class='col-xs-12 col-md-12 img-thumbnail'><p>"+reason+"</p></div>"
        });
    }
});

$.getIdFromURL = function(url){
    var parts = url.split('/');
    return parts[parts.length - 1];
}

$.fn.isMultiPartForm = function(){
    var enctype = this.attr('enctype');
    return (typeof enctype !== typeof undefined && enctype !== false);
}

$.fn.ajaxFormSubmit = function(){
    var form = this;
    var url = form.attr('action');
    if(form.isMultiPartForm()){
        var enctype = form.attr('enctype');
        var data = new FormData(form[0]);
        handleAjaxMultipartPost(url, data, enctype);
    }else{
        var data = form.serializeArray();
        handleAjaxPost(url, data);
    }
}

function resolveURL(url){
    return url.indexOf(DEFAULT_DOMAIN) == 0 ? url : DEFAULT_DOMAIN+url;
}

function handleAjaxGet(url, data){
    processAjaxRequest("get", resolveURL(url), data);
}

function handleAjaxPost(url, data){
    processAjaxRequest("post", resolveURL(url), data);
}

function handleAjaxMultipartPost(url, data, enctype){
    processAjaxMultipartRequest(resolveURL(url), data, enctype);
}


function processBtnRequestClick(clickSource, data){
    var url = clickSource.attr('data-href');
    var method = clickSource.attr('data-method');
    var target = clickSource.attr('data-target');
    var targetAction = clickSource.attr('data-target-action');
    var payload = clickSource.attr('data-payload');

    data = !$.isBlank(data) ? data : {};
    data.target = target;
    data.targetAction=targetAction;
    data = $.toDataArray(data);

    if(!$.isBlank(payload)){
        if(payload.contains("{") && payload.contains("\'") && payload.contains("}")){
            if(method.equalsIgnoreCase("GET") || method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT")
                || method.equalsIgnoreCase("PATCH") || method.equalsIgnoreCase("DELETE")){

                payload = $.toDataArray(JSON.parse(payload.replaceAll("\'", "\"")));
                $.merge(data, payload);
            }
        }
    }

    if(method.toLowerCase() === 'get'){
        handleAjaxGet(url, data);
    }else if(method.toLowerCase() === 'post'){
        handleAjaxPost(url, data);
    }else if(method.toLowerCase() === 'delete'){
        swal({
            title: "Are you sure?",
            text: "The information you are about to discard will be permanently lost!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#f05050", //#DD6B55
            confirmButtonText: "Yes, Delete",
            cancelButtonText: "Cancel",
            closeOnConfirm: false,
            showLoaderOnConfirm: true,
            closeOnCancel: true
        },
        function(isConfirm){
            if(isConfirm){
                handleAjaxPost(url, data);

                if(clickSource.hasClass('btn-request')){
                    var inputGroup = clickSource.closest('.input-group');
                    if(inputGroup.length > 0){
                        var collapsibleTrigger = inputGroup.find('[data-toggle=collapse]');
                        if(collapsibleTrigger.length > 0){
                            var collapsibleTarget = collapsibleTrigger.attr('href');
                            $(collapsibleTarget).remove();
                        }
                        inputGroup.remove();
                    }
                }
            }
        });
    }
}


function processAjaxRequest(method, url, data){
    $.ajax({
        url : url,
        type : method,
        dataType : "json",
        data: data,

        success : function(response) {
            processAjaxResponse(response);
        }
    });
    return false;
}

function processAjaxMultipartRequest(url, data, enctype){
    $.ajax({
        url : url,
        type : "POST",
        dataType : "json",
        data: data,
        enctype: enctype,
        /*async: false,*/
        cache: false,
        contentType: false,
        processData: false,

        success : function(response) {
            processAjaxResponse(response);
        }
    });
    return false;
}

function extractJson(response){
    try{
        return $.parseJSON(response);
    }catch(err){
        return null;
    }
}

function isJson(response){
    try{
        return (typeof response === 'object');
    }catch(err){
        return false;
    }
}

//Removes all modals from the dom
function overlayModal(){
    $('body .modal').each(function(index) {
        var modal = $(this);
        if(modal.length > 0){
            if(modal.attr("ajax-modal") === 'true'){
                $('body').removeClass('modal-open');
                $('.modal-backdrop').remove();
                modal.remove();
            }
        }
    });
}


function processAjaxResponse(response) {
    var URL_TYPE = 'url';
    var MODAL_TYPE = 'modal';
    var MODAL_ALERT_TYPE = 'modal_alert';
    var PAGE_TYPE = 'page';
    var PAGE_TARGET_TYPE = 'page_target';
    var PAGE_ALERT_TYPE = 'page_alert';
    var SWAL_TYPE = 'swal';

    //console.log('ajax-resp ====== ' + response);

    if (isJson(response)) {
        var result = response.result;
        var status = result.status;
        var type = result.type;
        var content = result.content;

        if(type !== URL_TYPE && type !== SWAL_TYPE){
            if(type === PAGE_TYPE){
                content = $(content).find('body').addClass('insertion');
            }else{
                content = $(content).addClass('insertion');
            }
        }

        if (type === URL_TYPE) {
            var redirectURL = content;
            var redirectType = result.redirectType;
            if (redirectType === 'simple') {
                window.location.replace(resolveURL(redirectURL));
            } else if (redirectType === 'ajax') {
                handleAjaxGet(redirectURL, {});
            } else if (redirectType === 'tab') {
                window.open(resolveURL(redirectURL), '_blank');
            } else {
                swal("Oops!", "Something went wrong. No redirect route found", "error");
            }
        } else if (type === MODAL_TYPE) {
            //Removes a modal before showing another modal
            if (status === 'success') {
                overlayModal();
            }
            $(content).modal('show');
        } else if (type === MODAL_ALERT_TYPE) {

            if ($('body .modal .msg-div .alert').length > 0) {
                $('body .modal .msg-div .alert').html(content);
            } else {
                $('body .modal .msg-div').append(content);
            }
        } else if (type === PAGE_TYPE) {
            var dom = $(document.createElement("html"));
            dom[0].innerHTML = content;
            $('body').html(dom.find("body").html());

        } else if (type === PAGE_TARGET_TYPE) {
            var target = result.target;
            var element = target.element;
            var action = target.action;

            var targetElement = $('body').find(element);
            if (targetElement.length > 0) {
                if(action === 'append'){
                    targetElement.append(content);
                }else if(action === 'prepend'){
                    targetElement.prepend(content);
                }else if(action === 'embed'){
                    targetElement.html(content);
                }else if(action === 'replace'){
                    targetElement.replaceWith(content);
                }
            }
            targetElement.hideBusyAnimation();
        } else if (type === PAGE_ALERT_TYPE) {

            if ($('body .msg-div .alert').length > 0) {
                $('body .msg-div .alert').html(content);
            } else {
                $('body .msg-div').append(content);
            }

        } else if (type === SWAL_TYPE) {

            //Removes a modal before showing swal
            if (status === 'success') {
                overlayModal();
            }

            if (status === 'input') {
                swal({
                        html: true,
                        type: status,
                        title: result.title,
                        text: content,
                        confirmButtonColor: "#686765",
                        closeOnConfirm: false,
                        showLoaderOnConfirm: true,
                        inputPlaceholder: result.post.field
                    },
                    function (inputValue) {
                        if (inputValue === false) {
                            return false;
                        }

                        if (inputValue === "") {
                            swal.showInputError("Your input is required!");
                            return false;
                        }

                        var data = {};
                        data[result.post.field] = inputValue;

                        handleAjaxPost(result.post.url, data);
                    });
            } else {
                swal({
                        html: true,
                        type: status,
                        title: result.title,
                        text: content,
                        confirmButtonColor: "#686765",
                        closeOnConfirm: true
                    },
                    function (isConfirm) {
                        if (isConfirm) {
                            if (!$.isBlank(result.redirect)) {
                                var redirectType = result.redirect.redirectType;
                                if (redirectType === 'tab') {
                                    window.open(resolveURL(result.redirect.url), '_blank');
                                } else if (redirectType === 'ajax') {
                                    handleAjaxGet(result.redirect.url, {});
                                } else if (redirectType === 'simple') {
                                    if(!$.isBlank(result.redirect.url)){
                                        window.location.replace(resolveURL(result.redirect.url));
                                    }else{
                                        window.location.reload();
                                    }
                                }
                            }
                        }
                    });
            }
        }
    } else {
        var reason = "Could not parse response as json >>> Ajax Response must be json!";
        swal({
            html: true,
            type: "error",
            title: "Error!",
            text: "<p>Please copy the contents of the following and send to " +
            "<a href='mailto:" + SUPPORT_EMAIL + "' class='text-blue'>" + SUPPORT_EMAIL + "</a></p>" +
            "<pre>" + "<p>" + reason + "</p><hr>" + response + "</pre>"
        });
    }

}
