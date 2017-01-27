package de.kuriositaet.pomerator;

import org.testng.annotations.Test;
import util.json.JSON;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * Created by a2800276 on 2017-01-27.
 */
public class StuffTest {

	@Test
	public void testMap2xml0 () {
		Map<String, Object> map = new HashMap<>();
		map.put( "one", "1" );
		map.put( "two", "2" );
		String xml = Stuff.map2xml( map );
		String expected = "<one>\n" +
				"  1\n" +
				"</one>\n" +
				"<two>\n" +
				"  2\n" +
				"</two>";
		assertEquals( xml, expected );
	}
	@Test
	public void testMap2xml1() {
		String json = "{\n" +
				"  \"dependency\" : [\n" +
				"    {\n" +
				"     \"groupId\"    : \"groupId0\"\n" +
				"    ,\"artifactId\" : \"artifactId0\"\n" +
				"    ,\"version\"    : \"version0\"\n" +
				"    }\n" +
				"    ,{\n" +
				"     \"groupId\"    : \"groupId1\"\n" +
				"    ,\"artifactId\" : \"artifactId1\"\n" +
				"    ,\"version\"    : \"version1\"\n" +
				"    }\n" +
				"  ]\n" +
				"}";

		String xml = Stuff.map2xml( (Map<String, ?>) JSON.parse(json) );

		String expected = "<dependency>\n" +
				"  <artifactId>\n" +
				"    artifactId0\n" +
				"  </artifactId>\n" +
				"  <groupId>\n" +
				"    groupId0\n" +
				"  </groupId>\n" +
				"  <version>\n" +
				"    version0\n" +
				"  </version>\n" +
				"</dependency>\n" +
				"<dependency>\n" +
				"  <artifactId>\n" +
				"    artifactId1\n" +
				"  </artifactId>\n" +
				"  <groupId>\n" +
				"    groupId1\n" +
				"  </groupId>\n" +
				"  <version>\n" +
				"    version1\n" +
				"  </version>\n" +
				"</dependency>";

		assertEquals( xml, expected );
	}
}