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
        productStatisticRepository.deleteAll();
        clientStatisticRepository.deleteAll();
        try (Connection connection = dataSource.getConnection()) {
            CallableStatement statement = connection.prepareCall(
                    "SELECT sum(pos.final_price) AS price," +
                            "sum(pos.final_discount) AS discount," +
                            "c.id as cid," +
                            "count(od.id) as odc " +
                            "FROM client c " +
                            "INNER JOIN order_document od " +
                            "ON od.client_id=c.id " +
                            "INNER JOIN position pos " +
                            "ON pos.order_id=od.id " +
                            "GROUP BY c.id");
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    ClientStatistic statistic = ClientStatistic.builder()
                            .clientId(resultSet.getLong("cid"))
                            .fillDiscount(resultSet.getLong("discount"))
                            .fullPrice(resultSet.getLong("price"))
                            .orderCount(resultSet.getLong("odc"))
                            .build();
                    clientStatisticRepository.save(statistic);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = dataSource.getConnection()) {
            CallableStatement statement = connection.prepareCall(
                    "SELECT sum(pos.final_price) AS price,\n" +
                            "sum(pos.final_discount) AS discount,\n" +
                            "pos.product_id as pid,\n" +
                            "count(od.id) as odc \n" +
                            "FROM position pos \n" +
                            "INNER JOIN order_document od \n" +
                            "ON od.id=pos.order_id \n" +
                            "GROUP BY pos.product_id");
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    ProductStatistic statistic = ProductStatistic.builder()
                            .productId(resultSet.getLong("pid"))
                            .fillDiscount(resultSet.getLong("discount"))
                            .fullPrice(resultSet.getLong("price"))
                            .orderCount(resultSet.getLong("odc"))
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

    public ProductStatistic findByProductId(Long productId) {
        return productStatisticRepository.findByProductId(productId);
    }
}
