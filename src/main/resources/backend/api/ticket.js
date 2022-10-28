// 查询列表接口
const getTicketPage = (params) => {
  return $axios({
    url: '/ticket/page',
    method: 'get',
    params
  })
}

// 编辑页面反查详情接口
const queryTicketById = (id) => {
  return $axios({
    url: `/t/${id}`,
    method: 'get'
  })
}

// 删除当前列的接口
const deleTicket = (id) => {
  return $axios({
    url: '/ticket',
    method: 'delete',
    params: { id }
  })
}

// 修改接口
const editTicket = (params) => {
  return $axios({
    url: '/ticket',
    method: 'put',
    data: { ...params }
  })
}

// 新增接口
const addTicket = (params) => {
  return $axios({
    url: '/ticket',
    method: 'post',
    data: { ...params }
  })
}