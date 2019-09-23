package com.classic.project.model.constantParty.file.schedule;

import com.classic.project.model.constantParty.ConstantParty;
import com.classic.project.model.constantParty.ConstantPartyRepository;
import com.classic.project.model.constantParty.ConstantPartyService;
import com.classic.project.model.constantParty.file.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

//-Dhttps.protocols=SSLv3,TLSv1,TLSv1.1,TLSv1.2
@Component
public class GetPoints {
    @Autowired
    private ConstantPartyService constantPartyService;

    private static final String APPLICATION_NAME = "Classic Project";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    //@Scheduled(cron = "0 0 1 * * *") //every day at midnight
    private void getPoints() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleCredential.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        final String range = "Main!A3:B8";
        List<ConstantParty> cps = constantPartyService.findAllWithSpreadSheet();

        for (ConstantParty cp : cps) {
            ValueRange response = service.spreadsheets().values()
                    .get(cp.getSpreadSheetId(), range)
                    .execute();
            List<List<Object>> values = response.getValues();
            List<List<Object>> points = new ArrayList<>();
            values.forEach(value -> {
                if (!value.isEmpty()) {
                    points.add(value);
                }
            });
            if (!points.isEmpty()) {
                for (List row : points) {
                    if(row.get(0).toString().equalsIgnoreCase("AQ")) {
                        cp.setAqPoints(Integer.valueOf(row.get(1).toString()));
                    } else if(row.get(0).toString().equalsIgnoreCase("Core")) {
                        cp.setCorePoints(Integer.valueOf(row.get(1).toString()));
                    } else if(row.get(0).toString().equalsIgnoreCase("Orfen")) {
                        cp.setOrfenPoints(Integer.valueOf(row.get(1).toString()));
                    }
                }
            }
            constantPartyService.save(cp);
        }
    }

}
