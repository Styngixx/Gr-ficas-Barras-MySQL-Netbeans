CREATE DATABASE componentes;
USE componentes;

CREATE TABLE ventas(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    producto VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL 
);

INSERT INTO ventas(producto, cantidad) VALUES
('LAPTOP',40),
('MOUSE',30),
('TECLADO',40),
('MONITOR',50);

SELECT * FROM VENTAS;