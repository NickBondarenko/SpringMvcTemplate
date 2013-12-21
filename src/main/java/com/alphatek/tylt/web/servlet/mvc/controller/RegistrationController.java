package com.alphatek.tylt.web.servlet.mvc.controller;

import static com.alphatek.tylt.web.servlet.mvc.view.View.REGISTRATION;

import com.alphatek.tylt.service.AddressService;
import com.alphatek.tylt.service.RegistrationService;
import com.alphatek.tylt.web.servlet.mvc.model.Country;
import com.alphatek.tylt.web.servlet.mvc.model.State;
import com.alphatek.tylt.web.servlet.mvc.model.User;
import com.alphatek.tylt.web.support.Path;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Contact Controller
 * Date: 2013-03-27:9:35 PM
 * @author jason.dimeo
 */
@Controller
@RequestMapping("/registration")
public final class RegistrationController extends AbstractController {
	private final RegistrationService registrationService;
	private final AddressService addressService;

	@Autowired public RegistrationController(RegistrationService registrationService, AddressService addressService) {
		this.registrationService = Preconditions.checkNotNull(registrationService, "registrationService cannot be null");
		this.addressService = Preconditions.checkNotNull(addressService, "addressService cannot be null");
	}

	@ModelAttribute("user")
	public User addUser() {
		return new User();
	}

	@ModelAttribute("states")
	public List<State> addStates() {
		return addressService.getStateList();
	}

	@ModelAttribute("countries")
	public List<Country> addCountries() {
		return addressService.getCountryList();
	}

	// Default view when request is /registration
	@RequestMapping(method = RequestMethod.GET)
	public String showRegistrationPage() {
		return REGISTRATION.getName();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, ModelMap modelMap) throws BindException {
		if (bindingResult.hasErrors()) {
			return handleBindingError(bindingResult, modelMap);
		}

		registrationService.registerUser(user);

		return Path.ROOT.redirect();
	}

	private static String handleBindingError(BindingResult bindingResult, ModelMap modelMap) {
		modelMap.addAttribute("hasBindingErrors", bindingResult.hasErrors());
		return REGISTRATION.getName();
	}
}