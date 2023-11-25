# Sistema de Gestión de Ventas
## _Spring Boot API_
#
#
#
### Descripción del Proyecto
Esta aplicación, desarrolada en Spring Boot utilizando Spring Data JPA y gestionada con Maven, proporciona una sólida API para la eficiente gestión de un sistema de ventas. Aquí están los aspectos más destacados:


### Funcionalidades Principales:
**CRUD para Entidades:**
- **Producto**: Operaciones completas de Crear, Leer, Actualizar y Eliminar.
- **Cliente**: Gestión completa de clientes con todas las operaciones CRUD.
- **Venta**: Implementación de CRUD para administrar las ventas.

**Funcionalidades Adicionales:**
- **Consultas con DTO**: Realización de consultas especializadas utilizando Data Transfer Objects (DTO).
- **Gestión de Stock**: Control del stock de productos, incluyendo consultas para productos con bajo stock.
- **Cálculos automáticos**: Cálculo automático del precio total en cada venta.
- **Gestión de Excepciones**: Manejo de excepciones para una experiencia de usuario más controlada.
- **Creación Automática de Registros Falsos**: Generación automática de registros ficticios en la base de datos para probar la app.
- **Consultas Específicas para Ventas**: Implementación de consultas personalizadas para obtener detalles específicos de las ventas.

### Tech
Esta aplicación se ha creado usando las siguientes tecnologias:


- [Java] - Lenguaje de programación principal.
- [Spring Boot] - Framework que simplifica el desarrollo de aplicaciones Java.
- [Spring Data JPA] - Facilita el acceso a bases de datos relacionales mediante el uso de JPA.
- [Hibernate] - Framework de mapeo objeto-relacional integrado con JPA.
- [Maven]- Herramienta de gestión de proyectos para la construcción y gestión de dependencias en el desarrollo de software Java.
- [MySQL] - El sistema de gestión de bases de datos relacional utilizado para almacenar y recuperar datos.
- [JavaFaker] - Una biblioteca que facilita la generación de datos ficticios para pruebas y desarrollo.

### Instrucciones de uso
**Configuración del proyecto:**
- Asegúrese de tener instalado Java y un IDE compatible con Maven como [IntelliJ] o [NetBeans].
- Clone este proyecto en su repositorio local: 
```sh
git clone https://github.com/tu-usuario/tu-proyecto.git
```
- Importe el proyecto en su IDE compatible con Maven.

**Configuración de la base de datos**:
- Configure las propiedades de conexión a su base de datos en 'application.properties'.
- La creación automática de la base de datos y las tablas está habilitada por defecto para facilitar la configuración. Éstas se crearán la primera vez quen ejecute la aplicación.

**Ejecución del Proyecto**:
- Ejecute la aplicación desde su IDE o mediante Maven:
```sh
mvn spring-boot:run
```

**Exploración de la API**:
- Acceda a los distintos endpoints según las rutas definidas en los controladores.
- En el código encontrará un archivo de Postman con las solicitudes request más destacadas.
- Se recomienda rellenar automáticamente la base de datos con datos fake para más comodidad. Realice las siguientes solicitudes GET, dónde {numeroDeRegistros} és un integer que marca la cantidad de registros a crear:
```sh
localhost:8080/fakeData/producto/{numeroDeRegistros}
localhost:8080/fakeData/cliente/{numeroDeRegistros}
```



   [Java]: <https://www.oracle.com/es/java/technologies/downloads/>
   [Spring Boot]: <https://spring.io/projects/spring-boot>
   [Spring Data JPA]: <https://spring.io/projects/spring-data-jpa>
   [Hibernate]: <https://hibernate.org>
   [MySQL]: <https://www.mysql.com>
   [JavaFaker]: <https://github.com/DiUS/java-faker>
   [Maven]: <https://maven.apache.org>
   [IntelliJ]: <https://www.jetbrains.com/es-es/idea/>
   [NetBeans]: <https://netbeans.apache.org/front/main/>
