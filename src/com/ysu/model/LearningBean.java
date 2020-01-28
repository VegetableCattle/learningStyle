package com.ysu.model;

public class LearningBean {
	private String learningId;
	private String dlyl;
	private String gwzj;
	private String hycs;
	public String getLearningId() {
		return learningId;
	}
	public void setLearningId(String learningId) {
		this.learningId = learningId;
	}
	public String getDlyl() {
		return dlyl;
	}
	public void setDlyl(String dlyl) {
		this.dlyl = dlyl;
	}
	public String getGwzj() {
		return gwzj;
	}
	public void setGwzj(String gwzj) {
		this.gwzj = gwzj;
	}
	public String getHycs() {
		return hycs;
	}
	public void setHycs(String hycs) {
		this.hycs = hycs;
	}
	
	public LearningBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LearningBean(String learningId, String dlyl, String gwzj, String hycs) {
		super();
		this.learningId = learningId;
		this.dlyl = dlyl;
		this.gwzj = gwzj;
		this.hycs = hycs;
	}
	@Override
	public String toString() {
		return "LearningBean [learningId=" + learningId + ", dlyl=" + dlyl + ", gwzj=" + gwzj + ", hycs=" + hycs + "]";
	}

	
}
