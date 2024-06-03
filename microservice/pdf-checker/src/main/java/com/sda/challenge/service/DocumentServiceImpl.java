package com.sda.challenge.service;

import com.sda.challenge.adapter.kafka.document.DocumentAdapter;
import com.sda.challenge.domain.enums.StateEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.rmi.server.LogStream.log;

@Slf4j
@AllArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentAdapter documentAdapter;

    @Override
    public boolean isInvalidDocument(File document) {
        log.info("Validating Magic Byte.");
        byte[] pdfMagicByte = {0x25, 0x50, 0x44, 0x46, 0x2D};

        byte[] fileByte = new byte[pdfMagicByte.length];

        try {
            FileInputStream fileStream = new FileInputStream(document);

            if (fileStream.read(fileByte) != pdfMagicByte.length) {
                return true;
            }

        } catch (IOException e) {
            log.error("DocumentService - isInvalidDocument - Some Error Occurred.");
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return !Arrays.equals(pdfMagicByte,fileByte);
    }

    @Override
    public StateEnum allIbansIsValid(File file) {
        try {
            log.info("Validating IBAN's");
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String pdfText = pdfTextStripper.getText(document);
            document.close();

            List<String> ibansExtractList = extractIbansFromFile(pdfText);

            if(ibansExtractList.isEmpty()) {
                log.info("The Document Doesn't have IBANs");
                return StateEnum.IGNORED;
            }

            boolean containsBlockedsIbans = ibansExtractList.stream().anyMatch(blockedIbans()::contains);

            if(containsBlockedsIbans) {
                log.info("The Document has Blockeds IBANs");
                return StateEnum.SUSPICIOUS;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("The Document it's OK.");
        return StateEnum.OK;
    }

    @Override
    public File getDocument(String documentUrl,String downloadFilePath) throws IOException {
        return documentAdapter.getDocument(documentUrl,downloadFilePath);
    }

    private List<String> extractIbansFromFile(String pdfText) {
        log.info("Extracting Germany IBANs.");
        String ibanRegex = "[A-Z]{2}\\d{2}\\s?(\\d{4}\\s?){4}\\d{2}";

        Pattern pattern = Pattern.compile(ibanRegex);
        Matcher matcher = pattern.matcher(pdfText);


        return matcher.results()
                .map(MatchResult::group)
                .map(result -> result.replaceAll(" ",""))
                .collect(Collectors.toList());
    }

    private List<String> blockedIbans() {
        ArrayList<String> blockedIbansList = new ArrayList<>();
        blockedIbansList.add("DE03500105179918751437");
        blockedIbansList.add("DE41500105174513489191");
        blockedIbansList.add("DE15300606010505780780");
        blockedIbansList.add("ABC1530060601050578078");

        return blockedIbansList;
    }
}
