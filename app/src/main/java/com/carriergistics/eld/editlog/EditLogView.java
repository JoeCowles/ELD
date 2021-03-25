package com.carriergistics.eld.editlog;

import android.content.Context;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class EditLogView extends ConstraintLayout {

    private EditText textField;
    private RadioButton offDutyBtn;
    private RadioButton onDutyBtn;
    private RadioButton drivingBtn;
    private RadioGroup group;
    private ConstraintSet set;
    // TODO: make the id system better
    private int id = 9000;

    public EditLogView(Context context, int selected) {
        super(context);
        this.setId(getID());
        this.setMinimumHeight(400);
        set = new ConstraintSet();
        set.clone(this);

        offDutyBtn = new RadioButton(context);
        onDutyBtn = new RadioButton(context);
        drivingBtn = new RadioButton(context);
        textField = new EditText(context);
        group = new RadioGroup(context);

        group.addView(offDutyBtn);
        group.addView(onDutyBtn);
        group.addView(drivingBtn);

        offDutyBtn.setText("Off duty");
        onDutyBtn.setText("On duty");
        drivingBtn.setText("Driving");
        textField.setHint("Note");

        offDutyBtn.setMinimumHeight(50);
        onDutyBtn.setMinimumHeight(50);
        drivingBtn.setMinimumHeight(50);
        textField.setMinWidth(450);
        //textField.setMinHeight(150);
        //group.setMinimumHeight(150);

        this.addView(textField);
        this.addView(group);

        offDutyBtn.setId(getID());
        onDutyBtn.setId(getID());
        drivingBtn.setId(getID());
        textField.setId(getID());
        group.setId(getID());

        switch (selected){
            case 0:
                offDutyBtn.setChecked(true);
                break;
            case 1:
                onDutyBtn.setChecked(true);
                break;
            case 2:
                drivingBtn.setChecked(true);
                break;
        }

        set.clone(this);
        set.connect(textField.getId(), ConstraintSet.TOP, group.getId(), ConstraintSet.BOTTOM);
        set.applyTo(this);



    }

    public int getSelected(){
        int selected = group.getCheckedRadioButtonId();
        if(selected == offDutyBtn.getId()){
            return 0;
        }else if(selected == onDutyBtn.getId()){
            return 1;
        }else if(selected == drivingBtn.getId()){
            return 2;
        }else{
            return 0;
        }
    }

    public String getNote(){
        return textField.getText().toString();
    }

    private int getID(){
        id++;
        return id;
    }
}
