package com.melo.eapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melo.eapp.Enum.OrderStatus;
import com.melo.eapp.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

  Order findByUserIdAndOrderStatus(Long userId,OrderStatus orderStatus);

}
