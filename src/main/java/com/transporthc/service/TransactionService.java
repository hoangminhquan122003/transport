package com.transporthc.service;

import com.transporthc.dto.request.ExportTransactionRequest;
import com.transporthc.dto.request.ImportTransactionRequest;
import com.transporthc.dto.response.TransactionResponse;
import com.transporthc.entity.Transaction;
import com.transporthc.exception.AppException;
import com.transporthc.repository.CargoRepository;
import com.transporthc.repository.CustomerRepository;
import com.transporthc.repository.TransactionRepository;
import com.transporthc.repository.WarehouseRepository;
import com.transporthc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    TransactionRepository transactionRepository;
    CargoRepository cargoRepository;
    CustomerRepository customerRepository;
    WarehouseRepository warehouseRepository;
    ConcurrentHashMap<Integer, Object> cargoLock=new ConcurrentHashMap<>();

    @PreAuthorize("hasRole('ADMIN')")
    public TransactionResponse createImportTransaction(ImportTransactionRequest request, MultipartFile image) throws IOException {
        var cargo= cargoRepository.findByCargoName(request.getCargoName())
                .orElseThrow(()-> new AppException(ErrorCode.CARGO_NOT_EXISTED));
        var customer=customerRepository.findByCustomerName(request.getCustomerName())
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        var warehouse= warehouseRepository.findByWarehouseName(request.getWarehouseName())
                .orElseThrow(()->new AppException(ErrorCode.WAREHOUSE_NOT_EXISTED));
        //lấy hoặc tạo khóa cho cargoId
        var lock=cargoLock.computeIfAbsent(cargo.getCargoId(),key ->new Object());//cái key=cargo.getCargoID nêú nó chưa có trong bản đồ
        synchronized (lock) {
            cargo.setAvailableMass(cargo.getAvailableMass() + request.getMass());
            cargoRepository.save(cargo);

            Transaction transaction = Transaction.builder()
                    .origin(request.getOrigin())
                    .cargo(cargo)
                    .customer(customer)
                    .warehouse(warehouse)
                    .transactionDate(request.getTransactionDate())
                    .transactionType(request.getTransactionType())
                    .mass(request.getMass())
                    .description(request.getDescription())
                    .imageName(image.getOriginalFilename())
                    .imageType(image.getContentType())
                    .imageData(image.getBytes())
                    .build();
            transactionRepository.save(transaction);
            return TransactionResponse.builder()
                    .transactionId(transaction.getTransactionId())
                    .transactionDate(transaction.getTransactionDate())
                    .transactionType(transaction.getTransactionType())
                    .origin(transaction.getOrigin())
                    .destination(transaction.getDestination())
                    .mass(transaction.getMass())
                    .description(transaction.getDescription())
                    .warehouseName(warehouse.getWarehouseName())
                    .customerName(customer.getCustomerName())
                    .cargoName(cargo.getCargoName())
                    .imageName(transaction.getImageName())
//                    .imageData(transaction.getImageData())
                    .build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public TransactionResponse createExportTransaction(ExportTransactionRequest request,MultipartFile image) throws IOException {
        var cargo= cargoRepository.findByCargoName(request.getCargoName())
                .orElseThrow(()-> new AppException(ErrorCode.CARGO_NOT_EXISTED));
        var customer=customerRepository.findByCustomerName(request.getCustomerName())
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        var warehouse= warehouseRepository.findByWarehouseName(request.getWarehouseName())
                .orElseThrow(()->new AppException(ErrorCode.WAREHOUSE_NOT_EXISTED));
        if (cargo.getAvailableMass() < request.getMass()) {
            throw new AppException(ErrorCode.NOT_ENOUGH_CARGO);
        }
        var lock=cargoLock.computeIfAbsent(cargo.getCargoId(),(key)->new Object());
        synchronized (lock) {
            cargo.setAvailableMass(cargo.getAvailableMass() - request.getMass());
            cargoRepository.save(cargo);
            Transaction transaction = Transaction.builder()
                    .destination(request.getDestination())
                    .cargo(cargo)
                    .customer(customer)
                    .warehouse(warehouse)
                    .transactionDate(request.getTransactionDate())
                    .transactionType(request.getTransactionType())
                    .mass(request.getMass())
                    .description(request.getDescription())
                    .imageName(image.getOriginalFilename())
                    .imageType(image.getContentType())
                    .imageData(image.getBytes())
                    .build();

            transactionRepository.save(transaction);
            return TransactionResponse.builder()
                    .transactionId(transaction.getTransactionId())
                    .transactionDate(transaction.getTransactionDate())
                    .transactionType(transaction.getTransactionType())
                    .origin(transaction.getOrigin())
                    .destination(transaction.getDestination())
                    .mass(transaction.getMass())
                    .description(transaction.getDescription())
                    .warehouseName(warehouse.getWarehouseName())
                    .customerName(customer.getCustomerName())
                    .cargoName(cargo.getCargoName())
                    .imageName(transaction.getImageName())
//                    .imageData(transaction.getImageData())
                    .build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<TransactionResponse> getAllTransaction(){
        var listTransaction=transactionRepository.findAll();
        return listTransaction.stream().map(transaction -> TransactionResponse.builder()
                        .transactionId(transaction.getTransactionId())
                        .transactionDate(transaction.getTransactionDate())
                        .transactionType(transaction.getTransactionType())
                        .origin(transaction.getOrigin())
                        .destination(transaction.getDestination())
                        .mass(transaction.getMass())
                        .description(transaction.getDescription())
                        .warehouseName(transaction.getWarehouse().getWarehouseName())
                        .customerName(transaction.getCustomer().getCustomerName())
                        .cargoName(transaction.getCargo().getCargoName())
                        .imageName(transaction.getImageName())
//                        .imageData(transaction.getImageData())
                        .imageType(transaction.getImageType())
                        .build()
                ).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public TransactionResponse getTransactionById(Integer transactionId){
        var transaction=transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(()->new AppException(ErrorCode.TRANSACTION_NOT_EXISTED));
        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .transactionDate(transaction.getTransactionDate())
                .transactionType(transaction.getTransactionType())
                .origin(transaction.getOrigin())
                .destination(transaction.getDestination())
                .mass(transaction.getMass())
                .description(transaction.getDescription())
                .warehouseName(transaction.getWarehouse().getWarehouseName())
                .customerName(transaction.getCustomer().getCustomerName())
                .cargoName(transaction.getCargo().getCargoName())
                .imageName(transaction.getImageName())
                .imageData(transaction.getImageData())
                .imageType(transaction.getImageType())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteTransaction(Integer transactionId){
        transactionRepository.deleteByTransactionId(transactionId);
        log.info("delete transaction id:{}",transactionId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<TransactionResponse> getAllTransactionWithPageable(int pageNo, int pageSize){
        Pageable pageable=PageRequest.of(pageNo,pageSize);
        Page<Transaction> transactions=transactionRepository.findAll(pageable);
        return transactions.stream().map(transaction -> TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .cargoName(transaction.getCargo().getCargoName())
                .mass(transaction.getMass())
                .description(transaction.getDescription())
                .imageName(transaction.getImageName())
                .customerName(transaction.getCustomer().getCustomerName())
                .transactionDate(transaction.getTransactionDate())
                .build()).toList();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<TransactionResponse> searchTransactionDate(LocalDate startDate, LocalDate endDate){
        var list=transactionRepository.searchTransactionDate(startDate,endDate);
        return list.stream().map(transaction -> TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .cargoName(transaction.getCargo().getCargoName())
                .mass(transaction.getMass())
                .description(transaction.getDescription())
                .imageName(transaction.getImageName())
                .customerName(transaction.getCustomer().getCustomerName())
                .transactionDate(transaction.getTransactionDate())
                .build()).toList();
    }


}
