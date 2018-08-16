package com.reactnativeshare;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.reactnativeshare.module.SharePackage;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.commonsdk.UMConfigure;
import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    public boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
              new SharePackage()
      );
    }
  };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this,false);
    // 此处配置类型，供后台分析各渠道时使用
    Config.shareType = "react native";
    // 初始化Umeng分享
    UMConfigure.init(this,"友盟Key","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
  }

  // 配置平台key、secret信息
  {
    PlatformConfig.setWeixin("微信Key", "微信Secret");
    PlatformConfig.setQQZone("qq app id", "QQ Secret");
    PlatformConfig.setSinaWeibo("微博key", "微博Secret", "www.baidu.com");
  }
}
