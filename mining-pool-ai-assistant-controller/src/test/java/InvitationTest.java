import com.alibaba.fastjson.JSON;
import com.binance.pool.MiningPoolBigdataApplication;
import com.binance.pool.base.bean.PoolUserInvitationUpdateBean;

import com.binance.pool.dao.pool.PoolUserInvitationUpdateDao;
import com.binance.pool.service.pool.InvitationService;
import com.binance.pool.service.vo.ResultBean;
import com.binance.pool.service.vo.invitation.InvitationRecordArg;
import com.binance.pool.service.vo.invitation.InvitationRecordRet;
import com.binance.pool.service.vo.invitation.RemoveInvitationArg;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.annotation.Resource;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MiningPoolBigdataApplication.class)
@Slf4j
public class InvitationTest {

    @Resource
    PoolUserInvitationUpdateDao poolUserRemoveInvitationDao;

    @Resource
    InvitationService invitationService;

    @Test
    public void testInsert(){
        PoolUserInvitationUpdateBean bean = new PoolUserInvitationUpdateBean();
        bean.setUidBinance(2L);
        bean.setUidBinanceInvitation(0L);
        bean.setStatus(0);
      Integer result =  poolUserRemoveInvitationDao.saveRemoveInvitation(bean);
      log.info(" 结果是："+result);
    }

    @Test
    public void invitation(){


        RemoveInvitationArg arg = new RemoveInvitationArg();
        arg.setUidBinanceInvitation(350814042L);
        arg.setRequestType(0);
        arg.setUidBinance(350609740L);
        ResultBean resultBean = invitationService.insertPoolUserRemoveInvitation(arg);
        log.info(" 结果是："+ JSON.toJSONString(resultBean));
    }
    @Test
    public void recordJson(){

        InvitationRecordArg arg = new InvitationRecordArg();
        arg.setAlgoId(1L);
        arg.setUidBinance(100102751640L);
        arg.setStartDate(123L);
        arg.setEndDate(1761881921259L);
        List<InvitationRecordRet> list = invitationService.recordJson(100102751640L,arg);
        log.info("xx");
    }
}
