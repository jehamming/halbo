package com.hamming.test.model;


import com.hamming.halbo.model.Baseplate;
import com.hamming.halbo.model.CityGrid;
import com.hamming.halbo.model.CityGridException;
import com.hamming.halbo.model.HalboID;
import org.junit.Test;

public class TestCityGrid {


    @Test
    public void testBasicGrid() {
        Baseplate startBaseplate = new Baseplate(HalboID.valueOf("BPL0"));
        CityGrid grid = new CityGrid(null, 5, startBaseplate, 2,2);
        // Add one
        Baseplate bp01 = new Baseplate(HalboID.valueOf("BPL01"));
        try {
            grid.addBasePlate(bp01, startBaseplate, CityGrid.Direction.NORTH);
        } catch (CityGridException e) {
            e.printStackTrace();
        }
        System.out.println(grid);
    }

    @Test
    public void testAllDirections() {
        Baseplate startBaseplate = new Baseplate(HalboID.valueOf("BPL0"));
        CityGrid grid = new CityGrid( null, 5, startBaseplate, 2,2);
        // Add one
        Baseplate bp01 = new Baseplate(HalboID.valueOf("BPL01"));
        try {
            grid.addBasePlate(bp01, startBaseplate, CityGrid.Direction.NORTH);
            grid.addBasePlate(bp01, startBaseplate, CityGrid.Direction.SOUTH);
            grid.addBasePlate(bp01, startBaseplate, CityGrid.Direction.EAST);
            grid.addBasePlate(bp01, startBaseplate, CityGrid.Direction.WEST);
        } catch (CityGridException e) {
            e.printStackTrace();
        }
        System.out.println(grid);
    }

    @Test
    public void testOccupied() {
        Baseplate startBaseplate = new Baseplate(HalboID.valueOf("BPL0"));
        CityGrid grid = new CityGrid( null, 5, startBaseplate, 2,2);
        Baseplate bp01 = new Baseplate(HalboID.valueOf("BPL01"));
        try {
            grid.addBasePlate(bp01, startBaseplate, CityGrid.Direction.NORTH);
            grid.addBasePlate(bp01, startBaseplate, CityGrid.Direction.SOUTH);
            grid.addBasePlate(bp01, startBaseplate, CityGrid.Direction.WEST);
            grid.addBasePlate(bp01, startBaseplate, CityGrid.Direction.EAST);
        } catch (CityGridException e) {
            e.printStackTrace();
        }
        boolean exceptionThrown = false;
        try {
            // Expect an occupied exception
            grid.addBasePlate(bp01, startBaseplate, CityGrid.Direction.NORTH);
        } catch (CityGridException e) {
            e.printStackTrace();
            exceptionThrown = true;
        }
        assert exceptionThrown;
        System.out.println(grid);
    }

    @Test
    public void testOutOfBounds() {
        Baseplate startBaseplate = new Baseplate(HalboID.valueOf("BPL0"));
        CityGrid grid = new CityGrid( null, 2, startBaseplate, 1,1);
        // Add one
        Baseplate bp01 = new Baseplate(HalboID.valueOf("BPL01"));
        boolean exceptionThrown = false;
        try {
            // Expect an exception
            grid.addBasePlate(bp01, startBaseplate, CityGrid.Direction.SOUTH);
        } catch (CityGridException e) {
            e.printStackTrace();
            exceptionThrown = true;
        }
        System.out.println(grid);
        assert exceptionThrown;
    }



}
