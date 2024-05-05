
import React, {useEffect, useState} from 'react';
import {Button, Card, Col, Image, message, Popconfirm, Row, Space, Statistic, Table, Tabs, Upload} from "antd";

import {CopyOutlined, UploadOutlined} from "@ant-design/icons";
import {deleteFile, getFileList} from "../../request/api";
import {uploadFile} from "../../request/uploadFile";




function App() {

  const [fileList, setFileList] = useState<FileInfo[]>([]);
  function copyUrl(url: string) {
    const copyText = url;
    const textarea = document.createElement('textarea');
    textarea.style.position = 'fixed';
    textarea.style.opacity = '0';
    textarea.value = copyText;
    document.body.appendChild(textarea);
    textarea.select();
    document.execCommand('copy');
    document.body.removeChild(textarea);
    message.info("复制成功")
  }

  function download(originalFilename: string, url: string) {
    fetch(url)
      .then(response => response.blob())
      .then(blob => {
        // 创建一个临时的 URL 对象，指向包含 Blob 数据的 URL
        const blobUrl = URL.createObjectURL(blob);
        // 创建一个<a>元素并设置其属性，然后模拟点击以启动下载
        const link = document.createElement('a');
        link.href = blobUrl;
        link.download = originalFilename;
        document.body.appendChild(link);
        link.click();
        // 释放 URL 对象以节省内存
        URL.revokeObjectURL(blobUrl);
        document.body.removeChild(link);
      });
  }

  function confirmDeleteFile(objectId: string) {
    deleteFile({objectId:objectId}).then(r => {
      if(r.msg === "success") {
        message.success("删除成功")
        fetchFileList();
      } else {
        message.error("删除失败: " + r.msg)
      }
    })
  }

  const columns = [
    {
      key: "预览",
      title: '预览',
      width: 150,
      render:(record:FileInfo) => {
        if (record.ext === "gif" || record.ext === "jpg" || record.ext === "png" || record.ext === "bmp" || record.ext === "jpeg") {
          return(
            <Image src={record.url}/>
          )
        }else {
          return (
            <CopyOutlined />
          )
        }
      }
    },
    {
      title: '源文件名称',
      dataIndex: 'filename',
      key: 'filename',
    },
    {
      title: '文件分类',
      dataIndex: 'objectType',
      key: 'objectType',
    },
    {
      title: '文件格式',
      dataIndex: 'ext',
      key: 'ext',
    },
    {
      title: '文件ID',
      dataIndex: 'objectId',
      key: 'objectId',
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      key: 'createTime',
    },
    {
      key: '操作',
      title: '操作',
      width: 340,
      render:(record:FileInfo) => (
        <>
          <Button
            type={"primary"}
            onClick={() => copyUrl(record.url)}
          >复制链接</Button>
          <Button
            type={"primary"}
            onClick={() => download(record.filename,record.url)}
          >下载文件</Button>
          <Popconfirm
            title="删除文件"
            description="确定要删除文件吗?"
            onConfirm={() => confirmDeleteFile(record.objectId)}
            okText="是"
            cancelText="否"
          >
            <Button
              type={"primary"}
              danger>删除</Button>
          </Popconfirm>
        </>
      )
    }
  ]


  function fetchFileList() {
    getFileList().then(r => {
      if(r.msg === "success") {
        setFileList(r.data)
      } else {
        message.error("获取文件列表失败：" + r.msg)
      }
    })
  }

  useEffect(() => {
    fetchFileList();
  }, []);


  function upload(file:File) {
    uploadFile(file).then(r => {
      console.log(r)
      if(r.msg === "success") {
        message.success("文件上传成功")
      } else {
        message.error("文件上传失败")
      }
      fetchFileList();
    })
  }

  return (
    <div>
      <Space direction="vertical" style={{ width: '100%' }} size={[0, 48]}>
        <Upload beforeUpload={upload}>
          <Button icon={<UploadOutlined />}>点击上传文件</Button>
        </Upload>
        <Table columns={columns}  dataSource={fileList}/>

      </Space>
    </div>
  );

}

export default App
