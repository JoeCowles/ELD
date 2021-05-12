package com.carriergistics.eld.dvir;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import com.carriergistics.eld.BuildConfig;
import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;
import static com.carriergistics.eld.fueling.FuelingFragment.PHOTO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateDvirFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateDvirFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "vinNum";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_TAKE_PHOTO = 1;
    private RadioButton preTripBtn, postTripBtn;
    private RadioGroup group;
    private ConstraintLayout absLayout;
    private ScrollView scrollView;
    private LinearLayout list;
    private Button addBtn, saveBtn;
    private ArrayList<ImageView> pictureViews;
    private ArrayList<Issue> issues;
    private int selectedIssue = 0;
    private int selectedPicture;
    private String mCurrentPhotoPath;
    private Vehicle editing;
    private static int id = 9213;
    private boolean safe = true;
    // TODO: Rename and change types of parameters
    private String vinNum;
    private String mParam2;

    public CreateDvirFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateDvirFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateDvirFragment newInstance(String param1, String param2) {
        CreateDvirFragment fragment = new CreateDvirFragment();
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
            vinNum = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_dvir, container, false);
        Context context = getContext();
        pictureViews = new ArrayList<>();
        issues = new ArrayList<>();
        editing = MainActivity.getVehicleFromVin(vinNum);

        preTripBtn = new RadioButton(context);
        postTripBtn = new RadioButton(context);
        group = new RadioGroup(context);
        scrollView = new ScrollView(context);
        list = new LinearLayout(context);
        addBtn = new Button(context);
        absLayout = view.findViewById(R.id.createDvirLayout);
        saveBtn = new Button(context);

        group.setId(getID());
        scrollView.setId(getID());
        list.setId(getID());
        addBtn.setId(getID());
        saveBtn.setId(getID());

        group.addView(preTripBtn);
        group.addView(postTripBtn);
        absLayout.addView(group);

        scrollView.addView(list);
        absLayout.addView(scrollView);
        absLayout.addView(addBtn);
        absLayout.addView(saveBtn);

        scrollView.setMinimumHeight(200);
        scrollView.setMinimumWidth(1400);

        preTripBtn.setText("Pre-trip inspection");
        postTripBtn.setText("Post-trip inspection");

        preTripBtn.setTextSize(24f);
        postTripBtn.setTextSize(24f);

        addBtn.setText("Add issue");
        addBtn.setWidth(200);

        saveBtn.setText("Save");
        saveBtn.setTextSize(24f);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dvir dvir = null;
                if(group.getCheckedRadioButtonId() == preTripBtn.getId() && safe){
                    dvir = new Dvir(Dvir.TripType.PRE_TRIP, MainActivity.getTime(), Dvir.Safety.SAFE);
                }else if(group.getCheckedRadioButtonId() == preTripBtn.getId() && !safe){
                    dvir = new Dvir(Dvir.TripType.PRE_TRIP, MainActivity.getTime(), Dvir.Safety.UNSAFE);
                }else if(group.getCheckedRadioButtonId() == postTripBtn.getId() && !safe){
                    dvir = new Dvir(Dvir.TripType.POST_TRIP, MainActivity.getTime(), Dvir.Safety.UNSAFE);
                }else if(group.getCheckedRadioButtonId() == postTripBtn.getId() && safe){
                    dvir = new Dvir(Dvir.TripType.POST_TRIP, MainActivity.getTime(), Dvir.Safety.SAFE);
                }else{
                    Toast.makeText(getContext(), "Must select an inspection type", Toast.LENGTH_LONG);
                    return;
                }
                dvir.setIssues(issues);
                dvir.setDriverName(MainActivity.currentDriver.getFirst_name() + " " + MainActivity.currentDriver.getLast_name());
                editing.getDvirLog().add(dvir);
                MainActivity.instance.switchToFragment(VehicleFragment.class, "vin", vinNum);
            }
        });

        list.setOrientation(LinearLayout.VERTICAL);

        group.setPadding(0, 30, 0, 30);


        addBtn.setWidth(200);
        addBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final Issue issue = new Issue();
                issues.add(issue);
                final CardView card = new CardView(getContext());
                ConstraintLayout layout = new ConstraintLayout(getContext());
                final ImageView picture = new ImageView(getContext());
                final EditText remarks = new EditText(getContext());
                final Switch issueSafeSw = new Switch(getContext());

                ImageView deleteBtn = new ImageView(getContext());

                layout.setId(getID());
                picture.setId(getID());
                remarks.setId(getID());
                deleteBtn.setId(getID());
                issueSafeSw.setId(getID());

                layout.addView(picture);
                layout.addView(remarks);
                layout.addView(deleteBtn);
                layout.addView(issueSafeSw);


                card.setMinimumHeight(100);
                card.setMinimumWidth(1400);
                layout.setMinWidth(600);

                picture.setImageResource(R.drawable.ic_baseline_photo_camera_24);
                pictureViews.add(picture);
                picture.setMinimumWidth(200);
                picture.setMinimumHeight(200);

                issueSafeSw.setText("Safe");
                issueSafeSw.setChecked(true);
                issueSafeSw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        issue.setSafe(issueSafeSw.isChecked());
                        if(!issueSafeSw.isChecked()){
                            safe = false;
                        }else{
                            safe = true;
                            for(Issue temp : issues){
                                if(!temp.isSafe()){
                                    safe = false;
                                }
                            }

                        }
                    }
                });

                remarks.setHint("Remarks");
                remarks.setPadding(0, 0, 10,0);
                remarks.setWidth(450);
                remarks.setMinHeight(75);
                remarks.setTextSize(24f);
                remarks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            issue.setRemarks(remarks.getText().toString());
                        }
                    }
                });
                deleteBtn.setImageResource(R.drawable.ic_baseline_delete_24);
                deleteBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Warning!").setMessage("Are you sure you want to delete this issue?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                list.removeView(card);
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
                picture.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        selectedPicture = pictureViews.indexOf(picture);
                        selectedIssue = issues.indexOf(issue);
                        try {
                            dispatchTakePictureIntent();
                            Log.d("DEBUGGING", "Photo was clicked!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
                ConstraintSet cardSet = new ConstraintSet();
                cardSet.clone(layout);

                cardSet.connect(picture.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
                cardSet.applyTo(layout);

                cardSet.connect(remarks.getId(), ConstraintSet.LEFT, picture.getId(), ConstraintSet.RIGHT);
                cardSet.applyTo(layout);

                cardSet.connect(deleteBtn.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
                cardSet.connect(deleteBtn.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
                cardSet.applyTo(layout);

                cardSet.connect(issueSafeSw.getId(), ConstraintSet.TOP, remarks.getId(), ConstraintSet.BOTTOM);
                cardSet.connect(issueSafeSw.getId(), ConstraintSet.LEFT, picture.getId(), ConstraintSet.RIGHT);
                cardSet.applyTo(layout);

                issue.setSafe(issueSafeSw.isChecked());

                card.addView(layout);
                list.addView(card);
            }
        });

        ConstraintSet set = new ConstraintSet();
        set.clone(absLayout);


        set.connect(addBtn.getId(), ConstraintSet.TOP, group.getId(), ConstraintSet.BOTTOM);
        set.applyTo(absLayout);

        set.connect(scrollView.getId(), ConstraintSet.TOP, addBtn.getId(), ConstraintSet.BOTTOM);
        //set.connect(scrollView.getId(), ConstraintSet.BOTTOM, absLayout.getId(), ConstraintSet.BOTTOM);
        set.connect(scrollView.getId(), ConstraintSet.RIGHT, absLayout.getId(), ConstraintSet.RIGHT);
        set.connect(scrollView.getId(), ConstraintSet.LEFT, absLayout.getId(), ConstraintSet.LEFT);
        set.applyTo(absLayout);

        set.connect(saveBtn.getId(), ConstraintSet.BOTTOM, absLayout.getId(), ConstraintSet.BOTTOM);
        set.connect(saveBtn.getId(), ConstraintSet.RIGHT, absLayout.getId(), ConstraintSet.RIGHT);
        set.connect(saveBtn.getId(), ConstraintSet.LEFT, absLayout.getId(), ConstraintSet.LEFT);
        set.applyTo(absLayout);

        return view;
    }

    private int getID() {
        return id++;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView
            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
                Bitmap picture = BitmapFactory.decodeStream(ims);
                pictureViews.get(selectedPicture).setImageBitmap(Bitmap.createScaledBitmap(picture, 200, 200, false));
                issues.get(selectedIssue).setPicturePath(imageUri.getPath());
            } catch (FileNotFoundException e) {
                return;
            }

            // ScanFile so it will be appeared on Gallery
            MediaScannerConnection.scanFile(MainActivity.instance,
                    new String[]{imageUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(MainActivity.getTime());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Camera");
        if(!storageDir.exists()){
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(MainActivity.instance.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}