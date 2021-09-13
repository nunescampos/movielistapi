package com.texoit.model;

import java.io.Serializable;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * JavaBean to represent each entry from CSV as blogPost object.
 * Contains validation checks which invokes blogPostExceptionHandler if not a valid bean
 * @author aluysio
 *
 */
@Validated
@Table("blogpost_table")
public class BlogPost implements Serializable {
	
	@PrimaryKey
	@NotNull
	private Integer id;
	
	
	@NotEmpty(message = "Por favor insira um ano de lançamento")
	@Size(min=1,max = 4,message="Insira um ano long")
	private long year;
	
	@NotEmpty(message = "Por favor insira um título")
	@Size(min=5,max = 140,message="Insira um título entre 0 & 140 characters long")
	private String title;
	
	
	@NotEmpty(message = "Por favor insira um intervalo")
	@Size(min=0,max = 2,message="Insira um intervalo entre 0 & 2 characters long")
	private String intervalo;
	
	@NotEmpty(message = "Por favor insira um previousWin de lançamento")
	@Size(min=1,max = 4,message="Insira um previousWin long")
	private long previousWin;
	
	@NotEmpty(message = "Por favor insira um followingWin de lançamento")
	@Size(min=1,max = 4,message="Insira um followingWin long")
	private long followingWin;
	
	
	@NotEmpty(message = "Por favor insira um studios")
	private String studios;
	
	@NotEmpty(message = "Por favor insira um producer")
	private String producer;
	
	@NotEmpty(message = "Por favor insira um ganhador")
	@Size(min=3,max=3,message="insira um sim ou não long")
	private String winner;
	
	
	
	public BlogPost() {
	
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

	public String getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(String intervalo) {
		this.intervalo = intervalo;
	}

	public long getPreviousWin() {
		return previousWin;
	}

	public void setPreviousWin(long previousWin) {
		this.previousWin = previousWin;
	}

	public long getFollowingWin() {
		return followingWin;
	}

	public void setFollowingWin(long followingWin) {
		this.followingWin = followingWin;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	public long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
	}

	public String getStudios() {
		return studios;
	}

	public void setStudios(String studios) {
		this.studios = studios;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(title).append(":").append(producer.substring(0,10));
		
		return sb.toString();
	}
	
	


}
