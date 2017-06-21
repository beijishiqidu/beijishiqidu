cd ..
set homePath=%cd%
set cssFolder=%homePath%\style
set jsFolder=%homePath%\javascript
set compressFloder=%homePath%\compressed

::rem start "WinLess" "C:\Program Files (x86)\Mark Lagendijk\WinLess\WinLess.exe -d %cssFolder% --minify --compile --clear" &
::rem ping localhost -n 20
::rem d:

cd %cssFolder%\app
copy/b *.css compressed-temp-app.css

cd %cssFolder%\lib
copy/b %cssFolder%\lib\jquery.qtip.min.css^
 + %cssFolder%\lib\jquery-ui.min.css^
 %cssFolder%\compressed-temp-lib.css

cd %jsFolder%\app
copy/b *.js compressed-temp-app.js

cd %jsFolder%\lib
copy/b *.js compressed-temp-lib.js
copy compressed-temp-lib.js %compressFloder%\lib-min.js

cd %compressFloder%
java -jar yuicompressor-2.4.8.jar %cssFolder%\app\compressed-temp-app.css -o app-min.css --charset utf-8
java -jar yuicompressor-2.4.8.jar %cssFolder%\compressed-temp-lib.css -o lib-min.css --charset utf-8
java -jar yuicompressor-2.4.8.jar %jsFolder%\app\compressed-temp-app.js -o app-min.js --charset utf-8

cd %cssFolder%
del compressed-temp-lib.css
cd %cssFolder%\app
del /S compressed-temp-app.css
cd %jsFolder%\app
del compressed-temp-app.js
cd %jsFolder%\lib
del compressed-temp-lib.js


pause






