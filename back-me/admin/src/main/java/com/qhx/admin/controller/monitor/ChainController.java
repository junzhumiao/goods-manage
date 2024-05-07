package com.qhx.admin.controller.monitor;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qhx.common.model.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jzm
 * @date: 2024-03-28 09:17
 **/

@RestController
@RequestMapping("/monitor/chain")
@Api("区块链监控") // TODO 抽时间替换fisco-java-sdk
public class ChainController
{

    private String  urlPre;

    @Value("${fisco.url}")
    public void setUrlPre(String url){
        this.urlPre = url + ":5002/WeBASE-Front/1/web3";
    }

    @RequestMapping(value = "/getNodeList",method = RequestMethod.GET)
    @ApiOperation("获取节点信息")
    public AjaxResult getNodeStatusList(){
        String body = HttpUtil.createGet(urlPre + "/getNodeStatusList").execute().body();
        JSONArray jsonAry = JSONUtil.parseArray(body);
        return AjaxResult.success(jsonAry);
    }

    @RequestMapping(value = "/getTxTotal",method = RequestMethod.GET)
    @ApiOperation("获取tx数量")
    public AjaxResult getTransactionTotal(){
        // 交易数量、区块数量
        String body = HttpUtil.createGet(urlPre + "/transaction-total").execute().body();
        JSONObject resObj = JSONUtil.parseObj(body);
        // 节点数量
        JSONArray nodeAry = JSONUtil.parseArray(HttpUtil.createGet(urlPre + "/groupPeers").execute().body());
        resObj.set("nodeTotal",nodeAry.size());
        // 待交易数量
        String pendTxTotal = HttpUtil.createGet(urlPre + "/pending-transactions-count").execute().body();
        resObj.set("pendTxSum",pendTxTotal);
        return AjaxResult.success(resObj);
    }


    @RequestMapping(value = "/searchBlock",method = RequestMethod.GET)
    @ApiOperation("查询区块详细信息")
    public AjaxResult searchBlock( String blockId){
        String body = HttpUtil.createGet(urlPre + "/search?input="+blockId).execute().body();
        JSONObject jsonObj = JSONUtil.parseObj(body);
        return AjaxResult.success(jsonObj);
    }
}
