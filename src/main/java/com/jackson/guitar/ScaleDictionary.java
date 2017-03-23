package com.jackson.guitar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jackson on 3/22/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScaleDictionary {

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
    public ArrayList<Integer> getScale(String name) {
        if( !scalesMap.containsKey(name) ) {
            return new ArrayList<>();
        }
        return scalesMap.get(name).getStepList();
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

    private static class Scale {
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
