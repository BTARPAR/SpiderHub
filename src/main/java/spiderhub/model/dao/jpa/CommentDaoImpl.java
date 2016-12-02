package spiderhub.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spiderhub.model.Comment;
import spiderhub.model.dao.CommentDao;

@Repository
public class CommentDaoImpl implements CommentDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Comment saveComment(Comment comment) {
		return entityManager.merge(comment);
	}

	@Override
	public List<Comment> getComment(Integer tid) {
		String query = "from Comment where taskComments.id = :tid";
		return entityManager.createQuery(query, Comment.class).setParameter("tid", tid).getResultList();
	}
}