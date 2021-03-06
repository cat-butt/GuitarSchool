package com.jackson.guitar;

/**
 * Created by jackson on 3/16/2017.
 */
public class Note {
    private int note;
    private int position;
    private boolean is_root = false;


    public enum Status { FOUND, NOT_FOUND, MUTE_STRING} ;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private Status status = Status.FOUND;

    Note(int n) {
        this(n,false);
    }
    Note(int n, boolean root) {
        note = n;
        is_root = root;
    }
    Note(int n, int pos) {
        note = n;
        position = pos;
        if(pos == 1) is_root = true;
        else is_root = false;
    }
    public boolean isRoot() { return is_root; }
    public int getNote() { return note; }
    public int getPos() { return position; }

}
