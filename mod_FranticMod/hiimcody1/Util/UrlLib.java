package hiimcody1.Util;

import hiimcody1.FranticMod;
import hiimcody1.Gui.GuiApprove;
import hiimcody1.Gui.GuiUserHistory;
import hiimcody1.Gui.GuiUserSearch;
import hiimcody1.Session.Temp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_FranticMod;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("unused")
public class UrlLib {

	static DefaultHttpClient httpclient = new DefaultHttpClient();
	public static boolean isBusy = false;
	
	public static void checkVersion() {
		Thread httpThread = new Thread() {
			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				try {
					String target = "http://www.pokemonnp.com/Frantic/currentversion.txt";
					HttpGet httpGet = new HttpGet(target);
					HttpResponse response = client.execute(httpGet);
					String rawData = getResult(response);
					if(!rawData.equals(mod_FranticMod.version)) {
						GuiTools.showModMessage("Update Available", "FMod "+rawData, Block.sponge);
					}
				} catch (Exception e) {
					GuiTools.showModMessage(ChatColors.DarkRed + "Error!", e.getMessage(), Block.tnt.blockID);
					e.printStackTrace();
				} finally {
					client.getConnectionManager().shutdown();
				}
			}
		};
		httpThread.setPriority(Thread.MIN_PRIORITY);
	}
	
	public static void cacheBanList() {
		Thread httpThread = new Thread() {
			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				try {
				String target = "http://www.pokemonnp.com/Frantic/index.php";
				HttpGet httpGet = new HttpGet(target);
				HttpResponse response;
				
				response = client.execute(httpGet);
				Temp.stringData.put("banCache", getResult(response));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					client.getConnectionManager().shutdown();
				}
			}
		};
		httpThread.setPriority(Thread.MIN_PRIORITY);
		httpThread.start();
	}
	
	public static void getBanList(final String q) {
		Thread httpThread = new Thread() {
			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				try {
					String rawData = "";
					GuiTools.showModMessage("Searching...", "Query: '" + q + "'", 347); // Clock
					GuiUserHistory.lines.clear();
					if(Temp.stringData.containsKey("banCache")) {
						rawData = Temp.stringData.get("banCache");
					} else {
						String target = "http://www.pokemonnp.com/Frantic/index.php";
						HttpGet httpGet = new HttpGet(target);
						HttpResponse response = client.execute(httpGet);
						rawData = getResult(response);
						Temp.stringData.put("banCache", rawData);
						//GuiTools.showModMessage("Retrieved page...", "Handle: "+response.toString(), 347); // Clock
					}
					String banLines[] = rawData.split("<hr>");
						//GuiTools.showModMessage("Split page...", "Entries: "+banLines.length, 347); // Clock
					List<String> userNames = new ArrayList<String>();
					List<String> userDates = new ArrayList<String>();
					int sz=1;
					if(rawData.toLowerCase().contains(q.toLowerCase())) {
						//GuiUserHistory.addLine("Preliminary checks have found a match. Please wait...");
					} else
					{
						client.getConnectionManager().shutdown();
						GuiTools.showModMessage(ChatColors.DarkGreen + "Search Complete!", "No results.", Block.cake);
						return;
					}
					for(int i=0; i < banLines.length; i++) {
						if(banLines[i].split("Username: ").length > 1) {
							if(banLines[i].split("Username: ")[1].split("\r\n")[0].toLowerCase().contains(q.toLowerCase())) {
								userNames.add(banLines[i].split("Username: ")[1].split("\r\n")[0]);
								userDates.add(banLines[i].split("Date: ")[1].split("\r\n")[0]);
								GuiUserHistory.addLine("["+sz+"] "+banLines[i].split("Date: ")[1].split("\r\n")[0]+" - "+banLines[i].split("Username: ")[1].split("\r\n")[0]);
								Thread.sleep(25);
								sz++;
							}
						}
					}
					Temp.hashData.put("banLines", banLines);
					Temp.hashData.put("banNames", userNames);
					GuiTools.showModMessage(ChatColors.DarkGreen + "Search Complete!", "Found "+(sz-1)+" users.", Block.cake);
				} catch (Exception e) {
					GuiTools.showModMessage(ChatColors.DarkRed + "Error!", e.getMessage(), Block.tnt.blockID);
					e.printStackTrace();
				} finally {
					client.getConnectionManager().shutdown();
				}
			}
		};
		httpThread.setPriority(Thread.MIN_PRIORITY);
		httpThread.start();
	}
	
	
	public static void testSearch(final String searchName) throws IllegalStateException, IOException {
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				try {
					String target = "http://www.franticme.com/?p=search";

					HttpPost httpPost = new HttpPost(target);

					GuiTools.showModMessage("Searching...", "Query: '" + searchName + "'", 347); // Clock

					BasicNameValuePair[] params = { new BasicNameValuePair("franticPostUsername", Temp.stringData.get("fModUsername")), new BasicNameValuePair("franticPostPassword", Temp.stringData.get("fModPassword")), new BasicNameValuePair("searchName", searchName), };

					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(Arrays.asList(params));
					urlEncodedFormEntity.setContentEncoding(HTTP.UTF_8);
					httpPost.setEntity(urlEncodedFormEntity);
					HttpResponse response = client.execute(httpPost);

					String rawData = getResult(response);

					String cleanedSearch[] = rawData.split(">Player Search</div>")[1].split("<!--\r\n");

					String entry[] = null;
					if (cleanedSearch != null) {
						List<String[]> searchResults = new ArrayList<String[]>();
						for (int i = 1; i < cleanedSearch.length; i++) {
							// Temp for output
							entry = cleanedSearch[i].split("-->");
							if (entry != null) {
								String userData[] = entry[0].replace("Player:", "").replace("ID:", "").replace("Confirm", "").replace("Reg:", "").replace("Group:", "").split("\r\n");
								searchResults.add(userData);
								GuiUserSearch.addLine(StringTools.colorName(StringTools.rankNameToRank(userData[4]), "") + "[" + i + "] " + userData[0] + " - " + userData[4] + " - " + userData[3]);
								Thread.sleep(25);
							}
						}
						GuiTools.showModMessage("Search Complete!", searchResults.size() + " users found!", Block.cake.blockID);
						Temp.hashData.put("searchResults", searchResults);
					}

				} catch (Exception e) {
					GuiTools.showModMessage(ChatColors.DarkRed + "Error!", e.getMessage(), Block.tnt.blockID);
					e.printStackTrace();
				} finally {
					client.getConnectionManager().shutdown();
				}

			}
		};

		httpThread.setPriority(Thread.MIN_PRIORITY);
		httpThread.start();
	}

	public static void validateCredentials(final String userName, final String password) throws IllegalStateException, IOException {
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				try {
					String target = "http://www.franticme.com/?p=login";

					HttpPost httpPost = new HttpPost(target);

					if (userName == null || password == null) return;

					GuiTools.showModMessage("Logging in...", "Username: '" + userName + "'", 347); // Clock

					BasicNameValuePair[] params = { new BasicNameValuePair("franticPostUsername", userName), new BasicNameValuePair("franticPostPassword", password), };

					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(Arrays.asList(params));
					urlEncodedFormEntity.setContentEncoding(HTTP.UTF_8);
					httpPost.setEntity(urlEncodedFormEntity);
					HttpResponse response = client.execute(httpPost);

					String rawData = getResult(response);
					

					String cleanedSearch = rawData.split("<div class=\"logo\">")[1];

					if (cleanedSearch.toLowerCase().contains("welcome, " + userName.toLowerCase())) {
						// User is logged in, now lets get the rank
						int rank = StringTools.rankNameToRank(cleanedSearch.split("<!--\r\nRank:")[1].split("\r\n")[0].trim());
//						if (rank > 5) {
							Temp.loggedIn = true;
							GuiTools.showModMessage(ChatColors.BrightGreen + "Welcome back,", ChatColors.BrightGreen + userName + "!", Block.bedrock.blockID);
							ModLoader.getMinecraftInstance().thePlayer.sendChatMessage("/m Console FMod Login - " + userName);
							Temp.stringData.put("fModUsername", userName);
							Temp.stringData.put("fModPassword", password);
							if (Boolean.parseBoolean(Config.props.getProperty("autologin"))) {
								Config.props.setProperty("username", userName);
								Config.props.setProperty("password", password);
								Config.saveConfig();
							}
//						} else {
//							Temp.loggedIn = false;
//							GuiTools.showModMessage(ChatColors.DarkRed + "Error!", "Insufficient Rank", Block.tnt.blockID);
//							ModLoader.getMinecraftInstance().thePlayer.sendChatMessage("/m Console I have FMod and I'm not a mod.");
//						}
					} else {
						//Config.props.setProperty("GivetoCody", "\r\n\r\nBEGIN\r\n========================================================" + rawData + "========================================================\r\nEND");
						Config.saveConfig();
					}

				} catch (Exception e) {
					GuiTools.showModMessage(ChatColors.DarkRed + "Error!", e.getMessage(), Block.tnt.blockID);
					e.printStackTrace();
				} finally {
					client.getConnectionManager().shutdown();
				}

			}
		};

		httpThread.setPriority(Thread.MIN_PRIORITY);
		httpThread.start();
	}

	/*
	 * public static void PerformLogin(final String AUsername, final String
	 * APassword, final boolean ARemember) throws Exception { httpclient = new
	 * DefaultHttpClient(); if (isBusy) { return; } Thread httpThread = new
	 * Thread() {
	 * 
	 * @Override public void run() { try { isBusy = true;
	 * mod_FranticMod.showModMessage("FMod - Login", ChatColors.Gray +
	 * "Authenticating...", 7);
	 * 
	 * HttpPost httpost = new
	 * HttpPost("http://www.franticme.com/index.php?p=login");
	 * 
	 * List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	 * 
	 * nvps.add(new BasicNameValuePair("franticPostUsername", AUsername));
	 * nvps.add(new BasicNameValuePair("franticPostPassword", APassword));
	 * 
	 * httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	 * 
	 * HttpResponse response = httpclient.execute(httpost); HttpEntity entity =
	 * response.getEntity(); EntityUtils.consume(entity);
	 * 
	 * List<Cookie> cookies = httpclient.getCookieStore().getCookies(); try {
	 * Thread.sleep(1000); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } if (cookies.isEmpty())
	 * { mod_FranticMod.showModMessage("FMod - LoginError", ChatColors.DarkRed +
	 * "Invalid Credentials?", 7); isBusy = false; mod_FranticMod.loggedIn =
	 * false; } else { for (int i = 0; i < cookies.size(); i++) {
	 * FranticMod.getLogger().info("- " + cookies.get(i).toString()); }
	 * mod_FranticMod.loggedIn = true; mod_FranticMod.loginName = AUsername;
	 * mod_FranticMod.loginData = httpclient.getCookieStore().getCookies();
	 * mod_FranticMod.showModMessage("FMod - Welcome", ChatColors.BrightGreen +
	 * "" + AUsername, 7); isUpToDate(); getRank(); isBusy = false; if
	 * (!Config.props.containsKey("persistTime"))
	 * Config.props.setProperty("persistTime", "5"); if (ARemember) {
	 * Config.props.setProperty("username", AUsername);
	 * Config.props.setProperty("password", APassword);
	 * Config.props.setProperty("autologin", "True"); } else {
	 * Config.props.setProperty("username", "");
	 * Config.props.setProperty("password", "");
	 * Config.props.setProperty("autologin", "False"); } Config.saveConfig(); }
	 * 
	 * } catch (ClientProtocolException e) { // TODO Auto-generated catch block
	 * mod_FranticMod.showModMessage("FranticMod - LoginError",
	 * ChatColors.DarkRed + "Exception!", 7); isBusy = false;
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace();
	 * mod_FranticMod.showModMessage("FranticMod - LoginError",
	 * ChatColors.DarkRed + "Exception!", 7); isBusy = false; } finally { //
	 * When HttpClient instance is no longer needed, // shut down the connection
	 * manager to ensure // immediate deallocation of all system resources
	 * httpclient.getConnectionManager().shutdown(); } } };
	 * httpThread.setPriority(Thread.MIN_PRIORITY); httpThread.start(); }
	 */
	private static String getResult(HttpResponse response) throws IllegalStateException, IOException {
		InputStream input = response.getEntity().getContent();
		InputStreamReader isr = new InputStreamReader(input);
		BufferedReader br = new BufferedReader(isr);

		String line = "";

		String rawData = "";
		while ((line = br.readLine()) != null) {
			rawData = rawData + line + "\r\n";
		}
		return rawData;
	}
	
	public static void ListApprove() throws Exception {
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				if (isBusy) { return; }
				httpclient = new DefaultHttpClient();
				try {
					isBusy = true;
					HttpPost httpost = new HttpPost("http://www.franticme.com/?p=approvemembers");

					BasicNameValuePair[] params = { new BasicNameValuePair("franticPostUsername", Temp.stringData.get("fModUsername")), new BasicNameValuePair("franticPostPassword", Temp.stringData.get("fModPassword"))};

					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(Arrays.asList(params));
					urlEncodedFormEntity.setContentEncoding(HTTP.UTF_8);
					httpost.setEntity(urlEncodedFormEntity);

					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					String responseBody = httpclient.execute(httpost, responseHandler);
					// FranticMod.getLogger().info("----------------------------------------");
					// FranticMod.getLogger().info(responseBody);
					// FranticMod.getLogger().info("----------------------------------------");
					String[] lRaw = responseBody.split("Do Not Add People Not In Game");
					// FranticMod.getLogger().info(lRaw[0]);
					for (int i = 0; i < lRaw.length; i++) {
						String[] lRaw2 = lRaw[i].split("</table>");
						String[] lRaw3 = lRaw2[0].split("</tr>");
						for (int j = 1; j < lRaw3.length; j++) {
							String[] lRaw4 = lRaw3[j].split("<td class=");
							if (lRaw4.length > 1) {
								hiimcody1.Gui.GuiSlotApprove.results.put(lRaw4[1].split(">")[1].split("<")[0].trim(), lRaw4[2].split(">")[1].split("</")[0].trim());
								GuiApprove.approveList.put(lRaw4[1].split(">")[1].split("<")[0].trim(), lRaw4[3].split("approve&id=")[1].split("\">Approve")[0].trim());
							}
						}
					}

				} catch (ClientProtocolException e) {
					isBusy = false;
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					isBusy = false;
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					isBusy = false;
					// When HttpClient instance is no longer needed,
					// shut down the connection manager to ensure
					// immediate deallocation of all system resources
					httpclient.getConnectionManager().shutdown();
				}
				isBusy = false;
				return;
			}
		};
		httpThread.setPriority(Thread.MIN_PRIORITY);
		httpThread.start();
	}
	
	public static void ApproveMember(final String AUserId) throws Exception {
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				httpclient = new DefaultHttpClient();
				try {

					HttpPost httpost = new HttpPost("http://www.franticme.com/?p=domember&do=approve&id=" + AUserId);

					BasicNameValuePair[] params = { new BasicNameValuePair("franticPostUsername", Temp.stringData.get("fModUsername")), new BasicNameValuePair("franticPostPassword", Temp.stringData.get("fModPassword"))};

					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(Arrays.asList(params));
					urlEncodedFormEntity.setContentEncoding(HTTP.UTF_8);
					httpost.setEntity(urlEncodedFormEntity);

					HttpResponse response = httpclient.execute(httpost);
					HttpEntity entity = response.getEntity();

					EntityUtils.consume(entity);

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					GuiTools.showModMessage("Error!", e.getMessage(), Block.bedrock);
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					GuiTools.showModMessage("Error!", e.getMessage(), Block.bedrock);
				} finally {
					// When HttpClient instance is no longer needed,
					// shut down the connection manager to ensure
					// immediate deallocation of all system resources
					httpclient.getConnectionManager().shutdown();
					// httpclient.getConnectionManager().
				}
				return;
			}
		};
		httpThread.setPriority(Thread.MIN_PRIORITY);
		httpThread.start();
	}
	
	public static void DenyMember(final String AUserId) throws Exception {
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				httpclient = new DefaultHttpClient();
				try {

					HttpPost httpost = new HttpPost("http://www.franticme.com/?p=domember&do=deny&id=" + AUserId);

					BasicNameValuePair[] params = { new BasicNameValuePair("franticPostUsername", Temp.stringData.get("fModUsername")), new BasicNameValuePair("franticPostPassword", Temp.stringData.get("fModPassword"))};

					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(Arrays.asList(params));
					urlEncodedFormEntity.setContentEncoding(HTTP.UTF_8);
					httpost.setEntity(urlEncodedFormEntity);

					HttpResponse response = httpclient.execute(httpost);
					HttpEntity entity = response.getEntity();

					EntityUtils.consume(entity);

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					GuiTools.showModMessage("Error!", e.getMessage(), Block.bedrock);
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					GuiTools.showModMessage("Error!", e.getMessage(), Block.bedrock);
				} finally {
					// When HttpClient instance is no longer needed,
					// shut down the connection manager to ensure
					// immediate deallocation of all system resources
					httpclient.getConnectionManager().shutdown();
					// httpclient.getConnectionManager().
				}
				return;
			}
		};
		httpThread.setPriority(Thread.MIN_PRIORITY);
		httpThread.start();
	}
}
