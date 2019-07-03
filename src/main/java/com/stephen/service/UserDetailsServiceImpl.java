package com.stephen.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stephen.model.User;
import com.stephen.model.UserDetailsImpl;
import com.stephen.repository.UserRepository;

//@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepo.findByFirstName(username);
		return Optional.ofNullable(optionalUser)
						.orElseThrow(() -> new UsernameNotFoundException("Username Not Found"))
						.map(UserDetailsImpl::new).get();
	}
}