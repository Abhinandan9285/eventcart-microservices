package inventory_service.service.impl;

import common_lib.dto.response.InventoryResponse;
import common_lib.event.InventoryFailedEvent;
import common_lib.event.InventoryReservedEvent;
import common_lib.event.InventoryRollbackEvent;
import common_lib.event.OrderCreatedEvent;
import common_lib.exception.BadRequestException;
import inventory_service.entity.Inventory;
import inventory_service.event.producer.InventoryReserveProducer;
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
    private final InventoryReserveProducer producer;

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

    @Override
    public void restoreInventory(InventoryRollbackEvent event) {
        Inventory inventory = inventoryRepository.findByProductCode(event.getProductCode()).orElseThrow(() -> new RuntimeException("Product not found"));

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + event.getQuantity());
        inventoryRepository.save(inventory);

        log.info("Inventory restored for product event: {}", event);
    }

    @Override
    public InventoryResponse getInventory(String productCode) {
        Inventory inventory = inventoryRepository
                .findByProductCode(productCode)
                .orElseThrow(() -> new BadRequestException("No Inventory Found With product: "+ productCode));

        return new InventoryResponse(
                inventory.getProductCode(),
                inventory.getAvailableQuantity()
        );
    }
}