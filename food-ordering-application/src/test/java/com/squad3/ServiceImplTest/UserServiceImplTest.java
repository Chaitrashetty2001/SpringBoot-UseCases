package com.squad3.ServiceImplTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.squad3.entity.User;
import com.squad3.exception.NoOrderHistoryFoundException;
import com.squad3.exception.UserIdNotFoundException;
import com.squad3.repository.OrdersRepository;
import com.squad3.repository.UserRepository;
import com.squad3.serviceimpl.UserServiceImpl;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
	@Mock
	private UserRepository userRepository;

	@Mock
	private OrdersRepository ordersRepository;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Test
	void getOrderHistory_WithNoOrderHistory() {

		Long userId = 1L;
		String timeframe = "week";
		User user = new User(1l, "chaitra", "c@gmail.com");

		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		Mockito.when(ordersRepository.findByUser_UserIdAndOrderDateBetween(userId, LocalDate.now().minusDays(7),
				LocalDate.now())).thenReturn(new ArrayList<>());

		assertThrows(NoOrderHistoryFoundException.class, () -> {

			userServiceImpl.getOrderHistory(userId, timeframe);
		});
	}

	@Test
	void getOrderHistory_WithInvalidUser() {

		Long userId = 1L;
		String timeframe = "week";

		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

		assertThrows(UserIdNotFoundException.class, () -> {

			userServiceImpl.getOrderHistory(userId, timeframe);
		});
	}

//	@Test
//	void getOrderHistory_WithValidInput_ReturnsOrderList() {
//		// Arrange
//		Long userId = 1L;
//		String timeframe = "week";
//		User user = new User(1l, "Doe", "john.doe@example.com");
//		Orders order1 = new Orders(1L, "2023-02-28", 100, 10, 1l);
//		Orders order2 = new Orders(2L, user, 2023 - 02 - 28, 100, 10, 1l);
//		List<Orders> ordersList = new ArrayList<>();
//		ordersList.add(order1);
//		ordersList.add(order2);
//
//		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//		Mockito.when(ordersRepository.findByUser_UserIdAndOrderDateBetween(userId, LocalDate.now().minusDays(7),
//				LocalDate.now())).thenReturn(ordersList);
//
//		List<Orders> actual = userServiceImpl.getOrderHistory(userId, timeframe);
//
//		assertEquals(2, actual.size());
//		assertTrue(actual.contains(order1));
//		assertTrue(actual.contains(order2));
//	}

}
