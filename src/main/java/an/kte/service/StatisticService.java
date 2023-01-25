package an.kte.service;

import an.kte.model.ClientStatistic;
import an.kte.model.ProductStatistic;
import an.kte.repository.ClientStatisticRepository;
import an.kte.repository.ProductStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        try (Connection connection = dataSource.getConnection()) {
            CallableStatement statement = connection.prepareCall(
                    "SELECT sum(pos.final_price) AS price," +
                            "sum(pos.final_discount) AS discount, p.id as pid " +
                            "FROM product p " +
                            "INNER JOIN position pos " +
                            "ON pos.product_id=p.id " +
                            "GROUP BY p.id");
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    ProductStatistic statistic = ProductStatistic.builder()
                            .productId(resultSet.getLong("pid"))
                            .fillDiscount(resultSet.getLong("discount"))
                            .fullPrice(resultSet.getLong("price"))
                            .build();
                    productStatisticRepository.save(statistic);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ClientStatistic findByClientId(Long clientId) {
        return clientStatisticRepository.findByClientId(clientId);
    }

    public ProductStatistic findByProductId(Long clientId) {
        return productStatisticRepository.findByProductId(clientId);
    }
}
