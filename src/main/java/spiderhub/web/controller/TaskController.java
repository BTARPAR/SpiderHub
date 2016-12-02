package spiderhub.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import spiderhub.model.Comment;
import spiderhub.model.Project;
import spiderhub.model.Task;
import spiderhub.model.TaskActivity;
import spiderhub.model.User;
import spiderhub.model.dao.CommentDao;
import spiderhub.model.dao.FileDao;
import spiderhub.model.dao.ProjectDao;
import spiderhub.model.dao.TaskActivityDao;
import spiderhub.model.dao.TaskDao;
import spiderhub.model.dao.TaskPriorityDao;
import spiderhub.model.dao.TaskStatusDao;
import spiderhub.model.dao.UserDao;

@Controller
public class TaskController {

	@Autowired
	private TaskActivityDao taskActivityDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private TaskPriorityDao taskpriorityDao;

	@Autowired
	private TaskStatusDao taskstatusDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private MailSender mailSender;

	@Autowired
	private CommentDao commentDao;

	/*
	 * Member variables for file upload
	 */
	@Autowired
	private ServletContext context;

	/*
	 * helper method for file upload to get the file path
	 */

	private File getFileDirectory() {
		String path = context.getRealPath("/WEB-INF/files");
		return new File(path);
	}

	@RequestMapping("/manager/listTasks.html")
	public String managerlist(ModelMap models) {
		// get all users from databases and pass them to JSP

		List<Task> tasks = taskDao.getTasks();
		models.put("tasks", tasks);
		return "manager/listTasks";
	}

	@RequestMapping("/manager/viewTask.html")
	// optional required = false
	public String managerview(@RequestParam(required = false) Integer id, ModelMap models) {
		// get user from database and pass it to JSP
		models.put("task", taskDao.getTask(id));
		return "manager/viewTask";

	}

	@RequestMapping(value = "/manager/addTask.html", method = RequestMethod.GET)
	public String add(ModelMap models) {
		models.put("task", new Task());
		return "manager/addTask";
	}

	@RequestMapping(value = "/manager/addTask.html", method = RequestMethod.POST)
	public String add(@ModelAttribute Task task, @RequestParam Integer id) {

		// save user to database
		task.setProjectTasks(projectDao.getProject(id));
		task.setCreateDate(new Date());
		task = taskDao.saveTask(task);
		// redirect to user list

		return "redirect:viewProject.html?id=" + id;
	}

	@RequestMapping(value = "/manager/assignTask.html", method = RequestMethod.GET)
	public String assign(@RequestParam Integer tid, @RequestParam Integer pid, ModelMap models) {
		models.put("task", taskDao.getTask(tid));
		Project temp = projectDao.getProject(pid);
		models.put("users", temp.getUsersRelatedProject());
		models.put("priority", taskpriorityDao.getTaskPriority());
		// no files to show first time
		return "manager/assignTask";
	}

	@RequestMapping(value = "/manager/assignTask.html", method = RequestMethod.POST)
	public String assign(@RequestParam Integer tid, @RequestParam Integer pid, @ModelAttribute Task task,
			BindingResult bindingResult, HttpServletRequest request, SessionStatus status, ModelMap models)
			throws ParseException {
		task = taskDao.getTask(tid);

		String endDate = request.getParameter("endDate");
		String endTime = request.getParameter("endTime");
		endDate = endDate + " " + endTime;
		System.out.println("endDate: " + endDate + " endTime : " + endTime);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date formattedDate = null;
		formattedDate = dateFormat.parse(endDate);

		task.setEndDate(formattedDate);
		task.setProjectTasks(projectDao.getProject(pid));
		task.setUserTasks(userDao.getUser(Integer.parseInt(request.getParameter("user"))));
		task.setTaskPriority(taskpriorityDao.getTaskpriority(Integer.parseInt(request.getParameter("priority"))));
		task.setStatusTasks(taskstatusDao.getTaskStatus(1));

		Date createDate = task.getCreateDate();

		task.setCreateDate(createDate);
		task.setStartDate(new Date());
		task = taskDao.saveTask(task);
		status.setComplete();
		SimpleMailMessage message = new SimpleMailMessage();
		User user = userDao.getUser(Integer.parseInt(request.getParameter("user")));
		message.setTo(user.getEmailAddress());
		message.setFrom("testspiderhub@gmail.com");
		message.setText("Dear" + user.getUserName() + ", You have New Task.");
		try {
			this.mailSender.send(message);
		} catch (MailException ex) {
			// simply log it and go on...
			System.err.println(ex.getMessage());
		}
		// redirect to user list
		return "redirect:viewProject.html?id=" + pid;

	}

	// file upload here
	@RequestMapping(value = "/manager/uploadFileToAssigned.html", method = { RequestMethod.GET, RequestMethod.POST })
	public String fileUpload(@RequestParam Integer tid, @RequestParam Integer pid, @ModelAttribute Task task,
			BindingResult bindingResult, HttpServletRequest request, SessionStatus status, ModelMap models,
			@RequestParam(required = false) MultipartFile file) throws IllegalStateException, IOException {
		System.out.println("***************Inside if");

		if ("POST".equals(request.getMethod())) {
			// handle upload
			// System.out.println("***************Inside if");
			models.put("task", taskDao.getTask(tid));
			Project temp = projectDao.getProject(pid);
			models.put("users", temp.getUsersRelatedProject());
			models.put("priority", taskpriorityDao.getTaskPriority());

			// for display of files
			models.put("fileModel", fileDao.getFilesAssignedToTask(tid));

			// models.put("users", userDao.getUsertoAddInProject());

			String fileName = "";
			String filePath = "";
			String fileType = "";
			String fileSize = "";
			// save in web-inf/files
			if (file.getOriginalFilename() != null) {
				String[] fileNameSplit = file.getOriginalFilename().split("[.]");
				fileName = fileNameSplit[0];
				fileType = fileNameSplit[1];
				// for testing fileType is fileName
				fileSize = String.valueOf(file.getSize());
				filePath = context.getRealPath("/WEB-INF/files");
				file.transferTo(new File(getFileDirectory(), file.getOriginalFilename()));
			}

			// to save into database
			spiderhub.model.File fileModel = new spiderhub.model.File();
			fileModel.setDelete(false);
			fileModel.setFileName(fileName);
			fileModel.setFilePath(filePath);
			fileModel.setFileSize(fileSize);
			fileModel.setFileType(fileType);
			fileModel.setUploadDate(new Date());
			fileModel.setTaskFiles(taskDao.getTask(tid));
			fileDao.saveFile(fileModel);
		}

		models.put("task", taskDao.getTask(tid));
		// for display of files
		models.put("fileModel", fileDao.getFilesAssignedToTask(tid));

		return "manager/uploadFileToAssigned";

	}

	@RequestMapping(value = "/member/doneTask.html")
	public String managerdisable(@RequestParam Integer pid, @RequestParam Integer tid) {

		Task changestatusoftask = taskDao.getTask(tid);

		changestatusoftask.setStatusTasks(taskstatusDao.getTaskStatus(2));

		changestatusoftask = taskDao.saveTask(changestatusoftask);
		return "redirect:viewProject.html?id=" + pid;
	}

	@RequestMapping(value = "/member/viewTask.html", method = { RequestMethod.GET, RequestMethod.POST })
	// optional required = false
	public String memberView(@RequestParam(required = false) Integer tid, @ModelAttribute TaskActivity taskActivity,
			@RequestParam(required = false, value = "action") String action, @ModelAttribute Comment comment,
			ModelMap models, HttpServletRequest request, HttpServletResponse response) {

		// for display of activiies
		models.put("activityModel", taskActivityDao.getTaskActivityFromRelatedTask(tid));

		if ("GET".equals(request.getMethod())) {
			// get user from database and pass it to JSP
			models.put("task", taskDao.getTask(tid));
			// for display of files
			models.put("fileModel", fileDao.getFilesAssignedToTask(tid));
			models.put("comments", commentDao.getComment(tid));
			models.put("comment", new Comment());
		} else if ("POST".equals(request.getMethod())) {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User User = (User) auth.getPrincipal();
			int uid = User.getId();

			if (action != null && action.equals("Comment")) {
				comment.setTaskComments(taskDao.getTask(tid));
				comment.setUserComment(userDao.getUser(uid));
				comment.setCreateDate(new Date());
				comment.setDelete(false);
				commentDao.saveComment(comment);
				SimpleMailMessage message = new SimpleMailMessage();
				Project proj = taskDao.getTask(tid).getProjectTasks();
				User user = proj.getCreatedUser();
				message.setTo(user.getEmailAddress());
				message.setFrom("testspiderhub@gmail.com");
				message.setText("Dear " + user.getUserName() + ", You have new Comment in task "
						+ taskDao.getTask(tid).getTaskName() + ".");
				try {
					this.mailSender.send(message);
				} catch (MailException ex) {
					// simply log it and go on...
					System.err.println(ex.getMessage());
				}

			}

			else if (action != null && action.equals("Start Working")) {
				taskActivity = new TaskActivity();
				taskActivity.setStartTime(new Date());
				taskActivity.setComplete(false);
				taskActivity.setActivityOfTask(taskDao.getTask(tid));

				taskActivity.setActivityOfTaskByUser(userDao.getUser(uid));
				taskActivity = taskActivityDao.saveTaskActivity(taskActivity);
				models.put("activityId", taskActivity.getId());
			} else if (action != null && action.equals("Take a Break")) {

				String activityId = request.getParameter("activityId");
				int id = 0;
				if (activityId != null && !activityId.isEmpty()) {
					id = Integer.parseInt((request.getParameter("activityId")));
				}
				taskActivity = taskActivityDao.getTaskActivity(id);
				taskActivity.setEndTime(new Date());
				taskActivity = taskActivityDao.saveTaskActivity(taskActivity);

			} else if (action != null && action.equals("Finish Task")) {
				
			}
			models.put("task", taskDao.getTask(tid));
			// for display of files
			models.put("fileModel", fileDao.getFilesAssignedToTask(tid));
			models.put("comments", commentDao.getComment(tid));

		}
		return "member/viewTask";

	}

	@RequestMapping(value = "/manager/viewTask.html", method = { RequestMethod.GET, RequestMethod.POST })
	// optional required = false
	public String managerView(@RequestParam(required = false) Integer tid, @ModelAttribute Comment comment,
			@RequestParam(required = false, value = "action") String action, ModelMap models,
			HttpServletRequest request, HttpServletResponse response) {
		if ("GET".equals(request.getMethod())) {

			// for display of activiies
			models.put("activityModel", taskActivityDao.getTaskActivityFromRelatedTask(tid));

			// get user from database and pass it to JSP
			models.put("task", taskDao.getTask(tid));
			
			// for display of files
			models.put("fileModel", fileDao.getFilesAssignedToTask(tid));
			models.put("comments", commentDao.getComment(tid));
			models.put("comment", new Comment());
			
		} else if ("POST".equals(request.getMethod())) {

			comment.setTaskComments(taskDao.getTask(tid));
			/*
			 * to get uid
			 */
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User User = (User) auth.getPrincipal();
			int uid = User.getId();

			comment.setUserComment(userDao.getUser(uid));
			comment.setCreateDate(new Date());
			comment.setDelete(false);
			commentDao.saveComment(comment);

			if (action != null && action.equals("Comment")) {
				SimpleMailMessage message = new SimpleMailMessage();
				User user = taskDao.getTask(tid).getUserTasks();
				message.setTo(user.getEmailAddress());
				message.setFrom("testspiderhub@gmail.com");
				message.setText("Dear " + user.getUserName() + ", You have new Comment in task "
						+ taskDao.getTask(tid).getTaskName() + ".");
				try {
					this.mailSender.send(message);
				} catch (MailException ex) {
					// simply log it and go on...
					System.err.println(ex.getMessage());
				}

			}
			models.put("task", taskDao.getTask(tid));
			// for display of files
			models.put("fileModel", fileDao.getFilesAssignedToTask(tid));
			models.put("comments", commentDao.getComment(tid));

		}
		return "manager/viewTask";

	}

	@RequestMapping("/member/download.html")
	public String download(@RequestParam String fileNameWithType, @RequestParam Integer fileId,
			HttpServletResponse response) throws IOException {

		// read in the file
		String fullPath = "/" + fileNameWithType;
		response.reset();
		response.setHeader("Content-Length", String.valueOf(fileDao.getFile(fileId).getFileSize()));
		response.setContentType(new MimetypesFileTypeMap().getContentType(fileNameWithType));
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileNameWithType + "\"");

		FileInputStream in = new FileInputStream(new File(getFileDirectory(), fullPath));
		OutputStream out = response.getOutputStream();
		// write it to response
		System.out.println("fileDownload--------*********");
		byte[] buffer = new byte[2048];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) > 0) {
			out.write(buffer, 0, bytesRead);
		}
		in.close();

		return null;
	}

	// start Activity
	@RequestMapping(value = "/member/viewActivity.html", method = { RequestMethod.GET, RequestMethod.POST })
	// optional required = false
	public String viewActivity(@RequestParam(required = false) Integer tid, @ModelAttribute TaskActivity taskActivity,
			@RequestParam(required = false, value = "action") String action, ModelMap models,
			HttpServletRequest request, HttpServletResponse response) {
		if ("GET".equals(request.getMethod())) {

			// for display of activiies
			models.put("activityModel", taskActivityDao.getTaskActivityFromRelatedTask(tid));

		} else if ("POST".equals(request.getMethod())) {

			if (action != null && action.equals("start")) {
				taskActivity = new TaskActivity();
				taskActivity.setStartTime(new Date());
				taskActivity.setComplete(false);
				taskActivity.setActivityOfTask(taskDao.getTask(tid));
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				User User = (User) auth.getPrincipal();
				int uid = User.getId();
				taskActivity.setActivityOfTaskByUser(userDao.getUser(uid));
				taskActivity = taskActivityDao.saveTaskActivity(taskActivity);
				models.put("activityId", taskActivity.getId());
			} else if (action != null && action.equals("stop")) {
				String activityId = request.getParameter("activityId");
				int id = 0;
				if (activityId != null && !activityId.isEmpty()) {
					id = Integer.parseInt((request.getParameter("activityId")));
				}
				taskActivity = taskActivityDao.getTaskActivity(id);
				taskActivity.setEndTime(new Date());
				taskActivity = taskActivityDao.saveTaskActivity(taskActivity);
			}

		}
		models.put("activityModel", taskActivityDao.getTaskActivityFromRelatedTask(tid));

		return "member/viewActivity";
	}
}
