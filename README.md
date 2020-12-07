# DayNightSwitch
A cute day night switch for android

Inspired from this gif :
https://www.uplabs.com/posts/on-off-switch-7af29fd7-131a-4aac-b142-63579710f52f

![](https://github.com/Mahfa/DayNightSwitch/blob/master/demo.gif)


### How to install
Add jitpack in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency

```
	dependencies {
	        compile 'com.github.Mahfa:DayNightSwitch:1.4'
	}
```

### Usage

just add it to your xml layout file

```xml
    <com.mahfa.dnswitch.DayNightSwitch
        android:id="@+id/day_night_switch"
        android:layout_width="76dp"
        android:layout_height="40dp"
        android:layout_gravity="center"/>
```


