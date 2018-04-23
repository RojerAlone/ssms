package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.mapper.MessageMapper;
import cn.edu.nwsuaf.cie.ssms.model.Message;
import cn.edu.nwsuaf.cie.ssms.service.MessageService;
import cn.edu.nwsuaf.cie.ssms.util.PageUtil;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangrenjie on 2018-04-23
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserHolder userHolder;

    @Override
    public Result addMessage(String title, String content) {
        Message message = new Message();
        message.setUid(userHolder.getUser().getUid());
        message.setTitle(title);
        message.setContent(content);
        int res = messageMapper.insert(message);
        if (res == 0) {
            LOGGER.error("fail to insert message, title : {}, content : {}", title, content);
            return Result.innerError();
        }
        return Result.success();
    }

    @Override
    public Result deleteMessage(int id) {
        messageMapper.updateStatById(Message.STAT_DEL, id);
        return Result.success();
    }

    @Override
    public Result getMessageById(int id) {
        Message message = messageMapper.selectById(id);
        if (message == null) {
            LOGGER.warn("getMessageById - message not exist, id : {}", id);
            return Result.errorParam();
        }
        return Result.success(message);
    }

    @Override
    public Result getMessageByPage(int page, int nums) {
        int[] pageInfo = PageUtil.getPage(page, nums);
        return Result.success(messageMapper.selectByStatAndPage(Message.STAT_OK, pageInfo[0], pageInfo[1]));
    }
}
