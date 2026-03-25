import com.alibaba.fastjson.JSON;
import com.binance.pool.MiningPoolBigdataApplication;
import com.binance.pool.service.pool.PoolSavingsService;
import com.binance.pool.service.vo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.annotation.Resource;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MiningPoolBigdataApplication.class)
@Slf4j
public class PoolSavingsControllerTest {

    @Resource
    PoolSavingsService poolSavingsService;

    @Test
    public void pendingAmountDetailTest(){
        ResultBean resultBean =  poolSavingsService.pendingAmountDetail(350758536L,1L);
        log.info("pendingAmountDetailTest :{}", JSON.toJSONString(resultBean));
    }
}
