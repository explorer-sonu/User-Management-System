package in.sonu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sonu.dto.LoginFormDTO;
import in.sonu.dto.RegisterFormDTO;
import in.sonu.dto.ResetPwdFormDTO;
import in.sonu.dto.UserDTO;
import in.sonu.entities.CityEntity;
import in.sonu.entities.CountryEntity;
import in.sonu.entities.StateEntity;
import in.sonu.entities.UserEntity;
import in.sonu.repo.CityRepo;
import in.sonu.repo.CountryRepo;
import in.sonu.repo.StateRepo;
import in.sonu.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private CountryRepo countryRepo;
	
	@Autowired
	private StateRepo stateRepo;
	
	@Autowired
	private CityRepo cityRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EmailService emailservice;
	
	@Override
	public Map<Integer, String> getCountries() {
		
		Map<Integer, String> countryMap = new HashMap<>();
		
		List<CountryEntity> countriesList = countryRepo.findAll();
				
		countriesList.stream().forEach(c -> {
			countryMap.put(c.getCountryId(), c.getCountryName());
		});
		
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		
		Map<Integer, String> statesMap = new HashMap<>();
		
		List<StateEntity> statesList = stateRepo.findByCountryCountryId(countryId);
		
		statesList.forEach(s -> {
			statesMap.put(s.getStateId(), s.getStateName());
		});
		
		return statesMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {

		Map<Integer, String> citiesMap = new HashMap<>();

		List<CityEntity> citiesList = cityRepo.findByStateId(stateId);
		
		citiesList.forEach(s -> {
			citiesMap.put(s.getCityId(), s.getCityName());
		});
		
		return citiesMap;
	}

	@Override
	public boolean duplicateEmailCheck(String email) {
		
		UserEntity byEmail = userRepo.findByEmail(email);
		
		if(byEmail != null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean saveUser(RegisterFormDTO regFormDTO) {

		UserEntity userEntity = new UserEntity();
		
		BeanUtils.copyProperties(regFormDTO, userEntity);
		
		CountryEntity country = countryRepo.findById(regFormDTO.getCountryId()).orElse(null);
		userEntity.setCountry(country);
		
		StateEntity state = stateRepo.findById(regFormDTO.getStateId()).orElse(null);
		userEntity.setState(state);
		
		CityEntity city= cityRepo.findById(regFormDTO.getCityId()).orElse(null);
		userEntity.setCity(city);
		
		String randomPwd = generateRandomPwd();
		
		userEntity.setPwd(randomPwd);
		userEntity.setPwdUpdated("No");
		
		UserEntity savedUser = userRepo.save(userEntity);
		
		if(null != savedUser.getUserId()) {
			String subject = "Your Account Created";
			String body = "Your Password To Login :"+ randomPwd;
			String to = regFormDTO.getEmail();
			
			emailservice.sendEmail(subject, body, to);
			
			return true;
		}
		
		return false;
	}

	@Override
	public UserDTO login(LoginFormDTO loginFormDTO) {
				
		UserEntity userEntity = userRepo.findByEmailAndPwd(loginFormDTO.getEmail(), loginFormDTO.getPwd());
		
		if(userEntity != null) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDTO);
			return userDTO;
		}
		return null;
	}

	@Override
	public boolean resetPwd(ResetPwdFormDTO resetPwdDTO) {
		
	  String email = resetPwdDTO.getEmail();
	  
	  UserEntity entity = userRepo.findByEmail(email);
		
	  // setting new password
	  entity.setPwd(resetPwdDTO.getNewPwd());
	  entity.setPwdUpdated("Yes");
	  
	  userRepo.save(entity);
	  
		return true;
	}
	
	private String generateRandomPwd() {
		
		String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		
		String alphabets = upperCaseLetters + lowerCaseLetters;
		
		Random random = new Random();
		
		StringBuffer generatedPwd = new StringBuffer();
		
		for(int i = 0; i<5; i++) {
			int nextIndex = random.nextInt(alphabets.length());
			generatedPwd.append(alphabets.charAt(nextIndex));
		}
		return generatedPwd.toString();
	}

}
