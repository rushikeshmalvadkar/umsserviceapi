package com.rm.ums.url.event;

import com.rm.ums.url.model.eventRequst.UrlVisitedEventRequest;
import com.rm.ums.url.services.UrlViewCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UrlVisitedEventListener {

    private final UrlViewCounter urlViewCounter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(UrlVisitedEventRequest url) {
        urlViewCounter.incrementViewCount(url.userId());
    }
}
