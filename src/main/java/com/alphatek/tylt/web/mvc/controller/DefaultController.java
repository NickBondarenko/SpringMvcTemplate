package com.alphatek.tylt.web.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jason.dimeo
 * Date: 2013-04-27
 * Time: 3:58 PM
 */
@Controller
public class DefaultController extends AbstractController {
	@RequestMapping(value = "/default")
	public String afterLogin(HttpServletRequest request) {
		return request.isUserInRole("ROLE_ADMIN") ? "redirect:/admin" : "redirect:/";
	}
}