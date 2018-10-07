/**
 * 
 */
package text.processor.api.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import text.processor.api.base.TextProcessorBase;
import text.processor.api.interfaces.TextProcessorInterface;

/**
 * @author MVEKSLER
 *
 */
public class TextProcessorDandelionImpl extends TextProcessorBase  implements TextProcessorInterface
{
	
	
	protected String url = "https://api.dandelion.eu/datatxt/nex/v1";
	
	protected String token = "41a33dd86024475d99c428e418398145"; 
	
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}

	public TextProcessorDandelionImpl()
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
	
	public TextProcessorDandelionImpl(HashMap<String, String> cSystemConfiguration,
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
	
	public TextProcessorDandelionImpl(HashMap<String, String> cSystemConfiguration)
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

	@Override
	public String extractDataFromTextByGet( HashMap<String, String> params ) 
	{
		String cMethodName = "";
	
		String cResponse = "";
		
		boolean cRes = true;
	
		URL restUrl = null;
		
		try 
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement ste = stacktrace[1];
			cMethodName = ste.getMethodName();
			
			if ( cRes )
			{
	        	if ( !params.containsKey("text"))
	        	{
	        		System.out.println("M.V. Custom::" + cMethodName + "::text parameter should be provided");
	        		
	        		return cResponse;
	        	}
			}
			
			String text = "";
			String token = "";
			String min_confidence = "";
			String social_hashtag = "";
			String social_mention = "";
			String include = "";
			
			if ( params.containsKey("text") )
			{
				text = params.get("text");
			}
			if ( params.containsKey("token") )
			{
				token = params.get("token");
			}
			else
			{
				token = this.getToken();
			}
			if ( params.containsKey("min_confidence") )
			{
				min_confidence = params.get("min_confidence");
			}
			if ( params.containsKey("social.hashtag") )
			{
				social_hashtag = params.get("social.hashtag");
			}
			if ( params.containsKey("social.mention") )
			{
				social_mention = params.get("social.mention");
			}
			if ( params.containsKey("include") )
			{
				include = params.get("include");
			}
			
		    if ( cRes )
		    {
		        restUrl = new URL(this.getUrl());
		    }
		
			Client client = Client.create();
			
			WebResource webResource = client.resource(restUrl.toString());
	
			ClientResponse response = webResource
					.queryParam("text", text)
					.queryParam("token", token)
					.queryParam("min_confidence", min_confidence)
					.queryParam("social.hashtag", social_hashtag)
					.queryParam("social.mention", social_mention)
					.queryParam("include", include)
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
	

	public String extractEntityFromTextByGetHttp(HashMap<String, String> params)
	{
		boolean cRes = true;
		
		String cMethodName = "";
		
		URL restUrl = null;
		
		HttpsURLConnection conn = null;
		
		BufferedReader br = null;
				
		StringBuffer cOutput = new StringBuffer("");
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	      
	       if ( !params.containsKey("text"))
	       {
	        	System.out.println("M.V. Custom::" + cMethodName + "::text parameter should be provided");
	        		
	        	cRes = false;
	       }
	        
	       
	        if ( cRes )
	        {
	        	 restUrl = new URL(this.getUrl());
	        }
	        
	        if ( cRes )
	        {
		        conn = (HttpsURLConnection) restUrl.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
		        
				if ( params.containsKey("text") )
				{
					conn.setRequestProperty("text", params.get("text"));
				}
				if ( params.containsKey("token") )
				{
					conn.setRequestProperty("token", params.get("token"));
				}
				else
				{
					conn.setRequestProperty("token", this.getToken());
				}
				if ( params.containsKey("min_confidence") )
				{
					conn.setRequestProperty("min_confidence", params.get("min_confidence"));
				}
				if ( params.containsKey("social.hashtag") )
				{
					conn.setRequestProperty("social.hashtag", params.get("social.hashtag"));
				}
				if ( params.containsKey("social.mention") )
				{
					conn.setRequestProperty("social.mention", params.get("social.mention"));
				}
				if ( params.containsKey("include") )
				{
					conn.setRequestProperty("include", params.get("include"));
				}
				
				int responseCode = conn.getResponseCode();
				if (responseCode != HttpsURLConnection.HTTP_OK) 
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
	public String extractDataFromTextByPost(HashMap<String, String> params) 
	{
		String cMethodName = "";
	
		String cResponse = "";
		
		boolean cRes = true;
	
		URL restUrl = null;
		
		try 
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement ste = stacktrace[1];
			cMethodName = ste.getMethodName();
			
			if ( !params.containsKey("text"))
			{
        		System.out.println("M.V. Custom::" + cMethodName + "::text parameter should be provided");
        		
        		return cResponse;
			}
			
			String text = "";
			String token = "";
			String min_confidence = "";
			String social_hashtag = "";
			String social_mention = "";
			String include = "";
			
			if ( params.containsKey("text") )
			{
				text = params.get("text");
			}
			if ( params.containsKey("token") )
			{
				token = params.get("token");
			}
			else
			{
				token = this.getToken();
			}
			if ( params.containsKey("min_confidence") )
			{
				min_confidence = params.get("min_confidence");
			}
			if ( params.containsKey("social.hashtag") )
			{
				social_hashtag = params.get("social.hashtag");
			}
			if ( params.containsKey("social.mention") )
			{
				social_mention = params.get("social.mention");
			}
			if ( params.containsKey("include") )
			{
				include = params.get("include");
			}
			
		    if ( cRes )
		    {
		        restUrl = new URL(this.getUrl());
		    }
			
			Client client = Client.create();
			
			WebResource webResource = client.resource(restUrl.toString());
	
			ClientResponse response = webResource
					.queryParam("text", text)
					.queryParam("token", token)
					.queryParam("min_confidence", min_confidence)
					.queryParam("social.hashtag", social_hashtag)
					.queryParam("social.mention", social_mention)
					.queryParam("include", include)
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
	
	
	public String extractEntityFromTextByPostHttp(HashMap<String, String> params)
	{
		boolean cRes = true;
		
		String cMethodName = "";
		
		URL restUrl = null;
		
		HttpsURLConnection conn = null;
		
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
	        	if ( params.containsKey("text"))
	        	{
	        		reqParams += "text=" + params.get("text");
	        	}
	        	else
	        	{
	        		System.out.println("M.V. Custom::" + cMethodName + "::text parameter should be provided");
	        		
	        		cRes = false;
	        	}
	        	
	        	if ( params.containsKey("token"))
	        	{
	        		reqParams += "&";
	        		reqParams += "token=" + params.get("token");
	        	}
	        	else
	        	{
	        		reqParams += "&";
	        		reqParams += "token=" + this.getToken();
	        	}
	        	
	        	if ( params.containsKey("min_confidence"))
	        	{
	        		reqParams += "&";
	        		reqParams += "min_confidence=" + params.get("min_confidence");
	        	}
	        	
	        	if ( params.containsKey("social.hashtag"))
	        	{
	        		reqParams += "&";
	        		reqParams += "social.hashtag=" + params.get("social.hashtag");
	        	}
	        	
	        	if ( params.containsKey("social.mention"))
	        	{
	        		reqParams += "&";
	        		reqParams += "social.mention=" + params.get("social.mention");
	        	}
	        	
	        	if ( params.containsKey("include")) //i.e. types, categories
	        	{
	        		reqParams += "&";
	        		reqParams += "include=" + params.get("include");
	        	}
	        }
	        
	        if ( cRes )
	        {
	        	 restUrl = new URL(this.getUrl());
	        }
	        
	        if ( cRes )
	        {
	        	conn = (HttpsURLConnection) restUrl.openConnection();
	        	conn.setRequestMethod("POST");
	        	conn.setRequestProperty("User-Agent", USER_AGENT);
	        	conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
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
	    		
	        	if (responseCode != HttpsURLConnection.HTTP_OK) 
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
	public String getKeywordsFromJSONData(String entityData) 
	{
		boolean cRes = true;
		
		String cMethodName = "";
				
		StringBuffer cOutput = new StringBuffer("");
		
		int maxNumberOfKeywords = 2;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	     
	        if ( StringUtils.isBlank(entityData) ) 
	        {
	        	System.out.println("M.V. Custom::" + cMethodName + "::entityData is empty!");
	        	
	        	cRes = false;
	        }
	        
	        int cKeywordIndex = 1;
	        
	        if ( cRes )
	        {
	        	JSONParser parser = new JSONParser();
	        	
	        	JSONObject jsonObject = (JSONObject) parser.parse(entityData);
	        	
	        	JSONArray annotations = (JSONArray) jsonObject.get("annotations");
	            
	        	for (Object annotationObj : annotations) 
	        	{
	                JSONObject annotation = (JSONObject) annotationObj;
	                
	                String spot = (String) annotation.get("spot");
	                
	                if ( cKeywordIndex <= maxNumberOfKeywords )
	                {
		                cOutput.append(spot);
		                cOutput.append(" ");
		                
		                ++cKeywordIndex;
	                }
	                System.out.println(spot);
	        	}    
	        }
	        
	        return cOutput.toString();
		}
        catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return "";
		}
	}


	@Override
	public LinkedHashMap<Double, String> getKeywordsFromJSONData(
			String jsonData, int numKeywords, boolean checkEmotions) 
	{
		boolean cRes = true;
		
		String cMethodName = "";
				
		StringBuffer cOutput = new StringBuffer("");
		
		LinkedHashMap<Double, String> cKeywords = 
				new LinkedHashMap<Double, String>();
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	     
	        if ( StringUtils.isBlank(jsonData) ) 
	        {
	        	System.out.println("M.V. Custom::" + cMethodName + "::entityData is empty!");
	        	
	        	cRes = false;
	        }
	        
	        int cKeywordIndex = 0;
	        
	        if ( cRes )
	        {
	        	JSONParser parser = new JSONParser();
	        	
	        	JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
	        	
	        	JSONArray annotations = (JSONArray) jsonObject.get("annotations");
	            
	        	for (Object annotationObj : annotations) 
	        	{
	                JSONObject annotation = (JSONObject) annotationObj;
	                
	                String spot = (String) annotation.get("spot");
	                
	                if ( cKeywordIndex < numKeywords )
	                {
		                cOutput.append(spot);
		                cOutput.append(" ");
		                
		                ++cKeywordIndex;
	                }
	                System.out.println(spot);
	        	}   
	        	
	        	cKeywords.put(new Double(1), cOutput.toString());
	        }
	        
	        return cKeywords;
		}
        catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return new LinkedHashMap<Double, String>();
		}
	}


	@Override
	public String getEntitiesFromJSONData(String jsonData) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
