package spiderhub.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import spiderhub.model.Project;
import spiderhub.model.Task;
import spiderhub.model.TaskActivity;
import spiderhub.model.User;
import spiderhub.model.dao.ProjectDao;
import spiderhub.model.dao.ProjectTypeDao;
import spiderhub.model.dao.TaskActivityDao;
import spiderhub.model.dao.TaskDao;
import spiderhub.model.dao.UserDao;
import spiderhub.web.validator.ProjectValidator;

@Controller
@SessionAttributes("project")
public class ProjectController {

	Set<User> users = new HashSet<>();

	@Autowired
	private TaskActivityDao taskActivityDao;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProjectTypeDao projecttypeDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	ProjectValidator projectValidator;

	@RequestMapping("/admin/listProjects.html")
	public String adminprojects(ModelMap models) {
		models.put("projects", projectDao.getProjects());
		return "admin/listProjects";
	}

	@RequestMapping("/manager/listProjects.html")
	public String managerprojects(ModelMap models) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User User = (User) auth.getPrincipal();
		models.put("projects", projectDao.getProjectofManager(User.getId()));
		return "manager/listProjects";
	}

	@RequestMapping("/manager/report.html")
	public String projectreport(ModelMap models, @RequestParam Integer id) {
		models.put("project", projectDao.getProject(id));
		models.put("tasks", projectDao.getProject(id).getTasks());

		/*
		 * to show weekly task related activity
		 */

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		System.out.println("Date = " + cal.getTime());

		List<Task> tasksWeekly = taskDao.getTasksWeeklyWithinProject(id, cal.getTime(), new Date());
		double[] totalHourArrayWeekly = new double[tasksWeekly.size()];
		int countWeekly = 0;
		double totalHourArraySumWeekly = 0;
		for (Task task : tasksWeekly) {
			List<TaskActivity> taskActivityListWeekly = taskActivityDao.getTaskActivityWeeklyByTaskInsideProject(id,
					task.getId(), cal.getTime(), new Date());
			double totalHourByTask = 0;
			for (TaskActivity activity : taskActivityListWeekly) {
				double hrs = 0;
				try {
					hrs = count_hr_from_start_end(activity.getStartTime(), activity.getEndTime());
				} catch (NullPointerException nu) {
					hrs = 0;
				}

				// hourList.add(hrs);
				totalHourByTask += hrs;
			}
			totalHourArrayWeekly[countWeekly++] = totalHourByTask;
			totalHourArraySumWeekly += totalHourByTask;

		}

		models.put("tasksWeekly", tasksWeekly);

		models.put("totalHourArrayWeekly", totalHourArrayWeekly);
		models.put("totalHourArraySumWeekly", totalHourArraySumWeekly);

		return "manager/report";
	}

	@RequestMapping("/member/listProjects.html")
	public String projects(ModelMap models) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User User = (User) auth.getPrincipal();
		int id = User.getId();
		models.put("projects", userDao.getUser(id).getProjects());
		return "member/listProjects";
	}

	@RequestMapping("/admin/viewProject.html")
	// optional required = false
	public String adminview(@RequestParam(required = false) Integer id, ModelMap models) {
		// get user from database and pass it to JSP
		models.put("project", projectDao.getProject(id));
		return "admin/viewProject";

	}

	@RequestMapping("/manager/viewProject.html")
	// optional required = false
	public String managerview(@RequestParam(required = false) Integer id, ModelMap models) {
		// get user from database and pass it to JSP
		models.put("project", projectDao.getProject(id));
		models.put("tasks", taskDao.getTaskByProject(id));
		if (projectDao.getProject(id).getUsersRelatedProject() != null)
			models.put("user", projectDao.getProject(id).getUsersRelatedProject());
		if (taskDao.getTotalNofTaskinProject(id) == 0) {
			models.put("progress", 0);
			System.out.println("---0");
		} else {
			models.put("progress",
					taskDao.getNoOfCompletedTaskinProject(id) * 100 / taskDao.getTotalNofTaskinProject(id));
			System.out.println(
					"++++" + taskDao.getNoOfCompletedTaskinProject(id) * 100 / taskDao.getTotalNofTaskinProject(id));
		}

		/*
		 * to show the activity of related project
		 */
		List<TaskActivity> taskActivityList = taskActivityDao.getTaskActivityByProject(id);
		models.put("activityModel", taskActivityList);

		// to calculate no of hrs for each activity
		List<Double> hourList = new ArrayList<Double>();
		double totalHour = 0;
		for (TaskActivity activity : taskActivityList) {

			double hrs = 0;
			try {
				hrs = count_hr_from_start_end(activity.getStartTime(), activity.getEndTime());
			} catch (NullPointerException nu) {

			}
			hourList.add(hrs);
			totalHour += hrs;
		}
		models.put("totalHour", totalHour);
		models.put("hours", hourList);

		/*
		 * to show the activity hrs for each task inside the Project
		 * 
		 */

		List<Task> taskList = taskDao.getTaskByProject(id);
		double[] totalHourArray = new double[taskList.size()];
		int count = 0;
		double totalHourArraySum = 0;
		for (Task task : taskList) {
			taskActivityList = taskActivityDao.getTaskActivityByTaskInsideProject(id, task.getId());
			double totalHourByTask = 0;
			for (TaskActivity activity : taskActivityList) {

				double hrs = 0;
				try {
					hrs = count_hr_from_start_end(activity.getStartTime(), activity.getEndTime());
				} catch (NullPointerException nu) {

				}
				// hourList.add(hrs);
				totalHourByTask += hrs;
			}
			totalHourArray[count++] = totalHourByTask;
			totalHourArraySum += totalHourByTask;

		}

		models.put("totalHourArray", totalHourArray);
		models.put("totalHourArraySum", totalHourArraySum);
		/*
		 * to show weekly task related activity
		 */

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		System.out.println("Date = " + cal.getTime());

		List<Task> tasksWeekly = taskDao.getTasksWeeklyWithinProject(id, cal.getTime(), new Date());
		double[] totalHourArrayWeekly = new double[tasksWeekly.size()];
		int countWeekly = 0;
		double totalHourArraySumWeekly = 0;
		for (Task task : tasksWeekly) {
			List<TaskActivity> taskActivityListWeekly = taskActivityDao.getTaskActivityWeeklyByTaskInsideProject(id,
					task.getId(), cal.getTime(), new Date());
			double totalHourByTask = 0;
			for (TaskActivity activity : taskActivityListWeekly) {
				double hrs = 0;
				try {
					hrs = count_hr_from_start_end(activity.getStartTime(), activity.getEndTime());
				} catch (NullPointerException n) {

				}

				hourList.add(hrs);
				totalHourByTask += hrs;
			}
			totalHourArrayWeekly[countWeekly++] = totalHourByTask;
			totalHourArraySumWeekly += totalHourByTask;

		}

		models.put("tasksWeekly", tasksWeekly);

		models.put("totalHourArrayWeekly", totalHourArrayWeekly);
		models.put("totalHourArraySumWeekly", totalHourArraySumWeekly);

		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.DATE, -7);
		System.out.println("Date = " + cal2.getTime());
		models.put("activityModelWeekly",
				taskActivityDao.getTaskActivityWeeklyByProject(id, cal2.getTime(), new Date()));

		return "manager/viewProject";

	}

	@RequestMapping("/member/viewProject.html")
	// optional required = false
	public String view(@RequestParam Integer id, ModelMap models) {
		// get user from database and pass it to JSP
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User User = (User) auth.getPrincipal();
		int uid = User.getId();
		System.out.println(uid);
		System.out.println(id);
		models.put("project", projectDao.getProject(id));
		models.put("task", taskDao.getTaskOfMemberByProject(uid, id));

		// for adding the task functionality
		System.out.println("no of ongoing task: " + taskDao.getNoOfOngoingTask(uid));
		models.put("ongoingTask", taskDao.getNoOfOngoingTask(uid));

		// no of completed task
		System.out.println("no of completed task: " + taskDao.getNoOfCompletedTask(uid));
		models.put("completedTask", taskDao.getNoOfCompletedTask(uid));
		// no of total task
		System.out.println("no of total task: " + taskDao.getTotalNofTask(uid));
		models.put("totalTask", taskDao.getTotalNofTask(uid));
		return "member/viewProject";

	}

	@RequestMapping(value = "/manager/addProject.html", method = RequestMethod.GET)
	public String manageradd(ModelMap models) {
		models.put("project", new Project());
		models.put("projecttype", projecttypeDao.getProjectType());
		return "manager/addProject";
	}

	@RequestMapping(value = "/manager/addProject.html", method = RequestMethod.POST)
	public String manageradd(@ModelAttribute Project project, BindingResult bindingResult, ModelMap models,
			HttpServletRequest request) {

		// for validation
		projectValidator.validate(project, bindingResult);
		if (bindingResult.hasErrors()) {
			// models.put("project", new Project());
			models.put("projecttype", projecttypeDao.getProjectType());
			return "manager/addProject";
		}
		project.setProjectType(projecttypeDao.getPerojectType(Integer.parseInt(request.getParameter("projecttype"))));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User User = (User) auth.getPrincipal();
		int userId = User.getId();
		project.setCreatedUser(userDao.getUser(userId));
		project.setCreatedDate(new Date());
		project = projectDao.saveProject(project);
		return "redirect:listProjects.html";
	}

	@RequestMapping(value = "/manager/editProject.html", method = RequestMethod.GET)
	public String edit(@RequestParam Integer id, ModelMap models) {
		models.put("project", projectDao.getProject(id));
		return "manager/editProject";
	}

	@RequestMapping(value = "/manager/editProject.html", method = RequestMethod.POST)
	public String edit(@ModelAttribute Project project, SessionStatus status) {
		project = projectDao.saveProject(project);
		status.setComplete();
		return "redirect:listProjects.html";
	}

	@RequestMapping(value = "/admin/disable.html")
	public String admindisable(@RequestParam Integer id) {
		Project deleteproject = projectDao.getProject(id);

		deleteproject.setDelete(true);
		deleteproject = projectDao.saveProject(deleteproject);

		return "redirect:listProjects.html";
	}

	@RequestMapping(value = "/manager/disable.html")
	public String managerdisable(@RequestParam Integer id) {
		Project deleteproject = projectDao.getProject(id);

		deleteproject.setDelete(true);
		deleteproject = projectDao.saveProject(deleteproject);

		return "redirect:listProjects.html";
	}

	@RequestMapping(value = "/manager/addUserInProject.html", method = RequestMethod.GET)
	public String addUser(@RequestParam Integer id, ModelMap models) {

		List<User> projectNotInProject = userDao.getUserToaddInProject();
		Project project = projectDao.getProject(id);
		Set<User> detail = project.getUsersRelatedProject();

		projectNotInProject.removeAll(detail);

		models.put("users", projectNotInProject);
		models.put("project", projectDao.getProject(id));

		return "manager/addUserInProject";
	}

	@RequestMapping(value = "/manager/addUserInProject.html", method = RequestMethod.POST)
	public String addUser(@ModelAttribute Project project, HttpServletRequest request, @RequestParam Integer id,
			SessionStatus sessionStatus, ModelMap models) {

		Set<User> detail = projectDao.getProject(id).getUsersRelatedProject();

		String[] chkSms = request.getParameterValues("chksms");
		int[] value = new int[chkSms.length];

		for (int i = 0; i < chkSms.length; i++)
			value[i] = Integer.parseInt(chkSms[i]);

		System.out.println("adding user to project" + chkSms);

		users.addAll(detail);

		for (int i = 0; i < value.length; i++) {
			users.add(userDao.getUser(value[i]));
		}

		project.setUsersRelatedProject(users);
		project = projectDao.saveProject(project);

		return "redirect:viewProject.html?id=" + id;

	}

	@RequestMapping(value = "/manager/remove.html")
	public String userRemove(@RequestParam Integer id, @RequestParam Integer pid) {
		Project project = projectDao.getProject(pid);
		Set<User> detail = project.getUsersRelatedProject();
		User removeUser = null;
		for (User u : detail) {
			if (u.getId() == id) {
				removeUser = u;
				break;
			}
		}
		detail.remove(removeUser);
		project.setUsersRelatedProject(detail);
		projectDao.saveProject(project);
		return "redirect:listProjects.html";
	}

	// helper methods
	private double count_hr_from_start_end(Date start, Date end) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		double hours = 0;
		try {
			String d1 = format.format(start);
			String d2 = format.format(end);
			long diff = (format.parse(d2).getTime() - format.parse(d1).getTime()) / 1000;
			hours = (diff / (double) 3600);

		} catch (Exception e)

		{
			hours = 0;
			e.printStackTrace();
		}
		return hours;
	}
}
