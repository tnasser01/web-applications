package com.yanya.springmvc.dao;
 
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.ImportResource;

import com.yanya.springmvc.model.Note;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.NoteDao;

 
@Repository("noteDao")
public class NoteDaoImpl extends AbstractDao<String, Note> implements NoteDao {

    @SuppressWarnings("unchecked")
    public Note saveNewNote(Note note) {
    	String id = (String)save(note);
   
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM Note p where p.entryId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Note)query.uniqueResult();
   
    }
	
    
    @SuppressWarnings("unchecked")
    public List<Note> findAllNotes() {
        Criteria criteria = createEntityCriteria();
    	List<Note> notes = (List<Note>)criteria.addOrder(Order.desc("entryTime")).list();
    	if(notes==null){
    		notes = new ArrayList<Note>();
    	}
    	return notes;  
  	}    
    
    @SuppressWarnings("unchecked")
    public Note findNoteByEntryId(String entryId){
    	Query query = getSession().createQuery("FROM Note p where p.entryId = '" + entryId + "'");
    	return (Note)query.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public List<Note> findNotesByCustomerId(String userId){
    	Query query = getSession().createQuery("FROM Note p where p.userId = '" + userId + "'");
    	List<Note> notes = (List<Note>)query.list();
    	if(notes==null){
    		notes = new ArrayList<Note>();
    	}
    	return notes;  
  	}
    
    @SuppressWarnings("unchecked")
    public List<Note> findNotesByMerchantId(String userId){
    	Query query = getSession().createQuery("FROM Note p where p.userId = '" + userId + "'");
    	List<Note> notes = (List<Note>)query.list();
    	if(notes==null){
    		notes = new ArrayList<Note>();
    	}
    	return notes;  
  	}
    
  	@SuppressWarnings("unchecked")
	public List<Note> findNotesByCustomerName(String customerName){
    	Query query = getSession().createQuery("FROM Note p where p.customerName = '" + customerName + "'");
    	List<Note> notes = (List<Note>)query.list();
    	if(notes==null){
    		notes = new ArrayList<Note>();
    	}
    	return notes;  
  	}
    
    
}