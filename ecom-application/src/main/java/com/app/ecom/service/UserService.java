package com.app.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	
	public List<UserResponse> fetchAllUsers(){
		List<User> userList = userRepository.findAll();
		List<UserResponse> responseList = new ArrayList<>();

	    for (User user : userList) {
	        UserResponse response = mapToUserResponse(user);
	        responseList.add(response);
	    }

	    return responseList;
	}
	
	public Optional<UserResponse> fetchUser(Long id){
		Optional<User> userOptional = userRepository.findById(id);

	    if (userOptional.isPresent()) {
	        UserResponse response = mapToUserResponse(userOptional.get());
	        return Optional.of(response);
	    } else {
	        return Optional.empty();
	    }
	}
	
	public void addUser(UserRequest userRequest){
		User user=new User();
		updateUserFromRequest(user,userRequest);
		userRepository.save(user);
	}
	
	

	public boolean updateUser(Long id, UserRequest userRequest ){
		Optional<User> optionalUser = userRepository.findById(id);
	    
	    if (optionalUser.isPresent()) {
	        User user = optionalUser.get();
	        updateUserFromRequest(user,userRequest);
	        userRepository.save(user);
	        return true; 
	    } else {
	        return false; 
	    }
	}
	
	private UserResponse mapToUserResponse(User user) {
		UserResponse response=new UserResponse();
		response.setId(String.valueOf(user.getId()));
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setEmail(user.getEmail());
		response.setPhone(user.getPhone());
		response.setRole(user.getRole());
		
		if(user.getAddress()!=null) {
			AddressDTO addressDTO =new AddressDTO();
			addressDTO.setStreet(user.getAddress().getStreet());
			addressDTO.setCity(user.getAddress().getCity());
			addressDTO.setState(user.getAddress().getState());
			addressDTO.setCountry(user.getAddress().getCountry());
			addressDTO.setZipcode(user.getAddress().getZipcode());
			response.setAddress(addressDTO);
		}
		return response;
	}
	
	private void updateUserFromRequest(User user, UserRequest userRequest) {
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setEmail(userRequest.getEmail());
		user.setPhone(userRequest.getPhone());
		
		if(userRequest.getAddress()!=null) {
			Address address =new Address();
			address.setStreet(userRequest.getAddress().getStreet());
			address.setCity(userRequest.getAddress().getCity());
			address.setState(userRequest.getAddress().getState());
			address.setCountry(userRequest.getAddress().getCountry());
			address.setZipcode(userRequest.getAddress().getZipcode());
			user.setAddress(address);
		}
		
	}
	

}
