package com.vpmsbcm.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gigaspaces.client.ChangeSet;
import com.j_spaces.core.client.SQLQuery;
import com.vpmsbcm.common.model.Charge;
import com.vpmsbcm.common.model.Color;
import com.vpmsbcm.common.model.Detonator;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.NormalRocket;
import com.vpmsbcm.common.model.OrderRocket;
import com.vpmsbcm.common.model.Wood;
import com.vpmsbcm.common.model.Work;
import com.vpmsbcm.common.model.order.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ProducerTest {

	@Autowired
	GigaSpace gigaSpace;

	@After
	public void teardown() {
		gigaSpace.clear(null);
	}

	@Before
	public void setUp() {
		gigaSpace.clear(null);

		gigaSpace.write(new Wood("DHL 1"));
		gigaSpace.write(new Wood("DHL 1"));
		gigaSpace.write(new Detonator("DHL 1"));
		gigaSpace.write(new Detonator("DHL 2"));
		gigaSpace.write(new Detonator("DHL 3"));
		gigaSpace.write(new Charge("DHL4"));
		gigaSpace.write(new Load("DHL5", true, Color.BLUE));
		gigaSpace.write(new Load("DHL6", false, Color.BLUE));

		gigaSpace.write(new Order("1", 4, 0, 0, 3, "adress"));
		gigaSpace.write(new Order("2", 4, 1, 0, 2, "adress"));
	}

	@Test
	public void testWithNotEnoughLoad() {
		gigaSpace.write(new Work());

		assertNull(gigaSpace.take(new NormalRocket(), 500));
		assertEquals(2, gigaSpace.count(new Wood()));
		assertEquals(3, gigaSpace.count(new Detonator()));
	}

	@Test
	public void testCreateRocket() {
		gigaSpace.write(new Load("DHL6", false, Color.GREEN));

		gigaSpace.write(new Work());

		NormalRocket rocket = gigaSpace.take(new NormalRocket(), 500);
		Charge charge = gigaSpace.take(new SQLQuery<Charge>(Charge.class, "amount < 500"));

		assertNotNull(rocket);
		assertNotNull(charge);
		assertEquals(500, rocket.getChargeAmount() + charge.getAmount());
	}

	@Test
	public void testCreateOrderRocketRedBlue() {
		gigaSpace.write(new Load("DHL6", false, Color.RED));

		gigaSpace.write(new Work());

		OrderRocket rocket = gigaSpace.take(new OrderRocket(), 500);

		assertNotNull(rocket);
		assertEquals("2", rocket.getOrderId());
	}

	@Test
	public void testCreateOrderRocketBlue() {
		gigaSpace.write(new Load("DHL6", false, Color.BLUE));

		gigaSpace.write(new Work());

		OrderRocket rocket = gigaSpace.take(new OrderRocket(), 500);

		assertNotNull(rocket);
		assertEquals("1", rocket.getOrderId());
	}

	@Test
	public void testCreate2OrderRocketBlue() {
		gigaSpace.write(new Order("3", 4, 0, 3, 0, "adress"));
		for (int i = 0; i < 6; i++) {
			gigaSpace.write(new Load("DHL6", false, Color.GREEN));
		}
		gigaSpace.write(new Work());
		gigaSpace.write(new Work());

		OrderRocket rocket = gigaSpace.read(new OrderRocket(), 500);
		assertNotNull(rocket);
		assertEquals("3", rocket.getOrderId());

		NormalRocket normalRocket = gigaSpace.take(new NormalRocket(), 100);
		assertNull(normalRocket);

		int amount = gigaSpace.count(new OrderRocket());
		assertEquals(2, amount);
	}

	@Test
	public void testCreateRocketWith2Charge() {
		gigaSpace.write(new Load("DHL6", false, Color.GREEN));
		gigaSpace.change(new Charge(), new ChangeSet().set("amount", 100));
		gigaSpace.write(new Charge("DHL4"));

		gigaSpace.write(new Work());

		NormalRocket rocket = gigaSpace.take(new NormalRocket(), 500);
		Charge charge = gigaSpace.take(new SQLQuery<Charge>(Charge.class, "amount < 500"));

		assertNotNull(rocket);
		assertNotNull(charge);
		assertEquals(2, rocket.getCharges().size());
		Integer sum = rocket.getCharges().get(0).getAmount() + rocket.getCharges().get(1).getAmount();
		System.out.println(rocket.getCharges().get(0));
		System.out.println(rocket.getCharges().get(1));
		assertEquals(sum, rocket.getChargeAmount());
		assertEquals(600, rocket.getChargeAmount() + charge.getAmount());
	}

	@Test
	public void testWithNotEnoughCharge() {
		gigaSpace.write(new Load("DHL6", false, Color.GREEN));
		gigaSpace.change(new Charge(), new ChangeSet().set("amount", 100));

		gigaSpace.write(new Work());

		assertNull(gigaSpace.take(new NormalRocket(), 500));
		assertNotNull(gigaSpace.take(new SQLQuery<Charge>(Charge.class, "amount < 500")));
	}
}
