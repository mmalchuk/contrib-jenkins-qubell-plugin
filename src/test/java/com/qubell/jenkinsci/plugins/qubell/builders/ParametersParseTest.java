package com.qubell.jenkinsci.plugins.qubell.builders;

import com.qubell.jenkinsci.plugins.qubell.JsonParser;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Alex Krupnov
 * @created 17.07.13 16:36
 */
public class ParametersParseTest {

    @Test
    public void testParsingEmptymap() throws Exception {
        Map<String, Object> resultMap = JsonParser.parseMap("{}");

        assertNotNull(resultMap);
        assertEquals(0, resultMap.size());
    }

    @Test
    public void testParsingJsonParametersString() throws Exception {
        Map<String, Object> resultMap = JsonParser.parseMap("{\n" +
                "  \"version\": \"v1\",\n" +
                "  \"intValue\" : 42,\n" +
                "  \"extraParams\" :\n" +
                "\t{\n" +
                "\t   \"param1\": \"1\",\n" +
                "\t   \"param2\": 2\n" +
                "        }\n" +
                "}");

        assertNotNull(resultMap);
        assertEquals(3, resultMap.size());
        assertEquals(42, resultMap.get("intValue"));
        Map<String, Object> nestedMap = (Map<String, Object>) resultMap.get("extraParams");
        assertEquals(2, nestedMap.size());


        //assertTrue(resultMap.get("extraParams") instanceof Map<String, Object>);
    }

    @Test
    public void testSerializeMap() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> firstNesting = new HashMap<String, Object>();
        Map<String, Object> secondNesting = new HashMap<String, Object>();

        secondNesting.put("second", "3");
        firstNesting.put("nested", secondNesting);
        firstNesting.put("fist", 2);

        map.put("rootValue", "root");
        map.put("nested", firstNesting);

        String jsonString = JsonParser.serialize(map);
        assertNotNull(jsonString);
    }
}
