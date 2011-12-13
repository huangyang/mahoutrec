package com.unresyst;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.util.Collection;

import javax.sql.DataSource;

import org.apache.cassandra.thrift.Cassandra.login_args;
import org.apache.cassandra.thrift.Cassandra.login_args;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.IDMigrator;

public class MySQLJDBCDataModelBigId extends MySQLJDBCDataModel {

	public MySQLJDBCDataModelBigId(DataSource dataSource,
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


