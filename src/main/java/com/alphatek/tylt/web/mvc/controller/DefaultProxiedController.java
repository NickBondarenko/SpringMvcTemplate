package com.alphatek.tylt.web.mvc.controller;

import org.springframework.ui.ModelMap;

/**
 * Created with IntelliJ IDEA.
 * User: jason.dimeo
 * Date: 5/11/13
 * Time: 7:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultProxiedController extends AbstractController implements ProxiedController {
	@Override public String handleProxiedRequest(String param, ModelMap modelMap) {
		return "";
	}
}
