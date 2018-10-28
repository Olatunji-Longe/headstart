/**
 * Created by olatunji Longe on 8/8/17.
 */
$(document).ready(function(){
    initParsley('form');

    window.Parsley.on('field:error', function(e){
        var form = e.$element.closest('form');
        if(form.find('[type=submit]').length > 0){
            var submitButton = $(form).find('.has-spinner[type=submit]');
            submitButton.removeSpinner();

        }else if(form.hasClass('ajax-form') && form.find('[type=button]').length > 0){
            var ajaxSubmitTriggerButton = $(form).find('.has-spinner[type=button]');
            ajaxSubmitTriggerButton.removeSpinner();

        }
    });
});

var parsleyConfig = {
    // This configuration ensures parsley error message is displayed after/under
    // an input-group and not just after the text field
    errorsContainer: function(errorContainer){
        var field = errorContainer.$element.closest('input');
        if(field.closest('.input-group'). length > 0){
            var inputGroup = field.closest('.input-group');
            errorContainer = $("<div class='input-group'></div>");
            errorContainer.insertAfter(inputGroup);
        }
        return errorContainer;
    }
};

function initParsley(selector) {
    if($(selector).is('form')){
        $(selector).parsley(parsleyConfig);
    }else {
        var forms = $(selector).find('form');
        if(forms.length > 0) {
            forms.each(function () {
                $(this).parsley(parsleyConfig);
            });
        }
    }
}

