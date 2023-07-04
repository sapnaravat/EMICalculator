import java.util.ArrayList;
import java.util.List;

public class Loan {
    private  String customerName;
    private String adminUserName;
    private  double principleAmount;
    private  double intrestRate;
    private int loanTenureMonths;

    private List<EmiPayment> emiPayments;
    public Loan(String customerName , String adminUserName , double principleAmount ,double intrestRate , int loanTenure ){
        this.customerName = customerName;
        this.adminUserName = adminUserName;
        this.principleAmount = principleAmount;
        this.intrestRate = intrestRate;
        this.loanTenureMonths = loanTenure;
        this.emiPayments = new ArrayList<>();
    }

    public  double getTotalPayableAmount(){
        double I = getTotalInterest();
        return  principleAmount+I;
    }

    public int getLoanTenureMonths() {
        return loanTenureMonths;
    }

    public double getPerMonthEMIAmount() {
        double totalAmount = getTotalPayableAmount();
        return totalAmount/loanTenureMonths;
    }

    public List<EmiPayment> getEmiPayments() {
        return emiPayments;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getPrincipalAmount() {
        return principleAmount;
    }

    public double getInterestRate() {
        return intrestRate;
    }

    public double getTotalInterest() {
        return (principleAmount*intrestRate*loanTenureMonths)/100;
    }
}
