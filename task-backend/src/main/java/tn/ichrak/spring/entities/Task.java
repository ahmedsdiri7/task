package tn.ichrak.spring.entities;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task implements Serializable {
	
	private static final long serialVersionUID = 6191889143079517027L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int reference;
	
	@Temporal(TemporalType.DATE)
	private Date dateDebut;
	
	private String typetask;



	@OneToOne(fetch = FetchType.EAGER)
	private Employe employe;
	private  String iduser;

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getTypetask() {
		return typetask;
	}

	public void setTypetask(String typetask) {
		this.typetask = typetask;
	}

	public Employe getEmploye() {
		return employe;
	}

	public void setEmploye(Employe employe) {
		this.employe = employe;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
}