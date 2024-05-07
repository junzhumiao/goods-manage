package com.qhx.admin.config.fisco.contract;

import cn.hutool.json.JSONArray;
import lombok.Data;

@Data
public class ContractResponse
{
    private JSONArray vals;

    private String mes; // 响应消息
}
