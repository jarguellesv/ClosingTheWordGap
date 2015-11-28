package edu.gatech.wordgap;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import edu.gatech.wordgap.spring.jdbc.dao.VocabQuizDAO;
import edu.gatech.wordgap.spring.jdbc.model.Kid;
import edu.gatech.wordgap.spring.jdbc.model.Question;
import edu.gatech.wordgap.spring.jdbc.model.Score;
import edu.gatech.wordgap.spring.jdbc.model.Stat;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private @Autowired ProfilesDAO profilesDAO;
	private @Autowired VocabQuizDAO quizDAO;

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
		logger.info("adding kid: " + kid.getName());
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
	public String deleteProfile(Locale locale, Model model, @RequestParam("kid") String kid) {
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
		logger.info(kidObj.getName());
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
		List<Score> scores = quizDAO.getScores();
		List<Integer> params = new ArrayList<Integer>();
		List<Question> questions = new ArrayList<Question>();
		for(Score score : scores)
			params.add(score.getQuestion_id());
		if(params.size() > 0)
			questions = quizDAO.getQuestions(params);
		List<Stat> stats = buildStatistics(scores, questions);
		model.addAttribute("allStats", stats);
		return "stats";
	}

	@RequestMapping(value = "/kid_stats", method = RequestMethod.POST)
	public String kidStats(Locale locale, Model model, @RequestParam("kid") String kid) {
		logger.info("Welcome to Kid Stats!");
		int id = Integer.parseInt(kid);
		Kid kidObj = profilesDAO.getKid(id);
		logger.info(kidObj.getName());
		model.addAttribute("kid", kidObj);
		model.addAttribute("bestCategory", "Synonyms");
		model.addAttribute("bestCategoryEfficiency", "85%");
		model.addAttribute("worstCategory", "Antonyms");
		model.addAttribute("worstCategoryEfficiency", "52%");
		List<Score> scores = quizDAO.getScoresById(id);
		List<Integer> params = new ArrayList<Integer>();
		List<Question> questions = new ArrayList<Question>();
		for(Score score : scores)
			params.add(score.getQuestion_id());
		if(params.size() > 0)
			questions = quizDAO.getQuestions(params);
		List<Stat> stats = buildStatistics(scores, questions);
		model.addAttribute("allStats", stats);
		return "kid_stats";
	}


	private List<Stat> buildStatistics(List<Score> scores,
			List<Question> questions) {
		Map<Integer, Question> qMap = new HashMap<Integer, Question>();
		Map<String, Stat> statMap = new HashMap<String, Stat>();
		for(Question q : questions)
			qMap.put(q.getId(), q);
		for(Score s: scores)
		{
			int qid = s.getQuestion_id();
			Question q = qMap.get(qid);
			for(String k : q.getKeywordsSplit()){
				Stat stat = statMap.get(k.trim());
				if(stat == null)
				{
					stat = new Stat();
					stat.setName(k.trim());
					stat.setCorrect(0);
					stat.setTotal(0);
				}
				int correct = stat.getCorrect();
				int total = stat.getTotal();
				
				stat.setTotal(total + 1);
				if(s.getCorrect())
				{
					stat.setCorrect(correct + 1);
				}
				statMap.put(k.trim(), stat);
			}
		}
		return new ArrayList<Stat> (statMap.values());
	}

	@RequestMapping(value = "/save/strategies", method = RequestMethod.GET)
	public String saveStrategies(Locale locale, Model model, @RequestParam("kid") String kid, 
			@RequestParam("strategyEnglish") String strategyEnglish, 
			@RequestParam("strategySpanish") String strategySpanish) {
		logger.info("saving strategies " + strategyEnglish + " and " + strategySpanish + " for kid " + kid);
		int id = Integer.parseInt(kid);
		profilesDAO.updateKidStrategies(id, strategyEnglish, strategySpanish);
		model.addAttribute("newKid", new Kid());
		return "profile";
	}

}
