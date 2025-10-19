
## Descripción

Aplicación bancaria desarrollada con arquitectura de microservicios utilizando Spring Boot y Java. El Backend implementa operaciones CRUD completas para gestión de clientes, cuentas y transacciones.

**Microservicios:**
- **Client Service** (puerto 8001): EndPoint de clientes con soft delete
- **Account Service** (puerto 8000): EndPoint cuentas bancarias y transacciones

**Stack:**
- Spring Boot 
- H2 Database 
- RestTemplate
- Bean Validation 
- Lombok

## Comandos

```bash
# Compilar
mvn -f client/pom.xml clean install -DskipTests
mvn -f account/pom.xml clean install -DskipTests

# Ejecutar
mvn -f client/pom.xml spring-boot:run  
mvn -f account/pom.xml spring-boot:run  
```
