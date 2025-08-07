package in.sonu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.sonu.dto.LoginFormDTO;
import in.sonu.dto.QutoApiResponseDTO;
import in.sonu.dto.RegisterFormDTO;
import in.sonu.dto.ResetPwdFormDTO;
import in.sonu.dto.UserDTO;
import in.sonu.service.DashboardService;
import in.sonu.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping("/register")
	public String loadRegisterPage(Model model) {
		
		Map<Integer,String> countriesMap = userService.getCountries();
		model.addAttribute("countries", countriesMap);
		
		RegisterFormDTO registerFormDTO = new RegisterFormDTO();
		model.addAttribute("registerForm", registerFormDTO);
		
		return "register";
	}
	
	@GetMapping("/states/{countryId}")
	@ResponseBody
	public Map<Integer, String> getStates(@PathVariable Integer countryId, Model model) {
		
		Map<Integer,String> statesMap = userService.getStates(countryId);
		System.out.println(statesMap);
		
		return statesMap;
	}
	
	@GetMapping("/cities/{statesId}")
	@ResponseBody
	public Map<Integer, String> getCities(@PathVariable Integer statesId, Model model) {
		
		Map<Integer,String> citiesMap = userService.getCities(statesId);
		
		return citiesMap;
	}
	
	@PostMapping("/register")
	public String handleRegisterPage(RegisterFormDTO registerFormDTO, Model model) {
		
		boolean status = userService.duplicateEmailCheck(registerFormDTO.getEmail());
		if(status) {
			model.addAttribute("emsg", "Duplicate Email Found");
		}else {
			boolean saveUser = userService.saveUser(registerFormDTO);
			if(saveUser) {
				// user saved
				model.addAttribute("smsg", "Registration Success, Please check your email..!!");
			}else {
				// failed to save
				model.addAttribute("emsg", "Registration Failed!");
			}
		}
		model.addAttribute("registerForm", new RegisterFormDTO());
		model.addAttribute("countries", userService.getCountries());
		return "register";
	}
	
	@GetMapping("/")
	public String index(Model model) {
		
		LoginFormDTO loginFormDTO = new LoginFormDTO();
		
		model.addAttribute("loginForm", loginFormDTO);
		
		return "login";
	}
	
	@PostMapping("/login")
	public String handleUserLogin(LoginFormDTO loginFormDTO, Model model) {
		
		UserDTO userDTO = userService.login(loginFormDTO);
		
		if(userDTO == null) {
			model.addAttribute("emsg", "Invalid Credentials");
			model.addAttribute("loginForm", new LoginFormDTO());
		}else {
			String pwdUpdated = userDTO.getPwdUpdated();
			if("Yes".equals(pwdUpdated)) {
				// display dashboard
				return "redirect:dashboard";
			}else {
				// display reset pwd page
				return "redirect:rest-pwd-page?email=" + userDTO.getEmail();
			}
		}
		return "login";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		QutoApiResponseDTO qutoApiResponseDTO = dashboardService.getQuote();
		
		model.addAttribute("quote", qutoApiResponseDTO);
		
		return "dashboard";
	}
	
	@GetMapping("/rest-pwd-page")
	public String loadResetPwdPage(@RequestParam String email, Model model) {
		
		ResetPwdFormDTO resetPwdFormDTO = new ResetPwdFormDTO();
		resetPwdFormDTO.setEmail(email);
		
		model.addAttribute("resetPwd", resetPwdFormDTO);
		
		return "resetPwd";
	}
	
	@PostMapping("/resetPwd")
	public String handlePwdReset(ResetPwdFormDTO resetPwdFormDTO, Model model) {
		
		boolean resetPwd = userService.resetPwd(resetPwdFormDTO);
		
		if(resetPwd) {
			return "redirect:dashboard";
		}
		
		return "resetPwd";
	}
}
