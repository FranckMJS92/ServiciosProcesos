EJERCICIO 1)
Una cola (queue) es una estructura de datos, es una sucesión de varios elementos del mismo tipo, que se gestionan siguiendo el principio FIFO (First In, First Out), es decir, en la que el primer elemento que entra es el primero en salir.
▪ Ejemplo: podemos imaginar que una cola es similar a las colas que se hacen en el supermercado o cuando esperas a que te atiendan en un banco. El primero que llega es el primero en ser atendido.

▪ Crea la interfaz ColaInterfaz, que contenga los métodos necesarios para poder trabajar con la estructura de datos tipo Cola:
encolar: para añadir un nuevo elemento al final del cola
desencolar: elimina y devuelve el primer elemento de la cola
getFirst: devuelve el primer elemento de la cola sin eliminarlo
mostrarElementos: muesta todos los elementos de la cola en orden.

NOTA: para que la cola funcione con cualquier tipo de datos, usa una interfaz genérica.

▪ Implementa esta interfaz en una clase llamada ColaArrayList que realice estas operaciones, usa un ArrayList para almacenar los elementos y permita realizar todas las operaciones definidas en la interfaz.

▪ Crea la clase Principal que crea un objeto de la clase ColaArrayList y que use todos sus métodos.