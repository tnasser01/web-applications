import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {
	
	  
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("userPhotos");
    }
    
    @Bean
    public CacheManager cacheManagerProducts() {
        return new ConcurrentMapCacheManager("products");
    }
    
    @Bean
    public CacheManager cacheManageVisibleMerchants(){
    	return  new ConcurrentMapCacheManager("visibleMerchants");
    }
    
    @Bean
    public CacheManager cacheManageImpressions(){
    	return  new ConcurrentMapCacheManager("impressions");
    }
    
    @Bean
    public CacheManager cacheManageProductImpressions(){
    	return  new ConcurrentMapCacheManager("productImpressions");
    }
    
    @Bean
    public CacheManager cacheManageConnectedMerchants(){
    	return  new ConcurrentMapCacheManager("connectedMerchants");
    }
    
    @Bean
    public CacheManager cacheManageConnectedFriends(){
    	return  new ConcurrentMapCacheManager("connectedFriends");
    }
    
  
}