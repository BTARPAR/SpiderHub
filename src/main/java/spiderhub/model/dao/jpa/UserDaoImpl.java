package spiderhub.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spiderhub.model.User;
import spiderhub.model.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public User saveUser(User user) {
		return entityManager.merge(user);
	}

	@Override
	public User getUserByUsername(String userName) {
		String query = "from User u left join fetch u.userRole "
				+ "where lower(userName) = :userName and u.isDelete= 'false' ";

		// List<User> users = entityManager.createQuery(query,
		// User.class).setParameter("emailAddress", emailAddress.toLowerCase())
		// .getResultList();
		List<User> users = entityManager.createQuery(query, User.class).setParameter("userName", userName.toLowerCase())
				.getResultList();

		return users.size() == 0 ? null : users.get(0);
	}

	@Override
	public User getUser(Integer id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public List<User> getUsers() {

		return entityManager.createQuery("from User order by id", User.class).getResultList();
	}

	@Override
	public List<User> getUserToaddInProject() {
		String query = "from User where userRole.userRole = 'ROLE_MEMBER'";
		return entityManager.createQuery(query, User.class).getResultList();
	}


	@Override
	public List<User> getUsrToAssignTask(Integer id) {

		// String query = "from User u where u.projects.id=:id";
		String query = "from Project where id = :id";
		return entityManager.createQuery(query, User.class).setParameter("id", id).getResultList();
	}

	@Override
	public User checkEmailExist(String emailAddress) {
		List<User> results = entityManager
				.createQuery("from User where LOWER(emailAddress) = LOWER(:emailAddress)", User.class)
				.setParameter("emailAddress", emailAddress).getResultList();

		return results.size() == 0 ? null : results.get(0);
	}

	@Override
	public boolean isUserAvailable(String userName) {
		String query = "from User where userName = :userName";
		User user=(User) entityManager.createQuery(query, User.class)
				.setParameter("userName", userName).getResultList();
		return user == null ? false : true;
	}

}
