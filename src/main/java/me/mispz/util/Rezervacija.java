package me.mispz.util;

import java.sql.Timestamp;

public class Rezervacija {
	private Integer stoId;
	private String musName;
	private String musBroj;
	private Timestamp rezTime;

	public Rezervacija(Integer stoId, String musName, String musBroj, Timestamp rezTime) {
		this.stoId = stoId;
		this.musName = musName;
		this.musBroj = musBroj;
		this.rezTime = rezTime;
	}

	public Integer getStoId() {
		return stoId;
	}

	public void setStoId(Integer stoId) {
		this.stoId = stoId;
	}

	public String getMusName() {
		return musName;
	}

	public void setMusName(String musName) {
		this.musName = musName;
	}

	public Timestamp getRezTime() {
		return rezTime;
	}

	public void setRezTime(Timestamp rezTime) {
		this.rezTime = rezTime;
	}

	public String getMusBroj() {
		return musBroj;
	}

	public void setMusBroj(String musBroj) {
		this.musBroj = musBroj;
	}
	@Override
	public String toString() {
		return "Rezervacija{" +
				"stoId=" + stoId +
				", musName='" + musName + '\'' +
				", musBroj='" + musBroj + '\'' +
				", rezTime=" + rezTime +
				'}';
	}
}
