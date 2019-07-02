/**
 * Reference: https://www.mkyong.com/spring-boot/spring-rest-hello-world-example/
 */
package com.stephen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stephen.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
