package com.transporthc.service;

import com.transporthc.entity.Cargo;
import com.transporthc.entity.Transaction;
import com.transporthc.exception.AppException;
import com.transporthc.repository.CargoRepository;
import com.transporthc.repository.CustomerRepository;
import com.transporthc.repository.TransactionRepository;
import com.transporthc.repository.WarehouseRepository;
import com.transporthc.utils.ErrorCode;
import com.transporthc.utils.TransactionType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TransactionExcel {

    void export(HttpServletResponse response) throws IOException ;

    void importFile(MultipartFile file) throws IOException ;
}
