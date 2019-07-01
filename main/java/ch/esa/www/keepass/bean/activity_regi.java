package ch.esa.www.keepass.bean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import ch.esa.www.keepass.R;

public class activity_regi extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 1000;
    public EditText txt_pw_regi;
    boolean lv_isLogin;
    boolean lv_isRegi;
    public Switch Switchregi;
    public Button btn_regi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lv_isRegi = getMySharedStatus();

        if(lv_isRegi){
            //GO TO LOGIN
            //Intent starten
            Intent intent = new Intent(this, activity_login.class);
            // Start AddEditNoteActivity, (with feedback).
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }else{
            //GO TO REGI
            //Intent starten
            setContentView(R.layout.activity_regi);
        }


        Switchregi = (Switch) findViewById(R.id.switchregi);
        txt_pw_regi = (EditText) this.findViewById(R.id.txt_pw_regi);


        //On Switch On make...
/*
        Switchregi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked){
                    txt_pw_regi.setTransformationMethod(new PasswordTransformationMethod());
                }else if (isChecked){
                    txt_pw_regi.setTransformationMethod(new HideReturnsTransformationMethod());
                }

            }
        });
*/

    }

    public void registrieren(View view) {
        boolean lv_isLogin = false;
        boolean lv_isRegi = false;
        this.txt_pw_regi = (EditText) this.findViewById(R.id.txt_pw_regi);
        //String lv_MasterPW = this.txt_pw.getText().toString();

        if (this.txt_pw_regi.getText().toString().length() <= 5) {
            //Fehlerausgabe
            Toast.makeText(getApplicationContext(),this.txt_pw_regi.getText()+ " ist zu kurz",Toast.LENGTH_LONG).show();
            return;
        }else{
            //Registrieren
            lv_isRegi = true;
            setMySharedPref(lv_isLogin,lv_isRegi,this.txt_pw_regi.getText().toString());

            Intent intent = new Intent(this, activity_login.class);
            // Start AddEditNoteActivity, (with feedback).
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }
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

    /*
    private void getMySharedPref() {
        SharedPreferences kaffePref = getSharedPreferences("KeePass", 0);
        lv_isLogin = kaffePref.getBoolean("lv_isLogin", false);
        lv_isRegi = kaffePref.getBoolean("lv_isRegi", false);
        lv_MasterPw = kaffePref.getString("lv_MasterPw", "test");
    }
    */

    private boolean getMySharedStatus() {
        SharedPreferences kaffePref = getSharedPreferences("KeePass", 0);
        lv_isRegi = kaffePref.getBoolean("lv_isRegi",false);
        return lv_isRegi;
    }

    @Override
    public void onBackPressed() {

    }
}
