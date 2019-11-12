package com.dc.util.http;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 发送http请求工具
 * @author 小俊俊
 *
 */
@Repository("httpUtil")
public class HttpUtil {

	private static final Logger logger = Logger.getLogger(HttpUtil.class);

	private String charset = "UTF-8";
	
	
	public String sendGet(String url, Map<String, String> paramsMap) {

		StringBuilder sb = new StringBuilder(url);
		
		Set<String> keys = paramsMap.keySet();
		
		if(sb.indexOf("?") < 0) {
			
			sb.append("?");
		}
		
		for (String key : keys) {
			sb.append(key).append("=").append(paramsMap.get(key)).append("&");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		String getUrl = sb.toString();
		
		return this.sendGet(getUrl);
	}
	
	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String sendGet(String url) {
		
		long responseLength = 0; // 响应长度
		String responseContent = null; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");

			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);

			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(
					new Scheme("https", 443, socketFactory));

			HttpGet httpPost = new HttpGet(url); // 创建HttpPost

	//		httpPost.setEntity(new StringEntity(content, this.charset));

			httpPost.addHeader("Accept",
					"application/json, text/javascript, */*; q=0.01");
			httpPost.addHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=" + this.charset);
		
			httpPost.addHeader("Accept-Language", "zh-CN");

			httpPost.addHeader("User-Agent",
							"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");

			HttpResponse response = httpClient.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体

			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, this.charset);
				EntityUtils.consume(entity); // Consume response content
			}
			logger.debug("请求地址: " + httpPost.getURI());
			logger.debug("响应状态: " + response.getStatusLine());
			logger.debug("响应长度: " + responseLength);
			logger.debug("响应内容: " + responseContent);

			Header[] hs = response.getAllHeaders();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {

			if (httpClient != null) {

				httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
			}
		}

		return responseContent;
	}

	public String sendPost(String url, String content) {

		long responseLength = 0; // 响应长度
		String responseContent = null; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");

			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);

			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(
					new Scheme("https", 443, socketFactory));

			HttpPost httpPost = new HttpPost(url); // 创建HttpPost

			httpPost.setEntity(new StringEntity(content, this.charset));

			httpPost.addHeader("Accept","*.*");
			httpPost.addHeader("Content-Type",
					"application/json;charset=" + this.charset);//键	值
			
			httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
		
			httpPost.addHeader("Accept-Language", "zh-CN");

			httpPost.addHeader("Accept-Encoding", "gzip, deflate");
	
			httpPost.addHeader("Connection", "Keep-Alive");

			HttpResponse response = httpClient.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体

			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, this.charset);
				EntityUtils.consume(entity); // Consume response content
			}
			logger.debug("请求地址: " + httpPost.getURI());
			logger.debug("content: " + content);
			logger.debug("响应状态: " + response.getStatusLine());
			logger.debug("响应长度: " + responseLength);
			logger.debug("响应内容: " + responseContent);

			Header[] hs = response.getAllHeaders();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {

			if (httpClient != null) {

				httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
			}
		}

		return responseContent;
	}

	public String sendPostWithCookie(String url, String content, String cookie,
			String ref) {

		long responseLength = 0; // 响应长度
		String responseContent = null; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");

			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);

			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(
					new Scheme("https", 443, socketFactory));

			HttpPost httpPost = new HttpPost(url); // 创建HttpPost
			
			if(content != null) {

				httpPost.setEntity(new StringEntity(content, this.charset));
			}

			httpPost.addHeader("Accept",
					"application/json, text/javascript, */*; q=0.01");
			httpPost.addHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=" + this.charset);
	//		httpPost.addHeader("X-Requested-With", "XMLHttpRequest");

			if (ref == null) {

				httpPost.addHeader("Referer", "https://mp.weixin.qq.com/");
			} else {

				httpPost.addHeader("Referer", ref);
			}
			httpPost.addHeader("Accept-Language", "zh-CN");

			httpPost.addHeader("Accept-Encoding", "gzip, deflate");
			httpPost
					.addHeader("User-Agent",
							"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");

	//		httpPost.addHeader("Host", "mp.weixin.qq.com");

	//		httpPost.addHeader("DNT", "1");

			httpPost.addHeader("Connection", "Keep-Alive");

			httpPost.addHeader(new BasicHeader("Cookie", cookie));

			// DefaultHttpClient dh = (DefaultHttpClient)httpClient;

			HttpResponse response = httpClient.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体

			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, this.charset);
				EntityUtils.consume(entity); // Consume response content
			}
			logger.debug("请求地址: " + httpPost.getURI());
			logger.debug("表单" + content);
			logger.debug("Cookie:" + cookie);
			logger.debug("响应状态: " + response.getStatusLine());
			logger.debug("响应长度: " + responseLength);
			logger.debug("响应内容: " + responseContent);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {

			if (httpClient != null) {

				httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
			}
		}

		return responseContent;
	}
	
	public String sendGetWithCookie(String url, String cookie,
			String ref) {

		long responseLength = 0; // 响应长度
		String responseContent = null; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");

			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);

			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(
					new Scheme("https", 443, socketFactory));

			HttpGet httpPost = new HttpGet(url); // 创建HttpPost

			httpPost.addHeader("Accept",
					"application/json, text/javascript, */*; q=0.01");
			httpPost.addHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=" + this.charset);
	//		httpPost.addHeader("X-Requested-With", "XMLHttpRequest");

			if (ref == null) {

				httpPost.addHeader("Referer", "https://mp.weixin.qq.com/");
			} else {

				httpPost.addHeader("Referer", ref);
			}
			httpPost.addHeader("Accept-Language", "zh-CN");

			httpPost.addHeader("Accept-Encoding", "gzip, deflate");
			httpPost
					.addHeader("User-Agent",
							"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");

	//		httpPost.addHeader("Host", "mp.weixin.qq.com");

	//		httpPost.addHeader("DNT", "1");

			httpPost.addHeader("Connection", "Keep-Alive");

			httpPost.addHeader(new BasicHeader("Cookie", cookie));

			// DefaultHttpClient dh = (DefaultHttpClient)httpClient;

			HttpResponse response = httpClient.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体

			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, this.charset);
				EntityUtils.consume(entity); // Consume response content
			}
			logger.debug("请求地址: " + httpPost.getURI());
			logger.debug("Cookie:" + cookie);
			logger.debug("响应状态: " + response.getStatusLine());
			logger.debug("响应长度: " + responseLength);
			logger.debug("响应内容: " + responseContent);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {

			if (httpClient != null) {

				httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
			}
		}

		return responseContent;
	}

	public List<String> sendPostWithCookie(String url, String content) {

		long responseLength = 0; // 响应长度
		String responseContent = null; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		List<String> cookies = new ArrayList<String>();
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");

			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);

			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(
					new Scheme("https", 443, socketFactory));

			HttpPost httpPost = new HttpPost(url); // 创建HttpPost

			httpPost.setEntity(new StringEntity(content, this.charset));

			httpPost.addHeader("Accept",
					"application/json, text/javascript, */*; q=0.01");
			httpPost.addHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=" + this.charset);
			httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpPost.addHeader("Referer", "https://mp.weixin.qq.com/");
			httpPost.addHeader("Accept-Language", "zh-CN");

			httpPost.addHeader("Accept-Encoding", "gzip, deflate");
			httpPost
					.addHeader("User-Agent",
							"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");

			httpPost.addHeader("Host", "	mp.weixin.qq.com");

			httpPost.addHeader("DNT", "1");

			httpPost.addHeader("Connection", "Keep-Alive");

			HttpResponse response = httpClient.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体

			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, this.charset);
				EntityUtils.consume(entity); // Consume response content
			}
			logger.debug("请求地址: " + httpPost.getURI());
			logger.debug("响应状态: " + response.getStatusLine());
			logger.debug("响应长度: " + responseLength);
			logger.debug("响应内容: " + responseContent);

			Header[] hs = response.getAllHeaders();

			for (Header header : hs) {

				if (header.getName().equals("Set-Cookie")) {

					String cookie = header.getValue();
					cookie = cookie.split("Path=/")[0];

					cookies.add(cookie);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {

			if (httpClient != null) {

				httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
			}
		}

		List<String> result = new ArrayList(cookies.size() + 1);
		result.add(responseContent);
		result.addAll(cookies);

		return result;
	}
	
	/**
	 * 
	 * @param url
	 * @param paramsMap
	 * @param charset
	 * @return
	 */
	public String sendPost(String url, Map<String, String> paramsMap) throws Exception {

		long responseLength = 0; // 响应长度
		String responseContent = null; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");

			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);

			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(
					new Scheme("https", 443, socketFactory));

			HttpPost httpPost = new HttpPost(url); // 创建HttpPost
			List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 构建POST请求的表单参数
			for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			HttpEntity he = new UrlEncodedFormEntity(formParams, charset);
			Long cl = he.getContentLength();
			Header header = he.getContentEncoding();
			InputStream in = he.getContent();
			byte[] b = new byte[cl.intValue()];
			in.read(b);
			String sb = new String(b, this.charset);
			httpPost.setEntity(he);
//			httpPost.setEntity(new UrlEncodedFormEntity(formParams, charset));
//			httpPost.setHeader("Content-Type", contentType);

			HttpResponse response = httpClient.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体

			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, charset);
				EntityUtils.consume(entity); // Consume response content
			}
			
			logger.debug("请求地址: " + httpPost.getURI());
			logger.debug("响应状态: " + response.getStatusLine());
			logger.debug("响应长度: " + responseLength);
			logger.debug("响应内容: " + responseContent);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {

			if (httpClient != null) {

				
				httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
				
			}
		}

		return responseContent;
	}

}