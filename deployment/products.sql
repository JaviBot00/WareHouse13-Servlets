CREATE DATABASE IF NOT EXISTS warehouse13 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE warehouse13;

SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS products (
    product_code    VARCHAR(16)    NOT NULL PRIMARY KEY,
    description     VARCHAR(255)   NOT NULL,
    price           DECIMAL(10,2)  NOT NULL,
    stock           INT            NOT NULL DEFAULT 0,
    retired         BOOLEAN        NOT NULL DEFAULT FALSE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS perishable_products (
    product_code    VARCHAR(16)    NOT NULL PRIMARY KEY,
    description     VARCHAR(255)   NOT NULL,
    price           DECIMAL(10,2)  NOT NULL,
    stock           INT            NOT NULL DEFAULT 0,
    retired         BOOLEAN        NOT NULL DEFAULT FALSE,
    expiration_date CHAR(8)        NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO products (product_code, description, price, stock, retired) VALUES
('PADT8901Y', 'Alfombrilla de ratón XXL con base de goma', 19.99, 89, FALSE),
('AURC3456L', 'Auriculares inalámbricos con cancelación de ruido', 129.99, 23, FALSE),
('COOL6789I', 'Base refrigeradora para portátil con 3 ventiladores', 29.95, 41, FALSE),
('CARG5678O', 'Cargador rápido USB-C 65W con 2 puertos', 45.80, 34, FALSE),
('DISK1234R', 'Disco duro externo 1TB USB-C resistente al agua', 79.99, 18, FALSE),
('HUBB2345M', 'Hub USB 3.0 de 4 puertos con alimentación', 24.75, 56, FALSE),
('MICR2345U', 'Micrófono USB de condensador para streaming', 65.30, 27, FALSE),
('MONS4567T', 'Monitor portátil 15.6 pulgadas Full HD', 189.50, 12, FALSE),
('RATN9012K', 'Ratón inalámbrico ergonómico con 5 botones', 34.50, 67, FALSE),
('LAPD9012P', 'Soporte ajustable para portátil de aluminio', 39.99, 50, FALSE),
('TECL5678X', 'Teclado mecánico RGB con switches rojos', 89.99, 45, FALSE),
('WEBC7890P', 'Webcam Full HD 1080p con micrófono integrado', 59.90, 32, FALSE),
('MOUSE1234', 'Ratón óptico básico con cable USB', 9.99, 0, TRUE),
('KEYB5678A', 'Teclado de membrana con cable USB', 14.99, 0, TRUE),
('HDMI9012A', 'Cable HDMI de alta velocidad 1.5 metros', 12.50, 0, TRUE),
('SPEK6789A', 'Altavoces estéreo para ordenador con subwoofer', 39.99, 0, TRUE),
('LAPD5678A', 'Base para portátil con ventilador y soporte ajustable', 24.99, 0, TRUE),
('MONS9012A', 'Monitor de ordenador de 24 pulgadas Full HD', 149.99, 0, TRUE);

INSERT INTO perishable_products (product_code, description, price, stock, retired, expiration_date) VALUES
('INK55665F', 'Toner b/w genérico HP 8750', 79.99, 18, FALSE, '20260713'),
('USB34560A', 'Memoria USB 16GB resistente al agua', 19.99, 0, TRUE, '20231231'),
('PRNT7890A', 'Impresora multifunción con Wi-Fi', 89.99, 0, TRUE, '20240215'),
('SCNR2345A', 'Escáner de documentos portátil A4', 49.95, 0, TRUE, '20240330'),
('WEBC1234A', 'Webcam HD 720p con micrófono integrado', 29.99, 0, TRUE, '20240101');
