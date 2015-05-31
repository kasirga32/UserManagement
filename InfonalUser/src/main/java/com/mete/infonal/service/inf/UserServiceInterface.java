package com.mete.infonal.service.inf;

import java.util.List;

import com.mete.infonal.model.User;


public interface UserServiceInterface {

	public static final String COLLECTION_NAME = "users";

	/* 
	 * Adding users to collection, if collection does not exists then firstly create collection
	 */
	public void addUser(User user);

	public List<User> getAllUsers();

	/*
	 * Delete person by ID
	 */
	public void deleteUser(User user);

	public void updateUser(User user);

}