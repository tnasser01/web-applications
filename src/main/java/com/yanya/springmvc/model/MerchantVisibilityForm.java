package com.yanya.springmvc.model;
 
import java.math.BigDecimal;
import java.util.Set;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import static javax.persistence.GenerationType.IDENTITY;

 
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
 
public class MerchantVisibilityForm {
 
    private String userId;
    private String visibility;

    public MerchantVisibilityForm(){ }

	public MerchantVisibilityForm(String userId, String visibility) {
		this.userId = userId;
		this.visibility = visibility;
	}


	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
 
     
}