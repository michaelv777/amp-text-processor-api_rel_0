package text.processor.api.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public abstract class TextProcessorBase
{
	protected HashMap<String, String> 
		cSystemConfiguration = new  HashMap<String, String>();
	
	protected HashMap<String, String> 
		cWorkerConfiguration = new  HashMap<String, String>();
	
	protected boolean lcRes = true;

	public boolean isLcRes() {
		return lcRes;
	}

	public void setLcRes(boolean lcRes) {
		this.lcRes = lcRes;
	}
	
	public HashMap<String, String> getcSystemConfiguration() {
		return cSystemConfiguration;
	}

	public void setcSystemConfiguration(HashMap<String, String> cSystemConfiguration) {
		this.cSystemConfiguration = cSystemConfiguration;
	}
	
	public HashMap<String, String> getcWorkerConfiguration() {
		return cWorkerConfiguration;
	}

	public void setcWorkerConfiguration(HashMap<String, String> cWorkerConfiguration) {
		this.cWorkerConfiguration = cWorkerConfiguration;
	}

	protected void printHttpsCert(HttpsURLConnection conn)
	{

      try 
      {
    	  if(conn == null)
    	  {
    		  return ;
    	  }
    	  
		  System.out.println("Response Code : " + conn.getResponseCode());
		  System.out.println("Cipher Suite : " + conn.getCipherSuite());
		  System.out.println("\n");
	
		  Certificate[] certs = conn.getServerCertificates();
			
		  for(Certificate cert : certs)
		  {
			   System.out.println("Cert Type : " + cert.getType());
			   System.out.println("Cert Hash Code : " + cert.hashCode());
			   System.out.println("Cert Public Key Algorithm : "
		                                    + cert.getPublicKey().getAlgorithm());
			   System.out.println("Cert Public Key Format : "
		                                    + cert.getPublicKey().getFormat());
			   System.out.println("\n");
		  }

      }
	  catch (SSLPeerUnverifiedException e) 
	  {
		e.printStackTrace();
	  } 
	  catch (IOException e)
	  {
		e.printStackTrace();
	  }
	    
	}
	
	protected void printContent(HttpsURLConnection con)
	{
		if(con == null)
		{
			return ;
		}
		
		try 
		{

		   System.out.println("****** Content of the URL ********");
		   BufferedReader br =
			new BufferedReader(
				new InputStreamReader(con.getInputStream()));

		   String input;

		   while ((input = br.readLine()) != null)
		   {
		      System.out.println(input);
		   }
		   
		   br.close();

		} 
		catch (IOException e) 
		{
		   e.printStackTrace();
		}
	}
	
	protected boolean setSystemProperties() 
	{
		String cMethodName = "";
		
		boolean cRes = true;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        for( Map.Entry<String, String> cConfiguration : this.cSystemConfiguration.entrySet() )
	        {
	        	String cConfigKey   = cConfiguration.getKey();
	        	String cConfigValue = cConfiguration.getValue();
	        	
	        	if ( System.getProperty(cConfigKey) != null )
        		{
        			System.clearProperty(cConfigKey);
        		}
	        	
	        	System.setProperty(cConfigKey, cConfigValue);
	        }
	        
	        return cRes;
		}
		catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return cRes;
		}
	}
	
	//---
	protected boolean setWorkerProperties(Class<? extends TextProcessorBase> clazz)
	{
		@SuppressWarnings("unused")
		String cMethodName = "";
		
		boolean cRes = true;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
			Field[] cFields = clazz.getDeclaredFields();
			
			for( int jondex = 0; jondex < cFields.length; ++jondex )
			{
				Field cField = (Field)cFields[jondex];
				
				String cSourceConfigKey = cField.getName();
						 
				if ( this.cWorkerConfiguration.containsKey(cSourceConfigKey))
				{
					String cSourceConfigValue = 
							this.cWorkerConfiguration.get(cSourceConfigKey);
					
					  Type type = (Type) cField.getGenericType();
					  	
					  if ( type.equals(String.class ))
					  {
						  cField.set(this, cSourceConfigValue);
					  }
					  else if ( type.equals(boolean.class ))
					  {
						  boolean cBoolSet = Boolean.parseBoolean(cSourceConfigValue);
						  cField.setBoolean(this, cBoolSet);	
					  }
					  else if ( type.equals(int.class ))
					  {
						  int cIntSet = Integer.parseInt(cSourceConfigValue);
						  cField.setInt(this, cIntSet);	
					  }
					  else if ( type.equals(long.class ))
					  {
						  long cIntSet = Long.parseLong(cSourceConfigValue);
						  cField.setLong(this, cIntSet);	
					  }
				}
			}
			
			return cRes;
		}
		catch( IllegalAccessException e )
		{
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return cRes;
		}
		catch( Exception e)
		{
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return cRes;
		}
	}
}

