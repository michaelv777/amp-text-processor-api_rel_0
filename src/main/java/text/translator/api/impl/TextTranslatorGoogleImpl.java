/**
 * 
 */
package text.translator.api.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;

import text.processor.api.base.TextProcessorBase;
import text.translator.api.interfaces.TextTranslatorInterface;

/**
 * @author MVEKSLER
 *
 */
public class TextTranslatorGoogleImpl extends TextProcessorBase implements TextTranslatorInterface
{
	
	
	protected String url = "https://translate.googleapis.com/translate_a/single";

	protected URI getBaseURI() {
        return UriBuilder.fromUri(url).build();
	}
	
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

	
	public TextTranslatorGoogleImpl(HashMap<String, String> cSystemConfiguration,
				  					HashMap<String, String> cWorkerConfiguration)
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement ste = stacktrace[1];
			cMethodName = ste.getMethodName();
			
			this.setcSystemConfiguration(cSystemConfiguration);
			
			this.setcWorkerConfiguration(cWorkerConfiguration);
			
			this.setSystemProperties();
		}
		catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			this.setLcRes(false);
		}
	}
	
	public TextTranslatorGoogleImpl(HashMap<String, String> cSystemConfiguration)
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        this.setcSystemConfiguration(cSystemConfiguration);
	        
	        this.setSystemProperties();
		}
		catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			this.setLcRes(false);
		}
	}
	
	public TextTranslatorGoogleImpl()
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	      
		}
		catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			this.setLcRes(false);
		}
	}
	
	
	
	public String translateTextByGet( String textToTranslate ) 
	{
		String cMethodName = "";
	
		String cResponse = "";
		
		@SuppressWarnings("unused")
		boolean cRes = true;
	
		try 
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement ste = stacktrace[1];
			cMethodName = ste.getMethodName();
			
			final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0";
			
			Client client = Client.create();
			client.addFilter(new ClientFilter()
			{ 
	            @Override 
	            public ClientResponse handle(ClientRequest request) 
	                            throws ClientHandlerException 
	            { 
	                    request.getHeaders().add( 
	                                    HttpHeaders.USER_AGENT, 
	                                    USER_AGENT); 
	                    return getNext().handle(request); 
	            }
			}); 
			
			WebResource webResource = client.resource(this.getBaseURI().toString());
	
			ClientResponse response = webResource
					.queryParam("client", String.valueOf("gtx"))
					.queryParam("sl", String.valueOf("auto"))
					.queryParam("tl", String.valueOf("en"))
					.queryParam("dt", String.valueOf("t"))
					.queryParam("q", textToTranslate)
					.get(ClientResponse.class);
	
			if (response.getStatus() == HttpURLConnection.HTTP_OK) 
			{
				cResponse = response.getEntity(String.class);
	
				System.out.println(cMethodName + "::" + cResponse);
	
				//cLogger.error(cMethodName + "::" + cResponse);
			} 
			else 
			{
				cResponse = "";
	
				System.out.println(cMethodName + "::" + cResponse);
	
				//cLogger.info(cMethodName + "::" + cResponse);
			}
	
			return cResponse;
	
		} 
		catch (Exception e) 
		{
			System.out.println(cMethodName + "::Exception:" + e.getMessage());
	
			e.printStackTrace();
	
			return "";
		}
	}

	@Override
	public String translateTextByPost(String textToTranslate) 
	{
		String cMethodName = "";
	
		String cResponse = "";
		
		@SuppressWarnings("unused")
		boolean cRes = true;
	
		try 
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement ste = stacktrace[1];
			cMethodName = ste.getMethodName();
			
			final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0";
			
			Client client = Client.create();
			client.addFilter(new ClientFilter()
			{ 
	            @Override 
	            public ClientResponse handle(ClientRequest request) 
	                            throws ClientHandlerException 
	            { 
	                    request.getHeaders().add( 
	                                    HttpHeaders.USER_AGENT, 
	                                    USER_AGENT); 
	                    return getNext().handle(request); 
	            }
			}); 
			
			WebResource webResource = client.resource(this.getBaseURI().toString());
	
			ClientResponse response = webResource
					.queryParam("client", String.valueOf("gtx"))
					.queryParam("sl", String.valueOf("auto"))
					.queryParam("tl", String.valueOf("en"))
					.queryParam("dt", String.valueOf("t"))
					.queryParam("q", textToTranslate)
					.accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_JSON).
					post(ClientResponse.class, "");
	
			String cStatus = response.getEntity(String.class);
	
			if (response.getStatus() != HttpURLConnection.HTTP_OK) 
			{
				cResponse = String.valueOf(cStatus) + ":" + response.getEntity(String.class);
	
				System.out.println(cMethodName + "::" + cResponse);
	
				//cLogger.error(cMethodName + "::" + cResponse);
			} 
			else 
			{
				cResponse = String.valueOf(cStatus) + ":" + response.getEntity(String.class);
	
				System.out.println(cMethodName + "::" + cResponse);
	
				//cLogger.info(cMethodName + "::" + cResponse);
			}
	
			return cResponse;
	
		} 
		catch (Exception e) 
		{
			System.out.println(cMethodName + "::Exception:" + e.getMessage());
	
			e.printStackTrace();
	
			return "";
		}
	}

	@Override
	public String translateTextByGetHttp(HashMap<String, String> params)
	{
		boolean cRes = true;
		
		String cMethodName = "";
		
		URL restUrl = null;
		
		HttpURLConnection conn = null;
		
		BufferedReader br = null;
				
		StringBuffer cOutput = new StringBuffer("");
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	      
	        final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0";
	        
	        if ( cRes )
	        {
	        	if ( !params.containsKey("text"))
	        	{
	        		System.out.println("M.V. Custom::" + cMethodName + "::text parameter should be provided");
	        		
	        		cRes = false;
	        	}
	        }
	        
	        if ( cRes )
	        {
	        	 restUrl = new URL(this.getUrl());
	        }
	        
	        if ( cRes )
	        {
		        conn = (HttpURLConnection) restUrl.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("User-Agent", USER_AGENT);
	        	conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	        	conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				
				if ( params.containsKey("client") )
				{
					conn.setRequestProperty("client", params.get("client"));
				}
				if ( params.containsKey("sl") )
				{
					conn.setRequestProperty("sl", params.get("sl"));
				}
				if ( params.containsKey("tl") )
				{
					conn.setRequestProperty("tl", params.get("tl"));
				}
				if ( params.containsKey("dt") )
				{
					conn.setRequestProperty("dt", params.get("dt"));
				}
				if ( params.containsKey("q") )
				{
					conn.setRequestProperty("q", params.get("q"));
				}
				
				int responseCode = conn.getResponseCode();
				if (responseCode != HttpURLConnection.HTTP_OK) 
				{
					System.out.println("M.V. Custom::" + cMethodName + "::" + conn.getResponseCode());
					System.out.println("M.V. Custom::" + cMethodName + "::" + conn.getResponseMessage());
					
					cRes = false;
				}
	        }
			
			if ( cRes )
			{
				br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
	
				System.out.println("Output from Server .... \n");
				String output = "";
				
				while ((output = br.readLine()) != null) 
				{
					System.out.println(output);
					
					cOutput.append(output);
				}
			}
			
	        return cOutput.toString();
		}
		catch (MalformedURLException e) 
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return "";

		}
		catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return "";
		}
		finally
		{
			if ( br != null )
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if ( conn != null )
			{
				conn.disconnect();
			}
		}
	}

	@Override
	public String translateTextByPostHttp(HashMap<String, String> params)
	{
		boolean cRes = true;
		
		String cMethodName = "";
		
		URL restUrl = null;
		
		HttpURLConnection conn = null;
		
		BufferedReader br = null;
				
		StringBuffer cOutput = new StringBuffer("");
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
			
	        final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0";
	        
	        String reqParams = "";
	        
	        if ( cRes )
	        {
	        	if ( params.containsKey("client"))
	        	{
	        		reqParams += "client=" + params.get("client");
	        	}
	        	else
	        	{
	        		System.out.println("M.V. Custom::" + cMethodName + "::text parameter should be provided");
	        		
	        		cRes = false;
	        	}
	        	
	        	if ( params.containsKey("sl"))
	        	{
	        		reqParams += "&";
	        		reqParams += "sl=" + params.get("sl");
	        	}
	        	
	        	if ( params.containsKey("tl"))
	        	{
	        		reqParams += "&";
	        		reqParams += "tl=" + params.get("tl");
	        	}
	        	
	        	if ( params.containsKey("dt"))
	        	{
	        		reqParams += "&";
	        		reqParams += "dt=" + params.get("dt");
	        	}
	        	
	        	if ( params.containsKey("q")) //i.e. types, categories
	        	{
	        		reqParams += "&";
	        		reqParams += "q=" + params.get("q");
	        	}
	        }
	        
	        if ( cRes )
	        {
	        	 restUrl = new URL(this.getUrl());
	        }
	        
	        if ( cRes )
	        {
	        	conn = (HttpURLConnection) restUrl.openConnection();
	        	conn.setRequestMethod("POST");
	        	conn.setRequestProperty("User-Agent", USER_AGENT);
	        	conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	        	conn.setRequestProperty("Accept", "application/json");
	        	conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        	//conn.setRequestProperty("Content-Length", String.valueOf(data.length));
	        	conn.setDoOutput(true);
	    		
	        	DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
	    		
	    		wr.writeBytes(reqParams);
	    		wr.flush();
	    		wr.close();
	        }
	        
	        if ( cRes )
	        {
	        	int responseCode = conn.getResponseCode();
	        	
	    		System.out.println("\nSending 'POST' request to URL : " + url);
	    		System.out.println("Post parameters : " + reqParams);
	    		System.out.println("Response Code : " + responseCode);
	    		
	        	if (responseCode != HttpURLConnection.HTTP_OK) 
				{
					System.out.println("M.V. Custom::" + cMethodName + "::" + conn.getResponseCode());
					System.out.println("M.V. Custom::" + cMethodName + "::" + conn.getResponseMessage());
					
					cRes = false;
				}
	        }
			if ( cRes )
			{
				br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
	
				System.out.println("Output from Server .... \n");
				String output = "";
				
				while ((output = br.readLine()) != null) 
				{
					System.out.println(output);
					
					cOutput.append(output);
				}
			}
			
	        return cOutput.toString();
		}
		catch (MalformedURLException e) 
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return "";

		}
		catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return "";
		}
		finally
		{
			if ( br != null )
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if ( conn != null )
			{
				conn.disconnect();
			}
		}
	}
	
	
	
	@Override
	public String getTranslatedData(String translatedData) 
	{
		boolean cRes = true;
		boolean isTranslationTaken = false;
		
		String cMethodName = "";
				
		StringBuffer cOutput = new StringBuffer("");
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	     
	        if ( StringUtils.isBlank(translatedData) ) 
	        {
	        	System.out.println("M.V. Custom::" + cMethodName + "::entityData is empty!");
	        	
	        	cRes = false;
	        }
	        
	        if ( cRes )
	        {
	        	JSONParser parser = new JSONParser();
	        	
	        	JSONArray root = (JSONArray) parser.parse(translatedData);
	        	
	        	if ( root != null )
	        	{
		        	for (Object rootObj : root) 
		        	{
		        		if ( !isTranslationTaken && rootObj != null )
		        		{
		        			isTranslationTaken = true;
		        			
		        			if ( rootObj instanceof JSONArray )
		        			{
				        		JSONArray flevelArray = (JSONArray)  rootObj;
				        		
				        		if ( flevelArray != null )
				        		{
					        		for( Object flevelObj : flevelArray )
					        		{
					        			if ( flevelObj != null )
					        			{
					        				if ( flevelObj instanceof JSONArray )
						        			{
							        			JSONArray slevelArray = (JSONArray) flevelObj;
							        			
							        			if ( slevelArray != null )
							        			{
								        			for( Object slevelObj : slevelArray )
									        		{
								        				if ( slevelObj != null )
								        				{
									        				if ( slevelObj instanceof String )
									        				{
									        					String translatedText = (String) slevelObj;
										        				
										        				cOutput.append(translatedText);
										    	                cOutput.append(" ");
										    	                System.out.println(translatedText);
									        				}
									        				
									        				break;
								        				}
									        		}
							        			}
						        			}
					        			}
					        		}
				        		}
			        		}
		        		}
		        	}
	        	}
	        }
	        
	        String cOutputStr = this.cleanResult(cOutput);
	        
	        return cOutputStr;
		}
        catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return "";
		}
	}

	/**
	 * @param cOutput
	 * @return
	 */
	protected String cleanResult(StringBuffer cOutput) 
	{
		String cMethodName = "";
		
		@SuppressWarnings("unused")
		boolean cRes = true;
	
		try 
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement ste = stacktrace[1];
			cMethodName = ste.getMethodName();
					
			if ( StringUtils.isBlank(cOutput))
			{
				return "";
			}
			
			String cOutputStr = cOutput.toString();
			
			cOutputStr = cOutputStr.replaceAll(Pattern.quote("+"), " ");
			cOutputStr = cOutputStr.replaceAll(Pattern.quote(":"), " ");
		
		return cOutputStr;
		
		}
        catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return "";
		}
	}
}
