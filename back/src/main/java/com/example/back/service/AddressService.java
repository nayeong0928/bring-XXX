package com.example.back.service;

import com.example.back.entity.Address;
import com.example.back.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public void parseExcel() throws IOException {
        ClassPathResource resource=new ClassPathResource("data/addr_data.xlsx");
        Path filePath= Paths.get(resource.getURI());

        FileInputStream file=new FileInputStream(filePath.toFile());
        ZipSecureFile.setMinInflateRatio(0);
        XSSFWorkbook workbook=new XSSFWorkbook(file);
        XSSFSheet sheet=workbook.getSheetAt(0);

        for(int i=sheet.getFirstRowNum()+1;i<sheet.getLastRowNum(); i++){
            log.info("index: {}", i);
            XSSFRow row=sheet.getRow(i);
            String code = readRow(row, 1);
            String step1=readRow(row, 2);
            String step2=readRow(row, 3);
            String step3=readRow(row, 4);
            String nx=readRow(row, 5);
            String ny=readRow(row, 6);
            addressRepository.save(new Address(code, step1, step2, step3, nx, ny));
        }

    }

    private String readRow(XSSFRow row, int idx){
        String result=null;

        if(row.getCell(idx)==null) return null;

        if (row.getCell(idx).getCellType() == CellType.NUMERIC) {
            result = String.valueOf(row.getCell(idx).getNumericCellValue());
        } else {
            result = row.getCell(idx).getStringCellValue();
        }

        return result;
    }

    public List<Address> getAddressAll(){
        return addressRepository.addressAll();
    }
}
