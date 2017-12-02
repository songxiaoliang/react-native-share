/**
 * react-native-share/login
 * update by Songlcy 2017-12-02
 */
import React, { Component } from 'react';
import {
  Text,
  View,
  NativeModules,
  StyleSheet,
  AppRegistry
} from 'react-native';

import UShare from './share/share'
import SharePlatform from './SharePlatform'

export default class ReactNativeShare extends Component {
    
    /**
     * 第三方分享
     * 参数：标题、分享内容、分享链接、图片、平台、分享结果回调
     */
    _share() {
        UShare.share('标题','内容', 'http://baidu.com','http://dev.umeng.com/images/tab2_1.png', SharePlatform.QQ,
        (message) => {
            // message: 分享成功、分享失败、取消分享
            // TODO ... 
        });
    }

    /**
     * 第三方登录
     * 参数：登录平台、登录结果回调
     */
    _getUserInfo() {
        UShare.authLogin(SharePlatform.QQ, (result) => {
            // code: 0成功、1失败、2取消
            if(result.code === 0) {
                console.log('授权登录成功:' + JSON.parse(result));
            } else {
                // TODO ...
            }
        });   
    }

    render() {
        return (
            <View style={ styles.container }>
                <Text style={ styles.title } onPress={ this._share.bind(this) }>
                    第三方分享
                </Text>
                <Text style={ styles.title } onPress={ this._getUserInfo.bind(this) }>
                    第三方登录
                </Text>
            </View>
        );
    }
}


const styles = StyleSheet.create({
    container: {
      flex: 1,
      justifyContent: 'center',
      alignItems: 'center',
      backgroundColor: '#F5FCFF',
    },
  
    title: {
      fontSize: 20,
      textAlign: 'center',
      margin: 10,
    }
  
});
  
AppRegistry.registerComponent('ReactNativeShare', () => ReactNativeShare);