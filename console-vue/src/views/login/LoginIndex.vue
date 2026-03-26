<template>
  <div class="login-page">
    <div class="background-layer"></div>
    <div class="grain-layer"></div>

    <section class="login-shell">
      <article class="intro-panel">
        <span class="panel-tag">YUNLINK CONSOLE</span>
        <h1>云链短链接</h1>
        <p class="panel-summary">
          一个聚焦短链创建、分组管理与访问统计的后台系统，适合本地演示、二次开发和面试展示。
        </p>

        <div class="feature-list">
          <div class="feature-card">
            <strong>短链生成</strong>
            <span>统一管理链接有效期、分组归档与批量创建。</span>
          </div>
          <div class="feature-card">
            <strong>数据统计</strong>
            <span>沉淀访问趋势、设备维度与地域分布等核心指标。</span>
          </div>
          <div class="feature-card">
            <strong>后台治理</strong>
            <span>覆盖回收站、用户信息维护与管理侧常见操作。</span>
          </div>
        </div>

        <div class="default-account">
          <span>默认账号</span>
          <strong>admin / admin123456</strong>
        </div>
      </article>

      <article class="auth-panel">
        <div class="auth-switch">
          <button
            class="switch-button"
            :class="{ active: isLogin }"
            type="button"
            @click="isLogin = true"
          >
            登录
          </button>
          <button
            class="switch-button"
            :class="{ active: !isLogin }"
            type="button"
            @click="isLogin = false"
          >
            注册
          </button>
        </div>

        <div v-if="isLogin" class="auth-card">
          <div class="auth-copy">
            <h2>欢迎回来</h2>
            <p>登录后进入短链工作台，继续管理你的业务链接。</p>
          </div>

          <el-form ref="loginFormRef1" :model="loginForm" :rules="loginFormRule" class="auth-form">
            <el-form-item prop="username">
              <el-input v-model="loginForm.username" placeholder="请输入用户名" clearable>
                <template #prepend>用户名</template>
              </el-input>
            </el-form-item>

            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="请输入密码"
                show-password
                clearable
              >
                <template #prepend>密码</template>
              </el-input>
            </el-form-item>

            <div class="form-actions">
              <el-checkbox v-model="checked">记住密码</el-checkbox>
              <el-button :loading="loading" type="primary" @click="login(loginFormRef1)">登录</el-button>
            </div>
          </el-form>
        </div>

        <div v-else class="auth-card">
          <div class="auth-copy">
            <h2>创建账号</h2>
            <p>注册后可直接进入后台，开始管理自己的短链数据。</p>
          </div>

          <el-form ref="loginFormRef2" :model="addForm" :rules="addFormRule" class="auth-form">
            <el-form-item prop="username">
              <el-input v-model="addForm.username" placeholder="请输入用户名" clearable>
                <template #prepend>用户名</template>
              </el-input>
            </el-form-item>
            <el-form-item prop="mail">
              <el-input v-model="addForm.mail" placeholder="请输入邮箱" clearable>
                <template #prepend>邮箱</template>
              </el-input>
            </el-form-item>
            <el-form-item prop="phone">
              <el-input v-model="addForm.phone" placeholder="请输入手机号" clearable>
                <template #prepend>手机号</template>
              </el-input>
            </el-form-item>
            <el-form-item prop="realName">
              <el-input v-model="addForm.realName" placeholder="请输入姓名" clearable>
                <template #prepend>姓名</template>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="addForm.password"
                type="password"
                placeholder="请输入密码"
                show-password
                clearable
              >
                <template #prepend>密码</template>
              </el-input>
            </el-form-item>

            <div class="form-actions register-actions">
              <span class="register-tip">注册后会自动登录并跳转到后台首页。</span>
              <el-button :loading="loading" type="primary" @click="addUser(loginFormRef2)">注册</el-button>
            </div>
          </el-form>
        </div>
      </article>
    </section>
  </div>
</template>

<script setup>
import { getCurrentInstance, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getUsername, setToken, setUsername } from '@/core/auth.js'

const { proxy } = getCurrentInstance()
const API = proxy.$API
const router = useRouter()

const loginFormRef1 = ref()
const loginFormRef2 = ref()
const isLogin = ref(true)
const loading = ref(false)
const checked = ref(true)

const loginForm = reactive({
  username: 'admin',
  password: 'admin123456'
})

const addForm = reactive({
  username: '',
  password: '',
  realName: '',
  phone: '',
  mail: ''
})

const addFormRule = reactive({
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    {
      pattern: /^1[3|5|7|8|9]\d{9}$/,
      message: '请输入正确的手机号',
      trigger: 'blur'
    },
    { min: 11, max: 11, message: '手机号必须是11位', trigger: 'blur' }
  ],
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 8, max: 15, message: '密码长度请在八位以上', trigger: 'blur' }
  ],
  mail: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    {
      pattern: /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/,
      message: '请输入正确的邮箱号',
      trigger: 'blur'
    }
  ],
  realName: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
})

const loginFormRule = reactive({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 8, max: 15, message: '密码长度请在八位以上', trigger: 'blur' }
  ]
})

const persistLoginState = (token, username) => {
  setToken(token)
  setUsername(username)
  localStorage.setItem('token', token)
  localStorage.setItem('username', username)
}

const addUser = (formEl) => {
  if (!formEl) return
  formEl.validate(async (valid) => {
    if (!valid) {
      return false
    }
    loading.value = true
    try {
      const res1 = await API.user.hasUsername({ username: addForm.username })
      if (res1.data.success === false) {
        ElMessage.warning('用户名已存在')
        return
      }

      const res2 = await API.user.addUser(addForm)
      if (res2.data.success === false) {
        ElMessage.warning(res2.data.message)
        return
      }

      const res3 = await API.user.login({ username: addForm.username, password: addForm.password })
      const token = res3?.data?.data?.token
      if (token) {
        persistLoginState(token, addForm.username)
      }
      ElMessage.success('注册登录成功')
      router.push('/home')
    } finally {
      loading.value = false
    }
  })
}

const login = (formEl) => {
  if (!formEl) return
  formEl.validate(async (valid) => {
    if (!valid) {
      return false
    }
    loading.value = true
    try {
      const res1 = await API.user.login(loginForm)
      if (res1.data.code === '0') {
        const token = res1?.data?.data?.token
        if (token) {
          persistLoginState(token, loginForm.username)
        }
        ElMessage.success('登录成功')
        router.push('/home')
        return
      }

      if (res1.data.message === '用户已登录') {
        const cookiesUsername = getUsername()
        if (cookiesUsername === loginForm.username) {
          ElMessage.success('登录成功')
          router.push('/home')
        } else {
          ElMessage.warning('用户已在别处登录，请勿重复登录')
        }
        return
      }

      if (res1.data.message === '用户不存在') {
        ElMessage.error('请输入正确的账号密码')
      }
    } finally {
      loading.value = false
    }
  })
}
</script>

<style lang="less" scoped>
.login-page {
  position: relative;
  min-height: 100vh;
  overflow: hidden;
  background: #102721;
}

.background-layer {
  position: absolute;
  inset: 0;
  background:
    radial-gradient(circle at 15% 20%, rgba(202, 157, 83, 0.22), transparent 26%),
    radial-gradient(circle at 85% 15%, rgba(97, 167, 143, 0.2), transparent 30%),
    linear-gradient(135deg, #0f1716 0%, #18322c 55%, #f2e8d8 180%);
}

.grain-layer {
  position: absolute;
  inset: 0;
  opacity: 0.18;
  background-image:
    linear-gradient(rgba(255, 255, 255, 0.06) 1px, transparent 1px),
    linear-gradient(90deg, rgba(255, 255, 255, 0.05) 1px, transparent 1px);
  background-size: 28px 28px;
  mask-image: radial-gradient(circle at center, black 58%, transparent 100%);
}

.login-shell {
  position: relative;
  z-index: 1;
  min-height: 100vh;
  display: grid;
  grid-template-columns: minmax(320px, 1.15fr) minmax(360px, 520px);
  align-items: center;
  gap: 36px;
  padding: 48px;
}

.intro-panel {
  padding: 36px;
  color: #f7f3eb;
}

.panel-tag {
  display: inline-flex;
  padding: 7px 14px;
  border-radius: 999px;
  background: rgba(242, 232, 216, 0.1);
  color: #e7cfaa;
  font-size: 12px;
  letter-spacing: 0.24em;
}

.intro-panel h1 {
  margin-top: 24px;
  font-size: 68px;
  line-height: 1;
  font-family: Georgia, 'Times New Roman', 'Songti SC', serif;
  font-weight: 700;
}

.panel-summary {
  max-width: 620px;
  margin-top: 20px;
  font-size: 18px;
  line-height: 1.85;
  color: rgba(247, 243, 235, 0.78);
}

.feature-list {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 16px;
  margin-top: 34px;
}

.feature-card {
  min-height: 150px;
  padding: 22px 20px;
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.08);
  backdrop-filter: blur(12px);
  box-shadow: inset 0 0 0 1px rgba(255, 255, 255, 0.05);

  strong {
    display: block;
    font-size: 18px;
    margin-bottom: 12px;
  }

  span {
    color: rgba(247, 243, 235, 0.72);
    line-height: 1.8;
  }
}

.default-account {
  margin-top: 28px;
  display: inline-flex;
  flex-direction: column;
  gap: 8px;
  padding: 18px 20px;
  border-radius: 22px;
  background: rgba(16, 39, 33, 0.42);

  span {
    font-size: 12px;
    letter-spacing: 0.18em;
    color: #d2b88e;
  }

  strong {
    font-size: 22px;
    color: #fff3de;
  }
}

.auth-panel {
  padding: 20px;
  border-radius: 32px;
  background: rgba(252, 248, 241, 0.88);
  box-shadow: 0 30px 80px rgba(0, 0, 0, 0.22);
  backdrop-filter: blur(16px);
}

.auth-switch {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  padding: 8px;
  border-radius: 22px;
  background: #ebe3d5;
}

.switch-button {
  border: 0;
  padding: 14px 0;
  border-radius: 16px;
  background: transparent;
  color: #6d614f;
  font-size: 15px;
  font-weight: 600;
}

.switch-button.active {
  background: linear-gradient(135deg, #20483f, #2f6f61);
  color: #fff;
  box-shadow: 0 14px 30px rgba(32, 72, 63, 0.18);
}

.auth-card {
  padding: 28px 16px 6px;
}

.auth-copy h2 {
  font-size: 34px;
  line-height: 1.1;
  color: #172924;
  font-family: Georgia, 'Times New Roman', 'Songti SC', serif;
  font-weight: 700;
}

.auth-copy p {
  margin-top: 10px;
  color: #66756c;
  line-height: 1.8;
}

.auth-form {
  margin-top: 26px;
}

.form-actions {
  margin-top: 30px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}

.register-actions {
  align-items: flex-start;
}

.register-tip {
  color: #7b817a;
  line-height: 1.7;
  font-size: 13px;
}

:deep(.el-form-item__content) {
  margin-left: 0 !important;
}

:deep(.el-input-group__prepend) {
  color: #41514a;
  background: #f2ebdd;
  border-color: #dfd3be;
}

:deep(.el-input__wrapper) {
  box-shadow: 0 0 0 1px #dfd3be inset;
  background: rgba(255, 255, 255, 0.92);
}

:deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px #2f6f61 inset;
}

:deep(.el-checkbox__label) {
  color: #5a655f;
}

@media (max-width: 1080px) {
  .login-shell {
    grid-template-columns: 1fr;
    padding: 24px;
  }

  .intro-panel {
    padding: 12px 4px;
  }

  .feature-list {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .intro-panel h1 {
    font-size: 48px;
  }

  .auth-panel {
    padding: 16px;
  }

  .form-actions {
    flex-direction: column;
    align-items: stretch;
  }
}
</style>
