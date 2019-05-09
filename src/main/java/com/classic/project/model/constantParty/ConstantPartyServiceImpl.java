package com.classic.project.model.constantParty;

import com.classic.project.model.character.CharacterRepository;
import com.classic.project.model.character.TypeOfCharacter;
import com.classic.project.model.constantParty.exception.ConstantPartyExistException;
import com.classic.project.model.constantParty.exception.FileExistsException;
import com.classic.project.model.constantParty.file.CpFile;
import com.classic.project.model.constantParty.file.CpFileRepository;
import com.classic.project.model.constantParty.file.FileType;
import com.classic.project.model.constantParty.response.ResponseConstantParty;
import com.classic.project.model.constantParty.response.file.FileResponse;
import com.classic.project.model.constantParty.response.file.RootFolderResponse;
import com.classic.project.model.constantParty.response.file.SubFolderResponse;
import com.classic.project.model.user.User;
import com.classic.project.model.user.UserRepository;
import com.classic.project.security.UserAuthConfirm;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
public class ConstantPartyServiceImpl implements ConstantPartyService {

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

    @Override
    public ResponseEntity<ResponseConstantParty> getCpByLeaderId(int userId) {
        ResponseConstantParty responseCP = ResponseConstantParty.convertForLeader(constantPartyRepository.findByLeaderId(userId));
        return new ResponseEntity<>(responseCP, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseConstantParty> getCp(int cpId, int userId) {
        userAuthConfirm.isTheAuthUser(userRepository.findById(userId).get());
        if(isMemberOfTheCP(cpId, userId)) {
            ResponseConstantParty responseCP = ResponseConstantParty.convertForLeader(constantPartyRepository.findById(cpId));
            return new ResponseEntity<>(responseCP, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public void deleteMember(int characterId) {
        User member = characterRepository.findById(characterId).get().getUser();
        userRepository.deleteMemberByCharacterIdId(member.getUserId());
        int boxes = constantPartyRepository.findById(member.getCp().getCpId()).get().getNumberOfBoxes() - characterRepository.countByTypeOfCharacterAndAndUser(TypeOfCharacter.BOX, member);
        int actives = constantPartyRepository.findById(member.getCp().getCpId()).get().getNumberOfActivePlayers() - 1;
        constantPartyRepository.addUsersTpCP(actives, boxes, member.getCp().getCpId());
    }

    @Override
    public void updateEpicPoints(String rbName, int pointsToAdd, int cpId) {
        Optional<ConstantParty> cpFromDb = constantPartyRepository.findById(cpId);
        if(rbName.equals("Orfen")){
            constantPartyRepository.updateOrfenPoints(cpId, cpFromDb.get().getOrfenPoints() + pointsToAdd);
        } else if(rbName.equals("Core")){
            constantPartyRepository.updateCorePoints(cpId, cpFromDb.get().getCorePoints() + pointsToAdd);
        } else if(rbName.equals("Queen Ant")) {
            constantPartyRepository.updateAQPoints(cpId, cpFromDb.get().getAqPoints() + pointsToAdd);
        }
    }

    @Override
    public void addNewCP(ConstantParty newCp) {
        if(constantPartyRepository.findByCpNameContaining(newCp.getCpName()).isPresent()){
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
        this.rootLocation = Paths.get(Paths.get(environment.getProperty("photo.upload.path")+cpName).toFile().getAbsolutePath());
        try {
            if (photo.isEmpty()) {
                //throw new StorageException("Failed to store empty file " + photo.getOriginalFilename());
                return false;
            }
            if (!rootLocation.toFile().exists()){
                boolean mkdir = rootLocation.toFile().mkdir();
                if(!mkdir){
                    boolean mkdirs = rootLocation.toFile().mkdirs();
                    if(!mkdirs) {
                        return false;
                    }
                }
            }
            if(this.rootLocation.toFile().isDirectory()) {
                Files.copy(photo.getInputStream(), this.rootLocation.resolve(photo.getOriginalFilename()));
            } else {
                return false;
            }
        } catch (Exception e) {
            if(e instanceof FileAlreadyExistsException){
                throw new FileExistsException("The photo already exists");
            }
            throw new FileExistsException(e.getLocalizedMessage());
        }
        return true;
    }

    private Boolean isMemberOfTheCP(int cpId, int userId) {
        return  userRepository.isUserMemberOfCP(cpId, userId).isPresent();
    }

    private RootFolderResponse getFodlersByCPId(){
        RootFolderResponse foldersResponse = null;
        List<CpFile> files = cpFileRepository.findAll();
        if(files.isEmpty()) {
            return new RootFolderResponse("","", new ArrayList<>(), FileType.FOLDER.getType());
        }
        for(CpFile file : files) {
            if(file.getFileType().equals(FileType.ROOT)) {
                foldersResponse = new RootFolderResponse(file.getFileId(), file.getFilename(),Arrays.asList(file.getParents().split(",")), file.getFileType().getType());
            }
        }
        List<CpFile> toBeRemoved = new ArrayList<>();
        for (CpFile file : files) {
            //System.out.printf("%s (%s)\n", file.getName(), file);
            if (file.getParents().contains(foldersResponse.getFolderId())) { //Monthly Folders
                foldersResponse.getFolderResponseMap().put(file.getFileId(), new SubFolderResponse(file.getFileId(), file.getFilename(), Arrays.asList(file.getParents().split(",")), file.getFileType().getType()));
                toBeRemoved.add(file);
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
            for (Map.Entry<String, SubFolderResponse> subFolderResponse : foldersResponse.getFolderResponseMap().entrySet()) {
                toBeRemoved.add(insertToFolder(subFolderResponse, file));
            }
        }
        files.removeAll(toBeRemoved);
        toBeRemoved.clear();

        //adds bosses folders for ech daily folder
        for (CpFile file : files) {
            for (Map.Entry<String, SubFolderResponse> subFolderResponse : foldersResponse.getFolderResponseMap().entrySet()) {
                for (Map.Entry<String, SubFolderResponse> depperSubFolder : subFolderResponse.getValue().getFolderResponseMap().entrySet()) {
                    toBeRemoved.add(insertToFolder(depperSubFolder, file));
                }
            }
        }
        files.removeAll(toBeRemoved);
        toBeRemoved.clear();

        for (CpFile file : files) {
            for (Map.Entry<String, SubFolderResponse> subFolderResponse : foldersResponse.getFolderResponseMap().entrySet()) {
                for (Map.Entry<String, SubFolderResponse> depperSubFolder : subFolderResponse.getValue().getFolderResponseMap().entrySet()) {
                    for (Map.Entry<String, SubFolderResponse> bossFolder : depperSubFolder.getValue().getFolderResponseMap().entrySet()) {
                        toBeRemoved.add(insertToFolder(bossFolder, file));
                    }
                }
            }
        }

        files.removeAll(toBeRemoved);
        toBeRemoved.clear();

    }

    private CpFile insertToFolder(Map.Entry<String, SubFolderResponse> folder, CpFile file) {
        if (file.getParents().contains(folder.getKey())) {
            if (file.getFileType().getType().equals(FileType.FOLDER.getType())) {
                addsFolder(folder, file);
            } else if (file.getFileType().getType().contains(FileType.IMAGE.getType())) {
                addsFile(folder, file);
            }
            return file;
        }
        return null;
    }

    private void addsFolder(Map.Entry<String, SubFolderResponse> folder, CpFile file) {
        folder.getValue().getFolderResponseMap().put(file.getFileId(), new SubFolderResponse(file.getFileId(), file.getFilename(),Arrays.asList(file.getParents().split(",")), file.getFileType().getType()));
    }

    private void addsFile(Map.Entry<String, SubFolderResponse> folder, CpFile file) {
        folder.getValue().getFileResponseMap().put(file.getFileId(), new FileResponse(file.getFileId(), file.getFilename(), Arrays.asList(file.getParents().split(",")), file.getFileType().getType()));
    }
}
