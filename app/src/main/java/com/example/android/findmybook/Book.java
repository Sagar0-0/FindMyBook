package com.example.android.findmybook;

public class Book {
    private String image;
    private String bookName;
    private String authorName;
    private String language;
    private String url;

    public Book(String bookName,String authorName,String language,String image,String url){
        this.image=image;
        this.bookName=bookName;
        this.authorName=authorName;
        this.language=language;
        this.url=url;
    }

    public String getImage() {
        return image;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return "By: "+ authorName;
    }

    public String getLanguage() {
        return language;
    }

    public String getUrl() {
        return url;
    }
}
