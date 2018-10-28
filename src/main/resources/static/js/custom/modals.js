/**
 * @author: Olatunji O. Longe
 * @created: 15 Oct, 2017, 10:14 PM
 */
$(document).ready(function() {

	// Every time a modal is shown, if it has an autofocus element, focus on it.
	$(document).on('shown.bs.modal', '.modal', function() {
		$(this).find('[autofocus]').focus();
	});

	// Every time a bootstrap modal is hidden, clear any form in it.
	$(document).on('hidden.bs.modal', '.modal', function() {

		var modal = $(this);
		modal.find('form').clearForm();

		if(!$.isBlank(modal.attr('redirect'))){
			window.location.replace(modal.attr('redirect'));
		}

		//if it's an ajax loaded modal, remove it from the dom
		if(modal.hasClass("ajax-modal")){
			modal.remove();
		}

	});
	
	function showMessage(response) {
		if($('.msg-div .alert').length > 0){
			$('.alert').replaceWith(response);
		}else{
			$('.msg-div').append(response);
		}
	}

	function clearMessage() {
		$('.msg-div').empty();
	}
});

//Clear a form
$.fn.clearForm = function() {
	this.find(':input').not(':button, :submit, :reset, :hidden, :checkbox, :radio, .not-reset').val('');
	this.find(':checkbox, :radio').prop('checked', false);
};

