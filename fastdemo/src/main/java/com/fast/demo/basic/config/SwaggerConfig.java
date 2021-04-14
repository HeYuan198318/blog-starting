package com.fast.demo.basic.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: SwaggerConfig
 * @Description: swagger接口文檔配置
 * @author:C3006748
 * @date 2021年3月30日 下午3:22:27
 * @version V1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/** 扫包根路径 **/
	private final String basePackage = "com.fast.demo";
	private final String title = "fastDemo";
	private final String description = "fastDemo";
	private final String termsOfServiceUrl = "/";
	private final String groupName = "fastDemo";
	private final String version = "1.0";
	private final String userName = "xxxxxxxx";
	private final String url = "xxxxxxxx";
	private final  String resp = "ResponseMsg";


	@Bean(value = "defaultApi")
	public Docket createRestApi() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.groupName(groupName)
				.select()
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(PathSelectors.regex("/.*"))
				.build();
		return buildDefaultApi(docket);
	}


	/**
	 * 默認配置
	 * @param docket
	 * @return
	 */
	public Docket  buildDefaultApi(Docket docket){
		List<ResponseMessage> responseMessageList = new ArrayList<>();
		responseMessageList.add(new ResponseMessageBuilder().code(404).message("找不到资源").responseModel(new ModelRef(resp)).build());
		responseMessageList.add(new ResponseMessageBuilder().code(400).message("参数校验异常").responseModel(new ModelRef(resp)).build());
		responseMessageList.add(new ResponseMessageBuilder().code(401).message("沒有登錄憑證/授權碼過期").responseModel(new ModelRef(resp)).build());
		responseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部错误").responseModel(new ModelRef(resp)).build());
		responseMessageList.add(new ResponseMessageBuilder().code(402).message("無效權限").responseModel(new ModelRef(resp)).build());

		docket.apiInfo(apiInfo())
				.globalResponseMessage(RequestMethod.GET, responseMessageList)
				.globalResponseMessage(RequestMethod.POST, responseMessageList)
				.globalResponseMessage(RequestMethod.PUT, responseMessageList)
				.globalResponseMessage(RequestMethod.DELETE, responseMessageList)
				.securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey()));
		return docket;
	}


	/**
	 * 登錄auto配置
	 * @return
	 */
	private ApiKey apiKey() {
		return new ApiKey("BearerToken", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/.*"))
				.build();
	}
	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
	}

	/**
	 * api 主頁配置
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(title)
				.description(description)
				.termsOfServiceUrl(termsOfServiceUrl)
				.version(version)
				.contact(userName)
				.build();
	}


}
