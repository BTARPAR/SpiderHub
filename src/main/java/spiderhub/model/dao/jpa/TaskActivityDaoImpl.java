package spiderhub.model.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spiderhub.model.TaskActivity;
import spiderhub.model.dao.TaskActivityDao;

@Repository
public class TaskActivityDaoImpl implements TaskActivityDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public TaskActivity getTaskActivity(Integer id) {
		String query = "from TaskActivity where id=:id";
		return entityManager.createQuery(query, TaskActivity.class).setParameter("id", id).getSingleResult();
	}

	@Override
	@Transactional
	public TaskActivity saveTaskActivity(TaskActivity taskActivity) {
		return entityManager.merge(taskActivity);

	}

	@Override
	public List<TaskActivity> getTaskActivityFromRelatedTask(Integer tid) {
		String query = "from TaskActivity where activityOfTask.id = :tId";
		return entityManager.createQuery(query, TaskActivity.class).setParameter("tId", tid).getResultList();
	}

	@Override
	public List<TaskActivity> getTaskActivityWeeklyByTask(Integer tid, Date start, Date end) {

		return entityManager
				.createQuery(
						"FROM TaskActivity AS t WHERE t.endTime BETWEEN :start AND :end AND t.activityOfTask.id = :tid ",
						TaskActivity.class)
				.setParameter("start", start).setParameter("end", end).setParameter("tid", tid).getResultList();
	}

	@Override
	public List<TaskActivity> getTaskActivityWeeklyByProject(Integer pid, Date start, Date end) {
		// TODO Auto-generated method stub
		return entityManager
				.createQuery(
						"FROM TaskActivity AS t WHERE t.endTime BETWEEN :start AND :end AND t.activityOfTask.projectTasks.id = :pid ",
						TaskActivity.class)
				.setParameter("start", start).setParameter("end", end).setParameter("pid", pid).getResultList();
	}

	@Override
	public List<TaskActivity> getTaskActivityWeeklyByUser(Integer uid, Date start, Date end) {
		// TODO Auto-generated method stub
		return entityManager
				.createQuery(
						"FROM TaskActivity AS t WHERE t.endTime BETWEEN :start AND :end AND t.activityOfTaskByUserr.id = :uid ",
						TaskActivity.class)
				.setParameter("start", start).setParameter("end", end).setParameter("uid", uid).getResultList();
	}

	@Override
	public List<TaskActivity> getTaskActivityByProject(Integer pid) {
		return entityManager.createQuery("FROM TaskActivity AS t WHERE t.activityOfTask.projectTasks.id = :pid ",
				TaskActivity.class).setParameter("pid", pid).getResultList();
	}

	@Override
	public List<TaskActivity> getTaskActivityByTaskInsideProject(Integer pid, Integer tid) {
		return entityManager.createQuery(
				"FROM TaskActivity AS t WHERE t.activityOfTask.projectTasks.id = :pid AND t.activityOfTask.id=:tid ",
				TaskActivity.class).setParameter("pid", pid).setParameter("tid", tid).getResultList();
	}

	@Override
	public List<TaskActivity> getTaskActivityWeeklyByTaskInsideProject(Integer pid, Integer tid, Date start, Date end) {
		return entityManager
				.createQuery(
						"FROM TaskActivity AS t WHERE t.endTime BETWEEN :start AND :end AND t.activityOfTask.id = :tid AND t.activityOfTask.projectTasks.id = :pid AND t.activityOfTask.id=:tid",
						TaskActivity.class)
				.setParameter("start", start).setParameter("end", end).setParameter("tid", tid).setParameter("pid", pid)
				.getResultList();
	}

}
