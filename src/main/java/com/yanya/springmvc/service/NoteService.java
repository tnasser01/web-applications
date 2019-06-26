package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.yanya.springmvc.model.Note;

import java.lang.Integer;
 
public interface NoteService {
	
	Note saveNewNote(Note note);
    List<Note> findAllNotes(); 
    List<Note> findNotesByCustomerId(String userId);
    List<Note> findNotesByMerchantId(String userId);
    List<Note> findNotesByCustomerName(String customerName);
    Note findNoteByEntryId(String entryId);
    
}