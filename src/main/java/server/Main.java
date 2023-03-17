package server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.util.ArrayList;


public class Main {
    private static final int PORT = 3777;

    public static void main(String[] args)throws IOException {
        ArrayList<Database> databases = new ArrayList<>();
        databases.add(0,new Database("1",11));
        databases.add(1,new Database("2",22));
        databases.add(2,new Database("22",333));
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try (

                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                ) {
                    System.out.println("Server started!");
                    output.writeUTF("Enter action (1-get a file,2-save a file, 3-delete a file): >");
                    String message = String.valueOf(Integer.parseInt(input.readUTF()));
                    while (!message.equals("exit")) {
                        if (message.equals("2") ) {
                            output.writeUTF("Enter name of file: > ");
                            String nameOfFile = input.readUTF();
                            output.writeUTF("Enter name of file to be saved on server: > ");
                            String nameToSave = input.readUTF();
                            int id = (int) (Math.random() * 300);
                            Database database = new Database(nameToSave, id);
                            for (int i=0;i<databases.size();i++){
                                if ((databases.get(i).nameOfFile.equals(nameToSave))||(databases.get(i).id==id)){
                                    output.writeUTF("This file have already exists");
                                    break;
                                }
                            }
                            copyFile(new File("C:\\Users\\kriss\\Desktop\\после форка\\se-lab04-tmp2223\\src\\main\\java\\client\\data\\" + nameOfFile + ".txt"), new File("C:\\Users\\kriss\\Desktop\\после форка\\se-lab04-tmp2223\\src\\main\\java\\server\\" + nameToSave + ".txt"));
                            System.out.println("Saving Done!");
                            databases.add(database);
                            int k = 0;
                            for (int i = 0; i < databases.size(); i++) {
                                if (databases.get(i).nameOfFile == nameToSave) {
                                    k = databases.get(i).id;
                                }
                            }
                            output.writeUTF("Response says that file is saved! ID = " + k+"\n"+"Enter action (1-get a file,2-save a file, 3-delete a file): >");
                            message = input.readUTF();
                        }
                        if (message.equals("1") ) {
                            output.writeUTF("Do you want to get the file by name or by id(1-name,2-id): > ");
                            String getFileBy = input.readUTF();
                            if (getFileBy.equals("1")) {
                                output.writeUTF("Enter name of file: > ");
                                String nameOfFile = input.readUTF();
                                for (int i=0;i<databases.size();i++) {
                                    if (databases.get(i).nameOfFile.equals(getFileBy)) {
                                        output.writeUTF("Specify a name for it: > ");
                                        String newName = input.readUTF();
                                        int id = (int) (Math.random() * 300);
                                        Database database = new Database(newName, id);
                                        for (int j = 0; j < databases.size(); j++) {
                                            if ((databases.get(j).nameOfFile.equals(newName)) || (databases.get(j).id == id)) {
                                                output.writeUTF("This file have already exists");
                                                break;
                                            }
                                        }
                                        copyFile(new File("C:\\Users\\kriss\\Desktop\\после форка\\se-lab04-tmp2223\\src\\main\\java\\server\\" + nameOfFile + ".txt"), new File("C:\\Users\\kriss\\Desktop\\после форка\\se-lab04-tmp2223\\src\\main\\java\\client\\data\\" + newName + ".txt"));
                                        databases.add(database);
                                        int k = 0;
                                        for (int j = 0; j < databases.size(); j++) {
                                            if (databases.get(i).nameOfFile == newName) {
                                                k = databases.get(i).id;
                                            }
                                        }
                                        output.writeUTF("Response says that file is saved! ID = " + k+"\n"+"Enter action (1-get a file,2-save a file, 3-delete a file): >");


                                    }else {
                                        output.writeUTF("I can't find this file.Please,do everything again");
                                        break;
                                    }

                                }
                                message = input.readUTF();
                            }
                            if (getFileBy.equals("2")) {
                                output.writeUTF("Enter id: >");
                                String idOfFile = input.readUTF();
                                int newIdOfFile = Integer.parseInt(idOfFile);
                                String findInList = "";
                                for (int i = 0; i < databases.size(); i++) {
                                    if (databases.get(i).id == newIdOfFile) {
                                        findInList = databases.get(i).nameOfFile;

                                        output.writeUTF("Specify a name for it: > ");
                                        String newName = input.readUTF();
                                        int id = (int) (Math.random() * 5);
                                        Database database = new Database(newName, id);
                                        for (int j = 0; j < databases.size(); j++) {
                                            if ((databases.get(j).nameOfFile.equals(newName)) ||(databases.get(j).id == id)) {
                                                output.writeUTF("This file have already exists");
                                                break;
                                            }
                                        }
                                        copyFile(new File("C:\\Users\\kriss\\Desktop\\после форка\\se-lab04-tmp2223\\src\\main\\java\\server\\" + findInList + ".txt"), new File("C:\\Users\\kriss\\Desktop\\после форка\\se-lab04-tmp2223\\src\\main\\java\\client\\data\\" + newName + ".txt"));
                                        databases.add(database);
                                        int k = 0;
                                        for (int p = 0; p < databases.size(); p++) {
                                            if (databases.get(p).nameOfFile == newName) {
                                                k = databases.get(p).id;
                                            }
                                        }
                                        output.writeUTF("Response says that file is saved! ID = " + k+"\n"+"Enter action (1-get a file,2-save a file, 3-delete a file): >");
                                    }

                                }
                            }
                            else {
                                output.writeUTF("Wrong number!Please do everything again");
                                break;
                            }
                            message=input.readUTF();
                        }



                        if (message.equals("3")) {
                            output.writeUTF("Do you want to delete the file by name or by id(1-name,2-id): >");
                            String HowToDelete = input.readUTF();
                            if (HowToDelete.equals("1")) {
                                output.writeUTF("Enter the name : >");
                                String deleteByName = input.readUTF();
                                for (int i = 0; i < databases.size(); i++) {
                                    if (databases.get(i).nameOfFile.equals(deleteByName)) {
                                        File file = new File("C:\\Users\\kriss\\Desktop\\после форка\\se-lab04-tmp2223\\src\\main\\java\\client\\data\\" + deleteByName + ".txt");
                                        if (file.delete()) {
                                            output.writeUTF("The response says that this file was deleted successfully!");
                                        }
                                        databases.remove(i);
                                        break;
                                    } else {
                                        output.writeUTF("The response says that this file is not found.Please,repeat everything again");
                                        break;
                                    }
                                }

                            }
                            if (HowToDelete.equals("2")) {
                                output.writeUTF("Enter the id: > ");
                                int id = Integer.parseInt(input.readUTF());
                                for (int i = 0; i < databases.size(); i++) {
                                    if (databases.get(i).id == id) {
                                        String nameOfFile = databases.get(i).nameOfFile;
                                        File file = new File("C:\\Users\\kriss\\Desktop\\после форка\\se-lab04-tmp2223\\src\\main\\java\\client\\data\\" + nameOfFile + ".txt");
                                        if (file.delete()) {
                                            output.writeUTF("The response says that this file was deleted successfully!");
                                        }
                                        databases.remove(i);
                                        break;
                                    } else {
                                        output.writeUTF("The response says that this file is not found.Please,repeat everything again");
                                        break;
                                    }
                                }
                            }
                            message= input.readUTF();
                        }
                        if (message.equals("exit")){
                            System.out.println("Клиент отключился");
                            break;
                        }
                    }
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(new File("C:\\Users\\kriss\\Desktop\\после форка\\se-lab04-tmp2223\\src\\main\\java\\server\\database.json"),databases);
                }
            }
        } catch (IOException e) {
            System.out.println("Проверьте правильность введенных данных");
        }
    }
    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if(!destFile.exists()) {
            destFile.createNewFile();
        }else {
            throw new RuntimeException("PUT 200 INTEGER IDENTIFIER 403");
        }


        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        }
        finally {
            if(source != null) {
                source.close();
            }
            if(destination != null) {
                destination.close();
            }
        }
    }
}
