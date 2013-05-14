package com.alphatek.tylt.web.mvc.controller;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
@Validated
public interface ProxiedController extends Controller {
	@RequestMapping(method = RequestMethod.GET)
	public String handleProxiedRequest(@RequestParam @NotBlank String param, ModelMap modelMap);
}