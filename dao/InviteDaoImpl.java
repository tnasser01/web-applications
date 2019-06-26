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

import com.yanya.springmvc.model.FriendInvite;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.InviteDao;

 
@Repository("inviteDao")
public class InviteDaoImpl extends AbstractDao<String, FriendInvite> implements InviteDao {

    @SuppressWarnings("unchecked")
    public FriendInvite saveNewInvite(FriendInvite invite) {
    	String id = (String)save(invite);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM FriendInvite c where c.friendInviteId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (FriendInvite)query.uniqueResult();
   
    }
	
    @SuppressWarnings("unchecked")
    public FriendInvite findByInviteCode(String id){   	
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("inviteCode", id));
        return (FriendInvite) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public void updateInviteStatus(String friendInviteId, String status){
    	String hql = "update FriendInvite a set a.status=:newStatus where a.friendInviteId=:friendInviteId";
    	Query query = getSession().createQuery(hql);
    	query.setParameter("friendInviteId", friendInviteId);
    	query.setParameter("newStatus", status);
    	query.executeUpdate();
    }
    

    
}