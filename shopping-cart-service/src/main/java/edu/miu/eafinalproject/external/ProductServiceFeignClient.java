package edu.miu.eafinalproject.external;

import edu.miu.eafinalproject.product.data.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "${api-client.name}", url = "${api-client.url}", configuration = CountryServiceFeignConfig.class)
@FeignClient(name = "${api-client.product.name}", url = "${api-client.product.url}")
public interface ProductServiceFeignClient {
	
	@GetMapping(value = "/products/{productNumber}", consumes = "application/json", produces = "application/json")
    ProductResponse findByProductNumber(@PathVariable("productNumber") Long productNumber);
}
