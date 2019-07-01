
package ch.esa.www.keepass.bean;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import ch.esa.www.keepass.R;

public class MainActivity extends AppCompatActivity {

    private ListView listView;


    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_CREATE = 333;
    private static final int MENU_ITEM_DELETE = 444;
    private static final int MENU_ITEM_COPY = 555;
    private static final int MENU_ITEM_COPYBN = 666;
    private static final int MY_REQUEST_CODE = 1000;

    private final List<Note> noteList = new ArrayList<Note>();
    ListAdapter lAdapter;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get ListView object from xml
        listView = findViewById(R.id.listView);
        final MyDatabaseHelper db = new MyDatabaseHelper(this);
        //delete Produktiv
        //db.createDefaultNotesIfNeed();
        //Lies alle Daten in die listViewAdapter
        final List<Note> list =  db.getAllNotes();
        this.noteList.addAll(list);
        // Auf der DB finden sich alle Titel zu den Verschlüsselungs Einträgen
        final String[] test = db.getAllNotesString();
        //URLButton = findViewById(R.id.urlButton);

        /*
        this.listViewAdapter = new ArrayAdapter<Note>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, this.noteList);

        // Assign adapter to ListView
        this.listView.setAdapter(this.listViewAdapter);

        // Register the ListView for Context menu
        registerForContextMenu(this.listView);
        */

        lAdapter = new CustomAdapterListview(MainActivity.this, list);
        //Setzt CustomAdapterListview
        this.listView.setAdapter(lAdapter);
        // Register the ListView for Context menu
        registerForContextMenu(this.listView);
        //onItem Click
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final List<Note> list =  db.getAllNotes();
                final Note selectedNote = list.get(i);
                /*
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(selectedNote.getNoteContent(), selectedNote.getNoteContent());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Passwort wurde Kopiert", Toast.LENGTH_LONG).show();
                */

                Intent intent = new Intent(this, activity_add_edit_note.class);

                intent.putExtra("note", selectedNote.getNoteUser());
                intent.putExtra("note2", selectedNote.getNoteContent());
                intent.putExtra("note3", selectedNote.getNoteTitle());

                startActivity(intent);
            }
        });



        /*
        String url = "market://details?id=<package_name>";

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
         */
/*
        Button mIdButtonHome = (imageView)findViewById(R.id.urlButton);
        mIdButtonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://android.okhelp.cz/category/software/"));
                startActivity(browserIntent);
            }
        });
*/
    }//OnCreate

    /**
     * @param menu
     * @param view
     * @param menuInfo
     */
    //beim draufklicken bei Items in Main
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderTitle("Aktion auswählen:");
        // groupId, itemId, order, title
        menu.add(0, MENU_ITEM_COPYBN, 1, "Benutzername Kopieren");
        menu.add(0, MENU_ITEM_EDIT, 2, "Eintrag bearbeiten");
        menu.add(0, MENU_ITEM_DELETE, 3, "Eintrag löschen");
    }


    /**
     * @param item
     * @return
     */
    //beim draufklicken bei Items Home
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();



        //Einmal befüllt, kann die Varieble kein anderes Ergebnis speichern. Ausser es wird erneut auch die ListView gklickt. //final
        //Kann selectedNoten nicht definieren.
        //String key = ((TextView) info.targetView).getText().toString();
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        final List<Note> list =  db.getAllNotes();
        final Note selectedNote = list.get(info.position);


        //https://www.programcreek.com/java-api-examples/android.widget.AdapterView.AdapterContextMenuInfo EXAMPLE 39
        //# Falls das Objekt nicht abgefüllt werden kann, muss dies halt im nachhinein geschehen. Dafür wurde eine Methode GetNote() entwickelt,
        //# um die Daten des Benutzers auszugebe, JE NACH INFO.POSITION also (Note.ID)
        //# Falsche behauptung das INFO.POSITION == Note.ID
        //MyDatabaseHelper dbHelper = null;
        //int NoteId = info.position;
        //final Note selectedNote = new Note(NoteId,"Titel","Passwort","Benutzer");
        //selectedNote.setNoteId(NoteId);
        //selectedNote = dbHelper.getNotefromSelectedNote(NoteId);
        //Toast.makeText(MainActivity.this, selectedNote.getNoteId(), Toast.LENGTH_SHORT).show();
        //Aktion ausführen, welche vorher definiert wurde (beim ankklicken der Listview)
        /*
        if (info.position == MENU_ITEM_VIEW) {
            Toast.makeText(getApplicationContext(), selectedNote.getNoteUser(), Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == MENU_ITEM_CREATE) {
            Intent intent = new Intent(this, activity_add_create_note.class);
            // Start AddEditNoteActivity, (with feedback).
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }

        */


            if (item.getItemId() == MENU_ITEM_EDIT) {
            Intent intent = new Intent(this, activity_add_edit_note.class);

            intent.putExtra("note", selectedNote.getNoteUser());
            intent.putExtra("note2", selectedNote.getNoteContent());
            intent.putExtra("note3", selectedNote.getNoteTitle());

            startActivity(intent);
        } else if (item.getItemId() == MENU_ITEM_DELETE) {
            // Ask before deleting.

            new AlertDialog.Builder(this)
                    .setMessage(selectedNote.getNoteTitle() + ". Sind Sie sicher dass Sie diesen Eintrag Löschen wollen ?")
                    .setCancelable(false)
                    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteNote(selectedNote);
                            //list.remove(info.position);
                            list.remove(selectedNote);
                            //¨Beim Löschen Item Löschen DANKE :)
                            lAdapter = new CustomAdapterListview(MainActivity.this, list);
                            //Setzt CustomAdapterListview
                            listView.setAdapter(lAdapter);
                            // Register the ListView for Context menu
                            registerForContextMenu(listView);

                        }
                    })
                    .setNegativeButton("Nein", null)
                    .show();
        }
            //else if Menu Copy
            /*
            else if (item.getItemId() == MENU_ITEM_COPY) {

            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(selectedNote.getNoteContent(), selectedNote.getNoteContent());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), selectedNote.getNoteContent(), Toast.LENGTH_LONG).show();

            }
            */
            //Benutzername Kopieren
            else if (item.getItemId() == MENU_ITEM_COPYBN){
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(selectedNote.getNoteUser(), selectedNote.getNoteUser());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Benutzername wurde Kopiert", Toast.LENGTH_LONG).show();
            }
        else {
            return false;
        }
        return true;

    }//onContextItemSelected


    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    // When AddEditNoteActivity completed, it sends feedback.
    // (If you start it using startActivityForResult ())
    // Wenn du zurück von der aufgerufenen Activity kommst.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
            boolean needRefresh = data.getBooleanExtra("needRefresh", true);
            // Refresh ListView
            final MyDatabaseHelper db = new MyDatabaseHelper(this);
            this.noteList.clear();
            //MyDatabaseHelper db = new MyDatabaseHelper(this);
            final List<Note> list =  db.getAllNotes();
            this.noteList.addAll(list);
            if (needRefresh) {
                /*
                this.noteList.clear();
                MyDatabaseHelper db = new MyDatabaseHelper(this);
                List<Note> list = db.getAllNotes();
                this.noteList.addAll(list);
                   */
                // Notify the data change (To refresh the ListView).
                //this.listViewAdapter.notifyDataSetChanged();
                final String[] test = db.getAllNotesString();

                MyDatabaseHelper db2 = new MyDatabaseHelper(this);
                this.noteList.clear();
                //MyDatabaseHelper db = new MyDatabaseHelper(this);

                this.noteList.addAll(list);
                //test = noteList.toArray(new String[0]);
            /*
                this.listViewAdapter = new ArrayAdapter<Note>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, this.noteList);

                // Assign adapter to ListView
                this.listView.setAdapter(this.listViewAdapter);

                // Register the ListView for Context menu
                registerForContextMenu(this.listView);
            */
                //final List<Note> list =  db.getAllNotes();
                //this.noteList.addAll(list);

                this.lAdapter = new CustomAdapterListview(MainActivity.this, list);

                listView.setAdapter(this.lAdapter);

                registerForContextMenu(listView);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final List<Note> list =  db.getAllNotes();

                        final Note selectedNote = list.get(i);
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText(selectedNote.getNoteContent(), selectedNote.getNoteContent());
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(getApplicationContext(), "Passwort wurde Kopiert", Toast.LENGTH_LONG).show();

                    }
                });
            }
        }
    }

    /**
     * onStart
     */
    //Wird gebraucht um autpmatisch zu Refresh
    @Override
    protected void onStart() {
        super.onStart();
        // Refresh ListView
        this.noteList.clear();
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        List<Note> list =  db.getAllNotes();
        this.noteList.addAll(list);
        // Notify the data change (To refresh the ListView).
        //this.listViewAdapter.notifyDataSetChanged();
    }


    /**
     * @param view
     */
    //Onclick Button
    public void newNote(View view) {
        //Note erstellen
        Intent intent = new Intent(this, activity_add_create_note.class);
        // Start AddEditNoteActivity, (with feedback).
        this.startActivityForResult(intent, MY_REQUEST_CODE);
    }

    /**
     * @param note
     */
    // Delete a record
    private void deleteNote(Note note) {
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        db.deleteNote(note);

        this.noteList.remove(note);


        //Refresh ListView.
        //this.listViewAdapter.notifyDataSetChanged();

    }

    /**
     * onDestroy
     */
    public void onDestroy() {
        super.onDestroy();
        setMySharedPrefLogonFalse(false);
    }

    /**
     * onPause
     */
    public void onPause(){
        super.onPause();
        setMySharedPrefLogonFalse(false);

        this.noteList.clear();
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        List<Note> list =  db.getAllNotes();
        this.noteList.addAll(list);
    }

    /**
     * onResume
     */
    public void onResume() {
        super.onResume();

        this.noteList.clear();
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        List<Note> list =  db.getAllNotes();
        this.noteList.addAll(list);
        // Refresh ListView.
        // this.listViewAdapter.notifyDataSetChanged();
    }

    /**
     * @param lv_isLogin
     */
    public void setMySharedPrefLogonFalse(boolean lv_isLogin) {
        SharedPreferences kaffePref = getSharedPreferences("KeePass", 0);
        SharedPreferences.Editor editor = kaffePref.edit();
        editor.putBoolean("lv_isLogin", lv_isLogin);
        editor.commit();
    }

    @Override
    public void onBackPressed() {

    }
    //Information Button Clicked
    public void openURL(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Möchsten Sie die App verlassen um die Benutzerdokumentation durchlesen?")
                .setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent browserIntent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://meineesa.atlassian.net/wiki/spaces/AK/pages/1003978754/Benutzerdokumentation"));
                        startActivity(browserIntent);
                    }
                })
                .setNegativeButton("Nein", null)
                .show();
    }
}