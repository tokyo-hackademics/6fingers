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
import com.fingers.six.elarm.common.QuestionList;
import com.fingers.six.elarm.common.Word;
import com.fingers.six.elarm.dbHandlers.MasterDbHandler;
import com.fingers.six.elarm.dbHandlers.WordListDbHandler;

import java.util.ArrayList;

/**
 * Created by tatung on 2015/05/17.
 */


public class QuestionListDetailSwipeAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private ArrayList<Word> values;
    private String querySearch = "";
    private String qstLstName;

    public QuestionListDetailSwipeAdapter(Context mContext, String qstLstName, String q) {
        this.mContext = mContext;
        querySearch = q;
        this.qstLstName = qstLstName;
        try {
            this.values = (ArrayList<Word>) (new WordListDbHandler(mContext, qstLstName)).search(querySearch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public QuestionListDetailSwipeAdapter(Context context, String qstLstName, ArrayList<Word> values, String querySearch) {
        this.mContext = context;
        this.values = values;
        this.qstLstName = qstLstName;
        this.querySearch = querySearch;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeQuestionListDetail;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_row_question_list_detail_swipe, null);

//        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
//        swipeLayout.setTag(position);

        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView lblWord = (TextView) convertView.findViewById(R.id.lblWord);
        lblWord.setText(values.get(position).get_eng());

        TextView lblMeaning = (TextView) convertView.findViewById(R.id.lblMeaning);
        lblMeaning.setText(values.get(position).get_jap());

        final SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, convertView.findViewById(R.id.bottomQuestionListDetailWrapper));

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
        convertView.findViewById(R.id.trash2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int delPos = (int) swipeLayout.getTag();

                (new WordListDbHandler(mContext, qstLstName)).deleteWord(values.get(delPos));
                try {
                    values = (ArrayList<Word>) (new WordListDbHandler(mContext, qstLstName)).search(querySearch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();

                Toast.makeText(mContext, "click trash " + swipeLayout.getTag(), Toast.LENGTH_SHORT).show();
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

