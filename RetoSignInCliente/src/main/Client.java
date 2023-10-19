package main;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Client {

    private final int PUERTO = 5000;
    private final String IP = "127.0.0.1";

    public void iniciar() {
        Socket cliente = null;
        ObjectInputStream entrada = null;
        ObjectOutputStream salida = null;
        Scanner scanner = new Scanner(System.in);
        try {
            cliente = new Socket(IP, PUERTO);
            System.out.println("Conexión realizada con servidor");

            //Cogemos los datos del deportivo
            System.out.println("Ingresa los datos de un deportivo:");

            System.out.print("Matrícula: ");
            String matricula = scanner.nextLine();

            System.out.print("Marca: ");
            String marca = scanner.nextLine();

            System.out.print("Tamaño del Depósito: ");
            double tamanoDeposito = scanner.nextDouble();
            scanner.nextLine(); // Consumir la nueva línea pendiente

            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();
            //Datos del Propietario
            System.out.print("Nombre Propietario: ");
            String nombre = scanner.nextLine();
            System.out.print("Telefono Propietario: ");
            int telefono = Integer.valueOf(scanner.nextLine());
            //creamos objetos
            Propietario prop = new Propietario(nombre, telefono);
            Deportivo deportivo = new Deportivo(matricula, marca, tamanoDeposito, modelo, prop);
            
            entrada = new ObjectInputStream(cliente.getInputStream());
            salida = new ObjectOutputStream(cliente.getOutputStream());
            
            //envia el objeto al servidor
            salida.writeObject(deportivo);
            //lee el objeto y printealo por pantalla
            Deportivo mensaje = (Deportivo) entrada.readObject();
            System.out.println("Datos del deportivo almacenados en el archivo:");
            System.out.println("Matrícula: " + mensaje.getMatricula());
            System.out.println("Marca: " + mensaje.getMarca());
            System.out.println("Tamaño del Depósito: " + mensaje.getDeposito());
            System.out.println("Modelo: " + mensaje.getModelo());
            System.out.println("Nombre Propietario: " + mensaje.getPropietario().getNombre());
            System.out.println("Telefono Propietario: " + mensaje.getPropietario().getTelefono());
            
            //Scanner input = new Scanner(System.in);
            /*if (!mensaje.substring(0, 5).equalsIgnoreCase("ERROR")) {
                String opcion = input.nextLine();
                salida.writeObject(opcion);
                mensaje = (String) entrada.readObject();
                System.out.println(mensaje);
            }*/
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (cliente != null) {
                    cliente.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
                if (salida != null) {
                    salida.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Fin cliente");
        }
    }

    public static void main(String[] args) {
        
        
        
        Client c1 = new Client();
        c1.iniciar();
    }
}// Cliente___
