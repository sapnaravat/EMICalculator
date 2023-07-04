public class EmiPayment {
    private  int emiNumber;
    private double emiAmount;
    public EmiPayment(int emiNumber , double emiAmount){
        this.emiNumber=emiNumber;
        this.emiAmount=emiAmount;
    }
    public  int getEmiNumber(){
        return emiNumber;
    }
    public  double getEmiAmount(){
        return emiAmount;
    }
}
