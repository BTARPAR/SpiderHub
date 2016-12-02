package spiderhub.model.dao;

import java.util.Date;
import java.util.List;

import spiderhub.model.Task;

public interface TaskDao {
	Task getTask(Integer id);

	List<Task> getTasks();

	Task saveTask(Task task);

	List<Task> getTaskByProject(Integer id);

	List<Task> getTaskOfMemberByProject(Integer uId, Integer pId);
	
	long getCountOfOngoingTaskOfMemberByDate(Integer uId, Date startDate );


	// to get tasks inside a project within past task
	List<Task> getTasksWeeklyWithinProject(Integer pid, Date start, Date end);

	List<Task> getAllTaskAccordingToHIGHPriorityWithinAProject(Integer uid);

	List<Task> getAllTaskAccordingToMEDIUMPriorityWithinAProject(Integer uid);
	List<Task> getAllTaskAccordingToLOWPriorityWithinAProject(Integer uid);

	
	// to get no of ongoing task
	long getNoOfOngoingTask(Integer uId);

	// to get No Of Completed Task

	long getNoOfCompletedTask(Integer uId);

	// To get total no of task

	long getTotalNofTask(Integer uId);

	long getNoOfOngoingTaskinProject(Integer pId);

	long getNoOfCompletedTaskinProject(Integer pId);

	long getTotalNofTaskinProject(Integer pId);
}
