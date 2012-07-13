package net.minecraft.FranticMod.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.FranticMod.FranticMod;
import net.minecraft.FranticMod.Gui.GuiBabyVote;
import net.minecraft.FranticMod.Gui.GuiSlotApprove;
import net.minecraft.FranticMod.Gui.GuiSlotBaby;
import net.minecraft.FranticMod.Gui.GuiSlotPlayer;
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

public class UrlLib {

	static DefaultHttpClient httpclient = new DefaultHttpClient();
	public static boolean isBusy = false;
	
	public static void getRankByName(final String name) {
		try {
			setUserToVar(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setUserToVar(final String AUser) throws Exception {
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				if (isBusy) { return; }
				httpclient = new DefaultHttpClient();
				for (int i = 0; i < mod_FranticMod.loginData.size(); i++) {
					httpclient.getCookieStore().addCookie(mod_FranticMod.loginData.get(i));
				}
				try {
					isBusy = true;
					HttpPost httpost = new HttpPost("http://www.franticme.com/?p=search");

					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("searchName", AUser));

					httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					String responseBody = httpclient.execute(httpost, responseHandler);
					// FranticMod.getLogger().info("----------------------------------------");
					// FranticMod.getLogger().info(responseBody);
					// FranticMod.getLogger().info("----------------------------------------");
					String[] lRaw = responseBody.split("names containing");
					String curName = "";
					String curGroup = "";
					String curId = "";
					String curConfirmed = "";
					String curRegDate = "";
					String[] lRaw2 = lRaw[1].split("</div>");
					if (lRaw2[0].split("<hr>").length > 0) {
						for (int i = 0; i < lRaw2[0].split("<hr>").length; i++) {
							String[] lTemp = lRaw2[0].split("<hr>")[i].split("<!--\r\n");
							String[] lTemp2 = lTemp[1].split("-->");
							String[] lTemp3 = lTemp2[0].split("\r\n");
							for (int r = 0; r < lTemp3.length; r++) {
								if (lTemp3[r].contains("Player:")) curName = lTemp3[r].replace("Player:", "");
								if (lTemp3[r].contains("ID:")) curId = lTemp3[r].replace("ID:", "");
								if (lTemp3[r].contains("Confirm")) curConfirmed = lTemp3[r].replace("Confirm", "");
								if (lTemp3[r].contains("Reg:")) curRegDate = lTemp3[r].replace("Reg:", "");
								if (lTemp3[r].contains("Group:")) curGroup = lTemp3[r].replace("Group:", "");
							}

							if (curGroup != "" && curRegDate != "" && curConfirmed != "" && curId != "" && curName.equals(AUser)) {
								HashMap<String, String> tempMap = new HashMap<String, String>();
								tempMap.put("regname", curName);
								tempMap.put("regdate", curRegDate);
								tempMap.put("confirmed", curConfirmed);
								tempMap.put("id", curId);
								tempMap.put("group", curGroup);
								FranticMod.tempData.put(AUser, tempMap);
								httpclient.getConnectionManager().shutdown();
								isBusy=false;
								return;
							}
						}
					}

				}catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Apparently "+AUser+" pissed off the system...");
					HashMap<String, String> tempMap = new HashMap<String, String>();
					tempMap.put("regname", AUser);
					tempMap.put("regdate", "null");
					tempMap.put("confirmed", "null");
					tempMap.put("id", "null");
					tempMap.put("group", "Guest");
					FranticMod.tempData.put(AUser, tempMap);
					isBusy = false;
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (ClientProtocolException e) {
					HashMap<String, String> tempMap = new HashMap<String, String>();
					tempMap.put("regname", AUser);
					tempMap.put("regdate", "null");
					tempMap.put("confirmed", "null");
					tempMap.put("id", "null");
					tempMap.put("group", "Guest");
					FranticMod.tempData.put(AUser, tempMap);
					isBusy = false;
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					HashMap<String, String> tempMap = new HashMap<String, String>();
					tempMap.put("regname", AUser);
					tempMap.put("regdate", "null");
					tempMap.put("confirmed", "null");
					tempMap.put("id", "null");
					tempMap.put("group", "Guest");
					FranticMod.tempData.put(AUser, tempMap);
					isBusy = false;
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					//FranticMod.tempData.put(AUser,(new HashMap<String, String>()).put("group", "Guest"));
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
	
	public static void PostBan(final String AName, final String AReason, final String ADuration) {
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				httpclient = new DefaultHttpClient();
				for (int i = 0; i < mod_FranticMod.loginData.size(); i++) {
					httpclient.getCookieStore().addCookie(mod_FranticMod.loginData.get(i));
				}
				try {

					HttpPost httpost = new HttpPost("http://www.franticme.com/dopost.php?f=12");

					List<NameValuePair> nvps = new ArrayList<NameValuePair>();

					nvps.add(new BasicNameValuePair("newPostSubject", (ADuration + " Ban: " + AName + " (" + AReason + ")")));
					nvps.add(new BasicNameValuePair("newPostPost", "[b]Player: [/b]" + AName + "\r\n" + "[b]Banned By: [/b]" + ModLoader.getMinecraftInstance().session.username + "\r\n" + "[b]Time Banned: [/b]" + "\r\n" + "[b]Duration: [/b]" + ADuration + "\r\n" + "[b]Reason: [/b]" + AReason + "\r\n" + "[b]Evidence: [/b]" + "\r\n" + "[b]Addt�l Info: [/b]\r\n"));
					// nvps.add(new BasicNameValuePair("franticPostUsername",
					// Config.props.getProperty("username")));
					// nvps.add(new BasicNameValuePair("franticPostPassword",
					// Config.props.getProperty("password")));
					httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

					HttpResponse response = httpclient.execute(httpost);
					HttpEntity entity = response.getEntity();

					EntityUtils.consume(entity);

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					mod_FranticMod.showMessage(ChatColors.Red + "Login failed!");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mod_FranticMod.showMessage(ChatColors.Red + "Login failed!");
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

	public static void UploadFile(String AFilename, String AFilepath) throws ClientProtocolException, IOException {
		final String name = ModLoader.getMinecraftInstance().session.username + "/" + AFilename;
		final String path = AFilepath;
		new Thread() {
			public void run() {
				// mod_FranticMod.ShowMessage("Uploading Screenshot", 4000,
				// true);
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://pokemonnp.com/Frantic/Screenshots/upload.php");

				MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

				try {
					reqEntity.addPart("filename", new StringBody(name));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				FileBody bin = new FileBody(new File(path));
				reqEntity.addPart("thefile", bin);

				httppost.setEntity(reqEntity);

				FranticMod.getLogger().info("executing request " + httppost.getRequestLine());
				HttpResponse response = null;
				try {
					response = httpclient.execute(httppost);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpEntity resEntity = response.getEntity();

				if (resEntity != null) {
					String page = null;
					try {
						page = EntityUtils.toString(resEntity);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					FranticMod.getLogger().info("PAGE :" + page);
					// mod_FranticMod.ShowMessage("Screenshot Uploaded", 4000,
					// false);
					// UrlLib.hasScreenshot = true;
				}
			}
		}.start();
	}
	
	public static void getRank() {
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				
				DefaultHttpClient rankClient = new DefaultHttpClient();
				for (int i = 0; i < mod_FranticMod.loginData.size(); i++) {
					rankClient.getCookieStore().addCookie(mod_FranticMod.loginData.get(i));
				}
				try {
					
					HttpPost httpost = new HttpPost("http://www.franticme.com/");

					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("null", null));

					httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					String responseBody = rankClient.execute(httpost, responseHandler);
					String[] lRow = responseBody.split("<!--\r\n");
					//System.out.println(lRow[1]);
					String lRankName = lRow[1].split("Rank:")[1].split("\r\n")[0].trim();
					mod_FranticMod.rank = mod_FranticMod.rankNameToRank(lRankName);
					System.out.println("Rank '"+lRankName+"' = "+mod_FranticMod.rank);
				} catch (ClientProtocolException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					
					// When HttpClient instance is no longer needed,
					// shut down the connection manager to ensure
					// immediate deallocation of all system resources
					rankClient.getConnectionManager().shutdown();
				}
				
				return;
			}
		};
		httpThread.setPriority(Thread.MIN_PRIORITY);
		httpThread.start();
	}
	
	
	public static void PerformLogin(final String AUsername, final String APassword, final boolean ARemember) throws Exception {
		httpclient = new DefaultHttpClient();
		if (isBusy) { return; }
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				try {
					isBusy = true;
					mod_FranticMod.showModMessage("FMod - Login", ChatColors.Gray + "Authenticating...", 7);

					HttpPost httpost = new HttpPost("http://www.franticme.com/index.php?p=login");

					List<NameValuePair> nvps = new ArrayList<NameValuePair>();

					nvps.add(new BasicNameValuePair("franticPostUsername", AUsername));
					nvps.add(new BasicNameValuePair("franticPostPassword", APassword));

					httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
					
					HttpResponse response = httpclient.execute(httpost);
					HttpEntity entity = response.getEntity();
					// TODO DETERMINE RANK
					EntityUtils.consume(entity);

					List<Cookie> cookies = httpclient.getCookieStore().getCookies();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (cookies.isEmpty()) {
						mod_FranticMod.showModMessage("FMod - LoginError", ChatColors.DarkRed + "Invalid Credentials?", 7);
						isBusy = false;
						mod_FranticMod.loggedIn = false;
					} else {
						for (int i = 0; i < cookies.size(); i++) {
							FranticMod.getLogger().info("- " + cookies.get(i).toString());
						}
						mod_FranticMod.loggedIn = true;
						mod_FranticMod.loginName = AUsername;
						mod_FranticMod.loginData = httpclient.getCookieStore().getCookies();
						mod_FranticMod.showModMessage("FMod - Welcome", ChatColors.BrightGreen + "" + AUsername, 7);
						isUpToDate();
						getRank();
						isBusy = false;
						if (!Config.props.containsKey("persistTime")) Config.props.setProperty("persistTime", "5");
						if (ARemember) {
							Config.props.setProperty("username", AUsername);
							Config.props.setProperty("password", APassword);
							Config.props.setProperty("autologin", "True");
						} else {
							Config.props.setProperty("username", "");
							Config.props.setProperty("password", "");
							Config.props.setProperty("autologin", "False");
						}
						Config.saveConfig();
					}

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					mod_FranticMod.showModMessage("FranticMod - LoginError", ChatColors.DarkRed + "Exception!", 7);
					isBusy = false;
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mod_FranticMod.showModMessage("FranticMod - LoginError", ChatColors.DarkRed + "Exception!", 7);
					isBusy = false;
				} finally {
					// When HttpClient instance is no longer needed,
					// shut down the connection manager to ensure
					// immediate deallocation of all system resources
					httpclient.getConnectionManager().shutdown();
				}
			}
		};
		httpThread.setPriority(Thread.MIN_PRIORITY);
		httpThread.start();
	}

	public static void promote(final String AUserId, final String AGroupId) throws Exception {
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				httpclient = new DefaultHttpClient();
				for (int i = 0; i < mod_FranticMod.loginData.size(); i++) {
					httpclient.getCookieStore().addCookie(mod_FranticMod.loginData.get(i));
				}
				try {

					HttpPost httpost = new HttpPost("http://www.franticme.com/?p=whitelist");

					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("do", "move"));
					nvps.add(new BasicNameValuePair("moved", AUserId));
					nvps.add(new BasicNameValuePair("targetGroup", AGroupId));
					// nvps.add(new BasicNameValuePair("franticPostUsername",
					// Config.props.getProperty("username")));
					// nvps.add(new BasicNameValuePair("franticPostPassword",
					// Config.props.getProperty("password")));
					httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

					HttpResponse response = httpclient.execute(httpost);
					HttpEntity entity = response.getEntity();

					EntityUtils.consume(entity);

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					mod_FranticMod.showMessage("Login failed!");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mod_FranticMod.showMessage("Login failed!");
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

	public static void ApproveMember(final String AUserId) throws Exception {
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				httpclient = new DefaultHttpClient();
				for (int i = 0; i < mod_FranticMod.loginData.size(); i++) {
					httpclient.getCookieStore().addCookie(mod_FranticMod.loginData.get(i));
				}
				try {

					HttpPost httpost = new HttpPost("http://www.franticme.com/?p=domember&do=approve&id=" + AUserId);

					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("null", "null"));
					// nvps.add(new BasicNameValuePair("franticPostUsername",
					// Config.props.getProperty("username")));
					// nvps.add(new BasicNameValuePair("franticPostPassword",
					// Config.props.getProperty("password")));
					httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

					HttpResponse response = httpclient.execute(httpost);
					HttpEntity entity = response.getEntity();

					EntityUtils.consume(entity);

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					mod_FranticMod.showMessage(ChatColors.Red + "Login failed!");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mod_FranticMod.showMessage(ChatColors.Red + "Login failed!");
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

	public static void download_zip_file(String version) {
		try {
			/*
			 * Get a connection to the URL and start up a buffered reader.
			 */
			long startTime = System.currentTimeMillis();

			mod_FranticMod.queuePersistentModMessage("FMod - Updating", "Starting...", Block.sponge.blockID, 20 * 1000);

			URL url = new URL("http://www.pokemonnp.com/Frantic/FMod/mod_FranticMod(" + version + ").zip");
			url.openConnection();
			InputStream reader = url.openStream();

			/*
			 * Setup a buffered file writer to write out what we read from the
			 * website.
			 */
			FileOutputStream writer = new FileOutputStream(Minecraft.getMinecraftDir() + "/mods/mod_FranticMod(" + version + ").zip");
			File f = new File(Minecraft.getMinecraftDir() + "/mods/mod_FranticMod(" + mod_FranticMod.version + ").zip");
			f.delete();
			byte[] buffer = new byte[153600];
			int totalBytesRead = 0;
			int bytesRead = 0;

			System.out.println("Reading ZIP file 150KB blocks at a time.\n");

			while ((bytesRead = reader.read(buffer)) > 0) {
				writer.write(buffer, 0, bytesRead);
				buffer = new byte[153600];
				totalBytesRead += bytesRead;
				mod_FranticMod.persistText = (totalBytesRead + " bytes downloaded.");
			}

			long endTime = System.currentTimeMillis();

			System.out.println("Done. " + (new Integer(totalBytesRead).toString()) + " bytes read (" + (new Long(endTime - startTime).toString()) + " millseconds).\n");
			mod_FranticMod.persistText = (totalBytesRead + " bytes downloaded in " + (new Long(endTime - startTime).toString()) + "ms");
			mod_FranticMod.persistTitle = "FMod - Updated!";
			mod_FranticMod.persistDrop = System.currentTimeMillis() + 1000L + (2 * 1000);
			ModLoader.getMinecraftInstance().thePlayer.addChatMessage(ChatColors.DarkRed+"Please Restart Minecraft to update FMod!");
			writer.close();
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void isUpToDate() {
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				if (isBusy) { return; }
				DefaultHttpClient miniClient = new DefaultHttpClient();
				try {
					isBusy = true;
					HttpPost httpost = new HttpPost("http://www.pokemonnp.com/Frantic/currentversion.txt");

					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("null", null));

					httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					String responseBody = miniClient.execute(httpost, responseHandler);
					if (responseBody.trim().equals(mod_FranticMod.version))
						mod_FranticMod.isUpToDate = true;
					else {
						System.out.println("Version Check: '"+mod_FranticMod.version+"' != '"+responseBody.trim()+"'");
						mod_FranticMod.isUpToDate = false;
						mod_FranticMod.newVersion = responseBody.trim();
						mod_FranticMod.showModMessage("FMod - Updates", "Update Available!", Block.sponge.blockID);
						mod_FranticMod.chatMessage(ChatColors.Yellow + "FMod '" + ChatColors.Gray + responseBody.trim() + ChatColors.Yellow + "' is available.");
						//mod_FranticMod.chatMessage(ChatColors.Yellow + "/yes or /no");
						//mod_FranticMod.AppData.setProperty("confirmUpdate", "true");
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
					miniClient.getConnectionManager().shutdown();
				}
				isBusy = false;
				return;
			}
		};
		httpThread.setPriority(Thread.MIN_PRIORITY);
		httpThread.start();
	}

	public static void SearchUser(final String AUser) throws Exception {
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				if (isBusy) { return; }
				httpclient = new DefaultHttpClient();
				for (int i = 0; i < mod_FranticMod.loginData.size(); i++) {
					httpclient.getCookieStore().addCookie(mod_FranticMod.loginData.get(i));
				}
				try {
					isBusy = true;
					HttpPost httpost = new HttpPost("http://www.franticme.com/?p=search");

					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("searchName", AUser));

					httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					String responseBody = httpclient.execute(httpost, responseHandler);
					// FranticMod.getLogger().info("----------------------------------------");
					// FranticMod.getLogger().info(responseBody);
					// FranticMod.getLogger().info("----------------------------------------");
					String[] lRaw = responseBody.split("names containing");
					String curName = "";
					String curGroup = "";
					String curId = "";
					String curConfirmed = "";
					String curRegDate = "";
					String[] lRaw2 = lRaw[1].split("</div>");
					if (lRaw2[0].split("<hr>").length > 0) {
						for (int i = 0; i < lRaw2[0].split("<hr>").length; i++) {
							String[] lTemp = lRaw2[0].split("<hr>")[i].split("<!--\r\n");
							String[] lTemp2 = lTemp[1].split("-->");
							String[] lTemp3 = lTemp2[0].split("\r\n");
							for (int r = 0; r < lTemp3.length; r++) {
								if (lTemp3[r].contains("Player:")) curName = lTemp3[r].replace("Player:", "");
								if (lTemp3[r].contains("ID:")) curId = lTemp3[r].replace("ID:", "");
								if (lTemp3[r].contains("Confirm")) curConfirmed = lTemp3[r].replace("Confirm", "");
								if (lTemp3[r].contains("Reg:")) curRegDate = lTemp3[r].replace("Reg:", "");
								if (lTemp3[r].contains("Group:")) curGroup = lTemp3[r].replace("Group:", "");
							}

							if (curGroup != "" && curRegDate != "" && curConfirmed != "" && curId != "" && curName != "") {
								GuiSlotPlayer.results.put(curName, curGroup);
								HashMap<String, String> tempMap = new HashMap<String, String>();
								tempMap.put("regdate", curRegDate);
								tempMap.put("confirmed", curConfirmed);
								tempMap.put("id", curId);
								tempMap.put("group", curGroup);
								mod_FranticMod.playerList.put(curName, tempMap);
								curName = "";
								curRegDate = "";
								curConfirmed = "";
								curId = "";
								curGroup = "";
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

	public static void ListApprove() throws Exception {
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				if (isBusy) { return; }
				httpclient = new DefaultHttpClient();
				for (int i = 0; i < mod_FranticMod.loginData.size(); i++) {
					httpclient.getCookieStore().addCookie(mod_FranticMod.loginData.get(i));
				}
				try {
					isBusy = true;
					HttpPost httpost = new HttpPost("http://www.franticme.com/?p=approvemembers");

					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("null", "null"));

					httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
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
								FranticMod.getLogger().info(lRaw4[1]);
								FranticMod.getLogger().info(lRaw4[3]);
								GuiSlotApprove.results.put(lRaw4[1].split(">")[1].split("<")[0].trim(), lRaw4[2].split(">")[1].split("</")[0].trim());
								mod_FranticMod.approveList.put(lRaw4[1].split(">")[1].split("<")[0].trim(), lRaw4[3].split("approve&id=")[1].split("\">Approve")[0].trim());
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
	
	public static void castBabyVote(final String name, final String vote) {
		//in support of
		//already voted
		//to reject
		
		Thread httpThread = new Thread() {

			@Override
			public void run() {
				if (isBusy) { return; }
				httpclient = new DefaultHttpClient();
				for (int i = 0; i < mod_FranticMod.loginData.size(); i++) {
					httpclient.getCookieStore().addCookie(mod_FranticMod.loginData.get(i));
				}
				try {
					isBusy = true;
					HttpPost httpost = new HttpPost("http://www.franticme.com/?p=dobabymod&do="+vote+"vote&id="+name);

					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("null", null));

					httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					String responseBody = httpclient.execute(httpost, responseHandler);
					if(responseBody.contains("in support of")) {
						mod_FranticMod.showModMessage("FMod - Vote", name+" +1", Block.blockDiamond.blockID);
					}
					if(responseBody.contains("to reject")) {
						mod_FranticMod.showModMessage("FMod - Vote", name+" -1", Block.tnt.blockID);
					}
					if(responseBody.contains("already voted")) {
						mod_FranticMod.showModMessage("FMod - Vote", ChatColors.Red+"You already voted.", Block.bedrock.blockID);
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
	
	public static void RetrieveBabies() throws Exception {
		Thread httpThread = new Thread() {

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void run() {
				if (isBusy) { return; }
				httpclient = new DefaultHttpClient();
				for (int i = 0; i < mod_FranticMod.loginData.size(); i++) {
					httpclient.getCookieStore().addCookie(mod_FranticMod.loginData.get(i));
				}
				try {
					HashMap<String, String[]> babyData = new HashMap<String, String[]>();
					FranticMod.tempData.put("babyData", babyData);
					String lTempSponsor = "";
					String lTempSponsoree = "";
					String lTempReason = "";
					String lTempUpVotes = "";
					String lTempDownVotes = "";
					isBusy = true;
					HttpPost httpost = new HttpPost("http://www.franticme.com/?p=babymodreview");

					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("null", "null"));

					httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					String responseBody = httpclient.execute(httpost, responseHandler);
					// FranticMod.getLogger().info("----------------------------------------");
					// FranticMod.getLogger().info(responseBody);
					// FranticMod.getLogger().info("----------------------------------------");
					String[] lRaw = responseBody.split("Nay");
					// FranticMod.getLogger().info(lRaw[0]);
					String[] lMain = lRaw[1].split("</table>");

					String[] lData = lMain[0].split("</tr>");

					for (int i = 1; i < lData.length; i++) {
						String[] lRows = lData[i].split("<!--");
						for (int l = 0; l < lRows.length; l++) {
							//System.out.println("Row "+l+": "+lRows[l]);
						}
						if(lRows.length == 2) {
							String[] lRows2 = lRows[1].split("-->");
							lTempReason = lRows2[0].split("Reason:")[1].split("Upvotes:")[0];
							String[] lRows3 = lRows2[0].split("\r\n");
							for (int r = 0; r < lRows3.length; r++) {
								 //
								 // Sponsor:juski814
								 // Sponsoree:CjWassel
								 // Reason:Test data, fuck this guy.
								 // Upvotes:0
								 // Downvotes:0
								 //
								if(lRows3[r].startsWith("Sponsor:")) lTempSponsor = lRows3[r].replace("Sponsor:", "").trim();
								if(lRows3[r].startsWith("Sponsoree:")) lTempSponsoree = lRows3[r].replace("Sponsoree:", "").trim();
								if(lRows3[r].startsWith("Upvotes:")) lTempUpVotes = lRows3[r].replace("Upvotes:", "").trim();
								if(lRows3[r].startsWith("Downvotes:")) lTempDownVotes = lRows3[r].replace("Downvotes:", "").trim();
								
								
								
							}
							String[] lTempArray = {lTempSponsor,lTempSponsoree,lTempUpVotes,lTempDownVotes,lTempReason};
							((HashMap) FranticMod.tempData.get("babyData")).put(lTempSponsoree,lTempArray);
							GuiSlotBaby.results.put(lTempSponsoree, lTempSponsor);
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
	
	// HOW TO REFACTOR THIS LATE TERM ABORTION OF A LIBRARY INTO SOMETHING USEFUL!
	// EXAMPLE:
	
	public static void example(String aNameOrSomething, ICallback<String /*The type of value that the thread gets*/> cb) {
		Thread t = new ExampleThread(cb);
			   t.setPriority(Thread.MIN_PRIORITY);
			   t.start();
	}
	
	public static class ExampleThread extends Thread {
		ICallback<String> cb;
		public ExampleThread(ICallback<String> cb) {this.cb = cb;}
		public void run() {
			// DO SHIT HERE
			cb.useValue("Insert your value here");
		}
	}
	
	// AND HOW TO USE THE VALUE:
	static String tempValue;
	public static void useExample() {
		example("Fucking example", new ICallback<String>() {
			public void useValue(String s) {
				tempValue = s;
			}});
		// someShit(tempValue);
	}
	
	// DELETE THIS CODE WHEN DONE
}
