package com.fingers.six.elarm.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fingers.six.elarm.R;
import com.fingers.six.elarm.adapters.QuestionListDetailSwipeAdapter;
import com.fingers.six.elarm.common.Word;
import com.fingers.six.elarm.dbHandlers.WordListDbHandler;
import com.fingers.six.elarm.utils.DateTimeUtils;

import org.joda.time.LocalDateTime;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionListDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionListDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "question_list_name";

    // Question list name
    String qstLstName;

    // Views
    Button btnAdd;
    EditText txtAddWord;
    EditText txtAddMeaning;
    ListView lstWordList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionListDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionListDetailFragment newInstance(String param1, String param2) {
        QuestionListDetailFragment fragment = new QuestionListDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public QuestionListDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


            if (getArguments().containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                qstLstName = getArguments().getString(ARG_ITEM_ID);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question_list_detail, container, false);
        btnAdd = (Button) v.findViewById(R.id.btnAdd);
        txtAddWord = (EditText) v.findViewById(R.id.txtAddWord);
        txtAddMeaning = (EditText) v.findViewById(R.id.txtAddMeaning);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new WordListDbHandler(getActivity(), qstLstName))
                        .addWord(new Word(txtAddWord.getText().toString(), txtAddMeaning.getText().toString(), DateTimeUtils.convertToSeconds(new LocalDateTime())));
                lstWordList.setAdapter(new QuestionListDetailSwipeAdapter(getActivity(), qstLstName, ""));
                txtAddWord.setText("");
                txtAddMeaning.setText("");
            }
        });

        lstWordList = (ListView) v.findViewById(R.id.lstQuestionDetailList);
        lstWordList.setAdapter(new QuestionListDetailSwipeAdapter(getActivity(), qstLstName, ""));


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        Toast.makeText(getActivity(), "in onButtonPressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
