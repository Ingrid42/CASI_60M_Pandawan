package pandawan.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "utilisateur")

public class Utilisateur implements Serializable {
	
	@Id
	private String login;
	
	@Email
	private String mail;
	
	@NotNull
	private String nom;
	
	@NotNull
	private String prenom;

	@NotNull
	private String password;
	
	@ManyToMany
	private List<Role> role;


	public Utilisateur() {
		super();
	}

	public Utilisateur(String login,String mail, String nom, String prenom, String password, List<Role> role) {
		super();
		this.login=login;
		this.mail = mail;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.role = role;
	}
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}
	
}