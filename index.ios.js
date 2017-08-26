/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
    NativeModules
} from 'react-native';

import UShare from './share/share';
import SharePlatform from './share/SharePlatform';
export default class ReactNativeShare extends Component {
    /**
     * 参数说明：
     * 1. 标题
     * 2. 内容
     * 3. 跳转链接
     * 4. 图片链接
     * 5. 分享平台
     * 6. 分享结果回调
     */
    _share() {
        UShare.share('标题','内容',
         'http://baidu.com','http://dev.umeng.com/images/tab2_1.png', SharePlatform.QQ,
            (message) => {
                // message:
                // 分享成功
                // 分享失败
                // 取消分享
                // ToastAndroid.show(message,ToastAndroid.SHORT);
                
        });
    }
  render() {
    return (
        <View style={styles.container}>
          <Text style={styles.welcome} onPress={this._share.bind(this)}>
            分享
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
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
});

AppRegistry.registerComponent('ReactNativeShare', () => ReactNativeShare);
