package com.carriergistics.eld.mapping;

import android.util.Log;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.mapping.load.Load;


import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

public class MappingXMLParser {



    public static Load deserialize(int resID) throws Exception {

        Serializer serializer = new Persister();
       // Log.d("DEBUGGING", "THE FILE WAS VALIDATED: " + serializer.validate(Load.class,
       //         MainActivity.instance.getResources().openRawResource(resID)));


        //return (Load) serializer.read(Load.class, new StringReader(xml));
        return (Load) serializer.read(Load.class, MainActivity.instance.getResources().openRawResource(resID));
        //return null;
    }

    public static Load deserialize(String xml) throws Exception {

        Serializer serializer = new Persister();
        Log.d("DEBUGGING", "THE FILE WAS VALIDATED: " + serializer.validate(Load.class, new StringReader(xml)));

        return (Load) serializer.read(Load.class, new StringReader(xml));

    }

    // TODO: Change this so that it doesn't use a String (it isn't big enough)
    public static String serialize(Load load) throws Exception {

        Serializer serializer = new Persister();
        StringWriter writer = new StringWriter();
        serializer.write(load, writer);

        return writer.toString();

    }


}
