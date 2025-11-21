//转运  接口

import request from "@/utils/request";

//
export function getDataByPage(data) {
  return request({
    url: "/deliveryCompany/page",
    method: "post",
    data: data,
  });
}

//
export function handleSave(data) {
  return request({
    url: "/deliveryCompany/save",
    method: "post",
    data: data,
  });
}

//
export function handleUpdate(data) {
  return request({
    url: "/deliveryCompany/update",
    method: "put",
    data: data,
  });
}

// 封箱管理 列表页
export function handleDelete(data) {
  return request({
    url: "/deliveryCompany/delete",
    method: "delete",
    data: data,
  });
}

// 配送人员管理 列表页
export function getDataById(id) {
  return request({
    url: `/deliveryCompany/${id}`,
    method: "get",
    // data: data,
  });
}

// 验证token
export function checkToken(data) {
  return request({
    url: "/deliveryCompany/seeToken",
    method: "post",
    data: data,
  });
}

// 重置token
export function reflashToken(data) {
  return request({
    url: "/deliveryCompany/generateTokenAgain",
    method: "post",
    data: data,
  });
}

export function getPatientDataByPage(data) {
  return request({
    url: "/patient/page",
    method: "post",
    data: data,
  });
}
