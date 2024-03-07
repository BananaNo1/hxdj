package com.leis.hxds.snm.service;

import com.leis.hxds.snm.db.pojo.MessageEntity;
import com.leis.hxds.snm.db.pojo.MessageRefEntity;

import java.util.HashMap;
import java.util.List;

public interface MessageService {

    String insertMessage(MessageEntity entity);

    List<HashMap> searchMessageByPage(long userId,String identity,long start,int length);

    HashMap searchMessageById(String id);

    String insertRef(MessageRefEntity entity);

    long searchUnreadCount(long userId, String identity);

    long searchLastCount(long userId, String identity);

    long updateUnreadMessage(String id);

    long deleteMessageRefById(String id);

    long deleteUserMessageRef(long userId, String identity);
}
