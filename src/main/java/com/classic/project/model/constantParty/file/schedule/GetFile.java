package com.classic.project.model.constantParty.file.schedule;

import com.classic.project.model.constantParty.ConstantPartyRepository;
import com.classic.project.model.constantParty.file.CpFile;
import com.classic.project.model.constantParty.file.CpFileRepository;
import com.classic.project.model.constantParty.file.FileType;
import com.classic.project.model.constantParty.file.GoogleCredential;
import com.classic.project.model.constantParty.file.parentFile.ParentFile;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//-Dhttps.protocols=SSLv3,TLSv1,TLSv1.1,TLSv1.2
@Component
public class GetFile {

    @Autowired
    private CpFileRepository cpFileRepository;

    @Autowired
    private ConstantPartyRepository constantPartyRepository;

    private static final String APPLICATION_NAME = "Classic Project";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */

    //@Scheduled(cron = "0 0 1 * * *") //every day at midnight
    public void getFile() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleCredential.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        SimpleDateFormat dfs = new SimpleDateFormat("YYYY-MM-dd");
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, -1);
        // Print the names and IDs for up to 10 files.
        String tokenPage = null;
        System.out.println(dfs.format(today.getTime()));
        do {
            FileList result = service.files().list()
                    .setSpaces("drive")
                    .setPageSize(1000).setPageToken(tokenPage)
                    .setFields("nextPageToken,incompleteSearch,files(id,name,parents,webViewLink,webContentLink,mimeType,createdTime)")
                    .setQ("createdTime > '" + dfs.format(today.getTime()) + "T00:00:00' and createdTime < '" + dfs.format(today.getTime()) + "T23:59:59' and (mimeType contains 'image/' or mimeType contains 'application/vnd.google-apps.folder')")
                    .setOrderBy("folder,createdTime")
                    .execute();

            List<File> files = result.getFiles();
            tokenPage = result.getNextPageToken();
            if (files == null || files.isEmpty()) {
                System.out.println("No files found.");
            } else {
                List<CpFile> cpFiles = new ArrayList<>();
                for (File file : files) {
                    CpFile toBeAdded;
                    if (constantPartyRepository.findByRootFolderId(file.getId()).isPresent()) {
                        toBeAdded = new CpFile(file.getId(), file.getName(), FileType.ROOT, new Date(file.getCreatedTime().getValue()),
                                file.getWebViewLink(), file.getWebContentLink(), constantPartyRepository.findByRootFolderId(file.getId()).get());
                    } else {
                        toBeAdded = new CpFile(file.getId(), file.getName(), FileType.getType(file.getMimeType()), new Date(file.getCreatedTime().getValue()), file.getWebViewLink(), file.getWebContentLink(), null);
                    }
                    toBeAdded.setParents(setParents(file.getParents(), toBeAdded));
                    cpFiles.add(toBeAdded);
                }
                cpFileRepository.saveAll(cpFiles);
            }
        } while (tokenPage != null);

    }

    private static List<ParentFile> setParents(List<String> parents, CpFile file) {
        if (parents != null && !parents.isEmpty()) {
            List<ParentFile> parent = new ArrayList<>();
            for (String parentId : parents) {
                parent.add(new ParentFile(file, parentId, file.getFileId()));
            }
            return parent;
        }
        return new ArrayList<>();
    }

}
