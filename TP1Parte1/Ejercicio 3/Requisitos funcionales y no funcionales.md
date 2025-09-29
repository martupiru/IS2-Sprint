# Requisitos funcionales y no funcionales
### Sistema a analizar:
Software de MiniMarket donde se deben dejar registrados los movimientos de Stock cuando se realiza una venta a los clientes o una compra a los proveedores de un producto determinado, por ejemplo “Gaseosa en botella”. 

## Requisitos funcionales
### Qué debe hacer el sistema: 
* Registrar una venta realizada a un cliente.
* Registrar una compra realizada a un proveedor.
* Actualizar automáticamente el stock del producto cuando se registra una venta o una compra.
* Permitir consultar el stock actual de un producto.
* Generar un historial de movimientos de stock (entradas y salidas).
* Asociar cada movimiento a un producto específico (ejemplo: “Gaseosa en botella”).
* Notificar si el stock de un producto baja de un nivel mínimo definido. Por ejemplo, alertar si tengo un stock menor a 5 unidades.

## Requisitos NO funcionales
### Cómo debe comportarse el sistema: 
* La actualización de stock debe realizarse de forma inmediata tras registrar la compra o venta
* El sistema debe garantizar la integridad de los datos
* El sistema debe ser seguro, controlando quién puede registrar movimientos (roles: cajero, administrador, etc.).
* El sistema debe ser fácil de usar, con una interfaz clara e intuitiva.
* El sistema debe estar disponible el 99% del tiempo para permitir registrar operaciones en horario de atención.
* El sistema debe permitir registrar y consultar operaciones en menos de 3 segundos
* El sistema debe ser escalable, permitiendo registrar movimientos para miles de productos sin perder rendimiento.


### Grupo Sprint. - Germani, Martinez, Nahman y Palau
