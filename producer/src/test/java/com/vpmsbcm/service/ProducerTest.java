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
import com.vpmsbcm.common.model.Detonator;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.Charge;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.Wood;
import com.vpmsbcm.common.model.Work;

/**
 * Integration test for the Processor. Uses similar xml definition file
 * (ProcessorIntegrationTest-context.xml) to the actual pu.xml. Writs an
 * unprocessed Data to the Space, and verifies that it has been processed by
 * taking a processed one from the space.
 */
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
		gigaSpace.write(new Charge("DHL4"));
		gigaSpace.write(new Load("DHL5", true));
		gigaSpace.write(new Load("DHL6", false));
	}

	@Test
	public void testWithNotEnoughLoad() {
		gigaSpace.write(new Work());

		assertNull(gigaSpace.take(new Rocket(), 500));
		assertEquals(2, gigaSpace.count(new Wood()));
		assertEquals(1, gigaSpace.count(new Detonator()));
	}

	@Test
	public void testCreateRocket() {
		gigaSpace.write(new Load("DHL6", false));

		gigaSpace.write(new Work());

		assertNotNull(gigaSpace.take(new Rocket(), 500));
		assertNotNull(gigaSpace.take(new SQLQuery<Charge>(Charge.class, "amount < 500")));
	}

	@Test
	public void testWithNotEnoughCharge() {
		gigaSpace.write(new Load("DHL6", false));
		gigaSpace.change(new Charge(), new ChangeSet().set("amount", 100));

		gigaSpace.write(new Work());

		assertNull(gigaSpace.take(new Rocket(), 500));
		assertNotNull(gigaSpace.take(new SQLQuery<Charge>(Charge.class, "amount < 500")));
	}
}
