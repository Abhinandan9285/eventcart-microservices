package inventory_service.controller;

import inventory_service.dto.InventoryResponse;
import inventory_service.entity.Inventory;
import inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    @GetMapping("/{productCode}")
    public InventoryResponse getInventory(@PathVariable("productCode") String productCode) {

        Inventory inventory = inventoryRepository
                        .findByProductCode(productCode)
                        .orElse(new Inventory());

        return new InventoryResponse(
                inventory.getProductCode(),
                inventory.getAvailableQuantity()
        );
    }
}