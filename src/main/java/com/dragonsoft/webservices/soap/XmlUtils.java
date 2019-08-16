package com.dragonsoft.webservices.soap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.logging.Logger;

/**
 * Xml解析工具类
 * @author ronin
 * @version V1.0
 * @since 2019/8/13 9:17
 */
public class XmlUtils {
    /**私有化工具类的构造方法*/
    private XmlUtils() {
        throw new UnsupportedOperationException();
    }

    /**用于记录日志*/
    private static Logger logger = Logger.getLogger("XmlUtils.class");

    /**代表整个文档对象*/
    private static Document document;

    /**
     * 把一段请求报文转换成一个文档对象,并注册资源
     * @param src soap协议格式的请求报文
     */
    private static void transform(String src) {
        try {
            logger.info("原始的请求报文:\n"+src);
            document = DocumentHelper.parseText(src);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式化xml文本
     * @param xmlText
     * @return 返回格式化的soap协议格式的xml文本
     */
    public static String transformXmlToFormatText(String xmlText){
        XMLWriter writer = null;
        StringWriter stringWriter = null;
        //格式化后的xml文本
        String formatXmlText = null;
        try {
            Document document = DocumentHelper.parseText(xmlText);
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(document.getXMLEncoding());
            //删除xml头
            format.setSuppressDeclaration(true);
            format.setEncoding("utf-8");
            stringWriter = new StringWriter();
            writer = new XMLWriter(stringWriter, format);
            writer.write(document);
            formatXmlText = stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (DocumentException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }finally {
            try {
                if(null != writer) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return formatXmlText;
    }

    /**
     * 获取根节点
     * @return 返回该xml对应的Document对象的根节点
     */
    public static Element getRootElement(){
        return document.getRootElement();
    }


    /**
     * 将参数设置到请求报文中
     * @param soapRequestMessage 获取到的原始的请求报文
     * @param targetMethodName webservices发布方提供的接口的方法名
     * @param requestMethodParams webservices发布方提供的接口的方法需要的参数
     * @return 返回值为设置好参数的请求报文
     */
    public static String setMethodParamValue(String soapRequestMessage,String targetMethodName, Map<String,String> requestMethodParams){
        //把原始的请求报文转换为一个Document
        transform(soapRequestMessage);
        //获取body节点
        Element soapBody = getRootElement().element("Body");
        //获取body节点下指定节点
        Element methodNode = soapBody.element(targetMethodName);
        //如果通过方法名去查找指定的节点，查询不到，说明webservices中没有提供该方法,程序终止执行
        if(null == methodNode){
            throw new IllegalArgumentException("无法根据方法名"+targetMethodName+"查询到对应的服务");
        }
        //获取该方法下所有的子节点，每一个子节点代表一个参数
        List<Element> paramsNodes = methodNode.elements();
        int webservicesParamCount = 0;
        for (Element element : paramsNodes) {
            //webservices服务中提供的形参名称
            String serviceParamName = element.getName();
            if(requestMethodParams.containsKey(serviceParamName)){
                element.setText(requestMethodParams.get(serviceParamName));
                webservicesParamCount++;
            }
        }
        logger.info("webservices提供的服务中,方法methodName需要"+webservicesParamCount+"个参数,实际传入了:"+requestMethodParams.size()+"个参数详情如下:");
        Set<String> keys = requestMethodParams.keySet();
        Iterator<String> iterator = keys.iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            String value = requestMethodParams.get(key);
            logger.info("传入的参数，key:"+key+",value:"+value);
        }
        //替换过参数后的请求报文
        String dealedSoapRequestMessage = transformXmlToFormatText(document.asXML());
        logger.info("替换过参数后的请求报文:"+dealedSoapRequestMessage);
        return dealedSoapRequestMessage;
    }
}
