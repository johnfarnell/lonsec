package au.com.lonsec.unit.factory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.lonsec.dao.input.CSVFundReturnSeriesProperties;
import au.com.lonsec.exception.FundReturnException;
import au.com.lonsec.factory.BenchmarkReturnSeriesFactory;
import au.com.lonsec.factory.ReturnSeriesLine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Countrywide Austral on 17-Jan-17.
 */
public class BenchmarkReturnSeriesFactoryTest {

    @InjectMocks
    BenchmarkReturnSeriesFactory benchmarkReturnSeriesFactory;
    @Mock
    private CSVReader reader;
    @Mock
    private CSVFundReturnSeriesProperties csvFundReturnSeriesInputProperties;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateReturnsNullWhenEOF() throws Exception
    {
        when(reader.readNext()).thenReturn(null);
        ReturnSeriesLine returnSeriesLine = benchmarkReturnSeriesFactory.create("id", reader);
        assertNull(returnSeriesLine);
    }
    @Test
    public void testCreateReturnsValidLine() throws Exception
    {
        //Mock the reading of the next line
        when(reader.readNext()).thenReturn(new String[] {"bm1", "22/10/2015", "2.5678"});
        //Mock the date pattern
        when(csvFundReturnSeriesInputProperties.getBrsDatePattern()).thenReturn("dd/MM/yyyy");

        ReturnSeriesLine returnSeriesLine = benchmarkReturnSeriesFactory.create("id", reader);
        assertNotNull(returnSeriesLine);
        assertEquals("bm1",returnSeriesLine.getCode());
        assertEquals(new SimpleDateFormat("dd/MM/yyyy").parse("22/10/2015"),returnSeriesLine.getDate());
        assertEquals(new BigDecimal("2.5678"),returnSeriesLine.getReturnPercentage());
    }
    @Test(expected = FundReturnException.class)
    public void testCreateThrowExceptionWhenLT3Columns() throws Exception
    {
        //Mock the reading of the next line - NOTE only 2 elements
        when(reader.readNext()).thenReturn(new String[] {"bm1", "22/10/2015"});

        benchmarkReturnSeriesFactory.create("id", reader);
    }
    @Test(expected = FundReturnException.class)
    public void testCreateThrowExceptionWhenGT3Columns() throws Exception
    {
        //Mock the reading of the next line - NOTE the extrat 4th element
        when(reader.readNext()).thenReturn(new String[] {"bm1", "22/10/2015", "2.5678", "Wrong"});

        benchmarkReturnSeriesFactory.create("id", reader);
    }

    @Test(expected = FundReturnException.class)
    public void testCreateReturnsThrowExceptionWhenGT3Columns() throws Exception
    {
        //Mock the reading of the next line - NOTE the extrat 4th element
        when(reader.readNext()).thenReturn(new String[] {"bm1", "22/10/2015", "2.5678", "Wrong"});

        benchmarkReturnSeriesFactory.create("id", reader);
    }

    @Test(expected = FundReturnException.class)
    public void testCreateReturnsThrowExceptionWhenReadNextFails() throws Exception
    {
        //Mock the reading of the next line- throw exception
        when(reader.readNext()).thenThrow(IOException.class);

        benchmarkReturnSeriesFactory.create("id", reader);
    }

}
