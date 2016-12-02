package spiderhub.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import spiderhub.model.User;
import spiderhub.model.dao.UserDao;

@Component
public class UserValidator implements Validator {

	// taken from stackoverflow
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern pattern;
	private Matcher matcher;

	@Autowired
	private UserDao userDao;

	// helper method
	private boolean validateEmail(String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);

		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		String email = user.getEmailAddress();
		if (!StringUtils.hasText(user.getUserName()))
			errors.rejectValue("userName", "error.field.empty");
		else if (StringUtils.hasText(user.getUserName())) {
			User u = userDao.getUserByUsername(user.getUserName());
			if (u != null) {
				errors.rejectValue("userName", "error.field.userName");
			}

		}
		if (!StringUtils.hasText(user.getPassword()))
			errors.rejectValue("password", "error.field.empty");

		if (!StringUtils.hasText(email))
			errors.rejectValue("emailAddress", "error.field.empty");
		else if (!validateEmail(email)) {
			// check using regex whether it is
			errors.rejectValue("emailAddress", "error.field.email");
		} else if (StringUtils.hasText(email)) {
			User u = userDao.checkEmailExist(email);
			if (u != null) {
				errors.rejectValue("emailAddress", "error.field.emailExist");
			}
		}

		if (!StringUtils.hasText(user.getPhoneNumber()))
			errors.rejectValue("phoneNumber", "error.field.empty");

	}

}
