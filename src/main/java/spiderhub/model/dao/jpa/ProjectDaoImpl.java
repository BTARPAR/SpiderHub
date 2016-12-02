package spiderhub.model.dao.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spiderhub.model.Project;
import spiderhub.model.dao.ProjectDao;

@Repository
public class ProjectDaoImpl implements ProjectDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Project getProject(Integer id) {
		return entityManager.find(Project.class, id);
	}

	@Override
	public List<Project> getProjects() {
		return entityManager.createQuery("from Project order by id", Project.class).getResultList();
	}

	@Override
	@Transactional
	public Project saveProject(Project project) {
		return entityManager.merge(project);
	}

	@Override
	public List<Project> getProjectofManager(Integer id) {
		String query = "from Project where createdUser.id = :id";
		return entityManager.createQuery(query, Project.class).setParameter("id", id).getResultList();
	}

	@Override
	public Project checkLinkExist(String projectGitHubLink) {
		List<Project> results = entityManager
				.createQuery("from Project where LOWER(projectGitHubLink) = LOWER(:projectGitHubLink)", Project.class)
				.setParameter("projectGitHubLink", projectGitHubLink).getResultList();

		return results.size() == 0 ? null : results.get(0);
	}

}
