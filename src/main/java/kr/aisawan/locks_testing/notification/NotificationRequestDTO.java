package kr.aisawan.locks_testing.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationRequestDTO {
    private Long customerId;
    private String state;
}
