package com.reactnativeshare.module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.d3o.R;
import com.d3o.sharemodule.utils.BitMapUtil;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.Map;

/**
 * Created by Song on 2017/7/10.
 */
public class ShareModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private Context context;
    private static Activity mActivity;
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void initActivity(Activity activity) {
        mActivity = activity;
    }

    public ShareModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    private static void runOnMainThread(Runnable task) {
        mHandler.post(task);
    }

    @Override
    public String getName() {
        return "sharemodule";
    }


    /**
     * 分享手机本地图片
     */
    @ReactMethod
    public void shareImg(String imgPath, final int platform, final Callback resultCallback) {

        final SHARE_MEDIA sharePlatform = getSharePlatform(platform);
        if(UMShareAPI.get(mActivity).isInstall(mActivity, sharePlatform)) {
            Bitmap img = BitmapFactory.decodeFile(BitMapUtil.getImageAbsolutePath(mActivity, Uri.parse(imgPath)));
            final UMImage image = new UMImage(mActivity, BitMapUtil.ImageCompress(img));
            runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    new ShareAction(mActivity)
                    .setPlatform(sharePlatform)
                    .withMedia(image)
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                            //分享开始的回调
                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            resultCallback.invoke("分享成功");
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            resultCallback.invoke("分享失败：" + throwable.getMessage());
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {
                            resultCallback.invoke("取消分享");
                        }
                    })
                    .share();
                }
            });
        }
    }

    /**
     * 分享drawable图片
     */
    @ReactMethod
    public void shareImage(final int platform, final Callback resultCallback) {

        final SHARE_MEDIA sharePlatform = getSharePlatform(platform);
        if(UMShareAPI.get(mActivity).isInstall(mActivity, sharePlatform)) {
            final UMImage image = new UMImage(mActivity, R.drawable.ic_socialshare_qrcode);
            runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    new ShareAction(mActivity)
                    .setPlatform(sharePlatform)
                    .withMedia(image)
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                            //分享开始的回调
                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            resultCallback.invoke("分享成功");
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            resultCallback.invoke("分享失败：" + throwable.getMessage());
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {
                            resultCallback.invoke("取消分享");
                        }
                    })
                    .share();
                }
            });
        }
    }

    /**
     * 分享链接
     * @param title
     * @param description
     * @param contentUrl
     * @param imgUrl
     * @param platform
     * @param resultCallback
     */
    @ReactMethod
    public void share(String title, String description, String contentUrl,
                      String imgUrl, final int platform, final Callback resultCallback) {
        final SHARE_MEDIA sharePlatform = getSharePlatform(platform);
        if(UMShareAPI.get(mActivity).isInstall(mActivity, sharePlatform)) {
            final UMWeb web = new UMWeb(contentUrl);
            web.setTitle(title); //标题
            web.setThumb(new UMImage(context, imgUrl));  //缩略图
            web.setDescription(description); //描述
            runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    new ShareAction(mActivity)
                    .setPlatform(sharePlatform)
                    .withMedia(web) // 分享链接
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                            //分享开始的回调
                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            resultCallback.invoke("分享成功");
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            resultCallback.invoke("分享失败：" + throwable.getMessage());
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {
                            resultCallback.invoke("取消分享");
                        }
                    })
                    .share();
                }
            });
        } else {
            resultCallback.invoke("未安装该软件");
        }
    }

    /**
     * 获取用户授权资料
     * 推荐直接使用该方式实现，因为本质上三方登录最终都需要拉取三方平台的用户资料，
     * 从这点来说，直接调用SDK和通过后台服务器请求，安全性是一样的
     */
    @ReactMethod
    public void authLogin(int platform, final Callback resultCallback) {

        final WritableMap result = new WritableNativeMap();
        final SHARE_MEDIA sharePlatform = getSharePlatform(platform);

        if(UMShareAPI.get(mActivity).isInstall(mActivity, sharePlatform)) {
            runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    UMShareAPI.get(mActivity).getPlatformInfo(mActivity, sharePlatform, new UMAuthListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                        }

                        @Override
                        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                            /**
                             *
                                 uid   用户唯一标识
                                 name	 用户昵称
                                 gender	 用户性别	该字段会直接返回男/女
                                 iconurl 用户头像
                             */
                            result.putInt("code", 0);
                            result.putString("userId", map.get("uid"));
                            result.putString("accessToken", map.get("accesstoken"));
                            result.putString("userName", map.get("name"));
                            result.putString("userGender", map.get("gender"));
                            result.putString("userAvatar", map.get("iconurl"));
                            resultCallback.invoke(result);
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                            result.putInt("code", 1);
                            resultCallback.invoke(result);
                            Log.e("--react-native-share--","授权登录失败: " + throwable.getMessage());
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media, int i) {
                            result.putInt("code", 2);
                            resultCallback.invoke(result);
                            Log.e("--react-native-share--","取消授权登录: " + i);
                        }
                    });
                }
            });
        } else {
            Log.e("--react-native-share--","设备未安装App软件" );
        }

    }


    /**
     * 分享或登录处理后的回调
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(mActivity).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onNewIntent(Intent intent) {
    }

    /**
     * 平台对应编号
     * @param platform
     * @return
     */
    private SHARE_MEDIA getSharePlatform(int platform){
        switch (platform) {
            case 0:
                return SHARE_MEDIA.QQ;
            case 1:
                return SHARE_MEDIA.SINA;
            case 2:
                return SHARE_MEDIA.WEIXIN;
            case 3:
                return SHARE_MEDIA.WEIXIN_CIRCLE;
            case 4:
                return SHARE_MEDIA.QZONE;
            case 5:
                return SHARE_MEDIA.FACEBOOK;
            default:
                return null;
        }
    }
}
