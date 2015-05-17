package com.fingers.six.elarm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fingers.six.elarm.R;
import com.fingers.six.elarm.common.HistoryItem;
import com.fingers.six.elarm.common.QuestionList;
import com.fingers.six.elarm.dbHandlers.HistoryDbHandler;
import com.fingers.six.elarm.dbHandlers.MasterDbHandler;

import org.joda.time.LocalDate;

import java.util.ArrayList;

/**
 * Created by tatung on 2015/05/17.
 */
public class HistoryListAdapter extends ArrayAdapter<HistoryItem> {
    private final Context context;
    private final ArrayList<HistoryItem> values;

    public HistoryListAdapter(Context context, ArrayList<HistoryItem> values) {
        super(context, R.layout.list_row_history);
        this.context = context;
        this.values = values;
    }

    public HistoryListAdapter(Context context, String q){
        super(context, R.layout.list_row_history);
        this.context = context;
        this.values = (ArrayList<HistoryItem>)(new HistoryDbHandler(context)).getAllHistory();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row_history, parent, false);
        TextView lblDate = (TextView) rowView.findViewById(R.id.lblDate);
        lblDate.setText((new LocalDate(2000, 1, 1).plusDays((int)values.get(position).get_date())).toString());
        TextView lblQstLstName = (TextView)rowView.findViewById(R.id.lblQstLstName);
        lblQstLstName.setText(values.get(position).get_questionName() + "");
        TextView lblScore = (TextView)rowView.findViewById(R.id.lblScore);
        lblScore.setText(values.get(position).get_score() + "");

        return rowView;
    }

    @Override
    public int getCount(){
        return values.size();
    }

    public HistoryItem getItem(int position){
        return values.get(position);
    }
}
