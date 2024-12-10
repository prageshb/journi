package com.pragesh.journi.controller;

import com.pragesh.journi.entity.JournalEntry;
import com.pragesh.journi.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        entry.setDate(LocalDateTime.now());
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
    public boolean updateEntryById(@PathVariable ObjectId entryId, @RequestBody JournalEntry newEntry) {
        JournalEntry entryToUpdate = (JournalEntry) journalEntryService.findById(entryId).orElse(null);

        if (entryToUpdate != null) {

            if (newEntry.getTitle() != "") {
                entryToUpdate.setTitle(newEntry.getTitle());

            }

            if (entryToUpdate.getContent() != "") {
                entryToUpdate.setContent(newEntry.getContent());
            }

            // entryToUpdate.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : entryToUpdate.getTitle());
            //entryToUpdate.setContent(newEntry.getContent() != null && !newEntry.equals("") ? newEntry.getContent() : entryToUpdate.getContent());
            journalEntryService.saveEntry(newEntry);
        }
        return true;
    }


}
