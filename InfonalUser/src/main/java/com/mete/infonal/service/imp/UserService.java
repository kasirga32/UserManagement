package com.mete.infonal.service.imp;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mete.infonal.controller.UserController;
import com.mete.infonal.model.User;
import com.mete.infonal.service.inf.UserServiceInterface;


@Repository
public class UserService implements UserServiceInterface {

	@Autowired
	private MongoTemplate mongo;
	
	final static Logger logger = Logger.getLogger(UserService.class);

	/* 
	 * Adding users to collection, if collection does not exists then firstly create collection
	 */
	public void addUser(User user) {
		if (!mongo.collectionExists(User.class)) {
			mongo.createCollection(User.class);
			logger.info("The collection can not be found. New collection just created.");
		}
		user.setId(UUID.randomUUID().toString()); 
		
		mongo.insert(user, COLLECTION_NAME); 
		
		logger.info(user + " user added to the collection.");
		
	}
	public void updateUser(User user) {
		mongo.save(user, COLLECTION_NAME);
	}
	
	public List<User> getAllUsers() {
		return mongo.findAll(User.class, COLLECTION_NAME);
	}	

	public void deleteUser(User user) {
		mongo.remove(user, COLLECTION_NAME);
	}


}
