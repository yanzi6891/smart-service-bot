<template>
  <div class="settings">
    <el-card>
      <template #header>
        <span>系统设置</span>
      </template>

      <el-tabs>
        <el-tab-pane label="AI模型配置">
          <el-form :model="aiConfig" label-width="120px">
            <el-form-item label="默认提供商">
              <el-select v-model="aiConfig.provider">
                <el-option label="OpenAI" value="OPENAI" />
                <el-option label="通义千问" value="TONGYI" />
                <el-option label="文心一言" value="WENXIN" />
              </el-select>
            </el-form-item>
            <el-form-item label="模型名称">
              <el-input v-model="aiConfig.model" placeholder="gpt-4" />
            </el-form-item>
            <el-form-item label="温度">
              <el-slider v-model="aiConfig.temperature" :min="0" :max="1" :step="0.1" />
            </el-form-item>
            <el-form-item label="最大Token">
              <el-input-number v-model="aiConfig.maxTokens" :min="100" :max="4000" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveAiConfig">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="知识库配置">
          <el-form :model="knowledgeConfig" label-width="120px">
            <el-form-item label="分块大小">
              <el-input-number v-model="knowledgeConfig.chunkSize" :min="100" :max="1000" />
            </el-form-item>
            <el-form-item label="重叠大小">
              <el-input-number v-model="knowledgeConfig.overlap" :min="0" :max="200" />
            </el-form-item>
            <el-form-item label="相似度阈值">
              <el-slider v-model="knowledgeConfig.threshold" :min="0" :max="1" :step="0.1" />
            </el-form-item>
            <el-form-item label="Top-K">
              <el-input-number v-model="knowledgeConfig.topK" :min="1" :max="20" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveKnowledgeConfig">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup lang="ts">
/**
 * 系统设置页面
 * @author SmartBot Team
 */
import { reactive } from 'vue'
import { ElMessage } from 'element-plus'

// AI配置
const aiConfig = reactive({
  provider: 'OPENAI',
  model: 'gpt-4',
  temperature: 0.7,
  maxTokens: 2000
})

// 知识库配置
const knowledgeConfig = reactive({
  chunkSize: 500,
  overlap: 50,
  threshold: 0.7,
  topK: 5
})

// 保存AI配置
const saveAiConfig = () => {
  ElMessage.success('AI配置已保存')
  // TODO: 调用API保存配置
}

// 保存知识库配置
const saveKnowledgeConfig = () => {
  ElMessage.success('知识库配置已保存')
  // TODO: 调用API保存配置
}
</script>

<style lang="scss" scoped>
.settings {
  max-width: 800px;
}
</style>
