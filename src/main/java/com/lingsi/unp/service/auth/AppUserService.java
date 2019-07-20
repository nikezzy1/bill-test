package com.lingsi.unp.service.auth;

import com.alibaba.fastjson.JSONObject;
import com.lingsi.unp.exception.DMException;
import com.lingsi.unp.mapper.auth.AppUserMapper;
import com.lingsi.unp.mapper.user.AppUserDetailsMapper;
import com.lingsi.unp.model.auth.AppUser;
import com.lingsi.unp.model.user.AppUserDetails;
import com.lingsi.unp.utils.app.parse.JsonSimpleUtil;
import com.lingsi.unp.utils.auth.UserUtils;
import com.lingsi.unp.utils.base.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "appUserService")
@Transactional
public class AppUserService implements  UserDetailsService {
    @Autowired
    private AppUserMapper userMapper;//这里会报错，但是并不会影响

    @Autowired
    private AppUserDetailsMapper appUserDetailsMapper;

    public int addUser(String userId,String userName,String passWord,String idCardNo,String telephone,String companyName,String companyLicenseNo,String grantedCode) {
        try {
            AppUser appuser = new AppUser();
            appuser.setUsername(userName);
            appuser.setUserid(userId);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            appuser.setPassword(encoder.encode(passWord));
            appuser.setEnabled(false);
            appuser.setAccountNonLocked(true);

            AppUserDetails record = new AppUserDetails();
            record.setUserId(userId);
            record.setUserName(userName);
            record.setTelephone(telephone);
            record.setIdCardNo(idCardNo);
            record.setCompanyName(companyName);
            record.setCompanyLicenseNo(companyLicenseNo);
            record.setInputTime(DateHelper.currentTime());
            record.setInputUserId(UserUtils.getCurrentUser().getUserid());
            record.setGrantedCode(grantedCode);
            if (appUserDetailsMapper.insert(record) == 1 && userMapper.insert(appuser) == 1) {
                return 1;
            } else
                throw new DMException("新增用户失败");
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new DMException(e);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public int deleteUser(String userid){
        try {
            if(userMapper.deleteByPrimaryKey(userid)==1 && appUserDetailsMapper.deleteByPrimaryKey(userid)==1)
                return 1;
            else
                throw new DMException("删除用户失败");
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new DMException(e);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public int updateUserPassWord(String userid, String oldPassWord, String newPassWord){
        AppUser appuser =  userMapper.selectByPrimaryKey(userid);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(appuser==null || appuser.getPassword()==null)
            return 0;
        if(!encoder.matches(oldPassWord,appuser.getPassword()))
            return -1;
        appuser.setPassword(encoder.encode(newPassWord));
        return userMapper.updateByPrimaryKeySelective(appuser);
    }

    public int resetUserPassWord(String userid,String newPassWord){
        AppUser appuser =  userMapper.selectByPrimaryKey(userid);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(appuser==null || appuser.getPassword()==null)
            return 0;
        appuser.setPassword(encoder.encode(newPassWord));
        return userMapper.updateByPrimaryKeySelective(appuser);
    }
    public int updateUserDetail(String userid,String userName,String idCardNo,String telephone,String companyName,String companyLicenseNo,String grantedCode){
        AppUser appuser =  userMapper.selectByPrimaryKey(userid);
        AppUserDetails record = appUserDetailsMapper.selectByPrimaryKey(userid);

        appuser.setUsername(userName);
        record.setUserName(userName);
        record.setTelephone(telephone);
        record.setIdCardNo(idCardNo);
        record.setCompanyName(companyName);
        record.setCompanyLicenseNo(companyLicenseNo);
        record.setGrantedCode(grantedCode);
        try{
            if(userMapper.updateByPrimaryKeySelective(appuser)==1 && appUserDetailsMapper.updateByPrimaryKeySelective(record)==1){
                return 1;
            }else
                throw new DMException("更新用户失败失败");
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new DMException(e);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public JSONObject selectByPrimaryKey(String userid){
        AppUser appUser = userMapper.selectByPrimaryKey(userid);
        AppUserDetails appUserDetails = appUserDetailsMapper.selectByPrimaryKey(userid);
        if(appUser==null)

            return null;

        appUser.setPassword("");
        JSONObject appUserJ= JsonSimpleUtil.toJSONObject(appUser);
        JSONObject appUserDetailsJ=JsonSimpleUtil.toJSONObject(appUserDetails);
        return JsonSimpleUtil.combineJson(appUserJ,appUserDetailsJ);
    }

    public List<AppUser> getAllUsers(){
        List<AppUser> appUsersList = userMapper.getAll();
        if(appUsersList!=null){
            for(AppUser appUser : appUsersList){
                appUser.setPassword("");
            }
        }
        return appUsersList;
    }
    /**
     *
     * @param userid  登陆时的用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        AppUser user = userMapper.selectByPrimaryKey(userid);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return user;
    }
}
