# WareHouse13

Sistema de gestión de inventario de almacén desarrollado como proyecto académico y ampliado progresivamente hasta una arquitectura cliente-servidor completa.

## Repositorios relacionados

| Componente | Repositorio |
|---|---|
| Cliente Android | [WareHouse13-Android](https://github.com/JaviBot00/WareHouse13-Android) |

## Descripción

WareHouse13 permite gestionar un catálogo de productos, incluyendo productos perecederos con fecha de caducidad. Soporta altas, consultas, edición de stock, retirada/reactivación y eliminación de productos.

El proyecto nació como una aplicación de consola Java con arquitectura MVC y persistencia en CSV, evolucionó a serialización JSON, y finalmente se convirtió en un sistema multicapa con backend Servlet, base de datos MySQL, cliente Android y frontend web.

---

## Arquitectura

```cmd
┌──────────────────┐     HTTP (JSON)     ┌────────────────────────────┐
│  Cliente Android │ ──────────────────► │  Backend — Java Servlets   │
│  (Java, MVC)     │                     │  Tomcat 9 · Docker         │
└──────────────────┘                     └────────────┬───────────────┘
                                                      │ JDBC
┌──────────────────┐     HTTP (JSON)                  ▼
│  Frontend Web    │ ──────────────────► ┌────────────────────────────┐
│  JSP + JS + CSS  │                     │  MySQL 8                   │
└──────────────────┘                     │  (tabla products +         │
                                         │   perishable_products)     │
                                         └────────────────────────────┘
```

### Capas del backend

| Capa | Paquete | Responsabilidad |
|---|---|---|
| Vista | `view` | Servlets — reciben HTTP, delegan en el Controller, devuelven JSON |
| Controlador | `controller` | Orquesta operaciones, construye objetos de modelo, serializa a JSON |
| Acceso a datos | `dataservice` | `DataRepository` (interfaz) + `DatabaseAccess` (implementación MySQL) |
| Modelo | `model` | `Product` y `PerishableProduct` con validación en setters |

---

## Stack tecnológico

| Componente | Tecnología |
|---|---|
| Backend | Java 21 · Java Servlets 4.0 · Tomcat 9 |
| Base de datos | MySQL 8 · JDBC |
| Frontend web | JSP · CSS · JavaScript (i18n ES/EN) |
| Cliente móvil | Android (Java) |
| Serialización | Gson |
| Build | Maven (empaquetado como `.war`) |
| Infraestructura | Docker · Docker Compose |

---

## API REST

Todos los endpoints están bajo el contexto `/WareHouse13-Servlets`.

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/listar-activos` | Lista todos los productos activos |
| `GET` | `/listar-retirados` | Lista todos los productos retirados |
| `GET` | `/buscar?code=XX` | Busca productos por código (parcial) |
| `POST` | `/insertar` | Inserta un nuevo producto |
| `PUT` | `/actualizar` | Actualiza descripción, precio y stock |
| `PUT` | `/retirar` | Marca un producto como retirado |
| `PUT` | `/reactivar` | Reactiva un producto retirado |
| `DELETE` | `/eliminar` | Elimina permanentemente un producto |

Los endpoints mutantes devuelven `400 Bad Request` si el código de producto no cumple las restricciones del modelo.

---

## Modelo de datos

### `products`

| Campo | Tipo | Descripción |
|---|---|---|
| `product_code` | `VARCHAR(16) PK` | Código único alfanumérico |
| `description` | `VARCHAR(255)` | Descripción del producto |
| `price` | `DECIMAL(10,2)` | Precio unitario |
| `stock` | `INT` | Cantidad en almacén |
| `retired` | `BOOLEAN` | Indica si está retirado |

### `perishable_products`

Mismos campos que `products` más:

| Campo | Tipo | Descripción |
|---|---|---|
| `expiration_date` | `CHAR(8)` | Fecha de caducidad en formato `YYYYMMDD` |

Se utiliza el patrón **Table-per-Class** para evitar columnas nulas en una tabla unificada.

---

## Puesta en marcha

### Requisitos

- Docker y Docker Compose instalados.

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/JaviBot00/WareHouse13.git
cd WareHouse13

# 2. Levantar los contenedores
cd deployment
docker compose up --build
```

Los servicios quedan disponibles en:

| Servicio | URL |
|---|---|
| API Servlet | `http://localhost:8080/WareHouse13-Servlets` |
| Frontend web | `http://localhost:8080/WareHouse13-Servlets/` |
| phpMyAdmin | `http://localhost:8081` |

La base de datos se inicializa automáticamente con el script `deployment/warehouse13.sql` al arrancar el contenedor MySQL. El servidor web espera a que MySQL esté listo gracias al `healthcheck` definido en el `docker-compose.yml`.

---

## Estructura del proyecto

```cmd
WareHouse13/
├── Dockerfile
├── pom.xml
├── deployment/
│   ├── docker-compose.yml
│   ├── db_backup/
│   │   └── warehouse13.sql          # Script de inicialización de la BD
│   └── products.json                # Dataset de ejemplo (legado JSON)
└── src/main/
    ├── java/com/hotguy/warehouse13/
    │   ├── model/
    │   │   ├── Product.java
    │   │   └── PerishableProduct.java
    │   ├── controller/
    │   │   ├── ControllerContract.java
    │   │   └── Controller.java
    │   ├── dataservice/
    │   │   ├── DataRepository.java
    │   │   ├── DatabaseAccess.java
    │   │   └── DatabaseConnection.java
    │   └── view/
    │       ├── ServletUtils.java
    │       ├── ListActiveServlet.java
    │       ├── ListRetiredServlet.java
    │       ├── FindProductsServlet.java
    │       ├── InsertProductServlet.java
    │       ├── UpdateProductServlet.java
    │       ├── RetireProductServlet.java
    │       ├── UnretireProductServlet.java
    │       └── DeleteProductServlet.java
    └── webapp/
        ├── WEB-INF/web.xml
        ├── index.jsp
        ├── css/styles.css
        └── js/app.js
```

---

## Origen del proyecto

Este proyecto se desarrolló inicialmente como ejercicio de examen de programación (Febrero 2026), cuyo enunciado pedía una aplicación de consola en Java con arquitectura MVC para gestionar productos de almacén. A partir de ese núcleo se amplió de forma progresiva añadiendo persistencia JSON, base de datos MySQL, API REST con Servlets, despliegue en Docker, frontend web y cliente Android.
