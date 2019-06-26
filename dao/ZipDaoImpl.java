package com.yanya.springmvc.dao;
 
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

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

import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.ProductDao;

 
@Repository("zipDao")
public class ZipDaoImpl extends AbstractDao<String, ZipCode> implements ZipDao {
    
    @SuppressWarnings("unchecked")
	public List<String> findZipsInRange(double latMax, double latMin, double lngMax, double lngMin){
		System.out.println("Dao is finding zips in range");
		String hql = "FROM ZipCode z where z.lat < '" + latMax + "' AND z.lat > '" + latMin + "' AND z.lng < '" + lngMax + "' AND z.lng > '" + lngMin + "'";	
    	Query query = getSession().createQuery(hql);
        List<ZipCode> zipcodes = (List<ZipCode>)query.list();
        List<String> zipStrings = new ArrayList<String>();
        if(zipcodes!=null){
        	for(ZipCode z: zipcodes){
        		zipStrings.add(z.getZip());
        	}
        }
        
    	System.out.println("Total zips in range:" + zipStrings.size());
    	return zipStrings;
		    		
		
	}
	
    public ZipCode findLatAndLng(String zipCode){
    	System.out.println("We made it to the zip Dao");
    	String hql = "FROM ZipCode z where z.zip =:zipCode";
    	Query query = getSession().createQuery(hql).setParameter("zipCode", zipCode);

    	return (ZipCode)query.uniqueResult();
    	
    }
 
 
}