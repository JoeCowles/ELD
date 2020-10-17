package com.carriergistics.eld.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class CustomTextView extends TextView {
    String attribute;
    public CustomTextView(Context context, String attribute) {
        super(context);
        this.attribute = attribute;
    }

    public void setAttribute(String attribute){
        this.attribute = attribute;
    }
    public String getAttribute() {
        return attribute;
    }

}
