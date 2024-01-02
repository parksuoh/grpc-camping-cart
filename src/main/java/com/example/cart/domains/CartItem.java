package com.example.cart.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_first_option_id")
    private Long productFirstOptionId;

    @Column(name = "product_second_option_id")
    private Long productSecondOptionId;

    @Column(name = "quantity")
    private Integer quantity;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated;


    public CartItem() {}

    public CartItem(Cart cart, Long productId, Long productFirstOptionId, Long productSecondOptionId, Integer quantity) {
        this.cart = cart;
        this.productId = productId;
        this.productFirstOptionId = productFirstOptionId;
        this.productSecondOptionId = productSecondOptionId;
        this.quantity = quantity;
    }
    public Long id() {return id; }
    public Cart cart() {return cart;}
    public Long productId() {return productId;}
    public Long productFirstOptionId() {return productFirstOptionId;}
    public Long productSecondOptionId() {return productSecondOptionId;}
    public Integer quantity() {return quantity;}

    public void addQuantity (Integer add) {
        this.quantity += add;
    }
    public void changeQuantity (Integer quantity) {this.quantity = quantity;}
}