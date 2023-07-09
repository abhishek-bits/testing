package bank;

import java.util.Objects;

public class BankAccount {

    private Double balance;

    public BankAccount() {
        balance = 0.0D;
    }

    public BankAccount(Double amount) {
        balance = amount;
    }

    public void withdraw(Double amount) {
        balance -= Math.min(BankAccountConstant.MAX_WITHDRAWAL_VALUE, amount);
    }

    public void deposit(Double amount) {
        if(amount < 0) {
            throw new NumberFormatException("Negative Values not allowed.");
        }
        balance += amount;
    }

    public Double getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                '}';
    }
}