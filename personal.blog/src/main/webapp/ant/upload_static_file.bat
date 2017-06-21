@echo off
echo "开始执行上传静态文件的子ant任务...";
call ant.bat -f "upload_static_file.xml" -logger org.apache.tools.ant.listener.TimestampedLogger
pause;