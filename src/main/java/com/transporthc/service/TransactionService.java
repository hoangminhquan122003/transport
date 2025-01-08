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

public interface TransactionService {

    TransactionResponse createImportTransaction(ImportTransactionRequest request, MultipartFile image) throws IOException;

    TransactionResponse createExportTransaction(ExportTransactionRequest request,MultipartFile image) throws IOException;

    List<TransactionResponse> getAllTransaction();

    TransactionResponse getTransactionById(Integer transactionId);

    void deleteTransaction(Integer transactionId);

    List<TransactionResponse> getAllTransactionWithPageable(int pageNo, int pageSize);

    List<TransactionResponse> searchTransactionDate(LocalDate startDate, LocalDate endDate);
}
