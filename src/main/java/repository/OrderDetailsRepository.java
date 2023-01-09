package repository;

import entity.OrderDetailsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends CrudRepository<OrderDetailsEntity,Integer> {

    @Query(value = "select * from orderdetails where orderId = ?1", nativeQuery = true)
    List<OrderDetailsEntity> getOrderDetailByOrderId(int id);
}
