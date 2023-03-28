package db;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

public class Connection {
	private static Connection instance;
	private MongoClient mongoClient;
	
	public Connection() {
		String uri = "mongodb://localhost:27017";
		mongoClient = MongoClients.create(uri);
		System.out.println("Connected!");
	}
	
	public static Connection getInstance() {
		if(instance == null)
			instance = new Connection();
		return instance;
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

}
