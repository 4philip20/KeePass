package ch.esa.www.keepass.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import ch.esa.www.keepass.bean.Note;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    private final int anzahlElemente = getNotesCount();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Note_DB";

    // Table name: Note.
    private static final String TABLE_NOTE = "Note";
    //Steht für ID
    private static final String COLUMN_NOTE_ID = "Note_Id";
    //Steht für Titel, also Facebook...123456
    private static final String COLUMN_NOTE_TITLE = "Note_Title";
    //Steht für das Passwort
    private static final String COLUMN_NOTE_CONTENT = "Note_Content";
    //Steht für den Benutzername
    private static final String COLUMN_NOTE_USER = "Note_User";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script.
        String script = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NOTE_TITLE + " TEXT,"
                + COLUMN_NOTE_CONTENT + " TEXT,"
                + COLUMN_NOTE_USER + " TEXT" + ")";
        // Execute Script.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);

        // Create tables again
        onCreate(db);
    }


    //Produktiv löschen
    public void createDefaultNotesIfNeed() {
        int count = this.getNotesCount();
        if (count == 0) {
            Note note1 = new Note("Facebook",
                    "Passwort", "Username");
            this.addNote(note1);
            Note note2 = new Note("Instagram",
                    "Passwort", "Username");
            this.addNote(note2);
            Note note3 = new Note("Outlook",
                    "Passwort", "Username");
            this.addNote(note3);

        }
    }
    //Get Note ID from Selected Note
    public Note getNotefromSelectedNote(int id) {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTE, new String[]{COLUMN_NOTE_ID,
                        COLUMN_NOTE_TITLE, COLUMN_NOTE_CONTENT, COLUMN_NOTE_USER}, COLUMN_NOTE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return note;
    }


    public void addNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + note.getNoteTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, note.getNoteTitle());
        values.put(COLUMN_NOTE_CONTENT, note.getNoteContent());
        values.put(COLUMN_NOTE_USER, note.getNoteUser());

        // Inserting Row
        db.insert(TABLE_NOTE, null, values);

        // Closing database connection
        db.close();
    }


    public Note getNote(int id) {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTE, new String[]{COLUMN_NOTE_ID,
                        COLUMN_NOTE_TITLE, COLUMN_NOTE_CONTENT, COLUMN_NOTE_USER}, COLUMN_NOTE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return note;
    }


    public List<Note> getAllNotes() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... ");

        List<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteId(Integer.parseInt(cursor.getString(0)));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteContent(cursor.getString(2));
                note.setNoteUser(cursor.getString(3));
                // Adding note to list OnCreate
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        // return note list
        return noteList;
    }

    public String[] getAllNotesString() {
        // Variablen...
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... ");
        String noteList[] = new String[anzahlElemente];
        int rowNum = 0;

        // Eintrage werden mit cursor aus der BG gelesen - Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        /*
        // wenn einträge in cursoer gefunden werden ..
        if (cursor.moveToFirst()) {
            //Falls mehrere Cursoreintröge gefunden werden
            do {
                noteList[rowNum++] = cursor.getString(1);
            }
            while (cursor.moveToNext());

            cursor.moveToFirst();

            while ( cursor.isAfterLast()) {
                noteList[rowNum++] = cursor.getString(1);
            }

        }
        */
        //https://stackoverflow.com/questions/10723770/whats-the-best-way-to-iterate-an-android-cursor
        try {
            while (cursor.moveToNext()){
               noteList[rowNum++] = cursor.getString(1);
            }
        } finally {
            cursor.close();
        }
        // return note list
        return noteList;
    }


    public int getNotesCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... ");

        String countQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();
        // return count
        return count;
    }


    public boolean TitelExists(String value) {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... ");
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlQuery = "SELECT * FROM " + TABLE_NOTE + " WHERE " + COLUMN_NOTE_TITLE + " = " + "'" + value + "'";
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SqlQuery, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            return true;
        }
        return false;
    }

    public boolean updateNote(Note note,String Titelbefore) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getNoteTitle());
        //Deklaration für SQL Abfrage
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, note.getNoteTitle());
        values.put(COLUMN_NOTE_CONTENT, note.getNoteContent());
        values.put(COLUMN_NOTE_USER, note.getNoteUser());
        //Variablen definition für SQL Query
        String note_Titel = note.getNoteTitle();
        String note_Content = note.getNoteContent();
        String note_User = note.getNoteUser();

        /*
        int update(String table, ContentValues values,
                   String whereClause, String[] whereArgs)
         */

        db.execSQL("UPDATE Note  " +
                "SET Note_Title ='"+ note_Titel+"', Note_Content='"+ note_Content+"',Note_User='"+ note_User+"' " +
                "WHERE Note_Title ='"+Titelbefore+"'");

        db.close();
        return true;
    }

    public void deleteNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getNoteTitle());
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, COLUMN_NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getNoteId())});
        db.close();
    }


}