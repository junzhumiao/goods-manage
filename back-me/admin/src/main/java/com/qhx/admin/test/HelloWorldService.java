package com.qhx.admin.test;


import com.qhx.admin.config.fisco.service.ContractService;
import org.fisco.bcos.sdk.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: jzm
 * @date: 2024-03-22 20:46
 **/

@Service
public class HelloWorldService extends ContractService
{

     // 一开始加载地址
    private  String ABI =  "[{\"constant\":true,\"inputs\":[],\"name\":\"age\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getAge\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_age\",\"type\":\"uint256\"}],\"name\":\"setAge\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";

    private String contractAddress = "0x172f6ad77e759705144f9ed1d6817275bc4bced8";

    private String  BIN =   "608060405234801561001057600080fd5b5061019c806100206000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063262a9dff1461005c578063967e6e6514610087578063d5dcf127146100b2575b600080fd5b34801561006857600080fd5b506100716100df565b6040518082815260200191505060405180910390f35b34801561009357600080fd5b5061009c6100e5565b6040518082815260200191505060405180910390f35b3480156100be57600080fd5b506100dd600480360381019080803590602001909291905050506100ee565b005b60005481565b60008054905090565b600a81111515610166576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f616765206973206d757374206265203e2031300000000000000000000000000081525060200191505060405180910390fd5b80600081905550505600a165627a7a72305820acdcc78cd997e0bb157107011ce344823efd6f695e51d4e01480b1eaa35d628d0029";

    @Autowired
    public HelloWorldService(Client client){
        super.client(client);
        super.initConfig(ABI,BIN,contractAddress);
    }

}
