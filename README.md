# FrostVault

## English

### Description

FrostVault is a Springboot Java-based product sales system developed as a professional challenge during a Java bootcamp. The application features a shopping cart and associated purchase orders, adhering to the Model-View-Controller (MVC) architecture and incorporating several design patterns to ensure maintainability and scalability.

### Features

- Product catalog management
- Shopping cart functionality
- Purchase order processing
- User authentication and authorization
- Unit tests ensuring code reliability

### Design Patterns Implemented

- **Model-View-Controller (MVC)**: Separates the application into three interconnected components to separate internal representations of information from the ways that information is presented and accepted from the user.
- **Strategy Pattern**: Used for implementing various payment methods or discount strategies, allowing the algorithm to vary independently from clients that use it.
- **Observer Pattern**: Facilitates communication between components, such as updating the view when the model changes.
- **Factory Pattern**: Manages the creation of complex objects, promoting loose coupling and adherence to the Single Responsibility Principle.
- **Singleton Pattern**: Included in Spring Framework to allow IoC (Inversion of Control) container to select which instance of a layer is better depending on the context.
- **Repository Pattern**: Included in Spring Framework ORM abstracting the logic in CRUD operations.

### Prerequisites

- Java 11 or higher
- Maven 3.6.0 or higher

### Installation

1. Clone this repository:

   ```bash
   git clone https://github.com/jeancaardo/FrostVault.git
   ```

2. Navigate to the project directory:

   ```bash
   cd FrostVault
   ```

3. Compile the project using Maven:

   ```bash
   mvn clean install
   ```

### Usage

To run the application locally:

```bash
mvn spring-boot:run
```

Once started, the application will be available at `http://localhost:8080`.

### Testing

To execute unit tests:

```bash
mvn test
```

### Contributions

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/new-feature`).
3. Commit your changes with descriptive messages using [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0-beta.2/)
4. Push to the branch (`git push origin feature/new-feature`).
5. Open a Pull Request detailing the changes made.

### License

This project is licensed under the MIT License. See the `LICENSE` file for details.

### Contact

For questions or suggestions, please contact me at [jeancaardo@gmail.com] or create an issue in this repository.

---

## Español

### Descripción

FrostVault es un sistema de ventas de productos basado en Java, desarrollado como un desafío profesional durante un bootcamp de Java. La aplicación cuenta con un carrito de compras y órdenes de compra asociadas, siguiendo la arquitectura Modelo-Vista-Controlador (MVC) e incorporando varios patrones de diseño para garantizar la mantenibilidad y escalabilidad.

### Características

- Gestión de catálogo de productos
- Funcionalidad de carrito de compras
- Procesamiento de órdenes de compra
- Autenticación y autorización de usuarios
- Pruebas unitarias que aseguran la confiabilidad del código

### Patrones de Diseño Implementados

- **Modelo-Vista-Controlador (MVC)**: Separa la aplicación en tres componentes interconectados para separar las representaciones internas de la información de las formas en que se presenta y acepta información del usuario.
- **Patrón Estrategia**: Utilizado para implementar diversos métodos de pago o estrategias de descuento, permitiendo que el algoritmo varíe independientemente de los clientes que lo utilizan.
- **Patrón Observador**: Facilita la comunicación entre componentes, como actualizar la vista cuando el modelo cambia.
- **Patrón Fábrica**: Gestiona la creación de objetos complejos, promoviendo un acoplamiento flexible y adherencia al Principio de Responsabilidad Única.
- **Patrón Singleton**: Incluido en Spring Framework con el objetivo de permitir al contenedor IoC (Inversion de Control) elegir cual es la mejor instancia del objeto de una capa dependeniendo el contexto.
- **Patrón Repositorio**: Incluido en el ORM de Spring Framework para abstraer la lógica en operaciones CRUD.

### Requisitos Previos

- Java 11 o superior
- Maven 3.6.0 o superior

### Instalación

1. Clona este repositorio:

   ```bash
   git clone https://github.com/jeancaardo/FrostVault.git
   ```

2. Navega al directorio del proyecto:

   ```bash
   cd FrostVault
   ```

3. Compila el proyecto utilizando Maven:

   ```bash
   mvn clean install
   ```

### Uso

Para ejecutar la aplicación localmente:

```bash
mvn spring-boot:run
```

Una vez iniciada, la aplicación estará disponible en `http://localhost:8080`.

### Pruebas

Para ejecutar las pruebas unitarias:

```bash
mvn test
```

### Contribuciones

¡Las contribuciones son bienvenidas! Por favor, sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios con mensajes de commit descriptivos usando [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0-beta.2/)
4. Envía tus cambios (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request detallando los cambios realizados.

### Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.

### Contacto

Para preguntas o sugerencias, puedes contactarme a través de [jeancaardo@gmail.com] o crear un issue en este repositorio.
