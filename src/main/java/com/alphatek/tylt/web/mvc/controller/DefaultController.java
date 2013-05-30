package com.alphatek.tylt.web.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jason.dimeo
 *         Date: 4/27/13
 *         Time: 3:58 PM
 */
@Controller
public class DefaultController {
	@RequestMapping(value = "/default")
	public String afterLogin(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/admin";
		}
		return "redirect:/";
	}
}