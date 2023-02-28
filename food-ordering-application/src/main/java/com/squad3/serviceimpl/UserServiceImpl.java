package com.squad3.serviceimpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad3.entity.Orders;
import com.squad3.entity.User;
import com.squad3.exception.IllegalArgumentException;
import com.squad3.exception.NoOrderHistoryFoundException;
import com.squad3.exception.UserIdNotFoundException;
import com.squad3.repository.OrdersRepository;
import com.squad3.repository.UserRepository;
import com.squad3.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<Orders> getOrderHistory(Long userId, String timeframe) {

		User users = userRepository.findById(userId)
				.orElseThrow(() -> new UserIdNotFoundException("User not found with id: " + userId));

		LocalDate startDate;
		LocalDate endDate;

		if (timeframe.equalsIgnoreCase("week")) {
			startDate = LocalDate.now().minusDays(7);
			endDate = LocalDate.now();
		} else if (timeframe.equalsIgnoreCase("month")) {
			startDate = LocalDate.now().minusMonths(1);
			endDate = LocalDate.now();
		} else {
			throw new IllegalArgumentException("Invalid timeframe");
		}
		List<Orders> ordersList = ordersRepository.findByUser_UserIdAndOrderDateBetween(userId, startDate, endDate);
		if (ordersList.isEmpty()) {
			throw new NoOrderHistoryFoundException("No order history found for user id: " + userId);
		}

		return ordersList;
	}

}
