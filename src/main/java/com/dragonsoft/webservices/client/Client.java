package com.dragonsoft.webservices.client;

import com.dragonsoft.webservices.soap.SoapUtils;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/8/12 15:06
 */
public class Client {

   /**
    * 远程调用的方法不包含参数
    */
   @Test
   public void fun1(){
       String wsdlUrl = "http://www.webxml.com.cn/webservices/ChinaTVprogramWebService.asmx?wsdl";
       String methodName = "getAreaDataSet";
       String result =  SoapUtils.getSoapResponseMessageByUrl(wsdlUrl, methodName);
       System.out.println(result);
   }

   /**
    * 远程调用的方法包含参数
    */
   @Test
   public void fun2(){
       String wsdlUrl = "http://www.webxml.com.cn/WebServices/TranslatorWebService.asmx?wsdl";
       String methodName = "getEnCnTwoWayTranslator";
       HashMap<String,String> hashMap = new HashMap<String,String>();
       hashMap.put("Word","hello");
       String result =  SoapUtils.getSoapResponseMessageByUrl(wsdlUrl, methodName,hashMap);
       System.out.println(result);
   }

   /**
    * 远程调用的方法包含参数
    */
   @Test
   public void fun3(){
       String wsdlUrl =
               "<soapenv:Envelope\n" +
               "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
               "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
               "    xmlns:soapenv=\"http://schemas.xmlsoap.org/webservices/envelope/\"\n" +
               "    xmlns:sjl=\"http://sjlwcx.za.Sjjh.zjjcnt.com\">\n" +
               "    <soapenv:Header/>\n" +
               "    <soapenv:Body>\n" +
               "        <sjl:ZAGL_RKXX_HJGLJBXXCX soapenv:encodingStyle=\"http://schemas.xmlsoap.org/webservices/encoding/\">\n" +
               "            <license xsi:type=\"xsd:string\">\n" +
               "                <![CDATA[\n" +
               "                <?xml version=\"1.0\" encoding=\"UTF-8\"?><LICENSE id=\"3415\"><USERINFO><ORG_CODE>000000000001</ORG_CODE><ORG_NAME>测试</ORG_NAME><ACCOUNT_ID>测试</ACCOUNT_ID><PASSWORD>b9b0ec5e6872c2237692f22dd05240</PASSWORD><IP>10.1.6.191</IP><BEGIN_DATE>20160323154603</BEGIN_DATE><END_DATE>20160423154603</END_DATE></USERINFO><SERVICEINFO><SERVICE><SERVICE_CODE>010101</SERVICE_CODE><SERVICE_NAME>户籍管理基本信息</SERVICE_NAME></SERVICE></SERVICEINFO><SIGNATURE>e8036842ca616b96f9c138f05ca4d1a401938e2c</SIGNATURE></LICENSE>]]>\n" +
               "            </license>\n" +
               "            <information xsi:type=\"xsd:string\">\n" +
               "                <![CDATA[\n" +
               "                <?xml version=\"1.0\" encoding=\"UTF-8\" ?><CONDITIONS><CONDITION exp=\"\" sid=\"010101\" result=\"\" no=\"1\"><GMSFHM operation=\"=\">612323199305256312</GMSFHM><TYBZ operation=\"=\">0</TYBZ><ZT operation=\"=\">0</ZT><ZDFHJLS operation=\"=\">20</ZDFHJLS></CONDITION></CONDITIONS>]]>\n" +
               "            </information>\n" +
               "        </sjl:ZAGL_RKXX_HJGLJBXXCX>\n" +
               "    </soapenv:Body>\n" +
               "</soapenv:Envelope>";
       String methodName = "ZAGL_RKXX_HJGLJBXXCX";
       HashMap<String,String> hashMap = new HashMap<String,String>();
       hashMap.put("license","hello");
       String result =  SoapUtils.getSoapResponseMessageByText(wsdlUrl, methodName,hashMap);
       System.out.println(result);
   }

    /**
     * 调用OpenFire
     */
   @Test
   public void fun4(){
       String getAllOnlineUserInfo =
               SoapUtils.getSoapResponseMessageByUrl("http://20.20.30.152:8989/OpenfireWebService?wsdl", "getAllOnlineUserInfo");
   }
}
