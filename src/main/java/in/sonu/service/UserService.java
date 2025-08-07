package in.sonu.service;

import java.util.Map;

import in.sonu.dto.LoginFormDTO;
import in.sonu.dto.RegisterFormDTO;
import in.sonu.dto.ResetPwdFormDTO;
import in.sonu.dto.UserDTO;

public interface UserService {
	
	public Map<Integer, String> getCountries();
	
	public Map<Integer, String> getStates(Integer countryId);
	
	public Map<Integer, String> getCities(Integer stateId);
	
	public boolean duplicateEmailCheck(String email);
	
	public boolean saveUser(RegisterFormDTO regFormDTO);
	
	public UserDTO login(LoginFormDTO loginFormDTO);
	
	public boolean resetPwd(ResetPwdFormDTO resetPwdDTO);	
	
}
