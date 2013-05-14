package com.alphatek.tylt.web.mvc.controller;

import org.springframework.ui.ModelMap;

public final class DefaultProxiedController extends AbstractController implements ProxiedController {
	@Override public String handleProxiedRequest(String param, ModelMap modelMap) {
		return "";
	}
}