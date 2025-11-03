@echo off
echo ========================================
echo smart-service-bot 开发环境启动脚本
echo ========================================
echo.

echo [1/2] 启动后端服务...
cd backend
start cmd /k "mvn spring-boot:run -Dspring-boot.run.profiles=dev"
timeout /t 3 /nobreak >nul

echo.
echo [2/2] 启动前端服务...
cd ..\frontend
start cmd /k "npm run dev"

echo.
echo ========================================
echo 服务启动完成！
echo 后端地址: http://localhost:8080
echo API文档: http://localhost:8080/doc.html
echo 前端地址: http://localhost:5173
echo ========================================
pause
