/**
 * @author: Olatunji O. Longe
 * @created: 20 Oct, 2018, 4.08 PM
 */
$(document).ready(function(){
    initPagination('.pagination');
});

var paginationConfig = {
    visiblePages: 7,
    next: 'Next',
    prev: 'Prev'/*,
    onPageClick: function (event, page) {
        handlePageButtonClick(page);
    }*/
};

function initPagination(selector){
    if($(selector).hasClass('pagination')){
        paginationConfig.totalPages=$(selector).attr('data-pages');
        $(selector).twbsPagination(paginationConfig).on('page',function (evt, page) {
            handlePageButtonClick(page);
        });
    }else{
        var pagination = $(selector).find('.pagination');
        if(pagination.length > 0){
            pagination.each(function(){
                paginationConfig.totalPages=pagination.attr('data-pages');
                pagination.twbsPagination(paginationConfig).on('page',function (evt, page) {
                    handlePageButtonClick(page);
                });
            });
        }
    }
}

function handlePageButtonClick(page){
    //var clickSource = $('.pagination .page-item .page-link:contains('+page+')');
    var url = $('.pagination').attr('data-href');
    var data = $.toDataArray({'page': page});
    handleAjaxPost(url, data);
}
