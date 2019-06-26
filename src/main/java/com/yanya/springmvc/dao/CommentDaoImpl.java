package com.yanya.springmvc.dao;
 
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;

import com.yanya.springmvc.model.Comment;
import com.yanya.springmvc.dao.CommentDao;

 
@Repository("commentDao")
public class CommentDaoImpl extends AbstractDao<String, Comment> implements CommentDao {

    @SuppressWarnings("unchecked")
    public Comment saveNewComment(Comment comment) {
    	String id = (String)save(comment);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM Comment c where c.commentId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Comment)query.uniqueResult();
   
    }
	
    @SuppressWarnings("unchecked")
    public List<Comment> findCommentsByUserPhotoId(String userPhotoId) {
    	System.out.println("The inputted userPhotoId to search for is:" + userPhotoId);
    	String hql = "FROM Comment c where c.userPhotoId = '" + userPhotoId + "'";
    	Query query = getSession().createQuery(hql);
    	List<Comment> comments = (List<Comment>)query.list();
    	if(comments==null){
    		comments = new ArrayList<Comment>();
    	}
    	return comments;
    
    }
    
    @SuppressWarnings("unchecked")
    public void deleteCommentsByUserPhotoId(String userPhotoId){
    	System.out.println("The inputted userPhotoId to search for is:" + userPhotoId);
    	String hql = "Delete FROM Comment c where c.userPhoto.userPhotoId = '" + userPhotoId + "'";
    	Query query = getSession().createQuery(hql);
    	query.executeUpdate();
    
    }

}