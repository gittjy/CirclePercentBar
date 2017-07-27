# 效果图
![](https://github.com/gittjy/CirclePercentBar/raw/master/demogif/圆环百分比View.gif)
# 使用方法
1、项目下的build.gradle添加

```
allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```
2、模块下的build.gradle添加依赖

```
dependencies {
	        compile 'com.github.gittjy:CirclePercentBar:1.0.1'
	}
```
3、代码中<br>
<br>
布局xml文件
```
<com.android.tu.circlelibrary.CirclePercentBar
        android:id="@+id/circle_bar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:layout_gravity="center_horizontal"
        app:arcWidth="30dp"
        app:circleRadius="100dp"
        app:centerTextSize="30sp"
        app:arcColor="@color/colorPrimary"
        app:centerTextColor="@color/colorAccent"
        app:arcStartColor="@android:color/holo_green_light"/>
```
java文件中设置数值
```
circlePercentBar.setPercentData(data,new DecelerateInterpolator());
```
