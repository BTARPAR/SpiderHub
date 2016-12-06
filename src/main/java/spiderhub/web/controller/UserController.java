package spiderhub.web.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spiderhub.model.User;
import spiderhub.model.dao.UserDao;
import spiderhub.model.dao.UserRoleDao;
import spiderhub.web.validator.UserValidator;

@Controller
@SessionAttributes("user")
public class UserController {

	@Autowired
	UserDao userDao;

	@Autowired
	UserRoleDao roleDao;

	@Autowired
	UserValidator userValidator;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/check.html")
	public User checkUser(@PathVariable String userName) {
		System.out.println("corret mapping");
		return userDao.getUserByUsername(userName);
	}

	@RequestMapping(value = "/userRegistration.html", method = RequestMethod.GET)
	public String register(ModelMap models) {
		models.put("user", new User());
		models.put("UserRole", roleDao.getUserRoles());
		return "userRegistration";
	}

	@RequestMapping(value = "/userRegistration.html", method = RequestMethod.POST)
	@ResponseBody
	public String register(@ModelAttribute User user, BindingResult bindingResult, ModelMap models,
			HttpServletRequest request) {

		// for validation
		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			// models.put("user", new User());
			models.put("UserRole", roleDao.getUserRoles());
			System.out.println("validation done");
			return "userRegistration";
		}
		user.setUserRole(roleDao.getUserRole(Integer.parseInt(request.getParameter("role"))));
		user.setDelete(false);
		user.setCreateDate(new Date());
		userDao.saveUser(user);
		models.addAttribute("modalShow", "Saved");
		return "index";
	}

	@RequestMapping(value = "/admin/userRegistration.html", method = RequestMethod.GET)
	public String register1(ModelMap models) {
		models.put("user", new User());
		models.put("UserRole", roleDao.getUserRoles());
		return "admin/userRegistration";
	}

	@RequestMapping(value = "/admin/userRegistration.html", method = RequestMethod.POST)
	public String register1(@ModelAttribute User user, BindingResult bindingResult, ModelMap models,
			HttpServletRequest request) {

		// for validation
		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			models.put("user", new User());
			models.put("UserRole", roleDao.getUserRoles());
			return "admin/userRegistration";
		}
		user.setUserRole(roleDao.getUserRole(Integer.parseInt(request.getParameter("role"))));
		user.setDelete(false);
		user.setCreateDate(new Date());
		userDao.saveUser(user);
		models.addAttribute("modalShow", "Saved");
		return "redirect:userManagement.html";
	}

	@RequestMapping(value = "/manager/userRegistration.html", method = RequestMethod.GET)
	public String register2(ModelMap models) {
		models.put("user", new User());
		models.put("UserRole", roleDao.getUserRoles());
		return "manager/userRegistration";
	}

	@RequestMapping(value = "/manager/userRegistration.html", method = RequestMethod.POST)
	public String register2(@ModelAttribute User user, BindingResult bindingResult, ModelMap models,
			HttpServletRequest request) {

		// for validation
		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors())
			return "manager/userRegistration";

		user.setUserRole(roleDao.getUserRole(Integer.parseInt(request.getParameter("role"))));
		user.setDelete(false);
		user.setCreateDate(new Date());
		userDao.saveUser(user);
		models.addAttribute("modalShow", "Saved");
		return "redirect:userManagement.html";
	}

	@RequestMapping(value = "/admin/editUser.html", method = RequestMethod.GET)
	public String update(@RequestParam Integer id, ModelMap models) {
		models.put("user", userDao.getUser(id));
		models.put("UserRole", roleDao.getUserRoles());
		return "admin/editUser";
	}

	@RequestMapping(value = "/admin/editUser.html", method = RequestMethod.POST)
	public String update(@ModelAttribute User user, BindingResult bindingResult, ModelMap models,
			HttpServletRequest request, SessionStatus status) {

		user.setUserRole(roleDao.getUserRole(Integer.parseInt(request.getParameter("role"))));
		user.setDelete(false);
		user.setCreateDate(new Date());
		user = userDao.saveUser(user);
		status.setComplete();
		return "redirect:userManagement.html";
	}

	@RequestMapping("/admin/userManagement.html")
	public String projects(ModelMap models) {
		models.put("users", userDao.getUsers());

		return "admin/userManagement";
	}

	@RequestMapping(value = "/admin/disableuser.html")
	public String admindisableuser(@RequestParam Integer id) {

		User usertobedisabled = userDao.getUser(id);

		usertobedisabled.setDelete(true);

		usertobedisabled = userDao.saveUser(usertobedisabled);
		return "redirect:userManagement.html";
	}

	@RequestMapping(value = "/manager/disableuser.html")
	public String managerdisableuser(@RequestParam Integer id) {

		User usertobedisabled = userDao.getUser(id);

		usertobedisabled.setDelete(true);

		usertobedisabled = userDao.saveUser(usertobedisabled);
		return "redirect:userManagement.html";
	}

	@RequestMapping(value = "/checkuser.html", method = RequestMethod.GET)
	@ResponseBody
	public String getUserName(@RequestParam String Username, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		
		String uname = "false";
		User user = userDao.isUserAvailable(Username);
		if(user.getUsername() != null){
			uname = "true";
		}
		
		response.setContentType("application/json");
       objectMapper.writeValue(response.getWriter(), uname);

		return null ;
	}
	
	@RequestMapping(value = "/email.html", method = RequestMethod.GET)
	@ResponseBody
	public String getEmail(@RequestParam String email, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException 
	{
		
		String check = "false";
		User user = userDao.ajaxEmailExist(email);
		if(user.getEmailAddress() != null){
			check = "true";
		}
		
		response.setContentType("application/json");
       objectMapper.writeValue(response.getWriter(), check);

		return null ;
	}
	
	
	
}
