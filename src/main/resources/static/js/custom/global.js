/**
 * Created by olatunji Longe on 8/8/17.
 */
var JSON_MIME_TYPE="application/json";
var FORM_URL_MIME_TYPE="application/x-www-form-urlencoded";
var SUPPORT_EMAIL = "olatunji.longe@gmail.com";

$.isBlank = function(obj) {
    return (!obj || $.trim(obj) === "");
};

$.randomString = function(){
    return ((moment().valueOf()*Math.random() + 1)).toString(36).substring(2, 7)+(moment().valueOf());
};

// Ref : https://stackoverflow.com/questions/901115/how-can-i-get-query-string-values-in-javascript/21152762#3855394
$.QueryString = (function(paramsArray) {
    var params = {};
    for (var i = 0; i < paramsArray.length; ++i){
        var param = paramsArray[i].split('=', 2);
        if (param.length === 2){
            params[param[0]] = decodeURIComponent(param[1].replace(/\+/g, " "));
        }
    }
    return params;
})(window.location.search.substr(1).split('&'));

$.toJson = function(data){
    try{
        return $.parseJSON(data)
    }catch(error){
        return null;
    }
};

$.toDataArray  = function(data){
    var dataArray = [];
    $.each(data, function(key, value){
        dataArray.push({"name":key, "value":value});
    });
    return dataArray;
};

String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.replace(new RegExp(search, 'g'), replacement);
};

String.prototype.contains = function(search) {
    return (this.indexOf(search) != -1);
};

String.prototype.toNum = function(){
    return numeral("'" + this + "'").value();
    //return !isNaN(this) ? Number(this) : 0;
};

String.prototype.equalsIgnoreCase = function(string){
    return this.toUpperCase() === string.toUpperCase();
};

String.prototype.strFormat = function() {
    var str = this.toString();
    if (!arguments.length)
        return str;
    var args = (("string" === typeof arguments[0] || "number" === typeof arguments[0]) ? arguments : arguments[0]);
    for (var arg in args){
        str = str.replace(RegExp("\\{" + arg + "\\}", "gi"), args[arg]);
    }
    return str;
};

String.prototype.endsWith = function(charSequence) {
    var string = this.toString();
    return string.slice(0 - charSequence.length) === charSequence;
};

String.prototype.removeLast = function(charSequence) {
    var string = this.toString();
    if (string.slice(0 - charSequence.length) === charSequence) {
        return string.slice(0, (0 - charSequence.length));
    }
    return string;
};


//Check if an element exists in dom
$.fn.exists = function() {
    return typeof this !== typeof undefined && this.length > 0;
};

$.fn.existsInDOM = function() {
    return $(this).length > 0;
};

//Clear a form
$.fn.clearForm = function() {
    this.find(':input').not(':button, :submit, :reset, :hidden, :checkbox, :radio, .not-reset').val('');
    this.find(':checkbox, :radio').prop('checked', false);
};

$.fn.setCaretPosition = function(caretPos) {
    var range;

    var elem = this[0];
    if (elem.createTextRange) {
        range = elem.createTextRange();
        range.move('character', caretPos);
        range.select();
    } else {
        elem.focus();
        elem.setSelectionRange(caretPos, caretPos);
    }
};










