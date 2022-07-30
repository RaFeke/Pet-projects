import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {
    private final Map<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) throws Exception {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        String[] components = data.split("\\s+");
        if (components.length != 4) {
            throw new IllegalArgumentException("Wrong format. Corret format:\n"
                    + "add Василий Петров vasily.petrov@gmail.com +79215637722");
        }
        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];
        storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));
        if (!components[3].matches("^+?[78][-\\(]?\\d{3}\\)?-?\\d{3}-?\\d{2}-?\\d{2}$")) {

            throw new Exception("Wrong format. Corret format: +79215637722");

        }
        if (!components[2].matches("^[_A-Za-z0-9]+[A-Za-z0-9]{5,}@gmail.com$")) {

            throw new Exception("Wrong format. Corret format: vasily.petrov@gmail.com");

        }
        if (!components[1].matches("([A-Za-z]+),\\s+([A-Za-z]+)")) {
            throw new Exception("Wrong format. Corret format: Василий Петров");
        }
    }


    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }
}