/**
 * Created by olatunji Longe on 5/12/17.
 */

$(document).ready(function () {

    $(document).on('click', '.has-spinner', function(e){
        $(this).prependSpinner();
    });
});

var busy = "<div class='sk-circle'> \
				<div class='sk-circle1 sk-child'></div> \
				<div class='sk-circle2 sk-child'></div> \
				<div class='sk-circle3 sk-child'></div> \
				<div class='sk-circle4 sk-child'></div> \
				<div class='sk-circle5 sk-child'></div> \
				<div class='sk-circle6 sk-child'></div> \
				<div class='sk-circle7 sk-child'></div> \
				<div class='sk-circle8 sk-child'></div> \
				<div class='sk-circle9 sk-child'></div> \
				<div class='sk-circle10 sk-child'></div> \
				<div class='sk-circle11 sk-child'></div> \
				<div class='sk-circle12 sk-child'></div> \
			</div>";

$.fn.showBusyAnimation = function(){
    this.append(busy);
    this.find('.sk-circle').show();
}

$.fn.hideBusyAnimation = function(){
    var busyAnimation = this.find('.sk-circle');
    if(busyAnimation){
        busyAnimation.hide();
        busyAnimation.remove();
    }
}

$.fn.isBusyAnimationVisible = function(){
    return this.find('.sk-circle').length > 0;
}

$.fn.showLoadingAnimation = function(){
    this.append('<div class="animation-load"><div class="loading"></div></div>');
    this.find(".animation-load").delay(200).fadeIn("slow");
    this.find(".loading").delay(100).fadeIn();
}

$.fn.hideLoadingAnimation = function(){
    var loadingAnimation = this.find(".animation-load");
    if(loadingAnimation){
        loadingAnimation.find('.loading').delay(100).fadeOut();
        loadingAnimation.delay(200).fadeOut("slow");
        loadingAnimation.remove();
    }
}

$.isLoadingAnimationVisible = function(){
    return $(document).find('.loading').length > 0;
}

$.fn.prependSpinner = function(){
    this.prepend('<span class="spinner"><i class="fa fa-spinner fa-spin"></i>&nbsp;</span>');
    this.attr("disabled", true);
    if(this.closest('form').length > 0 && this.attr('type') === 'button'){
        this.closest('form').submit();
    }
}

$.fn.removeSpinner = function(){
    var spinningAnimation = this.find('.spinner');
    if(spinningAnimation){
        spinningAnimation.closest('.has-spinner').removeAttr("disabled");
        spinningAnimation.remove();
    }
}




