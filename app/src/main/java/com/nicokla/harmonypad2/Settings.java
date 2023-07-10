package com.nicokla.harmonypad2;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Hashtable;
import java.lang.*;

import static java.lang.Math.abs;

public class Settings extends Fragment implements View.OnTouchListener {
    MainActivity activity;
    View myView;
    int check = 0;
    TableLayout mTlayout;
    TableRow tr;
    String typeGamme;
    String[] scales_names;


    private SeekBar seekBar;
    //Hashtable<Integer, Integer> idToInt;
    //Hashtable<Integer, Integer> idToTypeAccord;
    Spinner spinner1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.settings, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fragment locked in portrait screen orientation
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        //ImageView imageView = (ImageView) getView().findViewById(R.id.foo);
        // or  (ImageView) view.findViewById(R.id.foo);
        ////////////////////////
        activity=(MainActivity) getActivity();
        myView=getView();

        scales_names = getResources().getStringArray(R.array.scales_names);
        typeGamme=scales_names[0];//"All";
        //idToInt=new Hashtable<>();

        /////////////////////////////////////////////

        seekBar = view.findViewById(R.id.seekBar1);
        seekBar.setProgress(activity.midiPlayer.volume);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                activity.midiPlayer.volume = progresValue;}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        View v;

        spinner1 = (Spinner) activity.findViewById(R.id.spinner1);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
            (activity, android.R.layout.simple_spinner_item,
            activity.midiPlayer.nomsInstrumentsArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner1.setAdapter(spinnerArrayAdapter);
        spinner1.setSelection(activity.midiPlayer.instrument);
        addListenerOnSpinnerItemSelection();

        mTlayout = (TableLayout) activity.findViewById(R.id.tableLayout1);
        activity.addButtonsPiano(mTlayout, this, 3);
    }


    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) activity.findViewById(R.id.spinner1);
        //String s = spinner1.getSelectedItem().toString();

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                if(++check > 1) {
                    //TextView textView = (TextView)view;
                    //typeGamme = spinner1.getSelectedItem().toString();//textView.getText().toString();
                    //addButtons();
                    activity.midiPlayer.instrument=
                    spinner1.getSelectedItemPosition();
                    activity.midiPlayer.changeInstrument(activity.midiPlayer.instrument);

                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        int id = v.getId();
        int tag;
        Object i = activity.idToNumPiano.get(id);
        if(i!=null){ // one of the buttons stored in the matrix
            //i=(int)i;
            tag=(int) v.getTag();
            switch(action){
                case MotionEvent.ACTION_DOWN:
                    activity.midiPlayer.jouerNote(tag);
                    v.performClick();
                    break;
                case MotionEvent.ACTION_UP:
                    activity.midiPlayer.stopNote(tag);
                    break;
            }
        }else{ // other button (not in matrix)
            switch(action) {
                case MotionEvent.ACTION_DOWN:
                    switch (id) {
                        default:
                            break;
                    }
                    v.performClick();
                    break;

                case MotionEvent.ACTION_UP:
                    switch(id){
                        default:
                            break;
                    }
                    break;
            }
            return false;
        }
        return false;
    }


}




