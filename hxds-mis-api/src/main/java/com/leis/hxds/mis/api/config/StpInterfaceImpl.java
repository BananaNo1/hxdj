package com.leis.hxds.mis.api.config;

import cn.dev33.satoken.stp.StpInterface;
import com.leis.hxds.mis.api.db.dao.UserDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private UserDao userDao;

    @Override
    public List<String> getPermissionList(Object loginId, String s) {
        int userId = Integer.parseInt(loginId.toString());
        Set<String> permissions = userDao.searchUserPermissions(userId);
        ArrayList list = new ArrayList();
        list.addAll(permissions);
        return list;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        ArrayList list = new ArrayList();
        return list;
    }
}
