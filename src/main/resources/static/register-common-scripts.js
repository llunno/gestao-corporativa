function maskcpf(v){
    v=v.replace(/\D/g,"");
    v=v.replace(/(\d{3})(\d)/,"$1.$2");
    v=v.replace(/(\d{3})(\d)/,"$1.$2");
    v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2");
    return v;
}

const cpfElement = document.getElementById("cpf")
cpfElement.setAttribute("maxlength", "14")
cpfElement.oninput = function(){
    cpfElement.value = maskcpf(cpfElement.value);
}

function mascaraMoeda(event) {
    const onlyDigits = event.target.value
        .split("")
        .filter(s => /\d/.test(s))
        .join("")
        .padStart(3, "0")
    const digitsFloat = onlyDigits.slice(0, -2) + "." + onlyDigits.slice(-2)
    event.target.value = maskCurrency(digitsFloat)
}

function maskCurrency(valor, locale = 'pt-BR', currency = 'BRL') {
    return new Intl.NumberFormat(locale, {
        style: 'currency',
        currency
    }).format(valor)
}

const remuneracaoField = document.querySelector(".input-remuneracao");

remuneracaoField.addEventListener("input", mascaraMoeda);

document.querySelector("#form-main").addEventListener("submit", function(event) {
    remuneracaoField.value = remuneracaoField.value.replace(/\D/g,"");
    return true;
});

$(document).ready(function(){
    $('.date-input').datepicker({
        format: 'dd/mm/yyyy',
        language: 'pt-BR',
        autoclose: true
    });
});

const checkboxDivs = document.querySelectorAll(".div-checkbox");
checkboxDivs.forEach(function(element) {

    const checkbox = element.querySelector("input[type=checkbox]");
    checkbox.addEventListener("click", function(event) {
        event.stopPropagation();
        element.classList.toggle("div-checkbox-selected");
    });

    element.addEventListener("click", function() {
        checkbox.checked = !checkbox.checked;
        element.classList.toggle("div-checkbox-selected");
    });
});

const radioDivs = document.querySelectorAll(".div-radio");
radioDivs.forEach(function(radioDiv) {
    const radio = radioDiv.querySelector("input[type=checkbox]");

    radio.addEventListener("click", function(event) {
        event.stopPropagation();
        radio.checked = !radio.checked;
        radioDiv.dispatchEvent(new Event("click"));
        console.log("radio click")
    });

    radioDiv.addEventListener("click", function(event) {
        console.log("activated trought radio")
        event.stopPropagation();
        const clickedRadio = radioDiv.querySelector("input[type=checkbox]");
        if (!clickedRadio.checked) {
            radioDivs.forEach(function(radioDiv) {
                radioDiv.classList.remove("div-checkbox-selected");
                const radio = radioDiv.querySelector("input[type=checkbox]");
                radio.checked = false;
            });
            clickedRadio.checked = true;
            radioDiv.classList.add("div-checkbox-selected");
        } else {
            clickedRadio.checked = false;
            radioDiv.classList.remove("div-checkbox-selected");
        }
    });
});