package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.yanya.springmvc.model.Note;
 
public interface NoteDao {
 
	@Autowired
	Note saveNewNote(Note note);

	@Autowired
    List<Note> findAllNotes();
    
	@Autowired
    Note findNoteByEntryId(String entryId);
    
	@Autowired
    List<Note> findNotesByMerchantId(String userId);
	
	@Autowired
    List<Note> findNotesByCustomerId(String userId);
	
	@Autowired
	List<Note> findNotesByCustomerName(String customerName);
}