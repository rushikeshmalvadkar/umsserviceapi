package com.rm.ums.url.listners;

import com.rm.ums.url.counter.UrlViewCounter;
import com.rm.ums.url.model.events.UrlVisitedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class UrlVisitedEventListener {

    private final UrlViewCounter urlViewCounter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(UrlVisitedEvent urlVisitedEvent) {
        log.debug("incrementViewCount process started for urlId : {} ", urlVisitedEvent.urlId());
        try {
            urlViewCounter.incrementViewCount(urlVisitedEvent.urlId());
            log.debug("incrementViewCount process ended for urlId : {} ", urlVisitedEvent.urlId());
        } catch (Exception e) {
            log.error("incrementViewCount process failed", e);
        }
    }

}
