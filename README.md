# TabBar-master
标签栏控件,支持自定义四个圆角弧度、选中状态和未选中状态的背景颜色和字体颜色、自定义字体大小、设置分隔线、分隔线颜色、分隔线大小、设置子选项左右内边距、
整体固定宽度的情况下可以设置子选项是否均分总宽度大小、也可以通过子选项自适应宽度等样式。

截图
-------
![](https://github.com/Richard-person/TabBar-master/blob/master/screenshot/example1.png)

使用方法
-------
1.导入<br>
 Step 1. Add it in your root build.gradle at the end of repositories:
 
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.Richard-person:TabBar-master:v1.0'
	}

2.XML<br> 

<com.richard.tabbar.TabBarView<br> 
&emsp;&emsp;android:id="@+id/tbv_one"<br> 
&emsp;&emsp;android:layout_width="wrap_content"<br> 
&emsp;&emsp;android:layout_height="@dimen/tabbar_height"<br> 
&emsp;&emsp;android:layout_marginTop="@dimen/content_margin"<br> 
&emsp;&emsp;app:tbv_blank_view_color="#afafaf"<br> 
&emsp;&emsp;app:tbv_blank_view_width="0.5dp"<br> 
&emsp;&emsp;app:tbv_content_back_color="#0988a6"<br> 
&emsp;&emsp;app:tbv_default_checked_item_position="0"<br> 
&emsp;&emsp;app:tbv_item_checked_back_color="#034a58"<br> 
&emsp;&emsp;app:tbv_item_uncheck_back_color="#00000000"<br> 
&emsp;&emsp;app:tbv_padding_left_right="20dp"<br> 
&emsp;&emsp;app:tbv_radius="@dimen/tabbar_radius"<br> 
&emsp;&emsp;app:tbv_text_checked_color="#ffffff"<br> 
&emsp;&emsp;app:tbv_text_size="@dimen/tabbar_textSize"<br> 
&emsp;&emsp;app:tbv_text_uncheck_color="#ffffff"<br> 
&emsp;&emsp;app:tbv_texts="推荐|首页|科技|热点|情感"<br> 
&emsp;&emsp;/>
       
3.java 代码<br>

      tbv_one = findViewById(R.id.tbv_one);
      tbv_one.setOnTabBarCheckedChangeListener(new TabBarView.OnTabBarCheckedChangeListener() {
            @Override
            public void checked(String itemText, int position) {
                Toast.makeText(getApplicationContext(),"已选择了 " + itemText + "   位置 : " + position,Toast.LENGTH_LONG).show();
            }
      });
      //tbv_one.setData(Arrays.asList("推荐","首页","科技","热点","情感"));//动态设置子选项文本数据
      //tbv_one.checkItem(0);//动态设置默认选中子选项

控件属性介绍
-------
* tbv_texts 子选项文本值，多个以竖线“|”分隔
* tbv_text_size 子选项文本大小
* tbv_text_uncheck_color  未选中状态下的子选项文本颜色
* tbv_text_checked_color  选中状态下的子选项文本颜色
* tbv_radius  同时设置四个圆角弧度
* tbv_topLeftRadius  设置左上角圆角弧度
* tbv_topRightRadius  设置右上角圆角弧度
* tbv_bottomLeftRadius  设置左下角圆角弧度
* tbv_bottomRightRadius  设置右下角圆角弧度
* tbv_content_back_color  整体背景颜色
* tbv_item_uncheck_back_color 未选中状态下的子选项背景颜色
* tbv_item_checked_back_color 选中状态下的子选项背景颜色
* tbv_blank_view_width  分隔线宽度
* tbv_blank_view_color  分隔线颜色
* tbv_width_isAverage 子选项是否均分总宽度
* tbv_padding_left_right  子选项左右方向内部边距
* tbv_default_checked_item_position 默认选中子选项位置
* tbv_is_bottom_bar_style 设置选中时的状态底部是否为条状样式
