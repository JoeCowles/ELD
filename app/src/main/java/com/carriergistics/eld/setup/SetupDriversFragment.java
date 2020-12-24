package com.carriergistics.eld.setup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;
import com.carriergistics.eld.logging.Driver;
import com.carriergistics.eld.utils.Data;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetupDriversFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetupDriversFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button signupBtn;
    private ArrayList<EditText> fields;
    private ArrayList<EditText> secondFields;
    private EditText firstName;
    private EditText lastName;
    private EditText driverID;
    private EditText email;
    private EditText password;
    private EditText confPassword;
    private String lastValue;

    private Switch driverSwitch;
    private EditText firstName2;
    private EditText lastName2;
    private EditText driverID2;
    private EditText email2;
    private EditText password2;
    private EditText confPassword2;
    private String lastValue2;

    private ConstraintLayout layout;
    private ArrayList<Driver> drivers;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SetupDriversFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment setupDrivers.
     */
    // TODO: Rename and change types and number of parameters
    public static SetupDriversFragment newInstance(String param1, String param2) {
        SetupDriversFragment fragment = new SetupDriversFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setup_drivers, container, false);
        signupBtn = view.findViewById(R.id.signupBtn);
        load();
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        layout = view.findViewById(R.id.driverLayout);

        fields = new ArrayList<>();
        drivers = new ArrayList<>();
        secondFields = new ArrayList<>();

        firstName = view.findViewById(R.id.etDriverFirstName);
        lastName = view.findViewById(R.id.etDriverLastName);
        driverID = view.findViewById(R.id.etDriverID);
        email = view.findViewById(R.id.etEmail);
        password = view.findViewById(R.id.etPassword);
        confPassword = view.findViewById(R.id.etConfirmPassword);

        driverSwitch = view.findViewById(R.id.secondDriverSw);
        firstName2 = view.findViewById(R.id.etDriverFirstName2);
        lastName2 = view.findViewById(R.id.etDriverLastName2);
        driverID2 = view.findViewById(R.id.etDriverID2);
        email2 = view.findViewById(R.id.etEmail2);
        password2 = view.findViewById(R.id.etPassword2);
        confPassword2 = view.findViewById(R.id.etConfirmPassword2);



        fields.add(firstName);
        fields.add(lastName);
        fields.add(driverID);
        fields.add(email);
        fields.add(password);
        fields.add(confPassword);
        fields.add(firstName2);
        fields.add(lastName2);
        fields.add(driverID2);
        fields.add(email2);
        fields.add(password2);
        fields.add(confPassword2);

        secondFields.add(firstName2);
        secondFields.add(lastName2);
        secondFields.add(driverID2);
        secondFields.add(email2);
        secondFields.add(password2);
        secondFields.add(confPassword2);

        for(final EditText field : fields){
            field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if(field.getText().toString().isEmpty()){
                            field.setText(lastValue);
                        }
                        lastValue = "";
                    } else {
                        lastValue = field.getText().toString();
                        field.setText("");
                    }
                }
            });
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.requestFocus();
                firstName.clearFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        driverSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!driverSwitch.isChecked()){
                    for(EditText field : secondFields){
                        field.setVisibility(View.INVISIBLE);
                    }
                }else{
                    for(EditText field : secondFields){
                        field.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        for(EditText field : secondFields){
            field.setVisibility(View.INVISIBLE);
        }
        return view;
    }
    private void load(){
        drivers = Data.loadDrivers();
    }
    private void save(){
        Gson gson = new Gson();
        if(drivers.size() > 1){
            Driver driver = drivers.get(0);
            driver.setFirst_name(firstName.getText().toString());
            driver.setLast_name(lastName.getText().toString());
            driver.setLicenseNum(driverID.getText().toString());
            driver.setEmail(email.getText().toString());
            drivers.add(driver);
            Driver driver2 = drivers.get(1);
            driver2.setFirst_name(firstName2.getText().toString());
            driver2.setLast_name(lastName2.getText().toString());
            driver2.setLicenseNum(driverID2.getText().toString());
            driver2.setEmail(email2.getText().toString());
            drivers.add(driver2);
        }else{
            Driver driver;
            if(drivers != null && !drivers.isEmpty()){
                driver = drivers.get(0);
            }else{
                driver = new Driver();
            }
            driver.setFirst_name(firstName.getText().toString());
            driver.setLast_name(lastName.getText().toString());
            driver.setLicenseNum(driverID.getText().toString());
            driver.setEmail(email.getText().toString());
            drivers.add(driver);
            if(driverSwitch.isChecked()){
                Driver driver2 = new Driver();
                driver2.setFirst_name(firstName2.getText().toString());
                driver2.setLast_name(lastName2.getText().toString());
                driver2.setLicenseNum(driverID2.getText().toString());
                driver2.setEmail(email2.getText().toString());
                drivers.add(driver2);
            }
        }
        Data.saveDrivers(drivers);
    }
}