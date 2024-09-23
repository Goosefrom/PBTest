package com.goose.pbtest.service.implementation;

import com.goose.pbtest.config.exception.ErrorType;
import com.goose.pbtest.config.exception.FindingException;
import com.goose.pbtest.dto.EmitentJSONDTO;
import com.goose.pbtest.dto.EmitentResponseDTO;
import com.goose.pbtest.config.mapper.EmitentMapper;
import com.goose.pbtest.model.Emitent;
import com.goose.pbtest.repository.EmitentRepository;
import com.goose.pbtest.service.EmitentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmitentServiceI implements EmitentService {

    private final EmitentRepository repository;

    private final EmitentMapper mapper;

    @Override
    public EmitentResponseDTO findByCard(String cardNumber) {
        String cardNumberFull = cardNumber + "000";
        Optional<Emitent> optRes = repository.findByCardInRange(cardNumberFull);
        EmitentResponseDTO result = mapper.modelToResponseDTO(optRes
                .orElseThrow(() -> new FindingException(ErrorType.NOT_FOUND, "Information not found")));
        log.info("Getting bank info by number={}, result is: {}", cardNumberFull, result);
        return result;
    }


    @Override
    public List<EmitentResponseDTO> addAllEmitent(List<EmitentJSONDTO> emitents, List<Long> oldIds) {

        List<Emitent> models = emitents.stream().map(mapper::jsonDTOToModel).collect(Collectors.toList());
        List<EmitentResponseDTO> results = repository.saveAll(models)
                .stream().map(mapper::modelToResponseDTO)
                .collect(Collectors.toList());
        log.info("Emitents saved");

        for (int i = 0; i < oldIds.size(); i += 50000) {
            repository.deleteAllById(oldIds.subList(i, Math.min(i + 50000, oldIds.size())));
        }
        log.info("Deletion success");
        return results;

    }

    @Override
    public List<Long> getIds() {
        List<Long> results = repository.findAllId();
        log.info("Get all existing ids in db, number: {}", results.size());
        return results;
    }

}
