@echo off
call ant.bat -f "deploy.xml" -logger org.apache.tools.ant.listener.TimestampedLogger
pause;