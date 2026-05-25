package com.rm.ums.auth.services;

import com.rm.ums.auth.model.response.SignInResponse;
import com.rm.ums.common.model.response.CustomResponse;
import com.rm.ums.common.repositories.UrlStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static com.rm.ums.common.enums.SignInApiMetadataMappingNameEnum.urlStatusList;
import static com.rm.ums.common.enums.UmsResponseMessageEnum.SIGNED_IN_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UrlStatusRepository urlStatusRepo;

    public CustomResponse signIn() {
        SignInResponse signInResponse = new SignInResponse();
            signInResponse.setMetaData(prepareMetaData());
        return CustomResponse.success(signInResponse, SIGNED_IN_SUCCESSFULLY);
    }

    private Map<String, Object> prepareMetaData() {
        Map<String, Object> metaData = new HashMap<>();
        metaData.put(urlStatusList.name(), urlStatusRepo.findUrlStatusList());
        return metaData;
    }
}
