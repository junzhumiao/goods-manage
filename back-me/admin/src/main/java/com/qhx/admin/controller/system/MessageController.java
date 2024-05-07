package com.qhx.admin.controller.system;

import com.qhx.common.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-11
 */
@RestController
@RequestMapping("/message")
@Api("消息控制器暂定---")
public class MessageController extends BaseController
{
    //
    //@Autowired
    //private MessageService messageService;
    //
    //
    //@RequestMapping(path = "/getAll")
    //@ApiOperation("获取全部消息")
    //public PageResult getAll(MessQueryTo messQueryTo){
    //    Integer page = messQueryTo.getPage();
    //    Integer pageSize = messQueryTo.getPageSize();
    //    PageInfo pageInfo = startOrderPage(page, pageSize, () ->
    //    {
    //       messageService.getAllMes(messQueryTo);
    //    });
    //    return toAjax(pageInfo);
    //}
    //
    //
    //@RequestMapping(path = "/delete",method = RequestMethod.POST)
    //@ApiOperation("删除消息")
    //public AjaxResult delete(MessDeleteTo messDeleteTo){
    //    boolean end = messageService.deleteMes(messDeleteTo);
    //    return toAjax(end);
    //}
    //
    //
    //@RequestMapping(path = "/updateStatus")
    //@ApiOperation("修改消息状态(审核消息)")
    //public AjaxResult updateStatus(@RequestBody Message message){
    //    boolean end = messageService.updateStatus(message);
    //    return toAjax(end);
    //}
    //
    //
    //
    //


}
