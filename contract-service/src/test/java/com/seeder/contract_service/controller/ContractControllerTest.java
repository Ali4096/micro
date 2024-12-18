package com.seeder.contract_service.controller;

import com.seeder.contract_service.controllers.ContractController;
import com.seeder.contract_service.dtos.ContractDTO;
import com.seeder.contract_service.dtos.CreateContractDTO;
import com.seeder.contract_service.exceptions.ContractNotFoundException;
import com.seeder.contract_service.services.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractControllerTest {

    @Mock
    private ContractService contractService;

    @InjectMocks
    private ContractController contractController;

    @BeforeEach
    void setUp() {
        // Initializes the mocks
        MockitoAnnotations.openMocks(this);
    }

    private CreateContractDTO createMockCreateContractDTO() {
        CreateContractDTO createContractDTO = new CreateContractDTO();
        createContractDTO.setContractName("Sample Contract");
        createContractDTO.setContractStatus("ACTIVE");
        createContractDTO.setStartDate("2024-01-01");
        createContractDTO.setEndDate("2025-01-01");
        createContractDTO.setPaymentFrequency("Monthly");
        createContractDTO.setContractAmount(5000.0);
        createContractDTO.setPaidAmount(2000.0);
        createContractDTO.setOutstandingAmount(3000.0);
        createContractDTO.setStartAmount(5000.0);
        createContractDTO.setFee(100);
        return createContractDTO;
    }

    private ContractDTO createMockContractDTO(Long contractId) {
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setId(contractId);
        return contractDTO;
    }

    @Test
    void createContract_ShouldReturnCreatedContract() {
        // Arrange
        CreateContractDTO createContractDTO = createMockCreateContractDTO();
        CreateContractDTO createdContractDTO = createMockCreateContractDTO();

        when(contractService.createContract(createContractDTO)).thenReturn(createdContractDTO);

        // Act
        ResponseEntity<CreateContractDTO> response = contractController.createContract(createContractDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Sample Contract", response.getBody().getContractName());
        verify(contractService, times(1)).createContract(createContractDTO);
    }

    @Test
    void validateContracts_ShouldReturnContractList() {
        // Arrange
        List<Long> contractIds = Arrays.asList(1L, 2L, 3L);
        ContractDTO contractDTO1 = createMockContractDTO(1L);
        ContractDTO contractDTO2 = createMockContractDTO(2L);

        when(contractService.validateAndReturnContracts(contractIds))
                .thenReturn(Arrays.asList(contractDTO1, contractDTO2));

        // Act
        ResponseEntity<List<ContractDTO>> response = contractController.validateContracts(contractIds);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
//        assertEquals("Contract details for ID 1", response.getBody().get(0).getSomeField());
//        assertEquals("Contract details for ID 2", response.getBody().get(1).getSomeField());
        verify(contractService, times(1)).validateAndReturnContracts(contractIds);
    }

    @Test
    void makeAllContractsPending_ShouldReturnSuccessMessage() {
        // Arrange
        List<Long> contractIds = Arrays.asList(1L, 2L, 3L);

        // Act
        ResponseEntity<String> response = contractController.makeAllContractsPending(contractIds);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("All contracts have been successfully marked as pending.", response.getBody());
        verify(contractService, times(1)).makeAllContractsPending(contractIds);
    }

    @Test
    void getContractById_ShouldReturnContract() {
        // Arrange
        Long contractId = 1L;
        ContractDTO contractDTO = createMockContractDTO(contractId);

        when(contractService.fetchContractById(contractId)).thenReturn(contractDTO);

        // Act
        ResponseEntity<ContractDTO> response = contractController.getContractById(contractId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Contract details for ID 1", response.getBody().getSomeField());
        verify(contractService, times(1)).fetchContractById(contractId);
    }

    @Test
    void getContractById_ShouldReturnNotFound_WhenContractDoesNotExist() {
        // Arrange
        Long contractId = 1L;
        when(contractService.fetchContractById(contractId)).thenThrow(new ContractNotFoundException("Contract not found"));

        // Act
        ResponseEntity<ContractDTO> response = contractController.getContractById(contractId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(contractService, times(1)).fetchContractById(contractId);
    }
}
