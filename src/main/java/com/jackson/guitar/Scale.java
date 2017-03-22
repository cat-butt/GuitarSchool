package com.jackson.guitar;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by jackson on 3/22/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Scale {
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
}
