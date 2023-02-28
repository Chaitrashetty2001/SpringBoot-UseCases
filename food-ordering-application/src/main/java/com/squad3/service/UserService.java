package com.squad3.service;

import java.util.List;

import com.squad3.entity.Orders;

public interface UserService {

	List<Orders> getOrderHistory(Long userId, String timeframe);



}
