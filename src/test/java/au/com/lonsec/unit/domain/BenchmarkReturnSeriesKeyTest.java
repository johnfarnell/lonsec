package au.com.lonsec.unit.domain;

import au.com.lonsec.domain.Benchmark;
import au.com.lonsec.domain.BenchmarkReturnSeries;
import au.com.lonsec.domain.BenchmarkReturnSeriesKey;
import au.com.lonsec.domain.FundReturnSeries;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
public class BenchmarkReturnSeriesKeyTest {

    @Test
    public void testBenchmarkReturnSeriesKeyWorksAsAMapKeyWhenEqual()
    {
        Benchmark bm1 = new Benchmark("bm1", "bm1 description");
        //Set up date
        Calendar cal1 = Calendar.getInstance();

        BenchmarkReturnSeries bmrs1 = new BenchmarkReturnSeries(bm1, cal1.getTime(), new BigDecimal("4.65") );
        //Set up first key
        BenchmarkReturnSeriesKey benchmarkReturnSeriesKey1 = new BenchmarkReturnSeriesKey(bmrs1);

        //Set second date to be same as the first
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(cal1.getTime());
        //Set second benchmar to be the same as the first
        Benchmark bm2 = new Benchmark("bm1", "bm1 description");
        //Set up second key
        BenchmarkReturnSeriesKey benchmarkReturnSeriesKey2 = new BenchmarkReturnSeriesKey(bm2, cal2.getTime());

        Assert.assertEquals(benchmarkReturnSeriesKey1, benchmarkReturnSeriesKey2);
        Assert.assertEquals(benchmarkReturnSeriesKey1.hashCode(), benchmarkReturnSeriesKey2.hashCode());

        //Now check they are the same key by seeing if one replaces the other in a map

        Map<BenchmarkReturnSeriesKey, String> testMap = new HashMap<>();
        testMap.put(benchmarkReturnSeriesKey1, "key1");
        Assert.assertEquals(1, testMap.size());

        //Now replace it
        testMap.put(benchmarkReturnSeriesKey2, "key2");
        //If it has been replacfed, teh map will still only have 1
        Assert.assertEquals(1, testMap.size());
        Assert.assertEquals("key2", testMap.values().toArray()[0]);

        //Now put it back
        testMap.put(benchmarkReturnSeriesKey1, "key1 again");
        //If it has been replacfed, teh map will still only have 1
        Assert.assertEquals(1, testMap.size());
        Assert.assertEquals("key1 again", testMap.values().toArray()[0]);
    }
    @Test
    public void testBenchmarkReturnSeriesKeyWorksAsAMapKeyWhenBMCodeIsDifferent()
    {
        Benchmark bm1 = new Benchmark("bm1", "bm1 description");
        //Set up date
        Calendar cal1 = Calendar.getInstance();

        BenchmarkReturnSeries bmrs1 = new BenchmarkReturnSeries(bm1, cal1.getTime(), new BigDecimal("4.65") );
        //Set up first key
        BenchmarkReturnSeriesKey benchmarkReturnSeriesKey1 = new BenchmarkReturnSeriesKey(bmrs1);

        //Set second date to be same as the first
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(cal1.getTime());
        //Set second benchmar to be different to the first
        Benchmark bm2 = new Benchmark("bm2", "bm2 description");
        //Set up second key
        BenchmarkReturnSeriesKey benchmarkReturnSeriesKey2 = new BenchmarkReturnSeriesKey(bm2, cal2.getTime());

        Assert.assertNotEquals(benchmarkReturnSeriesKey1, benchmarkReturnSeriesKey2);
        Assert.assertNotEquals(benchmarkReturnSeriesKey1.hashCode(), benchmarkReturnSeriesKey2.hashCode());

        //Now check they are the NOT the same key by  confirming they can both exists in the  map

        Map<BenchmarkReturnSeriesKey, String> testMap = new HashMap<>();
        testMap.put(benchmarkReturnSeriesKey1, "key1");
        Assert.assertEquals(1, testMap.size());

        //Now add the second key
        testMap.put(benchmarkReturnSeriesKey2, "key2");
        //ICheck both keys are now there
        Assert.assertEquals(2, testMap.size());
        Assert.assertEquals("key2", testMap.get(benchmarkReturnSeriesKey2));

    }
    @Test
    public void testBenchmarkReturnSeriesKeyWorksAsAMapKeyWhenDateIsDifferent()
    {
        Benchmark bm1 = new Benchmark("bm1", "bm1 description");
        //Set up date
        Calendar cal1 = Calendar.getInstance();

        BenchmarkReturnSeries bmrs1 = new BenchmarkReturnSeries(bm1, cal1.getTime(), new BigDecimal("4.65") );
        //Set up first key
        BenchmarkReturnSeriesKey benchmarkReturnSeriesKey1 = new BenchmarkReturnSeriesKey(bmrs1);

        //Set second date to be 1 year less than the first
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(cal1.getTime());
        cal2.set(Calendar.YEAR, cal1.get(Calendar.YEAR) -1);
        //Set second benchmar to be the same as the first
        Benchmark bm2 = new Benchmark("bm1", "bm2 description");
        //Set up second key
        BenchmarkReturnSeriesKey benchmarkReturnSeriesKey2 = new BenchmarkReturnSeriesKey(bm2, cal2.getTime());

        Assert.assertNotEquals(benchmarkReturnSeriesKey1, benchmarkReturnSeriesKey2);
        Assert.assertNotEquals(benchmarkReturnSeriesKey1.hashCode(), benchmarkReturnSeriesKey2.hashCode());

        //Now check they are the NOT the same key by  confirming they can both exists in the  map

        Map<BenchmarkReturnSeriesKey, String> testMap = new HashMap<>();
        testMap.put(benchmarkReturnSeriesKey1, "key1");
        Assert.assertEquals(1, testMap.size());

        //Now add the second key
        testMap.put(benchmarkReturnSeriesKey2, "key2");
        //Check both keys are now there
        Assert.assertEquals(2, testMap.size());
        Assert.assertEquals("key2", testMap.get(benchmarkReturnSeriesKey2));

    }
}
