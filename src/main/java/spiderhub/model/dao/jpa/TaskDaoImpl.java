package spiderhub.model.dao.jpa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spiderhub.model.Task;
import spiderhub.model.dao.TaskDao;

@Repository
public class TaskDaoImpl implements TaskDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Task getTask(Integer id) {
		return entityManager.find(Task.class, id);
	}

	@Override
	public List<Task> getTasks() {
		return entityManager.createQuery("from Task order by id", Task.class).getResultList();
	}

	@Override
	@Transactional
	public Task saveTask(Task task) {
		return entityManager.merge(task);
	}

	@Override
	public List<Task> getTaskByProject(Integer id) {
		String query = "from Task where projectTasks.id = :id";
		return entityManager.createQuery(query, Task.class).setParameter("id", id).getResultList();
	}

	@Override
	public List<Task> getTaskOfMemberByProject(Integer uId, Integer pId) {
		String query = "from Task where projectTasks.id = :pId and userTasks.id = :uId";
		return entityManager.createQuery(query, Task.class).setParameter("pId", pId).setParameter("uId", uId)
				.getResultList();
	}

	@Override
	public long getNoOfOngoingTask(Integer uId) {
		// TODO Auto-generated method stub
		long count = (long) entityManager
				.createQuery("select count(*) from Task where userTasks.id = :uId and statusTasks.id=1")
				.setParameter("uId", uId).getSingleResult();
		return count;
	}

	@Override
	public long getNoOfCompletedTask(Integer uId) {
		// TODO Auto-generated method stub
		long count = (long) entityManager
				.createQuery("select count(*) from Task where userTasks.id = :uId and statusTasks.id=2")
				.setParameter("uId", uId).getSingleResult();
		return count;
	}

	@Override
	public long getTotalNofTask(Integer uId) {
		// TODO Auto-generated method stub
		long count = (long) entityManager.createQuery("select count(*) from Task where userTasks.id = :uId")
				.setParameter("uId", uId).getSingleResult();
		return count;
	}

	@Override
	public long getNoOfOngoingTaskinProject(Integer pId) {
		long count = (long) entityManager
				.createQuery("select count(*) from Task where projectTasks.id = :pId and statusTasks.id=1")
				.setParameter("pId", pId).getSingleResult();
		return count;
	}

	@Override
	public long getNoOfCompletedTaskinProject(Integer pId) {
		long count = (long) entityManager
				.createQuery("select count(*) from Task where projectTasks.id = :pId and statusTasks.id=2")
				.setParameter("pId", pId).getSingleResult();
		return count;
	}

	@Override
	public long getTotalNofTaskinProject(Integer pId) {
		long count = (long) entityManager.createQuery("select count(*) from Task where projectTasks.id = :pId")
				.setParameter("pId", pId).getSingleResult();
		return count;
	}

	@Override
	public List<Task> getTasksWeeklyWithinProject(Integer pid, Date start, Date end) {
		String query = "from Task AS t where t.startDate BETWEEN :start AND :end AND t.projectTasks.id = :pid";
		return entityManager.createQuery(query, Task.class).setParameter("start", start).setParameter("end", end)
				.setParameter("pid", pid).getResultList();
	}

	@Override
	public List<Task> getAllTaskAccordingToHIGHPriorityWithinAProject(Integer uid) {
		String query = "from Task where userTasks.id = :uid and statusTasks.id=1 AND taskPriority.id=1";
		return entityManager.createQuery(query, Task.class).setParameter("uid", uid).getResultList();
	}

	@Override
	public List<Task> getAllTaskAccordingToMEDIUMPriorityWithinAProject(Integer uid) {
		String query = "from Task where userTasks.id = :uid and statusTasks.id=1 AND taskPriority.id=2";
		return entityManager.createQuery(query, Task.class).setParameter("uid", uid).getResultList();
	}

	@Override
	public List<Task> getAllTaskAccordingToLOWPriorityWithinAProject(Integer uid) {
		String query = "from Task where userTasks.id = :uid and statusTasks.id=1 AND taskPriority.id=3";
		return entityManager.createQuery(query, Task.class).setParameter("uid", uid).getResultList();
	}

	@Override
	public long getCountOfOngoingTaskOfMemberByDate(Integer uId, Date startDate) {
		// TODO Auto-generated method stub

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		long count = (long) entityManager.createQuery(
				"select count(*) from Task where userTasks.id = :uId and statusTasks.id=1 AND startDate <= '"
						+ sdf.format(startDate) + "'")
				.setParameter("uId", uId).getSingleResult();
		return count;

		/*
		 * long count = (long) entityManager .createQuery(
		 * "select count(*) from Task where userTasks.id = :uId and statusTasks.id=1 AND startDate <= CURRENT_DATE()"
		 * ) .setParameter("uId", uId).getSingleResult(); return count;
		 */
	}

}
