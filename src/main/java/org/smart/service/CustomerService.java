package org.smart.service;

import org.common.annotation.Service;
import org.common.helper.DataBaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 2017/8/17.
 */
@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);


    public List<Customer>getCustomerList(){
//        Connection conn = null;
//
//        try {
//            List<Customer> customerList = new ArrayList<>();
//            String sql="SELECT * FROM CUSTOMER";
////            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            conn = DataBaseHelper.getConnection();
//            //预处理语句
//            PreparedStatement statement=conn.prepareStatement(sql);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                Customer customer = new Customer();
//                customer.setId(rs.getLong("id"));
//                customer.setName(rs.getString("name"));
//                customer.setConnect(rs.getString("connect"));
//                customer.setEmail(rs.getString("email"));
//                customer.setTelephone(rs.getString("telephone"));
//                customer.setRemark(rs.getString("remark"));
//                customerList.add(customer);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            DataBaseHelper.closeConnection(conn);
//        }


            String sql = "SELECT * FROM CUSTOMER";
            return DataBaseHelper.queryEntityList(Customer.class,sql);
    }

    public Customer getCustomer(long id){
        return null;
    }

    public boolean creatCustomer(Customer customer){
        return false;
    }

    public boolean updateCustomer(Customer customer) {
        return false;
    }

    public boolean deleteCustomer(Customer customer) {
        return false;
    }
}
