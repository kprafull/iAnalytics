package com.analytics.iAnalytics.dao.service;

import com.analytics.controller.ReportController;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FetchData {
    static String url = "jdbc:postgresql://localhost/";
    static String db = "prafull";
    static String driver = "org.postgresql.Driver";
    static String user = "prafull";
    static String pass = "";

    static {
        try {
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        log.info("Response-> {}", fetch("select city,avg_temp,avg_humidity from WEATHER"));
    }

    /**
     * The {{@link ReportController}} is for testing<br>
     * <b>Note:</b> It must be tested
     * @param sql
     * @return
     */
    public static List<Object[]> fetch(String sql) {
        List<Object[]> data = new ArrayList<Object[]>();
        try {
            Connection con = DriverManager.getConnection(url + db, user, pass);
            try {
                Statement st = con.createStatement();
                ResultSet res = st.executeQuery(sql);
                int cols = res.getMetaData().getColumnCount();
                while (res.next()) {
                    Object[] row = new Object[cols];


                    for (int i = 1; i <= cols; i++) {
                        int type = res.getMetaData().getColumnType(i);
                        //String name = res.getMetaData().getColumnName(i);
                        //log.info("Type is {} for column {}", type, name);
                        switch (type) {
                            case Types.NUMERIC:
                                row[i - 1] = res.getFloat(i);
                                break;
                            case Types.INTEGER:
                                row[i - 1] = res.getInt(i);
                                break;
                            case Types.DOUBLE:
                                row[i - 1] = res.getDouble(i);
                                break;
                            case Types.DATE:
                                row[i - 1] = res.getDate(i);
                                break;
                            default:
                                row[i - 1] = res.getString(i);
                                break;
                        }
                    }
                    data.add(row);
                    //log.info("Row-> {}", Arrays.toString(row));
                }
                con.close();
            } catch (SQLException s) {
                s.printStackTrace();
                log.error("Failed to fetch with ERROR -> {}", sql);
                throw new RuntimeException("Failed to get Data");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return data;
    }
}
