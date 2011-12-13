package com.unresyst;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.antlr.grammar.v3.ANTLRv3Parser.finallyClause_return;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public final class MarketplaceDataModel extends MySQLJDBCDataModel {
	
	private static final String DATABASE_URL = "127.0.0.1";
	private static final String DATABASE_NAME = "Marketplace_development";
	private static final int 	DATABASE_PORT = 3306;
	private static final String DATABASE_USERNAME = "root";
	private static final String DATABASE_PASSWORD = "";
	private static final String TABLE_NAME = "comments";
	private static final String USER_COLUMN_NAME = "authorId";
	private static final String APP_COLUMN_NAME = "app_id";
	private static final String RATING_COLUMN_NAME = "rating";
	private static final String UPDATE_TIME_COLUMN = "updated_at";
	
	public static MarketplaceDataModel createMarketplaceDataModel() {
    	MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName(DATABASE_URL);
		dataSource.setPort(DATABASE_PORT);
		dataSource.setUser(DATABASE_USERNAME);
		dataSource.setPassword(DATABASE_PASSWORD);
		dataSource.setDatabaseName(DATABASE_NAME);
		
		MarketplaceDataModel dataModel = new MarketplaceDataModel(dataSource, TABLE_NAME, USER_COLUMN_NAME, APP_COLUMN_NAME, RATING_COLUMN_NAME, UPDATE_TIME_COLUMN);
		return dataModel;
	}
	
	private MarketplaceDataModel(DataSource dataSource,
			String preferenceTable, String userIDColumn, String itemIDColumn,
			String preferenceColumn, String timestampColumn) {
		super(dataSource, preferenceTable, userIDColumn, itemIDColumn,
				preferenceColumn, timestampColumn);
	}
	
	@Override
	protected long getLongColumn(ResultSet rs, int position) throws SQLException {
		System.out.println("In GetLongColumn");
		String positionString = rs.getString(position);
		System.out.println("Ori id: "+positionString);
		long id = Utils.migrator.toLongID(positionString);
		System.out.println("after migrate: "+id);
		return id;
	}
	
	@Override
	protected void setLongParameter(PreparedStatement stmt, int position, long value) throws SQLException {
		System.out.println("In SetLongParameter");
		String longIDString = null;
		try {
			longIDString = Utils.migrator.toStringID(value);
			System.out.println("back to String: "+longIDString);
		} catch (TasteException e) {
			e.printStackTrace();
		}
		stmt.setString(position, longIDString);
	}
}
