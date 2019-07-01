package ch.esa.www.keepass.bean;

import java.io.Serializable;

public class Note implements Serializable {

    private int noteId;
    private String noteTitle;
    private String noteContent;
    private String noteUser;

    public Note() {

    }
    //für alte Version 8.03.2019
    public Note(String noteTitle, String noteContent, String noteUser) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteUser = noteUser;
    }

    public Note(int noteId, String noteTitle, String noteContent, String noteUser) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteUser = noteUser;
    }


    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteUser() {
        return noteUser;
    }

    public void setNoteUser(String noteUser) {
        this.noteUser = noteUser;
    }

    @Override
    public String toString() {
        return this.noteTitle;
    }

}