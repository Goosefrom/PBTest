package com.goose.pbtest.config.scheduling;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goose.pbtest.dto.EmitentJSONDTO;
import com.goose.pbtest.dto.EmitentResponseDTO;
import com.goose.pbtest.service.EmitentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipInputStream;


@Configuration
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class Scheduler {

    private final EmitentService service;

    //trustable url
    @Value("${url}")
    private String url;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 60)
    public void scheduledEmitentsAdding() throws IOException {
        log.info("Start scheduled job");

        List<Long> oldIds = service.getIds();

        //downloading file
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/zip");
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<Resource> response = template.exchange(url, HttpMethod.GET, request, Resource.class);
        Resource zipContent = response.getBody();

        log.info("Downloading end, status: {}", response.getStatusCode().value());

        if (zipContent != null) {
            //extract file content to byte array
            ZipInputStream zipInputStream = new ZipInputStream(zipContent.getInputStream());
            byte[] content = null;
            byte[] buff = new byte[4096];
            if (zipInputStream.getNextEntry() != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (zipInputStream.available() > 0) {
                    int byteLength = 0;
                    while ((byteLength = zipInputStream.read(buff)) > 0) {
                        byteArrayOutputStream.write(buff, 0, byteLength);
                    }
                }
                byteArrayOutputStream.close();
                content = byteArrayOutputStream.toByteArray();
            }
            zipInputStream.close();

            //cast file content to list of objects
            ObjectMapper jsonMapper = new ObjectMapper();
            List<EmitentJSONDTO> emitents = jsonMapper.readValue(content, new TypeReference<List<EmitentJSONDTO>>() {});

            //save to db
            List<EmitentResponseDTO> results =  service.addAllEmitent(emitents, oldIds);
            log.info("Results committed, number: {}", results.size());
        }
        else log.info("Downloading gain no results");

        log.info("Scheduled job is done, will repeat after one hour");
    }
}
