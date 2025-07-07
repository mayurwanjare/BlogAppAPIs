package com.blogset.blog.security;


import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;

public class KeyGenerator {
	
	public static void main(String[] args) {
        byte[] key = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(key);
        System.out.println("Secure JWT Secret: " + encodedKey);
    }

}
