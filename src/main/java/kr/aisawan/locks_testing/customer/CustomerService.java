package kr.aisawan.locks_testing.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.aisawan.locks_testing.notification.NotificationRequestDTO;
import kr.aisawan.locks_testing.outbox.OutboxEvent;
import kr.aisawan.locks_testing.outbox.OutboxStatus;
import kr.aisawan.locks_testing.outbox.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;


    @Transactional
    public Page<Customer> getCustomerList(Pageable pageable) {
        pageable = PageRequest.of(0, 5);

        var customers = customerRepository.findAll(pageable);

        for (Customer customer : customers) {
            processSingleCustomer(customer);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return customers;
    }


    private void processSingleCustomer(Customer customer) {
        customerRepository.save(customer);
        NotificationRequestDTO notificationRequestDTO =
                new NotificationRequestDTO(customer.getId(), "success");

        try {
            String payload = objectMapper.writeValueAsString(notificationRequestDTO);
            OutboxEvent event = OutboxEvent.builder()
                    .eventType("CUSTOMER_NOTIFICATION")
                    .payload(payload)
                    .status(OutboxStatus.NEW)
                    .createdAt(LocalDateTime.now())
                    .build();

            outboxEventRepository.save(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }



}
