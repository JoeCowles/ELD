package com.carriergistics.eld.dvir;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;
import com.carriergistics.eld.bluetooth.BluetoothConnector;
import com.google.android.gms.common.util.NumberUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddVehicleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddVehicleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button backBtn;
    private ImageView getOdoBtn;
    private EditText odoEt;
    private ImageView getVinBtn;
    private EditText vinNum;
    private Button saveBtn;
    private EditText nameEt;
    private Switch safeSw;

    public AddVehicleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddVehicleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddVehicleFragment newInstance(String param1, String param2) {
        AddVehicleFragment fragment = new AddVehicleFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_vehicle, container, false);
        backBtn = view.findViewById(R.id.vehicleBackBtn);
        vinNum = view.findViewById(R.id.vehicleVinET);
        getVinBtn = view.findViewById(R.id.vehicleGetVinBtn);
        safeSw = view.findViewById(R.id.vehicleSaveSw);
        nameEt = view.findViewById(R.id.vehicleNameEt);
        saveBtn = view.findViewById(R.id.vehicleSaveBtn);
        odoEt = view.findViewById(R.id.addVehicleOdoEt);
        getOdoBtn = view.findViewById(R.id.vehicleGetOdoBtn);


        getVinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vin = BluetoothConnector.getVinNum();
                if(vin != null && !vin.isEmpty() && !vin.equalsIgnoreCase("Couldn't retrieve")){
                    vinNum.setText(vin);
                }else{
                    vinNum.setHint("Couldn't retrieve!");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Warning!").setMessage("Please make sure that the VIN reading is correct\nit will cause errors if it is not correct!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.instance.switchToFragment(DvirFragment.class);
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vinNum.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("WARNING").setMessage("Vin cannot be empty!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            return;
                        }
                    }).create();
                    AlertDialog warning = builder.create();
                    warning.show();
                }else if(nameEt.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("WARNING").setMessage("Name cannot be empty!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            return;
                        }
                    }).create();
                    AlertDialog warning = builder.create();
                    warning.show();
                }else if(nameEt.getText().toString().equalsIgnoreCase("Name")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("WARNING").setMessage("Name cannot be \"Name\"").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            return;
                        }
                    }).create();
                    AlertDialog warning = builder.create();
                    warning.show();
                }else if(odoEt.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("WARNING").setMessage("Odometer cannot be empty!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            return;
                        }
                    }).create();
                    AlertDialog warning = builder.create();
                    warning.show();
                } else if(!isNumeric(odoEt.getText().toString())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("WARNING").setMessage("Odometer must be numeric! (no commas or spaces)").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            return;
                        }
                    }).create();
                    AlertDialog warning = builder.create();
                    warning.show();
                }else{
                    Vehicle vehicle = new Vehicle(nameEt.getText().toString(), vinNum.getText().toString(), safeSw.isChecked());
                    vehicle.setOdo(Integer.parseInt(odoEt.getText().toString()));
                    MainActivity.vehicles.add(vehicle);
                    MainActivity.instance.switchToFragment(DvirFragment.class);
                }
            }
        });
        getOdoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String odo = BluetoothConnector.getOdo() + "";
                if(odo != null && !odo.isEmpty()){
                    odoEt.setText(odo);
                }else{
                    odoEt.setHint("Couldn't retrieve!");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Warning!").setMessage("Please make sure that the odometer reading is correct\nit will cause errors if it is not!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        return view;
    }
    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}