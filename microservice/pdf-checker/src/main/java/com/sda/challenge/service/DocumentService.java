package com.sda.challenge.service;

import com.sda.challenge.domain.enums.StateEnum;

import java.io.File;
import java.io.IOException;
import java.net.ProtocolException;

public interface DocumentService {
    boolean isInvalidDocument(File document);

    StateEnum allIbansIsValid(File document);

    File getDocument(String documentUrl, String correlatioId) throws IOException;
}
