package com.sda.challenge.facade.challengeb.adapter.kafka.api;

import com.sda.challenge.facade.challengeb.adapter.kafka.ApiProperties;
import com.sda.challenge.facade.challengeb.domain.model.enums.StateEnum;
import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckDocumentRequest;
import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckResultEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(path = ApiProperties.ADDRESS, produces = MediaType.APPLICATION_JSON_VALUE)
public interface FacadeApi {

    /**
     * POST /check-document
     * @param checkDocumentRequest (required)
     * @return Success (status code 200)
     *         or Bad Request (status code 400)
     *         or Some Error Occured (status code 500)
     */
    @Operation(summary = "Validate a PDF File", description = "Checks if a PDF file is suspected of containing scams.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document File Validated With Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Some Error Occured")
    })
    ResponseEntity<StateEnum> checkDocument(@RequestBody CheckDocumentRequest checkDocumentRequest);
}
