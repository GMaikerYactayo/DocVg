
function calcular() {
    let VERMESANT = document.getElementById('frModificarDetalleInfo:VERMESANT');
    let CREDET = document.getElementById('frModificarDetalleInfo:CREDET_input');
    let PRODET = document.getElementById('frModificarDetalleInfo:PRODET_input');
    let SEMDET = document.getElementById('frModificarDetalleInfo:SEMDET_input');
    let COSDET = document.getElementById('frModificarDetalleInfo:COSDET_input');
    let PERDET = document.getElementById('frModificarDetalleInfo:PERDET_input');
    let AFEDET = document.getElementById('frModificarDetalleInfo:AFEDET_input');
    let ROTDET = document.getElementById('frModificarDetalleInfo:ROTDET_input');
    let VERDMES = document.getElementById('frModificarDetalleInfo:VERDMES');
    let PRODDET = document.getElementById('frModificarDetalleInfo:PRODDET_input');
    let PRECHA = document.getElementById('frModificarDetalleInfo:PRECHA_input');

    if(isNaN(parseFloat(SEMDET.value))){SEMDET.value = 0.00;}
    if(isNaN(parseFloat(COSDET.value))){COSDET.value = 0.00;}
    if(isNaN(parseFloat(PERDET.value))){PERDET.value = 0.00;}
    if(isNaN(parseFloat(ROTDET.value))){ROTDET.value = 0.00;}
    if(isNaN(parseFloat(AFEDET.value))){AFEDET.value = 0.00;}
    
    let total = (parseFloat(VERMESANT.innerHTML) + parseFloat(SEMDET.value)) - 
                (parseFloat(COSDET.value) 
                + parseFloat(PERDET.value) 
                + parseFloat(ROTDET.value)
                + parseFloat(AFEDET.value));
    console.log(total);
    VERDMES.innerHTML = total;

}
