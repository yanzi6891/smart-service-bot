<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <!-- 统计卡片 -->
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #e6f7ff; color: #1890ff">
              <el-icon :size="32"><ChatDotRound /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.totalConversations }}</div>
              <div class="stat-label">总对话数</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #f0f9ff; color: #52c41a">
              <el-icon :size="32"><ChatLineRound /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.totalMessages }}</div>
              <div class="stat-label">总消息数</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #fff7e6; color: #fa8c16">
              <el-icon :size="32"><Timer /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.avgResponseTime }}s</div>
              <div class="stat-label">平均响应时间</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #fff0f6; color: #eb2f96">
              <el-icon :size="32"><Star /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ (stats.satisfactionRate * 100).toFixed(1) }}%</div>
              <div class="stat-label">满意度</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>渠道分布</span>
          </template>
          <div ref="channelChartRef" style="height: 300px"></div>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card>
          <template #header>
            <span>意图分布</span>
          </template>
          <div ref="intentChartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 趋势图 -->
    <el-card style="margin-top: 20px">
      <template #header>
        <span>对话趋势</span>
      </template>
      <div ref="trendChartRef" style="height: 350px"></div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
/**
 * 数据统计页面
 * @author SmartBot Team
 */
import { ref, onMounted, reactive } from 'vue'
import * as echarts from 'echarts'
import { getStatistics } from '@/api/admin'
import type { StatisticsVO } from '@/types'

// 图表DOM引用
const channelChartRef = ref<HTMLElement>()
const intentChartRef = ref<HTMLElement>()
const trendChartRef = ref<HTMLElement>()

// 统计数据
const stats = reactive<StatisticsVO['overview']>({
  totalConversations: 0,
  totalMessages: 0,
  avgMessagesPerConversation: 0,
  avgResponseTime: 0,
  satisfactionRate: 0
})

// 加载数据
onMounted(async () => {
  await loadStatistics()
  initCharts()
})

// 加载统计数据
const loadStatistics = async () => {
  try {
    const endDate = new Date()
    const startDate = new Date()
    startDate.setDate(startDate.getDate() - 30)

    const data = await getStatistics({
      startDate: startDate.toISOString().split('T')[0],
      endDate: endDate.toISOString().split('T')[0]
    })

    Object.assign(stats, data.overview)

    // 更新图表
    updateChannelChart(data.channelDistribution)
    updateIntentChart(data.intentDistribution)
    updateTrendChart(data.dailyTrend)
  } catch (error) {
    console.error('加载统计数据失败:', error)
  }
}

// 初始化图表
const initCharts = () => {
  if (channelChartRef.value) {
    echarts.init(channelChartRef.value)
  }
  if (intentChartRef.value) {
    echarts.init(intentChartRef.value)
  }
  if (trendChartRef.value) {
    echarts.init(trendChartRef.value)
  }
}

// 更新渠道分布图表
const updateChannelChart = (data: Record<string, number>) => {
  if (!channelChartRef.value) return

  const chart = echarts.getInstanceByDom(channelChartRef.value)
  if (!chart) return

  const chartData = Object.entries(data).map(([name, value]) => ({ name, value }))

  chart.setOption({
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        type: 'pie',
        radius: '60%',
        data: chartData,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  })
}

// 更新意图分布图表
const updateIntentChart = (data: Record<string, number>) => {
  if (!intentChartRef.value) return

  const chart = echarts.getInstanceByDom(intentChartRef.value)
  if (!chart) return

  const chartData = Object.entries(data).map(([name, value]) => ({ name, value }))

  chart.setOption({
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        type: 'pie',
        radius: ['40%', '70%'],
        data: chartData,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  })
}

// 更新趋势图表
const updateTrendChart = (data: any[]) => {
  if (!trendChartRef.value) return

  const chart = echarts.getInstanceByDom(trendChartRef.value)
  if (!chart) return

  const dates = data.map((item) => item.date)
  const conversationCounts = data.map((item) => item.conversationCount)
  const messageCounts = data.map((item) => item.messageCount)

  chart.setOption({
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['对话数', '消息数']
    },
    xAxis: {
      type: 'category',
      data: dates
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '对话数',
        type: 'line',
        data: conversationCounts,
        smooth: true
      },
      {
        name: '消息数',
        type: 'line',
        data: messageCounts,
        smooth: true
      }
    ]
  })
}
</script>

<style lang="scss" scoped>
.dashboard {
  .stat-card {
    .stat-content {
      display: flex;
      align-items: center;
      gap: 16px;

      .stat-icon {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 60px;
        height: 60px;
        border-radius: 8px;
      }

      .stat-info {
        .stat-value {
          font-size: 28px;
          font-weight: bold;
          color: #303133;
        }

        .stat-label {
          margin-top: 4px;
          font-size: 14px;
          color: #909399;
        }
      }
    }
  }
}
</style>
