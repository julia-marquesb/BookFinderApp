package org.example.bookfinderapp;

import com.google.gson.annotations.SerializedName;
//Serialized Name annotation is part of the GSON library (Google's JSON utility).
// It's used to map JSON property names to Java class attributes when the names differ between the JSON file and your Java code.

//This class contains all book's attributes such as title, author, ect.
//This is the model or blueprint class
public class Book {

    private String title;

    @SerializedName("authors")
    private String authors;

    private String publisher;

    @SerializedName("publishedDate")
    private String publishedDate;

    private String description;

    @SerializedName("thumbnail")
    private String coverImageUrl;

    public Book (String title, String authors, String publisher, String publishedDate, String description, String coverImageUrl) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }


}//end of class
