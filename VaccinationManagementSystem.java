import java.util.ArrayList;
import java.util.Scanner;

class User {
    String name;
    String email;
    String password;
    ArrayList<Child> children;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.children = new ArrayList<>();
    }

    public void addChild(Child child) {
        children.add(child);
    }

    public ArrayList<Child> getChildren() {
        return children;
    }
}

class Child {
    String name;
    String dateOfBirth;
    String childID;
    ArrayList<Vaccination> vaccinations;

    public Child(String name, String dateOfBirth, String childID) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.childID = childID;
        this.vaccinations = new ArrayList<>();
    }

    public void addVaccination(Vaccination vaccination) {
        vaccinations.add(vaccination);
    }

    public ArrayList<Vaccination> getVaccinations() {
        return vaccinations;
    }
}

class Vaccination {
    String vaccineName;
    String date;
    boolean isCompleted;

    public Vaccination(String vaccineName, String date) {
        this.vaccineName = vaccineName;
        this.date = date;
        this.isCompleted = false;
    }

    public void markAsCompleted() {
        isCompleted = true;
    }

    public String toString() {
        return vaccineName + " on " + date + (isCompleted ? " (Completed)" : " (Scheduled)");
    }
}

public class VaccinationManagementSystem {
    static ArrayList<User> users = new ArrayList<>();
    static User currentUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (currentUser == null) {
                System.out.println("1. Register\n2. Login\n3. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                switch (choice) {
                    case 1:
                        register(scanner);
                        break;
                    case 2:
                        login(scanner);
                        break;
                    case 3:
                        System.exit(0);
                }
            } else {
                System.out.println("1. Add Child\n2. Schedule Vaccination\n3. View Vaccination Records\n4. Logout");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                switch (choice) {
                    case 1:
                        addChild(scanner);
                        break;
                    case 2:
                        scheduleVaccination(scanner);
                        break;
                    case 3:
                        viewVaccinationRecords(scanner);
                        break;
                    case 4:
                        currentUser = null;
                        break;
                }
            }
        }
    }

    public static void register(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        users.add(new User(name, email, password));
        System.out.println("Registration successful!");
    }

    public static void login(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        for (User user : users) {
            if (user.email.equals(email) && user.password.equals(password)) {
                currentUser = user;
                System.out.println("Login successful!");
                return;
            }
        }
        System.out.println("Invalid credentials, please try again.");
    }

    public static void addChild(Scanner scanner) {
        System.out.print("Enter child's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter child's date of birth: ");
        String dob = scanner.nextLine();
        System.out.print("Enter child's ID: ");
        String id = scanner.nextLine();
        Child child = new Child(name, dob, id);
        currentUser.addChild(child);
        System.out.println("Child added successfully!");
    }

    public static void scheduleVaccination(Scanner scanner) {
        if (currentUser.getChildren().isEmpty()) {
            System.out.println("No child registered. Please add a child first.");
            return;
        }
        System.out.println("Select a child:");
        for (int i = 0; i < currentUser.getChildren().size(); i++) {
            System.out.println((i + 1) + ". " + currentUser.getChildren().get(i).name);
        }
        int childIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        Child selectedChild = currentUser.getChildren().get(childIndex);
        System.out.print("Enter vaccine name: ");
        String vaccineName = scanner.nextLine();
        System.out.print("Enter vaccination date: ");
        String date = scanner.nextLine();
        Vaccination vaccination = new Vaccination(vaccineName, date);
        selectedChild.addVaccination(vaccination);
        System.out.println("Vaccination scheduled successfully!");
    }

    public static void viewVaccinationRecords(Scanner scanner) {
        if (currentUser.getChildren().isEmpty()) {
            System.out.println("No child registered.");
            return;
        }
        for (Child child : currentUser.getChildren()) {
            System.out.println("Vaccination records for " + child.name + ":");
            for (Vaccination vaccination : child.getVaccinations()) {
                System.out.println(vaccination);
            }
        }
    }
}
