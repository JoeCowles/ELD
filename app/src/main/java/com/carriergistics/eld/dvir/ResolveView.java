package com.carriergistics.eld.dvir;

import android.content.Context;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class ResolveView extends ConstraintLayout {

    private EditText nameView, notes;
    private int id = 213;
    public ResolveView(Context context) {
        super(context);

        nameView = new EditText(context);
        nameView.setId(getID());
        notes = new EditText(context);
        notes.setId(getID());
        setId(getID());

        addView(nameView);
        addView(notes);

        nameView.setWidth(300);
        nameView.setHint("Name");

        notes.setWidth(600);
        notes.setHeight(400);
        notes.setHint("Notes");

        ConstraintSet set = new ConstraintSet();
        set.clone(this);

        set.connect(nameView.getId(), ConstraintSet.LEFT, this.getID(), ConstraintSet.LEFT);
        set.connect(nameView.getId(), ConstraintSet.RIGHT, this.getID(), ConstraintSet.RIGHT);
        set.connect(nameView.getId(), ConstraintSet.TOP, this.getID(), ConstraintSet.TOP);
        set.applyTo(this);

        set.connect(notes.getId(), ConstraintSet.TOP, nameView.getId(), ConstraintSet.BOTTOM);
        set.connect(notes.getId(), ConstraintSet.RIGHT, this.getId(), ConstraintSet.RIGHT);
        set.connect(notes.getId(), ConstraintSet.LEFT, this.getId(), ConstraintSet.LEFT);
        set.applyTo(this);

    }
    public String getNotes(){
        return notes.getText().toString();
    }
    public String getResolverName(){
        return nameView.getText().toString();
    }

    private int getID(){
        return id++;
    }



}
