# Activos Fijos App

Esta aplicación de Android permite gestionar activos fijos, incluyendo la creación, visualización, actualización y eliminación de activos utilizando Firebase Firestore para el almacenamiento de datos.

## Requisitos

- **Android Studio**: Asegúrate de tener la última versión de Android Studio instalada.
- **Java Development Kit (JDK)**: Se recomienda usar JDK 11 o superior.
- **Firebase Firestore**: La app utiliza Firebase Firestore como base de datos, por lo que necesitarás configurar un proyecto de Firebase.

## Configuración

### 1. Clonar el proyecto

Clona el repositorio en tu entorno local:

```bash
https://github.com/Selkis90/ActivosFijosApp.git

2. Abrir el proyecto en Android Studio
Abre Android Studio.
Selecciona File > Open.
Navega hasta el directorio donde clonaste el proyecto y selecciónalo.
3. Configurar Firebase
Para integrar Firebase Firestore con tu aplicación:

Ve a Firebase Console.
Crea un nuevo proyecto de Firebase.
En la sección de Build selecciona Firestore Database y habilítalo.
Añade tu aplicación de Android al proyecto de Firebase.
Descarga el archivo google-services.json y colócalo en el directorio app/ de tu proyecto.
4. Actualizar las dependencias
Abre el archivo build.gradle del módulo app y asegúrate de tener las siguientes dependencias:
// Firebase dependencies
implementation platform('com.google.firebase:firebase-bom:31.2.0')
implementation 'com.google.firebase:firebase-firestore'
Luego, sincroniza las dependencias haciendo clic en Sync Now cuando Android Studio lo indique.

5. Configuración de permisos en AndroidManifest.xml
Verifica que el archivo AndroidManifest.xml esté configurado correctamente para acceder a internet, lo que es necesario para conectarse a Firebase:
<uses-permission android:name="android.permission.INTERNET" />
6. Ejecutar la aplicación
Conecta un dispositivo Android o inicia un emulador desde Android Studio.
Haz clic en el botón Run o utiliza el atajo de teclado Shift + F10 para ejecutar la aplicación.
7. Interactuar con la aplicación
La aplicación permite agregar un nuevo activo ingresando un nombre, descripción y valor.
Puedes eliminar o editar los activos directamente desde la interfaz.
Todos los datos se almacenan en Firebase Firestore y se sincronizan en tiempo real.
Estructura del Proyecto
MainActivity.kt: La actividad principal que maneja la interfaz de usuario y las interacciones con Firestore.
ActivoFijoAdapter.kt: Adaptador para mostrar los activos en un RecyclerView.
ActivoFijo.kt: Modelo de datos que representa un activo fijo.
Tecnologías y Librerías
Kotlin: Lenguaje de programación principal.
Android SDK: Herramientas de desarrollo para aplicaciones Android.
Firebase Firestore: Base de datos en la nube para almacenar los datos.
RecyclerView: Componente de Android para listas eficientes.
View Binding: Para acceder a los elementos de la interfaz de usuario de manera segura.
Firebase Firestore
Asegúrate de que Firestore esté configurado correctamente y que las reglas de seguridad te permitan leer y escribir datos. Puedes modificar las reglas en la consola de Firebase en la sección de Firestore Database > Rules:


service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if true;
    }
  }
}



Versiones recomendadas
Gradle: 8.0 o superior
Firebase SDK: Firebase BoM 31.2.0
Android SDK: Nivel de API mínimo 21 (Android 5.0 Lollipop)
Posibles Errores
Error al conectar con Firebase: Verifica que el archivo google-services.json esté en la ubicación correcta y que hayas agregado el ID de la aplicación en la consola de Firebase.
Firestore Rules Error: Asegúrate de que las reglas de Firestore estén configuradas para permitir la lectura y escritura de datos según tus necesidades de desarrollo.
Contribución
Las contribuciones son bienvenidas. Si deseas colaborar, por favor crea un pull request o abre una nueva issue en el repositorio.

















