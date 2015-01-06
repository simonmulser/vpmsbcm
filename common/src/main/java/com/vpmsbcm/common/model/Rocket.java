package com.vpmsbcm.common.model;

import java.io.Serializable;
import java.util.List;

import com.gigaspaces.annotation.pojo.FifoSupport;
import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceFifoGroupingProperty;
import com.gigaspaces.annotation.pojo.SpaceId;

@SpaceClass(fifoSupport = FifoSupport.OPERATION)
public class Rocket implements Serializable {

	protected String id;

	protected Wood wood;
	protected Detonator detonator;
	protected List<Charge> charges;
	protected List<Load> loades;

	protected Integer chargeAmount;
	protected State state;

	protected Integer producerID;
	protected Integer controllerID;
	protected Integer exporterID;

	public Rocket() {
	}

	public Rocket(Wood wood, Detonator detonator, List<Charge> charges, Integer chargeAmount, List<Load> loades, Integer producerID) {
		super();
		this.wood = wood;
		this.detonator = detonator;
		this.charges = charges;
		this.chargeAmount = chargeAmount;
		this.loades = loades;
		this.state = State.NOT_TESTED;
		this.producerID = producerID;
	}

	@SpaceId(autoGenerate = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Wood getWood() {
		return wood;
	}

	public void setWood(Wood wood) {
		this.wood = wood;
	}

	public Detonator getDetonator() {
		return detonator;
	}

	public void setDetonator(Detonator detonator) {
		this.detonator = detonator;
	}

	public List<Charge> getCharges() {
		return charges;
	}

	public void setCharges(List<Charge> charges) {
		this.charges = charges;
	}

	public List<Load> getLoades() {
		return loades;
	}

	public void setLoades(List<Load> loades) {
		this.loades = loades;
	}

	public Integer getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Integer chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public Integer getProducerID() {
		return producerID;
	}

	public void setProducerID(Integer producerID) {
		this.producerID = producerID;
	}

	public Integer getControllerID() {
		return controllerID;
	}

	public void setControllerID(Integer controllerID) {
		this.controllerID = controllerID;
	}

	public Integer getExporterID() {
		return exporterID;
	}

	public void setExporterID(Integer exporterID) {
		this.exporterID = exporterID;
	}

	@SpaceFifoGroupingProperty
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Rocket [id=" + id + "wood=" + wood + ", detonator=" + detonator + ", charges=" + charges + ", loades=" + loades + ", chargeAmount=" + chargeAmount + ", state="
				+ state + "]";
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
		Rocket other = (Rocket) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
