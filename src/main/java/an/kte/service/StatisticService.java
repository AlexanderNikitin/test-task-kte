package an.kte.service;

import an.kte.repository.ClientStatisticRepository;
import an.kte.repository.ProductStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Service
public class StatisticService {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProductStatisticRepository productStatisticRepository;
    @Autowired
    private ClientStatisticRepository clientStatisticRepository;

    @Transactional
    public void generateStatistic() {
        //TODO
    }
}
