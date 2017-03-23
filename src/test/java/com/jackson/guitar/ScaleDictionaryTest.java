package com.jackson.guitar;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by jackson on 3/22/2017.
 */
public class ScaleDictionaryTest {

    @Test
    public void testAAA() throws Exception {
        InputStream in = ScaleFinder.class.getResourceAsStream("/scalesA.json");
        ObjectMapper mapper = new ObjectMapper();
        ScaleDictionary scaleDictionary = mapper.readValue(in, ScaleDictionary.class);
        System.out.println(scaleDictionary);

    }

    @Test
    public void testBBB() throws Exception {
        InputStream in = ScaleFinder.class.getResourceAsStream("/scalesB.json");
        Assert.assertNotNull("input stream not null", in);
        ObjectMapper mapper = new ObjectMapper();
        Assert.assertNotNull("mapper not null", mapper);
        ScaleDictionary scaleDictionary = mapper.readValue(in, ScaleDictionary.class);
        Assert.assertNotNull("scaleDictionary not null", scaleDictionary);
        ArrayList<Integer> ionian = scaleDictionary.getScale("Ionian");
        Assert.assertNotNull("ionian scale not null", ionian);
        Assert.assertEquals("ionian scale right size", 7, ionian.size());
        System.out.println(scaleDictionary);

    }

    @Test
    public void getScales() throws Exception {

    }

    @Test
    public void setScales() throws Exception {

    }

}