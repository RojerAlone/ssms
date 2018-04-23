package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.util.Result;

/**
 * Created by zhangrenjie on 2018-04-23
 */
public interface MessageService {

    Result addMessage(String title, String content);

    Result deleteMessage(int id);

    Result getMessageById(int id);

    Result getMessageByPage(int page, int nums);

}
