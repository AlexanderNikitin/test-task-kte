package an.kte.listener;

import an.kte.scheduled.DiscountGenerator;
import an.kte.scheduled.StatisticCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Timer;

@Component
public class GlobalListeners {
    private final long DISCOUNT_DELAY = 10000;
    private final long DISCOUNT_PERIOD = 60000;

    private final long STATISTIC_DELAY = 60000;
    private final long STATISTIC_PERIOD = 60000;

    @Autowired
    private DiscountGenerator discountGenerator;
    @Autowired
    private StatisticCalculator statisticCalculator;

    @EventListener(ApplicationReadyEvent.class)
    public void startTimerTasks() {
        new Timer().schedule(discountGenerator, DISCOUNT_DELAY, DISCOUNT_PERIOD);
        new Timer().schedule(statisticCalculator, STATISTIC_DELAY, STATISTIC_PERIOD);
    }
}
