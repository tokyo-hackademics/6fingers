package com.fingers.six.elarm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private String querySearch = "";

    public QuestionListSwipeAdapter(Context mContext, String q) {
        this.mContext = mContext;
        querySearch = q;
        this.values = (ArrayList<QuestionList>) (new MasterDbHandler(mContext)).search(querySearch);
    }

    public QuestionListSwipeAdapter(Context context, ArrayList<QuestionList> values, String querySearch) {
        this.mContext = context;
        this.values = values;
        this.querySearch = querySearch;
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

        Button btnGreen = (Button) convertView.findViewById(R.id.btnGreen);
        btnGreen.setText(values.get(position).get_status()[0] + "");
        btnGreen.setClickable(false);

        Button btnYellow = (Button) convertView.findViewById(R.id.btnYellow);
        btnYellow.setText(values.get(position).get_status()[1] + "");
        btnYellow.setClickable(false);

        Button btnRed = (Button) convertView.findViewById(R.id.btnRed);
        btnRed.setText(values.get(position).get_status()[2] + "");
        btnRed.setClickable(false);

        final SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setTag(position);
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
//                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
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
                values = (ArrayList<QuestionList>) (new MasterDbHandler((mContext))).search(querySearch);
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

