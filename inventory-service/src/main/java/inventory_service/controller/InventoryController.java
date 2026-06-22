package inventory_service.controller;

import common_lib.dto.response.InventoryResponse;
import inventory_service.dto.ContactInfoDto;
import inventory_service.entity.Inventory;
import inventory_service.repository.InventoryRepository;
import inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final ContactInfoDto contactInfoDto;
    @GetMapping("/{productCode}")
    public InventoryResponse getInventory(@PathVariable("productCode") String productCode) {
        return inventoryService.getInventory(productCode);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<?> contactInfo(){
        return ResponseEntity.ok(contactInfoDto);
    }
}