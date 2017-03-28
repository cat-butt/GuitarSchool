package com.jackson.guitar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jackson on 3/22/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScaleDictionary {

    public static final int NOTE_MAPPING[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    public static final String NOTE_MAPPING_RELATIVE_NAMES[] = {"b9", "9", "m3", "M3", "4", "b5", "5", "#5", "6", "b7", "maj7"};
    public static final String NOTE_NAMES[] = {"C", "C#/Db", "D", "Eb/D#", "E", "F", "F#/Gb", "G", "Ab/G#", "A", "Bb/A#", "B"};
    private static HashMap masterList = new HashMap();
    private int currentRoot = 0;
    private String currentScaleName = "Ionian";
    private int[] currentScale;
    int index = 0;
    private ArrayList<Scale> scales = new ArrayList<>();
    private HashMap<String, Scale> scalesMap = new HashMap<>();

    public void setScales(ArrayList<Scale> scales) {
        this.scales = scales;
        for( Scale scale : scales) {
            this.scalesMap.put(scale.getName(), scale);
        }
    }

    @JsonIgnore
    public ArrayList<String> getScaleListUnderFamily(String familyName) {
        ArrayList<String> scaleList = new ArrayList<>();
        for( Scale s : scales) {
            if(s.getFamilyName().equals(familyName))
                scaleList.add(s.getName());
        }
        return scaleList;
    }

    @JsonIgnore
    public ArrayList<String> getFamilyList() {
        ArrayList<String> familyList = new ArrayList<>();
        for( Scale s : scales ) {
            if( !familyList.contains(s.getFamilyName()))
                familyList.add(s.getFamilyName());
        }
        return familyList;
    }

    @JsonIgnore
    public List<Integer> getScale(String name) {
        if( !scalesMap.containsKey(name) ) {
            return new ArrayList<>();
        }
        return scalesMap.get(name).getStepList();
    }

    @JsonIgnore
    public List<Note> getScaleNotesFromNameAndRoot(String scaleName, int root) {
        List<Integer> stepArray = getScale(scaleName);
        int current = root;
        ArrayList<Note> list = new ArrayList<>();
        for( int i = 0; i < stepArray.size(); i++) {
            list.add(new Note(current % 12, i + 1));
            current += stepArray.get(i);
        }
        return list;
    }

    public static ScaleDictionary createDict() {
        ScaleDictionary scaleDictionary;
        InputStream in = ScaleDictionary.class.getResourceAsStream("/scalesB.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            scaleDictionary = mapper.readValue(in, ScaleDictionary.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return scaleDictionary;
    }

    @JsonCreator
    public static ScaleDictionary Create(String json ) {
        ScaleDictionary scaleDictionary = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            scaleDictionary = mapper.readValue(json, ScaleDictionary.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaleDictionary;
    }
//    @JsonIgnore
//    public HashMap<String, Scale> getScalesMap() {
//        return scalesMap;
//    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ScaleDictionary{");
        sb.append("scales=").append(scales);
        sb.append('}');
        return sb.toString();
    }

    public static class Scale {
        private String name;
        private String familyName;
        private String steps;
        private ArrayList<Integer> stepList;

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty("familyName")
        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        @JsonProperty("steps")
        public String getSteps() {
            return steps;
        }

        public void setSteps(String steps) {
            ArrayList<Integer> temp = new ArrayList<>();
            int sum = 0;
            if (steps.matches("[0-9]+")) {
                for (char ch : steps.toCharArray()) {
                    int n = Character.getNumericValue(ch);
                    temp.add(n);
                    sum += n;
                }
                if (sum == 12) {
                    this.steps = steps;
                    this.stepList = temp;
                    return;
                }
            }
            this.steps = "Invalid Steps";
        }

        @JsonIgnore
        public ArrayList<Integer> getStepList() {
            return stepList;
        }



        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Scale{");
            sb.append("name='").append(name).append('\'');
            sb.append(", familyName='").append(familyName).append('\'');
            sb.append(", steps='").append(steps).append('\'');
            sb.append(", stepList=").append(stepList);
            sb.append('}');
            return sb.toString();
        }
    }
}
