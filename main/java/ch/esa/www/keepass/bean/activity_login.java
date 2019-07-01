package ch.esa.www.keepass.bean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ch.esa.www.keepass.R;

public class activity_login extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 1000;
    public EditText txt_pw;
    boolean lv_isLogin;
    boolean lv_isRegi;
    public String lv_MasterPw;
    public boolean init;
    public Switch Switch;
    public boolean isChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lv_isLogin = getMySharedAccess();
        if(lv_isLogin){
            Intent intent = new Intent(this, MainActivity.class);
            // Start AddEditNoteActivity, (with feedback).
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }else{
            setContentView(R.layout.activity_login);
        }
        //this.txt_pw = (EditText) this.findViewById(R.id.login_pw);
        Switch = (Switch) findViewById(R.id.switchOnOff);
        txt_pw = (EditText) this.findViewById(R.id.login_pw);


        //TODO behebe same Problem wie bei Registrieren
        //On Switch On make...

        Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked){
                    txt_pw.setTransformationMethod(new PasswordTransformationMethod());
                }else if (isChecked){
                    txt_pw.setTransformationMethod(new HideReturnsTransformationMethod());
                }
            }
        });


    }

    public void login(View view) {
        txt_pw = (EditText) this.findViewById(R.id.login_pw);

        if(getMySharedMasterPw().equals(this.txt_pw.getText().toString())) {
            //Login erfolgreich geprüft
            //Preferences Speichern
            lv_isLogin = true;
            setMySharedPref(lv_isLogin,lv_isRegi,txt_pw.getText().toString());
            //Intent starten
            Intent intent = new Intent(this, MainActivity.class);
            // Start AddEditNoteActivity, (with feedback).
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }else{
            //Login stimmt nicht überein
            Toast.makeText(getApplicationContext(),
                    "Ungültiges Passwort", Toast.LENGTH_LONG).show();
            return;
        }
    }


    private String getMySharedMasterPw() {
        SharedPreferences kaffePref = getSharedPreferences("KeePass", 0);
        lv_isLogin = kaffePref.getBoolean("lv_isLogin", false);
        lv_isRegi = kaffePref.getBoolean("lv_isRegi", false);
        lv_MasterPw = kaffePref.getString("lv_MasterPw", "test");

        return lv_MasterPw;
    }

    private Boolean getMySharedAccess() {
        SharedPreferences kaffePref = getSharedPreferences("KeePass", 0);
        lv_isLogin = kaffePref.getBoolean("lv_isLogin", false);
        return lv_isLogin;
    }

    // meine Sharde Preferenzen zur App Kaffee einlesen.
    //funktioniert nur wen man beim Mainactivity ist und kann nicht als aufruf von BW genutzt werden.
    public void setMySharedPref(boolean lv_isLogin, boolean lv_isRegi,  String lv_MasterPw) {
        SharedPreferences kaffePref = getSharedPreferences("KeePass", 0);
        SharedPreferences.Editor editor = kaffePref.edit();
        editor.putBoolean("lv_isLogin", lv_isLogin);
        editor.putBoolean("lv_isRegi", lv_isRegi);
        editor.putString("lv_MasterPw", lv_MasterPw);
        editor.commit();
    }

    @Override
    public void onBackPressed() {

    }

}
