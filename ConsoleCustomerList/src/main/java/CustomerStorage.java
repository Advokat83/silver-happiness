import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {
    private final Map<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        String phoneRegex = "[+][0-9]{11}";
        String emailRegex = "[A-Za-z0-9+_.-]+@(.+)$";
        String[] components = data.split("\\s+");
        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];

        if (components.length != 4) {
            throw new RuntimeException("Wrong format. Correct format: \n" +
                    "add Василий Петров vasily.petrov@gmail.com +79215637722");
        }
        if (!components[INDEX_PHONE].matches(phoneRegex)) {
            throw new RuntimeException("Wrong format. Correct format: \n" +
                    "Введите номер в следующем формате: +79215637722");
        }
        if (!components[INDEX_EMAIL].matches(emailRegex)) {
            throw new RuntimeException("Wrong format. Correct format: \n" +
                    "Введите корректный email. Например: vasily.petrov@gmail.com");
        }
        storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));
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