package com.goose.pbtest.service;

import com.goose.pbtest.dto.EmitentJSONDTO;
import com.goose.pbtest.dto.EmitentResponseDTO;
import com.goose.pbtest.model.Emitent;

import java.util.List;

public interface EmitentService {

    EmitentResponseDTO findByCard(String cardNumber);

    List<EmitentResponseDTO> addAllEmitent(List<EmitentJSONDTO> emitents, List<Long> oldIds);

    List<Long> getIds();
}
