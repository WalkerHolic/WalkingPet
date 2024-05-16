package com.walkerholic.walkingpet.domain.record.service;

import com.walkerholic.walkingpet.domain.record.dto.MyRecordResponse;
import com.walkerholic.walkingpet.domain.record.dto.response.AllUserRecordResponse;
import com.walkerholic.walkingpet.domain.record.dto.response.UploadRecordResponse;
import com.walkerholic.walkingpet.domain.record.entity.Record;
import com.walkerholic.walkingpet.domain.record.repository.RecordRepository;
import com.walkerholic.walkingpet.domain.users.address.AddressFunction;
import com.walkerholic.walkingpet.domain.users.address.AddressService;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UserDetailRepository;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import com.walkerholic.walkingpet.global.error.GlobalErrorCode;
import com.walkerholic.walkingpet.global.s3.dto.response.S3FileUpload;
import com.walkerholic.walkingpet.global.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecordService {
    private final UsersRepository usersRepository;
    private final RecordRepository recordRepository;
    private final S3Service s3Service;
    private final AddressFunction addressFunction;

    /**
     * 기록 등록하기
     * @param userId 업로드하려는 유저 아이디
     * @param multipartFile 업로드하려는 사진파일
     * @param characterId 유저가 장착하고있는 캐릭터 아이디
     * @param latitude 업로드한 시점의 위도
     * @param longitude 업로드한 시점의 경도
     * @return 업로드 결과 정보 UploadRecordResponse
     */
    public UploadRecordResponse uploadRecord(int userId, MultipartFile multipartFile, int characterId, double latitude, double longitude){
        if (multipartFile == null) {
            throw new IllegalArgumentException("업로드하려는 파일이 존재하지 않습니다.");
        }
        try{
            Users user = getUserById(userId);
            //s3에 파일 업로드
            S3FileUpload s3FileUpload = s3Service.saveFile(multipartFile);
            //업로드 response 데이터
            UploadRecordResponse uploadRecordResponse = setuploadRecordResponse(s3FileUpload, characterId);
            //RecordEntity에 Record 저장
            saveRecord(user, uploadRecordResponse, latitude, longitude);

            return uploadRecordResponse;
        }
        catch (Exception e){
            System.out.println("파일 업로드 실패" + e.getMessage());
            throw new RuntimeException("Failed to upload record", e);
        }
    }

    /**
     * 유저가 작성한 모든 기록 가져오기
     * @param userId 유저아이디
     * @return 유저가 작성한 기록
     */
    public AllUserRecordResponse getAllRecord(int userId) {
        List<Record> recordList = recordRepository.findAllByUserId(userId);
        List<MyRecordResponse> myRecordResponsesList = new ArrayList<>();

        for(Record r : recordList){
            myRecordResponsesList.add(MyRecordResponse.from(r));
        }

        return AllUserRecordResponse.builder()
                .myRecordResponsesList(myRecordResponsesList)
                .build();
   }

    /**
     * Record 저장하기
     * @param user 유저
     * @param uploadRecordResponse upload 결과물
     */
    private void saveRecord(Users user, UploadRecordResponse uploadRecordResponse, double latitude, double longitude) {
        String[] administrativeDistrict = addressFunction.getDistrictFromAddress(Double.toString(latitude),Double.toString(longitude));
        recordRepository.save(Record.builder()
                .user(user)
                .uploadRecordResponse(uploadRecordResponse)
                .latitude(BigDecimal.valueOf(latitude))
                .longitude(BigDecimal.valueOf(longitude))
                .city(administrativeDistrict[0])
                .district(administrativeDistrict[1])
                .region(administrativeDistrict[2])
                .build());
    }

    public boolean deleteImage(int userId, String fileName){
        Record record = getRecordByUserIdAndFileName(userId, fileName);
        if(record == null){
            return  false;
        }
        else{
            recordRepository.delete(record);
            s3Service.deleteImage(fileName);
            return true;
        }
    }

    /**
     * UploadRecordResponse의 세팅
     * @param s3FileUpload s3를 통해 업로드할때의 반환값
     * @param characterId 기록을 업로드한 유저의 캐릭터
     * @return UploadRecordResponse
     */
    private UploadRecordResponse setuploadRecordResponse(S3FileUpload s3FileUpload, int characterId){

        return UploadRecordResponse.builder()
                .characterId(characterId)
                .imageName(s3FileUpload.getImageFileName())
                .imageUrl(s3FileUpload.getImageUrl())
                .regDate(LocalDateTime.now())
                .build();
    }

    public Users getUserById(int userId){
        return usersRepository.findById(userId)
                .orElseThrow(()-> new GlobalBaseException(GlobalErrorCode.USER_NOT_FOUND));
    }

    public Record getRecordByUserIdAndFileName(int userId, String fileName){
        return recordRepository.findByUserIdAndImageName(userId, fileName)
                .orElseThrow(()-> new GlobalBaseException(GlobalErrorCode.RECORD_NOT_FOUND));
    }
}
