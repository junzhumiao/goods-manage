package com.qhx.admin.controller.monitor;

import com.qhx.admin.domain.web.Server;
import com.qhx.common.model.AjaxResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jzm
 * @date: 2024-03-20 08:42
 **/

@RestController
@RequestMapping("/monitor/server")
public class ServerController
{
   @RequestMapping(method = RequestMethod.GET)
    public AjaxResult getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }
}
