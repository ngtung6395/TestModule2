package storage;

import models.TelephoneDirectory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactFile {
    private static ContactFile INSTANCE;

    private ContactFile(){

    }
    public static ContactFile getINSTANCE(){
        if(INSTANCE == null) INSTANCE  = new ContactFile();
        return INSTANCE;
    }



    public List<TelephoneDirectory> readFile() throws IOException, ClassNotFoundException {
        File file = new File("listContact.dat");
        if(!file.exists()){
            file.createNewFile();
        }
        if (file.length() >0){
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object object = objectInputStream.readObject();
            List<TelephoneDirectory> list = (List<TelephoneDirectory>) object;
            objectInputStream.close();
            fileInputStream.close();
            return list;
        }
        else return new ArrayList<>();
    }

    public void writeFile(List<TelephoneDirectory> contactList) throws IOException{
        File file = new File("listContact.dat");
        if (!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(contactList);
        objectOutputStream.close();
        fileOutputStream.close();
    }
}
