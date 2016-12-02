package spiderhub.web.controller;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import spiderhub.model.User;
import spiderhub.model.dao.TaskDao;

@Controller
public class IndexController {
	@Autowired
	TaskDao taskDao;

	@RequestMapping("/index.html")
	public String index(ModelMap models) {
		models.put("user", "janak");
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User User = (User) auth.getPrincipal();
			int id = User.getId();
			models.put("complete", taskDao.getNoOfCompletedTask(id));
			models.put("remaining", taskDao.getNoOfOngoingTask(id));
			System.out.println("remaining" + taskDao.getNoOfOngoingTask(id));
			models.put("totalTasks", taskDao.getTotalNofTask(id));
			models.put("high", taskDao.getAllTaskAccordingToHIGHPriorityWithinAProject(id));
			models.put("medium", taskDao.getAllTaskAccordingToMEDIUMPriorityWithinAProject(id));
			models.put("low", taskDao.getAllTaskAccordingToLOWPriorityWithinAProject(id));
			// Map <String, Long> list = new HashMap<String, Long>();
			// list.put("new Date(2015, 12, 1)", (long) 5);
			// list.put("new Date(2015, 12, 2)", (long) 7);
			// for (Map.Entry<String, Long> entry : list.entrySet()) {
			// String key = entry.getKey();
			// Long value = entry.getValue();
			// System.out.println(key +" "+value);
			//
			// }
			// models.put("list", list);
			/*
			 * 
			*/
			Calendar now = Calendar.getInstance();
			//
			String year = String.valueOf(now.get(Calendar.YEAR));
			String month = String.valueOf(now.get(Calendar.MONTH));
			Map<String, Long> oldMap = new HashMap<String, Long>();
			now.set(Calendar.HOUR, 11);
			now.set(Calendar.MINUTE, 59);
			now.set(Calendar.SECOND, 59);

			int currentDate = now.get(Calendar.DATE);
			for (int i = 1; i <= currentDate; i++) {
				now.set(Calendar.DATE, i);
				Date d = now.getTime();
				String day = "";
				if (i < 10) {
					day = "0" + String.valueOf(i);
					System.out.println("day lfkflkfr" +day);
				} else{
					day = String.valueOf(i);
					System.out.println("day lfkflkfr" +day);
	
				}
				System.out.println("day lfkflkfr" +day);
				
				String dateString = "new Date(" + year + ", " + month + ", " + day + ")";
				oldMap.put(dateString, taskDao.getCountOfOngoingTaskOfMemberByDate(id, d));
			}

			for (Map.Entry<String, Long> entry : oldMap.entrySet()) {
				String key = entry.getKey();
				Long value = entry.getValue();
				System.out.println(key + "      " + value);

			}
			SortedMap<String, Long> map = new TreeMap<String, Long>(oldMap);
			for (SortedMap.Entry<String, Long> entry : map.entrySet()) {
				String key = entry.getKey();
				Long value = entry.getValue();
				System.out.println(key + "      " + value);

			}
			models.put("list", map);

		} catch (Exception e) {
		}
		return "index";
	}

	@RequestMapping("/projects/index.html")
	public String index1(ModelMap models) {
		models.put("user", "janak");
		return "index";
	}

	@RequestMapping("/task/index.html")
	public String index2(ModelMap models) {
		models.put("user", "janak");
		return "index";
	}
}
