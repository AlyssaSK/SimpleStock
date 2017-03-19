import com.simple.stock.model.Account;
import com.simple.stock.model.Client;
import com.simple.stock.model.Order;
import com.simple.stock.ref.OperationType;
import com.simple.stock.service.ClientService;
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
        out.println("Before");
        Stream<String> clientsStream = FileService.getStream(FileService.CLIENTS_FILE_NAME);
        Map<Client, Account> accountMap = ClientService.getAccounts(clientsStream);
        accountMap.values().stream().sorted().forEach( out :: println );

        out.println("After");
        Stream<String> ordersStream = FileService.getStream(FileService.ORDERS_FILE_NAME);
        Map<OperationType, List<Order>> operationTypeListMap = OrderService.getOrderMap(ordersStream);

        Map<Client, Account> accountMapNew = OrderService.processOrders(accountMap, operationTypeListMap);
        accountMapNew.values().stream().sorted().forEach( out :: println );

        ClientService.writeAccounts(accountMapNew.values(), FileService.RESULT_FILE_NAME);

        Result result = JUnitCore.runClasses();

        for (Failure failure : result.getFailures()) {
            out.println("failure: " + failure.toString());
        }

        out.println("Test result: " + result.wasSuccessful());
    }
}
