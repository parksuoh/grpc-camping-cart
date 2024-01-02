package com.example.cart.repositories;

import com.example.cart.domains.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findById(Long id);

//    List<CartItem> findByProductSecondOption_Id(Long productSecondOptionId);
//    List<CartItem> findByProductFirstOption_Id(Long productFirstOptionId);
//    List<CartItem> findByProduct_Id(Long productId);

    CartItem findByCartIdAndProductIdAndProductFirstOptionIdAndProductSecondOptionId(
            Long cartId,
            Long productId,
            Long productFirstOptionId,
            Long productSecondOptionId
    );

    List<CartItem> findByCart_Id(Long cartId);

}
