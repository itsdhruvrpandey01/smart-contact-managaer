package com.smart.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.helper.Message;
import com.smart.services.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotPasswordController {

	@Autowired
	private EmailService emailService;
	
	@GetMapping("/forgot")
	public String forgotPass() {
		return "forgot_password";
	}
	
	@PostMapping("/send-otp")
	public String sendOtp(@RequestParam("email") String email,HttpSession session) {
		System.out.println("otp module");
		Random random = new Random();
		int otp = 1000 + random.nextInt(9999);
		System.out.println(otp);
		
		String subject = "OTP for SCM";
		String message = "OTP = "+otp;
		String to = email;
		boolean f = this.emailService.sendEmail(subject, message, to);
		if(f) {
			return "verify_otp";
		}else {
			session.setAttribute("message", new Message("Check your email again","danger"));
			return "forgot_password";
		}
	}
	
}
