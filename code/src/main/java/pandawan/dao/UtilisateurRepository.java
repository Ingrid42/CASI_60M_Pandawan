package pandawan.dao;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pandawan.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String>{
	
	@Query("select p from Utilisateurs p where p.nom like :x")
	public List<Utilisateur> chercher(@Param("x") String mc) ;
	
	@Query("select p from Utilisateurs p where p.mail like :x")
	public Utilisateur findByEmail(@Param("x")String email);
	
	@Query("select p from Utilisateurs p where p.login like :x")
	public Utilisateur findByLogin(@Param("x")String login);
	
}
