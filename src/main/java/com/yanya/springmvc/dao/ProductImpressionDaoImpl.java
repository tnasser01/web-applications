package com.yanya.springmvc.dao;
 
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.ImportResource;

import com.yanya.springmvc.model.ProductImpression;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.ProductImpressionDao;

 
@Repository("productImpressionDao")
public class ProductImpressionDaoImpl extends AbstractDao<String, ProductImpression> implements ProductImpressionDao {

    @SuppressWarnings("unchecked")
    public void saveOrUpdateProductImpression(ProductImpression productImpression) {
    	String hql = "FROM ProductImpression i where i.product = '" + productImpression.getProduct().getProductId() + "' and i.userId = '" + productImpression.getUserId() + "'";
    	Query query = getSession().createQuery(hql);
    	ProductImpression imp = (ProductImpression)query.uniqueResult();
    	if(imp!=null){
    		update(productImpression);
    	}else{
    		save(productImpression);
    	}

    }
    
    @SuppressWarnings("unchecked")
    public void saveProductImpression(ProductImpression productImpression) {
    		save(productImpression);
    }
    
    @SuppressWarnings("unchecked")
    public void updateProductImpression(ProductImpression productImpression) {
    		update(productImpression);
    }
    
    
    @SuppressWarnings("unchecked")
    public ProductImpression findProductImpressionByProductIdAndUserId(String productId,String userId){
    	String hql = "FROM ProductImpression i where i.product = '" + productId + "' and i.userId = '" + userId + "'";
    	Query query = getSession().createQuery(hql);
    	return  (ProductImpression)query.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
	public List<ProductImpression> findProductImpressionsByProductIdsAndUserId(List<String> productIds, String userId, String userType){
		String hql = "FROM ProductImpression p where p.product.productId IN (:productIds) and p.userId =:userId and p.userType =:userType";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameter("userType", userType);
		query.setParameterList("productIds", productIds);
		List<ProductImpression> productImpressions = (List<ProductImpression>)query.list();
		if(productImpressions==null){
			return new ArrayList<ProductImpression>();
		}
		
		return productImpressions;
		
	}

}