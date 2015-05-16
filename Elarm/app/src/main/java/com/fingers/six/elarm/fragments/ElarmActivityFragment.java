package com.fingers.six.elarm.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fingers.six.elarm.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class ElarmActivityFragment extends Fragment {

    public ElarmActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_elarm, container, false);
    }
}
