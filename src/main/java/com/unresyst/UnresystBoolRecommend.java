package com.unresyst;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.io.IOException;

import org.apache.commons.cli2.OptionException; 
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class UnresystBoolRecommend {
    
    public static void main(String... args) throws FileNotFoundException, TasteException, IOException, OptionException {
        
    	
    	MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName("localhost");
		dataSource.setPort(3306);
		dataSource.setUser("root");
		dataSource.setPassword("");
		dataSource.setDatabaseName("Marketplace_development");
		
		MySQLJDBCDataModel dataModel = new MySQLJDBCDataModelBigId(dataSource, "comments", "authorId", "app_id", "rating", "updated_at");
		
		
		
/*		java.sql.Connection connection;
		Statement statement;
		ResultSet resultSet;
		
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from mytesttable");
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}*/
    	
    	
        // create data source (model) - from the csv file            
//        File ratingsFile = new File("datasets/dummy-bool.csv");                        
//        DataModel model = new FileDataModel(ratingsFile);
        
        // create a simple recommender on our data
//        CachingRecommender cachingRecommender = new CachingRecommender(new SlopeOneRecommender(model));
		CachingRecommender cachingRecommender = new CachingRecommender(new SlopeOneRecommender(dataModel));
        
        // for all users
//        for (LongPrimitiveIterator it = model.getUserIDs(); it.hasNext();){
		LongPrimitiveIterator it2 = dataModel.getUserIDs(); 
		
//		for (LongPrimitiveIterator it = dataModel.getUserIDs(); it.hasNext();){
		
			LongPrimitiveIterator it = dataModel.getUserIDs();
            long userId = it.nextLong();
            
            // get the recommendations for the user
            List<RecommendedItem> recommendations = cachingRecommender.recommend(userId, 10);
            
            // if empty write something
            if (recommendations.size() == 0){
                System.out.print("User ");
                System.out.print(Utils.migrator.toStringID(userId));
                System.out.println(": no recommendations");
            }
                            
            // print the list of recommendations for each 
            for (RecommendedItem recommendedItem : recommendations) {
                System.out.print("User ");
                System.out.print(Utils.migrator.toStringID(userId));
                System.out.print(": ");
                System.out.println(recommendedItem);
            }
//        }        
    }
}