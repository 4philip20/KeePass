package ch.esa.www.keepass.bean;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import ch.esa.www.keepass.R;


public class activity_add_edit_note extends AppCompatActivity {

    Note note;

    private int mode;

    private EditText textContent;
    private EditText textUser;
    private ImageView noteBild;
    private EditText textTitel;
    private boolean needRefresh;
    private EditText TitelBefore;

    String stringTitelBefore;
    //Spnner
    String[] spinnerTitles;
    String[] spinnerPopulation;
    int[] spinnerImages;
    Spinner mSpinner;
    private boolean isUserInteracting;
    String titel;


    final Note selectedNote = new Note();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        this.textContent = (EditText) this.findViewById(R.id.text_note_content);
        this.textUser = (EditText) this.findViewById(R.id.text_note_user);
        this.textTitel = (EditText) this.findViewById(R.id.titel);
        this.noteBild = (ImageView) this.findViewById(R.id.noteBild);

        //Intent intent = this.getIntent();
        //this.note = (Note) intent.getSerializableExtra("note");
        String newString = null;
        String newString2 = null;
        String newString3 = null;

        //Problem mit Java Konvention...
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            newString = extras.getString("note");
            newString2 = extras.getString("note2");
            newString3 = extras.getString("note3");
            stringTitelBefore = extras.getString("note3");
        }

        //if(note== null)  {

        //this.textTitle.setText(note.getNoteTitle());
        //this.textTitle.setText(titel);
        //this.textContent.setText(note.getNoteContent());
        this.textUser.setText(newString);
        this.textContent.setText(newString2);
        this.textTitel.setText(newString3);

        /*
        if (newString3.equals("Facebook")) {
            this.noteBild.setImageResource(R.drawable.facebook);
        } else if (newString3.equals("Instagram")) {
            this.noteBild.setImageResource(R.drawable.facebook);
        } else {
            this.noteBild.setImageResource(R.drawable.outlook);
        }
        */
        //str1.toLowerCase().contains(str2.toLowerCase())
        //TODO WICHTIG LOGO
        if (newString3.toLowerCase().contains("facebook")){
            this.noteBild.setImageResource(R.drawable.facebook);
        }else if (newString3.toLowerCase().contains("instagram")){
            this.noteBild.setImageResource(R.drawable.instagram);
        }else if (newString3.toLowerCase().contains("outlook")){
            this.noteBild.setImageResource(R.mipmap.outlook_logo);
        }else if (newString3.toLowerCase().contains("windows")){
            this.noteBild.setImageResource(R.mipmap.windows_logo);
        }else if (newString3.toLowerCase().contains("sap")){
            this.noteBild.setImageResource(R.mipmap.sap_logo);
        }else if (newString3.toLowerCase().contains("jira")){
            this.noteBild.setImageResource(R.mipmap.jira_logo);
        }


        else {
            this.noteBild.setImageResource(R.mipmap.ic_launcher);
        }


        //this.spinnerImages.set(R.drawable.facebook);

        //this.textUser.setText(note.getNoteUser());
            /*
            if (note.getNoteUser() == " "){
                Toast.makeText(getApplicationContext(),"User ist Leer",Toast.LENGTH_LONG).show();
            }
        */
        //this.textUser.setText("Test");

        spinnerTitles = new String[]{"Outlook", "Facebook", "Instagram"};
        //spinnerPopulation = new String[]{"Pojkar99", "207.7 Million", "1.379 Billion", "66.9 Million", "82.67 Million", "1.324 Billion", "4.773 Million", "60.6 Million", "127.5 Million", "37.95 Million"};
        //String halo = note.getNoteTitle();

        spinnerImages = new int[]{
                R.mipmap.outlook_logo
                , R.drawable.facebook
                , R.drawable.instagram
        };
        //Verkupplung spinner mit layout


        //deklaraation selected Note
        this.selectedNote.setNoteTitle(newString3);
        this.selectedNote.setNoteContent(newString2);
        this.selectedNote.setNoteUser(newString);







    }//OnCreate

    // User Click on the Save button.
    public void buttonEditClicked(View view) {
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        //String noteTitle = this.textTitle.getText().toString();
        //String noteTitle = titel;

        //password
        String noteContent = this.textContent.getText().toString();
        //user
        String noteUser = this.textUser.getText().toString();
        //Outlook..Facebook...
        String noteTitle = this.textTitel.getText().toString();

        //String noteTitelBefore = TitelBefore.getText().toString();

        //Fehler
        note = new Note();
        this.note.setNoteTitle(noteTitle);
        this.note.setNoteContent(noteContent);
        this.note.setNoteUser(noteUser);
        db.updateNote(note,stringTitelBefore);

        this.needRefresh = true;

        // Back to MainActivity.
        this.onBackPressed();
    }

    // User Click on the Cancel button.
    public void buttonCancelClicked(View view) {
        // Do nothing, back MainActivity.
        this.onBackPressed();
    }

    // When completed this Activity,
    // Send feedback to the Activity called it.
    @Override
    public void finish() {
        // Create Intent
        Intent data = new Intent();
        // Request MainActivity refresh its ListView (or not).
        data.putExtra("needRefresh", needRefresh);
        // Set Result
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }

    //Spinner
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        isUserInteracting = true;
    }

}