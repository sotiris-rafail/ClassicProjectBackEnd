package com.classic.project.model.constantParty;

import com.classic.project.model.character.CharacterRepository;
import com.classic.project.model.character.TypeOfCharacter;
import com.classic.project.model.constantParty.exception.ConstantPartyExistException;
import com.classic.project.model.constantParty.exception.FileExistsException;
import com.classic.project.model.constantParty.file.CpFile;
import com.classic.project.model.constantParty.file.CpFileRepository;
import com.classic.project.model.constantParty.file.FileType;
import com.classic.project.model.constantParty.file.GoogleCredential;
import com.classic.project.model.constantParty.file.parentFile.ParentFile;
import com.classic.project.model.constantParty.file.parentFile.ParentFileRepository;
import com.classic.project.model.constantParty.response.ResponseConstantParty;
import com.classic.project.model.constantParty.response.file.AddNewFile;
import com.classic.project.model.constantParty.response.file.FileResponse;
import com.classic.project.model.constantParty.response.file.RootFolderResponse;
import com.classic.project.model.constantParty.response.file.SubFolderResponse;
import com.classic.project.model.user.User;
import com.classic.project.model.user.UserRepository;
import com.classic.project.security.UserAuthConfirm;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@CacheConfig(cacheNames = {"membersDashBoard", "clanMembers"})
public class ConstantPartyServiceImpl implements ConstantPartyService {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "Classic Project";

    @Autowired
    private ConstantPartyRepository constantPartyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthConfirm userAuthConfirm;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private Environment environment;

    private Path rootLocation;

    @Autowired
    private CpFileRepository cpFileRepository;

    @Autowired
    private ParentFileRepository parentFileRepository;

    @Value("${google.drive.credentials}")
    private String googleDriveCredentials;

    @Value("${photo.upload.path}")
    private String uploadPhotoPath;

    @Override
    public ResponseEntity<ResponseConstantParty> getCpByLeaderId(int userId) {
        ResponseConstantParty responseCP = ResponseConstantParty.convertForLeader(constantPartyRepository.findByLeaderId(userId));
        return new ResponseEntity<>(responseCP, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseConstantParty> getCp(int cpId, int userId) {
        userAuthConfirm.isTheAuthUser(userRepository.findById(userId).get());
        if (isMemberOfTheCP(cpId, userId)) {
            ResponseConstantParty responseCP = ResponseConstantParty.convertForLeader(constantPartyRepository.findById(cpId));
            return new ResponseEntity<>(responseCP, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteMember(int characterId) {
        User member = characterRepository.findById(characterId).get().getUser();
        userRepository.deleteMemberByCharacterIdId(member.getUserId());
        int boxes = constantPartyRepository.findById(member.getCp().getCpId()).get().getNumberOfBoxes() - characterRepository.countByTypeOfCharacterAndAndUser(TypeOfCharacter.BOX, member);
        int actives = constantPartyRepository.findById(member.getCp().getCpId()).get().getNumberOfActivePlayers() - 1;
        constantPartyRepository.addUsersTpCP(actives, boxes, member.getCp().getCpId());
    }

    @Override
    public void addNewCP(ConstantParty newCp) {
        if (constantPartyRepository.findByCpNameContaining(newCp.getCpName()).isPresent()) {
            throw new ConstantPartyExistException(newCp.getCpName());
        }
        newCp.setMembers(new ArrayList<>());
        newCp.setAqPoints(0);
        newCp.setCorePoints(0);
        newCp.setOrfenPoints(0);
        newCp.setNumberOfActivePlayers(0);
        newCp.setNumberOfBoxes(0);
        constantPartyRepository.save(newCp);
    }

    @Override
    public ResponseEntity<List<ResponseConstantParty>> getCPIdName() {
        List<ConstantParty> cpsFromDb = constantPartyRepository.findAll();
        List<ResponseConstantParty> response = new ArrayList<>();
        cpsFromDb.forEach(cp -> response.add(ResponseConstantParty.convertForAddSingleUser(cp)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ResponseConstantParty>> getCPNumbers() {
        List<ConstantParty> cpsFromDb = constantPartyRepository.findAll();
        List<ResponseConstantParty> response = new ArrayList<>();
        cpsFromDb.forEach(cp -> response.add(ResponseConstantParty.convertNumbersForDashboard(cp)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ResponseConstantParty>> getEpicPoints() {
        List<ConstantParty> cpsFromDb = constantPartyRepository.findAll();
        List<ResponseConstantParty> response = new ArrayList<>();
        cpsFromDb.forEach(cp -> response.add(ResponseConstantParty.convertPointsForDashboard(cp)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public boolean uploadEpicPhoto(MultipartFile photo, int cpId, String cpName) {
        this.rootLocation = Paths.get(Paths.get(uploadPhotoPath + constantPartyRepository.findById(cpId).get().getCpName()).toFile().getAbsolutePath());
        try {
            if (photo.isEmpty()) {
                //throw new StorageException("Failed to store empty file " + photo.getOriginalFilename());
                return false;
            }
            if (!rootLocation.toFile().exists()) {
                boolean mkdir = rootLocation.toFile().mkdir();
                if (!mkdir) {
                    boolean mkdirs = rootLocation.toFile().mkdirs();
                    if (!mkdirs) {
                        return false;
                    }
                }
            }
            if (this.rootLocation.toFile().isDirectory()) {
                Files.copy(photo.getInputStream(), this.rootLocation.resolve(photo.getOriginalFilename()));
                saveNewFileToDB(photo, cpName, cpId);
                Files.delete(this.rootLocation.resolve(photo.getOriginalFilename()));
            } else {
                return false;
            }
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new FileExistsException("The photo already exists");
            }
            throw new FileExistsException(e.getLocalizedMessage());
        }
        return true;
    }

    private void saveNewFileToDB(MultipartFile photo, String cpName, int cpId) {
        CpFile newFile = new CpFile();
        newFile.setFileType(FileType.IMAGE);
        newFile.setCreationTime(new Date());
        newFile.setFileId(uploadPhotoToDrive(photo, cpName));
        newFile.setFilename(photo.getOriginalFilename());
        newFile.setCpImg(constantPartyRepository.findById(cpId).get());
        newFile.setWebViewLink("https://drive.google.com/file/d/" + newFile.getFileId());
        newFile.setWebContentLink("https://drive.google.com/uc?id=" + newFile.getFileId() + "&export=download");
        List<ParentFile> parentFiles = new ArrayList<>();
        parentFiles.add(new ParentFile(newFile, cpName));
        newFile.setParents(parentFiles);
        newFile.getParents().get(0).setFileId(newFile.getFileId());
        cpFileRepository.save(newFile);
    }

    private File setUpMetaData(MultipartFile photo, String parentId) {
        File fileMetaData = new File();
        fileMetaData.setName(photo.getOriginalFilename());
        List<String> parents = new ArrayList<>();
        parents.add(parentId);
        fileMetaData.setParents(parents);
        fileMetaData.setCreatedTime(new DateTime(new Date()));
        return fileMetaData;
    }

    private String uploadPhotoToDrive(MultipartFile photo, String parentId) {
        java.io.File filePath = new java.io.File(this.rootLocation.toFile().getPath() + "/" + photo.getOriginalFilename());
        FileContent mediaContent = new FileContent(photo.getContentType(), filePath);
        final NetHttpTransport HTTP_TRANSPORT;
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleCredential.getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            File file = service.files().create(setUpMetaData(photo, parentId), mediaContent)
                    .setFields("id")
                    .execute();
            return file.getId();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public ResponseEntity<RootFolderResponse> getCpPhotos(int cpId, int userId) {
        userAuthConfirm.isTheAuthUser(userRepository.findById(userId).get());
        if (isMemberOfTheCP(cpId, userId)) {
            return new ResponseEntity<>(getFoldersByCPId(cpId), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public void addNewFolder(int cpId, AddNewFile cpFile) throws GeneralSecurityException, IOException {
        CpFile newFile = AddNewFile.convertNewFileToCPFile(cpFile);
        newFile.getParents().get(0).setFolderId(newFile);
        newFile.setFileId(insertFolderInDrive(newFile));
        newFile.getParents().get(0).setFileId(newFile.getFileId());
        newFile.setCpImg(constantPartyRepository.findById(cpId).get());
        cpFileRepository.save(newFile);
    }

    @Override
    public void deleteFile(String fileId) throws GeneralSecurityException, IOException {
        if (!cpFileRepository.existsById(fileId)) {
            throw new FileNotFoundException("File with ID: " + fileId + " does not exist");
        }
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleCredential.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        service.files().delete(fileId).execute();
        cpFileRepository.deleteById(fileId);
    }

    private String insertFolderInDrive(CpFile newFile) throws GeneralSecurityException, IOException {
        List<String> parents = new ArrayList<>();
        newFile.getParents().forEach(parentFile -> parents.add(parentFile.getParentId()));
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleCredential.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        File fileMetadata = new File();
        fileMetadata.setName(newFile.getFilename());
        fileMetadata.setParents(parents);
        fileMetadata.setMimeType(newFile.getFileType().getType());

        File file = service.files().create(fileMetadata)
                .setFields("id")
                .execute();
        return file.getId();
    }

    private Boolean isMemberOfTheCP(int cpId, int userId) {
        return userRepository.isUserMemberOfCP(cpId, userId).isPresent();
    }

    private RootFolderResponse getFoldersByCPId(int cpId) {
        RootFolderResponse foldersResponse = null;
        List<CpFile> toBeRemoved = new ArrayList<>();
        List<CpFile> files = cpFileRepository.findAllByCpImg(cpId);
        if (files.isEmpty()) {
            return new RootFolderResponse("", "", new ArrayList<>(), FileType.FOLDER.getType());
        }
        for (CpFile file : files) {
            if (file.getFileType().equals(FileType.ROOT)) {
                foldersResponse = new RootFolderResponse(file.getFileId(), file.getFilename(), parentFileRepository.getParentIdByFileId(file.getFileId()), file.getFileType().name(), file.getCreationTime(), file.getWebViewLink(), file.getWebContentLink());
                toBeRemoved.add(file);
            }
        }
        files.removeAll(toBeRemoved);
        toBeRemoved.clear();

        for (CpFile file : files) {
            //System.out.printf("%s (%s)\n", file.getName(), file);
            for (ParentFile parent : file.getParents()) { //Monthly Folders
                Optional<CpFile> cpFile = cpFileRepository.findById(parent.getParentId());
                if (cpFile.isPresent() && foldersResponse.getFolderId().equals(cpFile.get().getFileId()) && cpFile.get().getCpImg() != null) {
                    foldersResponse.getFolderResponseMap().add(new SubFolderResponse(file.getFileId(), file.getFilename(), parentFileRepository.getParentIdByFileId(file.getFileId()), file.getFileType().name()));
                    toBeRemoved.add(file);
                }
            }
        }
        files.removeAll(toBeRemoved);
        toBeRemoved.clear();
        buildTreeStructure(files, foldersResponse);//adds Daily folders
        return foldersResponse;
    }

    private void buildTreeStructure(List<CpFile> files, RootFolderResponse foldersResponse) {
        if (files.size() == 0) {
            return;
        }
        List<CpFile> toBeRemoved = new ArrayList<>();
        for (CpFile file : files) {
            for (SubFolderResponse subFolderResponse : foldersResponse.getFolderResponseMap()) {
                toBeRemoved.add(insertToFolder(subFolderResponse, file));
            }
        }
        files.removeAll(toBeRemoved);
        toBeRemoved.clear();

        //adds bosses folders for ech daily folder
        for (CpFile file : files) {
            for (SubFolderResponse subFolderResponse : foldersResponse.getFolderResponseMap()) {
                for (SubFolderResponse deeperSubFolder : subFolderResponse.getFolderResponseMap()) {
                    toBeRemoved.add(insertToFolder(deeperSubFolder, file));
                }
            }
        }
        files.removeAll(toBeRemoved);
        toBeRemoved.clear();

        for (CpFile file : files) {
            for (SubFolderResponse subFolderResponse : foldersResponse.getFolderResponseMap()) {
                for (SubFolderResponse depperSubFolder : subFolderResponse.getFolderResponseMap()) {
                    for (SubFolderResponse bossFolder : depperSubFolder.getFolderResponseMap()) {
                        toBeRemoved.add(insertToFolder(bossFolder, file));
                    }
                }
            }
        }

        files.removeAll(toBeRemoved);
        toBeRemoved.clear();

    }

    private CpFile insertToFolder(SubFolderResponse folder, CpFile file) {
        for (ParentFile parentFile : file.getParents()) {
            if (folder.getFolderId().equals(parentFile.getParentId())) {
                if (file.getFileType().getType().equals(FileType.FOLDER.getType())) {
                    addsFolder(folder, file);
                } else if (file.getFileType().getType().contains(FileType.IMAGE.getType())) {
                    addsFile(folder, file);
                }
                return file;
            }
        }
        return null;
    }

    private void addsFolder(SubFolderResponse folder, CpFile file) {//String folderId, String name, List<String> parent, String type, Date creationTime, String webViewLink, String webContentLink
        folder.getFolderResponseMap().add(new SubFolderResponse(file.getFileId(), file.getFilename(), parentFileRepository.getParentIdByFileId(file.getFileId()), file.getFileType().name(), file.getCreationTime(), file.getWebViewLink(), file.getWebContentLink()));
    }

    private void addsFile(SubFolderResponse folder, CpFile file) {
        folder.getFileResponseMap().add(new FileResponse(file.getFileId(), file.getFilename(), parentFileRepository.getParentIdByFileId(file.getFileId()), file.getFileType().name(), file.getCreationTime(), file.getWebViewLink(), file.getWebContentLink()));
    }
}
