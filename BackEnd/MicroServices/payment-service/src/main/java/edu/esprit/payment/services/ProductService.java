package edu.esprit.payment.services;

import edu.esprit.payment.dto.ProductRequest;
import edu.esprit.payment.dto.paypal.PaypalProductRequest;
import edu.esprit.payment.dto.paypal.PaypalProductResponse;
import edu.esprit.payment.entities.Product;
import edu.esprit.payment.mapper.ProductMapper;
import edu.esprit.payment.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final PaypalService paypalService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Product create(ProductRequest request) {
        // Map to PayPal request
        PaypalProductRequest paypalRequest = productMapper.toPaypalRequest(request);

        // Create product in PayPal
        PaypalProductResponse paypalResponse = paypalService.createProduct(paypalRequest);

        // Map PayPal response to local DB entity
        Product product = Product.builder()
                .id(paypalResponse.getId())
                .name(paypalResponse.getName())
                .description(paypalResponse.getDescription())
                .type(paypalResponse.getType())
                .category(paypalResponse.getCategory())
                .imageUrl(request.getImageUrl())
                .createdAt(LocalDateTime.now())
                .build();

        return productRepository.save(product);
    }
}

