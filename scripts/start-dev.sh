#!/bin/bash

echo "========================================"
echo "smart-service-bot 开发环境启动脚本"
echo "========================================"
echo ""

echo "[1/2] 启动后端服务..."
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev &
BACKEND_PID=$!
echo "后端服务已启动 (PID: $BACKEND_PID)"

sleep 3

echo ""
echo "[2/2] 启动前端服务..."
cd ../frontend
npm run dev &
FRONTEND_PID=$!
echo "前端服务已启动 (PID: $FRONTEND_PID)"

echo ""
echo "========================================"
echo "服务启动完成！"
echo "后端地址: http://localhost:8080"
echo "API文档: http://localhost:8080/doc.html"
echo "前端地址: http://localhost:5173"
echo "========================================"
echo ""
echo "按 Ctrl+C 停止所有服务"

# 等待用户按Ctrl+C
wait
