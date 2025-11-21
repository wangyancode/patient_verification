import { base64_encode, base64_decode} from '../libs/base64';
import md5 from '../libs/md5';
import underscore from '../libs/underscore';
var util = {};

util.navigateTo = function(url){
	uni.navigateTo({
		url:url
	})
}

util.redirectTo = function(url){
	uni.redirectTo({
		url:url
	})
}
util.reLaunch = function(url){
	uni.reLaunch({
		url:url
	})
}

util.goback = function(){
	uni.navigateBack({
		delta:1
	})
}

/**
 * base64 加密
 * @param {Object} str
 */
util.base64Encode = function(str) {
	return base64_encode(str)
};

/**
 * base64 解密
 * @param {Object} str
 */
util.base64Decode = function(str) {
	return base64_decode(str)
};

/**
 * MD5加密
 * @param {Object} str
 */
util.md5 = function(str) {
	return md5(str)
};



function getQuery(url) {
	var theRequest = [];
	if (url.indexOf("?") != -1) {
		var str = url.split('?')[1];
		var strs = str.split("&");
		for (var i = 0; i < strs.length; i++) {
			if (strs[i].split("=")[0] && unescape(strs[i].split("=")[1])) {
				theRequest[i] = {
					'name': strs[i].split("=")[0],
					'value': unescape(strs[i].split("=")[1])
				}
			}
		}
	}
	return theRequest;
}
/*
* 获取链接某个参数
* url 链接地址
* name 参数名称
*/
function getUrlParam(url, name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = url.split('?')[1].match(reg);  //匹配目标参数
	if (r != null) return unescape(r[2]); return null; //返回参数值
}

module.exports = util;
