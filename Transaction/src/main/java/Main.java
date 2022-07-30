import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        ArrayList<Thread> threads = new ArrayList<>();

        // заполняем банк аккаунтами
        for (int i = 0; i < 100; i++) {
            bank.getAccounts().put("Account # " + i, new Account(200000, "First", false));
        }
        System.out.println("Всего денег в банке до транзакций: " + bank.getAllMoney());

        for (int i = 1; i <= 10; i++) // 10 потоков
        {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) // 100 транзакций в потоке
                {
                    // транзакции
                    try {
                        bank.transfer("Account # " + (int) (100 * Math.random()),
                                "Account # " + (int) (100 * Math.random()),
                                (int) (10000 * Math.random())
                        );
                        System.out.println(bank.getBalance("Account # " + (int) (100 * Math.random())));
                        System.out.println(bank.getBalance("Account # " + (int) (100 * Math.random())));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads.add(thread);
        }
        //Запускаем потоки
        threads.forEach(Thread::run);

        //ждем
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Всего денег в банке после транзакций: " + bank.getAllMoney());


    }
}
