package com.yanya.springmvc.service;
 
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.dao.NoteDao;
import com.yanya.springmvc.model.Note;
import com.yanya.springmvc.service.NoteService;

import java.lang.Integer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

@Service("noteService")
@Transactional
public class NoteServiceImpl implements NoteService {
 
    @Autowired
    private NoteDao noteDao;
       
    public Note saveNewNote(Note note) {
    	return noteDao.saveNewNote(note);
    }
    
    public List<Note> findNotesByCustomerId(String userId) {
    	return  noteDao.findNotesByCustomerId(userId);   	
    }
    
    public List<Note> findNotesByMerchantId(String userId) {
    	return  noteDao.findNotesByMerchantId(userId);   	
    }
    
    public List<Note> findNotesByCustomerName(String customerName) {
    	return  noteDao.findNotesByCustomerName(customerName);   	
    }
     
     
    public Note findNoteByEntryId(String entryId) {
    	return  noteDao.findNoteByEntryId(entryId);   	
    }
    
    public List<Note> findAllNotes() {
        return noteDao.findAllNotes();
    }
 
 
}