package org.smart.controller;

import org.common.annotation.Action;
import org.common.annotation.Controller;
import org.common.annotation.Inject;
import org.common.domain.Param;
import org.common.domain.View;
import org.smart.model.Customer;
import org.smart.service.CustomerService;

import java.util.List;

/**
 * Created by paul on 2017/8/21.
 */
@Controller
public class CustomerController {
    @Inject
    private CustomerService customerService;

    @Action("get:/customer")
    public View customerListView(Param param) {
        List<Customer> customerList = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList", customerList);
    }
}
