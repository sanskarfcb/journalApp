package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

 @GetMapping
 public List<JournalEntry> getAll(){

    return journalEntryService.getAll();

 }
 @PostMapping
 public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
     myEntry.setDate(LocalDateTime.now());
    journalEntryService.saveEntry(myEntry);
     return myEntry;

 }

 @GetMapping("id/{myId}")

    public JournalEntry getEntrybyId(@PathVariable ObjectId myId){

    return journalEntryService.findbyId(myId).orElse(null);

 }
    @DeleteMapping("id/{myId}")

    public Boolean  deleteEntryId(@PathVariable ObjectId myId){
    journalEntryService.deletebyId(myId);
    return true;

    }

    @PutMapping("/id/{id}")

    public JournalEntry updateJournalId(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
       JournalEntry Old = journalEntryService.findbyId(id).orElse(null);
       if(Old!=null){
        Old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : Old.getTitle());
        Old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")?newEntry.getContent() : Old.getContent());
       }
        journalEntryService.saveEntry(Old);
        return Old;


    }
}
