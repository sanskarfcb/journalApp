package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

 @GetMapping
 public ResponseEntity<?> getAll(){

     List<JournalEntry> all = journalEntryService.getAll();
     if(all!=null && !all.isEmpty()){
         return new ResponseEntity<>(all,HttpStatus.OK);
     }
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);

 }
 @PostMapping
 public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
     try {
         myEntry.setDate(LocalDateTime.now());
         journalEntryService.saveEntry(myEntry);
         return  new ResponseEntity<>(myEntry,HttpStatus.CREATED);
     }
     catch (Exception e){
     return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
 }
 }

 @GetMapping("id/{myId}")

    public ResponseEntity<JournalEntry> getEntrybyId(@PathVariable ObjectId myId){

     Optional<JournalEntry> journalEntry = journalEntryService.findbyId(myId);
     if(journalEntry.isPresent()){
         return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);

     }
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);



 }
    @DeleteMapping("id/{myId}")

    public ResponseEntity<? >  deleteEntryId(@PathVariable ObjectId myId){
    journalEntryService.deletebyId(myId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/id/{id}")

    public ResponseEntity<?> updateJournalId(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
       JournalEntry Old = journalEntryService.findbyId(id).orElse(null);
       if(Old!=null){
        Old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : Old.getTitle());
        Old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")?newEntry.getContent() : Old.getContent());
        journalEntryService.saveEntry(Old);
        return new ResponseEntity<>(HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
