import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {

    private static final Logger logger =
            LogManager.getLogger(Main.class);

    private static final String ADD_COMMAND = "add Василий Петров " +
            "vasily.petrov@gmail.com +79215637722";
    private static final String COMMAND_EXAMPLES = "\t" + ADD_COMMAND + "\n" +
            "\tlist\n\tcount\n\tremove Василий Петров";
    private static final String COMMAND_ERROR = "Wrong command! Available command examples: \n" +
            COMMAND_EXAMPLES;
    private static final String helpText = "Command examples:\n" + COMMAND_EXAMPLES;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();
        logger.log(Level.WARN, "Сообщение об ошибке");

        while (true) {

            String command = scanner.nextLine();
            logger.info("Информация о запросах в приложении " + command);
            String[] tokens = command.split("\\s+", 2);

            try {
                if (tokens[0].equals("add")) {
                    executor.addCustomer(tokens[1]);
                } else if (tokens[0].equals("list")) {
                    executor.listCustomers();
                } else if (tokens[0].equals("remove")) {
                    executor.removeCustomer(tokens[1]);
                } else if (tokens[0].equals("count")) {
                    System.out.println("There are " + executor.getCount() + " customers");
                } else if (tokens[0].equals("help")) {
                    System.out.println(helpText);
                } else {
                    System.out.println(COMMAND_ERROR);
                }
            }
            catch (Exception ex) {
                logger.error("Текущая ошибка: " + ex.getMessage());
            }
        }
    }
}
