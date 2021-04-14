package com.fast.demo.basic.config;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @ClassName: RestTemplateConfig
 * @Description: 創建連接池配置
 * @author:C3006748
 * @date 2021年3月30日 下午3:20:54
 * @version V1.0
 */

@Configuration
public class RestTemplateConfig {

	/**
	 * restTemplate 定義
	 * 
	 * @param builder
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate restTemplate = builder.build();
		restTemplate.setRequestFactory(clientHttpRequestFactory());
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
		while (iterator.hasNext()) {
			HttpMessageConverter<?> converter = iterator.next();
			if (converter instanceof StringHttpMessageConverter) {
				iterator.remove();
			}
		}
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}

	@Bean
	public HttpClientConnectionManager poolingConnectionManager() {
		PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
		// 连接池最大连接数
		poolingConnectionManager.setMaxTotal(1000);
		// 每个主机的并发
		poolingConnectionManager.setDefaultMaxPerRoute(100);
		return poolingConnectionManager;
	}

	@Bean
	public HttpClientBuilder httpClientBuilder() {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// 设置HTTP连接管理器
		httpClientBuilder.setConnectionManager(poolingConnectionManager());
		return httpClientBuilder;
	}

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient(httpClientBuilder().build());
		// 连接超时，毫秒
		clientHttpRequestFactory.setConnectTimeout(30 * 1000);
		// 读写超时，毫秒
		clientHttpRequestFactory.setReadTimeout(30 * 1000);
		return clientHttpRequestFactory;
	}
}
