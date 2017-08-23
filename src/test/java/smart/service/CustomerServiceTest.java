package smart.service;

import org.junit.Assert;
import org.junit.Test;
import org.smart.model.Customer;
import org.smart.service.CustomerService;

import java.util.List;

/**
 * Created by paul on 2017/8/21.
 */
public class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new CustomerService();
    }


    @Test
    public void getCustomerListTest() throws Exception {
        List<Customer> customerList = customerService.getCustomerList();
        System.err.print(customerList.get(0).getName());
//        Assert.assertNotNull(customerList);
    }
}
