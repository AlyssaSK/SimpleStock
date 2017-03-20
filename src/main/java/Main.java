import com.simple.stock.model.Account;
import com.simple.stock.model.Customer;
import com.simple.stock.model.Order;
import com.simple.stock.ref.OperationType;
import com.simple.stock.service.CustomerService;
import com.simple.stock.service.FileService;
import com.simple.stock.service.OrderService;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.runner.*;
import org.junit.runner.notification.Failure;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args){
        /* Получение списка клиентов в виде строк из файла */
        Stream<String> clientsStream = FileService.getStream( FileService.CLIENTS_FILE_NAME );
        /* Преобразование потока строк в отображение клиентов и их счетов */
        Map<Customer, Account> accountMap = CustomerService.getAccounts(clientsStream);

        out.println("Customer list before processing");
        accountMap.values().stream().sorted().forEach( out :: println );

        /* Получение списка заявок в виде строк из файла */
        Stream<String> ordersStream = FileService.getStream( FileService.ORDERS_FILE_NAME );
        /* Преобразование потока строк списки заявок сгруппированные по типу операций */
        Map<OperationType, List<Order>> operationTypeListMap = OrderService.getOrderMap(ordersStream);
        /* Обработка заявок. Обновление счетов с учетом исполненных заявок*/
        OrderService.processOrders(accountMap, operationTypeListMap);

        out.println("Customer list after processing");
        accountMap.values().stream().sorted().forEach( out :: println );
        /* Запись результата в файл */
        FileService.writeAccounts(accountMap.values(), FileService.RESULT_FILE_NAME);

        printTestResults();
    }

    private static void printTestResults() {
        Result result = JUnitCore.runClasses();

        for (Failure failure : result.getFailures()) {
            out.println("failure: " + failure.toString());
        }

        out.println("Test result: " + result.wasSuccessful());
    }
}
