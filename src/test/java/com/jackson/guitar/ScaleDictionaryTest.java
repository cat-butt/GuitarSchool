package com.jackson.guitar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.transform.Scale;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jackson on 3/22/2017.
 */
public class ScaleDictionaryTest {

    private static ScaleDictionary scaleDictionary = null;

    private static String jsonString = "{\\r\\n  \\\"scales\\\": [\\r\\n    {\\r\\n      \\\"name\\\": \\\"Lydian\\\",\\r\\n      \\\"steps\\\": \\\"2221221\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Ionian\\\",\\r\\n      \\\"steps\\\": \\\"2212221\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Mixolydian\\\",\\r\\n      \\\"steps\\\": \\\"2212212\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Dorian\\\",\\r\\n      \\\"steps\\\": \\\"2122212\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Aeolian\\\",\\r\\n      \\\"steps\\\": \\\"2122122\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Pythagorean\\\",\\r\\n      \\\"steps\\\": \\\"1222122\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Locrian\\\",\\r\\n      \\\"steps\\\": \\\"1221222\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Melodic Minor\\\",\\r\\n      \\\"steps\\\": \\\"2122221\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"MM 2nd Mode\\\",\\r\\n      \\\"steps\\\": \\\"1222212\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Lydian Augmented\\\",\\r\\n      \\\"steps\\\": \\\"2222121\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Lydian Dominant\\\",\\r\\n      \\\"steps\\\": \\\"2221212\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Hindu\\\",\\r\\n      \\\"steps\\\": \\\"2212122\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Locrian\\\",\\r\\n      \\\"steps\\\": \\\"2121222\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Altered\\\",\\r\\n      \\\"steps\\\": \\\"1212222\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Half-Whole\\\",\\r\\n      \\\"steps\\\": \\\"12121212\\\",\\r\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Whole-Half\\\",\\r\\n      \\\"steps\\\": \\\"12121212\\\",\\r\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\r\\n    },\\r\\n\\r\\n    {\\r\\n      \\\"name\\\": \\\"Whole\\\",\\r\\n      \\\"steps\\\": \\\"222222\\\",\\r\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Augmented\\\",\\r\\n      \\\"steps\\\": \\\"313131\\\",\\r\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Half-Whole\\\",\\r\\n      \\\"steps\\\": \\\"12121212\\\",\\r\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Major Pentatonic\\\",\\r\\n      \\\"steps\\\": \\\"22323\\\",\\r\\n      \\\"familyName\\\": \\\"Pentatonic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Minor Pentatonic\\\",\\r\\n      \\\"steps\\\": \\\"32232\\\",\\r\\n      \\\"familyName\\\": \\\"Pentatonic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Dom Pentatonic\\\",\\r\\n      \\\"steps\\\": \\\"23232\\\",\\r\\n      \\\"familyName\\\": \\\"Pentatonic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Chromatic\\\",\\r\\n      \\\"steps\\\": \\\"111111111111\\\",\\r\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Major Triad\\\",\\r\\n      \\\"steps\\\": \\\"435\\\",\\r\\n      \\\"familyName\\\": \\\"Triads\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Minor Triad\\\",\\r\\n      \\\"steps\\\": \\\"345\\\",\\r\\n      \\\"familyName\\\": \\\"Triads\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Diminished Triad\\\",\\r\\n      \\\"steps\\\": \\\"336\\\",\\r\\n      \\\"familyName\\\": \\\"Triads\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Augmented Triad\\\",\\r\\n      \\\"steps\\\": \\\"444\\\",\\r\\n      \\\"familyName\\\": \\\"Triads\\\"\\r\\n    }\\r\\n  ]\\r\\n}";
    private static String jsonStringB = "{\\r\\n  \\\"scales\\\": [\\r\\n    {\\r\\n      \\\"name\\\": \\\"Lydian\\\",\\r\\n      \\\"steps\\\": \\\"2221221\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Ionian\\\",\\r\\n      \\\"steps\\\": \\\"2212221\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Mixolydian\\\",\\r\\n      \\\"steps\\\": \\\"2212212\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Dorian\\\",\\r\\n      \\\"steps\\\": \\\"2122212\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Aeolian\\\",\\r\\n      \\\"steps\\\": \\\"2122122\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Pythagorean\\\",\\r\\n      \\\"steps\\\": \\\"1222122\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Locrian\\\",\\r\\n      \\\"steps\\\": \\\"1221222\\\",\\r\\n      \\\"familyName\\\": \\\"Major\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Melodic Minor\\\",\\r\\n      \\\"steps\\\": \\\"2122221\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"MM 2nd Mode\\\",\\r\\n      \\\"steps\\\": \\\"1222212\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Lydian Augmented\\\",\\r\\n      \\\"steps\\\": \\\"2222121\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Lydian Dominant\\\",\\r\\n      \\\"steps\\\": \\\"2221212\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Hindu\\\",\\r\\n      \\\"steps\\\": \\\"2212122\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Locrian\\\",\\r\\n      \\\"steps\\\": \\\"2121222\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Altered\\\",\\r\\n      \\\"steps\\\": \\\"1212222\\\",\\r\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Half-Whole\\\",\\r\\n      \\\"steps\\\": \\\"12121212\\\",\\r\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Whole-Half\\\",\\r\\n      \\\"steps\\\": \\\"12121212\\\",\\r\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\r\\n    },\\r\\n\\r\\n    {\\r\\n      \\\"name\\\": \\\"Whole\\\",\\r\\n      \\\"steps\\\": \\\"222222\\\",\\r\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Augmented\\\",\\r\\n      \\\"steps\\\": \\\"313131\\\",\\r\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Half-Whole\\\",\\r\\n      \\\"steps\\\": \\\"12121212\\\",\\r\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Major Pentatonic\\\",\\r\\n      \\\"steps\\\": \\\"22323\\\",\\r\\n      \\\"familyName\\\": \\\"Pentatonic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Minor Pentatonic\\\",\\r\\n      \\\"steps\\\": \\\"32232\\\",\\r\\n      \\\"familyName\\\": \\\"Pentatonic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Dom Pentatonic\\\",\\r\\n      \\\"steps\\\": \\\"23232\\\",\\r\\n      \\\"familyName\\\": \\\"Pentatonic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Chromatic\\\",\\r\\n      \\\"steps\\\": \\\"111111111111\\\",\\r\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Major Triad\\\",\\r\\n      \\\"steps\\\": \\\"435\\\",\\r\\n      \\\"familyName\\\": \\\"Triads\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Minor Triad\\\",\\r\\n      \\\"steps\\\": \\\"345\\\",\\r\\n      \\\"familyName\\\": \\\"Triads\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Diminished Triad\\\",\\r\\n      \\\"steps\\\": \\\"336\\\",\\r\\n      \\\"familyName\\\": \\\"Triads\\\"\\r\\n    },\\r\\n    {\\r\\n      \\\"name\\\": \\\"Augmented Triad\\\",\\r\\n      \\\"steps\\\": \\\"444\\\",\\r\\n      \\\"familyName\\\": \\\"Triads\\\"\\r\\n    }\\r\\n  ]\\r\\n}";
    private static String jsonStringC = "\"{\\n  \\\"scales\\\": [\\n    {\\n      \\\"name\\\": \\\"Lydian\\\",\\n      \\\"steps\\\": \\\"2221221\\\",\\n      \\\"familyName\\\": \\\"Major\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Ionian\\\",\\n      \\\"steps\\\": \\\"2212221\\\",\\n      \\\"familyName\\\": \\\"Major\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Mixolydian\\\",\\n      \\\"steps\\\": \\\"2212212\\\",\\n      \\\"familyName\\\": \\\"Major\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Dorian\\\",\\n      \\\"steps\\\": \\\"2122212\\\",\\n      \\\"familyName\\\": \\\"Major\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Aeolian\\\",\\n      \\\"steps\\\": \\\"2122122\\\",\\n      \\\"familyName\\\": \\\"Major\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Pythagorean\\\",\\n      \\\"steps\\\": \\\"1222122\\\",\\n      \\\"familyName\\\": \\\"Major\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Locrian\\\",\\n      \\\"steps\\\": \\\"1221222\\\",\\n      \\\"familyName\\\": \\\"Major\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Melodic Minor\\\",\\n      \\\"steps\\\": \\\"2122221\\\",\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"MM 2nd Mode\\\",\\n      \\\"steps\\\": \\\"1222212\\\",\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Lydian Augmented\\\",\\n      \\\"steps\\\": \\\"2222121\\\",\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Lydian Dominant\\\",\\n      \\\"steps\\\": \\\"2221212\\\",\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Hindu\\\",\\n      \\\"steps\\\": \\\"2212122\\\",\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Locrian\\\",\\n      \\\"steps\\\": \\\"2121222\\\",\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Altered\\\",\\n      \\\"steps\\\": \\\"1212222\\\",\\n      \\\"familyName\\\": \\\"Melodic Minor\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Half-Whole\\\",\\n      \\\"steps\\\": \\\"12121212\\\",\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Whole-Half\\\",\\n      \\\"steps\\\": \\\"12121212\\\",\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\n    },\\n\\n    {\\n      \\\"name\\\": \\\"Whole\\\",\\n      \\\"steps\\\": \\\"222222\\\",\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Augmented\\\",\\n      \\\"steps\\\": \\\"313131\\\",\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Half-Whole\\\",\\n      \\\"steps\\\": \\\"12121212\\\",\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Major Pentatonic\\\",\\n      \\\"steps\\\": \\\"22323\\\",\\n      \\\"familyName\\\": \\\"Pentatonic\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Minor Pentatonic\\\",\\n      \\\"steps\\\": \\\"32232\\\",\\n      \\\"familyName\\\": \\\"Pentatonic\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Dom Pentatonic\\\",\\n      \\\"steps\\\": \\\"23232\\\",\\n      \\\"familyName\\\": \\\"Pentatonic\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Chromatic\\\",\\n      \\\"steps\\\": \\\"111111111111\\\",\\n      \\\"familyName\\\": \\\"Synthetic\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Major Triad\\\",\\n      \\\"steps\\\": \\\"435\\\",\\n      \\\"familyName\\\": \\\"Triads\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Minor Triad\\\",\\n      \\\"steps\\\": \\\"345\\\",\\n      \\\"familyName\\\": \\\"Triads\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Diminished Triad\\\",\\n      \\\"steps\\\": \\\"336\\\",\\n      \\\"familyName\\\": \\\"Triads\\\"\\n    },\\n    {\\n      \\\"name\\\": \\\"Augmented Triad\\\",\\n      \\\"steps\\\": \\\"444\\\",\\n      \\\"familyName\\\": \\\"Triads\\\"\\n    }\\n  ]\\n}\"";
    @BeforeClass
    public static void loadScaleDictionary() throws Exception {
//        InputStream in = ScaleFinder.class.getResourceAsStream("/scalesB.json");
//        InputStream in = ScaleFinder.class.getResourceAsStream("/scalesB.json");
        ObjectMapper mapper = new ObjectMapper();
//        mapper.readValue(jsonStringC, new TypeReference<List<ScaleDictionary.Scale>>() {});
//        scaleDictionary = mapper.readValue(jsonStringC, ScaleDictionary.class);
        scaleDictionary = ScaleDictionary.Create(jsonStringC);
        Assert.assertNotNull("scaleDictionary not null", scaleDictionary);
    }

    @Test
    public void testA() throws Exception {

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
       Assert.assertEquals(5, familyList.size());
    }

    @Test
    public void getScaleListUnderFamily() throws Exception {
        ArrayList<String> familyList = scaleDictionary.getFamilyList();
        Assert.assertNotNull(familyList);
        Assert.assertEquals(5, familyList.size());
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