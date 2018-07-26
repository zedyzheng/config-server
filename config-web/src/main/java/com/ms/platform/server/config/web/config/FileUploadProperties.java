package com.ms.platform.server.config.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadProperties {

	private String fileupload_api_url;
	
	private String file_bucket_endpoint;

	public String getFile_bucket_endpoint() {
		return file_bucket_endpoint;
	}

	public void setFile_bucket_endpoint(String file_bucket_endpoint) {
		this.file_bucket_endpoint = file_bucket_endpoint;
	}

	public String getFileupload_api_url() {
		return fileupload_api_url;
	}

	public void setFileupload_api_url(String fileupload_api_url) {
		this.fileupload_api_url = fileupload_api_url;
	}
	
	
}
