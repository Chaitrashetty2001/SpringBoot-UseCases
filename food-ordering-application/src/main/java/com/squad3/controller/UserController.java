package com.squad3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad3.entity.Orders;
import com.squad3.service.UserService;

@RestController
@RequestMapping(value = "/user-orders")
public class UserController {
	
	 @Autowired
	    private UserService userService;

	    @GetMapping("/{userId}/{timeframe}")
	    public List<Orders> getOrderHistory(@PathVariable Long userId, @PathVariable String timeframe) {
	        return userService.getOrderHistory(userId, timeframe);
	    }
	
}
