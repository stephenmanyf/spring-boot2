/**
 * Reference: https://www.mkyong.com/spring-boot/spring-rest-hello-world-example/
 */
package com.stephen.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Book id not found : " + id);
    }

}
