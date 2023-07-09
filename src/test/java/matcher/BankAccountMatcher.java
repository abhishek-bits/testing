package matcher;

import bank.BankAccount;
import org.mockito.ArgumentMatcher;

public class BankAccountMatcher implements ArgumentMatcher<BankAccount> {

    private BankAccount bankAccount;

    public BankAccountMatcher(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public boolean matches(BankAccount otherBankAccount) {
        if(null == bankAccount && null == otherBankAccount) {
            return true;
        }
        if((null != bankAccount && null == otherBankAccount) || (null == bankAccount && null != otherBankAccount)) {
            return false;
        }
        return bankAccount.getBalance().equals(otherBankAccount.getBalance());
    }
}
