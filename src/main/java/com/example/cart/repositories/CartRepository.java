package com.example.cart.repositories;

import com.example.cart.domains.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findTop1ByUserId(Long id);

}
