import com.binance.pool.MiningPoolBigdataApplication;
import com.binance.pool.service.util.ApiUtil;
import com.binance.pool.service.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.TreeMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MiningPoolBigdataApplication.class)
@Slf4j
public class PoolReportTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception{
        //MockMvcBuilders.webAppContextSetup(WebApplicationContext context)：指定WebApplicationContext，将会从该上下文获取相应的控制器并得到相应的MockMvc；
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }

    @Test
    public void testSignProfit() throws Exception{


        Map<String, String> map = new TreeMap<>();

        map.put(Constants.APP_ID,"d9ed812e26884e60b18fa2c0b31dc40d");
        map.put(Constants.APP_NAME,"savings");
        map.put(Constants.TIMESTAMP,String.valueOf( System.currentTimeMillis()));
        map.put("day","20200628");


        Map<String,String> stringMap = ApiUtil.getMyComUrl("/mining-api/v1/private/pool/finance/gun",map);

        String requestUrl = stringMap.get("url");
        String headerSign = stringMap.get(Constants.SIGN);
        System.out.println("请求的接口 :"+requestUrl);
        System.out.println(" header:"+headerSign);
        MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get(requestUrl).header(Constants.SIGN,headerSign)
                .accept(MediaType.APPLICATION_STREAM_JSON_VALUE))
                // .andExpect(MockMvcResultMatchers.status().isOk())             //等同于Assert.assertEquals(200,status);
                // .andExpect(MockMvcResultMatchers.content().string("hello lvgang"))    //等同于 Assert.assertEquals("hello lvgang",content);
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status=mvcResult.getResponse().getStatus();                 //得到返回代码
        String content=mvcResult.getResponse().getContentAsString();    //得到返回结果
        System.out.println(content);


    }
    @Test
    public void testSign() throws Exception{


        Map<String, String> map = new TreeMap<>();

        map.put("app_id", "d9ed812e26884e60b18fa2c0b31dc40d");
        map.put("app_name", "savings");
        map.put("userId", "10000001");
        map.put("timestamp", "1606126683269");

        String sign = ApiUtil.getSign(map, "b1bb1b675d3c47b58d86aaa413ff474b");
        if(sign.equals("218gPRgNGTN2Lg7biEKT/jjU6GxvsAWea1/bUzxCJE0=")){
            System.out.println(true);
        }
        System.out.println(false);
        System.out.println(sign);
    }
}
