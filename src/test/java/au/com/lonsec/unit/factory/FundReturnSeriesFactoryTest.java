package au.com.lonsec.unit.factory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.lonsec.dao.input.CsvFundReturnSeriesProperties;
import au.com.lonsec.exception.FundReturnException;
import au.com.lonsec.factory.FundReturnSeriesFactory;
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
public class FundReturnSeriesFactoryTest {

    @InjectMocks
    FundReturnSeriesFactory fundReturnSeriesFactory;
    @Mock
    private CSVReader reader;
    @Mock
    private CsvFundReturnSeriesProperties csvFundReturnSeriesInputProperties;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateReturnsNullWhenEOF() throws Exception
    {
        when(reader.readNext()).thenReturn(null);
        ReturnSeriesLine returnSeriesLine = fundReturnSeriesFactory.create("id", reader);
        assertNull(returnSeriesLine);
    }
    @Test
    public void testCreateReturnsValidLine() throws Exception
    {
        //Mock the reading of the next line
        when(reader.readNext()).thenReturn(new String[] {"f1", "22/10/2015", "2.5678"});
        //Mock the date pattern
        when(csvFundReturnSeriesInputProperties.getFrsDatePattern()).thenReturn("dd/MM/yyyy");

        ReturnSeriesLine returnSeriesLine = fundReturnSeriesFactory.create("id", reader);
        assertNotNull(returnSeriesLine);
        assertEquals("f1",returnSeriesLine.getCode());
        assertEquals(new SimpleDateFormat("dd/MM/yyyy").parse("22/10/2015"),returnSeriesLine.getDate());
        assertEquals(new BigDecimal("2.5678"),returnSeriesLine.getReturnPercentage());
    }
    @Test(expected = FundReturnException.class)
    public void testCreateThrowExceptionWhenLT3Columns() throws Exception
    {
        //Mock the reading of the next line - NOTE only 2 elements
        when(reader.readNext()).thenReturn(new String[] {"f1", "22/10/2015"});

        fundReturnSeriesFactory.create("id", reader);
    }
    @Test(expected = FundReturnException.class)
    public void testCreateThrowExceptionWhenGT3Columns() throws Exception
    {
        //Mock the reading of the next line - NOTE the extrat 4th element
        when(reader.readNext()).thenReturn(new String[] {"f1", "22/10/2015", "2.5678", "Wrong"});

        fundReturnSeriesFactory.create("id", reader);
    }
    @Test(expected = FundReturnException.class)
    public void testCreateReturnsThrowExceptionWhenReadNextFails() throws Exception
    {
        //Mock the reading of the next line- throw exception
        when(reader.readNext()).thenThrow(IOException.class);

        fundReturnSeriesFactory.create("id", reader);
    }

}
