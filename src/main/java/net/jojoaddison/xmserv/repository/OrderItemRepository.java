package net.jojoaddison.xmserv.repository;

import net.jojoaddison.xmserv.domain.OrderItem;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the OrderItem entity.
 */
@SuppressWarnings("unused")
public interface OrderItemRepository extends MongoRepository<OrderItem,String> {

}
