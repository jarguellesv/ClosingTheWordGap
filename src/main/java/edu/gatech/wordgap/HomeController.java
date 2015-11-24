package edu.gatech.wordgap;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.gatech.wordgap.spring.jdbc.dao.ProfilesDAO;
import edu.gatech.wordgap.spring.jdbc.model.Kid;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private @Autowired ProfilesDAO profilesDAO;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		model.addAttribute("newKid", new Kid());
		return "profile";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(Locale locale, Model model) {
		logger.info("Welcome to Profile!");
		model.addAttribute("newKid", new Kid());
		return "profile";
	}

    @RequestMapping(value="/profile", method=RequestMethod.POST)
    public String addProfile(@ModelAttribute("newKid") Kid kid, Model model) {
		logger.info("adding kid: " + kid.getName() + " icon: " + kid.getIcon());
        profilesDAO.addKid(kid);
		model.addAttribute("newKid", new Kid());
        return "profile";
    }

	@RequestMapping(value = "/get/profiles", method = RequestMethod.GET)
	public @ResponseBody List<Kid> getProfiles(Locale locale, Model model) {
		logger.info("getting profiles");
		List<Kid> kids = profilesDAO.getKids();
		return kids;
	}

	@RequestMapping(value = "/delete/profile", method = RequestMethod.GET)
	public String getProfiles(Locale locale, Model model, @RequestParam("kid") String kid) {
		logger.info("deleting profile");
		int id = Integer.parseInt(kid);
		profilesDAO.removeKid(id);
		model.addAttribute("newKid", new Kid());
		return "profile";
	}

	@RequestMapping(value = "/activities", method = {RequestMethod.POST, RequestMethod.GET} )
	public String activities(Locale locale, Model model, @RequestParam(value = "kid", required = false) String kid, HttpServletRequest request) {
		logger.info("Welcome to Activities!");
		if(kid=="" || kid == null){
			kid = request.getSession().getAttribute("kid").toString();
		}
		else{
			request.getSession().setAttribute("kid",kid);
		}
		int id = Integer.parseInt(kid);
		Kid kidObj = profilesDAO.getKid(id);
		model.addAttribute("kid", kidObj);
		return "home";
	}

	@RequestMapping(value = "/stats", method = RequestMethod.GET)
	public String stats(Locale locale, Model model) {
		logger.info("Welcome to Stats!");
		model.addAttribute("bestCategory", "Synonyms");
		model.addAttribute("bestCategoryEfficiency", "85%");
		model.addAttribute("worstCategory", "Antonyms");
		model.addAttribute("worstCategoryEfficiency", "52%");
		return "stats";
	}

}
