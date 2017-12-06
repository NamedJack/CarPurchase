package com.ejar.carpurchase.utils.net;//package com.ejar.huidou.Utils.net;
//
//import java.io.IOException;
//import java.security.PublicKey;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Random;
//
//import android.util.Log;
//
//import com.squareup.okhttp.Call;
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.FormEncodingBuilder;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.Response;
//
//
//
//
//public class OkHttpClientManager {
//	private static OkHttpClientManager mInstance;
//	private OkHttpClient mOkHttpClient;
//	private static final String TAG = "OkHttpClientManager";
//	private static PublicKey KEY;
//	// 时间戳
//	public String timestamp;
//	// 随机码
//	public String nonce;
//
//	private OkHttpClientManager() {
//		mOkHttpClient = new OkHttpClient();
//	}
//
//	public static OkHttpClientManager getInstance() {
//		if (mInstance == null) {
//			synchronized (OkHttpClientManager.class) {
//				if (mInstance == null) {
//					// 获取请求密钥
////					KEY = AppContext.getInstance().GetRequestKey();
//					mInstance = new OkHttpClientManager();
//				}
//			}
//		}
//		return mInstance;
//	}
//
//	/**
//	 * 同步的Get请求
//	 *
//	 * @param url
//	 * @return Response
//	 */
//	public void _get(String url) throws IOException {
//
//		Response execute = _get2(url);
//		Log.d("请求结果", execute.body().string());
//	}
//
//	/**
//	 * 同步的Get请求
//	 *
//	 * @param url
//	 * @return Response
//	 */
//	private Response _get2(String url) throws IOException {
//		final Request request = new Request.Builder().url(url).build();
//		Call call = mOkHttpClient.newCall(request);
//		Response execute = call.execute();
//		return execute;
//	}
//
//	/**
//	 * 异步的get请求
//	 *
//	 * @param url
//	 * @param callback
//	 * @return
//	 */
//	public void _getAsyn(String url, HashMap<String, String> params, Callback c) {
//		Call call = mOkHttpClient.newCall(buildRequest(url, "get", params));
//		// 请求加入调度
//		call.enqueue(c);
//	}
//
//	/**
//	 * 异步的post请求
//	 *
//	 * @param url
//	 * @param callback
//	 * @param params
//	 */
//	public void _postAsyn(String url, HashMap<String, String> params, Callback c) {
//		Call call = mOkHttpClient.newCall(buildRequest(url, "post", params));
//		// 请求加入调度
//		call.enqueue(c);
//	}
//	/**
//	 * 异步的put请求
//	 *
//	 * @param url
//	 * @param callback
//	 * @param params
//	 */
//	public void _putAsyn(String url, HashMap<String, String> params, Callback c) {
//		Call call = mOkHttpClient.newCall(buildRequest(url, "put", params));
//		// 请求加入调度
//		call.enqueue(c);
//	}
//	/***
//	 * 构造http请求
//	 *
//	 * @param url
//	 * @param params
//	 * @return
//	 */
//	private Request buildRequest(String url, String type,
//								 HashMap<String, String> params) {
//		// 生成随机数与时间戳
//		timeStamp_nonce();
//		FormEncodingBuilder builder = new FormEncodingBuilder();
//		//判断是否有参数
//		if (params!=null) {
//			Iterator iter = params.entrySet().iterator();
//			while (iter.hasNext()) {
//				Map.Entry entry = (Map.Entry) iter.next();
//				builder.add(String.valueOf(entry.getKey()),
//						String.valueOf(entry.getValue()));
//				Log.d(String.valueOf(entry.getKey()),
//						String.valueOf(entry.getValue()));
//
//			}
//		}
//		// 追加签名参数
////		builder.add("signature", GetSignature());
////		builder.add("timestamp", timestamp);
////		builder.add("nonce", nonce);
//		RequestBody requestBody = builder.build();
//		// Post请求类型
//		if (type.equals("post")) {
//			return new Request.Builder().url(url).post(requestBody).build();
//		}
//		// Get请求类型
//		if (type.equals("get")) {
//			Log.d("请求地址：",url);
//			return new Request.Builder().url(url).build();
//		}
//		// Put请求类型
//		if (type.equals("put")) {
//			return new Request.Builder().url(url).put(requestBody).build();
//		}
//		// Delete请求类型
//		if (type.equals("delete")) {
//			return new Request.Builder().url(url).delete(requestBody).build();
//		}
//		return null;
//
//	}
//
//	/***
//	 * 生成签名
//	 *
//	 * @return
//	 */
//	private String GetSignature() {
//		try {
//			String str = "1920100102" + timestamp + nonce;
//			String result = RsaHelper.encryptDataFromStr(str, KEY);
//			Log.d("生成签名", result);
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			EventBus.getDefault().post(new Msg_Event("数据在封装时发生异常！"));
//			return "";
//		}
//	}
//
//	/**
//	 * 生成随机数与时间戳
//	 *
//	 * @return
//	 */
//	public void timeStamp_nonce() {
//		int max = 10000;
//		int min = 30000;
//		Random random = new Random();
//		nonce = String.valueOf(random.nextInt(max) % (max - min + 1) + min);
//		long time = System.currentTimeMillis();
//		timestamp = String.valueOf(time / 1000);
//		Log.d("随机数", nonce);
//		Log.d("时间戳", timestamp);
//	}
//
//}
