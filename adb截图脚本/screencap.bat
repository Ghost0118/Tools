@echo off
echo 当前路径：%cd%
rem 1、使用adb命令截取手机屏幕
rem 2、导出到本脚当前目录下，并以时间戳命名
rem 3、最后删除手机中的临时文件
rem 4、保存为ANSI格式可以正常回显中文

adb root

adb wait-for-device

adb remount

adb shell screencap -p /mnt/sdcard/tmp.png

set t=%DATE:~0,4%-%DATE:~5,2%-%DATE:~8,2%-%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%
set "t=%t: =%"

adb pull /mnt/sdcard/tmp.png ./%t%.png
echo 重命名为：%t%.png

adb shell rm -f /mnt/sdcard/tmp.png

pause
 
rem 2017-4-24 更新修复 时间0点到9点出现空格导致新文件生成失败BUG