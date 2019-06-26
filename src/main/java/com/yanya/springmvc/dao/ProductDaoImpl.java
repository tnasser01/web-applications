package com.yanya.springmvc.dao;
 
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;

import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.ProductDao;

 
@Repository("productDao")
public class ProductDaoImpl extends AbstractDao<String, Product> implements ProductDao {

    @SuppressWarnings("unchecked")
    public Product saveNewProduct(Product product) {
    	System.out.println("The value of product objects userId in productdao is " + product.getMerchant().getUserId());
    	System.out.println("The value of product objects product name in productdao is " + product.getProductName());
    	System.out.println("The value of product objects product descrip in productdao is " + product.getProductDescription());
    	String id = (String)save(product);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM Product p where p.productId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Product)query.uniqueResult();
   
    }
    
    @SuppressWarnings("unchecked")
    public Product updateProduct(Product product) {
    	System.out.println("The value of product objects userId in productdao is " + product.getMerchant().getUserId());
    	System.out.println("The value of product objects product name in productdao is " + product.getProductName());
    	System.out.println("The value of product objects product descrip in productdao is " + product.getProductDescription());
    	saveOrUpdate(product);
    	String hql = "FROM Product p where p.productId = '" + product.getProductId() + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Product)query.uniqueResult();
   
    }
    
    @SuppressWarnings("unchecked")
    public List<Product> findProductImagePath(String userId, String imagePath, Integer photoNumber){
    	
    		String hql ="FROM Product p  where p.merchant.userId = '" + userId + "' and p.imagePath" + photoNumber + " = '" + imagePath + "'";
    		Query query = getSession().createQuery(hql);
    		return (List<Product>)query.list();
    		
    	
    }

    @SuppressWarnings("unchecked")
    public List<Product> findPrivateProductsByMerchantIds(List<String> userIds){
    	String hql = "FROM Product p where p.merchant.visibility='private' and p.merchant.userId IN (:userIds)";
    	Query query = getSession().createQuery(hql);
    	query.setParameter("userIds", userIds);
    	return (List<Product>)query.list();
    }
    
    @SuppressWarnings("unchecked")
	public List<Product> findPublicProducts(){
    	String hql = "FROM Product p where p.merchant.visibility='public'";
		Query query = getSession().createQuery(hql);
		return (List<Product>)query.list();
    	
    }
    
    @SuppressWarnings("unchecked")
    public List<Product> findAllVisibleProducts(List<String> userIds){
    	String hql = "";
    	Query query = null;
    	if(userIds!=null || userIds.size()==0){
    		hql = "FROM Product p where p.merchant.visibility='public' ORDER BY p.rating DESC";
    		query = getSession().createQuery(hql);
    	}else{
    		hql = "FROM Product p where p.merchant.visibility='public' OR p.merchant.userId IN (:userIds) ORDER BY p.rating DESC";
    		query = getSession().createQuery(hql).setParameterList("userIds", userIds);
    	}
    	List<Product> products = (List<Product>)query.list();
    	if(products==null){
    		return new ArrayList<Product>();
    	}
    	
    	return products;
    	
    	
    }
    
    @SuppressWarnings("unchecked")
    public List<Product> searchByZip(String zipCode) {
    	System.out.println("The inputted string to search for is:" + zipCode);
    	String hql = "FROM Product p where p.merchant.zipCode = '" + zipCode + "'";
    	Query query = getSession().createQuery(hql);
    	List<Product> products = (List<Product>)query.list();
    	if(products==null){
    		products = new ArrayList<Product>();
    	}
    	return products;  
  	}
    
    
    @SuppressWarnings("unchecked")
    public List<Product> searchByZips(List<String> zips) {
    	
    	String hql = "FROM Product p where p.merchant.zipCode IN (:zips)";
    	
    	Query query = getSession().createQuery(hql).setParameterList("zips", zips);
    	List<Product> products = (List<Product>)query.list();
    	if(products==null){
    		products = new ArrayList<Product>();
    	}
    	return products;  
  	}
   
    @SuppressWarnings("unchecked")
    public List<Product> searchByKeyword(String keyword){
//       	System.out.println("The inputted string to search for is:" + keyword);

       	String hql = "FROM Product p where lower(p.productName) like lower('%"+ keyword +"%')"
    			                     + " OR lower(p.productType) like lower('%"+ keyword +"%')" 
    			                     + " OR lower(p.flowerStrain) like lower('%"+ keyword +"%')";
    	Query query = getSession().createQuery(hql);
    	List<Product> products = (List<Product>)query.list();
    	if(products==null){
    		products = new ArrayList<Product>();
    	}
    	return products;  
  	}
    
    @SuppressWarnings("unchecked")
    public List<Product> findAllProducts() {
        Criteria criteria = createEntityCriteria();
        List<Product> products = (List<Product>)criteria.addOrder(Order.desc("rating")).list();
        if(products==null){
        	products = new ArrayList<Product>();
        }
        return products;  
	}
    
    @SuppressWarnings("unchecked")
    public void deleteProduct(Product product) {
    	delete(product);
    }
    
    @SuppressWarnings("unchecked")
    public Product findProductByProductName(String productName){
    	Query query = getSession().createQuery("FROM Product p where p.productName = '" + productName + "'");
    	return (Product)query.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public Product findProductByProductId(String productId){
    	Query query = getSession().createQuery("FROM Product p where p.productId = '" + productId + "'");
    	return (Product)query.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    
    public List<Product> filterSearchResults(SearchFilterForm filter, List<String> zipCodes){
    	System.out.println("Distance is: " + filter.getDistance());
    	Query query = getSession().createQuery("FROM Product p where p.merchant.zipCode in (:zips)").setParameterList("zips", zipCodes);
    	List<Product> products = (List<Product>)query.list();
    	if(products==null){
    		products = new ArrayList<Product>();
    	}
    	return products;  
  	}
    
}