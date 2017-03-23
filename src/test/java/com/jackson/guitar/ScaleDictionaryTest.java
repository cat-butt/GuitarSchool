package com.jackson.guitar;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.transform.Scale;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by jackson on 3/22/2017.
 */
public class ScaleDictionaryTest {

    private static ScaleDictionary scaleDictionary = null;

    @BeforeClass
    public static void loadScaleDictionary() throws Exception {
        InputStream in = ScaleFinder.class.getResourceAsStream("/scalesB.json");
        ObjectMapper mapper = new ObjectMapper();
        scaleDictionary = mapper.readValue(in, ScaleDictionary.class);
        Assert.assertNotNull("scaleDictionary not null", scaleDictionary);
    }

    @Test
    public void testScaleSize() throws Exception {
        ArrayList<Integer> ionian = scaleDictionary.getScale("Ionian");
        Assert.assertNotNull("ionian scale not null", ionian);
        Assert.assertEquals("ionian scale right size", 7, ionian.size());
        System.out.println(scaleDictionary);

    }

    @Test
    public void getFamilyList() throws Exception {
        ArrayList<String> familyList = scaleDictionary.getFamilyList();
       Assert.assertNotNull(familyList);
       Assert.assertEquals(4, familyList.size());
    }

    @Test
    public void getScaleListUnderFamily() throws Exception {
        ArrayList<String> familyList = scaleDictionary.getFamilyList();
        Assert.assertNotNull(familyList);
        Assert.assertEquals(4, familyList.size());
        for( String familyName : familyList) {
            ArrayList<String> scaleList = scaleDictionary.getScaleListUnderFamily(familyName);
            Assert.assertNotNull(scaleList);
            System.out.println(scaleList);
        }
    }

    @Test
    public void getScales() throws Exception {

    }

    @Test
    public void setScales() throws Exception {

    }

}