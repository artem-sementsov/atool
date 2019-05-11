package ru.croc.ecosmartdata.design;

import org.junit.Test;

import java.io.IOException;

import static ru.croc.ecosmartdata.design.ATool.*;

public class AToolPerformanceTest {
    @Test
    public void testPerformance() throws IOException {

        {
            long ts = System.currentTimeMillis();
            buildingWithoutManager(getDataProvider());
            long duration1 = System.currentTimeMillis() - ts;

            ts = System.currentTimeMillis();
            buildingWithManager(getDataProvider());
            long duration2 = System.currentTimeMillis() - ts;
            System.out.println("Single thread : " + duration1 + " milliseconds vs Parallel execution : " + duration2 + " milliseconds. Single before parallel");
        }

        {
            long ts = System.currentTimeMillis();
            buildingWithManager(getDataProvider());
            long duration1 = System.currentTimeMillis() - ts;

            ts = System.currentTimeMillis();
            buildingWithoutManager(getDataProvider());
            long duration2 = System.currentTimeMillis() - ts;
            System.out.println("Single thread : " + duration2 + " milliseconds vs Parallel execution : " + duration1 + " milliseconds. Parallel before Single");
        }
    }

}
