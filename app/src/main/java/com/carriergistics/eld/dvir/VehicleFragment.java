package com.carriergistics.eld.dvir;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VehicleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VehicleFragment extends Fragment {

    private LinearLayout list;
    private Button addDvirBtn;
    private TextView nameTv;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "vin";
    private static final String ARG_PARAM2 = "param2";
    private static int id = 10023;
    // TODO: Rename and change types of parameters
    private static String vinNum;
    private String mParam2;

    public VehicleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VehicleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VehicleFragment newInstance(String param1, String param2) {
        VehicleFragment fragment = new VehicleFragment();
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
            if(getArguments().getString(ARG_PARAM1) != null && !getArguments().getString(ARG_PARAM1).isEmpty()){
                vinNum = getArguments().getString(ARG_PARAM1);
            }
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle, container, false);

        final Vehicle editing = MainActivity.getVehicleFromVin(vinNum);

        nameTv = view.findViewById(R.id.vehicleNameTv);
        list = view.findViewById(R.id.vehicleDvirList);
        addDvirBtn = view.findViewById(R.id.vehicleCreateDvirBtn);

        nameTv.setText(editing.getName());

        addDvirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.instance.switchToFragment(CreateDvirFragment.class, "vinNum",vinNum);
                refresh();

            }
        });

        for(int i = editing.getDvirLog().size() - 1; i >= 0; i--){
            final Dvir dvir = editing.getDvirLog().get(i);
            CardView card = new CardView(getContext());
            ConstraintLayout layout = new ConstraintLayout(getContext());
            TextView dateView = new TextView(getContext());
            TextView tripTypeView = new TextView(getContext());
            TextView safetyView = new TextView(getContext());

            dateView.setId(getID());
            tripTypeView.setId(getID());
            safetyView.setId(getID());
            layout.setId(getID());

            layout.addView(dateView);
            layout.addView(tripTypeView);
            layout.addView(safetyView);

            dateView.setText(dvir.getDate().toString());
            dateView.setTextSize(18f);

            tripTypeView.setText(dvir.getType().toString());
            tripTypeView.setTextSize(30f);

            safetyView.setText(dvir.getSafety().toString());
            safetyView.setTextSize(30f);
            safetyView.setPadding(0,0,20, 0);

            if(dvir.getSafety() == Dvir.Safety.SAFE){
                safetyView.setTextColor(Color.GREEN);
            }else if(dvir.getSafety() == Dvir.Safety.UNSAFE){
                safetyView.setTextColor(Color.RED);
            }
            //card.setMinimumHeight(150);
            //layout.setMinimumHeight(150);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.instance.switchToFragment(EditDvirFragment.class, "vin", vinNum, "dvirNum", editing.getDvirLog().indexOf(dvir) + "");
                }
            });
            ConstraintSet set = new ConstraintSet();
            set.clone(layout);

            set.connect(dateView.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
            set.connect(dateView.getId(), ConstraintSet.TOP, tripTypeView.getId(), ConstraintSet.BOTTOM);
            set.applyTo(layout);

            set.connect(tripTypeView.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
            set.connect(tripTypeView.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
            set.applyTo(layout);

            set.connect(safetyView.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
            set.connect(safetyView.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
            set.applyTo(layout);

            card.addView(layout);
            list.addView(card);
        }

        return view;
    }
    public static int getID(){
        return id++;
    }
    private void refresh(){
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}