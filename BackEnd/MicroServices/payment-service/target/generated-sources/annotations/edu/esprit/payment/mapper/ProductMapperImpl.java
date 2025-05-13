package edu.esprit.payment.mapper;

import edu.esprit.payment.dto.ProductRequest;
import edu.esprit.payment.dto.paypal.PaypalProductRequest;
import edu.esprit.payment.dto.paypal.PaypalProductResponse;
import edu.esprit.payment.entities.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-10T13:45:11+0200",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.14 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public PaypalProductRequest toPaypalRequest(ProductRequest req) {
        if ( req == null ) {
            return null;
        }

        PaypalProductRequest.PaypalProductRequestBuilder paypalProductRequest = PaypalProductRequest.builder();

        paypalProductRequest.image_url( req.getImageUrl() );
        paypalProductRequest.name( req.getName() );
        paypalProductRequest.description( req.getDescription() );
        paypalProductRequest.type( req.getType() );
        paypalProductRequest.category( req.getCategory() );

        return paypalProductRequest.build();
    }

    @Override
    public Product toEntity(PaypalProductResponse response) {
        if ( response == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( response.getId() );
        product.name( response.getName() );
        product.description( response.getDescription() );
        product.type( response.getType() );
        product.category( response.getCategory() );

        return product.build();
    }
}
