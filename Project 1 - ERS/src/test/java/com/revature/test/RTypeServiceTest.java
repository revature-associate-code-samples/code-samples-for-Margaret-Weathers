package com.revature.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.re.proj1.service.RTypeService;


public class RTypeServiceTest {
	
	private static Logger log = Logger.getLogger(RTypeServiceTest.class);
	RTypeService ts;
	
	@Before
	public void setUp() throws Exception{
		ts = new RTypeService();
	}
	
	@After
	public void tearDown() throws Exception{
		ts = null;
	}

	@Test
	public void test() {
		log.debug(ts.getRTypeById(2));
		assertEquals("Travel", ts.getRTypeById(2));
	}

}
