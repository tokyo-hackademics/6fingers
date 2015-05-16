package com.fingers.six.elarm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.fingers.six.elarm.R;
import com.fingers.six.elarm.dbHandlers.MasterDbHandler;
import com.fingers.six.elarm.common.QuestionList;

import java.util.ArrayList;

/**
 * Created by tatung on 2015/05/16.
 */

public class QuestionListSwipeAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private ArrayList<QuestionList> values;

    public QuestionListSwipeAdapter(Context mContext, String q) {
        this.mContext = mContext;
        this.values = (ArrayList<QuestionList>) (new MasterDbHandler(mContext)).search(q);
    }

    public QuestionListSwipeAdapter(Context context, ArrayList<QuestionList> values) {
        this.mContext = context;
        this.values = values;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeQuestionList;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_row_question_list_swipe, null);

//        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
//        swipeLayout.setTag(position);

        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView textView = (TextView) convertView.findViewById(R.id.lblQuestionListName);
        textView.setText(values.get(position).get_name());
        TextView lblGreen = (TextView) convertView.findViewById(R.id.lblGreen);
        lblGreen.setText(values.get(position).get_status()[0] + "");
        TextView lblYellow = (TextView) convertView.findViewById(R.id.lblYellow);
        lblYellow.setText(values.get(position).get_status()[1] + "");
        TextView lblRed = (TextView) convertView.findViewById(R.id.lblRed);
        lblGreen.setText(values.get(position).get_status()[2] + "");

        final SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setTag(position);
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int delPos = (int) swipeLayout.getTag();

                (new MasterDbHandler(mContext)).deleteList(values.get(delPos));
                values = (ArrayList<QuestionList>) (new MasterDbHandler((mContext))).search("");
                notifyDataSetChanged();
                Toast.makeText(mContext, "click delete" + swipeLayout.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

