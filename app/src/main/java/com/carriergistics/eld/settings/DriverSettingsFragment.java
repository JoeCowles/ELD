package com.carriergistics.eld.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;
import com.carriergistics.eld.ui.DrawingView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriverSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriverSettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView signature;
    private EditText firstName;
    private EditText lastName;
    private String mParam1;
    private String mParam2;

    public DriverSettingsFragment() {
        // Required empty public constructor
    }

    public static DriverSettingsFragment newInstance(String param1, String param2) {
        DriverSettingsFragment fragment = new DriverSettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_driver_settings, container, false);
        // Inflate the layout for this fragment

        signature = view.findViewById(R.id.settingsSignature);
        signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final DrawingView dv = new DrawingView(getContext());
                ConstraintLayout con = new ConstraintLayout(getContext());
                Button clearBtn = new Button(getContext());
                clearBtn.setText("Clear");
                con.addView(clearBtn);
                clearBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dv.clear();
                    }
                });
                con.addView(dv);
                con.setMaxHeight(300);
                if(MainActivity.currentDriver.getSignature() != null){
                    dv.setBitmap(MainActivity.currentDriver.getSignature());
                }
                builder.setMessage("Edit signature:").setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dv.save();
                        signature.setImageBitmap(MainActivity.currentDriver.getSignature());
                        MainActivity.save();
                        dialog.cancel();

                    }
                }).setView(con).create().show();
            }
        });
        //if(MainActivity.currentDriver.getSignature() != null){
            //signature.setImageBitmap(MainActivity.currentDriver.getSignature());
       // }
        firstName = view.findViewById(R.id.settingsDriverFirstName);
        lastName = view.findViewById(R.id.settingsDriverLastName);
        firstName.setText(MainActivity.currentDriver.getFirst_name());
        lastName.setText(MainActivity.currentDriver.getLast_name());
        return view;
    }
}