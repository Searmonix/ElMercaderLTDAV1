const emailFormat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

const nameFormat = /^\w[A-Za-z]*$/;

let validEmail = false;

let validPassword = false;

let matchingPassword = false;

let validName = false;

let form = document.getElementById("AccCreationForm");

console.log(form);

function checkForEmail(email) {
    let emailFound = false;
    $.ajax({
        url: 'http://localhost:8080/api/user/' + email,
        type: 'GET',
        dataType: 'json',
        // El proceso de búsqueda tendrá que esperar hasta que sea iniciada esta función
        async: false,
        success: function(response) {
            emailFound = Boolean(response)
        },

        error: function() {
            console.log("No se puede validar el e-mail");
        }
    });

    return emailFound;
}

form.addEventListener("input", function(event) {

    const data = new FormData(form);
    const name = data.get("uname");
    const email = data.get("e-mail");
    const password = data.get("password");
    const passwordConfirmation = data.get("cPassword");

    validName = nameFormat.test(name);
    validEmail = emailFormat.test(email);
    validPassword = password.length >= 6 && password.length !== 0;
    matchingPassword = password === passwordConfirmation && password !== "";

    if (validName) {
        document.getElementById("AccName").classList.remove("is-invalid");
        document.getElementById("AccName").classList.add("is-valid");
    } else {
        document.getElementById("AccName").classList.add("is-invalid");
        document.getElementById("AccName").classList.remove("is-valid");
        document.getElementById("ErrorNombre").innerText = "Ingresa un nombre válido";
    }

    if (validEmail) {
        document.getElementById("AccEmail").classList.remove("is-invalid");
        document.getElementById("AccEmail").classList.add("is-valid");
    } else {
        document.getElementById("AccEmail").classList.add("is-invalid");
        document.getElementById("AccEmail").classList.remove("is-valid");
        document.getElementById("ErrorEmail").innerText = "Ingresa un e-mail válido";
    }

    if (validPassword) {
        document.getElementById("AccPassword").classList.remove("is-invalid");
        document.getElementById("AccPassword").classList.add("is-valid");
    } else {
        document.getElementById("AccPassword").classList.add("is-invalid");
        document.getElementById("AccPassword").classList.remove("is-valid");
        document.getElementById("ErrorPassword").innerText = "Tu constraseña es muy corta";
    }

    if (matchingPassword) {
        document.getElementById("AccPasswordConfirmation").classList.remove("is-invalid");
        document.getElementById("AccPasswordConfirmation").classList.add("is-valid");
    } else {
        document.getElementById("AccPasswordConfirmation").classList.add("is-invalid");
        document.getElementById("AccPasswordConfirmation").classList.remove("is-valid");
        document.getElementById("ErrorConfPassword").innerText = "Las contraseñas no coinciden";   
    }
    
});


form.addEventListener("submit", function(event) {
    


    let email = document.getElementById("AccEmail").value;

    if (!form.checkValidity() || !validName || !validEmail || !validPassword || !matchingPassword || checkForEmail(email)) {

        if (validEmail && checkForEmail(email)) {
            document.getElementById("AccEmail").classList.add("is-invalid");
            document.getElementById("AccEmail").classList.add("is-valid");
            document.getElementById("ErrorEmail").innerText = "Este e-mail ya está registrado";
           
        }
       
        event.preventDefault();

        event.stopImmediatePropagation();   

    } else {

        let dataObject = {
            'email': document.getElementById("AccEmail").value,
            'password': document.getElementById("AccPassword").value,
            'name': document.getElementById("AccName").value
        }

        $.ajax({    
            url: 'http://localhost:8080/api/user/new',
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            async: false,
            data: JSON.stringify(dataObject),
            success: function(response) {
                sessionStorage.setItem('userName', response.name);
                
                Swal.fire(
                    'Genial',
                    'Tu cuenta ha sido creada',
                    'success'
                  )
            },
            error: function() {
                alert("Oh, no, hermano")
            }
        })

    }
})

