package com.yaxin.voice253;

import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSONObject;

/**
 * ����֪ͨ�ӿڵ���ʾ��
 * ��������֪ͨ�ӿ����ñ��к����������Ϣ���ݣ������ûز��ӿڣ������óɹ����������к��뷢������֪ͨ
 */
public class VoiceNotify
{
	//��Ҫ����ĵ绰����Ŀ����
			public static String  voiceNotify(String callingline,String xm){
				
				String url="http://audio.253.com";
				String company="YM7107718";
				String passwd="kXb4meniOs983b";
				String k="7f97921a5fb145a28c7f70a3291bd2d3";
				
				String teltemp="105042";//����ģ��id 
				String telno="95213176"; //ȥ���Ժ�
				//String callingline="13607483030";
				String sex="2";//1����  2Ů�� 3�Զ���  
				String contextparm="name:"+xm+",name:"+xm+",name:"+xm; //ģ�������б��������ޱ������� Ĭ�Ϸ���ģ������
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String keytime = sdf.format(new Date()); //��ǰʱ���
				String key=MD5(k+passwd+keytime);
				
				JSONObject params = new JSONObject();
				JSONObject data = new JSONObject();
				data.put("contextparm", contextparm);
				data.put("company", company); 
				data.put("teltemp", teltemp);	
				data.put("telno",telno);		 	
				data.put("callingline", callingline);
				data.put("key", key);
				data.put("sex",sex);	
				data.put("requestid", "2");	
				data.put("keytime", keytime);
				data.put("ivr", null);
				
				params.put("userinfo", data);
				String rs = "";
				try {
					 rs=sendMsg(url,params.toString());
					// System.out.println("��Ӧֵ:\n"+rs);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return rs;
			}
			
			public static String sendMsg(String url,String json)throws Exception {
				HttpClient client = new HttpClient();
				PostMethod method = new PostMethod();
				try {
					URI base = new URI(url, false);
					method.setURI(new URI(base, "/noticeapi/noticeapi_api", false));
					method.setRequestBody(new NameValuePair[] {
							new NameValuePair("userinfo", json)
							});
					HttpMethodParams params = new HttpMethodParams();
					params.setContentCharset("UTF-8");
					method.setParams(params);
					int result = client.executeMethod(method);
					if (result == HttpStatus.SC_OK) {
						byte[] ba = method.getResponseBody();
						method.getResponseBodyAsStream();
						String str= new String(ba,"UTF-8");
						return URLDecoder.decode(str, "UTF-8");
					} else {
						throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + new String(method.getStatusText().getBytes("GBK"),"UTF-8"));
					}
				} finally {
					method.releaseConnection();
				}
			}
			
			/**
			 * MD5����
			 * @param decript
			 * @return
			 */
			public static String MD5(String decript) {
				try {
					MessageDigest digest = MessageDigest.getInstance("MD5");
					digest.update(decript.getBytes());
					byte messageDigest[] = digest.digest();
					StringBuffer hexString = new StringBuffer();
					for (int i = 0; i < messageDigest.length; i++) {
						String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
						if (shaHex.length() < 2) {
							hexString.append(0);
						}
						hexString.append(shaHex);
					}
					return hexString.toString();
				} catch (NoSuchAlgorithmException e) {
					return "";
				}
			}
		
}
