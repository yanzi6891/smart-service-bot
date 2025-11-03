/**
 * 知识库相关API
 * @author SmartBot Team
 */
import { get, post, del } from '@/utils/request'
import type { DocumentVO, PageResp } from '@/types'

/** 上传文档 */
export function uploadDocument(formData: FormData) {
  return post<DocumentVO>('/v1/knowledge/documents/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

/** 查询文档列表 */
export function getDocumentList(params: {
  pageNum: number
  pageSize: number
  status?: string
  category?: string
  keyword?: string
}) {
  return get<PageResp<DocumentVO>>('/v1/knowledge/documents', params)
}

/** 删除文档 */
export function deleteDocument(documentId: number) {
  return del(`/v1/knowledge/documents/${documentId}`)
}

/** 获取文档分块 */
export function getDocumentChunks(documentId: number, pageNum: number = 1, pageSize: number = 20) {
  return get(`/v1/knowledge/documents/${documentId}/chunks`, {
    pageNum,
    pageSize
  })
}
