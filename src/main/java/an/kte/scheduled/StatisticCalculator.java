package an.kte.scheduled;

import an.kte.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

@Component
public class StatisticCalculator extends TimerTask {
    @Autowired
    private StatisticService statisticService;

    @Override
    public void run() {
        statisticService.generateStatistic();
    }
}
