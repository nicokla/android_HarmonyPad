package com.nicokla.harmonypad2;

import android.content.Context;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.lang.*;

import org.billthefarmer.mididriver.MidiDriver;

public class MidiPlayer implements MidiDriver.OnMidiStartListener{

    private Context context;

    public MidiDriver midiDriver;
    public int[] config;
    public int keyModeMajeur;
    public List<String> notes;
    public int instrument;
    public int pianoOuGuitare;
    public int volume;
    public boolean arpege;
    public int dtArpege;
    public List<String> nomsInstruments;
    public ArrayList<String> nomsInstrumentsArray;
    public int[] ci;//chord indexes
    public int[][] cv; // chord vectors
    public int[] ci2;
    public int[][] cv2;
    public int[] deca;
    public List<String> typeAccord;
    public List<String> typeAccord2;
    public List<Integer> gammeMaj;
    public List<Integer> gammeMin;
    public List<Integer> gammeBlues;
    public List<Integer> gammeMinH;
    public List<Integer> gammeMinD;
    public List<Integer> gammePentaMaj;
    public List<Integer> gammePentaMin;
    public List<Integer> gammeMajP;
    public List<Integer> gammeMinP;
    public List<Integer> gammeMajM;
    public List<Integer> gammeMin_9;
    public List<String> scaleName;
    public List<String> noteName;
    public List<Integer> gammeWholeTone;
    public List<Integer> gammeBebopDominant;
    public List<Integer> gammeAltered;
    public List<Integer> gammeDiminished_1;
    public List<Integer> gammeDiminished_2;

    public boolean modeChangeChords;
    public int lastTypeAccord;
    public int num;
    public int delta;
    public String typeGammeChords;
    public String typeDisplay;
    public String typeGammePiano;
    public int typeGammeChordsInt;
    public int typeDisplayInt;
    public int typeGammePianoInt;
    public String[] scales_names;

    public MidiPlayer(Context current) {
        this.context=current;
        midiDriver = new MidiDriver();
        midiDriver.setOnMidiStartListener(this);

        delta=0;
        deca = new int[]{5, 0, 7, 2, 9, 4, 2, 11, 4};
        modeChangeChords = false;
        ci = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        scaleName = Arrays.asList("0 (C, Am)", "-5 (C#, Bbm)", "+2 (D, Bm)", "-3 (Eb, Cm)", "+4 (E, C#m)",
        "-1 (F, Dm)", "+6 (F#, Ebm)", "+1 (G, Em)",  "-4 (Ab, Fm)","+3 (A, F#m)", "-2 (Bb, Gm)","+5 (B, Abm)");
        noteName=Arrays.asList(
            "C-1","C#-1","D-1","Eb-1","E-1","F-1","F#-1","G-1","Ab-1","A-1","Bb-1","B-1",
            "C0","C#0","D0","Eb0","E0","F0","F#0","G0","Ab0","A0","Bb0","B0",
            "C1","C#1","D1","Eb1","E1","F1","F#1","G1","Ab1","A1","Bb1","B1",
            "C2","C#2","D2","Eb2","E2","F2","F#2","G2","Ab2","A2","Bb2","B2",
            "C3","C#3","D3","Eb3","E3","F3","F#3","G3","Ab3","A3","Bb3","B3",
            "C4","C#4","D4","Eb4","E4","F4","F#4","G4","Ab4","A4","Bb4","B4",
            "C5","C#5","D5","Eb5","E5","F5","F#5","G5","Ab5","A5","Bb5","B5",
            "C6","C#6","D6","Eb6","E6","F6","F#6","G6","Ab6","A6","Bb6","B6",
            "C7","C#7","D7","Eb7","E7","F7","F#7","G7","Ab7","A7","Bb7","B7",
            "C8","C#8","D8","Eb8","E8","F8","F#8","G8","Ab8","A8","Bb8","B8",
            "C9","C#9","D9","Eb9","E9","F9","F#9","G9");

        cv = new int[][]{
            {0, 4, 7},
            {0, 4, 7},
            {0, 4, 7},
            {0, 3, 7},
            {0, 3, 7},
            {0, 3, 7},
            {0, 4, 7},
            {0, 3, 6},
            {0, 4, 7}
        };
        ci2 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        cv2 = new int[][]{
                {0, 4, 7, 11},
                {0, 4, 7, 11},
                {0, 4, 7, 10},
                {0, 3, 7, 10},
                {0, 3, 7, 10},
                {0, 3, 7, 10},
                {0, 4, 7, 10},
                {0, 3, 6, 10},
                {0, 4, 7, 10}
        };

        gammeMaj = Arrays.asList(0, 2, 4, 5, 7, 9, 11, 12);
        gammePentaMaj = Arrays.asList(0, 2, 4, 7, 9, 12);
        gammeMajP = Arrays.asList(0, 1, 4, 5, 7, 8, 10, 12);
        gammeMajM = Arrays.asList(0, 2, 4, 5, 7, 9, 10, 12);

        gammeMin = Arrays.asList(0, 2, 3, 5, 7, 8, 10, 12);
        gammePentaMin = Arrays.asList(0, 3, 5, 7, 10, 12);
        gammeMinH = Arrays.asList(0, 2, 3, 5, 7, 8, 11, 12);
        gammeMinD = Arrays.asList(0, 2, 3, 5, 7, 9, 10, 12);
        gammeMinP = Arrays.asList(0, 1, 3, 5, 7, 8, 10, 12);
        gammeMin_9 = Arrays.asList(0, 2, 3, 5, 7, 8, 9, 10, 11, 12);
        gammeBlues = Arrays.asList(0, 3, 5, 6, 7, 10, 12);
        gammeWholeTone = Arrays.asList(0, 2, 4, 6, 8, 10, 12);
        gammeBebopDominant = Arrays.asList(0, 2, 4, 5, 7, 9, 10, 11, 12);
        //gammeBebopMajor= Arrays.asList(0, 2, 4, 5, 7, 9, 10, 11, 12);
        //gammeBebopMinor = Arrays.asList(0, 2, 4, 5, 7, 9, 10, 11, 12);
        gammeAltered = Arrays.asList(0, 1, 3, 4, 6, 8, 10, 12);
        gammeDiminished_1 = Arrays.asList(0, 2, 3, 5, 6, 8, 9, 11, 12);
        gammeDiminished_2 = Arrays.asList(0, 1, 3, 4, 6, 7, 9, 10, 12);;

        notes = Arrays.asList("C", "C#", "D", "Eb", "E", "F",
                "F#", "G", "Ab", "A", "Bb", "B");

        typeAccord = Arrays.asList("", "", "", "m", "m", "m",
                "", "o", "");
        typeAccord2 = Arrays.asList("Δ", "Δ", "7", "m7", "m7", "m7",
                "7", "o7", "7");

        nomsInstruments = Arrays.asList("Acoustic Grand Piano", "Bright Acoustic Piano", "Electric Grand Piano",
                "Honky-tonk Piano", "Electric Piano 1", "Electric Piano 2", "Harpsichord", "Clavi", "Celesta", "Glockenspiel",
                "Music Box", "Vibraphone", "Marimba", "Xylophone", "Tubular Bells", "Dulcimer", "Drawbar Organ", "Percussive Organ", "Rock Organ", "Church Organ",
                "Reed Organ", "Accordion", "Harmonica", "Tango Accordion", "Acoustic Guitar (nylon)", "Acoustic Guitar (steel)",
                "Electric Guitar (jazz)", "Electric Guitar (clean)", "Electric Guitar (muted)", "Overdriven Guitar",
                "Distortion Guitar", "Guitar harmonics", "Acoustic Bass", "Electric Bass (finger)",
                "Electric Bass (pick)", "Fretless Bass", "Slap Bass 1", "Slap Bass 2", "Synth Bass 1", "Synth Bass 2", "Violin",
                "Viola", "Cello", "Contrabass", "Tremolo Strings", "Pizzicato Strings", "Orchestral Harp", "Timpani", "String Ensemble 1", "String Ensemble 2",
                "SynthStrings 1", "SynthStrings 2", "Choir Aahs", "Voice Oohs", "Synth Voice", "Orchestra Hit",
                "Trumpet", "Trombone", "Tuba", "Muted Trumpet", "French Horn", "Brass Section", "SynthBrass 1", "SynthBrass 2",
                "Soprano Sax", "Alto Sax", "Tenor Sax", "Baritone Sax", "Oboe", "English Horn", "Bassoon", "Clarinet", "Piccolo", "Flute", "Recorder", "Pan Flute",
                "Blown Bottle", "Shakuhachi", "Whistle", "Ocarina", "Lead 1 (square)", "Lead 2 (sawtooth)",
                "Lead 3 (calliope)", "Lead 4 (chiff)", "Lead 5 (charang)", "Lead 6 (voice)", "Lead 7 (fifths)", "Lead 8 (bass + lead)", "Pad 1 (new age)", "Pad 2 (warm)",
                "Pad 3 (polysynth)", "Pad 4 (choir)", "Pad 5 (bowed)", "Pad 6 (metallic)", "Pad 7 (halo)", "Pad 8 (sweep)",
                "FX 1 (rain)", "FX 2 (soundtrack)", "FX 3 (crystal)", "FX 4 (atmosphere)", "FX 5 (brightness)", "FX 6 (goblins)", "FX 7 (echoes)", "FX 8 (sci-fi)",
                "Sitar", "Banjo", "Shamisen", "Koto", "Kalimba", "Bag pipe", "Fiddle", "Shanai", "Tinkle Bell", "Agogo", "Steel Drums", "Woodblock", "Taiko Drum",
                "Melodic Tom", "Synth Drum", "Reverse Cymbal", "Guitar Fret Noise", "Breath Noise", "Seashore", "Bird Tweet", "Telephone Ring", "Helicopter",
                "Applause", "Gunshot");
        nomsInstrumentsArray=new ArrayList<String>();
        nomsInstrumentsArray.addAll(nomsInstruments);

        //System.out.println(supplierNames.get(1));
        instrument = 0;
        pianoOuGuitare = 0;
        volume = 100; //max=127
        keyModeMajeur = 48;
        arpege = false;
        dtArpege = 60;

        lastTypeAccord = 1;
        num = 0;

        typeGammeChords="Majeur";  //Major,Minor,Minor Dor ,Minor Phr
        typeDisplay= "Normal";  //Normal,Fifth special,Fifth ,Third

        typeGammePianoInt=1;
        scales_names =
            context.getResources().getStringArray(R.array.scales_names);
        typeGammePiano=scales_names[typeGammePianoInt];//"All, colored";
        typeDisplay= context
            .getResources()
            .getStringArray(R.array.array3)
            [typeDisplayInt];
        typeGammeChords= context
            .getResources()
            .getStringArray(R.array.array2)
            [typeGammeChordsInt];
    }

    public void resume() {
        midiDriver.start();

        // Get the configuration.
        config = midiDriver.config();

        // Print out the details.
        Log.d(this.getClass().getName(), "maxVoices: " + config[0]);
        Log.d(this.getClass().getName(), "numChannels: " + config[1]);
        Log.d(this.getClass().getName(), "sampleRate: " + config[2]);
        Log.d(this.getClass().getName(), "mixBufferSize: " + config[3]); // !!!
    }

    public void pause() {
        midiDriver.stop();
    }

    @Override
    public void onMidiStart() {
        Log.d(this.getClass().getName(), "onMidiStart()");
    }


    public void jouerAccord(){
        switch (lastTypeAccord){
            case 1:
                startAccord(keyModeMajeur+deca[num],cv[num][0],cv[num][1], cv[num][2]);break;
            case 2:
                startAccord(keyModeMajeur+deca[num],cv2[num][0],cv2[num][1], cv2[num][2], cv2[num][3]); break;
        }
    }

    public void finirAccord(){
        switch (lastTypeAccord){
            case 1:
                stopAccord(keyModeMajeur+deca[num],cv[num][0],cv[num][1], cv[num][2]);break;
            case 2:
                stopAccord(keyModeMajeur+deca[num],cv2[num][0],cv2[num][1], cv2[num][2], cv2[num][3]); break;
        }
    }

    public void sendMidi(int m, int p)
    {
        byte msg[] = new byte[2];

        msg[0] = (byte) m;
        msg[1] = (byte) p;

        midiDriver.write(msg);
    }

    public void sendMidi(int m, int n, int v)
    {
        byte msg[] = new byte[3];

        msg[0] = (byte) m;
        msg[1] = (byte) n;
        msg[2] = (byte) v;

        midiDriver.write(msg);
    }

    // n in [0, 127]
    public void changeInstrument(int n){
        instrument=(n%128);
        sendMidi(0b11000000, (byte)instrument);
    }


    public void attend(int n){
        try {
            Thread.sleep(n);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void startAccord(int n, int p1, int p2, int p3)
    {
        if(arpege){
            sendMidi(0x90, n+p1, volume);
            attend(dtArpege);
            sendMidi(0x90, n+p2, volume);
            attend(dtArpege);
            sendMidi(0x90, n+p3, volume);
        } else{
            sendMidi(0x90, n+p1, volume);
            sendMidi(0x90, n+p2, volume);
            sendMidi(0x90, n+p3, volume);
        }
    }

    public void startAccord(int n, int p1, int p2, int p3, int p4)
    {
        if(arpege){
            sendMidi(0x90, n+p1, volume);
            attend(dtArpege);
            sendMidi(0x90, n+p2, volume);
            attend(dtArpege);
            sendMidi(0x90, n+p3, volume);
            attend(dtArpege);
            sendMidi(0x90, n+p4, volume);
        } else{
            sendMidi(0x90, n+p1, volume);
            sendMidi(0x90, n+p2, volume);
            sendMidi(0x90, n+p3, volume);
            sendMidi(0x90, n+p4, volume);
        }
    }

    public void stopAccord(int n, int p1, int p2, int p3)
    {
        byte msg[] = new byte[3];

        sendMidi(0x80, n+p1, 0);
        sendMidi(0x80, n+p2, 0);
        sendMidi(0x80, n+p3, 0);

        midiDriver.write(msg);
    }


    public void stopAccord(int n, int p1, int p2, int p3, int p4)
    {
        byte msg[] = new byte[3];

        sendMidi(0x80, n+p1, 0);
        sendMidi(0x80, n+p2, 0);
        sendMidi(0x80, n+p3, 0);
        sendMidi(0x80, n+p4, 0);

        midiDriver.write(msg);
    }

    public void joueGammeUp(int root, List<Integer> gamme){
        for(int i=0; i<gamme.size(); i++){
            sendMidi(0x90, root+gamme.get(i), volume);
            attend(120);
            sendMidi(0x80, root+gamme.get(i), 0);
        }
    }

    public void joueGammeDown(int root, List<Integer> gamme){
        for(int i=gamme.size()-1; i>=0; i--){
            sendMidi(0x90, root+gamme.get(i), volume);
            attend(120);
            sendMidi(0x80, root+gamme.get(i), 0);
        }
    }

    public void joueGammeUpAndDown(int root, List<Integer> gamme){
        joueGammeUp(root, gamme);
        attend(240);
        joueGammeDown(root, gamme);
    }

    public void diminuerRenversement(){
        int temp;
        if (lastTypeAccord == 1) {
            temp=cv[num][2];
            if(keyModeMajeur+deca[num]+temp-12 >= 0){
                ci[num] = (ci[num] + 2) % 3;
                cv[num][2]=cv[num][1];
                cv[num][1]=cv[num][0];
                cv[num][0]=temp-12;
            }
        } else if (lastTypeAccord == 2) {
            temp=cv2[num][3];
            if(keyModeMajeur+deca[num]+temp-12 >= 0){
                ci2[num] = (ci2[num] + 3) % 4;
                cv2[num][3]=cv2[num][2];
                cv2[num][2]=cv2[num][1];
                cv2[num][1]=cv2[num][0];
                cv2[num][0]=temp-12;
            }
        }
        jouerAccord();
    }


    public void augmenterRenversement(){
        int temp;
        if(lastTypeAccord==1){
            temp=cv[num][0];
            if(keyModeMajeur+deca[num]+temp+12 <= 127){
                ci[num]=(ci[num]+1)%3;
                cv[num][0]=cv[num][1];
                cv[num][1]=cv[num][2];
                cv[num][2]=temp+12;
            }
        }else if(lastTypeAccord==2){
            temp=cv2[num][0];
            if(keyModeMajeur+deca[num]+temp+12 <= 127) {
                ci2[num] = (ci2[num] + 1) % 4;
                cv2[num][0] = cv2[num][1];
                cv2[num][1] = cv2[num][2];
                cv2[num][2] = cv2[num][3];
                cv2[num][3] = temp + 12;
            }
        }
        jouerAccord();
    }


    public void joueGammeMajUp(){
        finirAccord();
        joueGammeUp(keyModeMajeur+deca[1],gammeMaj);
    }

    public void joueGammeMinUp(){
        finirAccord();
        joueGammeUp(keyModeMajeur+deca[4],gammeMin);
    }

    public void joueGammeMinHUp(){
        finirAccord();
        joueGammeUp(keyModeMajeur+deca[4],gammeMinH);
    }

    public void joueGammeMinDUp(){
        finirAccord();
        joueGammeUp(keyModeMajeur+deca[4],gammeMinD);
    }

    public void joueGammePentaMajUp(){
        finirAccord();
        joueGammeUp(keyModeMajeur+deca[1],gammePentaMaj);
    }

    public void joueGammePentaMinUp(){
        finirAccord();
        joueGammeUp(keyModeMajeur+deca[4],gammePentaMin);
    }

    public String getChordName(int j){
        return notes.get((keyModeMajeur+ deca[j] )%12) + typeAccord.get(j);
    }

    public String getChordName2(int j){
        return notes.get((keyModeMajeur+ deca[j] )%12) + typeAccord2.get(j);
    }

    public String getRenversementName(int i){
        //return ""+ (deca[i]+cv[i][0]) +", "+ ci[i];
        return ""+ noteName.get(keyModeMajeur+deca[i]+cv[i][0]) +", "+ ci[i];
    }

    public String getRenversementName2(int i){
        //return ""+ (deca[i]+cv2[i][0]) +", "+ ci2[i];
        return ""+ noteName.get(keyModeMajeur+deca[i]+cv2[i][0]) +", "+ ci2[i];
    }

    public String getInstrumentName(){
        return nomsInstruments.get(instrument);
    }

    public int getNoteDansGamme(String typeGamme, int tag){
        int ligne;
        int colonne;
        //if(typeGamme==0) {
        int n=12;
        List<Integer> l = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        int origine=48+delta;



        switch(typeGamme){
            case "All":
            case "All, colored":
                break;
            case "All, from major":
                origine=keyModeMajeur+deca[1]+delta;
                break;
            case "All, from minor":
                origine=keyModeMajeur+deca[4]-12+delta;
                break;
            case "Major":
                l = gammeMaj;
                n=7;
                origine=keyModeMajeur+deca[1]+delta;
                break;
            case "Minor Natural":
                l = gammeMin;
                n=7;
                origine=keyModeMajeur+deca[4]-12+delta;
                break;
            case "Minor Harmonic":
                l = gammeMinH;
                n=7;
                origine=keyModeMajeur+deca[4]-12+delta;
                break;
            case "Minor Dorian":
                l = gammeMinD;
                n=7;
                origine=keyModeMajeur+deca[3]+delta;
                break;
            case "Minor Phrygian":
                l = gammeMinP;
                n=7;
                origine=keyModeMajeur+deca[5]+delta;
                break;
            case "Minor All":
                l = gammeMin_9;
                n=9;
                origine=keyModeMajeur+deca[4]-12+delta;
                break;
            case "Penta Major":
                l = gammePentaMaj;
                n=5;
                origine=keyModeMajeur+deca[1]+delta;
                break;
            case "Penta Minor":
                l = gammePentaMin;
                n=5;
                origine=keyModeMajeur+deca[4]-12+delta;
                break;
            case "Blues":
                l = gammeBlues;
                n=6;
                origine=keyModeMajeur+deca[4]+delta;
                break;
            case "Whole Tone":
                l = gammeWholeTone;
                n=6;
                origine=keyModeMajeur+deca[1]+delta;
                break;
            case "Bebop Dominant":
                l = gammeBebopDominant;
                n=8;
                origine=keyModeMajeur+deca[2]+delta;
                break;
            case "Altered":
                l = gammeAltered;
                n=7;
                origine=keyModeMajeur+deca[2]+delta;
                break;
            case "Diminished 1":
                l = gammeDiminished_1;
                n=8;
                origine=keyModeMajeur+deca[2]+delta;
                break;
            case "Diminished 2":
                l = gammeDiminished_2;
                n=8;
                origine=keyModeMajeur+deca[2]+delta;
                break;

            default:
                break;
        }
        ligne=tag/n;
        colonne=tag%n;
        return origine + 12*(ligne-1) + l.get(colonne);
    }

    public void jouerNote(int x){
        sendMidi(0x90, x, volume);
    }

    public void stopNote(int x){
        sendMidi(0x80, x, 0);
    }
}
