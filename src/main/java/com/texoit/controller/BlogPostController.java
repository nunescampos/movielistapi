package com.texoit.controller;

import java.util.ArrayList;



import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.texoit.model.BlogPost;
import com.texoit.repository.BlogPostRepository;
/**
 * Creates the RESTFul end-points to the Aluysio Database
 * @author Aluysio
 *
 */
@RestController
@RequestMapping("blogpost")
public class BlogPostController {
	
	@Autowired
	BlogPostRepository blogspotrepo;
	
	@GetMapping("/all")
	public List<BlogPost> getBlogPosts() {
		Iterable<BlogPost> result = blogspotrepo.findAll();
		List<BlogPost> blogpostList = new ArrayList<BlogPost>();
		result.forEach(blogpostList::add);
		return blogpostList;
	}
	
	/**
	 * Get a set of all winner in the post
	 * @return
	 */
	@GetMapping("/winner")
	public Set<String> getAllWinner() {
		Iterable<BlogPost> result = blogspotrepo.findAll();
		Set<String> set = new HashSet<>();
		
		result.forEach(bp -> {
			String[] sp = bp.getWinner().replaceAll("\\s+","").split(",");
			for (String s : sp) {
			set.add(s);
			}
		});
		return set;
	}
	
	/**
	 * On each return, the paginated result has @size of 2
	 * @param page
	 * @return
	 */
	@GetMapping("/all/{page}")
	public List<BlogPost> getBlogPostsPaginated( @PathVariable Integer page) {
		if(page < 0)
			throw new MethodArgumentTypeMismatchException(page, Integer.class, "page",null, new Throwable());
		
		final int size = 2;
		int currpage = 0;
		
		Slice<BlogPost> slice = blogspotrepo.findAll(CassandraPageRequest.first(size));
		while(slice.hasNext() && currpage < page) {
			slice = blogspotrepo.findAll(slice.nextPageable());
			currpage++;
		}
		
		return slice.getContent();
	}

	@GetMapping("/all/{intervaloanterior}")
	public Optional<BlogPost> getIntervaloAnterior(@Valid @RequestBody BlogPost nbp, @PathVariable Integer page, @PathVariable Integer id,  @PathVariable String producer, @PathVariable Long anoAnteriorGanhador, Long proximoAnoGanhador ) {
		if(page < 0)
			throw new MethodArgumentTypeMismatchException(page, Integer.class, "page",null, new Throwable());
		
		final int size = 2;
		int currpage = 0;
		
		int intervalo = 0;
		
		String winner = "YES";
		
		Slice<BlogPost> slice = blogspotrepo.findPreviousWin(CassandraPageRequest.first(size),producer, intervalo, anoAnteriorGanhador);

		Optional<BlogPost> optionalBp = blogspotrepo.findById(id);

		
		while(slice.hasNext() && currpage < page) {
			

			if (optionalBp.isPresent()) {
				BlogPost bp = optionalBp.get();
				
				bp.setId(nbp.getId());
				bp.setYear(nbp.getYear());
				bp.setTitle(nbp.getTitle());
				bp.setStudios(nbp.getStudios());
				bp.setProducer(nbp.getProducer());
				if (bp.getWinner().equalsIgnoreCase("yes")) {
					slice = blogspotrepo.findPreviousWin(slice.nextPageable(),producer, intervalo, anoAnteriorGanhador);
			}

			intervalo +=1;
		
			currpage++;
		}
		
	}
		return optionalBp;
		
	}
	
	@GetMapping("/all/{intervaloproximo}")
	public Optional<BlogPost> getIntervaloProximo(@Valid @RequestBody BlogPost nbp, @PathVariable Integer id, @PathVariable String producer, @PathVariable Integer page,Long proximoAnoGanhador ) {
		if(page < 0)
			throw new MethodArgumentTypeMismatchException(page, Integer.class, "page",null, new Throwable());
		
		final int size = 2;
		int currpage = 0;
		
		int intervalo = 0;
		
		Slice<BlogPost> slice = blogspotrepo.findFollowingWin(CassandraPageRequest.first(size), producer, intervalo, proximoAnoGanhador);

		Optional<BlogPost> optionalBp = blogspotrepo.findById(id);

		
		while(slice.hasNext() && currpage < page) {
			
		
			if (optionalBp.isPresent()) {
				BlogPost bp = optionalBp.get();
				
				
				if (bp.getWinner().equalsIgnoreCase("yes")) {
					slice = blogspotrepo.findFollowingWin(slice.nextPageable(),producer,intervalo, proximoAnoGanhador);
			}

			
			
			slice = blogspotrepo.findFollowingWin(slice.nextPageable(), producer, intervalo, proximoAnoGanhador );
			
			currpage++;
			intervalo+=1;
			}
		}
		
		return optionalBp;
	}

	
	@GetMapping("/{id}")
	public Optional<BlogPost> getBlogPost(@PathVariable Integer id) {
		Optional<BlogPost> bp = blogspotrepo.findById(id);
		return bp;
	}

	@PutMapping("/{id}")
	public Optional<BlogPost> updateBlogPost(@Valid @RequestBody BlogPost nbp, @PathVariable Integer id) {
		Optional<BlogPost> optionalBp = blogspotrepo.findById(id);
		if (optionalBp.isPresent()) {
			BlogPost bp = optionalBp.get();
			
			bp.setId(nbp.getId());
			bp.setYear(nbp.getYear());
			bp.setTitle(nbp.getTitle());
			bp.setStudios(nbp.getStudios());
			bp.setProducer(nbp.getProducer());
			bp.setWinner(nbp.getWinner());
			blogspotrepo.save(bp);
		}

		return optionalBp;
	}

	@DeleteMapping(value = "/{id}", produces = "application/json; charset=utf-8")
	public String deleteBlogSpot(@PathVariable Integer id) {
		Boolean result = blogspotrepo.existsById(id);
		blogspotrepo.deleteById(id);
		return "{ \"success\" : " + (result ? "true" : "false") + " }";
	}

	@PostMapping("/")
	public BlogPost addBlogSpot(@Valid @RequestBody BlogPost bp) {
		Integer id = new Random().nextInt();
		bp.setId(id);
		blogspotrepo.save(bp);
		return bp;
	}
	
	


}
