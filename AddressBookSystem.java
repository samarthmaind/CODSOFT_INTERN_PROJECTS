import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber + ", Email Address: " + emailAddress;
    }
}

class AddressBook {
    private List<Contact> contacts;

    public AddressBook() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null; // Contact not found
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }
}

public class AddressBookSystem {
    private AddressBook addressBook;
    private Scanner scanner;

    public AddressBookSystem() {
        addressBook = new AddressBook();
        scanner = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Address Book Options:");
        System.out.println("1. Add Contact");
        System.out.println("2. Remove Contact");
        System.out.println("3. Search Contact");
        System.out.println("4. Display All Contacts");
        System.out.println("5. Exit");
    }

    public void performAction(int choice) {
        switch (choice) {
            case 1:
                addContact();
                break;
            case 2:
                removeContact();
                break;
            case 3:
                searchContact();
                break;
            case 4:
                displayAllContacts();
                break;
            case 5:
                System.out.println("Exiting Address Book.");
                scanner.close();
                System.exit(0);
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void addContact() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty. Contact not added.");
            return;
        }

        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine().trim();

        System.out.print("Enter Email Address: ");
        String emailAddress = scanner.nextLine().trim();

        Contact contact = new Contact(name, phoneNumber, emailAddress);
        addressBook.addContact(contact);
        writeContactsToFile();
        System.out.println("Contact added successfully.");
    }

    private void removeContact() {
        System.out.print("Enter Name to remove: ");
        String nameToRemove = scanner.nextLine().trim();

        Contact contactToRemove = addressBook.searchContact(nameToRemove);

        if (contactToRemove != null) {
            addressBook.removeContact(contactToRemove);
            writeContactsToFile();
            System.out.println("Contact removed successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    private void searchContact() {
        System.out.print("Enter Name to search: ");
        String nameToSearch = scanner.nextLine().trim();

        Contact searchedContact = addressBook.searchContact(nameToSearch);

        if (searchedContact != null) {
            System.out.println("Contact details:");
            System.out.println(searchedContact);
        } else {
            System.out.println("Contact not found.");
        }
    }

    private void displayAllContacts() {
        List<Contact> contacts = addressBook.getAllContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("All Contacts:");
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    private void writeContactsToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("contacts.dat"))) {
            outputStream.writeObject(addressBook.getAllContacts());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readContactsFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("contacts.dat"))) {
            List<Contact> contacts = (List<Contact>) inputStream.readObject();
            addressBook = new AddressBook();
            addressBook.getAllContacts().addAll(contacts);
        } catch (IOException | ClassNotFoundException e) {
            // File not found or error in reading, proceed with an empty AddressBook
            addressBook = new AddressBook();
        }
    }

    public static void main(String[] args) {
        AddressBookSystem addressBookSystem = new AddressBookSystem();
        addressBookSystem.readContactsFromFile();

        while (true) {
            addressBookSystem.displayOptions();
            System.out.print("Enter your choice: ");
            int choice = addressBookSystem.scanner.nextInt();
            addressBookSystem.scanner.nextLine(); // Consume the newline character
            addressBookSystem.performAction(choice);
        }
    }
}
