package com.mindhub.skywalkies.dtos;

import com.mindhub.skywalkies.models.Cart;
import com.mindhub.skywalkies.models.Client;
import com.mindhub.skywalkies.models.Shoe;
import java.time.LocalDate;
import java.util.Set;

public class CartDTO {
  private long id;
  private Set<Shoe> shoes;
  private Client client;
  private double totalAmount;
  private LocalDate localDate;

  public CartDTO(){}

  public CartDTO(Cart cart){
    this.id = cart.getId();
    this.shoes = cart.getShoes();
    this.client = cart.getClient();
    this.totalAmount = cart.getTotalAmount();
    this.localDate = cart.getLocalDate();
  }

  public long getId() {
    return id;
  }

  public Set<Shoe> getShoes() {
    return shoes;
  }

  public Client getClient() {
    return client;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public LocalDate getLocalDate() {
    return localDate;
  }
}