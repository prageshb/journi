package com.pragesh.journi.controller;

import com.pragesh.journi.entity.JournalEntry;
import com.pragesh.journi.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController()
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("journi")
public class JourniEntryController {

    @Autowired
    private JournalEntryService journalEntryService;


    //Returns entered data as the object of Entry class as a list
    @GetMapping()
    public List<JournalEntry> getEntry() {

        return journalEntryService.getAllEntries();
    }

    //Taking in entries from user
    @PostMapping()
    public ResponseEntity<JournalEntry> enterEntry(@RequestBody JournalEntry entry) {
        try {
            entry.setCreationDate(LocalDateTime.now());
            journalEntryService.saveEntry(entry);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(entry, HttpStatus.BAD_REQUEST);
        }

    }

    //Get journal entry with it's id
    @GetMapping("/id/{entryId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId entryId) {

        Optional<JournalEntry> journalEntry = journalEntryService.findById(entryId);
        if(journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } 

    //Deleting an entry with id
    @DeleteMapping("/id/{entryId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId entryId) {

            journalEntryService.deleteEntryById(entryId);

        // it seems in case of ? you cannot use try catch block and return statement has to be outside the block
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @PutMapping("/id/{entryId}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId entryId, @RequestBody JournalEntry recievedEntry) {
        JournalEntry entryToUpdate = (JournalEntry) journalEntryService.findById(entryId).orElse(null);


        if (entryToUpdate != null) {

            if (recievedEntry.getTitle() != null && !recievedEntry.getTitle().equals("") && !recievedEntry.getTitle().equals(entryToUpdate.getTitle())) {
                entryToUpdate.setTitle(recievedEntry.getTitle());
                entryToUpdate.setLastModified(LocalDateTime.now());

            } else {

                entryToUpdate.setTitle(entryToUpdate.getTitle());
            }

            if (recievedEntry.getContent() != null && !recievedEntry.getContent().equals("") && !recievedEntry.getContent().equals(entryToUpdate.getContent())) {
                entryToUpdate.setContent(recievedEntry.getContent());
                entryToUpdate.setLastModified(LocalDateTime.now());

            } else {
                entryToUpdate.setContent(entryToUpdate.getContent());
            }

            if (recievedEntry.getImageUrl() != null && !recievedEntry.getImageUrl().equals("") && !recievedEntry.getImageUrl().equals(entryToUpdate.getImageUrl())) {
                entryToUpdate.setTitle(recievedEntry.getImageUrl());
                entryToUpdate.setLastModified(LocalDateTime.now());

            } else {

                entryToUpdate.setTitle(entryToUpdate.getImageUrl());
            }

            //error was because i was saving the new entry directly instead of updated entry
            journalEntryService.saveEntry(entryToUpdate);
            return new ResponseEntity<>(entryToUpdate, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
