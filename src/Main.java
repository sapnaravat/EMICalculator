// The User, Loan, and EmiPayment classes remain the same as described earlier.

import java.text.DecimalFormat;
import java.util.*;

public class Main {

    private static Map<String, Double> loans = new HashMap<>();
    private static List<User> users = new ArrayList<>();
    private static List<Loan> loanList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Creating default admin users
        users.add(new User("user1", true, "user2"));
        users.add(new User("user2", true, "user1"));

        while (true) {
            System.out.println("Welcome to Loan Management System");
            System.out.println("1. Create User (Admin)");
            System.out.println("2. Create Loan");
            System.out.println("3. Calculate EMI");
            System.out.println("4. Make EMI Payment");
            System.out.println("5. Fetch Loan Info");
            System.out.println("6. Fetch All Loans for Customer (Admin)");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createUser(scanner);
                    break;
                case 2:
                    createLoan(scanner);
                    break;
                case 3:
                    calculateEMI(scanner);
                    break;
                case 4:
                    makeEmiPayment(scanner);
                    break;
                case 5:
                    fetchLoanInfo(scanner);
                    break;
                case 6:
                    fetchAllLoansForCustomer(scanner);
                    break;
                case 7:
                    System.out.println("Exiting Loan Management System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createUser(Scanner scanner) {
        System.out.print("Enter new username: ");
        String newUsername = scanner.next();

        boolean isAdmin = false;
        System.out.print("Is the user an admin? (true/false): ");
        isAdmin = scanner.nextBoolean();

        if (findUser(newUsername) != null) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        String adminName = "";
        if (isAdmin) {
            System.out.print("Enter admin name: ");
            adminName = scanner.next();
        }

        users.add(new User(newUsername, isAdmin, adminName));
        System.out.println("User created successfully.");
    }

    private static void createLoan(Scanner scanner) {
        System.out.print("Enter your username (admin): ");
        String adminUsername = scanner.next();

        User adminUser = findAdminUser(adminUsername);
        if (adminUser == null) {
            System.out.println("Admin user not found. Please create an admin user first.");
            return;
        }

        System.out.print("Enter customer name: ");
        String customerName = scanner.next();

        if (findUser(customerName) != null) {
            System.out.println("Customer name already exists as a user. Please choose a different name.");
            return;
        }

        System.out.print("Enter the loan amount: ");
        double loanAmount = scanner.nextDouble();

        System.out.print("Enter the interest rate (annual): ");
        double interestRate = scanner.nextDouble();
        // Convert interest rate from annual to monthly
        interestRate /= (12 * 100);

        System.out.print("Enter the loan tenure (in months): ");
        int loanTenureMonths = scanner.nextInt();
        Loan newLoan = new Loan(customerName, adminUsername, loanAmount, interestRate, loanTenureMonths);
        loanList.add(newLoan);
        loans.put(customerName, loanAmount);
        System.out.println("Loan created successfully for " + customerName);
    }

    private static void calculateEMI(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.next();

        User user = findUser(username);
        if (user == null) {
            System.out.println("User not found. Please create a user first.");
            return;
        }

        System.out.print("Enter customer name: ");
        String customerName = scanner.next();

        Loan loan = findLoanByCustomerName(customerName);
        if (loan == null) {
            System.out.println("Loan not found for the customer. Please create a loan first.");
            return;
        }

        int loanTenureMonths = loan.getLoanTenureMonths();
        double emiAmount = loan.getPerMonthEMIAmount() / loanTenureMonths;

        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("EMI Amount: " + df.format(emiAmount));
    }

    private static void makeEmiPayment(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.next();

        User user = findUser(username);
        if (user == null) {
            System.out.println("User not found. Please create a user first.");
            return;
        }

        System.out.print("Enter customer name: ");
        String customerName = scanner.next();

        Loan loan = findLoanByCustomerName(customerName);
        if (loan == null) {
            System.out.println("Loan not found for the customer. Please create a loan first.");
            return;
        }

        System.out.print("Enter EMI number to be paid: ");
        int emiNumber = scanner.nextInt();

        List<EmiPayment> emiPayments = loan.getEmiPayments();
        if (emiNumber <= 0 || emiNumber > emiPayments.size()) {
            System.out.println("Invalid EMI number. Please enter a valid EMI number.");
            return;
        }

        EmiPayment selectedEmi = emiPayments.get(emiNumber - 1);
        System.out.println("Paid EMI " + selectedEmi.getEmiNumber() + " of amount " + selectedEmi.getEmiAmount());
    }

    private static void fetchLoanInfo(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.next();

        User user = findUser(username);
        if (user == null) {
            System.out.println("User not found. Please create a user first.");
            return;
        }

        System.out.print("Enter customer name: ");
        String customerName = scanner.next();

        Loan loan = findLoanByCustomerName(customerName);
        if (loan == null) {
            System.out.println("Loan not found for the customer.");
            return;
        }

        System.out.println("Loan Details for Customer: " + loan.getCustomerName());
        System.out.println("Principal Amount: " + loan.getPrincipalAmount());
        System.out.println("Interest Rate (Annual): " + loan.getInterestRate() * 12 * 100 + "%");
        System.out.println("Loan Tenure (Months): " + loan.getLoanTenureMonths());
        System.out.println("Total Interest: " + loan.getTotalInterest());
        System.out.println("Total Payable Amount: " + loan.getTotalPayableAmount());

        List<EmiPayment> emiPayments = loan.getEmiPayments();
        if (!emiPayments.isEmpty()) {
            System.out.println("EMI Payments:");
            System.out.println("EMI Number\tEMI Amount");
            for (EmiPayment emiPayment : emiPayments) {
                System.out.println(emiPayment.getEmiNumber() + "\t\t\t" + emiPayment.getEmiAmount());
            }
        } else {
            System.out.println("No EMI payments done yet.");
        }
    }

    private static void fetchAllLoansForCustomer(Scanner scanner) {
        System.out.print("Enter your username (admin): ");
        String adminUsername = scanner.next();

        User adminUser = findAdminUser(adminUsername);
        if (adminUser == null) {
            System.out.println("Admin user not found. Please create an admin user first.");
            return;
        }

        System.out.print("Enter customer name: ");
        String customerName = scanner.next();

        List<Loan> customerLoans = findAllLoansForCustomer(customerName);
        if (customerLoans.isEmpty()) {
            System.out.println("No loans found for the customer.");
            return;
        }

        System.out.println("Loans for Customer: " + customerName);
        System.out.println("Loan ID\t\tPrincipal Amount\tInterest Rate\tLoan Tenure (Months)");
        for (Loan loan : customerLoans) {
            System.out.println(loan.hashCode() + "\t\t" + loan.getPrincipalAmount() + "\t\t" + loan.getInterestRate() * 12 * 100 + "%\t\t" + loan.getLoanTenureMonths());
        }
    }

    // Helper methods to find users and loans
    private static User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    private static User findAdminUser(String username) {
        User user = findUser(username);
        if (user != null && user.isAdmin()) {
            return user;
        }
        return null;
    }

    private static Loan findLoanByCustomerName(String customerName) {
        for (Loan loan : loanList) {
            if (loan.getCustomerName().equalsIgnoreCase(customerName)) {
                return loan;
            }
        }
        return null;
    }

    private static List<Loan> findAllLoansForCustomer(String customerName) {
        List<Loan> customerLoans = new ArrayList<>();
        for (Loan loan : loanList) {
            if (loan.getCustomerName().equalsIgnoreCase(customerName)) {
                customerLoans.add(loan);
            }
        }
        return customerLoans;
    }
}




