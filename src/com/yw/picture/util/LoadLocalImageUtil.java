package com.yw.picture.util;

import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.yw.picture.R;

/**
 * 异步加载本地图片工具�?
 * 
 * @author tony
 * 
 */
public class LoadLocalImageUtil {
	private LoadLocalImageUtil() {
	}

	private static LoadLocalImageUtil instance = null;

	public static synchronized LoadLocalImageUtil getInstance() {
		if (instance == null) {
			instance = new LoadLocalImageUtil();
		}
		return instance;
	}

	/**
	 * 从内存卡中异步加载本地图�?
	 * 
	 * @param uri
	 * @param imageView
	 */
	public void displayFromSDCard(String uri, ImageView imageView) {
		// String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
		ImageLoader.getInstance().displayImage("file://" + uri, imageView,
				initOptions());
	}

	/**
	 * 从assets文件夹中异步加载图片
	 * 
	 * @param imageName
	 *            图片名称，带后缀的，例如�?.png
	 * @param imageView
	 */
	public void dispalyFromAssets(String imageName, ImageView imageView) {
		// String imageUri = "assets://image.png"; // from assets
		ImageLoader.getInstance().displayImage("assets://" + imageName,
				imageView);
	}

	/**
	 * 从drawable中异步加载本地图�?
	 * 
	 * @param imageId
	 * @param imageView
	 */
	public void displayFromDrawable(int imageId, ImageView imageView) {
		// String imageUri = "drawable://" + R.drawable.image; // from drawables
		// (only images, non-9patch)
		ImageLoader.getInstance().displayImage("drawable://" + imageId,
				imageView);
	}

	/**
	 * 从内容提提供者中抓取图片
	 */
	public void displayFromContent(String uri, ImageView imageView) {
		// String imageUri = "content://media/external/audio/albumart/13"; //
		// from content provider
		ImageLoader.getInstance().displayImage("content://" + uri, imageView);
	}

	public static void initImageLoader(Context context) {
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
		HttpConnectionParams.setSoTimeout(params, 10 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		HttpClientParams.setRedirecting(params, false);
		HttpProtocolParams.setUserAgent(params, "some_randome_user_agent");
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));

		ClientConnectionManager manager = new ThreadSafeClientConnManager(
				params, schemeRegistry);

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())

				.discCacheSize(50 * 1024 * 1024)
				//
				.discCacheFileCount(300)
				// ����3����ͼƬ
				.writeDebugLogs()
				/*
				 * .imageDownloader( new HttpClientImageDownloader(context, new
				 * DefaultHttpClient(manager, params)))
				 */
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();

		ImageLoader.getInstance().init(config);
	}

	public static DisplayImageOptions initOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.trip_bg_index_default_test)
				.showImageForEmptyUri(R.drawable.trip_bg_index_default_test)
				.showImageOnFail(R.drawable.trip_bg_index_default_test)
				.cacheInMemory(true)
				// .cacheOnDisc(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
		return options;
	}

}
