<template>
  <div class="conversations">
    <el-card>
      <template #header>
        <div class="header">
          <span>会话列表</span>
          <el-button type="primary" @click="loadData">
            <el-icon><Refresh /></el-icon>
            刷新
          </el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form :inline="true" :model="queryForm" class="search-form">
        <el-form-item label="状态">
          <el-select v-model="queryForm.status" placeholder="请选择" clearable>
            <el-option label="进行中" value="ACTIVE" />
            <el-option label="已关闭" value="CLOSED" />
          </el-select>
        </el-form-item>
        <el-form-item label="渠道">
          <el-select v-model="queryForm.channel" placeholder="请选择" clearable>
            <el-option label="网页" value="WEB" />
            <el-option label="微信" value="WECHAT" />
            <el-option label="API" value="API" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 表格 -->
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="sessionId" label="会话ID" width="200" />
        <el-table-column prop="userId" label="用户ID" width="100" />
        <el-table-column prop="channel" label="渠道" width="100">
          <template #default="{ row }">
            <el-tag>{{ row.channel }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 'ACTIVE' ? 'success' : 'info'">
              {{ row.status === 'ACTIVE' ? '进行中' : '已关闭' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="messageCount" label="消息数" width="100" />
        <el-table-column prop="lastMessage" label="最后消息" show-overflow-tooltip />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="viewDetail(row)">
              查看详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="pagination.pageNum"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>
  </div>
</template>

<script setup lang="ts">
/**
 * 会话管理页面
 * @author SmartBot Team
 */
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getConversationList } from '@/api/admin'
import type { ConversationVO } from '@/types'

// 查询表单
const queryForm = reactive({
  status: '',
  channel: '',
  userId: undefined as number | undefined
})

// 分页
const pagination = reactive({
  pageNum: 1,
  pageSize: 20,
  total: 0
})

// 表格数据
const tableData = ref<ConversationVO[]>([])
const loading = ref(false)

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryForm,
      pageNum: pagination.pageNum,
      pageSize: pagination.pageSize
    }
    const res = await getConversationList(params)
    tableData.value = res.records
    pagination.total = res.total
  } catch (error) {
    console.error('加载会话列表失败:', error)
    ElMessage.error('加载数据失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  pagination.pageNum = 1
  loadData()
}

// 重置
const handleReset = () => {
  Object.assign(queryForm, {
    status: '',
    channel: '',
    userId: undefined
  })
  handleSearch()
}

// 查看详情
const viewDetail = (row: ConversationVO) => {
  ElMessage.info(`查看会话详情: ${row.sessionId}`)
  // TODO: 打开详情对话框
}

// 初始化
onMounted(() => {
  loadData()
})
</script>

<style lang="scss" scoped>
.conversations {
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .search-form {
    margin-bottom: 20px;
  }
}
</style>
