#LYSwipeRefreshLayout



##What
This is a project on the swipeRefreshlayout package.

###show

![](https://raw.githubusercontent.com/yongli991581716/LYSwipeRefreshLayout/master/pic/1.gif)

##why
1. The author needs a simple and satisfying drop-down control for the project.
2. Support google original refresh control.

##where&when
1. We can use it in project development.
2. The project contains or not contains a refresh feature.
3. ...

##how

###Requirements
Min SDK version 16

Installing with Gradle

In your project level build.gradle

```
allprojects {
repositories {
...
maven { url 'https://jitpack.io' }
}
}
```

In your app level build.gradle

```
compile 'com.github.yongli991581716:LYSwipeRefreshLayout:1.1'
```

###usage

In xml

```
<com.ly.library.view.LYSwipeRefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:orientation="vertical"
app:mode="list">

</com.ly.library.view.LYSwipeRefreshLayout>
```

detail see sample(app)

##who

###Developed By
Mr.Li

## License

Copyright 2017, Mr.Li

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.








