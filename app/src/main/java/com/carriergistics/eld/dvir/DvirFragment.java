package com.carriergistics.eld.dvir;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DvirFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DvirFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static int idIndex = 12344;
    private ImageView addVehicleBtn;

    private LinearLayout vehicleList;
    public DvirFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DvirFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DvirFragment newInstance(String param1, String param2) {
        DvirFragment fragment = new DvirFragment();
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
        View view = inflater.inflate(R.layout.fragment_dvir, container, false);
        addVehicleBtn = view.findViewById(R.id.dvirAddVehicleBtn);
        addVehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.instance.switchToFragment(AddVehicleFragment.class);
            }
        });
        vehicleList = view.findViewById(R.id.dvirVehicleList);
        if(MainActivity.vehicles.size() > 0) {

            for (int i = 0; i < MainActivity.vehicles.size(); i++) {

                final Vehicle vehicle = MainActivity.vehicles.get(i);
                final CardView card = new CardView(getContext());
                final ImageView truckIcon = new ImageView(getContext());
                final TextView truckNum = new TextView(getContext());
                ImageView deleteBtn = new ImageView(getContext());
                TextView vin = new TextView(getContext());
                ImageView warning = new ImageView(getContext());
                TextView nameView = new TextView(getContext());
                TextView odo = new TextView(getContext());

                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.instance.switchToFragment(VehicleFragment.class, "vin", vehicle.getVin());
                    }
                });
                ConstraintLayout layout = new ConstraintLayout(getContext());
                card.addView(layout);
                layout.setId(getID());


                truckIcon.setImageResource(R.drawable.carriergisticssemi);
                truckIcon.setMaxHeight(100);
                truckIcon.setMaxWidth(100);
                layout.addView(truckIcon);
                truckIcon.setId(getID());

                nameView.setText(vehicle.getName());
                layout.addView(nameView);
                nameView.setId(getID());
                nameView.setTextSize(45f);
                nameView.setPadding(0, 0, 0, 0);

                vin.setText(vehicle.getVin());
                vin.setId(getID());
                vin.setPadding(20, 10, 0, 0);
                layout.addView(vin);


                warning.setImageResource(R.drawable.ic_baseline_warning_24);
                warning.setId(getID());
                layout.addView(warning);

                truckNum.setText((i + 1) + "");
                truckNum.setId(getID());
                truckNum.setVisibility(View.INVISIBLE);
                layout.addView(truckNum);

                odo.setText("Odo: " + vehicle.getOdo() + " mi");
                odo.setId(getID());
                odo.setTextSize(28f);
                odo.setPadding(0,0,0,0);
                layout.addView(odo);

                deleteBtn.setImageResource(R.drawable.ic_baseline_delete_24);
                deleteBtn.setId(getID());
                layout.addView(deleteBtn);

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Remove the vehicle
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setIcon(R.drawable.ic_baseline_warning_24).setTitle("WARNING").setMessage("Are you sure you want to delete this vehicle?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.vehicles.remove(Integer.parseInt(truckNum.getText().toString())-1);
                                MainActivity.save();
                                refresh();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                ConstraintSet set = new ConstraintSet();
                set.clone(layout);

                set.connect(truckIcon.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
                set.applyTo(layout);

                set.connect(nameView.getId(), ConstraintSet.LEFT, truckIcon.getId(), ConstraintSet.RIGHT);
                set.applyTo(layout);

                set.connect(vin.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
                set.connect(vin.getId(), ConstraintSet.LEFT, truckIcon.getId(), ConstraintSet.LEFT);
                set.applyTo(layout);

                set.connect(warning.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
                set.connect(warning.getId(), ConstraintSet.RIGHT, deleteBtn.getId(), ConstraintSet.LEFT);
                set.applyTo(layout);

                set.connect(deleteBtn.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
                set.connect(deleteBtn.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
                set.applyTo(layout);

                set.connect(truckNum.getId(), ConstraintSet.LEFT, truckNum.getId(), ConstraintSet.LEFT);
                set.connect(truckNum.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
                set.applyTo(layout);

                set.connect(odo.getId(), ConstraintSet.TOP, nameView.getId(), ConstraintSet.BOTTOM);
                set.connect(odo.getId(), ConstraintSet.LEFT, truckIcon.getId(), ConstraintSet.RIGHT);
                set.applyTo(layout);

                vehicleList.addView(card);
            }
        }

        return view;
    }
    private int getID(){
        idIndex++;
        return idIndex;
    }
    private void refresh(){
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

}