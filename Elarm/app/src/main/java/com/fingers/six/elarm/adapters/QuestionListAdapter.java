package com.fingers.six.elarm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fingers.six.elarm.R;
import com.fingers.six.elarm.common.QuestionList;

/**
 * Created by tatung on 2015/05/16.
 */

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

import java.util.ArrayList;

public class QuestionListAdapter extends ArrayAdapter<QuestionList> {
    private final Context context;
    private final ArrayList<QuestionList> values;

    public QuestionListAdapter(Context context, ArrayList<QuestionList> values) {
        super(context, R.layout.list_row_question_list);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row_question_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.lblQuestionListName);
        textView.setText(values.get(position).get_name());
        TextView lblGreen = (TextView)rowView.findViewById(R.id.lblGreen);
        lblGreen.setText(values.get(position).get_status()[0] + "");
        TextView lblYellow = (TextView)rowView.findViewById(R.id.lblYellow);
        lblYellow.setText(values.get(position).get_status()[1] + "");
        TextView lblRed = (TextView)rowView.findViewById(R.id.lblRed);
        lblGreen.setText(values.get(position).get_status()[2] + "");
        return rowView;
    }

    @Override
    public int getCount(){
        return values.size();
    }
}
