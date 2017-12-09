package pandawan.web;

import java.io.File;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;


import pandawan.dao.RoleRepository;
import pandawan.dao.UtilisateurRepository;
import pandawan.entities.Utilisateur;
import pandawan.entities.Role;

@Controller
public class UtilisateurController {
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(768);
	}
	
	@RequestMapping(value="/login")		
	public String indexLogin(Model model){
		List<Utilisateur>  utilisateurs=utilisateurRepository.findAll();
		model.addAttribute("ListeUtilisateurs", utilisateurs);	
		return "login";
	}

	
	@RequestMapping(value="/saveUtilisateur",method=RequestMethod.POST)	
	public String saveUtilisateur(Model model,Utilisateur user,WebRequest request){
		Role roleUser = roleRepository.save(new Role("USER","SIMPLE UTILISATEUR"));
		List<Role> listeRole = new ArrayList<Role>();
		listeRole.add(roleUser);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setRole(listeRole);
		user=utilisateurRepository.save(user);
		return "home";
	}
	

	
}