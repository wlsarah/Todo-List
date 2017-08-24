package com.codepath.todo;

/**
 * Created by Sarahwang on 8/23/17.
 */

public class ContactInfo {
    int id;
    protected String name;
    protected String path;
    protected String description;



    public ContactInfo(int id, String path, String name, String description) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.description = description;


    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }


    public int getId() {
        return id;
    }

    protected static final String NAME_PREFIX = "Name_";
    // protected static final String SURNAME_PREFIX = "Surname_";
    protected static final String EMAIL_PREFIX = "email_";
}
