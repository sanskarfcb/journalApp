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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAll();
    }


    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.saveEntry(user);
    }


    @PutMapping("/{username}")
    public ResponseEntity<?> updateuser(@RequestBody User user , @PathVariable String username){
        User userindb = userService.findByUsername(username);
        if(userindb!=null){
            userindb.setUsername(user.getUsername());
            userindb.setPassword(user.getPassword());
            userService.saveEntry(userindb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
