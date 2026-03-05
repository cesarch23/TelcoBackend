INSERT INTO usuarios (username, password_hash, role, supervisor_id, activo, created_at, updated_at)
VALUES ('admin@gmail.com', '$2a$12$0Npbf3ziL0NiL0XjzbL/eeO./NmFx0OWhu3hpHpQR.YewAKwvKAue', 'ADMIN', NULL, true, '2026-02-01 09:30:00', '2026-02-01 09:30:00');
INSERT INTO usuarios (username, password_hash, role, supervisor_id, activo, created_at, updated_at)
VALUES ('supervisor.wperez@gmail.com', '$2a$12$0Npbf3ziL0NiL0XjzbL/eeO./NmFx0OWhu3hpHpQR.YewAKwvKAue', 'SUPERVISOR', NULL, true, '2026-02-01 09:30:00', '2026-02-01 09:30:00');
INSERT INTO usuarios (username, password_hash, role, supervisor_id, activo, created_at, updated_at)
VALUES ('agente.manuel@gmail.com', '$2a$12$0Npbf3ziL0NiL0XjzbL/eeO./NmFx0OWhu3hpHpQR.YewAKwvKAue', 'AGENTE', 2, true, '2026-02-01 09:30:00', '2026-02-01 09:30:00');
INSERT INTO usuarios (username, password_hash, role, supervisor_id, activo, created_at, updated_at)
VALUES ('backoffice.juan@gmail.com', '$2a$12$0Npbf3ziL0NiL0XjzbL/eeO./NmFx0OWhu3hpHpQR.YewAKwvKAue', 'BACKOFFICE', NULL, true, '2026-02-01 09:30:00', '2026-02-01 09:30:00');


-- Insert sample sales
INSERT INTO ventas (
    agente_id,
    dni_cliente,
    nombre_cliente,
    telefono_cliente,
    direccion_cliente,
    plan_actual,
    plan_nuevo,
    codigo_llamada,
    producto,
    monto,
    estado,
    motivo_rechazo,
    fecha_registro,
    fecha_validacion,
    created_at,
    updated_at
)
VALUES
    (3, '12345678', 'Juan García', '987654321', 'Av. Principal 123', 'Plan Básico', 'Plan Premium', 'CALL001', 'FIJA_HOGAR', 150.00, 'PENDIENTE', NULL, '2026-03-04', NULL, '2026-03-04 10:00:00', '2026-03-04 10:00:00'),
    (3, '23456789', 'María López', '987654322', 'Calle Secundaria 456', 'Plan Básico', 'Plan Premium', 'CALL002', 'FIJA_HOGAR', 120.00, 'APROBADA', NULL, '2026-03-03', '2026-03-03', '2026-03-04 10:00:00', '2026-03-04 10:00:00'),
    (3, '34567890', 'Carlos Rodríguez', '987654323', 'Pasaje Tercera 789', NULL, 'Plan Básico', 'CALL003', 'FIJA_HOGAR', 100.00, 'RECHAZADA', 'Cliente rechazó la propuesta', '2026-03-02', '2026-03-02', '2026-03-04 10:00:00', '2026-03-04 10:00:00'),
    (3, '45678901', 'Ana Martínez', '987654324', 'Zona Sur 321', 'Plan Premium', 'Plan Super', 'CALL004', 'FIJA_HOGAR', 200.00, 'APROBADA', NULL, '2026-03-01', '2026-03-01', '2026-03-04 10:00:00', '2026-03-04 10:00:00'),
    (3, '56789012', 'Pedro González', '987654325', 'Este Barrio 654', 'Plan Básico', 'Plan Premium', 'CALL005', 'FIJA_HOGAR', 135.50, 'PENDIENTE', NULL, '2026-03-04', NULL, '2026-03-04 10:00:00', '2026-03-04 10:00:00'),
    (3, '67890123', 'Laura Fernández', '987654326', 'Centro Ciudad 987', 'Plan Premium', 'Plan Super', 'CALL006', 'FIJA_HOGAR', 250.00, 'APROBADA', NULL, '2026-02-28', '2026-02-28', '2026-03-04 10:00:00', '2026-03-04 10:00:00'),
    (3, '78901234', 'Ricardo Díaz', '987654327', 'Sector Norte 159', NULL, 'Plan Básico', 'CALL007', 'FIJA_HOGAR', 110.00, 'RECHAZADA', 'No cumple requisitos', '2026-02-27', '2026-02-27', '2026-03-04 10:00:00', '2026-03-04 10:00:00'),
    (3, '89012345', 'Sofía López', '987654328', 'Área Este 357', 'Plan Básico', 'Plan Premium', 'CALL008', 'FIJA_HOGAR', 140.00, 'PENDIENTE', NULL, '2026-03-04', NULL, '2026-03-04 10:00:00', '2026-03-04 10:00:00'),
    (3, '90123456', 'Miguel Álvarez', '987654329', 'Zona Oeste 852', 'Plan Premium', 'Plan Super', 'CALL009', 'FIJA_HOGAR', 180.00, 'APROBADA', NULL, '2026-02-26', '2026-02-26', '2026-03-04 10:00:00', '2026-03-04 10:00:00');