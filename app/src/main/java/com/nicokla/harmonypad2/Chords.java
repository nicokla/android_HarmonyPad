package com.nicokla.harmonypad2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.lang.*;

import static java.lang.Math.abs;

public class Chords extends Fragment implements View.OnTouchListener {
    MainActivity activity;
    View myView;
    Fragment f;
    private Spinner spinner2;
    private Spinner spinner3;
    //GridLayout gridLayout1;
    int origineDuMode;
    TableLayout tableLayout1;
    TableRow tr;
    DisplayMetrics displayMetrics;
    float dpHeight ;
    float dpWidth;
    float density;

    //String typeGammeChords;
    //String typeDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.chords, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fragment locked in portrait screen orientation
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        activity=(MainActivity) getActivity();
        myView=getView();

        activity.idToNumChords.clear();
        activity.idToLastTypeAccord.clear();

        f=this;

        Spinner spinner2 = activity.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.array2, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        addListenerOnSpinnerItemSelection();
        spinner2.setSelection(activity.midiPlayer.typeGammeChordsInt);

        Spinner spinner3 = activity.findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(activity,
                R.array.array3, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        addListenerOnSpinnerItemSelection3();
        spinner3.setSelection(activity.midiPlayer.typeDisplayInt);

        tableLayout1 = activity.findViewById(R.id.tableLayout1);
        activity.addButtonsChords(tableLayout1, this, false);
    }



    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int action = event.getAction();
        int id = v.getId();
        int num= activity.idToNumChords.get(id);
        int lastTypeAccord= activity.idToLastTypeAccord.get(id);
        if(lastTypeAccord!=3){
            activity.midiPlayer.lastTypeAccord=lastTypeAccord;
            activity.midiPlayer.num=num;
            switch(action){
                case MotionEvent.ACTION_DOWN:
                    activity.midiPlayer.jouerAccord();
                    v.performClick();
                    break;
                case MotionEvent.ACTION_UP:
                    activity.midiPlayer.finirAccord();
                    break;
            }
        } else {
            if (action == MotionEvent.ACTION_DOWN) {
                if(num==1){
                    activity.midiPlayer.keyModeMajeur =
                    (activity.midiPlayer.keyModeMajeur - 7) % 12 + 48;}
                else if (num==2){
                    activity.midiPlayer.keyModeMajeur =
                    (activity.midiPlayer.keyModeMajeur + 7) % 12 + 48;}
                    activity.addButtonsChords(tableLayout1, this, false);
                    v.performClick();
            }
        }
        return false;
    }


    public void addListenerOnSpinnerItemSelection() {
        spinner2 = (Spinner) activity.findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                activity.midiPlayer.typeGammeChords =
                    spinner2.getSelectedItem().toString();//textView.getText().toString();
                activity.midiPlayer.typeGammeChordsInt=
                    spinner2.getSelectedItemPosition();
                activity.addButtonsChords(tableLayout1, (View.OnTouchListener)f, false);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void addListenerOnSpinnerItemSelection3() {
        spinner3 = (Spinner) activity.findViewById(R.id.spinner3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                activity.midiPlayer.typeDisplay =
                    spinner3.getSelectedItem().toString();//textView.getText().toString();
                activity.midiPlayer.typeDisplayInt=
                    spinner3.getSelectedItemPosition();
                activity.addButtonsChords(tableLayout1, (View.OnTouchListener)f, false);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


}




