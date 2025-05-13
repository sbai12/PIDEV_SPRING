package edu.esprit.payment.mapper;

import edu.esprit.payment.dto.ProductRequest;
import edu.esprit.payment.dto.paypal.PaypalProductRequest;
import edu.esprit.payment.dto.paypal.PaypalProductResponse;
import edu.esprit.payment.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "image_url", source = "imageUrl")
    PaypalProductRequest toPaypalRequest(ProductRequest req);

    Product toEntity(PaypalProductResponse response); // From PayPal to our DB entity
}
