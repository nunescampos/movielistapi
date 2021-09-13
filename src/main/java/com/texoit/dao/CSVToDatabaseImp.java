package com.texoit.dao;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.texoit.model.BlogPost;
import com.texoit.repository.BlogPostRepository;

/**
 * Maps the CSV entry to BlogPost JavaBean which is saved into the Aluysio database
 * @author aluysio
 *
 */
@Repository
public class CSVToDatabaseImp implements CSVToDatabase {

	@Autowired
	BlogPostRepository blogpostRepository;
	
	@Override
	public void initDatabase() {
		csvBlogToDatabase();
		return;
	}

	@Override
	public List<BlogPost> csvBlogToDatabase()  {
		List<BlogPost> blogPosts = new ArrayList<>();
		try(Reader in = new FileReader(getClass().getClassLoader().getResource("movielist.csv").getPath())){
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader(HEADERS.class).parse(in);
			records.iterator().next();
			while(records.iterator().hasNext()) {
				CSVRecord record = records.iterator().next();
				BlogPost bp = new BlogPost();
				
				bp.setYear(Integer.parseInt(record.get(HEADERS.year)));
				bp.setTitle(record.get(HEADERS.title));
				bp.setStudios(record.get(HEADERS.studios));
				bp.setProducer(record.get(HEADERS.producer));
				bp.setWinner(record.get(HEADERS.winner));
				
				blogPosts.add(bp);
			}
		} catch (FileNotFoundException ex) {
			//TODO System.out.println("Arquivo não encontrado");
			ex.printStackTrace();
		} catch (IOException io) {
			//TODO System.out.println("IO Exception some");
			io.printStackTrace();
		}
		
		blogpostRepository.saveAll(blogPosts);
		
		return blogPosts;

	}
	

	private LocalDate parseDate(final String in) {
		final List<String> formatterList = Arrays.asList("M.d.yyyy.","M.d.yyyy","MM/d/yyyy","M/d/yyyy","MM.d.yyyy,","MM.d.yy","M/d/yy");
		
		for(String formmater : formatterList) {
			try {
				return LocalDate.parse(in, DateTimeFormatter.ofPattern(formmater));
			} catch (DateTimeParseException px) {
				
			}
		}
		
		return null;
	}

}
