package com.leis.hxds.vhr.db.dao;


import com.leis.hxds.vhr.db.pojo.VoucherEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface VoucherDao {
    public int insert(VoucherEntity entity);

    public ArrayList<String> searchIdByUUID(ArrayList<String> list);

    ArrayList<HashMap> searchVoucherByPage(Map param);

    long searchVoucherCount(Map param);

    HashMap searchVoucherById(long id);

    int updateVoucherStatus(Map param);

    ArrayList<HashMap> searchVoucherTakeCount(Long[] ids);

    int deleteVoucherByIds(Long[] ids);

    ArrayList<HashMap> searchUnTakeVoucherByPage(Map param);

    long searchUnTakeVoucherCount(Map param);
    ArrayList<HashMap> searchUnUseVoucherByPage(Map param);

    long searchUnUseVoucherCount(Map param);
    ArrayList<HashMap> searchUsedVoucherByPage(Map param);

    long searchUsedVoucherCount(Map param);

}




