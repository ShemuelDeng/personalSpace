<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <div class="search-wrapper">
      <el-form :model="queryParams" ref="queryFormRef" inline>
        <el-form-item label="资源名" prop="name">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入资源名"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-select v-model="queryParams.category" placeholder="请选择分类">
            <el-option
              v-for="item in categoryList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="是否免费" prop="isFree">
          <el-select v-model="queryParams.isFree" placeholder="请选择是否免费">
            <el-option
              v-for="item in freeList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="请选择状态">
                <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery"
            >搜索</el-button
          >
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-card class="box-card">
      <!-- 操作工具栏 -->
      <template #header>
        <el-button type="primary"  v-permission="['sys:resource:add']" plain icon="Plus" @click="handleAdd"
          >新增
        </el-button>
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="selectedIds.length === 0"
          @click="handleBatchDelete"
          v-permission="['sys:resource:delete']"
          >批量删除
        </el-button>
      </template>

      <!-- 数据表格 -->
      <el-table
        v-loading="loading"
        :data="dataList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="资源名" show-overflow-tooltip align="center" prop="name" width="200"/>
        <el-table-column label="分类" align="center" prop="category">
          <template #default="scope">
            <span v-for="item in categoryList" :key="item.value">
              <el-tag
                v-if="scope.row.category === item.value"
                :type="item.style"
              >
                {{ item.label }}
              </el-tag>
            </span>
          </template>
        </el-table-column>
        <el-table-column label="下载量" align="center" prop="downloads" width="80"/>
        <el-table-column label="是否免费" align="center" prop="isFree" width="80">
          <template #default="scope">
            <span v-for="item in freeList" :key="item.value">
              <el-tag
                v-if="scope.row.isFree === item.value"
                :type="item.style"
              >
                {{ item.label }}
              </el-tag>
            </span>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status">
            <template #default="scope">
                <span v-for="item in statusList" :key="item.value">
                    <el-tag :type="item.style" v-if="scope.row.status === item.value">
                        {{ item.label }}
                    </el-tag>
                </span>
            </template>
        </el-table-column>
        <el-table-column label="网盘地址" show-overflow-tooltip align="center" prop="panPath" width="200" />
        <el-table-column label="网盘验证码" align="center" prop="panCode" />
        <el-table-column label="创建时间" align="center" prop="createTime" width="160" />
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template #default="scope">
            <el-button
              type="primary"
              link
              icon="Edit"
              v-permission="['sys:resource:update']"
              @click="handleUpdate(scope.row)"
              >修改
            </el-button>
            <el-button
              type="danger"
              link
              icon="Delete"
              v-permission="['sys:resource:delete']"
              @click="handleDelete(scope.row)"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页工具栏 -->
      <div class="pagination-container">
        <el-pagination
          background
          v-model:current-page="queryParams.pageNum"
          v-model:page-size="queryParams.pageSize"
          :page-sizes="[10, 20, 30, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>

      <!-- 添加或修改对话框 -->
      <el-dialog v-model="open" :title="title" width="500px" append-to-body>
        <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="资源名" prop="name">
            <el-input v-model="form.name" placeholder="请输入资源名" />
          </el-form-item>
          <el-form-item label="分类" prop="category">
            <el-select v-model="form.category" placeholder="请选择分类">
                <el-option v-for="item in categoryList" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="是否免费" prop="isFree">
            <el-select v-model="form.isFree" placeholder="请选择是否免费">
                <el-option v-for="item in freeList" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="付费方式" prop="payType">
            <el-input v-model="form.payType" placeholder="请输入付费方式" />
          </el-form-item> -->
          <el-form-item label="网盘地址" prop="panPath">
            <el-input v-model="form.panPath" placeholder="请输入网盘地址" />
          </el-form-item>
          <el-form-item label="提取码" prop="panCode">
            <el-input v-model="form.panCode" placeholder="请输入提取码" />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="form.status" placeholder="请选择状态">
                <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </template>
      </el-dialog>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ElMessage, ElMessageBox } from "element-plus";
import {
  listSysResourceApi,
  detailSysResourceApi,
  deleteSysResourceApi,
  addSysResourceApi,
  updateSysResourceApi,
} from "@/api/site/resource";
import { getDictDataByDictTypesApi } from "@/api/system/dict";

// 遮罩层
const loading = ref(true);
// 选中数组
const selectedIds = ref<any[]>([]);
// 总条数
const total = ref(0);
// 表格数据
const dataList = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);
// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  name: undefined,
  category: undefined,
  isFree: undefined,
  status: undefined,
});

const categoryList = ref<any[]>([]);
const freeList = ref<any[]>([
  {
    value: 1,
    label: "免费",
    style: "success",
  },
  {
    value: 0,
    label: "付费",
    style: "danger",
  },
]);
const statusList = ref<any[]>([
  {
    value: 0,
    label: "未通过",
    style: "danger",
  },
  {
    value: 1,
    label: "待审核",
    style: "warning",
  },
  {
    value: 2,
    label: "已通过",
    style: "success",
  },
]);

// 表单参数
const form = reactive<any>({});
// 表单校验
const rules = reactive({
  name: [{ required: true, message: "资源名不能为空", trigger: "blur" }],
  category: [{ required: true, message: "分类不能为空", trigger: "blur" }],
  isFree: [{ required: true, message: "是否免费不能为空", trigger: "blur" }],
  panPath: [{ required: true, message: "网盘地址不能为空", trigger: "blur" }],
  panCode: [{ required: true, message: "提取码不能为空", trigger: "blur" }],
  status: [{ required: true, message: "状态不能为空", trigger: "blur" }],
});

const queryFormRef = ref();
const formRef = ref();

/** 查询列表 */
const getList = () => {
  loading.value = true;
  listSysResourceApi(queryParams).then((response) => {
    dataList.value = response.data.records;
    total.value = response.data.total;
    loading.value = false;
  });
};

/** 获取分类列表 */
const getCategoryList = () => {
  getDictDataByDictTypesApi("sys_resource_category").then((res) => {
    categoryList.value = res.data.sys_resource_category.list;
  });
};

/** 取消按钮 */
const cancel = () => {
  open.value = false;
  reset();
};

/** 表单重置 */
const reset = () => {
  form.value = {
    id: undefined,
    userId: undefined,
    name: undefined,
    category: undefined,
    downloads: undefined,
    isFree: undefined,
    payType: undefined,
    panPath: undefined,
    panCode: undefined,
    createTime: undefined,
  };
  formRef.value?.resetFields();
};

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNum = 1;
  getList();
};

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
};

/** 多选框选中数据 */
const handleSelectionChange = (selection: { id: any }[]) => {
  selectedIds.value = selection.map((item) => item.id);
};

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  open.value = true;
  title.value = "添加资源表";
};

/** 修改按钮操作 */
const handleUpdate = (row: any) => {
  reset();
  detailSysResourceApi(row.id).then((response) => {
    Object.assign(form, response.data);
    open.value = true;
    title.value = "修改资源表";
  });
};

/** 提交按钮 */
const submitForm = () => {
  formRef.value?.validate((valid: any) => {
    if (valid) {
      if (form.id !== undefined) {
        updateSysResourceApi(form).then((response) => {
          ElMessage.success("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addSysResourceApi(form).then((response) => {
          ElMessage.success("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
};

/** 批量删除按钮操作 */
const handleBatchDelete = () => {
  if (!selectedIds.value.length) {
    return;
  }
  ElMessageBox.confirm(
    '是否确认删除"' + selectedIds.value.length + '"条数据项?',
    "警告",
    {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  )
    .then(() => {
      deleteSysResourceApi(selectedIds.value);
    })
    .then(() => {
      getList();
      ElMessage.success("删除成功");
    });
};

/** 删除按钮操作 */
const handleDelete = (row: any) => {
  ElMessageBox.confirm('是否确认删除编号为"' + row.id + '"的数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      deleteSysResourceApi(row.id);
    })
    .then(() => {
      getList();
      ElMessage.success("删除成功");
    });
};

// 添加分页方法
const handleSizeChange = (val: any) => {
  queryParams.pageSize = val;
  getList();
};

const handleCurrentChange = (val: any) => {
  queryParams.pageNum = val;
  getList();
};

onMounted(() => {
  getList();
  getCategoryList();
});
</script>
