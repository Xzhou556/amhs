package amhs.amhs.entity.vo;

import org.springframework.stereotype.Component;

@Component
public class ResultGenerator extends  RestResult {
    private static final String SUCCESS = "success";
    RestResult restResult=new RestResult();

    //成功
    public RestResult getSuccessResult() {
        return new RestResult()
                .setCode(ResultCode.SUCCESS)
                .setMessage(SUCCESS);
    }

    //成功，附带额外数据
    public  RestResult getSuccessResult(Object data) {
        return new RestResult()
                .setCode(ResultCode.SUCCESS)
                .setMessage(SUCCESS)
                .setData(data);
    }
    //成功，自定义消息及数据
    public  RestResult getSuccessResult(String message,Object data) {
        return new RestResult()
                .setCode(ResultCode.SUCCESS)
                .setMessage(message)
                .setData(data);
    }
    //失败，附带消息
    public  RestResult getFailResult(String message) {
        return new RestResult()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
    //失败，自定义消息及数据
    public RestResult getFailResult(String message, Object data) {
        return new RestResult()
                .setCode(ResultCode.FAIL)
                .setMessage(message)
                .setData(data);
    }
    //自定义创建
    public RestResult getFreeResult(ResultCode code, String message, Object data) {
        return new RestResult()
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }

}
