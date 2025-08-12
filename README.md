Conversor de Monedas
Proyecto en Java que convierte entre diferentes monedas en tiempo real usando una API de tasas de cambio y guarda el historial de conversiones en un archivo JSON.

Requisitos
Java 8 o superior.

Biblioteca Gson para manejo de JSON.

Instalación y Configuración
Descarga este repositorio.

Descarga gson-2.13.x.jar o + desde el enlace siguiente.
https://mvnrepository.com/artifact/com.google.code.gson/gson

En tu IDE (por ejemplo IntelliJ IDEA):

Ve a File → Project Structure → Libraries → + → Java

Selecciona el archivo gson-2.13.x.jar y aplica los cambios.

Asegúrate de que la carpeta src/principal/recursos esté marcada como Resources Root.

Configura tu API Key en config.properties.

Uso
Ejecuta la clase com.conversor.menu.Principal.

Selecciona las monedas y el monto a convertir.

El historial de conversiones se guarda en historial.json en la carpeta raíz del proyecto.

Autor
Waldiuxx
