package org.common.helper;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.common.constant.ConfigConstant;
import org.common.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by paul on 2017/8/21.
 */
public class DataBaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseHelper.class);

    private static String DRIVER;
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    //apache dbutil
    private static final QueryRunner QUERY_RUNNER ;

    private static final DruidDataSource DATA_SOURCE;

    //存放Connection
    //创建放入threadlocal,使用完毕，关闭，并从threadlocal中移除
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    //读取配置文件，获取jdbc相关配置
    static {

        CONNECTION_HOLDER = new ThreadLocal<>();
        QUERY_RUNNER = new QueryRunner();

        DRIVER = ConfigHelper.getJdbcriver();
        URL = ConfigHelper.getJdbcUrl();
        USERNAME = ConfigHelper.getJdbcUsername();
        PASSWORD = ConfigHelper.getJdbcPassword();

//        try {
//            Class.forName(DRIVER);
//        } catch (ClassNotFoundException e) {
//            LOGGER.error("can load jdbc driver",e);
//
//        }

        //初始化数据库连接池
        DATA_SOURCE = new DruidDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
    }

    /**
     * 获取连接
     * @return
     */
    public static Connection getConnection() {
//        Connection conn = null;
        Connection conn = CONNECTION_HOLDER.get();
        if (null == conn) {
            try {
//                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                conn= DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * 关闭连接
     * @param
     */
    public static void closeConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

//    ArrayHandler ：将ResultSet中第一行的数据转化成对象数组
//    ArrayListHandler将ResultSet中所有的数据转化成List，List中存放的是Object[]
//    BeanHandler ：将ResultSet中第一行的数据转化成类对象
//    BeanListHandler ：将ResultSet中所有的数据转化成List，List中存放的是类对象
//    ColumnListHandler ：将ResultSet中某一列的数据存成List，List中存放的是Object对象
//    KeyedHandler ：将ResultSet中存成映射，key为某一列对应为Map。Map中存放的是数据
//    MapHandler ：将ResultSet中第一行的数据存成Map映射
//    MapListHandler ：将ResultSet中所有的数据存成List。List中存放的是Map
//    ScalarHandler ：将ResultSet中一条记录的其中某一列的数据存成Object
//    org.apache.commons.dbutils.wrappers
//    SqlNullCheckedResultSet ：对ResultSet进行操作，改版里面的值
//    StringTrimmedResultSet ：去除ResultSet中中字段的左右空格。Trim()


    /**
     * 查询实体列表（DButil提供的queryRunner对象可以面向实体查询，实际上dbutil执行sql首先返回一个ResultSet,然后利用反射
     * 创建实体对象）
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass,String sql,Object... params){
        List<T> entityList = null;
        try {
            Connection conn = getConnection();
            entityList=  QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return entityList;
    }

    public static <T> T queryEntity(Class<T> entityClass,String sql,Object... params){
        T entity = null;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return entity;
    }

    /**
     * 执行查询语句，map表示列名与列值得映射关系
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String ,Object>> executeQuery(String sql,Object...params) {
        List<Map<String, Object>> result = null;
        try {
            Connection conn = getConnection();
            result  = QUERY_RUNNER.query(conn, sql,new MapListHandler(),params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return result;
    }


    /**
     * 执行更新语句（update,insert,delete）
     */
    public static int executeUpdate(String sql,Object...params) {
        int row=0;
        try {
            Connection conn = getConnection();
            row  = QUERY_RUNNER.update(conn,sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return row;
    }

    //todo
    // 插入实体
    //todo
    // 更新实体
    //todo
    // 删除实体


}
