
$(document).ready(function(){
    $('.question + .answer').hide();
    $('.question').on('click', function(){
        $(this).next('.answer').slideToggle('fast');
   });
});

$(document).ready(function() {

    $('.information').hover(function(){
        // Hover over code
        var title = $(this).attr('title');
        $(this).data('tipText', title).removeAttr('title');
        $('<p class="tooltip"></p>')
            .text(title)
            .appendTo('body')
            .fadeIn('slow');
    }, function() {
        // Hover out code
        $(this).attr('title', $(this).data('tipText'));
        $('.tooltip').remove();
    }).mousemove(function(e) {
        var mousex = e.pageX + 20; //Get X coordinates
        var mousey = e.pageY + 10; //Get Y coordinates
        $('.tooltip')
            .css({ top: mousey, left: mousex })
    });
    
    // Format JSON
    $("code.json").each(function() {
        try {
            $(this).html( JSON.stringify(JSON.parse($(this).html()), null ,2) );
        }
        catch (e) {
            // Probably not a JSON body
        }
    });

});

var addEvent = function(object, type, callback) {
    if (object == null || typeof(object) == 'undefined') return;
    if (object.addEventListener) {
        object.addEventListener(type, callback, false);
    } else if (object.attachEvent) {
        object.attachEvent("on" + type, callback);
    } else {
        object["on"+type] = callback;
    }
};
