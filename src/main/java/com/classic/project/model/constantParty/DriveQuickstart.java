package com.classic.project.model.constantParty;

import com.classic.project.model.constantParty.file.CpFile;
import com.classic.project.model.constantParty.file.CpFileRepository;
import com.classic.project.model.constantParty.file.FileType;
import com.classic.project.model.constantParty.response.file.FileResponse;
import com.classic.project.model.constantParty.response.file.RootFolderResponse;
import com.classic.project.model.constantParty.response.file.SubFolderResponse;
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
public class DriveQuickstart {

    @Autowired
    private CpFileRepository cpFileRepository;

    @Autowired
    private ConstantPartyRepository constantPartyRepository;

    private static final String APPLICATION_NAME = "Classic Project";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";//to se set as property

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = DriveQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
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

    List<CpFile> cpFiles = new ArrayList<>();

    @Scheduled(cron = "0 * * * * *")
    public void main() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        RootFolderResponse foldersResponse = null;
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        SimpleDateFormat dfs = new SimpleDateFormat("YYYY-MM-dd");
        Calendar today = Calendar.getInstance();
        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
                .setSpaces("drive")
                .setPageSize(1000)
                .setFields("files(id,name,parents,webViewLink,webContentLink,mimeType,createdTime,trashed)")
                //.setQ("createdTime > '"+ dfs.format(today.getTime()) +"T00:00:00' and createdTime < '"+ dfs.format(today.getTime()) +"T23:59:59' and (mimeType contains 'file/' or mimeType contains 'application/vnd.google-apps.folder')")
                //.setQ("createdTime > '2019-05-05T00:00:00' and createdTime < '2019-05-05T23:59:59'")
                //.setQ("'1-ShVtg0FZvYWXbl11QLE9qMjbmGrL44Y' in parents and (mimeType contains 'image/' or mimeType contains 'application/vnd.google-apps.folder')")
                .setQ("mimeType contains 'image/' or mimeType contains 'application/vnd.google-apps.folder'")
                //.setQ("createdTime > '2012-06-04T12:00:00' and (mimeType contains 'file/' or mimeType contains 'video/')")
                .setOrderBy("folder,createdTime")
                .execute();

        List<File> files = result.getFiles();

        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {

            List<CpFile> rootFolders = new ArrayList<>();
            List<File> toBeRemoved = new ArrayList<>();
            List<File> trashed = new ArrayList<>();
            for (File file : files) {
                if (constantPartyRepository.findByRootFolderId(file.getId()).isPresent()) {
                    rootFolders.add(new CpFile(file.getId(), file.getName(), String.join(",", file.getParents()), FileType.ROOT, new Date(file.getCreatedTime().getValue()),
                            file.getWebViewLink(), file.getWebContentLink(), constantPartyRepository.findByRootFolderId(file.getId()).get()));
                    toBeRemoved.add(file);
                }

            }

            cpFileRepository.saveAll(rootFolders);
            files.removeAll(toBeRemoved);

            for (File file : files) {
                //String fileId, String filename, String parents, FileType fileType, Date creationTime, String webViewLink, String webContentLink, ConstantParty cpImg
                cpFiles.add(new CpFile(file.getId(), file.getName(), String.join(",", file.getParents()), FileType.getType(file.getMimeType()), new Date(file.getCreatedTime().getValue()), file.getWebViewLink(), file.getWebContentLink(), null));
            }

            for (CpFile file : cpFiles) {
                if (!file.getFileType().getType().equals(FileType.ROOT.getType())) {
                    file.setCpImg(findCpId(cpFiles, file).get().getCpImg());
                }
            }
            cpFileRepository.saveAll(cpFiles);
        }
        cpFiles.clear();
    }

    private Optional<CpFile> findCpId(List<CpFile> files, CpFile file) {
        Optional<CpFile> parent;
        if (!cpFileRepository.existsById(file.getFileId())) {
            parent = cpFileRepository.findById(file.getParents());
            if (!parent.isPresent()) {
                parent = findParent(files, file.getParents());
            }
        } else {
            return cpFileRepository.findById(file.getFileId());
        }
        if (parent.isPresent()) {
            if (parent.get().getCpImg() != null || parent.get().getFileType().getType().equals(FileType.ROOT.getType())) {
                return parent;
            } else {
                return findCpId(files, parent.get());
            }
        } else {
            if (!findParent(cpFiles, file.getParents()).isPresent()) {
                CpFile cpFile = new CpFile();
                cpFile.setCpImg(constantPartyRepository.findById(99).get());
                return Optional.of(cpFile);
            } else {
                return findParent(cpFiles, file.getParents());
            }
        }

    }

    private Optional<CpFile> findParent(List<CpFile> cpFiles, String parents) {
        for (CpFile cpFile : cpFiles) {
            if (cpFile.getFileId().equals(parents)) {
                return Optional.of(cpFile);
            }
        }
        return Optional.empty();
    }
}
