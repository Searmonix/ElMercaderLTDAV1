let sessionName = sessionStorage.getItem('userName');

if (sessionName === "NO DEFINIDO") {
    document.getElementById("userRegistered").innerText = "No existe un usuario con esas credenciales";
} else if (sessionName != null) {
    document.getElementById("userRegistered").innerText = "Bienvenido " + sessionName;

    $.ajax({
        url: 'http://localhost:8080/api/user/all',
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            showData(response);
        },
        error: function() {
            console.log("Error al encontrar usuarios");
        }
    });
}

function showData(data) {
    let appendUserTable = '';

    appendUserTable += '<thead><tr><th>ID</th><th>E-MAIL</th><th>PASSWORD</th><th>NAME</th></tr></thead><tbody>';

    data.forEach((data) => {
        appendUserTable += '<tr>';
        Object.values(data).forEach((userAttributes) => {
            appendUserTable += '<td>' + userAttributes + '</td>';
        });

        appendUserTable += '</tr>';
    });

    document.getElementById('userTable').innerHTML = appendUserTable;
}