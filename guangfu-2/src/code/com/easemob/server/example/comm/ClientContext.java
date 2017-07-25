package com.easemob.server.example.comm;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientContext {
	
	/*
	 * Configuration Source Type
	 */
	public static final String INIT_FROM_PROPERTIES = "FILE";
	
	public static final String INIT_FROM_CLASS = "CLASS";
	
	/*
	 * Implementation List
	 */
	public static final String JERSEY_API = "jersey";
	
	public static final String HTTPCLIENT_API = "httpclient";
	
	private static final String API_PROTOCAL = "https";
	private static final String API_HOST = "a1.easemob.com";
	private static final String API_ORG = "0597208";
	private static final String API_APP = "uen";
	private static final String APP_CLIENT_ID = "YXA6RYa84DfCEeawvTEs_r-OXg";
	private static final String APP_CLIENT_SECRET = "YXA6KvSmpKZ_Gl4Pt4qIqtyIb_iXU7I";
	private static final String APP_IMP_LIB = "httpclient";

	private static final Logger log = LoggerFactory.getLogger(ClientContext.class);
	
	private static ClientContext context;
		
	private Boolean initialized = Boolean.FALSE;
	
	private String protocal;
	
	private String host;
	
	private String org;
	
	private String app;
	
	private String clientId;
	
	private String clientSecret;
	
	private String impLib;
	
	private EasemobRestAPIFactory factory;
	
	private TokenGenerator token; // Wrap the token generator
	
	private ClientContext() {};
	
	public static ClientContext getInstance() {
		if( null == context ) {
			context = new ClientContext();
		}
		
		return context;
	}
	
	public ClientContext init(String type) {
		if( initialized ) {
			log.warn("Context has been initialized already, skipped!");
			return context;
		}
		
		if( StringUtils.isBlank(type) ) {
			log.warn("Context initialization type was set to FILE by default.");
			type = INIT_FROM_PROPERTIES;
		}
		
		if( INIT_FROM_PROPERTIES.equals(type) ) {
			initFromPropertiesFile();
		} 
		else if( INIT_FROM_CLASS.equals(type) ){
			initFromStaticClass();
		} 
		else {
			log.error(MessageTemplate.print(MessageTemplate.UNKNOW_TYPE_MSG, new String[]{type, "context initialization"}));
			return context; // Context not initialized
		}
		
		// Initialize the token generator by default
		if( context.initialized ) {
			token = new TokenGenerator(context);
		}
		
		return context;
	}
	
	public EasemobRestAPIFactory getAPIFactory() {
		if( !context.isInitialized() ) {
			log.error(MessageTemplate.INVAILID_CONTEXT_MSG);
			throw new RuntimeException(MessageTemplate.INVAILID_CONTEXT_MSG);
		}
		
		if( null == this.factory ) {
			this.factory = EasemobRestAPIFactory.getInstance(context);
		}
		
		return this.factory;
	}
	
	public String getSeriveURL() {
		if (null == context || !context.isInitialized()) {
			log.error(MessageTemplate.INVAILID_CONTEXT_MSG);
			throw new RuntimeException(MessageTemplate.INVAILID_CONTEXT_MSG);
		}

		String serviceURL = context.getProtocal() + "://" + context.getHost() + "/" + context.getOrg() + "/" + context.getApp();

		return serviceURL;
	}
	
	public String getAuthToken() {
		if( null == token ) {
			log.error(MessageTemplate.INVAILID_TOKEN_MSG);
			throw new RuntimeException(MessageTemplate.INVAILID_TOKEN_MSG);
		}
		
		return token.request(Boolean.FALSE);
	}
	
	private void initFromPropertiesFile() {
		context.protocal = API_PROTOCAL;
		context.host = API_HOST;
		context.org = API_ORG;
		context.app = API_APP;
		context.clientId = APP_CLIENT_ID;
		context.clientSecret = APP_CLIENT_SECRET;
		context.impLib = APP_IMP_LIB;
		
		log.debug("protocal: " + context.protocal);
		log.debug("host: " + context.host);
		log.debug("org: " + context.org);
		log.debug("app: " + context.app);
		log.debug("clientId: " + context.clientId);
		log.debug("clientSecret: " + context.clientSecret);
		log.debug("impLib: " + context.impLib);
		
		initialized = Boolean.TRUE;
	}
	
	private ClientContext initFromStaticClass() {
		return null;
	}

	public String getProtocal() {
		return protocal;
	}

	public String getHost() {
		return host;
	}

	public String getOrg() {
		return org;
	}

	public String getApp() {
		return app;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}
	
	public Boolean isInitialized() {
		return initialized;
	}
	
	public String getImpLib() {
		return impLib;
	}
	
	public static void main(String[] args) {
		ClientContext.getInstance().init(null);
	}
	
}
