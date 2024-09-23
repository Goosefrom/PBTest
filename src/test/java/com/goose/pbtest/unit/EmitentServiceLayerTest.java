package com.goose.pbtest.unit;

import com.goose.pbtest.config.exception.ErrorType;
import com.goose.pbtest.config.exception.FindingException;
import com.goose.pbtest.config.mapper.EmitentMapper;
import com.goose.pbtest.dto.EmitentJSONDTO;
import com.goose.pbtest.dto.EmitentRequestDTO;
import com.goose.pbtest.dto.EmitentResponseDTO;
import com.goose.pbtest.model.Emitent;
import com.goose.pbtest.repository.EmitentRepository;
import com.goose.pbtest.service.EmitentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmitentServiceLayerTest {

    @Autowired
    private EmitentService service;

    @Autowired
    private EmitentMapper mapper;

    @MockBean
    private EmitentRepository repository;

    @Test
    public void findByCardIsOkTest() {
        Emitent okResponse = new Emitent();
        okResponse.setBin("1");
        okResponse.setMinRange("1111110000000000000");
        okResponse.setMaxRange("1111119999999999999");
        okResponse.setCode("UA");
        okResponse.setName("test entry");

        when(repository.findByCardInRange(any())).thenReturn(Optional.of(okResponse));

        EmitentResponseDTO responseDTO = service.findByCard("1111111111111111");
        verify(repository).findByCardInRange("1111111111111111000");
        assertThat(responseDTO.getAlphacode()).isEqualTo(okResponse.getCode());
    }

    @Test
    public void findByCardThrownException() {
        assertThatThrownBy(() -> service.findByCard("1111111111111111"))
                .isInstanceOf(FindingException.class)
                .matches(error -> ((FindingException) error).getErrorType() == ErrorType.NOT_FOUND);
    }

    @Test
    public void addAllEmitentIsOk() {
        EmitentJSONDTO jsondto = new EmitentJSONDTO();
        jsondto.setBin("1");
        jsondto.setMinRange("1111110000000000000");
        jsondto.setMaxRange("1111119999999999999");
        jsondto.setCode("UA");
        jsondto.setName("test entry 1");
        EmitentJSONDTO jsondto1 = new EmitentJSONDTO();
        jsondto1.setBin("2");
        jsondto1.setMinRange("1111120000000000000");
        jsondto1.setMaxRange("1111129999999999999");
        jsondto1.setCode("UA");
        jsondto1.setName("test entry 2");
        List<EmitentJSONDTO> jsondtoList = List.of(jsondto, jsondto1);
        List<Emitent> emitentList = jsondtoList.stream().map(mapper::jsonDTOToModel).collect(Collectors.toList());
        List<EmitentResponseDTO> responseList = emitentList.stream()
                .map(mapper::modelToResponseDTO).collect(Collectors.toList());
        when(repository.saveAll(any())).thenReturn(emitentList);
        List<EmitentResponseDTO> result = service.addAllEmitent(jsondtoList, List.of(1L, 2L));

        verify(repository).saveAll(any());
        verify(repository).deleteAllById(List.of(1L, 2L));

        assertThat(result.get(0).getBank()).isEqualTo(responseList.get(0).getBank());
    }

    @Test
    public void getIdsIsOk() {
        when(repository.findAllId()).thenReturn(List.of(1L, 2L));
        List<Long> result = service.getIds();
        verify(repository, times(2)).findAllId();
        assertThat(result.get(0)).isEqualTo(1L);
    }
}
