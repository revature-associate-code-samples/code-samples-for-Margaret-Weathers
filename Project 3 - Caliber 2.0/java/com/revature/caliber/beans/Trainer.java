package com.revature.caliber.beans;

import java.util.Set;

/**
 * The type Trainer.
 */
public class Trainer {

	private int trainerId;
	private String name;

	public Trainer() {
		super();
	}

	public Trainer(int trainerId, String name, TrainerRole tier, Set<BatchEntity> batches) {
		super();
		this.trainerId = trainerId;
		this.name = name;
	}

	public int getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "Trainer [trainerId=" + trainerId + ", name=" + name + "]";
	}

}
