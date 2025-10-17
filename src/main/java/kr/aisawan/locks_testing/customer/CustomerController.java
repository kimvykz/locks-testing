package kr.aisawan.locks_testing.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;


    @GetMapping("/customers")
    public ResponseEntity<Page<Customer>> getCustomerList(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(
                customerService.getCustomerList(pageable)
        );
    }


}
