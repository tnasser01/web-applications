package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

//import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.Message;
import com.yanya.springmvc.model.ReadTimeForm;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.ZipCode;
 
public interface MessageDao {
	 
	@Autowired
	Message saveNewMessage(Message message);

	@Autowired
	List<Message> findMessagesByMerchantId(String userId);
	
	@Autowired
	List<Message> findMessagesByCustomerId(String userId);
	
	@Autowired
	void markMessageRead(ReadTimeForm form);
	
	@Autowired	
	Message findMessageByMessageId(String messageId);
	
	@Autowired
	void deleteMessage(Message message);
	
}
