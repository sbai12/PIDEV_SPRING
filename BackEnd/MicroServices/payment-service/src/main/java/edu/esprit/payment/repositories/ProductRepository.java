package edu.esprit.payment.repositories;

import edu.esprit.payment.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
