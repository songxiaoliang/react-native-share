# react-native-share
#### [一行代码，双平台分享]  支持平台：【QQ】【QQ空间】【微信】【朋友圈】【微博】【FaceBook】
#### 详细配置：http://blog.csdn.net/u013718120/article/details/75040805

#### 【 Android平台配置 】
##### 1. app目录下创建 libs 文件夹，添加依赖文件【直接复制源码中 libs 目录即可】
##### 2. app / src / main 目录下创建 jniLibs 目录，添加JNI文件【直接复制源码中 jniLibs 目录即可】
##### 3. 包名目录下，引入所需交互代码【直接复制源码中 apshare、wxapi 、 WBShareActivity 、module 即可，注意import的路径是否正确】
##### 4. 在AndroidMainfest.xml文件下添加权限【直接复制源码即可】
    <uses-permission android:name="android.permission.INTERNET" />  
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />  
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />  
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />  
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>  
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>  
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />  
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />  
    <uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS" />  
##### 5. 在AndroidMainfest.xml文件下的 <application></application>中添加分享平台【直接复制源码即可】

    <activity  
        android:name=".WBShareActivity"  
        android:configChanges="keyboardHidden|orientation"  
        android:screenOrientation="portrait" >  
        <intent-filter>  
            <action android:name="com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY" />  
            <category android:name="android.intent.category.DEFAULT" />  
        </intent-filter>  
    </activity>  
    <activity  
        android:name="com.sina.weibo.sdk.component.WeiboSdkBrowser"  
        android:configChanges="keyboardHidden|orientation"  
        android:windowSoftInputMode="adjustResize"  
        android:exported="false" >  
    </activity>  
    <service android:name="com.sina.weibo.sdk.net.DownloadService"  
        android:exported="false"></service>  
    <activity  
        android:name=".wxapi.WXEntryActivity"  
        android:configChanges="keyboardHidden|orientation|screenSize"  
        android:exported="true"  
        android:screenOrientation="portrait"  
        android:theme="@android:style/Theme.Translucent.NoTitleBar" />  
    <activity  
        android:name="com.tencent.tauth.AuthActivity"  
        android:launchMode="singleTask"  
        android:noHistory="true" >  
      
        <intent-filter>  
            <action android:name="android.intent.action.VIEW" />  
            <category android:name="android.intent.category.DEFAULT" />  
            <category android:name="android.intent.category.BROWSABLE" />  
            <data android:scheme="tencent100424468" />  <= 改为自己申请的QQkey  
        </intent-filter>  
    </activity>  
    <activity  
        android:name="com.tencent.connect.common.AssistActivity"  
        android:screenOrientation="portrait"  
        android:theme="@android:style/Theme.Translucent.NoTitleBar"  
        android:configChanges="orientation|keyboardHidden|screenSize"/>  
    <activity  
        android:name=".apshare.ShareEntryActivity"  
        android:configChanges="keyboardHidden|orientation|screenSize"  
        android:exported="true"  
        android:screenOrientation="portrait"  
        android:theme="@android:style/Theme.Translucent.NoTitleBar" />  
    <meta-data  
        android:name="UMENG_APPKEY"  
        android:value="561cae6ae0f55abd990035bf" >   <= 改为自己申请的友盟Key  
    </meta-data>  

##### 6. 使用【keytool -genkey -v -keystore my-release-key.keystore -alias my-key-alias -keyalg RSA -keysize 2048 -validity 10000】生成签名文件，并将签名文件放入app目录
##### 7. 在gradle.properties文件下，添加签名信息【直接复制源码即可】
     MYAPP_RELEASE_STORE_FILE=my-release-key.keystore  
     MYAPP_RELEASE_KEY_ALIAS=my-key-alias  
     MYAPP_RELEASE_STORE_PASSWORD=123456（填写自己设置的密码）  
     MYAPP_RELEASE_KEY_PASSWORD=123456 （填写自己设置的密码）  
##### 8. 在app / build.gradle 文件下的添加签名配置【直接复制源码即可】
     android {     
       ...     
       defaultConfig {     
         ...     
       }     
       signingConfigs {     
         release {     
             storeFile file(MYAPP_RELEASE_STORE_FILE)     
             storePassword MYAPP_RELEASE_STORE_PASSWORD     
             keyAlias MYAPP_RELEASE_KEY_ALIAS     
             keyPassword MYAPP_RELEASE_KEY_PASSWORD     
         }     
       }     
       buildTypes {     
         release {     
           ...     
          signingConfig signingConfigs.release     
         }     
       }    
     }    
##### 9. 在MainApplication中初始化分享【直接复制源码即可】
    @Override  
    protected List<ReactPackage> getPackages() {  
      return Arrays.<ReactPackage>asList(  
          new MainReactPackage(),  
              new SharePackage() <= 添加引用  
      );  
    }  

    @Override  
    public void onCreate() {  
      super.onCreate();  
      SoLoader.init(this,false);  
      Config.shareType = "react native";  
      UMShareAPI.get(this);  
    }  
      
    // 配置平台  
    {  
      PlatformConfig.setWeixin("wx083bf496cbc48aec", "750e9075fa521c82274a9d548c399825");  
      PlatformConfig.setQQZone("1106207359", "3JjbG8aXMuh5w0sV");  
      PlatformConfig.setSinaWeibo("2733400964", "fac50980a44e3e3afd4bc968ea572887", "www.baidu.com");  
    }  
##### 10. 在MainActivity中初始化分享回调【直接复制源码即可】
    @Override  
      protected void onCreate(Bundle savedInstanceState) {  
          super.onCreate(savedInstanceState);  
          ShareModule.initActivity(this);  
      }  
      
      @Override  
      public void onActivityResult(int requestCode, int resultCode, Intent data) {  
          super.onActivityResult(requestCode, resultCode, data);  
          UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);  
      }  
#### 【 iOS平台配置 】
##### 1. 将源码中 ios 目录下的UMSocial添加到工程，直接拖进即可
##### 2. 选择TARGET下的项目，在Build Setting下找到Other Linker Flags加入-ObjC ，注意不要写为-Objc
##### 3. 加入U-Share SDK依赖的系统库
###### 依次添加如下依赖：
SystemConfiguration.framework
CoreGraphics.framework
CoreTelephony.framework
ImageIO.framework
libsqlite3.tbd
libc++.tbd
libz.tbd

##### 4. 配置SSO白名单【直接复制即可】
    <key>LSApplicationQueriesSchemes</key>  
    <array>  
        <!-- 微信 URL Scheme 白名单-->  
        <string>wechat</string>  
        <string>weixin</string>  
      
        <!-- 新浪微博 URL Scheme 白名单-->  
        <string>sinaweibohd</string>  
        <string>sinaweibo</string>  
        <string>sinaweibosso</string>  
        <string>weibosdk</string>  
        <string>weibosdk2.5</string>  
      
        <!-- QQ、Qzone、TIM URL Scheme 白名单-->  
        <string>mqqapi</string>  
        <string>mqq</string>  
        <string>mqqOpensdkSSoLogin</string>  
        <string>mqqconnect</string>  
        <string>mqqopensdkdataline</string>  
        <string>mqqopensdkgrouptribeshare</string>  
        <string>mqqopensdkfriend</string>  
        <string>mqqopensdkapi</string>  
        <string>mqqopensdkapiV2</string>  
        <string>mqqopensdkapiV3</string>  
        <string>mqqopensdkapiV4</string>  
        <string>mqzoneopensdk</string>  
        <string>wtloginmqq</string>  
        <string>wtloginmqq2</string>  
        <string>mqqwpa</string>  
        <string>mqzone</string>  
        <string>mqzonev2</string>  
        <string>mqzoneshare</string>  
        <string>wtloginqzone</string>  
        <string>mqzonewx</string>  
        <string>mqzoneopensdkapiV2</string>  
        <string>mqzoneopensdkapi19</string>  
        <string>mqzoneopensdkapi</string>  
        <string>mqqbrowser</string>  
        <string>mttbrowser</string>  
        <string>tim</string>  
        <string>timapi</string>  
        <string>timopensdkfriend</string>  
        <string>timwpa</string>  
        <string>timgamebindinggroup</string>  
        <string>timapiwallet</string>  
        <string>timOpensdkSSoLogin</string>  
        <string>wtlogintim</string>  
        <string>timopensdkgrouptribeshare</string>  
        <string>timopensdkapiV4</string>  
        <string>timgamebindinggroup</string>  
        <string>timopensdkdataline</string>  
        <string>wtlogintimV1</string>  
        <string>timapiV1</string>  
        <!-- Facebook URL Scheme 白名单-->  
        <string>fbapi</string>  
        <string>fb-messenger-api</string>  
        <string>fbauth2</string>  
        <string>fbshareextension</string>  
    </array>  
    
##### 5. 配置URL Scheme
##### 微信 	微信appKey 	wxdc1e388c3822c80b 	 
##### QQ/Qzone/TIM 	需要添加两项URL Scheme：
##### 1、"tencent"+腾讯QQ互联应用appID
##### 2、“QQ”+腾讯QQ互联应用appID转换成十六进制（不足8位前面补0） 	如appID：100424468 1、tencent100424468 
##### 3、QQ05fc5b14 	QQ05fc5b14为100424468转十六进制而来，因不足8位向前补0，然后加"QQ"前缀
##### 新浪微博 	“wb”+新浪appKey 	wb3921700954
##### Facebook 	“fb”+FacebookID 	fb506027402887373

##### 6.在AppDelegate.m中初始化U-Share及第三方平台
##### （1）引入头文件: #import<UMSocialCore/UMSocialCore.h>【直接复制即可】
##### （2）launchOptions中设置友盟Key【直接复制即可】

    /* 打开调试日志 */  
    [[UMSocialManager defaultManager] openLog:YES];  
      
    /* 设置友盟appkey */  
    [[UMSocialManager defaultManager] setUmSocialAppkey:USHARE_DEMO_APPKEY];  
      
    [self configUSharePlatforms];  
      
    [self confitUShareSettings];  
##### （3）添加如下代码，配置第三方平台【直接复制即可，修改对应key,secret】
    - (void)configUSharePlatforms  
    {  
        /*  
         设置微信的appKey和appSecret 
         [微信平台从U-Share 4/5升级说明]http://dev.umeng.com/social/ios/%E8%BF%9B%E9%98%B6%E6%96%87%E6%A1%A3#1_1 
         */  
        [[UMSocialManager defaultManager] setPlaform:UMSocialPlatformType_WechatSession appKey:@"wxdc1e388c3822c80b" appSecret:@"3baf1193c85774b3fd9d18447d76cab0" redirectURL:nil];  
        /* 
         * 移除相应平台的分享，如微信收藏 
         */  
        //[[UMSocialManager defaultManager] removePlatformProviderWithPlatformTypes:@[@(UMSocialPlatformType_WechatFavorite)]];  
      
        /* 设置分享到QQ互联的appID 
         * U-Share SDK为了兼容大部分平台命名，统一用appKey和appSecret进行参数设置，而QQ平台仅需将appID作为U-Share的appKey参数传进即可。 
         100424468.no permission of union id 
         [QQ/QZone平台集成说明]http://dev.umeng.com/social/ios/%E8%BF%9B%E9%98%B6%E6%96%87%E6%A1%A3#1_3 
        */  
        [[UMSocialManager defaultManager] setPlaform:UMSocialPlatformType_QQ appKey:@"1105821097"/*设置QQ平台的appID*/  appSecret:nil redirectURL:@"http://mobile.umeng.com/social"];  
      
        /*  
         设置新浪的appKey和appSecret 
         [新浪微博集成说明]http://dev.umeng.com/social/ios/%E8%BF%9B%E9%98%B6%E6%96%87%E6%A1%A3#1_2 
         */  
        [[UMSocialManager defaultManager] setPlaform:UMSocialPlatformType_Sina appKey:@"3921700954"  appSecret:@"04b48b094faeb16683c32669824ebdad" redirectURL:@"https://sns.whalecloud.com/sina2/callback"];  
        /* 设置Facebook的appKey和UrlString */  
        [[UMSocialManager defaultManager] setPlaform:UMSocialPlatformType_Facebook appKey:@"506027402887373"  appSecret:nil redirectURL:nil];  
    }  
##### （4）设置回调【直接复制即可】
    - (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation  
    {  
        //6.3的新的API调用，是为了兼容国外平台(例如:新版facebookSDK,VK等)的调用[如果用6.2的api调用会没有回调],对国内平台没有影响  
        BOOL result = [[UMSocialManager defaultManager] handleOpenURL:url sourceApplication:sourceApplication annotation:annotation];  
        if (!result) {  
             // 其他如支付等SDK的回调  
        }  
        return result;  
    }  

##### 7. 将 ios 目录下【sharemodule.h】、【sharemodule.m】文件拖入你的项目即可

#### 【 分享 】

    import UShare from './share/share';  
    import SharePlatform from './share/SharePlatform';  

    /** 
       * 参数说明： 
       * 1. 标题 
       * 2. 内容 
       * 3. 跳转链接 
       * 4. 图片链接 
       * 5. 分享平台 
       * 6. 分享结果回调 
       */  
      UShare.share('标题','内容','http://baidu.com','http://dev.umeng.com/images/tab2_1.png', SharePlatform.QQ,  
          (code, message) => {  
              // 分享成功：code=200  
              // ToastAndroid.show(message,ToastAndroid.SHORT);  
                
      });  
