package me.mispz.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name = "nastup")
public class Nastup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "nastupId", nullable = false)
	private int id;
	@Column(name = "nastupName", nullable = false)
	private String nastupName;
	@Column(name = "nastupDate", nullable = false)
	private Timestamp nastupDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNastupName() {
		return nastupName;
	}

	public void setNastupName(String nastupName) {
		this.nastupName = nastupName;
	}

	public Timestamp getNastupDate() {
		return nastupDate;
	}

	public void setNastupDate(Timestamp nastupDate) {
		this.nastupDate = nastupDate;
	}

	@Override
	public String toString() {
		return "Nastup{" +
				"id=" + id +
				", nastupName='" + nastupName + '\'' +
				", nastupDate=" + nastupDate +
				'}';
	}
}
