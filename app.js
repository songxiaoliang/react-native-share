/**
 * Created by shixia on 2017/7/12.
 */

import React, { Component } from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    NativeModules,
    ToastAndroid
} from 'react-native';

export default class ReactNativeShare extends Component {
    /**
     * content内容
     * imgurl图片链接
     * 平台：
     *  0:QQ; 1:新浪; 2:微信; 3:微信朋友圈;4:FACEBOOK;
     */
    _share() {
        NativeModules.sharemodule.share('标题','内容', 'http://baidu.com','http://dev.umeng.com/images/tab2_1.png', 2,
            (code, message) => {
                ToastAndroid.show(message,ToastAndroid.SHORT);
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