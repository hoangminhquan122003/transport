package com.transporthc.controller;

import com.transporthc.dto.request.ExportTransactionRequest;
import com.transporthc.dto.request.ImportTransactionRequest;
import com.transporthc.dto.response.ApiResponse;
import com.transporthc.dto.response.TransactionResponse;
import com.transporthc.service.TransactionExcel;
import com.transporthc.service.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {
    TransactionService transactionService;
    TransactionExcel transactionExcel;

    @PostMapping(value="/import",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<TransactionResponse> createImportTransaction(@RequestPart("request") @Valid ImportTransactionRequest request,
                                                                    @RequestPart("image") MultipartFile image) throws IOException {
        log.info("Request: {}", request);
        log.info("Image Name: {}", image.getOriginalFilename());
        log.info("Image Content Type: {}", image.getContentType());
        return ApiResponse.<TransactionResponse>builder()
                .message("import transaction")
                .result(transactionService.createImportTransaction(request,image))
                .build();
    }

    @PostMapping(value="/export",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<TransactionResponse> createExportTransaction(@RequestPart("request") @Valid ExportTransactionRequest request,
                                                                    @RequestPart("image") MultipartFile image) throws IOException {
        return ApiResponse.<TransactionResponse>builder()
                .message("export transaction")
                .result(transactionService.createExportTransaction(request,image))
                .build();
    }
    @GetMapping
    public ApiResponse<List<TransactionResponse>> getAllTransaction(){
        return ApiResponse.<List<TransactionResponse>>builder()
                .message("get all transaction")
                .result(transactionService.getAllTransaction())
                .build();
    }

    @DeleteMapping("/{transactionId}")
    public ApiResponse<Void> deleteTransactionById(@PathVariable Integer transactionId){
        transactionService.deleteTransaction(transactionId);
        return ApiResponse.<Void>builder()
                .message("delete transaction id: "+transactionId+ " successful")
                .build();
    }

    @GetMapping("/{transactionId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable("transactionId") Integer transactionId){
        var transaction=transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(transaction.getImageType()))
                .body(transaction.getImageData());
    }
    @PostMapping("/pageable")
    public ApiResponse<List<TransactionResponse>> pageable(@Min(value = 0  ,message = "PAGE_NO_INVALID") @RequestParam int pageNo,
                                                           @Min(value = 10 ,message = "PAGE_SIZE_INVALID") @RequestParam int pageSize){
        return ApiResponse.<List<TransactionResponse>>builder()
                .message("get user by pageable")
                .result(transactionService.getAllTransactionWithPageable(pageNo,pageSize))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<TransactionResponse>> searchTransactions(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TransactionResponse> transactions = transactionService.searchTransactionDate(startDate, endDate);
        return ApiResponse.<List<TransactionResponse>>builder()
                .message("Search results")
                .result(transactions)
                .build();
    }

    @GetMapping("/excel/export")
    public ApiResponse<Void> exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");
        transactionExcel.export(response);
        return ApiResponse.<Void>builder()
                .message("export to excel")
                .build();
    }

    @PostMapping("/excel/import")
    public ApiResponse<Void> importExcel(@RequestPart("file") MultipartFile file) throws IOException {
        transactionExcel.importFile(file);
        return ApiResponse.<Void>builder()
                .message("import successful")
                .build();
    }

}
