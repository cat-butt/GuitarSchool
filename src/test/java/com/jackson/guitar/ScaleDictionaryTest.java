package com.jackson.guitar;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

/**
 * Created by jackson on 3/22/2017.
 */
public class ScaleDictionaryTest {

    @Test
    public void testAAA() throws Exception {
        InputStream in = ScaleFinder.class.getResourceAsStream("/scalesB.json");
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        ObjectMapper mapper = new ObjectMapper();
        ScaleDictionary scaleDictionary = mapper.readValue(in, ScaleDictionary.class);
        System.out.println(scaleDictionary);

    }
    @Test
    public void getScales() throws Exception {

    }

    @Test
    public void setScales() throws Exception {

    }

}