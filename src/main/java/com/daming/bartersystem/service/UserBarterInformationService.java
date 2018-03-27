package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.UserBarterUnformation;

public interface UserBarterInformationService {
    UserBarterUnformation getUBInfoByUid(Integer uid);
}
