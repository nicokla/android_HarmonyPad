package com.nicokla.harmonypad2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TableLayout;

import java.util.Hashtable;

public class EditChords extends Fragment implements View.OnTouchListener {
    MainActivity activity;
    View myView;
    TableLayout tableLayout1;

    //private int[] intToId;
    //private int[] intToId2;
    //Hashtable<Integer, Integer> idToInt;
    //Hashtable<Integer, Integer> idToTypeAccord;
    Button btn_current;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_chords, container, false);
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

        tableLayout1 = activity.findViewById(R.id.tableLayout1);
        /*idToInt=new Hashtable<>();
        idToTypeAccord=new Hashtable<>();
        for(int i=0; i<9; i++){
            idToInt.put(intToId[i], i);
            idToInt.put(intToId2[i], i);
            idToTypeAccord.put(intToId[i],1); // 3 notes : typeAccord=1
            idToTypeAccord.put(intToId2[i],2); // 4 notes : typeAccord=2
        }*/
        Button button = view.findViewById(R.id.button72);
        if (activity.midiPlayer.arpege)
            button.setText("Arpeggio");
        else
            button.setText("Chord");
        //btn_current=myView.findViewById(R.id.button11);
        /*if(activity.midiPlayer.lastTypeAccord==1){
            btn_current=activity.
                //myView.findViewById(
                //intToId[activity.midiPlayer.num]);
        }else{
            btn_current=
                myView.findViewById(
                intToId2[activity.midiPlayer.num]);
        }
        btn_current.getBackground().setTint(getResources().getColor(R.color.colorButtonLeftRight));
*/
        btn_current=new Button(activity);

        /////////////////////////////////////////////

        View v;

        activity.addButtonsChords(tableLayout1,this, true);

        v = view.findViewById(R.id.button72);
        v.setOnTouchListener(this);

        v = view.findViewById(R.id.button92);
        v.setOnTouchListener(this);
        v = view.findViewById(R.id.button93);
        v.setOnTouchListener(this);

        activity.addButtonsChords(tableLayout1, this, true);
    }



    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int action = event.getAction();
        int id = v.getId();
        Object num= activity.idToNumChords.get(id);
        Object lastTypeAccord= activity.idToLastTypeAccord.get(id);
        try{
            if((int)lastTypeAccord!=3){
                activity.midiPlayer.lastTypeAccord=(int)lastTypeAccord;
                activity.midiPlayer.num=(int)num;

                btn_current.setBackgroundColor(
                        getResources().getColor(R.color.colorNormal));
                //getBackground()
                //.setTint(getResources()
                //.getColor(R.color.colorNormal));
                btn_current=myView.findViewById(id);
                btn_current.setBackgroundColor(
                        getResources().getColor(R.color.colorButtonLeftRight));

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
                    if((int)num==1){
                        activity.midiPlayer.keyModeMajeur =
                                (activity.midiPlayer.keyModeMajeur - 7) % 12 + 48;}
                    else if((int)num==2){
                        activity.midiPlayer.keyModeMajeur =
                                (activity.midiPlayer.keyModeMajeur + 7) % 12 + 48;}
                    activity.addButtonsChords(tableLayout1, this, true);
                    v.performClick();
                }
            }
        }catch(Exception ex) {
            switch(action) {
                case MotionEvent.ACTION_DOWN:
                    switch (id) {
                        case R.id.button72:
                            activity.midiPlayer.arpege = !activity.midiPlayer.arpege;
                            Button button = myView.findViewById(R.id.button72);
                            if (activity.midiPlayer.arpege)
                                button.setText("Arpeggio");
                            else
                                button.setText("Chord");
                            activity.midiPlayer.jouerAccord();
                            break;
                        case R.id.button92:
                            //if (activity.midiPlayer.modeChangeChords) {
                            activity.midiPlayer.diminuerRenversement();
                            activity.addButtonsChords(tableLayout1, this, true);
                            //}
                            break;
                        case R.id.button93:
                            //if (activity.midiPlayer.modeChangeChords) {
                            activity.midiPlayer.augmenterRenversement();
                            activity.addButtonsChords(tableLayout1, this, true);
                            //}
                            break;
                        default:
                            break;
                    }
                    v.performClick();
                    break;
                case MotionEvent.ACTION_UP:
                    switch(id){
                        case R.id.button92:
                        case R.id.button93:
                        case R.id.button72:
                            //if (activity.midiPlayer.modeChangeChords)
                            activity.midiPlayer.finirAccord();
                            break;
                        default:
                            break;
                    }
                    break;
            }
        }
        return false;
    }

}





