package com.revature.caliber.beans;

import java.io.Serializable;

public class BatchEntity implements Serializable{

	private static final long serialVersionUID = 2916173221333913998L;

	private Integer batchId;
	private Integer weeks;
	
	public BatchEntity() {}

	public BatchEntity(Integer batchId, Integer weeks) {
		super();
		this.batchId = batchId;
		this.weeks = weeks;
	}
	
	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}


	public Integer getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer weeks) {
		this.weeks = weeks;
	}

	@Override
	public String toString() {
		return "BatchEntity [batchId=" + batchId + ", weeks=" + weeks + "]";
	}

	

}
