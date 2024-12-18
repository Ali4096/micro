package com.seeder.contract_service.services;

import com.seeder.contract_service.dtos.ContractDTO;
import com.seeder.contract_service.dtos.CreateContractDTO;
import com.seeder.contract_service.entities.Contract;
import com.seeder.contract_service.exceptions.ContractNotFoundException;
import com.seeder.contract_service.repositories.ContractRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    private final ModelMapper modelMapper;
    private final ContractRepository contractRepository;

    public ContractService(ModelMapper modelMapper, ContractRepository contractRepository) {
        this.modelMapper = modelMapper;
        this.contractRepository = contractRepository;
    }

    public CreateContractDTO createContract(CreateContractDTO createContractDTO) {
        // Convert CreateContractDTO to Contract entity
        Contract contract = modelMapper.map(createContractDTO, Contract.class);

//        // Handle enum conversion (if not done in DTO)
//        contract.setStatus(Contract.Status.valueOf(createContractDTO.getContractStatusAsEnum());

        // Save the contract to the database
        Contract savedContract = contractRepository.save(contract);

        // Convert saved contract entity back to ContractDTO

        // Return the ContractDTO
        return modelMapper.map(savedContract, CreateContractDTO.class);
    }

    public ContractDTO fetchContractById(Long contractId) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isEmpty()) {
            throw new ContractNotFoundException("Contract not found with ID: " + contractId);
        }
        return modelMapper.map(contractOptional,ContractDTO.class);
    }

    public void attachContractToCashkickAndUser(List<Long> contractIds, Long cashkickId, Long userId) {
        List<Contract> contracts = contractRepository.findAllById(contractIds);

        if (contracts.size() != contractIds.size()) {
            List<Long> missingIds = contractIds.stream()
                    .filter(id -> !contracts.stream().map(Contract::getId).toList().contains(id))
                    .toList();

            throw new ContractNotFoundException("Contracts not found with IDs: " + missingIds);
        }

        contracts.forEach(contract -> {
            contract.setStatus(Contract.Status.PENDING);
            contract.setCashkickId(cashkickId.intValue()); // Convert Long to Integer
            contract.setUserId(userId);
        });
        contractRepository.saveAll(contracts);
    }


    public List<ContractDTO> validateAndReturnContracts(List<Long> contractIds) {
        List<ContractDTO> contractDTOS = new ArrayList<>();
        // Loop through each contract ID
        for (Long id : contractIds) {
            // Retrieve the contract by ID from the repository
            Optional<Contract> contractOptional = contractRepository.findById(id);

            // Check if the contract exists
            if (contractOptional.isEmpty()) {
                throw new IllegalArgumentException("Contract with ID " + id + " does not exist.");
            }

            Contract contract = contractOptional.get();

            // Add your custom validation logic here (e.g., check status, amount, etc.)
            if (contract.getStatus().equals(Contract.Status.PENDING) || contract.getStatus().equals(Contract.Status.INACTIVE)) {
                throw new IllegalArgumentException("Contract with ID " + id + " is not in a valid status.");
            }
            contractDTOS.add(modelMapper.map(contract,ContractDTO.class));
        }
        return contractDTOS;
    }

    public void makeAllContractsPending(List<Long> contractIds) {
        System.out.println("Contract " + contractIds.toString());
        // Find all contracts by their IDs
        List<Contract> contracts = contractRepository.findAllById(contractIds);

        // Check if all contracts exist
        if (contracts.isEmpty()) {
            throw new IllegalArgumentException("No contracts found for the given IDs.");
        }

        // Update the status of each contract to 'PENDING'
        for (Contract contract : contracts) {
            contract.setStatus(Contract.Status.PENDING);  // Assuming `setStatus()` is the method to update the status
        }

        // Save the updated contracts
        contractRepository.saveAll(contracts);
    }

}
