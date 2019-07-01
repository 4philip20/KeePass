package ch.esa.www.keepass.bean;

import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ch.esa.www.keepass.R;
import ch.esa.www.keepass.bean.Note;


public class CustomAdapterListview extends BaseAdapter {

    Context context;
    public final List<Note> fruitname;
    //private final  int[] image;
    //private final String [] values;

    public CustomAdapterListview(@NonNull Context context, List<Note> fruitname) {
        this.fruitname = fruitname;
        //this.image = image;
        this.context = context;
        //this.values = values;
    }

    @Override
    public int getCount() {
        return fruitname.size();

        //return fruitname.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        final View result;
        //Hier werden daten abgefüllt.

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_listview_row, parent, false);
            viewHolder.rollename = (TextView) convertView.findViewById(R.id.rollename);
            viewHolder.rollenameUser = (TextView) convertView.findViewById(R.id.rollename2);
            viewHolder.rolle = (ImageView) convertView.findViewById(R.id.rolle);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        //fruitname ist ein Interface und kein Objekt
        //List<Note> set text
        //viewHolder.rollename.setText("");
        //viewHolder.rollenameUser.setText("");
        //viewHolder.rollename.setText(fruitname);

        //viewHolder.rollenameUser.setText(this.fruitname);
        //viewHolder.rollename.setText(fruitname[position].getNoteTitle());

        MyDatabaseHelper db = new MyDatabaseHelper(this.context);
        List<Note> list =  db.getAllNotes();
        //viewHolder.rollename.setText(fruitname);
        viewHolder.rollename.setText(list.get(position).getNoteTitle());
        viewHolder.rollenameUser.setText(list.get(position).getNoteUser());

        //Dieser String wird für die Bildausgabe gebraucht, Sie prüft ob der Name einem Nild zugeordnet werden kann.
        String newString;
        newString = viewHolder.rollename.getText().toString();
        //TODO WICHTIG LOGO
        if (newString.toLowerCase().contains("facebook")){
            viewHolder.rolle.setImageResource(R.drawable.facebook);

        }else if (newString.toLowerCase().contains("instagram")){
            viewHolder.rolle.setImageResource(R.drawable.instagram);

        }else if (newString.toLowerCase().contains("outlook")){
            viewHolder.rolle.setImageResource(R.mipmap.outlook_logo);
        }else if (newString.toLowerCase().contains("sap")){
            viewHolder.rolle.setImageResource(R.mipmap.sap_logo);
        }else if (newString.toLowerCase().contains("windows")){
            viewHolder.rolle.setImageResource(R.mipmap.windows_logo);
        }else if (newString.toLowerCase().contains("jira")){
            viewHolder.rolle.setImageResource(R.mipmap.jira_logo);
        }







        else {
            viewHolder.rolle.setImageResource(R.mipmap.ic_launcher);
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView rolle;
        TextView rollename;
        TextView rollenameUser;
    }




}

