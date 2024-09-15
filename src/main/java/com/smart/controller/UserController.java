package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;



@Controller
public class UserController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	
	// Method for adding common data
	@ModelAttribute
	public void addCommonData(Model model,Principal principal) {
		String userName = principal.getName();
		User user = userRepository.getUserByName(userName);
		model.addAttribute("user",user);
	}
	
	// dash-board home
	@GetMapping("/user/index")
	public String userDashboard(Model model,Principal principal) {
		model.addAttribute("title","userDashBoard");
		return "Normal/dash_board";
	}
	
	//show all contacts of a user
	@GetMapping("user/view-contact/{page}")
	public String showContacts(@PathVariable("page") Integer page, Model m,Principal principal) {
		m.addAttribute("title","Show User Contact");
		
		String userName = principal.getName();
		User user = this.userRepository.getUserByName(userName);
		
		Pageable pageable = PageRequest.of(page, 5);
		
		Page<Contact>contacts = this.contactRepository.findContactByUser(user.getId(),pageable);
		
		m.addAttribute("contacts",contacts);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", contacts.getTotalPages());
		
		return "normal/show_contacts";
	}
	
	@GetMapping("/user/contact/{cId}")
	public String getMethodName(@PathVariable("cId") int cId, Model m,Principal principal) {
		//user fetch for seeing permissions
		String username = principal.getName();
		User user = this.userRepository.getUserByName(username);
		//contact fetch
		Optional<Contact> contactById = this.contactRepository.findById(cId);
		Contact contact = contactById.get();
		if(user.getId()==contact.getUser().getId()) {
			m.addAttribute("contact",contact);
			m.addAttribute("title",contact.getName());
		}
		return "Normal/contact_Details";
	}
	
	//deleting the contact 
	@GetMapping("/user/delete/{cId}")
	public String getMethodName(@PathVariable("cId") int cid,HttpSession session) {
		
		Contact contact = this.contactRepository.findById(cid).get();
		System.out.println(contact);
		// detaching the contact from the user 
		contact.setUser(null);
		this.contactRepository.delete(contact);
		System.out.println("DELETED");
		session.setAttribute("message", new Message("Contact deleted successfully","success"));
		return "redirect:/user/view-contact/0";
	}
	
	
     //	open add form handler
	@GetMapping("user/add-contact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title","Add contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}
	
	//update form 
	@PostMapping("user/update/{cId}")
	public String postMethodName(@PathVariable("cId") int id,Model m) {
		Contact contact = this.contactRepository.findById(id).get();
		m.addAttribute("contact",contact);
		return "Normal/contact_update_form";
	}
	
	@PostMapping("user/update-contact")
    public String update(@ModelAttribute Contact contact,@RequestParam("profileImage") MultipartFile file,Model m,HttpSession session,Principal principal,@RequestParam("id") Integer cid){
        try {
            contact.setId(cid);
            //old contact details
            Contact oldContactDetail = this.contactRepository.findById(contact.getId()).get();

            //image
            if(!file.isEmpty()){
                //file work
                //rewrite


                //delete old photo
            	try {
                    File imageFile = new ClassPathResource("static/img/" + oldContactDetail.getImage()).getFile();
                    Files.deleteIfExists(imageFile.toPath());
                } catch (Exception e) {
                    // Handle exceptions related to file deletion
                }


                //update new photo
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                contact.setImage(file.getOriginalFilename());
            }else{
                contact.setImage(oldContactDetail.getImage());
            }

                User user = this.userRepository.getUserByName(principal.getName());
                contact.setUser(user);
                this.contactRepository.save(contact);
//                m.addAttribute("successes", "Your Contact has been modified!!");
                session.setAttribute("message",new Message("Yous contact has been modified!!!"," alert-success "));


        }catch (Exception e){
            m.addAttribute("error", "Something Went Wrong!!!");
            session.setAttribute("message",new Message("Something Went Wrong!!!"," alert-danger "));


        }
//        System.out.println(contact);
        return "redirect:/user/contact/"+contact.getId();
    }
	
	
	@PostMapping("user/process-contact")
	public String postMethodName(@ModelAttribute Contact contact,@RequestParam("profileImage")MultipartFile file,Principal principal,HttpSession session) {
		try {
			String name = principal.getName();
			User user = this.userRepository.getUserByName(name);
			if(!file.isEmpty()) {
			contact.setImage(file.getOriginalFilename());
			File saveFile = new ClassPathResource("static/img").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}else {
				contact.setImage("profile.png");
			}
			contact.setUser(user);
			user.getContacts().add(contact);
			this.userRepository.save(user);
			System.out.println(contact);
			System.out.println("added to database successfully..!");
			session.setAttribute("message",new Message("Contact added successfully Add more...!", "success"));
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("Something went wrong please try again..", "danger");
		}
		return "normal/add_contact_form";
	}
	
	// dash board
	@GetMapping("user/profile")
	public String postMethodName(Model m) {
		//TODO: process POST request
		m.addAttribute("title","profile");
		return "Normal/profile";
	}
	
	//settings 
	@GetMapping("user/settings")
	public String getSetting() {
		return "Normal/settings";
	}
	
	@PostMapping("user/change-password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,Principal principal,HttpSession session) {
		System.out.println("Old Password "+oldPassword);
		System.out.println("New Password "+newPassword);
		
		String userName = principal.getName();
		User currentUser = this.userRepository.getUserByName(userName);
		if(this.bCryptPasswordEncoder.matches(oldPassword,currentUser.getPassword())) {
			// change
			currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			this.userRepository.save(currentUser);
			session.setAttribute("message",new Message("Your password has been modified !!!"," alert-success "));
		}else {
			session.setAttribute("message",new Message("Old password didn't matched please enter the correct password again !!!"," alert-danger"));
			return "redirect:/user/settings";
		}
		
		return "redirect:/user/index";
	}
	
}
