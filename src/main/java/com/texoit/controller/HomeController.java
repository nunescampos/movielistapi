package com.texoit.controller;

import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.texoit.dao.CSVToDatabase;
import com.texoit.model.BlogPost;

/**
 * Basic controller to invoke CSV to database population manually
 * @author aluysio
 *
 */
@RestController
@RequestMapping("init")
public class HomeController {
	
	@Autowired
	CSVToDatabase csvToDatabase;
	
	@GetMapping("/csvtodatabase")
	public List<BlogPost> csvToDatabase() {
		
		return csvToDatabase.csvBlogToDatabase();
	}


}
