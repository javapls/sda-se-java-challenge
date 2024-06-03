package com.sda.challenge.adapter.kafka.document;

import java.io.File;
import java.io.IOException;

public interface DocumentAdapter {

    File getDocument(String documentUrl, String downloadFilePath) throws IOException;

}
