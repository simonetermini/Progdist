public class SupportBalancr {

    private int balance;

    public int getBal() {
        return balance;
    }

    public synchronized void setBal(int bal) {
        this.balance = bal;
    }
}
