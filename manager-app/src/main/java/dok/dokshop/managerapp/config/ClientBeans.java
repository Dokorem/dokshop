package dok.dokshop.managerapp.config;

import dok.dokshop.managerapp.client.ProductsRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public ProductsRestClientImpl productsRestClient(
            @Value("${dok-shop.services.catalogue.uri:http://localhost:8080}") String catalogueBaseUri,
            @Value("${dok-shop.services.catalogue.username:}") String catalogueUsername,
            @Value("${dok-shop.services.catalogue.password:}") String cataloguePassword) {
        return new ProductsRestClientImpl(RestClient
                .builder()
                .baseUrl(catalogueBaseUri)
                .requestInterceptor(new BasicAuthenticationInterceptor(catalogueUsername, cataloguePassword))
                .build());
    }

}
