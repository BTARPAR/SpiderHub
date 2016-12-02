package spiderhub.model.dao;

import java.util.List;

import spiderhub.model.Project;

public interface ProjectDao {
	Project getProject(Integer id);

	List<Project> getProjects();

	Project saveProject(Project project);

	List<Project> getProjectofManager(Integer id);

	Project checkLinkExist(String link);

}
