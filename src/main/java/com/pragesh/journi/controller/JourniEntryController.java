package com.pragesh.journi.controller;

import com.pragesh.journi.entity.JournalEntry;
import com.pragesh.journi.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController()
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
    public boolean enterEntry(@RequestBody JournalEntry entry) {
        entry.setCreationDate(LocalDateTime.now());
        journalEntryService.saveEntry(entry);
        return true;
    }

    //Get journal entry with it's id
    @GetMapping("/id/{entryId}")
    public JournalEntry getEntryById(@PathVariable ObjectId entryId) {
        return (JournalEntry) journalEntryService.findById(entryId).orElse(null);

    }

    //Deleting an entry with id
    @DeleteMapping("/id/{entryId}")
    public String deleteEntryById(@PathVariable ObjectId entryId) {
        journalEntryService.deleteEntryById(entryId);
        return "Deleted";
    }

    @PutMapping("/id/{entryId}")
    public boolean updateEntryById(@PathVariable ObjectId entryId, @RequestBody JournalEntry recievedEntry) {
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

            //error was because i was saving the new entry directly instead of updated entry
            journalEntryService.saveEntry(entryToUpdate);
        }
        else {
            return false;
        }
        return true;
    }


}
