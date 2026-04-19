import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length == 0) {
            System.out.println("No se ha proporcionado un archivo de configuración.");
            return;
        }

        String archivo = args[0];

        Properties properties = new Properties();
        properties.load(new FileReader(archivo));

        int numSensores = Integer.parseInt(properties.getProperty("ni"));
        int numBaseEventos = Integer.parseInt(properties.getProperty("ne"));
        int numClasificadores = Integer.parseInt(properties.getProperty("nc"));
        int numServidores = Integer.parseInt(properties.getProperty("ns"));
        int capacidadBuzonClasificacion = Integer.parseInt(properties.getProperty("tam1"));
        int capacidadBuzonConsolidacion = Integer.parseInt(properties.getProperty("tam2"));

        // Crear buzones
        Buzon buzonEntrada = new Buzon(-1);
        Buzon buzonAlertas = new Buzon(-1);
        Buzon buzonClasificacion = new Buzon(capacidadBuzonClasificacion);

        Buzon[] buzonesConsolidacion = new Buzon[numServidores];
        for (int i = 0; i < numServidores; i++) {
            buzonesConsolidacion[i] = new Buzon(capacidadBuzonConsolidacion);
        }

        // Calcular el total de eventos esperados
        int totalEventos = 0;
        for (int i = 1; i <= numSensores; i++) {
            totalEventos += numBaseEventos * i;
        }

        // Inicializar clasificadores
        Clasificador.inicializar(numClasificadores);

        // Crear sensores
        Sensor[] sensores = new Sensor[numSensores];
        for (int i = 0; i < numSensores; i++) {
            int idSensor = i + 1;
            int numEventos = numBaseEventos * idSensor;
            sensores[i] = new Sensor(idSensor, numEventos, numServidores, buzonEntrada);
        }

        // Crear broker
        Broker broker = new Broker(buzonEntrada, buzonAlertas, buzonClasificacion, totalEventos);

        // Crear administrador
        Administrador administrador = new Administrador(buzonAlertas, buzonClasificacion, numClasificadores);

        // Crear clasificadores
        Clasificador[] clasificadores = new Clasificador[numClasificadores];
        for (int i = 0; i < numClasificadores; i++) {
            clasificadores[i] = new Clasificador(buzonClasificacion, buzonesConsolidacion);
        }

        // Crear servidores
        Servidor[] servidores = new Servidor[numServidores];
        for (int i = 0; i < numServidores; i++) {
            servidores[i] = new Servidor(i + 1, buzonesConsolidacion[i]);
        }

        // Iniciar servidores
        for (int i = 0; i < numServidores; i++) {
            servidores[i].start();
        }

        // Iniciar clasificadores
        for (int i = 0; i < numClasificadores; i++) {
            clasificadores[i].start();
        }

        // Iniciar administrador
        administrador.start();

        // Iniciar broker
        broker.start();

        // Iniciar sensores
        for (int i = 0; i < numSensores; i++) {
            sensores[i].start();
        }

        // Esperar sensores
        for (int i = 0; i < numSensores; i++) {
            sensores[i].join();
        }

        // Esperar broker
        broker.join();

        // Esperar administrador
        administrador.join();

        // Esperar clasificadores
        for (int i = 0; i < numClasificadores; i++) {
            clasificadores[i].join();
        }

        // Esperar servidores
        for (int i = 0; i < numServidores; i++) {
            servidores[i].join();
        }

        System.out.println("El sistema terminó correctamente.");

    }

}
