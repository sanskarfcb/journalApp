package net.engineeringdigest.journalApp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Document(collection = "journal_entries")
public class JournalEntry {

    @Id
    private ObjectId id;


    private String title;

    private LocalDateTime date;

    public void setDate(LocalDateTime date) {
        this.date = date;
    }




    public LocalDateTime getDate() {
        return date;
    }



    public ObjectId getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    private String content;
}
