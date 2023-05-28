// máscara de cpf
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


// máscara moeda
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

// chamada da função de máscara de moeda
remuneracaoField.addEventListener("load", mascaraMoeda);
remuneracaoField.addEventListener("input", mascaraMoeda);

// remoção de caracteres não numéricos da remuneração && checkin de campos obrigatórios ao ao enviar formulário
const form = document.querySelector("#form-main");
form.addEventListener("submit", function(event) {
    remuneracaoField.value = remuneracaoField.value.replace(/\D/g,"");
    remuneracaoField.value = remuneracaoField.value.substring(0, remuneracaoField.value.length - 2);
    let decimalPart = remuneracaoField.value.substring(remuneracaoField.value.length - 2);
    if (decimalPart.length === 1) {
        decimalPart = "0" + decimalPart;
    }
    remuneracaoField.value = remuneracaoField.value + "." + decimalPart;

    const superioresCheckboxes = document.querySelectorAll(".superior-selection");
    const superioresChecked = Array.from(superioresCheckboxes).some(function(checkbox) {
        return checkbox.checked;
    });

    if (superioresChecked === false) {
        return true;
    }
    else {
        return true;
    }
});

// init datepicker e máscara de data
$(document).ready(function(){
    $('.date-input').datepicker({
        format: 'dd/mm/yyyy',
        language: 'pt-BR',
        autoclose: true
    });
});

// adicionado efeito de seleção para checkbox
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

// modificando comportamento de checkbox para radio, selecionando apenas um e permitindo desmarcar o selecionado
const radioDivs = document.querySelectorAll(".div-radio");
radioDivs.forEach(function(radioDiv) {
    const radio = radioDiv.querySelector("input[type=checkbox]");

    radio.addEventListener("click", function(event) {
        event.stopPropagation();
        if (radio.name === "presidente-chefe") {
            event.preventDefault();
            return;
        }

        radio.checked = !radio.checked;
        radioDiv.dispatchEvent(new Event("click"));
    });

    radioDiv.addEventListener("click", function(event) {
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

            if (clickedRadio.name === "presidente-chefe") {
                event.preventDefault();
                return;
            }

            clickedRadio.checked = false;
            radioDiv.classList.remove("div-checkbox-selected");
        }
    });
});