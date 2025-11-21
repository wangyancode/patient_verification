<template>
	<uv-navbar :title="title" @leftClick="goBack" :bgColor="backgroundColor" :titleStyle="{color}" :leftIconColor="color" leftIconSize="32rpx" :leftIcon="IsBacks?'arrow-leftward':''" placeholder></uv-navbar>
</template>

<script>
	export default {
		name: "navbar",
		props: {
			title: {
				type: String,
				default: ""
			},
			IsBacks: {
				type: Boolean,
				default: true
			},
			autoBack: {
				type: Boolean,
				default: true
			},
			backgroundColor: {
				type: String,
				default: '#ffffff'
			},
			color: {
				type: String,
				default: '#000000'
			}
		},
		data() {
			return {
				
			}
		},
		onLoad() {

		},
		methods: {
			goBack() {
				if (this.IsBacks) {
					if (this.autoBack) {
						uni.navigateBack({
							delta: 1,
						});
					} else {
						//自定义返回事件
						this.$emit('backEvent');
					}
				}
			}
		}
	}
</script>