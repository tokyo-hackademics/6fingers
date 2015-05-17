package com.fingers.six.elarm;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fingers.six.elarm.adapters.HistoryListAdapter;
import com.fingers.six.elarm.common.HistoryItem;
import com.fingers.six.elarm.dbHandlers.HistoryDbHandler;
import com.fingers.six.elarm.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    // Views
    ListView lstHistory;
    LineChartView chart;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    LineChartData lineData;

    private void generateInitialLineData() {


        // For build-up animation you have to disable viewport recalculation.
        chart.setViewportCalculationEnabled(false);

        // And set initial max viewport and current viewport- remember to set viewports after data.
        Viewport v = new Viewport(0, 110, 6, 0);
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);

        chart.setZoomType(ZoomType.HORIZONTAL);
    }

    private void generateLineData(int color, float range) {



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);


        lstHistory = (ListView) v.findViewById(R.id.lstHistory);
        chart = (LineChartView) v.findViewById(R.id.chart);
        this.generateInitialLineData();

        lstHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<HistoryItem> chartList = (ArrayList<HistoryItem>) (new HistoryDbHandler(getActivity()))
                        .searchByDateAndName(0, ((HistoryItem) lstHistory.getAdapter().getItem(position)).get_questionName());

                // Cancel last animation if not finished.
                chart.cancelDataAnimation();

                int numValues = chartList.size();

                List<AxisValue> axisValues = new ArrayList<AxisValue>();
                List<PointValue> values = new ArrayList<PointValue>();
                for (int i = 0; i < numValues; ++i) {
                    values.add(new PointValue(i, (float) chartList.get(i).get_score()));
                    axisValues.add(new AxisValue(i).setLabel(DateTimeUtils.convertDaysToString(chartList.get(i).get_date())));
                }

                Line line = new Line(values);

                List<Line> lines = new ArrayList<Line>();
                lines.add(line);

                lineData = new LineChartData(lines);
                lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
                lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

                // Modify data targets
                line = lineData.getLines().get(0);// For this example there is always only one line.
                line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);



//                lineData.setLines(lines);

                chart.setLineChartData(lineData);

                for (PointValue value : line.getValues()) {
                    // Change target only for Y value.
                    value.setTarget(value.getX(), value.getY());
                }

                // Start new data animation with 300ms duration;
                chart.startDataAnimation(300);
            }


        });


        lstHistory.setAdapter(new HistoryListAdapter(this.getActivity(), ""));

//        List<PointValue> values = new ArrayList<PointValue>();
//        values.add(new PointValue(0, 2));
//        values.add(new PointValue(1, 4));
//        values.add(new PointValue(2, 3));
//        values.add(new PointValue(3, 4));
//
//        //In most cased you can call data model methods in builder-pattern-like manner.
//        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
//        List<Line> lines = new ArrayList<Line>();
//        lines.add(line);
//
//        LineChartData data = new LineChartData();
//        data.setLines(lines);
//
//        chart.setLineChartData(data);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
