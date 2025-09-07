package me.mispz.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "obavestenje")
public class Obavestenje {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "obavestenjeId", nullable = false)
	private int id;
	@Column(name = "obavestenjeText", nullable = false)
	private String text;
	@Column(name = "obavestenjeDate", nullable = false)
	private Timestamp date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Obavestenje{" +
				"id=" + id +
				", text='" + text + '\'' +
				", date=" + date +
				'}';
	}
}
