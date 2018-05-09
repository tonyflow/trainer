package com.hackathon.trainer.elasticsearch.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Class has dual purpose. Parsing configuration properties yaml and declaring
 * (configuring) the required beans for the elastic search integration with the
 * server
 * 
 * @author niko.strongioglou
 *
 */
@ConfigurationProperties(locations = "classpath:application.yml", ignoreUnknownFields = false, prefix = "application.elasticsearch")
public class ElasticSearchConfigurationProperties {

	private String host;

	private Integer port;

	private String clusterName;

	private String index;

	private String documentType;

	private String numberOfShards;

	private String numberOfReplicas;

	private boolean clusterSniff;

	public boolean isClusterSniff() {
		return clusterSniff;
	}

	public void setClusterSniff(boolean clusterSniff) {
		this.clusterSniff = clusterSniff;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getNumberOfReplicas() {
		return numberOfReplicas;
	}

	public void setNumberOfReplicas(String numberOfReplicas) {
		this.numberOfReplicas = numberOfReplicas;
	}

	public String getNumberOfShards() {
		return numberOfShards;
	}

	public void setNumberOfShards(String numberOfShards) {
		this.numberOfShards = numberOfShards;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

}
