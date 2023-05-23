package edu.miu.eafinalproject.external;

import edu.miu.eafinalproject.product.data.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "${api-client.name}", url = "${api-client.url}", configuration = CountryServiceFeignConfig.class)
@FeignClient(name = "${api-client.customer.name}", url = "${api-client.customer.url}")
public interface CustomerServiceFeignClient {
	
	@GetMapping(value = "/customers/{customerId}")
    CustomerResponse findByCustomerId(@PathVariable("customerId") long customerId);
}
