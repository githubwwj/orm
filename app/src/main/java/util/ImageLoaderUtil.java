package util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ormlite.wwj.com.ormlite.R;

public class ImageLoaderUtil {

	private static DisplayImageOptions options;
	public static final int LOAD_LOCAL = 1;
	public static final int LOAD_CONTENT_PROVIDER = 2;
	public static final int LOAD_ASSETS = 3;
	public static final int LOAD_DRAWABLE = 4;
	public static final int LOAD_NETWORK = 5;

	public static void loadLoacalImg(String imgUrl, ImageView iv) {
		loadLoacalImg(imgUrl, iv, listener, null);
	}


	public static void loadLoacalImg(String imgUrl, ImageView iv,
			ImageLoadingListener loadingListener,
			ImageLoadingProgressListener progressListener) {
		loadImg(imgUrl, iv, LOAD_LOCAL, R.drawable.default_pic,
				R.drawable.default_pic, R.drawable.default_pic,
				loadingListener, progressListener);
	}

	public static void loadDrawablelImg(String imgUrl, ImageView iv) {
		loadDrawableImg(imgUrl, iv, listener, null);
	}

	public static void loadDrawableImg(String imgUrl, ImageView iv,
									 ImageLoadingListener loadingListener,
									 ImageLoadingProgressListener progressListener) {
		loadImg(imgUrl, iv, LOAD_DRAWABLE, R.drawable.default_pic,
				R.drawable.default_pic, R.drawable.default_pic,
				loadingListener, progressListener);
	}

	public static void loadNetImage(String imgUrl, ImageView iv) {
		loadNetImage(imgUrl, iv, listener, null);
	}

	// public static void loadImg(String imgUrl, ImageView iv) {
	// options = new DisplayImageOptions.Builder()
	// .showImageForEmptyUri(R.drawable.empty_gray)
	// .showImageOnFail(R.drawable.empty_gray)
	// .showImageOnLoading(R.drawable.load)
	// .cacheInMemory(true)
	// .cacheOnDisk(true)
	// .build();
	// ImageLoader.getInstance().displayImage(imgUrl, iv, options, listener);
	// }

	public static void loadNetImage(String imgUrl, ImageView iv,
			ImageLoadingListener loadingListener,
			ImageLoadingProgressListener progressListener) {
		loadImg(imgUrl, iv, LOAD_NETWORK, R.drawable.default_pic,
				R.drawable.default_pic, R.drawable.default_pic,
				loadingListener, progressListener);
	}

	public static void loadNetAvatar(String imgUrl, ImageView iv) {
		loadNetAvatar(imgUrl, iv, listener, null);
	}

	public static void loadNetAvatar(String imgUrl, ImageView iv,
			ImageLoadingListener loadingListener,
			ImageLoadingProgressListener progressListener) {
		loadImg(imgUrl, iv, LOAD_NETWORK, R.drawable.avatar,
				R.drawable.avatar, R.drawable.avatar, loadingListener,
				progressListener);
	}

	/**
	 * 
	 * @param imgUrl
	 *            加载图片的地址
	 * @param iv
	 *            图片加载成功后显示
	 * @param loadFromWhere
	 *            加载图片的地址: 1SD卡 2content 3资源 4Drawable
	 * @param resLoading
	 *            加载时默认显示
	 * @param resDefault
	 *            加载空图片时默认显示
	 * @param resFail
	 *            加载失败时默认显示
	 * @param loadingListener
	 *            加载监听
	 * @param progressListener
	 *            进度监听
	 */
	public static void loadImg(String imgUrl, ImageView iv, int loadFromWhere,
			int resLoading, int resDefault, int resFail,
			ImageLoadingListener loadingListener,
			ImageLoadingProgressListener progressListener) {
		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(resLoading) // 设置图片在下载期间显示的图片
//				.showImageForEmptyUri(resDefault)// 设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(resFail) // 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(false)// 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true)
				.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				.resetViewBeforeLoading(true)   // 设置图片在下载前是否重置，复位
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的下载前的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				// .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
				// .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
				.build();// 构建完成

		if (loadFromWhere == LOAD_LOCAL) {
			imgUrl =Scheme.FILE + imgUrl;
		} else if (loadFromWhere == LOAD_CONTENT_PROVIDER) {
			imgUrl =Scheme.CONTENT + imgUrl;
		} else if (loadFromWhere == LOAD_ASSETS) {
			imgUrl = Scheme.ASSETS.wrap(imgUrl);
		} else if (loadFromWhere == LOAD_DRAWABLE) {
			imgUrl = Scheme.DRAWABLE.wrap(imgUrl);
		}
		ImageLoader.getInstance().displayImage(imgUrl, iv, options,loadingListener, progressListener);
	}

	public static void loadImgNoRest(String imgUrl, ImageView iv) {
		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(resLoading) // 设置图片在下载期间显示的图片
//				.showImageForEmptyUri(resDefault)// 设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(resFail) // 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true).
				 considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				.build();// 构建完成
		ImageLoader.getInstance().displayImage(imgUrl, iv, options);
	}
	
	public static void loadRoundImg(Context context, String imgUrl, ImageView iv) {
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.avatar)
				.showImageOnFail(R.drawable.avatar)
				.showImageOnLoading(R.drawable.avatar)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 设置图片的解码类型//
				.cacheInMemory(true).cacheOnDisk(true)
				.resetViewBeforeLoading(true)
				.displayer(new RoundedBitmapDisplayer(CommUtils.dp2px(context, 70)))
				.build();
		ImageLoader.getInstance().displayImage(imgUrl, iv, options, listener);
	}
	
	public static void loadRoundImgNoAvatar(Context context, String imgUrl, ImageView iv) {
		options = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 设置图片的解码类型//
				.cacheInMemory(true).cacheOnDisk(true)
				.resetViewBeforeLoading(true)
				.displayer(new RoundedBitmapDisplayer(CommUtils.dp2px(context, 70)))
				.build();
		ImageLoader.getInstance().displayImage(imgUrl, iv, options, listener);
	}
	

	public static void loadLocalRoundImg(Context context, String imgUrl,
			ImageView iv) {
		options = new DisplayImageOptions.Builder()
//				.showImageForEmptyUri(R.drawable.default_pic)
//				.showImageOnFail(R.drawable.default_pic)
//				.showImageOnLoading(R.drawable.default_pic)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.considerExifParams(true)
				// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 设置图片的解码类型//
				.cacheInMemory(true).cacheOnDisk(true)
				.resetViewBeforeLoading(true)
				.displayer(new RoundedBitmapDisplayer(CommUtils.dp2px(context, 70)))
				.build();
		ImageLoader.getInstance().displayImage("file:///" + imgUrl, iv,options, listener);
	}

	private static ImageLoadingListener listener = new ImageLoadingListener() {

		List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingStarted(String arg0, View arg1) {

		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

		}

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
			if (bitmap != null) {
				ImageView iv = (ImageView) view;
				boolean isFirstDisplay = !displayedImages.contains(imageUri);
				if (isFirstDisplay) {
					FadeInBitmapDisplayer.animate(iv, 500);
					displayedImages.add(imageUri);
				}
			}
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {

		}
	};
	
	public static void pause(){
		ImageLoader.getInstance().pause();
	}
	
	public static void resume(){
		ImageLoader.getInstance().resume();
	}
	
	public static void stop(){
		ImageLoader.getInstance().stop();
	}

	public static void destroy(){
		ImageLoader.getInstance().destroy();
	}
	
}
