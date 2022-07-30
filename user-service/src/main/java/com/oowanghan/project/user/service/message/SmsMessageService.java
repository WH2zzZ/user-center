package com.oowanghan.project.user.service.message;

import com.oowanghan.atlantis.framework.common.exception.BizErrorCode;
import com.oowanghan.atlantis.framework.common.exception.BizException;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 短信服务
 *
 * @Author WangHan
 * @Create 2021/3/4 5:28 下午
 */
@Slf4j
@Service
public class SmsMessageService {

    public static final String REGION = "ap-nanjing";
    public static final String MOBILE_PREFIX = "+86";
    @Value("${sms.accessKeyId}")
    private String accessKeyId;

    @Value("${sms.accessKeySecret}")
    private String accessKeySecret;

    @Value("${sms.appId}")
    private String appId;

    @Value("${sms.templateId}")
    private String templateId;

    @Value("${sms.signatureName}")
    private String signatureName;

    public void sendLoginSms(String mobile, String code) {

        log.debug("Send SMS  to '{}' and content={}", mobile, code);

        SmsClient client = getSmsClient();
        SendSmsRequest req = getSendSmsRequest(mobile);

        /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
         * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
        SendSmsResponse res = null;
        try {
            res = client.SendSms(req);
        } catch (TencentCloudSDKException e) {
            throw new BizException(BizErrorCode.SEND_SMS_ERROR);
        }
        if (res.getSendStatusSet()[0].getCode() != "OK") {
            throw new BizException(BizErrorCode.SEND_SMS_ERROR);
        }
    }

    @NotNull
    private SendSmsRequest getSendSmsRequest(String mobile) {
        SendSmsRequest req = new SendSmsRequest();
        // 填充请求参数,这里request对象的成员变量即对应接口的入参

        /* 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId，示例如1400006666 */
        // 应用 ID 可前往 [短信控制台](https://console.cloud.tencent.com/smsv2/app-manage) 查看
        req.setSmsSdkAppId(appId);

        /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名 */
        // 签名信息可前往 [国内短信](https://console.cloud.tencent.com/smsv2/csms-sign) 或 [国际/港澳台短信](https://console.cloud.tencent.com/smsv2/isms-sign) 的签名管理查看
        req.setSignName(signatureName);

        /* 模板 ID: 必须填写已审核通过的模板 ID */
        // 模板 ID 可前往 [国内短信](https://console.cloud.tencent.com/smsv2/csms-template) 或 [国际/港澳台短信](https://console.cloud.tencent.com/smsv2/isms-template) 的正文模板管理查看
        req.setTemplateId(templateId);
        req.setPhoneNumberSet(new String[]{MOBILE_PREFIX + mobile});
        return req;
    }

    @NotNull
    private SmsClient getSmsClient() {
        /* 必要步骤：
         * 实例化一个认证对象，入参需要传入腾讯云账户密钥对secretId，secretKey。
         * 这里采用的是从环境变量读取的方式，需要在环境变量中先设置这两个值。
         * 你也可以直接在代码中写死密钥对，但是小心不要将代码复制、上传或者分享给他人，
         * 以免泄露密钥对危及你的财产安全。
         * SecretId、SecretKey 查询: https://console.cloud.tencent.com/cam/capi */
        Credential cred = new Credential(accessKeyId, accessKeySecret);

        /* 非必要步骤:
         * 实例化一个客户端配置对象，可以指定超时时间等配置 */
        ClientProfile clientProfile = new ClientProfile();
        /* 实例化要请求产品(以sms为例)的client对象
         * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，支持的地域列表参考 https://cloud.tencent.com/document/api/382/52071#.E5.9C.B0.E5.9F.9F.E5.88.97.E8.A1.A8 */
        SmsClient client = new SmsClient(cred, REGION, clientProfile);
        return client;
    }

}
