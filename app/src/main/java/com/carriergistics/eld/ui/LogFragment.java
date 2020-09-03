package com.carriergistics.eld.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carriergistics.eld.R;
import com.carriergistics.eld.utils.DataLogger;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static LineChart graph;
    private static LineData data;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogFragment newInstance(String param1, String param2) {
        LogFragment fragment = new LogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        graph = view.findViewById(R.id.logGraph);
        data = new LineData();
        data.setValueTextColor(Color.WHITE);
        graph.setData(data);
        graph.setDescription(null);
        Legend legend = graph.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextColor(Color.BLUE);
        XAxis x = graph.getXAxis();
        x.setTextColor(Color.BLUE);
        x.setAvoidFirstLastClipping(true);
        YAxis y = graph.getAxisLeft();
        y.setAxisMaximum(4f);
        ArrayList<Entry> log = DataLogger.requestGraph();
        if(log != null) {
            for (Entry entry : log) {
                addEntry(entry);
            }
        }
        return view;
    }
    private static void addEntry(Entry entry){
        if(data != null){
            LineDataSet set = (LineDataSet) data.getDataSetByIndex(0);
            if(set == null){
                set = createSet();
                data.addDataSet(set);
            }
            data.addEntry(entry, 0);
            graph.notifyDataSetChanged();
            graph.setVisibleXRange(0, 10);
            graph.moveViewToX(data.getXMin() + 9);
        }
    }
    private static LineDataSet createSet(){
        LineDataSet set = new LineDataSet(null, "Driver");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(ColorTemplate.getHoloBlue());
        set.setLineWidth(2f);
        set.setCircleSize(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        return set;
    }
}