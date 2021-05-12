package com.carriergistics.eld.dvir;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditDvirFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditDvirFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "vin";
    private static final String ARG_PARAM2 = "dvirNum";

    // TODO: Rename and change types of parameters
    private String vin;
    private String dvirNum;

    private TextView typeTv;
    private TextView nameTv;
    private TextView safetyTv;
    private TextView dateView;
    private LinearLayout list;
    private int id = 3214;
    public EditDvirFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditDvirFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditDvirFragment newInstance(String param1, String param2) {
        EditDvirFragment fragment = new EditDvirFragment();
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
            vin = getArguments().getString(ARG_PARAM1);
            dvirNum = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_dvir, container, false);
        Vehicle vehicle = MainActivity.getVehicleFromVin(vin);
        final Dvir dvir = vehicle.getDvirLog().get(Integer.parseInt(dvirNum));

        nameTv = view.findViewById(R.id.dvirByNameView);
        typeTv = view.findViewById(R.id.dvirTypeView);
        safetyTv = view.findViewById(R.id.dvirSafetyView);
        dateView = view.findViewById(R.id.dvirDateView);
        list = view.findViewById(R.id.dvirIssueList);

        typeTv.setText(dvir.getType().toString() + " inspection");
        nameTv.setText("by " + dvir.getDriverName());
        safetyTv.setText(dvir.getSafety().toString());
        dateView.setText(dvir.getDate().toString());

        if(dvir.getSafety() == Dvir.Safety.SAFE){
            safetyTv.setTextColor(Color.GREEN);
        }else if(dvir.getSafety() == Dvir.Safety.UNSAFE){
            safetyTv.setTextColor(Color.RED);
        }
        for(final Issue issue : dvir.getIssues()){
            Log.d("DEBUGGING", "Issue found: " + issue.getRemarks());
            CardView card = new CardView(getContext());
            ConstraintLayout layout = new ConstraintLayout(getContext());
            card.addView(layout);

            ImageView picture = new ImageView(getContext());
            final TextView remarks = new TextView(getContext());
            final Button resolve = new Button(getContext());
            TextView safetyView = new TextView(getContext());

            layout.addView(picture);
            layout.addView(remarks);
            layout.addView(resolve);
            layout.addView(safetyView);

            picture.setId(getID());
            remarks.setId(getID());
            resolve.setId(getID());
            layout.setId(getID());
            safetyView.setId(getID());

            remarks.setText("Remarks:\n" + issue.getRemarks());
            remarks.setTextSize(30f);
            remarks.setWidth(600);

            if(!issue.isResolved()){
                resolve.setText("Resolve");
                resolve.setTextSize(30f);
                resolve.setWidth(200);
                resolve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final ResolveView resolveView = new ResolveView(getContext());
                        builder.setTitle("Resolve").setView(resolveView).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (resolveView.getNotes().isEmpty()) {
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                    builder1.setTitle("Warning").setMessage("Must include a note!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    AlertDialog dialog1 = builder1.create();
                                    dialog1.show();
                                }else if(resolveView.getResolverName().isEmpty()){
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                    builder1.setTitle("Warning").setMessage("Must include name of resolver!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    AlertDialog dialog1 = builder1.create();
                                    dialog1.show();
                                }else{
                                    issue.setResolved(true);
                                    issue.setResolveNotes(resolveView.getNotes());
                                    issue.setResolverName(resolveView.getResolverName());
                                    dialog.cancel();
                                    refresh();
                                }

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }else if (!issue.isDriverSigned()){
                resolve.setText("Sign");
                resolve.setTextSize(30f);
                resolve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final ResolveView resolveView = new ResolveView(getContext());
                        builder.setTitle("Sign").setMessage("Driver signs off on resolution?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                issue.setDriverSigned(true);
                                boolean safe = true;
                                for(Issue i : dvir.getIssues()){
                                    safe = (!i.isDriverSigned()) ? false : safe;
                                }
                                dvir.setSafety((safe) ? Dvir.Safety.RESOLVED : dvir.getSafety());
                                refresh();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }else{
                resolve.setVisibility(View.INVISIBLE);
            }
            if(issue.isResolved() && issue.isDriverSigned()){
                safetyView.setText("Resolved");
                safetyView.setTextSize(30);
                safetyView.setTextColor(Color.GREEN);
            }else
            if(issue.isSafe()){
                safetyView.setText("Safe");
                safetyView.setTextSize(30);
                safetyView.setTextColor(Color.GREEN);
            }else{
                safetyView.setText("Unsafe");
                safetyView.setTextSize(30);
                safetyView.setTextColor(Color.RED);
            }

            safetyView.setPadding(0,0,30,0);

            Bitmap pictureBitmap = BitmapFactory.decodeFile(issue.getPicturePath());
            if(pictureBitmap != null){
                pictureBitmap = Bitmap.createScaledBitmap(pictureBitmap, 200, 200, false);
                picture.setImageBitmap(pictureBitmap);
            }

            remarks.setWidth(200);

            ConstraintSet set = new ConstraintSet();
            set.clone(layout);

            set.connect(picture.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
            set.applyTo(layout);

            set.connect(remarks.getId(), ConstraintSet.LEFT, picture.getId(), ConstraintSet.RIGHT);
            set.connect(remarks.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
            set.applyTo(layout);

            set.connect(resolve.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
            set.connect(resolve.getId(), ConstraintSet.TOP, safetyView.getId(), ConstraintSet.BOTTOM);
            set.applyTo(layout);

            set.connect(safetyView.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
            set.connect(safetyView.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
            set.applyTo(layout);

            list.addView(card);
        }
        return view;
    }
    private int getID(){
        return id++;
    }
    private void refresh(){
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}