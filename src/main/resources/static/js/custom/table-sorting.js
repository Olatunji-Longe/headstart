/**
 * @author: Olatunji O. Longe
 * @created: 20 Oct, 2018, 5.35 PM
 */
$(document).ready(function(){
    initTableSorting('.table-sortable');
});


var tableSortingConfig = {
    ascIcon: '<i class="fa fa-sort-up margin-left-20" aria-hidden="true" title="sort by title"></i>',
    descIcon: '<i class="fa fa-sort-down margin-left-20" aria-hidden="true" title="sort by title"></i>'
};

function initTableSorting(selector){
    if($(selector).hasClass('table-sortable')){
        handleSorting(selector, tableSortingConfig);
    }else{
        var sortable = $(selector).find('.table-sortable');
        if(sortable.length > 0){
            sortable.each(function(){
                handleSorting(selector, tableSortingConfig);
            });
        }
    }
    $(selector).find('#bk-title').click();
}

function handleSorting(selector, tableSortingConfig){
    $(selector).find('th').on('click', function(){
        var table = $(this).parents('table').eq(0);
        var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()));
        this.asc = !this.asc;

        if(this.asc){
            $(this).find('i').replaceWith(tableSortingConfig.ascIcon);
        }else if (!this.asc){
            rows = rows.reverse();
            $(this).find('i').replaceWith(tableSortingConfig.descIcon);
        }

        for (var i = 0; i < rows.length; i++){
            table.append(rows[i]);
        }
    });
}

function comparer(index) {
    return function(a, b) {
        var cellValueA = getCellValue(a, index);
        var cellValueB = getCellValue(b, index);
        return $.isNumeric(cellValueA) && $.isNumeric(cellValueB) ? cellValueA - cellValueB : cellValueA.toString().localeCompare(cellValueB);
    }
}

function getCellValue(row, index){
    return $(row).children('td').eq(index).text();
}