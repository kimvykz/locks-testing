package kr.aisawan.locks_testing.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.aisawan.locks_testing.outbox.OutboxEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final ObjectMapper objectMapper;

    public void send(OutboxEvent event) {


        //very slow process, can fail
        try {
            NotificationRequestDTO dto = objectMapper.readValue(event.getPayload(), NotificationRequestDTO.class);

            Thread.sleep(5000);

            System.out.println("Sending notification for customerId=" + dto.getCustomerId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
