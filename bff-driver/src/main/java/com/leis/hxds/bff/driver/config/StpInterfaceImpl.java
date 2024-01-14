package com.leis.hxds.bff.driver.config;

import cn.dev33.satoken.stp.StpInterface;

import java.util.ArrayList;
import java.util.List;

public class StpInterfaceImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object o, String s) {
        ArrayList<String> list = new ArrayList<>();
        return list;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        ArrayList<String> list = new ArrayList<>();
        return list;
    }
}
