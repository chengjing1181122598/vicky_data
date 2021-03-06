/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicky.modules.usermgr;

import com.vicky.common.utils.DealFile.WebFileUtils;
import com.vicky.common.utils.encodepwd.EncodePassword;
import com.vicky.modules.commentmgr.mapper.CommentFloorMapper;
import java.util.List;
import java.util.Map;
import net.rubyeye.xmemcached.MemcachedClient;
import org.apache.ibatis.session.RowBounds;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Vicky
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:web/WEB-INF/applicationContext.xml", 
    "file:web/WEB-INF/dispatcher-servlet.xml","file:web/WEB-INF/application_memcached.xml"})
public class UsermgrTest {

    @Autowired
    private CommentFloorMapper commentFloorMapper;
    @Autowired
    private MemcachedClient memcachedClient;

    public UsermgrTest() {
    }

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void test() throws Exception {
        Integer integer = this.memcachedClient.get("整数");
        System.out.println(integer);
        this.memcachedClient.set("整数", 36000, 20);
        integer = this.memcachedClient.get("整数");
        System.out.println(integer);
//        RowBounds rowBounds = new RowBounds(1, 10);
//        List<Map<String, Object>> maps = this.commentFloorMapper.getAll("1", rowBounds);
//        for (Map<String, Object> map : maps) {
//            System.out.println(map.get("createTime").getClass());
//        }
//        Long time1 = System.currentTimeMillis();
//                .param("username", "程景")
//                .param("password", "5201314liweiqi")
//                .param("email", "1181122598@qq.com")
//                .param("condition_GT_D_birthday", "1994-10-10")
//                .param("pageSize", "1")
//                .param("pageIndex", "2")
//                .param("order_property", "age")
//                .param("order_type", "asc")))
//                .andExpect(status().isOk()).andDo(print());
//        Long time2 = System.currentTimeMillis();
//        System.out.println(time2 - time1);
    }

}
