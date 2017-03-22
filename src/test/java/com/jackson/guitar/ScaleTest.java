package com.jackson.guitar;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

/**
 * Created by jackson on 3/22/2017.
 */
public class ScaleTest {

    @Test
    public void testPOJO() throws Exception {
        ObjectMapper mapper  = new ObjectMapper();
        InputStream in = ScaleFinder.class.getResourceAsStream("/scalesB.json");
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        Scale scale = mapper.readValue(in, Scale.class);
        System.out.println(scale);
    }

    @Test
    public void getName() throws Exception {

    }

    @Test
    public void setName() throws Exception {

    }

    @Test
    public void getFamilyName() throws Exception {

    }

    @Test
    public void setFamilyName() throws Exception {

    }

    @Test
    public void getSteps() throws Exception {

    }

    @Test
    public void setSteps() throws Exception {

    }

}