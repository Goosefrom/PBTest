package com.goose.pbtest.controller;

import com.goose.pbtest.dto.EmitentRequestDTO;
import com.goose.pbtest.dto.EmitentResponseDTO;
import com.goose.pbtest.service.EmitentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestApiController {
    private final EmitentService service;

    @PostMapping("/")
    public EmitentResponseDTO findBankByCard(@Valid @RequestBody EmitentRequestDTO requestDTO) {
        return service.findByCard(requestDTO.getCard());
    }
}
