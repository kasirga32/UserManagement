package com.mete.infonal.controller;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.mete.infonal.model.User;
import com.mete.infonal.service.inf.UserServiceInterface;
import com.mete.infonal.util.Key;





@Controller
public class UserController {
	
	private String captchaText; 
	private String inputResponse;
	private ReCaptchaImpl captcha;
	private ReCaptchaResponse captchaResponse;
	final static Logger logger = Logger.getLogger(UserController.class);
	
	/*
	 * DI for UserServiceInterface
	 */
	@Autowired
	private UserServiceInterface userService;

	/**
	 * Sets homepage
	 * Get all saved users in database by invoking UserService.getAllUsers()
	 * @param model
	 * @return to homepage
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String goHomePage(ModelMap model) {
		model.addAttribute("allUsers", userService.getAllUsers());
		logger.info("Return to homepage");
		return "output";
	}

	
	/**
	 * GET Method for getting all saved users then goes back to homepage
	 * @param model
	 * @return to homepage
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String getAllUsers(ModelMap model) {
		model.addAttribute("allUsers", userService.getAllUsers());
		logger.info("All users retrieved");
		return "output";
	}

	
	/**
	 * POST Method for creating and updating user 
	 * @param user 
	 * @param model
	 * @return back to homepage for display new entry
	 */
	@RequestMapping(value="user/save", method = RequestMethod.POST)
	public @ResponseBody RedirectView create(@ModelAttribute User user, ModelMap model) {
		if (StringUtils.hasText(user.getId())) { 
			userService.updateUser(user);
			logger.info(user + " user information just updated.");
		} else { 
			userService.addUser(user);
			logger.info(user + " saved. Back to homepage");
		}
		return new RedirectView("/InfonalUser/user"); // redirect personmanager/person 
		
	}

	
	/**
	 * Get entered input for validate captcha and check with HttpServletRequest
	 * @param model
	 * @param request
	 * @return true if response is valid
	 */
	@RequestMapping(value = "/user/validate", method = RequestMethod.POST)
	public @ResponseBody String isValidCaptcha(ModelMap model, HttpServletRequest request) {
		captcha = new ReCaptchaImpl();

		captchaText = request.getParameter("recaptcha_challenge_field");
		inputResponse = request.getParameter("recaptcha_response_field");
		captcha.setPrivateKey(Key.RECAPTCHA_PRIVATE_KEY);
		captchaResponse = captcha.checkAnswer(request.getRemoteAddr(), captchaText, inputResponse);
		if(captchaResponse.isValid()){
			logger.info("Captcha response is valid. The process completed succesfully");
		}else{
			logger.warn("Captcha response is invalid.");
		}
		
		return ""+captchaResponse.isValid();
	}

	
	/**
	 * POST Method for deleting user who is selected for it
	 * @param user
	 * @param model
	 * @return to homepage for new list
	 */
	@RequestMapping(value = "/user/delete", method = RequestMethod.POST)
	public View deletePerson(@ModelAttribute User user, ModelMap model) {
		userService.deleteUser(user);
		logger.warn(user + " user deleted.");
		return new RedirectView("/InfonalUser/user");
	}
	
}