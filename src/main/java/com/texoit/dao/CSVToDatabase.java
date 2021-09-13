package com.texoit.dao;

import java.util.List;




import com.texoit.model.BlogPost;

/**
 * Defines the DAO API for implementation for CSV to Database
 * @author aluysio
 *
 */
public interface CSVToDatabase {
	public enum HEADERS {
		year,title, studios, producer, winner
	}

	public void initDatabase();
	public List<BlogPost> csvBlogToDatabase();
	
}
