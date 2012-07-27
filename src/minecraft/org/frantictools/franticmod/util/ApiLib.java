package org.frantictools.franticmod.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.frantictools.franticmod.FranticMod;
import org.yaml.snakeyaml.Yaml;

@Deprecated
public class ApiLib
{
/***
 * DEPRECATED
 * DO NOT USE THIS CLASS
 */
	public static boolean login(String username, String password)
	{
		HttpClient client = new DefaultHttpClient();
		try
		{
			String target = "http://www.franticme.com/?r=login";

			HttpPost httpPost = new HttpPost(target);

			BasicNameValuePair[] params =
			{ new BasicNameValuePair("franticPostUsername", username), new BasicNameValuePair("franticPostPassword", password), new BasicNameValuePair("api", "FMOD") };

			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(Arrays.asList(params));
			urlEncodedFormEntity.setContentEncoding(HTTP.UTF_8);
			httpPost.setEntity(urlEncodedFormEntity);
			HttpResponse response = client.execute(httpPost);

			String rawData = getResult(response);

			Yaml yaml = new Yaml();
			Map map = (Map) yaml.load(rawData.substring(3));
			
			FranticMod.username = (String) map.get("username");
			FranticMod.password = (String) map.get("password");
			return true;

		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		} finally
		{
			client.getConnectionManager().shutdown();
		}
	}

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
}
