package com.test;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.NashornMed;
import com.demo.TemplateParser;

public class TemplateParserTest {

	private static final Logger logger =
			LoggerFactory.getLogger(TemplateParserTest.class);

	@Test
	public void testRender() {
		
		TemplateParser med = new TemplateParser();
		HashMap map = new HashMap();
		map.put("id", "2");
		String a = med.renderer("main.tpl",map);
		if (a.indexOf("milanga")!=																																																																																																																																																								-1)
			Assert.assertNotNull(a,"Passed");
		if (a.indexOf("Luis")!=-1)
			Assert.assertNotNull(a,"Passed");
		logger.debug(a);
	}
	
}
