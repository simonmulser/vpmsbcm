package com.vpmsbcm.service;

import static org.junit.Assert.assertNotNull;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vpmsbcm.common.model.Color;
import com.vpmsbcm.common.model.IDFactory;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.State;
import com.vpmsbcm.common.model.Wood;

/**
 * Integration test for the Processor. Uses similar xml definition file
 * (ProcessorIntegrationTest-context.xml) to the actual pu.xml. Writs an
 * unprocessed Data to the Space, and verifies that it has been processed by
 * taking a processed one from the space.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class QualityTest {

	@Autowired
	GigaSpace gigaSpace;

	@After
	public void teardown() {
		gigaSpace.clear(null);
	}

	@Before
	public void setUp() {
		gigaSpace.clear(null);
		IDFactory idFactory = new IDFactory();
		idFactory.init();
		gigaSpace.write(idFactory);
		gigaSpace.write(new Wood("DHL 1"));
	}

	@Test
	public void testWorkingRocketClassA() {
		List<Load> loads = new LinkedList<Load>();
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		gigaSpace.write(new Rocket(null, null, null, 130, loads, 1));

		Rocket template = new Rocket();
		template.setState(State.CLASS_A);
		Rocket rocket = gigaSpace.take(template, 500);
		assertNotNull(rocket);
	}

	@Test
	public void testWorkingRocketClassB() {
		List<Load> loads = new LinkedList<Load>();
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		gigaSpace.write(new Rocket(null, null, null, 129, loads, 1));

		Rocket template = new Rocket();
		template.setState(State.CLASS_B);
		Rocket rocket = gigaSpace.take(template, 500);
		assertNotNull(rocket);
	}

	@Test
	public void testRocketWithNotEnoughCharge() {
		List<Load> loads = new LinkedList<Load>();
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		gigaSpace.write(new Rocket(null, null, null, 119, loads, 1));

		Rocket template = new Rocket();
		template.setState(State.DEFECT);
		Rocket rocket = gigaSpace.take(template, 500);
		assertNotNull(rocket);
	}

	@Test
	public void testRocketWithDefectLoad() {
		List<Load> loads = new LinkedList<Load>();
		loads.add(new Load("DHL5", true, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		gigaSpace.write(new Rocket(null, null, null, 140, loads, 1));

		Rocket template = new Rocket();
		template.setState(State.DEFECT);
		Rocket rocket = gigaSpace.take(template, 500);
		assertNotNull(rocket);
	}
}
