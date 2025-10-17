package kr.aisawan.locks_testing.outbox;

import kr.aisawan.locks_testing.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxScheduler {
    private final OutboxEventRepository outboxEventRepository;
    private final NotificationService notificationService;

    @Scheduled(fixedDelay = 10000)
    public void processOutbox() {
        Pageable pageable = PageRequest.of(0, 2);

        Page<OutboxEvent> events = outboxEventRepository.findByStatusOrderByCreatedAtAsc(OutboxStatus.NEW, pageable);

        for (OutboxEvent event : events) {
            System.out.println(event);
            try {
                notificationService.send(event);
                event.setStatus(OutboxStatus.SENT);
                event.setProcessedAt(LocalDateTime.now());
            } catch (Exception ex) {
                event.setStatus(OutboxStatus.FAILED);
                ex.printStackTrace();
            }
            outboxEventRepository.save(event);
        }

        log.info("2 Notifications sent " + events.getContent());
    }
}
