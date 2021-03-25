package com.carriergistics.eld.editlog;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;
import com.carriergistics.eld.logging.Day;
import com.carriergistics.eld.logging.HOSEventCodes;
import com.carriergistics.eld.logging.HOSLogger;
import com.carriergistics.eld.logging.TimePeriod;
import com.carriergistics.eld.utils.DataConverter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogViewerFragment extends Fragment {

    static int id = 300000;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogViewerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogViewerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogViewerFragment newInstance(String param1, String param2) {
        LogViewerFragment fragment = new LogViewerFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_viewer, container, false);
        LinearLayout list = view.findViewById(R.id.logList);
        if (MainActivity.currentDriver.getDays() != null && MainActivity.currentDriver.getDays().size() > 0) {
            list.setPadding(30, 10, 30, 10);
            for (int i = MainActivity.currentDriver.getDays().size() - 1; i >= 0; i--) {
                Day day = MainActivity.currentDriver.getDays().get(i);
                day.updateTimePeriods();
                // Card holds all the info
                CardView card = new CardView(getContext());
                card.setForegroundGravity(Gravity.CENTER);

                ConstraintLayout con = new ConstraintLayout(getContext());
                ConstraintSet set = new ConstraintSet();
                set.clone(con);
                con.setId(id);
                id++;

                final TextView dateView = new TextView(getContext());
                dateView.setId(id);
                id++;
                dateView.setText("  " + day.getDate().toString().substring(0, 10) + ", " + day.getDate().toString().substring(24, 28));
                dateView.setTextSize(35);
                con.addView(dateView);

                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleDateFormat sdf = new SimpleDateFormat("  EEE MMM dd, yyyy");
                        Date date = MainActivity.getTime();
                        try {
                            date = sdf.parse(dateView.getText().toString());
                            Log.d("DEBUGGING", "Opening log with the date: " + date.toString());
                            if (DataConverter.sameDayNoTime(date, MainActivity.getTime())) {
                                MainActivity.instance.switchToFragment(LogFragment.class, "date", "");
                                return;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        MainActivity.instance.switchToFragment(LogFragment.class, "date", dateView.getText().toString());

                    }
                });

                final TextView timeView = new TextView(getContext());
                timeView.setId(id);
                id++;
                int mins = 0;
                if (day != null) {
                    mins = day.getSecsDrivenToday() / 60;
                }
                int hours = mins / 60;
                mins %= 60;

                String time = (hours > 9) ? hours + ":" : "0" + hours + ":";
                time += (mins > 9) ? mins + ":" : "0" + mins + ":";
                time += (day.getSecsDrivenToday() % 60 > 9) ? day.getSecsDrivenToday() % 60 : "0" + day.getSecsDrivenToday() % 60;

                timeView.setText(time);
                timeView.setTextSize(30f);
                con.addView(timeView);
                card.setMinimumHeight(150);

                if(MainActivity.currentDriver.getDays().size() - i < 4){
                    card.setMinimumHeight(250);
                    addGraph(con, dateView.getText().toString(), set, day, dateView);
                }

                card.setCardElevation(1f);

                set.clone(con);
                set.connect(timeView.getId(), ConstraintSet.RIGHT, con.getId(), ConstraintSet.RIGHT);
                set.applyTo(con);

                card.addView(con);
                list.addView(card);
            }

        }

        return view;
    }

    public void addGraph(ConstraintLayout con, String date, ConstraintSet set, Day day, TextView dateView) {
        LineChart graph = new LineChart(getContext());
        graph.setMinimumWidth(400);
        graph.setMinimumHeight(200);
        XAxis xAxis = graph.getXAxis();
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
                long millis = TimeUnit.MINUTES.toMillis((long) value);
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
                switch ((int) value) {
                    case HOSEventCodes.OFF_DUTY:
                        sValue = "OFF";
                        break;
                    case HOSEventCodes.SLEEPING:
                        sValue = "SB";
                        break;
                    case HOSEventCodes.DRIVING:
                        sValue = "D";
                        break;
                    case HOSEventCodes.ON_DUTY_NOT_DRIVING:
                        sValue = "ON";
                        break;
                }
                return sValue;
            }
        });
        YAxis rightAxis = graph.getAxisRight();
        rightAxis.setEnabled(false);
        con.addView(graph);

        ArrayList<TimePeriod> eventLog = new ArrayList<>();
        try {
            if (DataConverter.sameDayNoTime(day.getDate(), MainActivity.getTime())) {
                eventLog = HOSLogger.getLog();
            } else {
                eventLog = day.getTimePeriods();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            eventLog = day.getTimePeriods();
        }
        ArrayList<Entry> values = new ArrayList<>();
        graph.disableScroll();
        graph.setTouchEnabled(false);
        Date start = Calendar.getInstance().getTime();
        Date end = MainActivity.getTime();
        for (TimePeriod t : eventLog) {
            if (t.getStartTime().before(start)) {
                start = t.getStartTime();
            } else if (t.getEndTime() != null && t.getEndTime().after(end)) {
                end = t.getEndTime();
            }
        }
        xAxis.mAxisRange = (float) ((end.getTime() - start.getTime()));
        if (eventLog != null) {
            try {
                for (TimePeriod event : eventLog) {
                    // If it is the current event, or it lasted more than 1 min
                    if (event.getDuration() == 0 || event.getDuration() > 60) {
                        switch (event.getStatus()) {
                            case DRIVING:
                                values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getStartTime().getTime()), HOSEventCodes.DRIVING));
                                if (event.getEndTime() != null) {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getEndTime().getTime()), HOSEventCodes.DRIVING));
                                } else {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(MainActivity.getTime().getTime()), HOSEventCodes.DRIVING));
                                }
                                break;
                            case ON_DUTY:
                                values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getStartTime().getTime()), HOSEventCodes.ON_DUTY_NOT_DRIVING));
                                if (event.getEndTime() != null) {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getEndTime().getTime()), HOSEventCodes.ON_DUTY_NOT_DRIVING));
                                } else {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(MainActivity.getTime().getTime()), HOSEventCodes.ON_DUTY_NOT_DRIVING));
                                }
                                break;
                            case OFF_DUTY:
                                values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getStartTime().getTime()), HOSEventCodes.OFF_DUTY));
                                if (event.getEndTime() != null) {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getEndTime().getTime()), HOSEventCodes.OFF_DUTY));
                                } else {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(MainActivity.getTime().getTime()), HOSEventCodes.OFF_DUTY));
                                }
                                break;
                            case SLEEPING:
                                values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getStartTime().getTime()), HOSEventCodes.SLEEPING));
                                if (event.getEndTime() != null) {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getEndTime().getTime()), HOSEventCodes.SLEEPING));
                                } else {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(MainActivity.getTime().getTime()), HOSEventCodes.SLEEPING));
                                }
                                break;
                        }
                    }


                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "Driver");
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
        graph.setId(id);
        graph.setMarker(null);
        id++;

        set.connect(graph.getId(), ConstraintSet.TOP, dateView.getId(), ConstraintSet.BOTTOM);
        set.connect(graph.getId(), ConstraintSet.LEFT, con.getId(), ConstraintSet.LEFT);
        set.connect(graph.getId(), ConstraintSet.RIGHT, con.getId(), ConstraintSet.RIGHT);
        set.applyTo(con);

    }
}