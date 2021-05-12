package controller;

import models.TelephoneDirectory;
import storage.ContactFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelephoneDirectoryManager {
    private static TelephoneDirectoryManager INSTANCE;


    private TelephoneDirectoryManager(){

    }
    public static TelephoneDirectoryManager getINSTANCE(){
        if(INSTANCE == null) INSTANCE  = new TelephoneDirectoryManager();
        return INSTANCE;
    }

    private static List<TelephoneDirectory> telephoneDirectories = new ArrayList<>();


    public static List<TelephoneDirectory> getTelephoneDirectories() {
        ContactFile contactFile = ContactFile.getINSTANCE();
        try {
            telephoneDirectories = contactFile.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return telephoneDirectories;

    }

    public void setTelephoneDirectories(List<TelephoneDirectory> telephoneDirectories) {
        this.telephoneDirectories = telephoneDirectories;
    }

    ContactFile contactFile = ContactFile.getINSTANCE();

    public void addTelephoneDirectory(TelephoneDirectory telephoneDirectory){
        telephoneDirectories.add(telephoneDirectory);
        try {
            contactFile.writeFile(telephoneDirectories);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeTelephoneDirectory(TelephoneDirectory telephoneDirectory){
        telephoneDirectories.remove(telephoneDirectory);
        try {
            contactFile.writeFile(telephoneDirectories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TelephoneDirectory findDirectoryByPhoneNumber(String phoneNumber){
        TelephoneDirectory telephoneDirectory = null;
        for (TelephoneDirectory d: telephoneDirectories
             ) {
            if(d.getPhoneNumber().equals(phoneNumber))
                telephoneDirectory = d;
        }
        return telephoneDirectory;
    }

    public void display() throws Exception {
        if(telephoneDirectories.isEmpty())
            throw new Exception();
        else {
            for (TelephoneDirectory d:telephoneDirectories
                 ) {
                System.out.println(d.toString());
            }
        }
    }

    public boolean checkPhoneNumber(String phoneNumber){
        boolean check = false;
        for (TelephoneDirectory p: telephoneDirectories
             ) {
            if(p.getPhoneNumber().equals(phoneNumber))
                check = true;
        }
        return check;
    }
}
