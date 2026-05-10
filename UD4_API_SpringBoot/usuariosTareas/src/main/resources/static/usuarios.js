// URL base de la API
const API_URL = 'http://localhost:4000/user';

async function loadUser() {
    const res = await fetch(API_URL);

    // Convertumos la respuesta a JSON
    const users = await res.json();

    // Obtener el cuerpo de  la tabla => id uiserTable
    const tbody = document.getElementById('userTable');
    // Limpiamos los datos anteriores
    tbody.innerHTML = '';

    // Recorremos los usuarios y por cada uno creamos una fila en la tabla
    users.forEach(u => {
        tbody.innerHTML += `
            <tr>
                <td>${u.id}</td>
                <td>${u.nombre}</td>
                <td>${u.apellido}</td>
                <td>${u.email}</td>
                <td>
                    <button onclick="deleteUser(${u.id})">Eliminar</button>
                </td>
            </tr>
        `
    });
}

async function saveUser() {

    // Obtener los valores del formulario
    const nombre = document.getElementById('nombre').value;
    const apellido = document.getElementById('apellido').value;
    const email = document.getElementById('email').value;

    // Obtenemos el div para pintar el mensaje del servidor
    const messageDiv = document.getElementById('message');

    // Validar que todos los datos estan rellenos
    if (!nombre || !apellido || !email) {
        alert('Por favor, rellena todos los campos');
        return;
    }

    // Crear un objeto con los datos del usuario
    const userData = { nombre, apellido, email };

    try {
        // Realizamos FECTH
        const res = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        // Convertir la respuesta a JSON
        const data = await res.json();

        if (!res.ok) {
            messageDiv.className = 'error';
            messageDiv.textContent = data.message;
            return;
        }

        messageDiv.className = 'success';
        messageDiv.textContent = `Usuario con id ${data.id} con email ${data.email} creado correctamente`;

        // Cargar de nuevo los usuarios
        clearForm();
        loadUser();

    } catch (error) {
        console.error('Error al conectar con el servidor:');
        messageDiv.className = 'error';
        messageDiv.textContent = 'Error al conectar con el servidor. Por favor, inténtalo de nuevo más tarde.';
        return;
    }
}

async function clearForm() {
    // Obtener los valores del formulario
    document.getElementById('nombre').value = "";
    document.getElementById('apellido').value = "";
    document.getElementById('email').value = "";
}

async function deleteUser(id) {
    // Pedir confirmacion al usuario
    if (!confirm('¿Estas seguro de eliminar este usuario?')) {
        return;
    }

    // Peticion FETCH de tipo DELETE
    const res = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });

    if (!res.ok) {
        console.log('Error al eliminar el usuario');
        return;
    }

    // Cargar de nuevo los usuarios
    loadUser();
}

// Cuando carguemos la pagina, tenemos que ejecutar loadUser
window.onload = loadUser;