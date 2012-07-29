package org.frantictools.franticapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.frantictools.franticmod.FranticMod;
import org.yaml.snakeyaml.Yaml;

public class FranticMeAPI
{
	final String username;
	final String password;
	
	public boolean loggedIn = false;
	static final String url = "http://www.franticme.com";
	
	public FranticMeAPI(String username, String password)
	{
		this.username = username;
		this.password = password;

	}
	
	public void login(final ICallback<Boolean> cb)
	{
		Thread t = new Thread()
		{
			public void run()
			{
				HttpClient client = new DefaultHttpClient();
				try
				{
					String target = url + "/?r=login";
					
					List<BasicNameValuePair> params = makeNameValueList(new String[][] {
						{"franticPostUsername", username},
						{"franticPostPassword", password},
						{"api", "FMOD"}
					});
					
					String result = getResult(dispatchPostRequest(target, params, client));
					
					// Now do something with the data
					Yaml yaml = new Yaml();
					Map map = (Map) yaml.load(result.substring(3));
					
					FranticMod.username = (String) map.get("username");
					FranticMod.password = (String) map.get("password");
					cb.onFinish(true);

				} catch (Exception e)
				{
					e.printStackTrace();
					cb.onFinish(false);
				} finally
				{
					client.getConnectionManager().shutdown();
				}
			}
		};
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
	}
	
	public void banGuest(final String guestname, final String reason, final ICallback<Boolean> cb)
	{
		
	}
	
	public void banPlayer(final String playername, final BanReason reason, final ICallback<Boolean> cb)
	{
		
	}
	
	public void searchPlayers(final String query, final ICallback<FMPlayer[]> cb)
	{
		Thread t = new Thread()
		{
			@SuppressWarnings("unchecked")
			public void run()
			{
				HttpClient client = new DefaultHttpClient();
				try
				{
					String target = url + "/?r=search&do=search";
					
					List<BasicNameValuePair> params = makeNameValueList(new String[][] {
						{"franticPostUsername", username},
						{"franticPostPassword", password},
						{"api", "FMOD"},
						{"searchName", query}
					});
					
					String result = getResult(dispatchPostRequest(target, params, client));
					
					// Now do something with the data
					Yaml yaml = new Yaml();
					Map map = (Map) yaml.load(result.substring(3));

					List<Map> users = (List<Map>) map.get("users");
					FMPlayer[] resultArray = new FMPlayer[(Integer) map.get("results")];
					
					int i = 0;
					for (Map x : users)
					{
						resultArray[i++] = new FMPlayer(x);
					}
					cb.onFinish(resultArray);

				} catch (Exception e)
				{
					e.printStackTrace();
					cb.onFinish(new FMPlayer[0]);
				} finally
				{
					client.getConnectionManager().shutdown();
				}
			}
		};
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
	}
	
	public void approveMember(final String guest, final ICallback<Boolean> cb)
	{
		
	}
	
	
	
	
	/**********************************************************************************************************************************/
	
	
	
	
	/* ** These functions are to move the ugliness away from the api functionality ** */
	private static String getResult(HttpResponse response) throws IllegalStateException, IOException
	{
		InputStream input = response.getEntity().getContent();
		InputStreamReader isr = new InputStreamReader(input);
		BufferedReader br = new BufferedReader(isr);

		String line = "";

		String rawData = "";
		while ((line = br.readLine()) != null)
		{
			rawData = rawData + line + "\r\n";
		}
		return rawData;
	}
	
	private static HttpResponse dispatchPostRequest(String target, List<BasicNameValuePair> params, HttpClient client) throws ClientProtocolException, IOException
	{
		HttpPost httpPost = new HttpPost(target);

		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params);
		urlEncodedFormEntity.setContentEncoding(HTTP.UTF_8);
		httpPost.setEntity(urlEncodedFormEntity);
		return client.execute(httpPost);
	}
	
	private static List<BasicNameValuePair> makeNameValueList(String[][] x)
	{
		List<BasicNameValuePair> result = new ArrayList<BasicNameValuePair>();
		for (String[] pair : x)
		{
			result.add(new BasicNameValuePair(pair[0], pair[1]));
		}
		return result;
	}
}
