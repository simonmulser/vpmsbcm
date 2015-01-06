package com.vpmsbcm.service;

import static org.junit.Assert.assertEquals;
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
import com.vpmsbcm.common.model.NormalRocket;
import com.vpmsbcm.common.model.OrderRocket;
import com.vpmsbcm.common.model.State;
import com.vpmsbcm.common.model.Wood;
import com.vpmsbcm.common.model.order.Order;

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

		gigaSpace.write(new Order("order1", 0, 0, 0, 0, "testAdress"));
		gigaSpace.write(new Wood("DHL 1"));
	}

	@Test
	public void testWorkingRocketClassA() {
		List<Load> loads = new LinkedList<Load>();
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		gigaSpace.write(new NormalRocket(null, null, null, 130, loads, 1));

		NormalRocket template = new NormalRocket();
		template.setState(State.CLASS_A);
		NormalRocket rocket = gigaSpace.take(template, 500);
		assertNotNull(rocket);
	}

	@Test
	public void testWorkingRocketClassB() {
		List<Load> loads = new LinkedList<Load>();
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		gigaSpace.write(new NormalRocket(null, null, null, 129, loads, 1));

		NormalRocket template = new NormalRocket();
		template.setState(State.CLASS_B);
		NormalRocket rocket = gigaSpace.take(template, 500);
		assertNotNull(rocket);
	}

	@Test
	public void testWorkingOrderRocketClassA() {
		List<Load> loads = new LinkedList<Load>();
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		gigaSpace.write(new OrderRocket(null, null, null, 130, loads, 1, "order1"));

		OrderRocket template = new OrderRocket();
		template.setState(State.CLASS_A);
		OrderRocket rocket = gigaSpace.take(template, 500);
		assertNotNull(rocket);
		assertEquals(loads, rocket.getLoades());
	}

	@Test
	public void testWorkingOrderRocketClassB() {
		List<Load> loads = new LinkedList<Load>();
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		gigaSpace.write(new OrderRocket(null, null, null, 129, loads, 1, "order1"));

		NormalRocket template = new NormalRocket();
		template.setState(State.CLASS_B);
		NormalRocket rocket = gigaSpace.take(template, 500);
		assertNotNull(rocket);
		assertEquals(loads, rocket.getLoades());

		Order order = new Order();
		order.setId("order1");
		order = gigaSpace.read(order, 100);
		assertNotNull(order);
		assertEquals(1, (int) order.getMissingRockets());
	}

	@Test
	public void testRocketWithNotEnoughCharge() {
		List<Load> loads = new LinkedList<Load>();
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		gigaSpace.write(new NormalRocket(null, null, null, 119, loads, 1));

		NormalRocket template = new NormalRocket();
		template.setState(State.DEFECT);
		NormalRocket rocket = gigaSpace.take(template, 500);
		assertNotNull(rocket);
	}

	@Test
	public void testRocketWithDefectLoad() {
		List<Load> loads = new LinkedList<Load>();
		loads.add(new Load("DHL5", true, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		loads.add(new Load("DHL5", false, Color.BLUE));
		gigaSpace.write(new NormalRocket(null, null, null, 140, loads, 1));

		NormalRocket template = new NormalRocket();
		template.setState(State.DEFECT);
		NormalRocket rocket = gigaSpace.take(template, 500);
		assertNotNull(rocket);
	}
}
