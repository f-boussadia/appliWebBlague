package com.fairouz.m2i.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class MyConnection {
	public static DataSource ds = null;

	public static DataSource initDataSource() {
		if (ds == null) {
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			try {
				ResourceBundle ressources = ResourceBundle.getBundle("myDataBase");
				// myDatabase.properties
				String jdbcDriver = ressources.getString("jdbcDriver");
				String dbUrl = ressources.getString("dbUrl");
				String username = ressources.getString("username");
				String password = ressources.getString("password");
				cpds.setDriverClass(jdbcDriver);
				cpds.setJdbcUrl(dbUrl);
				cpds.setUser(username);
				cpds.setPassword(password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Optional Settings:
			cpds.setInitialPoolSize(2);
			cpds.setMinPoolSize(2);
			cpds.setMaxPoolSize(10);
			System.out.println("cpds=" + cpds.toString());
			ds = cpds;
		}
		return ds;
	}

	public static Connection seConnecter() {

		DataSource dataSource = initDataSource();
		Connection cn = null;
		try {
			cn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cn;
	}

	public static void closeConnection(Connection cn) {
		try {
			cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void closeStatement(Statement st) {
		try {
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Long getAutoIncPk(Statement st) {

		Long pk = null;
		try {
			ResultSet rsKeys = st.getGeneratedKeys();

			if (rsKeys.next()) {
				pk = rsKeys.getLong(1);
			}
			rsKeys.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pk;
	}
}
