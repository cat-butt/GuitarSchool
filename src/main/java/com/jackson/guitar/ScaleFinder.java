package com.jackson.guitar;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by jackson on 3/16/2017.
 */
public class ScaleFinder {
    public static final int NOTE_MAPPING[] = { 0,1,2,3,4,5,6,7,8,9,10,11 };
    public static final String NOTE_MAPPING_RELATIVE_NAMES[] = { "b9", "9", "m3", "M3", "4", "b5", "5", "#5", "6", "b7", "maj7" };
    public static final String NOTE_NAMES[] = { "C","Db/C#","D","Eb/D#","E","F","F#/Gb","G","Ab/G#","A","Bb/A#","B" };
    private static HashMap masterGroupList = new HashMap();
    private static HashMap masterList = new HashMap();
    private int currentRoot = 0;
    private String currentScaleName = "Ionian";
    private int[] currentScale;
    int index = 0;
    ScaleFinder() throws FileNotFoundException {
        this("scales.dat");
    }
    ScaleFinder(File file) throws FileNotFoundException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        try {
            init(bf);
            bf.close();
        } catch (IOException e) { }
        currentScale = (int[])(masterList.get("Ionian"));
    }
    ScaleFinder(String file) throws FileNotFoundException {
        this(new File(file));
    }
    /**
     * For every scale group, store the name, build
     * an ArrayList to hold the names in the group,
     * and put the ArrayList in a HashMap
     * with the name as its key.
     **/
    private void init(BufferedReader bf) throws IOException {
        int c;
        while((c=bf.read())!=-1) {
            if((char)c=='<') {
                ArrayList list = new ArrayList();
                String scaleGroup = bf.readLine().trim();
                if(scaleGroup.length()>1)
                    scaleGroup = scaleGroup.substring(0,scaleGroup.length()-1);
                String s = null;
                do {
                    s = bf.readLine().trim();
                    //System.out.println("s: " + s);
                    if(s.charAt(0) != '<') {
                        String name = s.substring(0,s.indexOf(':'));
                        masterList.put(name,decodeValue(s.substring(s.indexOf(':')+1).trim()));
                        list.add(name);
                    }
                } while(s.charAt(0) != '<');
                masterGroupList.put(scaleGroup,list);
                index++;
            }
        }
    }
    private int[] decodeValue(String s) {
        int[] array = new int[s.length()];
        int sum=0;
        for(int i=0; i<array.length; i++) {
            array[i] = Character.digit(s.charAt(i),10);
            sum += array[i];
        }
        System.out.println(s + ": " + sum);
        return array;
    }
    /*
       * Returns an array of Strings containing
       * the group names
       */
    public String[] getGroupNameList() {
        Set s = masterGroupList.keySet();
        Object o[] = s.toArray();
        String names[] = new String[o.length];
        for(int i=0; i<o.length; i++) {
            names[i] = (String)o[i];
        }
        return names;
        //return masterGroupNameList;
    }

    /*
     * Returns a String[] containing a group of scale names.
     */
    public String[] getGroup(String group) {
        ArrayList list = (ArrayList)masterGroupList.get(group);
        String[] names = new String[list.size()];
        Iterator it = list.iterator();
        int i=0;
        while(it.hasNext())
            names[i++] = (String)it.next();

        return names;
    }

    public void setRoot(int root) {
        this.currentRoot = root;
    }

    public void setRoot(String root) {
        for(int i=0; i<NOTE_NAMES.length; i++) {
            if(root.equals(NOTE_NAMES[i]))
                currentRoot = i;
        }
    }

    public void setScaleName(String type) {
        this.currentScaleName = type;
    }

    public ArrayList getScale() {
        int[] array = (int[])masterList.get(currentScaleName);
        int current = currentRoot;
        ArrayList list = new ArrayList();
        //list.add(new Integer(current));
        //list.add(new Note(
        for(int i=0; i<array.length; i++) {
            //list.add(new Integer((current += array[i])%12));
            list.add(new Note(current%12,i+1));
            current += array[i];
        }
        return list;
    }

    public int getRoot() {
        return currentRoot;
    }


}
