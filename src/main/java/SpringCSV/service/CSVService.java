package SpringCSV.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import SpringCSV.Repository.DeveloperTutorialRepository;
import SpringCSV.helper.CSVHelper;
import SpringCSV.model.DeveloperTutorial;

@Service
public class CSVService {

	@Autowired
	DeveloperTutorialRepository repository;
	
	public void save(MultipartFile file) {
		try {
			List<DeveloperTutorial> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
			repository.saveAll(tutorials);
		}catch(IOException e) {
			throw new RuntimeException("fail to store csv data : "+e.getMessage());
		}
	}
	
	public ByteArrayInputStream load() {
		List<DeveloperTutorial> tutorials = repository.findAll();
		
		ByteArrayInputStream in =CSVHelper.tutorialsToCSV(tutorials);
		return in;
	}
	
	public List<DeveloperTutorial> getALlTutorials() {
		return repository.findAll();
	}
}

