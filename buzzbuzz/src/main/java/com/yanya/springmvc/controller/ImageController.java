package com.yanya.springmvc.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ImageController {

	@RequestMapping("/image/user/{userId}/{imagePath:.+}")
	@ResponseBody
	public HttpEntity<byte[]> getUserPhoto(@PathVariable("userId") String userId, @PathVariable("imagePath") String imagePath, HttpServletRequest request, HttpServletResponse response) {
		
		HttpHeaders headers = new HttpHeaders();
		byte[] image = null;

		try{
			File file = new File( "/media/Rihup/User/UserPhotos" + File.separator + userId + File.separator + imagePath);
			System.out.println(file.getPath());
		    image = org.apache.commons.io.FileUtils.readFileToByteArray(file);
		    
		    headers.setContentType(MediaType.IMAGE_PNG); 
		    headers.setContentLength(image.length);
		    
		}catch(IOException e){System.out.println("Could not render the image");}
		response.setHeader("Cache-Control", "max-age=31536000");
		return new HttpEntity<byte[]>(image, headers);
	    
	}
	
	@RequestMapping("/image/profile/{userId}/{profilePhoto:.+}")
	@ResponseBody
	public HttpEntity<byte[]> getProfilePhoto(@PathVariable("userId") String userId, @PathVariable("profilePhoto") String profilePhoto) {
		
		HttpHeaders headers = new HttpHeaders();
		byte[] image = null;

		try{
			File file = new File( "/media/Rihup/User/ProfilePhotos" + File.separator + userId + File.separator + profilePhoto);
			System.out.println(file.getPath());
		    image = org.apache.commons.io.FileUtils.readFileToByteArray(file);
		    
		    headers.setContentType(MediaType.IMAGE_PNG); 
		    headers.setContentLength(image.length);
		    
		}catch(IOException e){System.out.println("Could not render the image");}
	
		return new HttpEntity<byte[]>(image, headers);
	    
	}
	

	@RequestMapping("/image/product/{userId}/1/{imagePath:.+}")
	@ResponseBody
	public HttpEntity<byte[]> getProductPhoto1(@PathVariable("userId") String userId, @PathVariable("imagePath") String imagePath, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Image path is: " + imagePath);
		HttpHeaders headers = new HttpHeaders();
		byte[] image = null;

		try{
			File file = new File( "/media/Rihup/Merchant/ProductPhotos1" + File.separator + userId + File.separator  + imagePath );
			System.out.println(file.getPath());
		    image = org.apache.commons.io.FileUtils.readFileToByteArray(file);
		    
		    headers.setContentType(MediaType.IMAGE_JPEG); 
		    headers.setContentLength(image.length);
		    
		}catch(IOException e){System.out.println("Could not render the image");}
		response.setHeader("Cache-Control", "max-age=31536000");
		return new HttpEntity<byte[]>(image, headers);
	    
	}
	
	@RequestMapping("/image/product/{userId}/2/{imagePath:.+}")
	@ResponseBody
	public HttpEntity<byte[]> getProductPhoto2(@PathVariable("userId") String userId, @PathVariable("imagePath") String imagePath, HttpServletRequest request, HttpServletResponse response ){
		System.out.println("Image path is: " + imagePath);
		HttpHeaders headers = new HttpHeaders();
		byte[] image = null;

		try{
			File file = new File( "/media/Rihup/Merchant/ProductPhotos2" + File.separator + userId + File.separator  + imagePath  );
			System.out.println(file.getPath());
		    image = org.apache.commons.io.FileUtils.readFileToByteArray(file);
		    
		    headers.setContentType(MediaType.IMAGE_JPEG); 
		    headers.setContentLength(image.length);
		    
		}catch(IOException e){System.out.println("Could not render the image");}
		response.setHeader("Cache-Control", "max-age=31536000");
		return new HttpEntity<byte[]>(image, headers);
	    
	}
	
	@RequestMapping("/image/product/{userId}/3/{imagePath:.+}")
	@ResponseBody
	public HttpEntity<byte[]> getProductPhoto3(@PathVariable("userId") String userId, @PathVariable("imagePath") String imagePath, HttpServletRequest request, HttpServletResponse response ) {
		System.out.println("Image path is: " + imagePath);
		HttpHeaders headers = new HttpHeaders();
		byte[] image = null;

		try{
			File file = new File( "/media/Rihup/Merchant/ProductPhotos3" + File.separator + userId + File.separator  + imagePath );
			System.out.println(file.getPath());
		    image = org.apache.commons.io.FileUtils.readFileToByteArray(file);
		    
		    headers.setContentType(MediaType.IMAGE_JPEG); 
		    headers.setContentLength(image.length);
		    
		}catch(IOException e){System.out.println("Could not render the image");}
		response.setHeader("Cache-Control", "max-age=31536000");
		return new HttpEntity<byte[]>(image, headers);
	    
	}
	
	@RequestMapping("/image/product/{userId}/4/{imagePath:.+}")
	@ResponseBody
	public HttpEntity<byte[]> getProductPhoto4(@PathVariable("userId") String userId, @PathVariable("imagePath") String imagePath, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Image path is: " + imagePath);
		System.out.println("userId is: " + userId);
		HttpHeaders headers = new HttpHeaders();
		byte[] image = null;

		try{
			File file = new File( "/media/Rihup/Merchant/ProductPhotos4" + File.separator + userId + File.separator  + imagePath  );
			System.out.println(file.getPath());
		    image = org.apache.commons.io.FileUtils.readFileToByteArray(file);
		    
		    headers.setContentType(MediaType.IMAGE_JPEG); 
		    headers.setContentLength(image.length);
		    
		}catch(IOException e){System.out.println("Could not render the image");}
		response.setHeader("Cache-Control", "max-age=31536000");
		return new HttpEntity<byte[]>(image, headers);
	    
	}
	
	@RequestMapping("/image/product/{userId}/5/{imagePath:.+}")
	@ResponseBody
	public HttpEntity<byte[]> getProductPhoto5(@PathVariable("userId") String userId, @PathVariable("imagePath") String imagePath, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Image path is: " + imagePath);
		HttpHeaders headers = new HttpHeaders();
		byte[] image = null;

		try{
			File file = new File( "/media/Rihup/Merchant/ProductPhotos5" + File.separator + userId + File.separator  + imagePath );
			System.out.println(file.getPath());
		    image = org.apache.commons.io.FileUtils.readFileToByteArray(file);
		    
		    headers.setContentType(MediaType.IMAGE_JPEG); 
		    headers.setContentLength(image.length);
		    
		}catch(IOException e){System.out.println("Could not render the image");}
		response.setHeader("Cache-Control", "max-age=31536000");
		return new HttpEntity<byte[]>(image, headers);
	    
	}

	
	@RequestMapping("/image/store/{userId}/{profilePhoto:.+}")
	@ResponseBody
	public HttpEntity<byte[]> getStorePhoto(@PathVariable("userId") String userId, @PathVariable("profilePhoto") String profilePhoto) {
		
		HttpHeaders headers = new HttpHeaders();
		byte[] image = null;

		try{
			File file = new File( "/media/Rihup/Merchant/StorePhotos" + File.separator + userId + File.separator + profilePhoto);
			System.out.println(file.getPath());
		    image = org.apache.commons.io.FileUtils.readFileToByteArray(file);
		    
		    headers.setContentType(MediaType.IMAGE_PNG); 
		    headers.setContentLength(image.length);
		    
		}catch(IOException e){System.out.println("Could not render the image");}
	
		return new HttpEntity<byte[]>(image, headers);
	    
	}
	
	@RequestMapping("/image/profileBanner/{userId}/{profileBanner:.+}")
	@ResponseBody
	public HttpEntity<byte[]> getProfileBanner(@PathVariable("userId") String userId, @PathVariable("profileBanner") String profileBanner) {
		
		HttpHeaders headers = new HttpHeaders();
		byte[] image = null;

		try{
			File file = new File( "/media/Rihup/User/ProfileBanners" + File.separator + userId + File.separator + profileBanner);
			System.out.println(file.getPath());
		    image = org.apache.commons.io.FileUtils.readFileToByteArray(file);
		    
		    headers.setContentType(MediaType.IMAGE_PNG); 
		    headers.setContentLength(image.length);
		    
		}catch(IOException e){System.out.println("Could not render the image");}
	
		return new HttpEntity<byte[]>(image, headers);
	    
	}
	
	@RequestMapping("/image/storeBanner/{userId}/{storeBanner:.+}")
	@ResponseBody
	public HttpEntity<byte[]> getStoreBanner(@PathVariable("userId") String userId, @PathVariable("storeBanner") String storeBanner) {
		
		HttpHeaders headers = new HttpHeaders();
		byte[] image = null;

		try{
			File file = new File( "/media/Rihup/Merchant/StoreBanners" + File.separator + userId + File.separator + storeBanner);
			System.out.println(file.getPath());
		    image = org.apache.commons.io.FileUtils.readFileToByteArray(file);
		    
		    headers.setContentType(MediaType.IMAGE_PNG); 
		    headers.setContentLength(image.length);
		    
		}catch(IOException e){System.out.println("Could not render the image");}
	
		return new HttpEntity<byte[]>(image, headers);
	    
	}
	
	
	

}