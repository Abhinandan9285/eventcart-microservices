package inventory_service.service.impl;

import inventory_service.entity.Inventory;
import inventory_service.event.payload.OrderCreatedEvent;
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

    @Override
    public void reserveInventory(OrderCreatedEvent event) {

        Inventory inventory = inventoryRepository.findByProductCode(event.getProductCode()).orElseThrow(() -> new RuntimeException("Product not found"));

        if (inventory.getAvailableQuantity() < event.getQuantity()) {
            throw new RuntimeException("Insufficient inventory");
        }

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - event.getQuantity());

        inventoryRepository.save(inventory);

        log.info("Inventory reserved for product: {}", event.getProductCode());
    }
}