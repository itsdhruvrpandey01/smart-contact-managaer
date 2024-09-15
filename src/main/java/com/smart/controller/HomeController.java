package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; 
	
	@Autowired
	private UserRepository userRepository;
	@GetMapping("/")
	public String home(Model m) {
		m.addAttribute("title", "Home-controller smart controller.");
		return "home";
	}
	@GetMapping("/about")
	public String about(Model m) {
		m.addAttribute("title", "About-controller smart controller.");
		return "about";
	}
	@GetMapping("/signup")
	public String signup(Model m) {
		m.addAttribute("user",new User());
		return "signup";
	}
	@PostMapping("/do_register")
	public String postMethodName(@Valid @ModelAttribute("user")User user,BindingResult result1,Model model,@RequestParam(value="agreement",defaultValue="false")boolean agreement,HttpSession session) {
		try {
			System.out.println("Agreement "+agreement);
			System.out.println(user);
			if(!agreement) {
				System.out.println("not accepted the T&C");
				throw new Exception("You have not Agreed T&C");
			}
			if(result1.hasErrors()) {
				System.out.println("Error" + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			User result = userRepository.save(user); 
			model.addAttribute("user",new User());
			session.setAttribute("message", new Message("Successfully Registered.. ", "alert-success"));
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message", new Message("Something Went Wrong "+e.getMessage(), "alert-danger"));
			return "signup";
		}
		
	}
	
	@GetMapping("/signin")
	public String customLogin(Model model) {
		model.addAttribute("title","Login page");
		return "login";
	}
	
}
