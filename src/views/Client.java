package views;

import controller.TelephoneDirectoryManager;
import models.TelephoneDirectory;
import storage.ContactFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    static final TelephoneDirectoryManager MANAGER = TelephoneDirectoryManager.getINSTANCE();
    private static List<TelephoneDirectory> contactList = new ArrayList<>();
    static {
        try {
            contactList = ContactFile.getINSTANCE().readFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        MANAGER.setTelephoneDirectories(contactList);
        mainMenu();
    }

    public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        int choose;

        do {
            System.out.println("---- CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ ----");
            System.out.println("Chọn chức năng theo số (để tiếp tục): ");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm mới");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xóa");
            System.out.println("5. Tìm kiếm");
            System.out.println("6. Đọc từ file");
            System.out.println("7. Ghi từ file");
            System.out.println("8. Thoát");
            System.out.print("Chọn chức năng");
            choose = Integer.parseInt(scanner.nextLine());
            switch (choose) {
                case 1:
                    try {
                        MANAGER.display();
                    } catch (Exception e) {
                        System.err.println("Không tồn tại contact nào ");
                        ;
                    }
                    break;
                case 2:
                    addNewContact();
                    break;
                case 3:
                    editContactMenu();
                    break;
                case 4:
                    removeContact();
                    break;
                case 5:
                    search();
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Bạn nhập sai vui lòng nhập lại !");
                    break;
            }
        }
        while (choose != 8);
    }

    public static void addNewContact() {
        Scanner scanner = new Scanner(System.in);
        int choose;
        do {
            System.out.println("1. Thêm mới danh bạ");
            System.out.println("0. Quay lại menu");
            System.out.println("Mời bạn nhập lựa chọn ");
            choose = Integer.parseInt(scanner.nextLine());
            switch (choose) {
                case 1:
                    createContact();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Bạn nhập sai vui lòng nhập lại !");
                    break;
            }
        }
        while (choose != 0);
    }

    public static void editContactMenu() {
        Scanner scanner = new Scanner(System.in);
        int choose;
        do {
            System.out.println("1. Sửa danh bạ");
            System.out.println("0. Quay lại menu");
            System.out.println("Mời bạn nhập lựa chọn ");
            choose = Integer.parseInt(scanner.nextLine());
            switch (choose) {
                case 1:
                    editContact();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Bạn nhập sai vui lòng nhập lại !");
                    break;
            }
        }
        while (choose != 0);
    }

    public static void createContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mời bạn nhập tên: ");
        String name = scanner.nextLine();
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Mời bạn nhập số điện thoại: ");
        String phoneNumber = scanner2.nextLine();
        Scanner scanner3 = new Scanner(System.in);
        System.out.println("Mời bạn nhập địa chỉ: ");
        String address = scanner3.nextLine();
        Scanner scanner4 = new Scanner(System.in);
        System.out.println("Mời bạn nhập email: ");
        String email = scanner4.nextLine();
        TelephoneDirectory contact = new TelephoneDirectory(name, phoneNumber, address, email);
        Boolean check = MANAGER.checkPhoneNumber(phoneNumber);
        if (check == true) {
            System.out.println("Số điện thoại đã tồn tại");
        } else {
            MANAGER.addTelephoneDirectory(contact);
            System.out.println("Đã thêm mới danh bạ");
        }
    }

    public static void editContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mời bạn số điện thoại: ");
        String phoneNumber = scanner.nextLine();
        Boolean check = MANAGER.checkPhoneNumber(phoneNumber);
        if (check == true) {
            TelephoneDirectory contact = MANAGER.findDirectoryByPhoneNumber(phoneNumber);
            if (contact == null)
                System.err.println("Không tồn tại khách hàng có tên: " + phoneNumber);
            else {
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Mời bạn nhập tên mới: ");
                String newName = scanner1.nextLine();
                contact.setName(newName);
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("Mời bạn nhập số điện thoại mới: ");
                String newPhoneNumber = scanner2.nextLine();
                contact.setPhoneNumber(newPhoneNumber);
                Scanner scanner3 = new Scanner(System.in);
                System.out.println("Mời bạn nhập địa chỉ mới: ");
                String newAddress = scanner3.nextLine();
                contact.setAddress(newAddress);
                Scanner scanner4 = new Scanner(System.in);
                System.out.println("Mời bạn nhập email mới: ");
                String newEmail = scanner4.nextLine();
                contact.setEmail(newEmail);
            }
        } else {
            System.out.println("Không tồn tại danh bạ có số điện thoại " + phoneNumber);
        }
    }

    public static void removeContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mời bạn nhập phoneNumber ");
        String phoneNumber = scanner.nextLine();
        TelephoneDirectory contact = MANAGER.findDirectoryByPhoneNumber(phoneNumber);
        if (contact == null) {
            System.out.println("Không có danh bạ có số điện thoại " + phoneNumber);
        } else {
            if (acceptDelete())
                MANAGER.removeTelephoneDirectory(contact);
        }
    }

    public static boolean acceptDelete() {
        boolean check = false;
        Scanner scanner = new Scanner(System.in);
        String accept = scanner.nextLine();
        switch (accept) {
            case "Có":
                check = true;
                break;
            case "Không":
                check = false;
            default:
                System.out.println("Bạn đã nhập sai vui lòng nhập lại");
                break;
        }
        return check;
    }

    public static void search() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mời bạn nhập số điện thoại");
        String phoneNumber = scanner.nextLine();
        TelephoneDirectory contact = MANAGER.findDirectoryByPhoneNumber(phoneNumber);
        if (contact == null)
            System.out.println("Không tồn tại contact có số điện thoại " + phoneNumber);
        else
            System.out.println(contact.toString());
    }
}