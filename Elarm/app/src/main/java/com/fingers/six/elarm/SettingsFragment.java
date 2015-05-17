package com.fingers.six.elarm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SharedPreferences sharedPreferences;
    private View mView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        sharedPreferences = getActivity().getSharedPreferences("Elarm", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_settings, container, false);
        RadioButton time_mode_1 = (RadioButton)mView.findViewById(R.id.setting_time_mode_1);
        RadioButton time_mode_2 = (RadioButton)mView.findViewById(R.id.setting_time_mode_2);
        RadioButton time_mode_3 = (RadioButton)mView.findViewById(R.id.setting_time_mode_3);

        CheckBox unlock_mode = (CheckBox)mView.findViewById(R.id.setting_unlock_alarm1);

        int time_mode = sharedPreferences.getInt("time_mode",-1);
        int unlock_or_not = sharedPreferences.getInt("unlock_or_not",-1);

        // Update the view to match the preferences
        switch(time_mode) {
            case 0:
                time_mode_1.toggle();
                break;

            case 1:
                time_mode_2.toggle();
                break;

            default:
                time_mode_3.toggle();
                break;
        }

        if((unlock_mode.isChecked() && unlock_or_not == 1) ||
                (!unlock_mode.isChecked() && unlock_or_not == 0)) {
            unlock_mode.toggle();
        }
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
