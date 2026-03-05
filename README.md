#Guía de Inicio Rápido

## 1. Requisitos Previos
- PostgreSQL instalado y ejecutándose en localhost:5432
- Base de datos `ventas_db` creada:
  ```sql
  CREATE DATABASE ventas_db;
  ```

## 2. Ejecutar la Aplicación
```bash
cd rutaArchivoDescargado
mvnw.cmd spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 3. Credenciales de prueba

```
Usuario: agente.manuel@gmail.com
Contraseña: 12345678Ad$
Rol: AGENTE

Usuario: backoffice.juan@gmail.com
Contraseña: 12345678Ad$
Rol: BACKOFFICE

Usuario: supervisor.wperez@gmail.com
Contraseña: 12345678Ad$
Rol: SUPERVISOR

Usuario: admin@gmail.com
Contraseña: 12345678Ad$
Rol: ADMIN
```

## 4. Test de Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"agente.manuel@gmail.com\",\"password\":\"12345678Ad$"}"
```

**Esperado:** Recibir un JSON con `accessToken` y `type: Bearer`



## 5. Verificar Datos Iniciales

### Usuarios creados
- admin@gmail.com (ADMIN) - sin supervisor
- supervisor.wperez@gmail.com (SUPERVISOR) - sin supervisor
- agente.manuel@gmail.com (AGENTE) - bajo supervisor1
- backoffice.juan@gmail.com (BACKOFFICE)

### Ventas de ejemplo
- Se crero nueve ventas con estados PENDIENTE, APROBADA, RECHAZADA
- Todas creadas por 'agente.manuel@gmail.com'  (AGENTE)

## 6. Flujo Típico de Prueba

### Como AGENTE:
1. Login con agente
2. POST /api/v1/ventas (crear nueva venta)
3. GET /api/v1/ventas/mis-ventas (ver solo sus ventas)

### Como BACKOFFICE:
1. Login con backoffice
2. GET /api/v1/ventas/pendientes (ver todas pendientes)
3. POST /api/v1/ventas/{id}/aprobar (aprobar una)
4. POST /api/v1/ventas/{id}/rechazar (rechazar otra)

### Como SUPERVISOR:
1. Login con supervisor
2. GET /api/v1/ventas/equipo (ver ventas de agentes)
3. GET /api/v1/ventas/equipo?estado=APROBADA (filtrar por estado)

### Como ADMIN o SUPERVISOR:

1. GET /api/v1/reportes/resumen?desde=2025-03-01&hasta=2026-03-31 (con filtros de fecha)



## 7. Solución de problemas

### Error: "connect to server failed"
- Verificar que PostgreSQL está corriendo
- Verificar credenciales en application.yml

### Error: "401 Unauthorized"
- Token expirado, hacer login nuevamente
- Token inválido, generar nuevo con login

### Error: "VALIDATION_ERROR"
- DNI debe ser 8 o 11 dígitos
- Telefono debe ser exactamente 9 dígitos
- Campos requeridos no pueden estar vacíos

### Error: "codigo_llamada already exists"
- Usar un código de llamada único en cada nueva venta

## 8. Archivos de Configuración

### application.yml
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ventas_db
    username: usuarioPostgres
    password: IngresaContraseña
```

### data.sql
- Se ejecuta automáticamente al iniciar
- Se crea los 4 usuarios de prueba
- Se crea 9 ventas de ejemplo


---

**Documentación completa**: Ver `el archivo 'TELCO RETO TENICO.postman_collection.json' para hacer pruebas con postman y que pose ejemplos. Antes de ejecutar esas pruebas, ejecute la peticion de login segun para el usuario `
