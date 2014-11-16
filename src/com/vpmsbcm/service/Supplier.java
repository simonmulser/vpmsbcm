package com.vpmsbcm.service;

import java.util.Random;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import com.j_spaces.core.client.SQLQuery;
import com.vpmsbcm.model.IOrderable;
import com.vpmsbcm.model.PropellingCharge;
import com.vpmsbcm.model.Woodstick;
import com.vpmsbcm.model.Woodstick;

public class Supplier {
	
	public void order(IOrderable orderable, int amount){
		for (int i = 0; i < amount;  i++){
			new Order(orderable).start();
		}
		
	}
	
	private class Order extends Thread{
		
		private IOrderable orderable;
		
		public Order(IOrderable orderable){
			this.orderable = orderable;
		}
		
		@Override
		public void run(){
			try {
				Thread.sleep(new Random().nextInt(1) + 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Connection.INSTANCE.getSpace().write(orderable);
			System.out.println("ordered");
		}
	}
}
