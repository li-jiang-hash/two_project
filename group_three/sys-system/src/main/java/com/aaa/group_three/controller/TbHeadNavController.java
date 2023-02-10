package com.aaa.group_three.controller;



import com.aaa.entity.TbHeadNav;
import com.aaa.group_three.service.ITbHeadNavService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-14
 */
@RestController
@RequestMapping("/tb-head-nav")
//@CrossOrigin//跨域
public class TbHeadNavController {

    @Resource
    private ITbHeadNavService headNavService;


    // 查询所有
    @PostMapping
    public Result getAllEmp(PageInfo page, TbHeadNav headNav){
        Page page1 = headNavService.getPageData(page, headNav);
        return new Result(page1);
    }


//    /**
//     * 添加角色信息
//     * @param headNav
//     * @return
//     */
//    @PostMapping("insertEmp")
//    public Result addEmp(TbHeadNav headNav){
//        //saveOrUpdate  添加或修改
//        //role  对象有id的值的时候 修改
//        //id没有值的时候 添加
//        return new Result(headNavService.saveOrUpdate(headNav));
//    }

    /**
     * 首页导航栏
     * @return
     */
    @GetMapping("find")
    public Result find(){
        QueryWrapper<TbHeadNav> query = new QueryWrapper<>();
        query.eq("is_disable","0");
        return new Result<>(headNavService.list(query));
    }



    //删除
    @DeleteMapping("{headId}")
    public Result delheadNav(@PathVariable Integer headId){
        boolean b=headNavService.removeById(headId);
        return new Result(b);

    }


    //添加//修改
    @PostMapping("insertNavigation")
    public Result addheadNav(TbHeadNav headNav){
        boolean b=headNavService.saveOrUpdate(headNav);
        System.out.println("headNav的添加"+headNav);
        return new Result(b);
    }

    //查询添加表单的导航标题
    @GetMapping("navigationSearch")
    public Result getAll(){
        return new Result(headNavService.list());
    }


    /**
     * 修改状态
     */
    @PostMapping("updateDisable/{headId}/{isDisable}")
    public Result updBanner(@PathVariable Integer headId, @PathVariable String isDisable){
        TbHeadNav headNav=new TbHeadNav();
        headNav.setIsDisable(isDisable);
        headNav.setHeadId(headId);
        return new Result(headNavService.updateById(headNav));
    }

}

