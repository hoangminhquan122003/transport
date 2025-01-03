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

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class TransactionExcel {
    TransactionRepository transactionRepository;
    CargoRepository cargoRepository;
    CustomerRepository customerRepository;
    WarehouseRepository warehouseRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public void export(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("transaction");
        writeHeaderLine(workbook,sheet);
        writeData(workbook,sheet);
        workbook.write(response.getOutputStream()); // Xuất dữ liệu ra excel
        workbook.close();
    }

    private void writeHeaderLine(XSSFWorkbook workbook,XSSFSheet sheet){
        Row row=sheet.createRow(0);
        CellStyle cellStyle=workbook.createCellStyle();
        Font font=workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        font.setColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFont(font);
        String[] header={"Id","Tên hàng hóa","KL(Tấn)","Ghi chú","Hóa đơn","Khách hàng","Ngày tạo"};
        for(int i=0;i<header.length;i++){
            Cell cell=row.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(cellStyle);
        }
        for (int i = 0; i < header.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void writeData(XSSFWorkbook workbook,XSSFSheet sheet){
        int rowCount=1;
        var list=transactionRepository.findAll();
        CellStyle dateCellStyle= workbook.createCellStyle();
        dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("yyyy/MM/dd"));
        for(Transaction transaction: list){
            Row row=sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(transaction.getTransactionId());
            row.createCell(1).setCellValue(transaction.getCargo().getCargoName());
            row.createCell(2).setCellValue(transaction.getMass());
            row.createCell(3).setCellValue(transaction.getDescription());
            row.createCell(4).setCellValue(transaction.getImageName());
            row.createCell(5).setCellValue(transaction.getCustomer().getCustomerName());

            Cell dateCell=row.createCell(6);
            if(transaction.getTransactionDate()!=null) {
                dateCell.setCellValue(transaction.getTransactionDate());
                dateCell.setCellStyle(dateCellStyle);
            }
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void importFile(MultipartFile file) throws IOException {
        XSSFWorkbook workbook =new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet=workbook.getSheetAt(0);
        for(int i=1; i<= sheet.getLastRowNum();i++){
            Row row=sheet.getRow(i);
            if(row!=null){
                try {
                    Transaction transaction = new Transaction();
                    transaction.setCargo(cargoRepository.findByCargoName(row.getCell(1).getStringCellValue())
                            .orElseThrow(() -> new AppException(ErrorCode.CARGO_NOT_EXISTED)));
                    transaction.setMass(row.getCell(2).getNumericCellValue());
                    transaction.setDescription(row.getCell(3)!=null?row.getCell(3).getStringCellValue():"");
                    transaction.setImageName(row.getCell(4).getStringCellValue());
                    transaction.setCustomer(customerRepository.findByCustomerName(row.getCell(5).getStringCellValue())
                            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
                    transaction.setTransactionDate(row.getCell(6).getLocalDateTimeCellValue().toLocalDate());
                    transaction.setTransactionType(row.getCell(7).getNumericCellValue()==0? TransactionType.IMPORT : TransactionType.EXPORT);
                    transaction.setOrigin(row.getCell(8)!=null ? row.getCell(8).getStringCellValue(): null );
                    transaction.setDestination(row.getCell(9)!=null ?row.getCell(9).getStringCellValue(): null);
                    transaction.setWarehouse(warehouseRepository.findByWarehouseName(row.getCell(10).getStringCellValue())
                            .orElseThrow(()-> new AppException(ErrorCode.WAREHOUSE_NOT_EXISTED)));
                    transactionRepository.save(transaction);
                }catch(AppException e){
                    log.warn("Error processing row {}",i+1);
                }
            }
        }

    }

}
