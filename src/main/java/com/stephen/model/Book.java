/**
 * Reference: https://www.mkyong.com/spring-boot/spring-rest-hello-world-example/
 * Reference: https://www.mkyong.com/spring-boot/spring-rest-validation-example/
 */
package com.stephen.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.stephen.validator.Author;

@Table(name="book")
@Entity
public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="book_id")
	private Long id;
	
	@NotEmpty(message = "Please provide a name")
	private String name;
	
	@Author
	@NotEmpty(message = "Please provide an author")
	private String author;
	
	@NotNull(message = "Please provide a price")
	@DecimalMin("1.00")
	private BigDecimal price;
	
	public Book(String name, String author, BigDecimal price) {
		this.name = name;
		this.author = author;
		this.price = price;
	}
	
	//for unit test only
	public Book(Long id, String name, String author, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.price = price;
	}
	
	public Book() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
}
