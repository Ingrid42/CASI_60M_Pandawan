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

import org.pac4j.core.client.BaseClient;
import org.pac4j.core.client.Clients;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.oauth.client.LinkedIn2Client;
import org.pac4j.springframework.security.authentication.Pac4jAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.annotation.Secured;
// import org.springframework.security.authentication.AnonymousAuthenticationToken;
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

  @Autowired
  Clients clients;

  @RequestMapping("/login")
  String login(HttpServletRequest request, HttpServletResponse response, Model model) throws HttpAction {
		if (isAuthenticated()) {
			return "redirect:/home";
		}
		List<Utilisateur>  utilisateurs = utilisateurRepository.findAll();
		model.addAttribute("ListeUtilisateurs", utilisateurs);
    final WebContext context = new J2EContext(request, response);
    final LinkedIn2Client linkedinClient = (LinkedIn2Client) clients.findClient("LinkedIn2Client");
    model.addAttribute("linkedinAuthUrl",  getClientLocation(linkedinClient, context));
    return "login";
  }

  public String getClientLocation(LinkedIn2Client client, WebContext context) throws HttpAction {
      return client.getRedirectAction(context).getLocation();
  }

	// protected boolean isAuthenticated() {
	// 	SecurityContext context = SecurityContextHolder.getContext();
	// 	Authentication auth = context.getAuthentication();
	// 	return !(auth instanceof AnonymousAuthenticationToken);
	// }
	protected boolean isAuthenticated() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		System.out.println(auth);
		return (auth != null && auth instanceof Pac4jAuthentication);
	}


	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String home(Model model, WebRequest request){
		return "home";
	}

}
