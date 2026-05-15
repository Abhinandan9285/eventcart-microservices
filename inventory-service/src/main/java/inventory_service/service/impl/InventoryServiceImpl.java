package inventory_service.service.impl;

import inventory_service.entity.Inventory;
import inventory_service.event.payload.InventoryFailedEvent;
import inventory_service.event.payload.InventoryReservedEvent;
import inventory_service.event.payload.OrderCreatedEvent;
import inventory_service.event.producer.InventoryEventProducer;
import inventory_service.repository.InventoryRepository;
import inventory_service.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryEventProducer producer;

    @Override
    public void reserveInventory(OrderCreatedEvent event) {

        try {

            Inventory inventory = inventoryRepository.findByProductCode(event.getProductCode()).orElseThrow(() -> new RuntimeException("Product not found"));
            if (inventory.getAvailableQuantity() < event.getQuantity()) {
                throw new RuntimeException("Insufficient inventory");
            }

            inventory.setAvailableQuantity(inventory.getAvailableQuantity() - event.getQuantity());
            inventoryRepository.save(inventory);

            producer.publishSuccessEvent(new InventoryReservedEvent(event.getOrderId(), event.getProductCode(), event.getQuantity()));

        } catch (Exception ex) {
            producer.publishFailureEvent(new InventoryFailedEvent(event.getOrderId(), ex.getMessage()));
        }
    }
}