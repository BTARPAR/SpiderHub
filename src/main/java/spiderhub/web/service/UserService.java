package spiderhub.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import spiderhub.model.dao.UserDao;

@Controller
public class UserService {

	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/service/user/{userName}", method = RequestMethod.GET)
	
	public boolean getUserName(@PathVariable String UserName) {
		System.out.println(UserName);
		return userDao.isUserAvailable(UserName);
	}

}
