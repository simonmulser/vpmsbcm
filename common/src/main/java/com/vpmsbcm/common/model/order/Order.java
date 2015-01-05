package com.vpmsbcm.common.model.order;

import java.io.Serializable;

import com.gigaspaces.annotation.pojo.SpaceId;

public class Order implements Serializable {

	private String id;

	private Integer amount;

	private Integer amountRed;
	private Integer amountGreen;
	private Integer amountBlue;

	private String adress;
	private State state;

	public Order() {
	}

	public Order(String id, Integer amount, Integer amountRed, Integer amountGreen, Integer amountBlue, String adress) {
		super();
		this.id = id;
		this.amount = amount;
		this.amountRed = amountRed;
		this.amountGreen = amountGreen;
		this.amountBlue = amountBlue;
		this.adress = adress;
		this.state = State.SEND;
	}

	@SpaceId(autoGenerate = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getAmountRed() {
		return amountRed;
	}

	public void setAmountRed(Integer amountRed) {
		this.amountRed = amountRed;
	}

	public Integer getAmountGreen() {
		return amountGreen;
	}

	public void setAmountGreen(Integer amountGreen) {
		this.amountGreen = amountGreen;
	}

	public Integer getAmountBlue() {
		return amountBlue;
	}

	public void setAmountBlue(Integer amountBlue) {
		this.amountBlue = amountBlue;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", amountRed=" + amountRed + ", amountGreen=" + amountGreen + ", amountBlue=" + amountBlue + ", adress=" + adress + ", state=" + state + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
