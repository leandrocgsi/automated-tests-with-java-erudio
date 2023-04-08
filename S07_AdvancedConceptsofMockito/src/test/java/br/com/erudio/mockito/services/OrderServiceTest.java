package br.com.erudio.mockito.services;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class OrderServiceTest {
    
    OrderService service = new OrderService();
    Object defaultUuid = UUID.fromString("8d8b30e3-de52-4f1c-a71c-9905a8043dac");

    @DisplayName("Should Include Randon OrderId When No OrderId Exists")
    @Test
    void testShouldIncludeRandonOrderId_When_NoOrderIdExists() {
        // Given / Arrange
        try(MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
            mockedUuid.when(UUID::randomUUID).thenReturn(defaultUuid);

            // When / Act
            Order result = service.createOrder("MacBook Pro", 2L, null);

            // Then / Assert
            assertEquals(defaultUuid, result.getId());
        }
    }

}
