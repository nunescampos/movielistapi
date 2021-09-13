package com.texoit.repository;

import org.springframework.data.domain.Pageable;



import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

import com.texoit.model.BlogPost;

/**
 * Repository layer which has the interface to interact with the Aluysio database
 * @author aluysio
 *
 */
public interface BlogPostRepository extends CrudRepository<BlogPost, Integer> {
	
	Slice<BlogPost> findAll(Pageable pageable);

	Slice<BlogPost> findPreviousWin(Pageable pageable, String producer, long intervalo, long year);

	Slice<BlogPost> findFollowingWin(Pageable pageable, String producer, long intervalo, long year);

	
}
