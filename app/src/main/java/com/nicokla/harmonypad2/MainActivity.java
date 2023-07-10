package com.nicokla.harmonypad2;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
/*  !! */


import java.lang.*;
import java.util.Hashtable;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {
    private int[][] all_lastTypeAccord;
    private int[][] all_num;

    private AdView mAdView; //!!
    public MidiPlayer midiPlayer;
    public Fragment frag; // create a Fragment Object
    DisplayMetrics displayMetrics;
    float dpHeight;
    float dpWidth;
    float pxHeight;
    float pxWidth;
    float density;
    Hashtable<Integer, Integer> idToNumPiano;
    TableRow tr;
    Hashtable<Integer, Integer> idToNumChords; // num
    Hashtable<Integer, Integer> idToLastTypeAccord; // lastTypeAccord

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-9490533862623645~1386276398");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);/* !! */

        //////////////////////////
        midiPlayer=new MidiPlayer(this);
        //Toast.makeText(getApplicationContext(), "coucou", Toast.LENGTH_SHORT).show(); // !!!

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        pxHeight=displayMetrics.heightPixels;
        pxWidth=displayMetrics.widthPixels;
        density=displayMetrics.density;
        dpHeight = pxHeight / density;
        dpWidth = pxWidth / density;

        if(frag==null){
            frag = new Piano();
            lanceFragment();
        }

        idToNumChords =new Hashtable<>();
        idToLastTypeAccord =new Hashtable<>();
        idToNumPiano = new Hashtable<>();

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

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();/*
        if (id == R.id.action_settings) {
            frag = new Settings();
            return lanceFragment();
        }*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        frag=null;

        switch(id){
            case R.id.piano:
                frag = new Piano();
                break;
            case R.id.chords:
                frag = new Chords();
                break;
            case R.id.pianoAndChords:
                frag = new PianoAndChords();
                break;
            case R.id.settings:
                frag = new Settings();
                break;
            case R.id.editChords:
                frag = new EditChords();
                break;
            default:
                break;
        }

        if(frag!=null)
            return lanceFragment();
        else
            return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        midiPlayer.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        midiPlayer.pause();
    }

    public boolean lanceFragment(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (frag != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, frag); // replace a Fragment with Frame Layout
            transaction.commit(); // commit the changes
            drawer.closeDrawers(); // close the all open Drawer Views // ...(GravityCompat.START)
        }
        return frag!=null;
    }


    public void addButtonsPiano(TableLayout mTlayout, View.OnTouchListener on, int lignes){
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float density=displayMetrics.density;

        int id;
        int tag;
        boolean b1, b2;
        int coucou;
        int coucou2;

        int largeur=12;
        switch(midiPlayer.typeGammePiano){
            case "All":
            case "All, colored":
            case "All, from major":
            case "All, from minor":
                largeur=12;
                break;
            case "Major":
            case "Minor Natural":
            case "Minor Harmonic":
            case "Minor Dorian":
            case "Minor Phrygian":
            case "Altered":
                largeur=7;
                break;
            case "Minor All":
                largeur=9;
                break;
            case "Penta Major":
            case "Penta Minor":
                largeur=5;
                break;
            case "Blues":
            case "Whole Tone":
                largeur=6;
                break;
            case "Bebop Dominant":
            case "Diminished 1":
            case "Diminished 2":
                largeur=8;
                break;
            default:
                break;
        }


        mTlayout.removeAllViews();

        idToNumPiano.clear();
        //intToId.clear();

        for(int i=0; i < largeur*lignes; i++){ //i<mTextofButton.length
            if (i % largeur == 0) {
                tr = new TableRow(this);
                mTlayout.addView(tr);
            }
            Button btn = new Button(this);

            tag=midiPlayer.getNoteDansGamme(midiPlayer.typeGammePiano,i);
            if(tag>=0 && tag <= 127) {
                btn.setTag(tag);

                switch (midiPlayer.typeGammePiano) {
                    case "All, colored":
                    case "All, from major":
                    case "All, from minor":
                        coucou = (tag - midiPlayer.keyModeMajeur + 240) % 12;
                        coucou = ((7 * coucou) % 12) - 2;
                        if (coucou > 6)
                            coucou -= 12;
                        b2 = abs(coucou) <= 3;

                        coucou2 = ((7 * tag) % 12) - 2;
                        if (coucou2 > 6)
                            coucou2 -= 12;
                        b1 = abs(coucou2) <= 3;

                        if (!b2) {
                            if (b1) {
                                btn.setBackgroundColor(0xFFCCCCCC);
                            } else {
                                btn.setBackgroundColor(0xFF333333);
                            }
                        } else {
                            if (b1) {
                                btn.setBackgroundColor(0xFFB4FFB4);
                            } else {
                                btn.setBackgroundColor(0xFF00B400);
                            }
                        }
                        break;
                    case "All":
                        coucou2 = ((7 * i) % 12) - 2;
                        if (coucou2 > 6)
                            coucou2 -= 12;
                        b1 = abs(coucou2) <= 3;

                        if (b1) {
                            btn.setBackgroundColor(0xFFCCCCCC);
                        } else {
                            btn.setBackgroundColor(0xFF333333);
                        }
                        break;
                    case "Minor All":
                        int a = i % 9;
                        switch (a) {
                            case 6:
                            case 8:
                                btn.setBackgroundColor(0xFF333333);
                                break;
                            default:
                                btn.setBackgroundColor(0xFFCCCCCC);
                                break;
                        }
                        break;
                    default:
                        btn.setBackgroundColor(0xFFCCCCCC);
                        break;
                }

                id = btn.getId();
                idToNumPiano.put(id, i); // tag ou i ?
                //intToId.put(i, id);

                btn.setOnTouchListener(on);
                btn.setAllCaps(false);
                btn.setText(midiPlayer.noteName.get(tag));
            }else{
                btn.setBackgroundColor(0xFFCCCCCC);
            }
            btn.setPadding(2,2,2,2);
            tr.addView(btn);
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    Math.round((displayMetrics.widthPixels - 4*largeur)/largeur),
                    Math.round(46 * density));// en dp
            params.setMargins(2, 2, 2, 2);
            btn.setLayoutParams(params);
        }
    }


    public void ajouteView(int id,
                           int lastTypeAccord,
                           int num,
                           View.OnTouchListener on,
                           boolean showVoicings){
        //all_lastTypeAccord[i][id] , all_num[i][id]
        Button btn = new Button(this);
        //btn.setTag(id);
        switch(lastTypeAccord){
            case 3:
                switch(num){
                    case 1:
                        btn.setText("←");
                        btn.getBackground().setTint(
                                getResources().getColor(R.color.colorButtonLeftRight));
                        break;
                    case 2:
                        btn.setText("→");
                        btn.getBackground().setTint(
                                getResources().getColor(R.color.colorButtonLeftRight));
                        break;
                    case 3:
                        TextView t = new TextView(this);
                        t.setTransformationMethod(null);
                        t.setText(midiPlayer.scaleName.get(midiPlayer.keyModeMajeur % 12));
                        t.setGravity(Gravity.CENTER);
                        tr.addView(t);
                        return; // !!!
                }
                break;
            case 1:
                //btn.getBackground().setTint(
                //    getResources().getColor(R.color.colorNormal));
                btn.setBackgroundColor(
                        getResources().getColor(R.color.colorNormal));
                if(showVoicings){
                    btn.setText(midiPlayer.getChordName(num)+" ("+
                            midiPlayer.getRenversementName(num)+")");
                }else{
                    btn.setText(midiPlayer.getChordName(num));
                }
                break;
            case 2:
                //btn.getBackground().setTint(
                //    getResources().getColor(R.color.colorNormal));
                btn.setBackgroundColor(
                        getResources().getColor(R.color.colorNormal));
                if(showVoicings){
                    btn.setText(midiPlayer.getChordName2(num)+" ("+
                            midiPlayer.getRenversementName2(num)+")");
                }else {
                    btn.setText(midiPlayer.getChordName2(num));
                }
                break;
        }

        idToLastTypeAccord.put(id, lastTypeAccord);
        idToNumChords.put(id, num);
        btn.setTransformationMethod(null);
        btn.setAllCaps(false);
        btn.setId(id);//View.generateViewId()
        btn.setOnTouchListener(on);

        //btn.setBackgroundColor(0xFFCCCCCC);
        btn.setPadding(2,2,2,2);
        tr.addView(btn);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                Math.round((displayMetrics.widthPixels - 4*7)/7),
                Math.round(46 * density));// en dp
        params.setMargins(2, 2, 2, 2);
        btn.setLayoutParams(params);

    }

    public void addButtonsChords(TableLayout tableLayout1,
                                 View.OnTouchListener on,
                                 boolean showVoicings){
        displayMetrics = this.getResources().getDisplayMetrics();
        dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        density=displayMetrics.density;

        tableLayout1.removeAllViews();
        idToNumChords.clear();
        idToLastTypeAccord.clear();

        // spinner2 // Major,Minor,Minor Dor ,Minor Phr
        // spinner3 // Normal,Fifth special,Fifth ,Third
        // j        // maj min dor phr fifth1 fifth2 third
        int j=0;
        //if(spinner3.getSelectedItemPosition()==0){//Normal
        if(midiPlayer.typeDisplayInt==0){//midiPlayer.typeDisplay=="Normal"){
            j=midiPlayer.typeGammeChordsInt;
        }else{
            j=midiPlayer.typeDisplayInt+3;
        }
        for(int i=0; i<21; i++) {
            if(i%7==0){
                tr = new TableRow(this);
                tableLayout1.addView(tr);
            }
            ajouteView(i, all_lastTypeAccord[j][i], all_num[j][i], on, showVoicings);
        }
    }


}


