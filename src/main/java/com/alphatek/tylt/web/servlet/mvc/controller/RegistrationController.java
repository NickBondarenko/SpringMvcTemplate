package com.alphatek.tylt.web.servlet.mvc.controller;

import com.alphatek.tylt.authority.AuthorityGroup;
import com.alphatek.tylt.service.AddressService;
import com.alphatek.tylt.service.RegistrationService;
import com.alphatek.tylt.web.servlet.mvc.model.Country;
import com.alphatek.tylt.web.servlet.mvc.model.RegistrationForm;
import com.alphatek.tylt.web.servlet.mvc.model.State;
import com.alphatek.tylt.web.servlet.mvc.model.User;
import com.alphatek.tylt.web.servlet.mvc.view.View;
import com.alphatek.tylt.web.support.Path;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Registration Controller
 *
 * @author jason.dimeo
 * Date: 2013-03-27:9:35 PM
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController extends AbstractController implements Serializable {
	private final RegistrationService registrationService;
	private final AddressService addressService;

	@Autowired public RegistrationController(RegistrationService registrationService, AddressService addressService) {
		this.registrationService = registrationService;
		this.addressService = addressService;
	}

	@ModelAttribute("registrationForm")
	public RegistrationForm addModel() {
		return new RegistrationForm();
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
		return View.REGISTRATION.getName();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm, Errors errors, ModelMap modelMap) {
		if (errors.hasErrors()) {
			return handleBindingError(errors, modelMap);
		}

		User.Builder user = User.newBuilder(AuthorityGroup.USER)
			.firstName(registrationForm.getFirstName())
			.lastName(registrationForm.getLastName())
			.username(registrationForm.getUsername())
			.password(registrationForm.getPassword())
			.emailAddress(registrationForm.getEmailAddress())
			.address(registrationForm.getAddress());

		registrationService.registerUser(user);

		return Path.ROOT.redirect();
	}

	@RequestMapping(value = "/checkUsername", method = RequestMethod.GET)
	public @ResponseBody Map<String, Boolean> userExists(@RequestParam String username) {
		Map<String, Boolean> result = Maps.newHashMapWithExpectedSize(1);
		result.put("usernameExists", registrationService.userExists(username));
		return result;
	}

	private static String handleBindingError(Errors errors, ModelMap modelMap) {
		modelMap.addAttribute("hasBindingErrors", errors.hasErrors());
		return View.REGISTRATION.getName();
	}

	@Override public int hashCode() {
		return Objects.hashCode(registrationService, addressService);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final RegistrationController other = (RegistrationController) obj;
		return Objects.equal(this.registrationService, other.registrationService) &&
			Objects.equal(this.addressService, other.addressService);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("registrationService", registrationService)
			.add("addressService", addressService)
			.toString();
	}
}