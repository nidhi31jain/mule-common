package org.mule.common.metadata.datatype;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mule.common.query.expression.*;

import java.util.Arrays;
import java.util.List;

/**
 */
public class SupportedOperatorsFactoryTest {

    SupportedOperatorsFactory sof;
    @Before
    public void setUp(){
        sof = SupportedOperatorsFactory.getInstance();
    }

    @Test
    public void testBooleanOrEnumSymbolsDataType(){
        testCollectionsOfSymbolsWithOperations(Arrays.asList(" = ", " <> "),SupportedOperatorsFactory.getInstance().getSupportedOperationsFor(DataType.BOOLEAN));
        testCollectionsOfSymbolsWithOperations(Arrays.asList(" = ", " <> "),SupportedOperatorsFactory.getInstance().getSupportedOperationsFor(DataType.ENUM)) ;
    }

    @Test
    public void testStringSymbolsDataType(){
        testCollectionsOfSymbolsWithOperations(
                Arrays.asList(" < "," <= "," = "," > "," >= "," <> "," like "),
                SupportedOperatorsFactory.getInstance().getSupportedOperationsFor(DataType.STRING));
    }

    @Test
    public void testNumberDateTimeByteSymbolsDataType(){
        List<String> symbols = Arrays.asList(" < "," <= "," = "," > "," >= "," <> ");
        testCollectionsOfSymbolsWithOperations(symbols, sof.getSupportedOperationsFor(DataType.NUMBER));
        testCollectionsOfSymbolsWithOperations(symbols, sof.getSupportedOperationsFor(DataType.INTEGER));
        testCollectionsOfSymbolsWithOperations(symbols, sof.getSupportedOperationsFor(DataType.DOUBLE));
        testCollectionsOfSymbolsWithOperations(symbols, sof.getSupportedOperationsFor(DataType.DECIMAL));
        testCollectionsOfSymbolsWithOperations(symbols, sof.getSupportedOperationsFor(DataType.DATE_TIME));
        testCollectionsOfSymbolsWithOperations(symbols, sof.getSupportedOperationsFor(DataType.BYTE));
    }

    @Test
    public void testEmptySymbolsDataType(){
        assertTrue(sof.getSupportedOperationsFor(DataType.VOID).isEmpty());
        assertTrue(sof.getSupportedOperationsFor(DataType.STREAM).isEmpty());
        assertTrue(sof.getSupportedOperationsFor(DataType.POJO).isEmpty());
        assertTrue(sof.getSupportedOperationsFor(DataType.LIST).isEmpty());
        assertTrue(sof.getSupportedOperationsFor(DataType.MAP).isEmpty());
        assertTrue(sof.getSupportedOperationsFor(DataType.XML).isEmpty());
        assertTrue(sof.getSupportedOperationsFor(DataType.CSV).isEmpty());
        assertTrue(sof.getSupportedOperationsFor(DataType.JSON).isEmpty());
    }

    public void testCollectionsOfSymbolsWithOperations(List<String> symbols, List<Operator> supported ){
        assertEquals(symbols.size(), supported.size());
        for (int i=0; i < symbols.size() ; i++){
            assertEquals(symbols.get(i), supported.get(i).toString());
        }
    }

}
