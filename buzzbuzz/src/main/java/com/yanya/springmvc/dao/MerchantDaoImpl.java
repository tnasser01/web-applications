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

import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.MerchantDao;
 
@Repository("merchantDao")
public class MerchantDaoImpl extends AbstractDao<String, Merchant> implements MerchantDao {
    
    @SuppressWarnings("unchecked")
    public Merchant saveMerchant(Merchant merchant) {
        String id = (String)save(merchant);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM Merchant m where m.userId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Merchant)query.uniqueResult();       
    }
   
    @SuppressWarnings("unchecked")
    public List<Merchant> findAllMerchants() {  
        Query query = getSession().createQuery("select distinct merchant from Merchant as merchant");
    	List<Merchant> merchants = (List<Merchant>)query.list();
    	if(merchants==null){
    		merchants = new ArrayList<Merchant>();
    	}
    	return merchants;  
    }
    
    @SuppressWarnings("unchecked")
	public void updateMerchantLocation(String userId, String location ,String lat, String lng){
    	String hql ="Update Merchant c set c.location =:location, c.lat =:lat, c.lng =:lng where c.userId =:userId";
    	Query query = getSession().createQuery(hql);
    	query.setParameter("userId", userId);
    	query.setParameter("location", location);
    	query.setParameter("lat", lat);
    	query.setParameter("lng", lng);
    	query.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")               
    public Merchant findByPhone(String phone) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("phone", phone));
        return (Merchant) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public Merchant findByStoreName(String storeName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("storeName", storeName));
        return (Merchant) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public Merchant findByMerchantId(String userId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("userId", userId));
        return (Merchant) criteria.uniqueResult();
    }
    
    public Merchant findByUsername(String username) {
        Criteria criteria = getSession().createCriteria(Merchant.class);
        criteria.add(Restrictions.eq("username", username));
        return (Merchant)criteria.uniqueResult();
    }

    public Merchant updateMerchantVisibility(String userId, String newVisibility){
        String hql = "update Merchant m set m.visibility=:visibility where m.userId=:userId ";
        Query query = getSession().createQuery(hql);
        System.out.println("userId is: " + userId);
        System.out.println("new visibility is: " + newVisibility);
        query.setParameter("visibility", newVisibility);
        query.setParameter("userId", userId);
        query.executeUpdate();
        
    	String hql2 = "FROM Merchant m where m.userId = '" + userId + "'";
    	Query merchQuery = getSession().createQuery(hql2);
    	return  (Merchant)merchQuery.uniqueResult(); 
   
    }
    
    @SuppressWarnings("unchecked")
	public Merchant updateMerchantStatus(String userId, String status){
        String hql = "update Merchant m set m.status=:status where m.userId=:userId";
        Query query = getSession().createQuery(hql);
        System.out.println("userId is: " + userId);
        System.out.println("new status is: " + status);
        query.setParameter("status", status);
        query.setParameter("userId", userId);
        query.executeUpdate();
        
    	String hql2 = "FROM Merchant m where m.userId=:userId";
    	Query merchQuery = getSession().createQuery(hql2);
    	merchQuery.setParameter("userId", userId);
    	return  (Merchant)merchQuery.uniqueResult(); 
	}
    
    @SuppressWarnings("unchecked")
	public List<Merchant> findMerchantsByVisibility(String visibility){
		String hql = "FROM Merchant m where m.visibility = :visibility";
		Query query = getSession().createQuery(hql);
		query.setParameter("visibility", visibility);
    	List<Merchant> merchants = (List<Merchant>)query.list();
    	if(merchants==null){
    		merchants = new ArrayList<Merchant>();
    	}
    	return merchants;  
    }
    
    @SuppressWarnings("unchecked")
    public List<Merchant> findMerchantsByVisibilityAndStatus(String visibility, String status){
		String hql = "FROM Merchant m where m.visibility = :visibility and m.status=:status";
		Query query = getSession().createQuery(hql);
		query.setParameter("visibility", visibility);
		query.setParameter("status", status);
    	List<Merchant> merchants = (List<Merchant>)query.list();
    	if(merchants==null){
    		merchants = new ArrayList<Merchant>();
    	}
    	return merchants;  
    }
    
    @SuppressWarnings("unchecked")
	public List<Merchant> findMerchantsByVisibilityAndStatus(String visibility, String status, List<String> merchantIds){
    	String hql = "FROM Merchant m where m.visibility =:visibility and m.status =:status and m.userId IN (:merchantIds)";
    	Query query = getSession().createQuery(hql);
		query.setParameter("visibility", visibility);
		query.setParameter("status", status);
		query.setParameterList("merchantIds", merchantIds);
		List<Merchant> merchants = (List<Merchant>)query.list();
		if(merchants==null){
			merchants = new ArrayList<Merchant>();
		}
		return merchants;
    }
    
  @SuppressWarnings("unchecked")
	public List<Merchant> findMerchantsByMerchantIds(List<String> merchantIds){
    	String hql = "FROM Merchant m where m.userId IN (:merchantIds)";
    	Query query = getSession().createQuery(hql);
		query.setParameterList("merchantIds", merchantIds);
		List<Merchant> merchants = (List<Merchant>)query.list();
		if(merchants==null){
			merchants = new ArrayList<Merchant>();
		}
		return merchants;
    }
    

    
    public Merchant updateProfilePhoto(String userId, String profilePhoto){
        String hql = "update Merchant m set m.profilePhoto=:profilePhoto where m.userId=:userId ";
        Query query = getSession().createQuery(hql);
        System.out.println("userId is: " + userId);
        System.out.println("profile Photo is: " + profilePhoto);
        query.setParameter("profilePhoto", profilePhoto);
        query.setParameter("userId", userId);
        query.executeUpdate();
        
    	String hql2 = "FROM Merchant m where m.userId = '" + userId + "'";
    	Query merchQuery = getSession().createQuery(hql2);
    	return  (Merchant)merchQuery.uniqueResult(); 
   
    }
    
    public Merchant updateStoreBanner(String userId, String storeBanner){
        String hql = "update Merchant m set m.profileBanner=:storeBanner where m.userId=:userId ";
        Query query = getSession().createQuery(hql);
        System.out.println("userId is: " + userId);
        System.out.println("profile Photo is: " + storeBanner);
        query.setParameter("storeBanner", storeBanner);
        query.setParameter("userId", userId);
        query.executeUpdate();
        
    	String hql2 = "FROM Merchant m where m.userId = '" + userId + "'";
    	Query merchQuery = getSession().createQuery(hql2);
    	return  (Merchant)merchQuery.uniqueResult(); 
   
    }
    
    public Merchant updateStoreTagLine(String userId, String storeTagLine){
        String hql = "update Merchant m set m.profileTagLine=:storeTagLine where m.userId=:userId ";
        Query query = getSession().createQuery(hql);
        System.out.println("userId is: " + userId);
        System.out.println("profile Photo is: " + storeTagLine);
        query.setParameter("storeTagLine", storeTagLine);
        query.setParameter("userId", userId);
        query.executeUpdate();
        
    	String hql2 = "FROM Merchant m where m.userId = '" + userId + "'";
    	Query merchQuery = getSession().createQuery(hql2);
    	return  (Merchant)merchQuery.uniqueResult(); 
   
    }
    
	@SuppressWarnings("unchecked")
	public Merchant incrementNotifications(String userId){
		String  hql ="UPDATE Merchant c set c.notifications = c.notifications + 1 WHERE c.userId = :userId";
        Query query = getSession().createQuery(hql);
        System.out.println("userId is: " + userId);
        query.setParameter("userId", userId);
        query.executeUpdate();
        
    	String hql2 = "FROM Merchant m where m.userId = '" + userId + "'";
    	Query custQuery = getSession().createQuery(hql2);
    	return  (Merchant)custQuery.uniqueResult(); 
	}
	
	
	@SuppressWarnings("unchecked")
	public Merchant decrementNotifications(String userId){
		String  hql ="UPDATE Merchant c set c.notifications = c.notifications - 1 WHERE c.userId = :userId";
        Query query = getSession().createQuery(hql);
        System.out.println("userId is: " + userId);
        query.setParameter("userId", userId);
        query.executeUpdate();
        
    	String hql2 = "FROM Merchant m where m.userId = '" + userId + "'";
    	Query custQuery = getSession().createQuery(hql2);
    	return  (Merchant)custQuery.uniqueResult(); 
	}
    
    
}