package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    private  static final String SERVER_ADRESS = "127.0.0.1";
    private static final int SERVER_PORT = 3777;
    public static void main(String[] args) {

        try(
                Socket socket = new Socket(SERVER_ADRESS,SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        ){
            Scanner scanner = new Scanner(System.in);
            String message = "";
            while (message!="exit"){
                System.out.println("Сервер : "+input.readUTF());
                message = scanner.nextLine();
                output.writeUTF(message);
                System.out.println("The request was sent.");
            }
        } catch (IOException e){
            System.out.println("Клиент отключился");
        }
    }
}

