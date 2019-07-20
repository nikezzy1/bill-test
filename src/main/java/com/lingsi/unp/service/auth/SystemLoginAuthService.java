package com.lingsi.unp.service.auth;

import com.lingsi.unp.mapper.auth.AppUserMapper;
import com.lingsi.unp.mapper.user.AppUserDetailsMapper;
import com.lingsi.unp.model.auth.AppUser;
import com.lingsi.unp.model.user.AppUserDetails;
import com.lingsi.unp.utils.base.SpringUtils;
import org.springframework.stereotype.Service;

@Service
public class SystemLoginAuthService {
    public String  verifyUserDetails(String userName,String idCardNo,String telephone,String companyName,String grantedCode,String massageCode){
        AppUserDetailsMapper appUserDetailsMapper = SpringUtils.getBean(AppUserDetailsMapper.class);
        AppUserDetails appUserDetails= appUserDetailsMapper.selectByPhone(telephone);
        boolean verifyResult =
        userName.equals(appUserDetails.getUserName()) && idCardNo.equals(appUserDetails.getIdCardNo())
                && companyName.equals(appUserDetails.getCompanyName()) && grantedCode.equals(appUserDetails.getGrantedCode())
                && massageCode.equals("0000") ;
        System.out.println("verifyResult="+verifyResult+";appUserDetails="+appUserDetails.getUserId());
        if(verifyResult)
            return appUserDetails.getUserId();
        else
            return null;
    }

    public int enbleUser(String userid){
        AppUserMapper appUserMapper = SpringUtils.getBean(AppUserMapper.class);
        AppUser appuser =  appUserMapper.selectByPrimaryKey(userid);
        appuser.setEnabled(true);
        return appUserMapper.updateByPrimaryKeySelective(appuser);
    }
}
