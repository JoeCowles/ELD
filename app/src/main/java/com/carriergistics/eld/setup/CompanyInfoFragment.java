package com.carriergistics.eld.setup;

import android.content.Context;
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

import com.carriergistics.eld.R;
import com.carriergistics.eld.utils.Company;
import com.carriergistics.eld.utils.Data;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompanyInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Button nextBtn;
    static EditText companyName;
    static EditText dotNum;
    static EditText companyAddress;
    static EditText city;
    static EditText state;
    static EditText zip;
    static EditText companyPhone;
    static ArrayList<EditText> fields;
    private static String lastEditValue;
    private static ConstraintLayout layout;
    private static Company company;

    public static String nameStr;
    public static String dotNumStr;
    public static String addressStr;
    public static String cityStr;
    public static String stateStr;
    public static int zipNum;
    public static String phoneStr;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CompanyInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompanyInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompanyInfoFragment newInstance(String param1, String param2) {
        CompanyInfoFragment fragment = new CompanyInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_company_info, container, false);
        company = Data.loadCompany();
        nextBtn = view.findViewById(R.id.setupNextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkForErrors()){
                    Data.saveCompany(company);
                    SetupActivity.setTab(1);
                }
                return;

            }
        });

        layout = view.findViewById(R.id.companyFragLayout);

        // All the text fields
        fields = new ArrayList<>();
        companyName = view.findViewById(R.id.etCompanyName);
        dotNum = view.findViewById(R.id.etDOTNum);
        companyAddress = view.findViewById(R.id.etCompanyAddress);
        city = view.findViewById(R.id.etCity);
        state = view.findViewById(R.id.etState);
        zip = view.findViewById(R.id.etZip);
        companyPhone = view.findViewById(R.id.etPhone);

        // Add the fields to the array
        fields.add(companyName);
        fields.add(companyName);
        fields.add(dotNum);
        fields.add(companyAddress);
        fields.add(city);
        fields.add(state);
        fields.add(zip);
        fields.add(companyPhone);

        // Set click listeners to clear the field when it is clicked
        for(final EditText field : fields){
            field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if(field.getText().toString().isEmpty()){
                            field.setText(getLastValue());
                        }
                        setLastValue("");
                    } else {
                        setLastValue(field.getText().toString());
                        field.setText("");
                    }
                }
            });
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.requestFocus();
                companyName.clearFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });


        return view;
    }
    private void setLastValue(String lastValue){
        lastEditValue = lastValue;
    }
    private String getLastValue(){
        return lastEditValue;
    }
    private boolean checkForErrors(){
        company.setName(companyName.getText().toString());
        company.setAddress(companyAddress.getText().toString());
        company.setCity(city.getText().toString());
        company.setDotNum(dotNum.getText().toString());
        company.setZip(123);
        //TODO: Change zip
        company.setState(state.getText().toString());
        company.setPhoneNum("555-555-5555");
        // TODO: Check errors
        return true;
    }
    public boolean save(){
        if(checkForErrors()){
            Data.saveCompany(company);
            return true;
        }
        return false;
    }


}