@echo off
echo "��ʼִ���ϴ���̬�ļ�����ant����...";
call ant.bat -f "upload_static_file.xml" -logger org.apache.tools.ant.listener.TimestampedLogger
pause;