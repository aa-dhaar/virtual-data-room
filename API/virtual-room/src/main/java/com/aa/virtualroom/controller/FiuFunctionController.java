package com.aa.virtualroom.controller;

import com.aa.virtualroom.exception.RecordNotFoundException;
import com.aa.virtualroom.model.FunctionDetails;
import com.aa.virtualroom.response.ErrorResponse;
import com.aa.virtualroom.response.FunctionIdResponse;
import com.aa.virtualroom.service.FiuFunctionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vdr")
public class FiuFunctionController {

    private static Logger LOGGER = LogManager.getLogger(FiuFunctionController.class);

    @Autowired
    private FiuFunctionService fiuFunctionService;

    @PostMapping("/createFunction")
    public ResponseEntity<?> createFunction(@RequestParam("function") MultipartFile file,
                                            @RequestParam("fiuId") String fiuId,
                                            @RequestParam("jsonSchema") String jsonSchema) {
        FunctionDetails functionDetails = new FunctionDetails();
        functionDetails.setFiuId(fiuId);
        functionDetails.setJsonSchema(jsonSchema);
        //TODO: validate JsonSchema
        FunctionIdResponse functionIdResponse =
            new FunctionIdResponse(fiuFunctionService.createFunction(file, functionDetails));
		    /*
		    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(fileName)
				.toUriString();
			*/
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(functionIdResponse);
    }

    @GetMapping("/getFunctionDetails")
    public ResponseEntity<?> getFunctionDetails(@RequestParam("functionId") String functionId) {
        try {
            FunctionDetails functionDetails = fiuFunctionService.getFunctionDetails(functionId);
            return ResponseEntity.status(HttpStatus.OK)
                .body(functionDetails);
        } catch (RecordNotFoundException e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/getFunctionsByFiuId")
    public ResponseEntity<?> getFunctionsByFiuId(@RequestParam("fiuId") String fiuId) {
        List<FunctionDetails> functions = fiuFunctionService.getFunctions(fiuId);
        return ResponseEntity.status(HttpStatus.OK).body(functions);
    }
}
