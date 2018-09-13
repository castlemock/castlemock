/**
 * Only enable "elementIds" if "id" element's value is within "enabledValues",
 * otherwise disable the "elementIds"
 */
function toggleEnabled(id, enabledValues, elementIds) {

    enabledValues = enabledValues || [];
    elementIds = elementIds || [];

    var element = document.getElementById(id);

    var valueNotFound = enabledValues.indexOf(element.value) === -1;

    elementIds.forEach(function (toDisable) {
        if (valueNotFound){
            document.getElementById(toDisable).classList.add('disabled');
        }else{
            document.getElementById(toDisable).classList.remove('disabled');
        }
    })
}

