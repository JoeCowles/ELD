package com.carriergistics.eld.settings;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;
import com.carriergistics.eld.bluetooth.BluetoothConnector;
import com.carriergistics.eld.utils.Settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BluetoothSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BluetoothSettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button selectDeviceBtn;
    private Button setName;
    private Button setPassword;
    private EditText etName;
    private EditText etPassword;
    private String device;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BluetoothSettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BluetoothSettingsFragment newInstance(String param1, String param2) {
        BluetoothSettingsFragment fragment = new BluetoothSettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_bluetooth_settings, container, false);
        // Inflate the layout for this fragment
        selectDeviceBtn = view.findViewById(R.id.selectDeviceBtn);
        selectDeviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDevice();
            }
        });
        etName = view.findViewById(R.id.deviceName);
        etPassword = view.findViewById(R.id.etBluePassword);
        setName = view.findViewById(R.id.renameBtn);
        setName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BluetoothConnector.renameDevice(etName.getText().toString(), etPassword.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        etPassword.setText(MainActivity.loadSettings().getDevicePassword());
        return view;
    }
    private void selectDevice(){
        ArrayList deviceStrs = new ArrayList();
        final ArrayList names = new ArrayList();
        final ArrayList devices = new ArrayList();
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                deviceStrs.add(device.getName() + "\n" + device.getAddress());
                devices.add(device.getAddress());
                names.add(device.getName());
            }
        }

        // show list
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,
                deviceStrs.toArray(new String[deviceStrs.size()]));

        alertDialog.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                int position = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                String deviceAddress = devices.get(position).toString();
                device = deviceAddress;
                Log.d("DEBUGGING", "THE ADDRESS of device: " + device);
                setDevice(names.get(position).toString(), device);
                if(BluetoothConnector.connect(device)){
                    makeText("Connected");
                }else{
                    makeText("Couldn't connect!");
                }
            }
        });

        alertDialog.setTitle("Choose Bluetooth device");
        alertDialog.show();
    }
    private void makeText(String text){
        Toast.makeText(getActivity().getBaseContext(), text, Toast.LENGTH_SHORT).show();
    }
    public void setDevice(String name, String address){
        etName.setText(name);
        etPassword.setHint("Password");
        Settings settings = new Settings();
        settings.setDeviceName(name);
        settings.setDevicePassword("1234");
        settings.setDeviceAddress(address);
        MainActivity.saveSettings(settings);
    }

}