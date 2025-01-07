package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

 @GetMapping
 public ResponseEntity<?> getAlljournalEntriesofUser(){
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     String username = authentication.getName();

     User user = userService.findByUsername(username);
     List<JournalEntry> all = user.getJournalEntries();
     if(all!=null && !all.isEmpty()){
         return new ResponseEntity<>(all,HttpStatus.OK);
     }
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);

 }
 @PostMapping
 public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
     try {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String username = authentication.getName();
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
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     String username = authentication.getName();
     User user = userService.findByUsername(username);
    List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());;
    if(!collect.isEmpty()){
        Optional<JournalEntry> journalEntry = journalEntryService.findbyId(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);

        }
    }

     return new ResponseEntity<>(HttpStatus.NOT_FOUND);



 }
    @DeleteMapping("id/{myId}")

    public ResponseEntity<? >  deleteEntryId(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deletebyId(myId, username);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{myId}")

    public ResponseEntity<?> updateJournalId(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());;
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findbyId(myId);

            if(journalEntry.isPresent()){
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")?newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);

            }
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
