package com.nicokla.harmonypad2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Hashtable;

import static java.lang.Math.abs;

public class PianoAndChords extends Fragment implements View.OnTouchListener {
    MainActivity activity;
    View myView;
    //Hashtable<Integer, Integer> idToNumChords; // num
    //Hashtable<Integer, Integer> idToNumPiano; // num
    //Hashtable<Integer, Integer> idToLastTypeAccord; // lastTypeAccord
    //GridLayout gridLayout1;
    int origineDuMode;
    TableLayout tableLayout1;
    TableLayout tableLayout2;

    TableRow tr;
    DisplayMetrics displayMetrics;
    float dpHeight ;
    float dpWidth;
    float density;

    private int[][] all_lastTypeAccord;
    private int[][] all_num;
    String typeGammeChords;
    String typeGammePiano;
    String typeDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.piano_and_chords, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        activity=(MainActivity) getActivity();
        myView=getView();

        all_lastTypeAccord = new int[][]{
        {1,1,1,1,1,1,1,2,2,2,2,2,2,2,1,2,1,2,3,3,3},//maj
        {1,1,1,1,1,1,1,2,2,2,2,2,2,2,1,2,1,2,3,3,3},//min
        {1,1,1,1,1,1,1,2,2,2,2,2,2,2,1,2,1,2,3,3,3},//dor
        {1,1,1,1,1,1,1,2,2,2,2,2,2,2,1,2,1,2,3,3,3},//phr
        {1,1,1,2,2,2,3,1,1,1,2,2,2,3,1,1,1,2,2,2,3},//fifth1
        {1,1,1,1,1,1,1,2,2,2,2,2,2,2,1,2,1,2,3,3,3},//fifth2
        {1,1,1,1,1,1,1,2,2,2,2,2,2,2,1,2,1,2,3,3,3}//third
        };

        all_num = new int[][]{
        {1,3,5,0,2,4,7,1,3,5,0,2,4,7,6,6,8,8,1,2,3},//maj
        {4,7,1,3,5,0,2,4,7,1,3,5,0,2,6,6,8,8,1,2,3},//min
        {3,5,0,2,4,7,1,3,5,0,2,4,7,1,6,6,8,8,1,2,3},//dor
        {5,0,2,4,7,1,3,5,0,2,4,7,1,3,6,6,8,8,1,2,3},//phr
        {0,1,2,0,1,2,3,3,4,5,3,4,5,1,6,7,8,6,7,8,2},//fifth1
        {0,1,2,3,4,5,7,0,1,2,3,4,5,7,6,6,8,8,1,2,3},//fifth2
        {3,0,4,1,5,2,7,3,0,4,1,5,2,7,6,6,8,8,1,2,3}//third
        };

        //idToNumChords =new Hashtable<>();
        //idToNumPiano =new Hashtable<>();

        //idToLastTypeAccord =new Hashtable<>();

        //typeGammeChords="Majeur";  //Major,Minor,Minor Dor ,Minor Phr
        //typeDisplay= "Normal";  //Normal,Fifth special,Fifth ,Third

        tableLayout1 = activity.findViewById(R.id.tableLayout1);
        tableLayout2 = activity.findViewById(R.id.tableLayout2);

        activity.addButtonsPiano(tableLayout1,this, 3);
        activity.addButtonsChords(tableLayout2,this, false);
    }



    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int action = event.getAction();
        int id = v.getId();

        Object lastTypeAccord= activity.idToLastTypeAccord.get(id);
        Object num= activity.idToNumChords.get(id);

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
        }else if((int)lastTypeAccord!=3){
            activity.midiPlayer.lastTypeAccord=(int)lastTypeAccord;
            activity.midiPlayer.num=(int)num;
            switch(action){
                case MotionEvent.ACTION_DOWN:
                    activity.midiPlayer.jouerAccord();
                    //activity.midiPlayer.jouerNote(tag);
                    v.performClick();
                    break;
                case MotionEvent.ACTION_UP:
                    activity.midiPlayer.finirAccord();
                    //activity.midiPlayer.stopNote(tag);
                    break;
            }
        } else {
            if (action == MotionEvent.ACTION_DOWN) {
                if((int)num==1){
                    activity.midiPlayer.keyModeMajeur =
                    (activity.midiPlayer.keyModeMajeur - 7) % 12 + 48;
                } else if ((int)num==2){
                    activity.midiPlayer.keyModeMajeur =
                    (activity.midiPlayer.keyModeMajeur + 7) % 12 + 48;
                }
                activity.addButtonsPiano(tableLayout1,this, 3);
                activity.addButtonsChords(tableLayout2,this, false);
                v.performClick();
            }
        }
        return false;
    }




}




