package com.jackson.guitar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by jackson on 3/23/2017.
 */
public class ScaleFinderTest {

    private ScaleFinder scaleFinder = null;
    @Before
    public  void initializeScaleFinder() throws Exception {
        scaleFinder = new ScaleFinder();
        Assert.assertNotNull(scaleFinder);
    }

    @Test
    public void getGroupNameList() throws Exception {
        ArrayList<String> groupNameList = scaleFinder.getGroupNameList();
        Assert.assertNotNull(groupNameList);
        Assert.assertEquals(4, groupNameList.size());
    }

    @Test
    public void getGroup() throws Exception {

    }

    @Test
    public void setRoot() throws Exception {

    }

    @Test
    public void setRoot1() throws Exception {

    }

    @Test
    public void setScaleName() throws Exception {

    }

    @Test
    public void getScale() throws Exception {

    }

    @Test
    public void getRoot() throws Exception {

    }

}