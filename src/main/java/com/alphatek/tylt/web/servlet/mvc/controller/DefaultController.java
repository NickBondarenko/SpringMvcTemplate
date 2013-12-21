package com.alphatek.tylt.web.servlet.mvc.controller;

import com.alphatek.tylt.web.support.Path;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jason.dimeo
 * Date: 2013-04-27
 * Time: 3:58 PM
 */
@Controller
public final class DefaultController extends AbstractController {
	@RequestMapping(value = "/default")
	public String afterLogin(HttpServletRequest request) {
		return request.isUserInRole("ROLE_ADMIN") ? Path.ADMIN.redirect() : Path.ROOT.redirect();
	}
}