import lombok.Data;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Bank {
    private HashMap<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        synchronized (fromAccountNum.compareTo(toAccountNum) > 0 ? toAccountNum : fromAccountNum) {
            synchronized (toAccountNum.compareTo(fromAccountNum) > 0 ? fromAccountNum : toAccountNum) {
                if (!(accounts.get(fromAccountNum).isBlocked() && accounts.get(toAccountNum).isBlocked())) {

                    accounts.get(fromAccountNum).setMoney(accounts.get(fromAccountNum).getMoney() - amount);
                    accounts.get(toAccountNum).setMoney(accounts.get(toAccountNum).getMoney() + amount);
                } else {
                    System.out.println("Accounts is blocked. Transfer is not possible");
                }
                if (amount > 50000) {
                    if (isFraud(fromAccountNum, toAccountNum, amount)) {
                        accounts.get(fromAccountNum).setBlocked(true);
                        accounts.get(toAccountNum).setBlocked(true);
                        System.out.println("Accounts have been blocked");
                    }
                }
            }
        }

    }

    public long getBalance(String accountNum) {
        return accounts.get(accountNum).getMoney();
    }

    public long getAllMoney() {
        AtomicLong allMoney = new AtomicLong();
        getAccounts().forEach((k, v) -> allMoney.addAndGet(v.getMoney()));
        return allMoney.get();
    }
}
