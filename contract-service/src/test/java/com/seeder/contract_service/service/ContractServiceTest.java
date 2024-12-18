package com.seeder.contract_service.service;

import com.seeder.contract_service.dtos.ContractDTO;
import com.seeder.contract_service.dtos.CreateContractDTO;
import com.seeder.contract_service.entities.Contract;
import com.seeder.contract_service.exceptions.ContractNotFoundException;
import com.seeder.contract_service.repositories.ContractRepository;
import com.seeder.contract_service.services.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractServiceTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ContractService contractService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks
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

    private Contract createMockContract(Long contractId) {
        Contract contract = new Contract();
        contract.setId(contractId);
        contract.setContractName("Contract " + contractId);
        contract.setStatus(Contract.Status.ACTIVE);
        contract.setContractAmount(5000.0);
        return contract;
    }

    @Test
    void createContract_ShouldReturnCreatedContractDTO() {
        // Arrange
        CreateContractDTO createContractDTO = createMockCreateContractDTO();
        Contract contract = createMockContract(1L);
        Contract savedContract = createMockContract(1L);

        when(modelMapper.map(createContractDTO, Contract.class)).thenReturn(contract);
        when(contractRepository.save(contract)).thenReturn(savedContract);
        when(modelMapper.map(savedContract, CreateContractDTO.class)).thenReturn(createContractDTO);

        // Act
        CreateContractDTO result = contractService.createContract(createContractDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Sample Contract", result.getContractName());
        verify(contractRepository, times(1)).save(contract);
    }

//    @Test
//    void fetchContractById_ShouldReturnContractDTO() {
//        // Arrange
//        Long contractId = 1L;
//        Contract contract = createMockContract(contractId);
//        ContractDTO contractDTO = new ContractDTO();
//        contractDTO.setId(contractId);
//        contractDTO.setContractName("Contract 1");
//
//        when(contractRepository.findById(contractId)).thenReturn(Optional.of(contract));
//        when(modelMapper.map(contract, ContractDTO.class)).thenReturn(contractDTO);
//
//        // Act
//        ContractDTO result = contractService.fetchContractById(contractId);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(contractId, result.getId());
//        assertEquals("Contract 1", result.getContractName());
//        verify(contractRepository, times(1)).findById(contractId);
//    }

    @Test
    void fetchContractById_ShouldThrowContractNotFoundException_WhenNotFound() {
        // Arrange
        Long contractId = 1L;

        when(contractRepository.findById(contractId)).thenReturn(Optional.empty());

        // Act & Assert
        ContractNotFoundException exception = assertThrows(ContractNotFoundException.class, () -> {
            contractService.fetchContractById(contractId);
        });
        assertEquals("Contract not found with ID: 1", exception.getMessage());
    }

    @Test
    void validateAndReturnContracts_ShouldReturnValidContractDTOList() {
        // Arrange
        List<Long> contractIds = Arrays.asList(1L, 2L);
        Contract contract1 = createMockContract(1L);
        Contract contract2 = createMockContract(2L);
        ContractDTO contractDTO1 = new ContractDTO();
        ContractDTO contractDTO2 = new ContractDTO();

        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract1));
        when(contractRepository.findById(2L)).thenReturn(Optional.of(contract2));
        when(modelMapper.map(contract1, ContractDTO.class)).thenReturn(contractDTO1);
        when(modelMapper.map(contract2, ContractDTO.class)).thenReturn(contractDTO2);

        // Act
        List<ContractDTO> result = contractService.validateAndReturnContracts(contractIds);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(contractRepository, times(1)).findById(1L);
        verify(contractRepository, times(1)).findById(2L);
    }

    @Test
    void validateAndReturnContracts_ShouldThrowIllegalArgumentException_WhenContractNotFound() {
        // Arrange
        List<Long> contractIds = Arrays.asList(1L, 2L);

        when(contractRepository.findById(1L)).thenReturn(Optional.empty());
        when(contractRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            contractService.validateAndReturnContracts(contractIds);
        });
        assertEquals("Contract with ID 1 does not exist.", exception.getMessage());
    }

    @Test
    void makeAllContractsPending_ShouldUpdateStatusToPending() {
        // Arrange
        List<Long> contractIds = Arrays.asList(1L, 2L);
        Contract contract1 = createMockContract(1L);
        Contract contract2 = createMockContract(2L);

        when(contractRepository.findAllById(contractIds)).thenReturn(Arrays.asList(contract1, contract2));

        // Act
        contractService.makeAllContractsPending(contractIds);

        // Assert
        assertEquals(Contract.Status.PENDING, contract1.getStatus());
        assertEquals(Contract.Status.PENDING, contract2.getStatus());
        verify(contractRepository, times(1)).findAllById(contractIds);
        verify(contractRepository, times(1)).saveAll(Arrays.asList(contract1, contract2));
    }

    @Test
    void makeAllContractsPending_ShouldThrowException_WhenContractsNotFound() {
        // Arrange
        List<Long> contractIds = Arrays.asList(1L, 2L);

        when(contractRepository.findAllById(contractIds)).thenReturn(Arrays.asList());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            contractService.makeAllContractsPending(contractIds);
        });
        assertEquals("No contracts found for the given IDs.", exception.getMessage());
    }

    @Test
    void attachContractToCashkickAndUser_ShouldAttachContractsSuccessfully() {
        // Arrange
        List<Long> contractIds = Arrays.asList(1L, 2L);
        Long cashkickId = 123L;
        Long userId = 456L;
        Contract contract1 = createMockContract(1L);
        Contract contract2 = createMockContract(2L);

        when(contractRepository.findAllById(contractIds)).thenReturn(Arrays.asList(contract1, contract2));

        // Act
        contractService.attachContractToCashkickAndUser(contractIds, cashkickId, userId);

        // Assert
        assertEquals(Integer.valueOf(cashkickId.intValue()), contract1.getCashkickId());
        assertEquals(Integer.valueOf(cashkickId.intValue()), contract2.getCashkickId());
        assertEquals(userId, contract1.getUserId());
        assertEquals(userId, contract2.getUserId());
        assertEquals(Contract.Status.PENDING, contract1.getStatus());
        assertEquals(Contract.Status.PENDING, contract2.getStatus());
        verify(contractRepository, times(1)).saveAll(Arrays.asList(contract1, contract2));
    }
}
