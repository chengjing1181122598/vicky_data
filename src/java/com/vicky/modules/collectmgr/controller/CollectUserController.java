/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicky.modules.collectmgr.controller;

import com.vicky.common.controller.MyEntityController;
import com.vicky.common.utils.page.Page;
import com.vicky.common.utils.service.BaseService;
import com.vicky.common.utils.statusmsg.StatusMsg;
import com.vicky.modules.collectmgr.entity.CollectUser;
import com.vicky.modules.collectmgr.service.CollectUserService;
import com.vicky.modules.usermgr.service.UserService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping("collectUser")
public class CollectUserController extends MyEntityController<CollectUser, String> {

    @Autowired
    private CollectUserService collectUserService;
    @Autowired
    private UserService userService;

    @Override
    protected BaseService<CollectUser, String> getBaseService() {
        return this.collectUserService;
    }

    @RequestMapping("isCollected")
    @ResponseBody
    public StatusMsg isCollected(String username) {
        CollectUser collectUser = new CollectUser();
        collectUser.setCollectingUsername(super.getUser().getUsername());
        collectUser.setCollectedUsername(username);
        CollectUser r = this.collectUserService.selectOne(collectUser);
        if (r != null) {
            return super.simpleBuildSuccessMsg("yes");
        } else {
            return super.simpleBuildSuccessMsg("no");
        }
    }

    @RequestMapping("getList")
    @ResponseBody
    public Map<String, Object> getList(String username, Integer pageIndex, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        int index = Page.DEFAULT_PAGE_INDEX;
        int limit = Page.DEFAULT_PAGE_SIZE;
        if (pageIndex != null) {
            index = pageIndex;
        }
        if (pageSize != null) {
            limit = pageSize;
        }
        RowBounds rowBounds = new RowBounds((index - 1) * limit, limit);
        int total = this.collectUserService.getListCount(username);
        List<Map<String, Object>> maps = this.collectUserService.getList(username, rowBounds);
        map.put(Page.TOTAL_KEY, total);
        map.put(Page.DATA_KEY, maps);
        return map;
    }

    @RequestMapping("uncollect")
    @ResponseBody
    public StatusMsg uncollect(HttpServletRequest request, HttpServletResponse response, String collectedUsername) throws Exception {
        CollectUser c = new CollectUser();
        c.setCollectedUsername(collectedUsername);
        c.setCollectingUsername(super.getUser().getUsername());
        CollectUser collectUser = this.collectUserService.selectOne(c);
        if (!collectUser.getCollectingUsername().equals(super.getUser().getUsername())) {
            return super.simpleBuildErrorMsg("权限不够");
        }
        this.collectUserService.delete(collectUser);
        return super.simpleBuildSuccessMsg("取消关注成功", collectUser);
    }

    @RequestMapping("collect")
    @ResponseBody
    public StatusMsg collect(HttpServletRequest request, HttpServletResponse response, CollectUser t) throws Exception {
        if (this.userService.selectByPrimaryKey(t.getCollectedUsername()) == null) {
            return super.simpleBuildErrorMsg("被关注的用户名有误");
        }
        t.setCollectingUsername(super.getUser().getUsername());
        t.setCreateTime(new Date());
        return super.save(request, response, t); //To change body of generated methods, choose Tools | Templates.
    }

}
