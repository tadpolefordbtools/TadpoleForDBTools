package com.hangum.tadpole.mongodb.core.test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

/**
 * or example
 * 
 * select * from rental where rental_id = 1 or rental_id = 2;
 * 
 * @author hangum
 * 
 */
public class MongoTestInStmt {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		ConAndAuthentication testMongoCls = new ConAndAuthentication();
		Mongo mongo = testMongoCls.connection(ConAndAuthentication.serverurl, ConAndAuthentication.port);
		DB db = mongo.getDB("test");
		
		DBCollection myColl = db.getCollection("rental");
		
		Integer[] inCondition = {1, 2};		
		BasicDBObject inQuery = new BasicDBObject();
		inQuery.put("rental_id", new BasicDBObject("$in", inCondition));
		
		DBCursor myCursor = myColl.find(inQuery);		
		while (myCursor.hasNext()) {
			System.out.println(myCursor.next());
		}

	}

}
