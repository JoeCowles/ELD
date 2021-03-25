package com.carriergistics.eld.dvir;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;

public class CreateDvirView extends ConstraintLayout {

    private RadioButton preTripBtn, postTripBtn;
    private RadioGroup group;

    private ScrollView scrollView;
    private LinearLayout list;
    private Button addBtn;
    private Switch safeSw;
    private static int id = 9213;

    public CreateDvirView(final Context context) {
        super(context);

        preTripBtn = new RadioButton(context);
        postTripBtn = new RadioButton(context);
        group = new RadioGroup(context);
        scrollView = new ScrollView(context);
        list = new LinearLayout(context);
        addBtn = new Button(context);
        safeSw = new Switch(context);

        group.setId(getID());
        scrollView.setId(getID());
        list.setId(getID());
        addBtn.setId(getID());
        safeSw.setId(getID());
        setId(getID());
        group.addView(preTripBtn);
        group.addView(postTripBtn);
        addView(group);

        scrollView.addView(list);
        addView(scrollView);
        addView(addBtn);
        addView(safeSw);

        scrollView.setMinimumHeight(200);
        scrollView.setMinimumWidth(600);

        preTripBtn.setText("Pre-trip inspection");
        postTripBtn.setText("Post-trip inspection");

        preTripBtn.setTextSize(24f);
        postTripBtn.setTextSize(24f);
        safeSw.setTextSize(24f);

        addBtn.setText("Add issue");

        safeSw.setText("Truck is safe");

        list.setOrientation(LinearLayout.VERTICAL);

        group.setPadding(0, 30, 0, 30);
        safeSw.setPadding(0,0,0, 30);

        addBtn.setWidth(200);

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                CardView card = new CardView(context);
                ConstraintLayout layout = new ConstraintLayout(context);
                ImageView picture = new ImageView(context);
                final EditText remarks = new EditText(context);

                layout.setId(getID());
                picture.setId(getID());
                remarks.setId(getID());

                layout.addView(picture);
                layout.addView(remarks);

                card.setMinimumHeight(100);

                layout.setMinWidth(600);

                picture.setImageResource(R.drawable.ic_baseline_photo_camera_24);


                remarks.setHint("Remarks");
                remarks.setPadding(0, 0, 10,0);
                remarks.setWidth(350);
                remarks.setMinHeight(75);
                remarks.setTextSize(24f);


                ConstraintSet cardSet = new ConstraintSet();
                cardSet.clone(layout);

                cardSet.connect(picture.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
                cardSet.applyTo(layout);

                cardSet.connect(remarks.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
                cardSet.applyTo(layout);

                card.addView(layout);
                list.addView(card);
            }
        });

        ConstraintSet set = new ConstraintSet();
        set.clone(this);

        set.connect(addBtn.getId(), ConstraintSet.TOP, safeSw.getId(), ConstraintSet.BOTTOM);
        set.applyTo(this);

        set.connect(safeSw.getId(), ConstraintSet.TOP, group.getId(), ConstraintSet.BOTTOM);
        set.applyTo(this);

        set.connect(scrollView.getId(), ConstraintSet.TOP, addBtn.getId(), ConstraintSet.BOTTOM);
        set.connect(scrollView.getId(), ConstraintSet.BOTTOM, getId(), ConstraintSet.BOTTOM);
        set.connect(scrollView.getId(), ConstraintSet.RIGHT, getId(), ConstraintSet.RIGHT);
        set.connect(scrollView.getId(), ConstraintSet.LEFT, getId(), ConstraintSet.LEFT);
        set.applyTo(this);


    }
    private static int getID(){
        return id++;
    }
    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

}
