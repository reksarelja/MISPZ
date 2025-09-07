package me.mispz.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class Mus2stoId implements Serializable {

	@Column(name = "musterijaId")
	private Integer musterijaId;
	@Column(name = "stoId")
	private Integer stoId;
	@Column(name = "id")
	private Timestamp id;

	public Mus2stoId() {}

	public Mus2stoId(int musterijaId, int stoID, Timestamp id) {
		this.musterijaId = musterijaId;
		this.stoId = stoID;
		this.id = id;
	}

	public int getMusterijaId() {

		return musterijaId;
	}

	public void setMusterijaId(Integer musterijaId) {
		this.musterijaId = musterijaId;
	}

	public Integer getStoId() {
		return stoId;
	}

	public void setStoId(Integer stoId) {
		this.stoId = stoId;
	}

	public Timestamp getId() {
		return id;
	}

	public void setId(Timestamp id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Mus2stoId mus2stoId = (Mus2stoId) o;
		return Objects.equals(musterijaId, mus2stoId.musterijaId) && Objects.equals(stoId, mus2stoId.stoId) && Objects.equals(id, mus2stoId.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(musterijaId, stoId, id);
	}
}

