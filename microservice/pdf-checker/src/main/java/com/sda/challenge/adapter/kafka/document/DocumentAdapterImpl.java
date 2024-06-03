package com.sda.challenge.adapter.kafka.document;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
public class DocumentAdapterImpl implements DocumentAdapter{
    @Override
    public File getDocument(String documentUrl, String downloadFilePath) throws IOException {
        log.info("DocumentAdapter - getDocument");
        URL url = new URL(documentUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();
        File outputFile = new File(downloadFilePath);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        log.debug("Document URL: " + documentUrl);
        StreamUtils.copy(inputStream, outputStream);

        inputStream.close();
        outputStream.close();

        log.info("The Document Has Been Loaded.");
        return outputFile;
    }
}
