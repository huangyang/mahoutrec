package com.unresyst;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.IDMigrator;

public class Utils {
	
	static IDMigrator migrator = new IDMigrator() {
		
		@Override
		public void refresh(Collection<Refreshable> alreadyRefreshed) {
			
		}
		
		@Override
		public String toStringID(long longID) throws TasteException {	
			return Utils.toStringID(longID);
		}
		
		@Override
		public long toLongID(String stringID) {
			return Utils.toLongID(stringID);
		}
	};
	
	public static String toStringID(long longID) {
		String oriIDString = null;
		System.out.println("longID<0?: "+(longID<0));
		if (longID < 0) {
			BigDecimal bigDecimal = new BigDecimal(longID);
			BigDecimal minLong = new BigDecimal(Long.MIN_VALUE);
			BigDecimal maxLong = new BigDecimal(Long.MAX_VALUE);
			BigDecimal oriBigDecimal = bigDecimal.subtract(minLong.subtract(maxLong)).subtract(new BigDecimal(-1));
			oriIDString = oriBigDecimal.toPlainString();
		}
		if (oriIDString == null) {
			oriIDString = String.format("%d", longID);
		}
		String oriIDStringWithSpace = String.format("%20s", oriIDString);
		String fullIDString = oriIDStringWithSpace.replace(' ', '0');
		return fullIDString;
	}
	
	public static long toLongID(String stringID) {
		return new BigDecimal(stringID).longValue();
	}

}
