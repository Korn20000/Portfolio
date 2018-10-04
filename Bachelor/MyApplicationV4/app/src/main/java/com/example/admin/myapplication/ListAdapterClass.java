package com.example.admin.myapplication;

/**
 * Created by KoRn on 27-11-2017.
 */


import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.List;

import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.security.auth.Subject;

public class ListAdapterClass extends ArrayAdapter<PatientInformation> {

    private Context context;
    private List<Subject> valueList;

    public ListAdapterClass(@NonNull Context context, @NonNull List<PatientInformation> objects) {
        super(context, 0, objects);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewItem viewItem = null;

        if(convertView == null)
        {
            viewItem = new ViewItem();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_items,parent,false);

            viewItem.TextViewMeasuredLevel = convertView.findViewById(R.id.bloodLevel);
            viewItem.TextViewDate = convertView.findViewById(R.id.dateLevel);

            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        PatientInformation patientInformation= getItem(position);

        viewItem.TextViewMeasuredLevel.setText(patientInformation.getMeasuredLevel() + "");
        String reformatedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(patientInformation.getDate());
        viewItem.TextViewDate.setText(reformatedDate + "");

        return convertView;
    }
}

class ViewItem
{
   public TextView TextViewMeasuredLevel;
   public TextView TextViewDate;

}
