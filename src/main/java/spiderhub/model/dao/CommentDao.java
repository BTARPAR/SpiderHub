package spiderhub.model.dao;

import java.util.List;

import spiderhub.model.Comment;

public interface CommentDao {

	Comment saveComment(Comment comment);

	List<Comment> getComment(Integer tid);

}
