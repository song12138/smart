package org.smart.service;

import org.common.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by paul on 2017/8/17.
 */
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private static String DRIVER;
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;


    //读取配置文件，获取jdbc相关配置
    static {
        Properties properties= PropsUtil.loadProperties("jbconfig.properties.");
        DRIVER = PropsUtil.getString(properties, "jdbc.driver");
        URL = PropsUtil.getString(properties, "jdbc.url");
        USERNAME = PropsUtil.getString(properties, "jdbc.username");
        PASSWORD = PropsUtil.getString(properties, "jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can load jdbc driver",e);

        }

    }



    public List<Customer>getCustomerList(String keyword){
        Connection conn = null;

        try {
            List<Customer> customerList = new ArrayList<>();
            String sql="SELECT * FROM CUSTOMER";
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            //预处理语句
            PreparedStatement statement=conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setConnect(rs.getString("connect"));
                customer.setEmail(rs.getString("email"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setRemark(rs.getString("remark"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null ;
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
