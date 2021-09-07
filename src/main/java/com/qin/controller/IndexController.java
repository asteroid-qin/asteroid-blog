package com.qin.controller;

import com.alibaba.druid.util.StringUtils;
import com.qin.entity.R;
import com.qin.service.BlogService;
import com.qin.vo.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @RequestMapping({"/","/index"})
    public String index(){
        // 跳转来到首页
        return "index";
    }

    /**
     * 分页返回所有博客数据
     * @param _start
     * @param _size
     * @param _categoryId
     * @return
     */
    @RequestMapping("/blogs")
    @ResponseBody
    public R getBlogs(@RequestParam("start")String _start,
                      @RequestParam("size")String _size,
                      @RequestParam("categoryId")String _categoryId){
        // 其中任何一个不是数字就返回no
        if(StringUtils.isNumber(_start) && StringUtils.isNumber(_size) && StringUtils.isNumber(_categoryId)){
            int start = Integer.parseInt(_start);
            int size = Integer.parseInt(_size);
            int categoryId = Integer.parseInt(_categoryId);

            List<BlogVO> blogs = blogService.getIndexPageBlogVOs(categoryId, start, size);

            return R.ok("日志查询成功！", blogs);
        }

        return R.no("前端发送的数据错误！", null);
    }
}
