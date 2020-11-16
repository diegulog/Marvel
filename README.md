# Marvel

**Resumen:**

Aplicación Android/iOS utilizando api de marvel https://developer.marvel.com/


## Implementation



**iOS**

- **CharactersCollectionController**: muestra un listado de los personajes Marvel en una  vista de coleccion

- **DetailsViewController**: muestra los detalles de un personaje


**Android**

Se ha desarrollado utilizando las librerías de  Android Jetpack:

 * [Android KTX][0] - Escribe código Kotlin más conciso e idiomático.
* [Architecture][1] - Una colección de bibliotecas que lo ayudan a diseñar robustas, probables y
   aplicaciones mantenibles. Comience con clases para administrar el ciclo de vida de los componentes de la interfaz de usuario y manejar datos
   persistencia.

  * [LiveData][2] -Cree objetos de datos que notifiquen a las vistas cuando cambia la base de datos subyacente.
  * [Paging][3] - La biblioteca de paginación le ayuda a cargar y mostrar pequeños fragmentos de datos a la vez.
  * [Navigation][4] - Maneje todo lo necesario para la navegación dentro de la aplicación.
  * [ViewModel][5] - Almacene datos relacionados con la interfaz de usuario que no se destruyen en las rotaciones de aplicaciones. Programe fácilmente
      Tareas asincrónicas para una ejecución óptima.
  * [Fragment][6] - Una unidad básica de IU componible.

* Bibliotecas de terceros y diversas
  * [Glide][7] para cargar imágenes
  * [Kotlin Coroutines][8] para administrar subprocesos en segundo plano con código simplificado y reducir la necesidad de devoluciones de llamada

[0]: https://developer.android.com/kotlin/ktx
[1]: https://developer.android.com/jetpack/arch/
[2]: https://developer.android.com/topic/libraries/architecture/paging
[3]: https://developer.android.com/topic/libraries/architecture/livedata
[4]: https://developer.android.com/topic/libraries/architecture/navigation/
[5]: https://developer.android.com/topic/libraries/architecture/viewmodel
[6]: https://developer.android.com/guide/components/fragments
[7]: https://bumptech.github.io/glide/
[8]: https://kotlinlang.org/docs/reference/coroutines-overview.html


## Requirements
**Android**
 - Android Studio 4.+
 - minSdkVersion 19

**iOS**
 - Xcode Version 12.0.1
 - Swift 5 
 - iOS 13.0


## License

    Copyright 2020 Diego Gl

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
