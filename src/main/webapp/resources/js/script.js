
String.prototype.capitalize = function () {
    return this.replace(/(^|\s)([a-z-á-é-í-ó-ú])/g, function (m, p1, p2) {
        return p1 + p2.toUpperCase();
    });
};

function upperCase(e) {
    var texto = validateSpace(e.value.toUpperCase());
    e.value = texto;
}

function capitalize(e) {
    e.value = e.value.toLowerCase();
    var texto = e.value.toString();
    texto = validateSpace(texto.capitalize());
    e.value = texto;
}

function validateSpace(texto) {
    let regexp = / +/g;
    texto = texto.replace(regexp, " ");
    return texto;
}

//onblur function
function requiredField(e) {
    console.log(isNaN(e.value));

    if (e.value.toString().trim().length < 1) {
        //red border
        PF('growlId').renderMessage({"summary": "El campo es obligatorio",
            "detail": "",
            "severity": "error"});
        $("#j_idt50_container").css("z-index","2000");
        $(e).addClass("ui-state-error");
    } else if (e.type == "email") {
        console.log("this is an email type");

        if (e.value.indexOf("@") != -1 && e.value.indexOf(".") != -1) {
            //green border
            e.style.borderColor = "#2ecc71";
        } else {
            //red border
            e.style.borderColor = "#e74c3c";
        }

    } else {
        //green border
        $(e).removeClass("ui-state-error");
    }
}


function validateMask(e) {
    if (isNaN(e.value)) {
        //red border
        PF('growlId').renderMessage({"summary": "El campo es obligatorio",
            "detail": "",
            "severity": "error"});

        $(e).addClass("ui-state-error");
    } else {
        //green border
        $(e).removeClass("ui-state-error");
    }
}

function showGrowl(){
    console.log("aaaaa");
    PF('growlId').renderMessage({"summary": "El campo es obligatorio",
            "detail": "",
            "severity": "error"});
}
