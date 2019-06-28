package com.yanya.springmvc.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.yanya.springmvc.model.Comment;
import com.yanya.springmvc.model.Customer;

@Entity
@Table(name = "UserPhoto")
public class UserPhoto implements Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "userPhotoId", unique = true, nullable = false)
	private String userPhotoId;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "userId", nullable = false)
	private Customer customer;
	
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userPhoto")
    private List<Comment> comments;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "userPhoto")
	private Set<Impression> impressions;
	
	@Column(name = "description", nullable=true)
	private String description;

	@Column(name = "imagePath")
	private String imagePath;
	
	private Integer likes =0;
	
	public UserPhoto() {}
	
	public UserPhoto(Customer customer, String description, String imagePath) {
		this.customer = customer;
		this.description = description;
		this.imagePath = imagePath;
	
		
	}

	public UserPhoto(String userPhotoId, Customer customer, String description, String imagePath) {
		this.userPhotoId = userPhotoId;
		this.customer = customer;
		this.description = description;
		this.imagePath = imagePath;
	
	}



	@Override
	public int hashCode() {

		if (userPhotoId == null)
			return 0;

		final int prime = 31;
		int result = 1;
		result = prime * result + Integer.parseInt(userPhotoId);
		// result = prime * result + ((ssn == null) ? 0 : ssn.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserPhoto))
			return false;
		UserPhoto other = (UserPhoto) obj;
		if (userPhotoId != other.userPhotoId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserPhotoId=" + userPhotoId  + "]";
	}



	public String getUserPhotoId() {
		return userPhotoId;
	}



	public void setUserPhotoId(String userPhotoId) {
		this.userPhotoId = userPhotoId;
	}



	public Customer getCustomer() {
		return customer;
	}



	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



	public String getUserPhotoDescription() {
		return description;
	}



	public void setUserPhotoDescription(String description) {
		this.description = description;
	}


	public String getImagePath() {
		return imagePath;
	}



	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImpressions(Set<Impression> impressions) {
		this.impressions = impressions;
	}

	public Set<Impression> getImpressions() {
		return impressions;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes() {
		int likeCnt = 0;
		for(Impression i: impressions){
			if(i.getImpressionType().equals("like")){
				System.out.println("######" + i.getImpressionId());
				likeCnt++;
			}	
		}
		this.likes = likeCnt;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}
	




}