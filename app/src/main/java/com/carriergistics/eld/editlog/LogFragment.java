package com.carriergistics.eld.editlog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;
import com.carriergistics.eld.logging.Day;
import com.carriergistics.eld.logging.HOSEventCodes;
import com.carriergistics.eld.logging.HOSLogger;
import com.carriergistics.eld.logging.Status;
import com.carriergistics.eld.logging.TimePeriod;
import com.carriergistics.eld.utils.DataConverter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
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

import static com.carriergistics.eld.logging.Status.DRIVING;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "date";
    private static final String ARG_PARAM2 = "param2";
    private static LineChart graph;
    private static LinearLayout list;
    public static String date;
    private static int id = 4000;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static XAxis xAxis;
    public LogFragment() {
        // Required empty public constructor
    }

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
            date = getArguments().getString(ARG_PARAM1);
            Log.d("DEBUGGING", "got date: " + date);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        graph = view.findViewById(R.id.graphingLog);
        graph.setMarker(null);
        Legend l = graph.getLegend();
        l.setEnabled(false);
        list = view.findViewById(R.id.logEventList);
        list.setPadding(0, 20, 0, 20);
        xAxis = graph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTextSize(10f);
        xAxis.setEnabled(true);
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
        if(date != null && !date.isEmpty()){

            try {
                //TODO: Make this more reliable
                SimpleDateFormat sdf = new SimpleDateFormat("  EEE MMM dd, yyyy");
                Day retrieved = null;
                for(Day d : MainActivity.currentDriver.getDays()){
                    if(DataConverter.sameDayNoTime(d.getDate(), sdf.parse(date))){
                        Log.d("DEBUGGING", "Matched to day: " + d.getDate().toString());
                        retrieved =  d;
                    }
                }
                setData(retrieved.getTimePeriods());
            } catch (ParseException e) {
                try {
                    setData(MainActivity.currentDriver.getDay(MainActivity.getTime()).getTimePeriods());
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            } catch(NullPointerException e){
                try {
                    setData(MainActivity.currentDriver.getDay(MainActivity.getTime()).getTimePeriods());
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
        }
        else{
            try {
                setData(MainActivity.currentDriver.getDay(MainActivity.getTime()).getTimePeriods());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //setData(HOSLogger.getLog());
        return view;
    }
    private void setData(final ArrayList<TimePeriod> eventLog) {
        ArrayList<Entry> values = new ArrayList<>();
        for(TimePeriod period : eventLog){
            Log.d("DEBUGGING", "Found event:  " + period.getStatus() + " " + period.getStartTime());
        }
        Date start = Calendar.getInstance().getTime();
        Date end = MainActivity.getTime();
        for(TimePeriod t : eventLog){
            Log.d("DEBUGGING", t.getStartTime() + " - " + t.getEndTime() + " " + t.getStatus());
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
                    // If it is the current event, or it lasted more than 1 min
                    if((event.getDuration() == 0 || event.getDuration() > 60) && event.getStatus() != null){
                        switch (event.getStatus()){
                            case DRIVING:
                                values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getStartTime().getTime()), HOSEventCodes.DRIVING));
                                if(event.getEndTime() != null){
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getEndTime().getTime()), HOSEventCodes.DRIVING));
                                }else{
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(MainActivity.getTime().getTime()), HOSEventCodes.DRIVING));
                                }
                                break;
                            case ON_DUTY:
                                values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getStartTime().getTime()), HOSEventCodes.ON_DUTY_NOT_DRIVING));
                                if(event.getEndTime() != null) {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getEndTime().getTime()), HOSEventCodes.ON_DUTY_NOT_DRIVING));
                                }else{
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(MainActivity.getTime().getTime()), HOSEventCodes.ON_DUTY_NOT_DRIVING));
                                }
                                break;
                            case OFF_DUTY:
                                values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getStartTime().getTime()), HOSEventCodes.OFF_DUTY));
                                if(event.getEndTime() != null) {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getEndTime().getTime()), HOSEventCodes.OFF_DUTY));
                                }else{
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(MainActivity.getTime().getTime()), HOSEventCodes.OFF_DUTY));
                                }
                                break;
                            case SLEEPING:
                                values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getStartTime().getTime()), HOSEventCodes.SLEEPING));
                                if(event.getEndTime() != null) {
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(event.getEndTime().getTime()), HOSEventCodes.SLEEPING));
                                }else{
                                    values.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(MainActivity.getTime().getTime()), HOSEventCodes.SLEEPING));
                                }
                                break;
                        }
                        Log.d("DEBUGGING", "Found event: " + event.getStatus() + " " + event.getStartTime() + " - " + event.getEndTime());
                    }



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
        // TODO: switch to 24 hr system
        for(int t = eventLog.size() -1; t > 0; t--){
            final TimePeriod tp = eventLog.get(t);
            final int index = t;
            CardView view = new CardView(getContext());
            ConstraintLayout con = new ConstraintLayout(getContext());
            con.setId(id);
            id++;

            ConstraintSet set = new ConstraintSet();
            set.clone(con);
            if(tp == null){
                break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd     HH:mm");
            TextView tv = new TextView(getActivity());
            tv.setText(tp.getStatus() + "");
            tv.setId(id);
            tv.setTextSize(20);
            id++;
            TextView startTime = new TextView(getActivity());
            startTime.setId(id);
            startTime.setTextSize(20);
            id++;
            if(tp.getStatus() == DRIVING){
                startTime.setText(sdf.format(tp.getStartTime()) + " - ");
                startTime.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                startTime.setText(sdf.format(tp.getStartTime())+ " - ");
                startTime.setTypeface(Typeface.DEFAULT_BOLD);
            }
            sdf = new SimpleDateFormat("HH:mm     MM/dd     ");
            TextView endTime = new TextView(getActivity());
            endTime.setId(id);
            endTime.setTextSize(20);
            id++;
            if(tp.getEndTime() != null){
                endTime.setText(sdf.format(tp.getEndTime()));
                endTime.setTypeface(Typeface.DEFAULT_BOLD);

            }else{
                endTime.setText("now\t\t\t\t\t\t\t\t");
                endTime.setTypeface(Typeface.DEFAULT_BOLD);
            }

            if(t % 2 == 0){
                con.setBackgroundColor(Color.GRAY);
            }else {
                con.setBackgroundColor(Color.WHITE);
            }
            Button editBtn = new Button(getActivity());
            editBtn.setId(id);
            id++;
            editBtn.setText("Edit");
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    int checked = 0;
                    if(tp.getStatus() == Status.ON_DUTY){
                        checked = 1;
                    }
                    final EditLogView editView = new EditLogView(getContext(), checked);
                    final int finalChecked = checked;
                    builder.setTitle("Edit event").setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(!editView.getNote().isEmpty() && finalChecked != editView.getSelected()) {
                                switch (editView.getSelected()) {
                                    case 0:
                                        eventLog.get(index).setStatus(Status.OFF_DUTY);
                                        // If it is the current time period, then set the driver's status
                                        if (tp.getEndTime() == null) {
                                            MainActivity.currentDriver.setStatus(Status.OFF_DUTY);
                                        }
                                        break;
                                    case 1:
                                        eventLog.get(index).setStatus(Status.ON_DUTY);
                                        if (tp.getEndTime() == null) {
                                            MainActivity.currentDriver.setStatus(Status.ON_DUTY);
                                        }
                                        break;
                                    case 2:
                                        eventLog.get(index).setStatus(Status.DRIVING);
                                        if (tp.getEndTime() == null) {
                                            MainActivity.currentDriver.setStatus(Status.DRIVING);
                                        }
                                        break;
                                }
                                refresh();
                                dialog.cancel();
                            }else if(finalChecked == editView.getSelected()){
                                dialog.cancel();
                            }else{
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                builder1.setTitle("Warning!").setMessage("Edits must have a note!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog dialog1 = builder1.create();
                                dialog1.show();
                            }
                        }
                    }).setView(editView);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            if(tp.getStatus() == DRIVING){
                editBtn.setEnabled(false);
            }
            list.addView(view);
            view.addView(con);
            con.addView(editBtn);
            con.addView(startTime);
            con.addView(endTime);
            con.addView(tv);

            set.clone(con);
            set.connect(tv.getId(), ConstraintSet.LEFT, con.getId(), ConstraintSet.LEFT);
            set.applyTo(con);
            set.clone(con);
            set.connect(editBtn.getId(), ConstraintSet.RIGHT, con.getId(), ConstraintSet.RIGHT);
            set.applyTo(con);
            set.clone(con);
            set.connect(endTime.getId(), ConstraintSet.RIGHT, editBtn.getId(), ConstraintSet.LEFT);
            set.applyTo(con);
            set.clone(con);
            set.connect(startTime.getId(), ConstraintSet.RIGHT, endTime.getId(), ConstraintSet.LEFT);
            set.applyTo(con);

        }

    }

    public static void setDate(String _date){
        date = _date;
    }
    public String getDate(){
        return date;
    }
    private void refresh(){
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}