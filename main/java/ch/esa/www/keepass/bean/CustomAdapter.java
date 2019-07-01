package ch.esa.www.keepass.bean;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ch.esa.www.keepass.R;


public class CustomAdapter extends ArrayAdapter<String> {

    String[] spinnerTitles;
    String[] spinnerBegriffe;
    int[] spinnerImages;
    //String[] spinnerPopulation;
    Context mContext;

    public CustomAdapter(@NonNull Context context, String[] titles, int[] images,String[] begriffe) {
        super(context, R.layout.custom_spinner_row);
        this.spinnerTitles = titles;
        this.spinnerImages = images;
        this.spinnerBegriffe = begriffe;
        //this.spinnerPopulation = population;
        this.mContext = context;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return spinnerTitles.length;
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.custom_spinner_row, parent, false);
            mViewHolder.mFlag = (ImageView) convertView.findViewById(R.id.ivFlag);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.tvName);
            mViewHolder.mBegriff = (TextView) convertView.findViewById(R.id.tvbegriff);
            //mViewHolder.mPopulation = (TextView) convertView.findViewById(R.id.tvPopulation);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mFlag.setImageResource(spinnerImages[position]);
        mViewHolder.mName.setText(spinnerTitles[position]);

        if (spinnerBegriffe[position].isEmpty()){
            mViewHolder.mBegriff.setText("Leer");
            //mit Gone wird as Element nicht nur auf Invisible gesetzt, sondarn das ganze Element wird gelöscht und die anderen Elemente verschieben sich. So wird Platz geschaffen.
            mViewHolder.mBegriff.setVisibility(TextView.GONE);
        }
        else{
            mViewHolder.mBegriff.setText(spinnerBegriffe[position]);
        }

        return convertView;
    }

    /**
     * static class no Return
     */
    private static class ViewHolder {
        ImageView mFlag;
        TextView mName;
        TextView mBegriff;
        //TextView mPopulation;
    }
}
