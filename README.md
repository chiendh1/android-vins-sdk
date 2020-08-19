# VinSdk
 [![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Thư viện VinSdk cho phép lập trình viên tích hợp quảng cáo các sản phẩm của công ty trên ứng dụng của bạn đang phát triển. Có 2 cách quảng cáo trên thiết bị của bạn :

  - Hiển thị một loạt các ứng dụng của công ty phát hành dưới dạng danh sách
  - Hiển thị một dialog quảng cáo ứng dụng của công ty cứ sau 2 lần vào ứng dụng thì quảng cáo sẽ được hiển thị

# Download

  - Đầu tiên bạn cần thêm thư viện sau vào project của mình 
  ```gradle
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }  
    }

    dependencies {
         implementation 'com.github.chiendh1:android-vins-sdk:1.0.0'
    }
```
  - Tiếp theo bạn cần thêm 
```gradle
    android {
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
``` 
   - Sau đó trong file Manifest bạn cần thêm
```gradle
    <uses-permission android:name="android.permission.INTERNET"/>
    <activity android:name="com.vinstudio.vinsdk.AppsActivity"/>
``` 
   - Cuối cùng là trong file styles.xml bạn cần thêm 
```gradle
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>
    </style>
```   
### Using
- Để sử dụng quảng cáo dưới dạng dialog thì bạn cần sử dụng đoạn code sau
```gradle
    ShowDialog.promo(this)
``` 
- Để sử dụng quảng cáo dưới dạng danh sách các ứng dụng bạn cũng thêm đoạn code sau
```gradle
    startActivity(AppsActivity.getIntent(this))
``` 
