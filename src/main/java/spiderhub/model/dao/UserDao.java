package spiderhub.model.dao;

import java.util.List;

import spiderhub.model.User;

public interface UserDao {

	User saveUser(User user);

	User getUserByUsername(String userName);


	User getUser(Integer id);

	List<User> getUsers();
	
	List<User> getUserToaddInProject();
	
	List<User> getUsrToAssignTask(Integer id);

	User checkEmailExist(String emailAddress);

	User isUserAvailable(String userName);

	User ajaxEmailExist(String emailAddress);
	

	
	
	
	
}
