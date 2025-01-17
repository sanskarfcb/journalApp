package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private  UserService userService;


    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        try{ User user = userService.findByUsername(username);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e){
            System.out.println("kya bhaiii");
            throw new RuntimeException("An error occured while saving entries",e);
        }

    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findbyId(ObjectId id){
    return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deletebyId(ObjectId id, String username){
        boolean removed = false;
    try {
        User user = userService.findByUsername(username);
         removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        if (removed) {
            userService.saveUser(user);
            journalEntryRepository.deleteById(id);
        }
    } catch(Exception e){
        System.out.println(e);
        throw new RuntimeException("An error occured while deleting the entry" , e);

        }

        return removed;

    }

}
