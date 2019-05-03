package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/jsp")
@Controller
public class JspController {

	@GetMapping("/test")
	public ModelAndView test(ModelAndView model) {
		model.setViewName("welcome");
		model.addObject("hello", "welcome to jsp");
		return model;
	}
	
}
