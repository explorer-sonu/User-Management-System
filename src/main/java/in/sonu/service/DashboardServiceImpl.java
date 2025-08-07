package in.sonu.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import in.sonu.dto.QutoApiResponseDTO;

@Service
public class DashboardServiceImpl implements DashboardService {
	
	private String quoteApiURL = "https://dummyjson.com/quotes/random"; 
	
	@Override
	public QutoApiResponseDTO getQuote() {
		
		RestTemplate rt = new RestTemplate();
		
		ResponseEntity<QutoApiResponseDTO> forEntity =
				rt.getForEntity(quoteApiURL, QutoApiResponseDTO.class);
		
		QutoApiResponseDTO body = forEntity.getBody();
		
		return body;
	}
}
