@echo off
echo "��ʼִ��amwiki�ļ�����ant����...";
call ant.bat -f "upload_amwiki_file.xml" -logger org.apache.tools.ant.listener.TimestampedLogger
pause;