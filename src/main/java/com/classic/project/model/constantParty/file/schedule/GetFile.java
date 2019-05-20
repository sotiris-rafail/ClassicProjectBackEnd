package com.classic.project.model.constantParty.file.schedule;

import com.classic.project.model.constantParty.ConstantPartyRepository;
import com.classic.project.model.constantParty.file.CpFile;
import com.classic.project.model.constantParty.file.CpFileRepository;
import com.classic.project.model.constantParty.file.FileType;
import com.classic.project.model.constantParty.file.parentFile.ParentFile;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

//-Dhttps.protocols=SSLv3,TLSv1,TLSv1.1,TLSv1.2
@Component
public class GetFile {

    @Autowired
    private CpFileRepository cpFileRepository;

    @Autowired
    private ConstantPartyRepository constantPartyRepository;

    @Value("${google.drive.credentials}")
    private String googleDriveCredentials;

    private static final String APPLICATION_NAME = "Classic Project";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GetFile.class.getResourceAsStream(googleDriveCredentials);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + googleDriveCredentials);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("online")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    @Scheduled(cron = "0 0 1 * * *") //every day at midnight
    public void getFile() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
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
