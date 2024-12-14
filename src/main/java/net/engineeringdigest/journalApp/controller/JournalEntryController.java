package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
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
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

 @GetMapping("{username}")
 public ResponseEntity<?> getAlljournalEntriesofUser(@PathVariable String username){

     User user = userService.findByUsername(username);
     List<JournalEntry> all = user.getJournalEntries();
     if(all!=null && !all.isEmpty()){
         return new ResponseEntity<>(all,HttpStatus.OK);
     }
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);

 }
 @PostMapping("{username}")
 public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String username){
     try {
         myEntry.setDate(LocalDateTime.now());
         journalEntryService.saveEntry(myEntry,username);
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
    @DeleteMapping("id/{username}/{myId}")

    public ResponseEntity<? >  deleteEntryId(@PathVariable ObjectId myId,@PathVariable String username){
    journalEntryService.deletebyId(myId,username);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/id/{username}/{id}")

    public ResponseEntity<?> updateJournalId(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry,@PathVariable String username){
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
