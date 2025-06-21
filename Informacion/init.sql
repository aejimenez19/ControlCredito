CREATE TABLE tipo_persona (
  id UUID PRIMARY KEY,
  nombre VARCHAR
);

CREATE TABLE persona (
  id UUID PRIMARY KEY,
  nombre VARCHAR,
  identificacion VARCHAR UNIQUE,
  telefono VARCHAR,
  creado_en TIMESTAMP
);

CREATE TABLE persona_rol (
  id UUID PRIMARY KEY,
  persona_id UUID NOT NULL,
  tipo_id UUID NOT NULL,
  FOREIGN KEY (persona_id) REFERENCES persona(id),
  FOREIGN KEY (tipo_id) REFERENCES tipo_persona(id)
);

CREATE TABLE prestador_cliente (
  id UUID PRIMARY KEY,
  prestador_id UUID NOT NULL,
  cliente_id UUID NOT NULL,
  fecha_asociacion DATE,
  FOREIGN KEY (prestador_id) REFERENCES persona(id),
  FOREIGN KEY (cliente_id) REFERENCES persona(id)
);

CREATE TABLE prestamo (
  id UUID PRIMARY KEY,
  prestador_cliente_id UUID NOT NULL,
  monto NUMERIC(15,2) NOT NULL,
  tasa_interes NUMERIC(5,2) NOT NULL,
  fecha_desembolso DATE NOT NULL,
  fecha_vencimiento DATE NOT NULL,
  dia_cobro INTEGER NOT NULL,
  estado VARCHAR NOT NULL,
  saldo_restante NUMERIC(15,2) NOT NULL,
  intereses_restante NUMERIC(15,2),
  FOREIGN KEY (prestador_cliente_id) REFERENCES prestador_cliente(id)
);

CREATE TABLE pago (
  id UUID PRIMARY KEY,
  prestamo_id UUID NOT NULL,
  fecha_pago DATE NOT NULL,
  monto_pagado NUMERIC(10,2) NOT NULL,
  tipo_pago VARCHAR NOT NULL,
  observaciones VARCHAR,
  FOREIGN KEY (prestamo_id) REFERENCES prestamo(id)
);
