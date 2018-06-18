@echo off
echo "开始执行amwiki文件的子ant任务...";
call ant.bat -f "upload_amwiki_file.xml" -logger org.apache.tools.ant.listener.TimestampedLogger
pause;