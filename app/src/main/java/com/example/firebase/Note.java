package com.example.firebase;

class Note {
    private String name;
    private String comment;
    private String text;

    Note() {

    }

    public Note(String name, String comment, String text) {
        this.name = name;
        this.comment = comment;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTexts(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
