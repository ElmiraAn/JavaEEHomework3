package com.elmira.aston.homework3.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnection {
    private static Properties properties;
    private static volatile MyConnection INSTANCE;
    private static String MYSQL = "datasource.properties";

    public MyConnection() {
        setProperties();
        connectDriver();
    }

    public MyConnection(String file) {
        setProperties(file);
        connectDriver();
    }

    private void connectDriver() {
        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("check the driver");
        }
    }

    private void setProperties(String file) {
        try {
            properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            throw new RuntimeException("check the properties");
        }
    }

    private void setProperties() {
        try {
            properties = new Properties();
            InputStream in = getClass().getClassLoader().getResourceAsStream(MYSQL);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password")
        );
    }

    public static MyConnection getInstance() {
        if (INSTANCE == null) {
            synchronized (MyConnection.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyConnection();
                }
            }
        }
        return INSTANCE;
    }

    public static MyConnection getInstance(String file) {
        if (INSTANCE == null) {
            synchronized (MyConnection.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyConnection(file);
                }
            }
        }
        return INSTANCE;
    }
}
