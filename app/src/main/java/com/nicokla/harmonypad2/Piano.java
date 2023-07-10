package com.nicokla.harmonypad2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import java.lang.*;
import java.util.Hashtable;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

import static java.lang.Math.abs;

public class Piano extends Fragment implements View.OnTouchListener{//
    MainActivity activity;
    View myView;
    Fragment f;
    TableLayout mTlayout;
    private Spinner spinner1;


    //Hashtable<Integer, Integer> intToId;
    String[] scales_names;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.piano, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        activity=(MainActivity) getActivity();
        myView=getView();
        f=this;
        //scales_names = getResources().getStringArray(R.array.scales_names);
        //activity.midiPlayer.typeGammePiano=scales_names[1];//"All, colored";

        //intToId=new Hashtable<>();
        activity.idToNumPiano.clear();

        View v;
        v = view.findViewById(R.id.button71);
        v.setOnTouchListener(this);
        v = view.findViewById(R.id.button73);
        v.setOnTouchListener(this);
        v = view.findViewById(R.id.buttonAigu);
        v.setOnTouchListener(this);
        v = view.findViewById(R.id.buttonGrave);
        v.setOnTouchListener(this);

        changeNom();

        Spinner spinner1 = (Spinner) activity.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.scales_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        addListenerOnSpinnerItemSelection();
        //spinner1.setSelection(1); // 2eme element, "All, colored"
        spinner1.setSelection(activity.midiPlayer.typeGammePianoInt);

        TextView b = myView.findViewById(R.id.button_scaleName);
        b.setGravity(Gravity.CENTER);

        mTlayout = (TableLayout) activity.findViewById(R.id.tableLayout1);
        activity.addButtonsPiano(mTlayout, this, 4);
    }



    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) activity.findViewById(R.id.spinner1);
        //String s = spinner1.getSelectedItem().toString();

        spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                //TextView textView = (TextView)view;
                activity.midiPlayer.typeGammePiano = spinner1.getSelectedItem().toString();//textView.getText().toString();
                activity.midiPlayer.typeGammePianoInt=
                        spinner1.getSelectedItemPosition();
                activity.addButtonsPiano(mTlayout, (View.OnTouchListener)f, 4);
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
                    case R.id.button71:
                        activity.midiPlayer.keyModeMajeur = (activity.midiPlayer.keyModeMajeur - 7) % 12 + 48;
                        changeNom();
                        activity.addButtonsPiano(mTlayout, this, 4);
                        break;
                    case R.id.button73:
                        activity.midiPlayer.keyModeMajeur = (activity.midiPlayer.keyModeMajeur + 7) % 12 + 48;
                        changeNom();
                        activity.addButtonsPiano(mTlayout, this, 4);
                        break;
                    case R.id.buttonGrave:
                        activity.midiPlayer.delta -= 12;
                        activity.addButtonsPiano(mTlayout, this, 4);
                        break;
                    case R.id.buttonAigu:
                        activity.midiPlayer.delta += 12;
                        activity.addButtonsPiano(mTlayout, this, 4);
                        break;
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

    protected void changeNom(){
        TextView t;
        t= myView.findViewById(R.id.button_scaleName);
        t.setText(activity.midiPlayer.scaleName.get(activity.midiPlayer.keyModeMajeur % 12));
    }

}




