package com.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.NashornMed;

public class NashornMedTest {

	private static final Logger logger =
			LoggerFactory.getLogger(TemplateParserTest.class);
	
	@Test
	public void testExecute() {
		
		NashornMed med = new NashornMed();
		med.execute("function sum(a, b) { return a + b; };var aa = sum(9, 2);print('test' + aa);");
		String zz = med.getValue("aa");
		Assert.assertNotNull(zz,"Passed");
		
	}
}
