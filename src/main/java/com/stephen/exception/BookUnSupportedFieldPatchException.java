/**
 * Reference: https://www.mkyong.com/spring-boot/spring-rest-hello-world-example/
 */
package com.stephen.exception;

import java.util.Set;

public class BookUnSupportedFieldPatchException extends RuntimeException {

    public BookUnSupportedFieldPatchException(Set<String> keys) {
        super("Field " + keys.toString() + " update is not allow.");
    }

}