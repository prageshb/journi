package controller;

import entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/journi")
public class JournalEntryController {

    //A map consisting of Journal Entries from user
    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    //Journal Entries' map returning to the users
    @GetMapping
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    //Inserting data received from user (json) into journalEntries map
    @PostMapping
    public void createEntry(@RequestBody JournalEntry journalEntry){
        journalEntries.put(journalEntry.getId(), journalEntry);
    }

    //Get post by id
    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable long myId){
      return journalEntries.get(myId);
    }

    //Delete a post by its Id

    @DeleteMapping("id/{myId}")
    public JournalEntry deleteJournalEntryById(@PathVariable long myId){
        return journalEntries.remove(myId);
    }

    @PutMapping
    public JournalEntry updateJournalEntryById(@PathVariable long myId, @RequestBody JournalEntry journalEntryUpdate ){
        return journalEntries.put(myId, journalEntryUpdate);
    }


    }


