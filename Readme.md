# SYDPP
## Trabajo practico

### Punto 1 - P2P
    Para correr este punto, es necesario correr la clase Master.java y luego de se deben correr todos los peers que se deseen, archivo Peer.Java.
    Los peers funcionan como clientes y servidores, mientras que el master siempre funciona como servidor. Los clientes (peers) al conectarse a la red P2P, deben anunciar en que directorio quieren realizar las descargas de archivos y que directorio quieren compartir con los demas peers.
    Los peers intentan conectarse al server Master 3 veces con un tiempo de espera entre intentos. Si pasados todos los intentos no logra conectarse al Master, se finaliza la sesión.
    Los directorios ingrresados por el usuario estan validados.
    Y por último, en el caso de que mas de un peer tenga el archivo que se quiere descargar, se le pregunta al usuario desde que peer quiere descargarlo. 
  
### Punto 2 - Banco
    Para correr este punto, basta con correr el archivo Main.Java y este se encarga de levantar a los clientes y la cuenta bancaria.
    La cuenta bancaria tiene un saldo inicial seteado por defecto, y las operaciones que realicen los clientes son random.
  
    Este punto esta divido en dos partes, las cuales son:
      Banco no sincronizado: En esta parte, se puede ver claramente la inconsistencia de datos.
      Banco sincronizado: Con solo agregar "synchronized" en los hilos de extraccion y deposito, ya no hay mas inconsistencia de datos.
    
### Punto 3 - Red flexible
     Para poder utilizar esta red, es necesario primero correr el archivo Main.java, este inicia a Balanceador.Java el cual es el            encargado de crear o dar de baja a los servidores que se necesiten para atender a los clientes. El balanceador tiene seteados            umbrales y en base a estos umbrales toma las decisiones.
     Además, el main levanta clientes que le solicitan al balanceador una tarea, en este caso le solicitan la suma de vectores.
  
### Punto 4 - Sobel
	 El archivo que sube el usuario para aplicarle el filtro sobel esta validado.
    Este punto esta divido en 3 partes:
      - Sobel centralizado: Se ejecuta localmente, basta con iniciar el archivo client.java, y se indica la ruta del archivo que se quiere aplicar el filtro.
      - Sobel sobre RMI: En este punto, se dividio en cliente y servidor, es necesario primero correr Server.java y luego el client.java. Luego la usabilidad del filtro es lo mismo que el centralizado.
      - Sobel Balanceado: Para realizar este punto, se reutilizo la idea de la red flexible, y dependiendo de cuantas partes indica el cliente que quiere cortar la imagen, el balanceador crea los workers necesarios para aplicar los filtros.