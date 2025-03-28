CREATE TABLE Clientes (
    id_cliente BIGSERIAL PRIMARY KEY,
    nombre CHARACTER VARYING(100) NOT NULL,
    apellido CHARACTER VARYING(100) NOT NULL,
    telefono CHARACTER VARYING(20),
    direccion TEXT,
    fecha_registro DATE DEFAULT CURRENT_DATE
);

CREATE TABLE Prestamos (
    id_prestamo BIGSERIAL PRIMARY KEY,
    id_cliente INT NOT NULL,
    monto NUMERIC(15, 2) NOT NULL,
    tasa_interes NUMERIC(5, 2) NOT NULL,
    fecha_desembolso DATE NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    dia_cobro INT NOT NULL CHECK (dia_cobro BETWEEN 1 AND 31),
    estado CHARACTER VARYING(15) NOT NULL CHECK (estado IN ('Activo', 'Finalizado', 'Moroso')),
    saldo_restante NUMERIC(15, 2) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente) ON DELETE CASCADE
);

CREATE TABLE Pagos (
    id_pago BIGSERIAL PRIMARY KEY,
    id_prestamo INT NOT NULL,
    fecha_pago DATE NOT NULL,
    monto_pagado NUMERIC(10, 2) NOT NULL,
    tipo_pago CHARACTER VARYING(10) NOT NULL CHECK (tipo_pago IN ('Abono', 'Pago Total')),
    observaciones TEXT,
    FOREIGN KEY (id_prestamo) REFERENCES Prestamos(id_prestamo) ON DELETE CASCADE
);

CREATE TABLE Usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre CHARACTER VARYING(100) NOT NULL,
    apellido CHARACTER VARYING(100) NOT NULL,
    usuario CHARACTER VARYING(50) UNIQUE NOT NULL,
    password CHARACTER VARYING(100) NOT NULL,
    rol CHARACTER VARYING(15) NOT NULL CHECK (rol IN ('Admin', 'Empleado', 'Supervisor')),
    fecha_registro DATE DEFAULT CURRENT_DATE
);
