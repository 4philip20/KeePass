package ch.esa.www.keepass.bean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

import ch.esa.www.keepass.R;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;


public class activity_add_create_note extends AppCompatActivity {

    Note note;

    private int mode;
    private EditText textTitle;
    private EditText textContent;
    private EditText textUser;
    private boolean needRefresh;
    public boolean init;

    //Spnner
    String[] spinnerTitles;
    String[] spinnerBegriffe;
    String[] spinnerPopulation;
    int[] spinnerImages;
    Spinner mSpinner;
    private boolean isUserInteracting;
    String titel;

    String newString3 = null;

    public SeekBar seekBar;
    public TextView seekBartxt;
    public Switch Switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_create_note);

        init = true;
        String textTitle;

        this.textContent = (EditText) this.findViewById(R.id.text_note_content);
        this.textUser = (EditText) this.findViewById(R.id.text_note_user);
        this.seekBar = (SeekBar) findViewById(R.id.seekBar );
        this.seekBartxt = (TextView) findViewById(R.id.seekBartxt);

        mSpinner = (Spinner) findViewById(R.id.spinner);
        Switch = (Switch) findViewById(R.id.switch1);
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
            this.newString3 = extras.getString("note3");
        }
        //TODO WICHTIG LOGO
        spinnerTitles = new String[]{"Default","Outlook", "Facebook", "Instagram","Sap","Jira","Windows"};
        spinnerBegriffe = new String[]{"Default","Soziale Medien", "", "","Arbeitplatz","",""};
        //spinnerPopulation = new String[]{"Pojkar99", "207.7 Million", "1.379 Billion", "66.9 Million", "82.67 Million", "1.324 Billion", "4.773 Million", "60.6 Million", "127.5 Million", "37.95 Million"};
        //String halo = note.getNoteTitle();

        spinnerImages = new int[]{
                  R.mipmap.ic_launcher
                , R.mipmap.outlook_logo
                , R.mipmap.facebook_logo
                , R.mipmap.instagram_logo
                , R.mipmap.sap_logo
                , R.mipmap.jira_logo
                , R.mipmap.windows_logo

        };
        //Verkupplung spinner mit layout
        CustomAdapter mCustomAdapter = new CustomAdapter(activity_add_create_note.this, spinnerTitles, spinnerImages,spinnerBegriffe);
        mSpinner.setAdapter(mCustomAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isUserInteracting) {
                    //Toast.makeText(activity_add_edit_note.this, spinnerTitles[i], Toast.LENGTH_SHORT).show();
                    titel = spinnerTitles[i];
                    Toast.makeText(activity_add_create_note.this, "Sie haben "+titel+" ausgewählt", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //On Switch On make...
        Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!init){
                    seekBar.setVisibility(View.INVISIBLE);
                    seekBartxt.setVisibility(View.INVISIBLE);
                    init = true;
                    textContent.setTransformationMethod(new PasswordTransformationMethod());
                }else if (init){
                    SeekBar seekBar;
                    TextView seekBartxt;
                    seekBar = (SeekBar) findViewById(R.id.seekBar );
                    seekBartxt = (TextView) findViewById(R.id.seekBartxt);
                    seekBar.setVisibility(View.VISIBLE);
                    seekBartxt.setVisibility(View.VISIBLE);
                    init = false;
                    textContent.setTransformationMethod(new HideReturnsTransformationMethod());
                }
            }
        });



        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            // When Progress value changed.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                seekBartxt.setText(progress + " von " + seekBar.getMax() + " max. Zeichen");
                randomGenerator(progressValue);
                textContent.setText(randomGenerator(progressValue));
            }

            // Notification that the user has started a touch gesture.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            // Notification that the user has finished a touch gesture
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBartxt.setText(progress + " von " + seekBar.getMax() + " max. Zeichen");

            }
        });
    }//OnCreate

    //generates random String. Return is String value
    public static String randomGenerator(int i){
        final String charakter = "abcdefghijklmnopqrstuvwxyz0123456789?!";
        StringBuilder result = new StringBuilder();
        while (i>0){
            Random rand = new Random();
            result.append(charakter.charAt(rand.nextInt(charakter.length())));
            i--;
        }
        return result.toString();
    }

    // User Click on the Save button.
    public void buttonSaveClicked(View view) {
        //Datenbank einbinden
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        //Setzt Defulat wert falls Fehler
        String noteTitle = titel;
        if (noteTitle == null) {
            noteTitle = "Default";
        }
        //User daten definieren
        String noteContent = this.textContent.getText().toString();
        String noteUser = this.textUser.getText().toString();
        //User Input prüfen... IF false return zu eingabe
        if (noteTitle.equals("") || noteContent.equals("") || noteUser.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Bitte alle Felder angeben", Toast.LENGTH_LONG).show();
            return;
        }
        if (noteContent.length() <= 4){
            Toast.makeText(getApplicationContext(),
                    "Passwort ist nicht sicher genug", Toast.LENGTH_LONG).show();
            return;
        }

        //unterschied neuer Eintrag oder Eintrag Updaten
        boolean result = db.TitelExists(noteTitle);
        //if result == True  -> Eintrag kann nicht nochmals gespeichert werden.
        //if result == False -> Eintrag kann gespeichert werden.
        if (result) {
            Toast.makeText(getApplicationContext(),
                    "Sie haben " + noteTitle + " bereits Registirert", Toast.LENGTH_LONG).show();
            return;
        } else {
            //Falls Query false, also kein Eintrag gefunden
            this.note = new Note(noteTitle, noteContent, noteUser);
            db.addNote(note);
            //MainActivity reefreshen, um Daten anzuzeigen.
            this.needRefresh = true;
            // Back to MainActivity.
            this.onBackPressed();
        }

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




