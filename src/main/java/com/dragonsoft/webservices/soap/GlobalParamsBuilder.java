package com.dragonsoft.webservices.soap;

import java.util.Map;

/**
 * <pre>
 * 全局参数构建者:
 *      1.预处理参数
 *      2.构建全局参数
 *      3.提供外部访问接口
 * 何时被调用:
 *      1.全局参数在请求报文开始构建的时候，经过预处理过后，保存此全局参数构建者中
 *      2.具体的请求报文构建者
 *          执行构建请求报文操作时用到
 *              {@link GlobalParamsBuilder#requestSource(String)}
 *              {@link GlobalParamsBuilder#targetMethodName(String)}
 *              {@link GlobalParamsBuilder#soapMessage(String)}
 *              eg:{@link BasicSoapResponseBuilder#buildSoapRequestMessage()}
 *          执行构建替换请求报文参数时用到
 *              {@link GlobalParamsBuilder#soapMessage(String)}
 *              {@link GlobalParamsBuilder#targetMethodName(String)}
 *              {@link GlobalParamsBuilder#requestMethodParams(Map)}
 *              eg:{@link BasicSoapResponseBuilder#buildDealedSoapRequestMessage()}
 *          执行构建响应报文时用到
 *              {@link GlobalParamsBuilder#requestSource(String)}
 *              {@link GlobalParamsBuilder#soapMessage(String)}
 *              eg:{@link BasicSoapResponseBuilder#buildSoapResponseMessage()}
 * 使用注意事项:
 *      1.此全局参数构建者不支持setter()方式设置内部变量的值,只支持如下方式(支持链式风格):
 *          eg:
 *              GlobalParamsBuilder globalParamsBuilder = new GlobalParamsBuilder();
 *              globalParamsBuilder.requestSource(requestSource).targetMethodName(methodName);
 *      2.获取内部变量的值使用常规的getter()方法即可
 * <pre/>
 * @author ronin
 * @version V1.0
 * @since 2019/8/12 16:16
 */
public class GlobalParamsBuilder {

    /**无参构造*/
    public GlobalParamsBuilder() {}

    /**
     * 和当前线程绑定的 soap协议请求报文或者响应报文:
     *  在没发Soap请求之前，这个变量代表请求报文
     *  在发送Soap请求之后这个变量代表响应报文
     */
    private ThreadLocal<String> soapMessageThreadLocal = new ThreadLocal<String>();

    /**和当前线程绑定的 请求源:webservice服务发布方提供的WSDL或者wsdl格式的文本*/
    private ThreadLocal<String> requestSourceThreadLocal = new ThreadLocal<String>();

    /**和当前线程绑定的 方法名称*/
    private ThreadLocal<String> targetMethodNameThreadLocal = new ThreadLocal<String>();

    /**和当前线程绑定的 目标方法需要的参数*/
    private ThreadLocal<Map<String,String>> requestMethodParamsThreadLocal = new ThreadLocal<Map<String,String>>();

    /**
     * 全局参数soapMessage外部访问接口
     */
    public String getSoapMessage() {
        return soapMessageThreadLocal.get();
    }

    /**
     * 全局参数requestSource外部访问接口
     */
    public String getRequestSource() {
        return requestSourceThreadLocal.get();
    }

    /**
     * 全局参数methodName外部访问接口
     */
    public String getTargetMethodName() {
        return targetMethodNameThreadLocal.get();
    }

    /**
     * 全局参数requestMethodParams外部访问接口
     */
    public Map<String, String> getRequestMethodParams() {
        return requestMethodParamsThreadLocal.get();
    }

    /**
     * 预处理soapMessage(请求/响应报文)这个参数并将其和当前线程绑定,此处预处理操作为去左右空格
     * @param soapMessage 请求/响应报文
     * @return 内部构建者
     */
    public GlobalParamsBuilder soapMessage(String soapMessage) {
        this.soapMessageThreadLocal.set(soapMessage.trim());
        return this;
    }

    /**
     * 预处理requestSource(请求源)这个参数并将其和当前线程绑定,此处预处理操作为去左右空格
     * @param requestSource 请求源：wsdl链接地址或soap格式的请求报文
     * @return 内部构建者
     */
    public GlobalParamsBuilder requestSource(String requestSource) {
        this.requestSourceThreadLocal.set(requestSource);
        return this;
    }

    /**
     * 预处理methodName(要调用的方法的名称)并将其和当前线程绑定,此处预处理操作为去左右空格
     * @param methodName 要调用的方法的名称
     * @return 内部构建者
     */
    public GlobalParamsBuilder targetMethodName(String methodName) {
        this.targetMethodNameThreadLocal.set(methodName.trim());
        return this;
    }

    /**
     * 把requestMethodParams(要调用的方法需要的参数)和当前线程绑定
     * @param requestMethodParams 要调用的方法需要的参数
     * @return 内部构建者
     */
    public GlobalParamsBuilder requestMethodParams(Map<String,String> requestMethodParams) {
        this.requestMethodParamsThreadLocal.set(requestMethodParams);
        return this;
    }

    /**
     * 回收ThreadLocal占用的内存防止内存泄漏和内存溢出
     */
    public void garbageCollection(){
        soapMessageThreadLocal.remove();
        requestSourceThreadLocal.remove();
        targetMethodNameThreadLocal.remove();
        requestMethodParamsThreadLocal.remove();
    }

    @Override
    public String toString() {
        return "GlobalParamsBuilder{" +
                "soapMessage='" + soapMessageThreadLocal.get() + '\'' +
                ", requestSource='" + requestSourceThreadLocal.get() + '\'' +
                ", methodName='" + targetMethodNameThreadLocal.get() + '\'' +
                ", requestMethodParams=" + requestMethodParamsThreadLocal.get() +
                '}';
    }
}
