<template>
  <div class="knowledge">
    <el-card>
      <template #header>
        <div class="header">
          <span>知识库管理</span>
          <el-button type="primary" @click="handleUpload">
            <el-icon><Upload /></el-icon>
            上传文档
          </el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form :inline="true" :model="queryForm" class="search-form">
        <el-form-item label="状态">
          <el-select v-model="queryForm.status" placeholder="请选择" clearable>
            <el-option label="待处理" value="PENDING" />
            <el-option label="处理中" value="PROCESSING" />
            <el-option label="已完成" value="COMPLETED" />
            <el-option label="失败" value="FAILED" />
          </el-select>
        </el-form-item>
        <el-form-item label="分类">
          <el-input v-model="queryForm.category" placeholder="请输入分类" clearable />
        </el-form-item>
        <el-form-item label="关键词">
          <el-input v-model="queryForm.keyword" placeholder="搜索标题" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 表格 -->
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="documentId" label="ID" width="80" />
        <el-table-column prop="title" label="标题" min-width="200" show-overflow-tooltip />
        <el-table-column prop="fileType" label="类型" width="100" />
        <el-table-column prop="fileSize" label="大小" width="120">
          <template #default="{ row }">
            {{ formatFileSize(row.fileSize) }}
          </template>
        </el-table-column>
        <el-table-column prop="category" label="分类" width="120" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="chunkCount" label="分块数" width="100" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="viewChunks(row)">
              查看分块
            </el-button>
            <el-button link type="danger" size="small" @click="handleDelete(row)">
              删除
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

    <!-- 上传对话框 -->
    <el-dialog v-model="uploadDialogVisible" title="上传文档" width="500px">
      <el-upload
        ref="uploadRef"
        :auto-upload="false"
        :limit="1"
        accept=".pdf,.doc,.docx,.txt,.md"
      >
        <template #trigger>
          <el-button type="primary">选择文件</el-button>
        </template>
        <template #tip>
          <div class="el-upload__tip">支持PDF、Word、TXT、Markdown格式，文件大小不超过10MB</div>
        </template>
      </el-upload>
      <template #footer>
        <el-button @click="uploadDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitUpload">确定上传</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
/**
 * 知识库管理页面
 * @author SmartBot Team
 */
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getDocumentList, deleteDocument } from '@/api/knowledge'
import type { DocumentVO } from '@/types'

// 查询表单
const queryForm = reactive({
  status: '',
  category: '',
  keyword: ''
})

// 分页
const pagination = reactive({
  pageNum: 1,
  pageSize: 20,
  total: 0
})

// 表格数据
const tableData = ref<DocumentVO[]>([])
const loading = ref(false)

// 上传对话框
const uploadDialogVisible = ref(false)
const uploadRef = ref()

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryForm,
      pageNum: pagination.pageNum,
      pageSize: pagination.pageSize
    }
    const res = await getDocumentList(params)
    tableData.value = res.records
    pagination.total = res.total
  } catch (error) {
    console.error('加载文档列表失败:', error)
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
    category: '',
    keyword: ''
  })
  handleSearch()
}

// 打开上传对话框
const handleUpload = () => {
  uploadDialogVisible.value = true
}

// 提交上传
const submitUpload = () => {
  uploadRef.value?.submit()
  uploadDialogVisible.value = false
  ElMessage.success('上传成功')
  loadData()
}

// 查看分块
const viewChunks = (row: DocumentVO) => {
  ElMessage.info(`查看文档分块: ${row.title}`)
  // TODO: 打开分块详情对话框
}

// 删除文档
const handleDelete = async (row: DocumentVO) => {
  try {
    await ElMessageBox.confirm(`确定要删除文档"${row.title}"吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    await deleteDocument(row.documentId)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除文档失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

// 格式化文件大小
const formatFileSize = (size: number) => {
  if (size < 1024) {
    return size + ' B'
  } else if (size < 1024 * 1024) {
    return (size / 1024).toFixed(2) + ' KB'
  } else {
    return (size / (1024 * 1024)).toFixed(2) + ' MB'
  }
}

// 获取状态类型
const getStatusType = (status: string) => {
  const typeMap: Record<string, any> = {
    PENDING: 'info',
    PROCESSING: 'warning',
    COMPLETED: 'success',
    FAILED: 'danger'
  }
  return typeMap[status] || 'info'
}

// 获取状态文本
const getStatusText = (status: string) => {
  const textMap: Record<string, string> = {
    PENDING: '待处理',
    PROCESSING: '处理中',
    COMPLETED: '已完成',
    FAILED: '失败'
  }
  return textMap[status] || status
}

// 初始化
onMounted(() => {
  loadData()
})
</script>

<style lang="scss" scoped>
.knowledge {
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
