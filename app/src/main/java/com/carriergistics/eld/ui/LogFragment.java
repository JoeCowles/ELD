package com.carriergistics.eld.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.carriergistics.eld.R;
import com.carriergistics.eld.logging.HOSEvent;
import com.carriergistics.eld.logging.HOSEventCodes;
import com.carriergistics.eld.logging.HOSLog;
import com.carriergistics.eld.logging.HOSLogger;
import com.carriergistics.eld.logging.Status;
import com.carriergistics.eld.logging.TimePeriod;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static XAxis xAxis;
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
        graph = view.findViewById(R.id.graphingLog);
        Legend l = graph.getLegend();
        l.setEnabled(false);

        xAxis = graph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(0.01f);

        xAxis.setValueFormatter(new ValueFormatter() {

            private final SimpleDateFormat mFormat = new SimpleDateFormat("MMM/dd HH:mm", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value) {
                long millis = TimeUnit.MINUTES.toMillis((long)value);
                return mFormat.format(new Date(millis));
            }
        });
        YAxis leftAxis = graph.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(1f);
        leftAxis.setAxisMaximum(4f);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String sValue = "";
                switch((int) value){
                    case HOSEventCodes.OFF_DUTY:
                        sValue = "OFF_DUTY";
                        break;
                    case HOSEventCodes.SLEEPING:
                        sValue = "SLEEPING";
                        break;
                    case HOSEventCodes.DRIVING:
                        sValue = "DRIVING";
                        break;
                    case HOSEventCodes.ON_DUTY_NOT_DRIVING:
                        sValue = "FUELING";
                        break;
                }
                return sValue;
            }
        });
        YAxis rightAxis = graph.getAxisRight();
        rightAxis.setEnabled(false);
        setData(HOSLogger.getLog());
        return view;
    }
    private void setData(ArrayList<TimePeriod> eventLog) {
        ArrayList<Entry> values = new ArrayList<>();

        Date start = Calendar.getInstance().getTime();

        Date end = eventLog.get(eventLog.size()-2).getEndTime();
        int i = eventLog.size() -1;
        while(end == null){
            end = eventLog.get(i).getEndTime();
            i--;
            if(i == 0 && eventLog.get(0).getEndTime() == null){
                return;
            }
        }
        for(TimePeriod t : eventLog){
            if(t.getStartTime().before(start)){
                start = t.getStartTime();
            }else if(t.getEndTime() != null && t.getEndTime().after(end)){
                end = t.getEndTime();
            }
        }
        xAxis.mAxisRange = (float) ((end.getTime() - start.getTime()));
        if(eventLog != null){
            try{
                for(TimePeriod event : eventLog){

                    if(event.getDuration() == 0 || event.getDuration() > 300){
                        switch (event.getStatus()){
                            case DRIVING:
                                values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getStartTime().getTime()), HOSEventCodes.DRIVING));
                                if(event.getEndTime() != null){
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getEndTime().getTime()), HOSEventCodes.DRIVING));
                                }
                                break;
                            case STOPPED:
                                values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getStartTime().getTime()), HOSEventCodes.OFF_DUTY));
                                if(event.getEndTime() != null) {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getEndTime().getTime()), HOSEventCodes.OFF_DUTY));
                                }
                                break;
                            case FUELING:
                                values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getStartTime().getTime()), HOSEventCodes.ON_DUTY_NOT_DRIVING));
                                if(event.getEndTime() != null) {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getEndTime().getTime()), HOSEventCodes.ON_DUTY_NOT_DRIVING));
                                }
                                break;
                            case SLEEPING:
                                values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getStartTime().getTime()), HOSEventCodes.SLEEPING));
                                if(event.getEndTime() != null) {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getEndTime().getTime()), HOSEventCodes.SLEEPING));
                                }
                                break;
                        }
                    }


                    Log.d("DEBUGGING", "Found event: " + event.getStatus() + " " + event.getStartTime() + " - " + event.getEndTime());
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "Driver 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);
        // set data
        graph.setData(data);
        
    }

}