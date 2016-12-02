package spiderhub.model.dao;

import java.util.Date;
import java.util.List;

import spiderhub.model.TaskActivity;

public interface TaskActivityDao {
	TaskActivity getTaskActivity(Integer id);

	TaskActivity saveTaskActivity(TaskActivity taskActivity);

	List<TaskActivity> getTaskActivityFromRelatedTask(Integer tid);

	List<TaskActivity> getTaskActivityByProject(Integer pid);

	List<TaskActivity> getTaskActivityByTaskInsideProject(Integer pid, Integer tid);

	// weekly

	List<TaskActivity> getTaskActivityWeeklyByTaskInsideProject(Integer pid, Integer tid, Date start, Date end);

	List<TaskActivity> getTaskActivityWeeklyByTask(Integer tid, Date start, Date end);

	List<TaskActivity> getTaskActivityWeeklyByProject(Integer pid, Date start, Date end);

	List<TaskActivity> getTaskActivityWeeklyByUser(Integer uid, Date start, Date end);

}
