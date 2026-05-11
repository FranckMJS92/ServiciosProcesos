// URL base de la API
const API_URL = 'http://localhost:4000/product';
const CATEGORY_URL = 'http://localhost:4000/category';

// Variable para saber si estamos editando
let editMode = false;
let editId = null;

// Cargar categorias para el select
async function loadCategories() {
    try {
        const res = await fetch(CATEGORY_URL);
        const categories = await res.json();

        const select = document.getElementById('categoryId');
        select.innerHTML = '<option value="">Seleccione una categoría</option>';

        categories.forEach(cat => {
            select.innerHTML += `<option value="${cat.id}">${cat.name}</option>`;
        });
    } catch (error) {
        console.error('Error al cargar categorías:', error);
    }
}

// Cargar productos en la tabla
async function loadProducts() {
    try {
        const res = await fetch(API_URL);
        const products = await res.json();

        const tbody = document.getElementById('productTable');
        tbody.innerHTML = '';

        for (const p of products) {
            // Obtener el nombre de la categoría
            let categoryName = 'Sin categoría';
            if (p.category && p.category.name) {
                categoryName = p.category.name;
            }

            tbody.innerHTML += `
                <tr>
                    <td>${p.id}</td>
                    <td>${p.name}</td>
                    <td>${p.price} €</td>
                    <td>${p.stock}</td>
                    <td>${categoryName}</td>
                    <td>
                        <button class="edit" onclick="editProduct(${p.id})">Editar</button>
                        <button class="delete" onclick="deleteProduct(${p.id})">Eliminar</button>
                    </td>
                </tr>
            `;
        }
    } catch (error) {
        console.error('Error al cargar productos:', error);
        showMessage('Error al conectar con el servidor', 'error');
    }
}

// Guardar producto (Create o Update)
async function saveProduct() {
    const name = document.getElementById('name').value;
    const price = document.getElementById('price').value;
    const stock = document.getElementById('stock').value;
    const categoryId = document.getElementById('categoryId').value;

    const messageDiv = document.getElementById('message');

    // Validar campos obligatorios
    if (!name || !price || !stock || !categoryId) {
        showMessage('Por favor, rellena todos los campos', 'error');
        return;
    }

    if (parseFloat(price) <= 0) {
        showMessage('El precio debe ser mayor que 0', 'error');
        return;
    }

    if (parseInt(stock) < 0) {
        showMessage('El stock no puede ser negativo', 'error');
        return;
    }

    // Crear objeto con los datos
    const productData = {
        name: name,
        price: parseFloat(price),
        stock: parseInt(stock),
        category: { id: parseInt(categoryId) }
    };

    try {
        let url = API_URL;
        let method = 'POST';

        // Si estamos en modo edicion, cambiamos a PUT
        if (editMode && editId) {
            url = `${API_URL}/${editId}`;
            method = 'PUT';
        }

        const res = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productData)
        });

        const data = await res.json();

        if (!res.ok) {
            showMessage(data.message || 'Error al guardar el producto', 'error');
            return;
        }

        if (editMode) {
            showMessage(`Producto "${data.name}" actualizado correctamente`, 'success');
        } else {
            showMessage(`Producto "${data.name}" creado correctamente`, 'success');
        }

        // Limpiar formulario y recargar tabla
        clearForm();
        loadProducts();

    } catch (error) {
        console.error('Error al conectar con el servidor:', error);
        showMessage('Error al conectar con el servidor', 'error');
    }
}

// Editar producto (cargar datos en el formulario)
async function editProduct(id) {
    try {
        const res = await fetch(`${API_URL}/${id}`);
        const product = await res.json();

        if (!res.ok) {
            showMessage('Error al cargar el producto', 'error');
            return;
        }

        // Rellenar formulario
        document.getElementById('name').value = product.name;
        document.getElementById('price').value = product.price;
        document.getElementById('stock').value = product.stock;

        if (product.category && product.category.id) {
            document.getElementById('categoryId').value = product.category.id;
        }

        // Cambiar a modo edicion
        editMode = true;
        editId = id;

        // Cambiar el titulo del formulario
        document.querySelector('h2').textContent = 'Editar Producto';

        // Mostrar mensaje
        showMessage(`Editando producto: ${product.name}`, 'success');

    } catch (error) {
        console.error('Error al cargar el producto:', error);
        showMessage('Error al cargar el producto para editar', 'error');
    }
}

// Cancelar edicion
function cancelEdit() {
    editMode = false;
    editId = null;
    clearForm();
    document.querySelector('h2').textContent = 'Nuevo Producto';
    showMessage('Edición cancelada', 'success');
}

// Eliminar producto
async function deleteProduct(id) {
    if (!confirm('¿Estás seguro de eliminar este producto?')) {
        return;
    }

    try {
        const res = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        const data = await res.json();

        if (!res.ok) {
            showMessage(data.message || 'Error al eliminar el producto', 'error');
            return;
        }

        showMessage(data.message || 'Producto eliminado correctamente', 'success');
        loadProducts();

    } catch (error) {
        console.error('Error al eliminar:', error);
        showMessage('Error al conectar con el servidor', 'error');
    }
}

// Limpiar formulario
function clearForm() {
    document.getElementById('name').value = '';
    document.getElementById('price').value = '';
    document.getElementById('stock').value = '';
    document.getElementById('categoryId').value = '';
}

// Mostrar mensaje en el div
function showMessage(text, type) {
    const messageDiv = document.getElementById('message');
    messageDiv.className = type;
    messageDiv.textContent = text;

    // Limpiar mensaje después de 3 segundos
    setTimeout(() => {
        messageDiv.className = '';
        messageDiv.textContent = '';
    }, 3000);
}

// Cargar datos al iniciar la pagina
window.onload = () => {
    loadCategories();
    loadProducts();
};