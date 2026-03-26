<template>
  <div class="dashboard-shell">
    <aside class="sidebar">
      <button class="brand-card" type="button" @click="toMySpace">
        <span class="brand-badge">YUNLINK</span>
        <div class="brand-copy">
          <h1>云链短链接</h1>
          <p>面向管理后台的短链工作台，聚焦创建、统计与分组治理。</p>
        </div>
      </button>

      <nav class="sidebar-nav">
        <button
          v-for="item in menuItems"
          :key="item.path"
          class="nav-item"
          :class="{ active: route.path === item.path }"
          type="button"
          @click="navigate(item.path)"
        >
          <el-icon class="nav-icon"><component :is="item.icon" /></el-icon>
          <span class="nav-copy">
            <strong>{{ item.name }}</strong>
            <small>{{ item.description }}</small>
          </span>
        </button>
      </nav>

      <div class="sidebar-note">
        <p>短链生成、数据回收、用户配置统一在同一工作区完成。</p>
      </div>
    </aside>

    <section class="workspace">
      <header class="workspace-header">
        <div class="page-copy">
          <span class="page-kicker">Cloud Short Link Console</span>
          <h2>{{ currentTitle }}</h2>
          <p>统一管理短链接生命周期，追踪访问趋势，保持后台操作路径清晰可控。</p>
        </div>

        <div class="header-actions">
          <button class="profile-card" type="button" @click="toMine">
            <span class="profile-label">当前账号</span>
            <strong>{{ username || '...' }}</strong>
          </button>
          <el-dropdown>
            <button class="action-button" type="button">
              更多操作
              <el-icon><ArrowDown /></el-icon>
            </button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="toMine">个人信息</el-dropdown-item>
                <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </header>

      <main class="workspace-body">
        <RouterView class="content-space" />
      </main>
    </section>
  </div>
</template>

<script setup>
import { computed, getCurrentInstance, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ArrowDown, DataAnalysis, DeleteFilled, UserFilled } from '@element-plus/icons-vue'
import { removeKey, removeUsername, getToken, getUsername } from '@/core/auth.js'
import { ElMessage } from 'element-plus'

const { proxy } = getCurrentInstance()
const API = proxy.$API
const router = useRouter()
const route = useRoute()

const menuItems = [
  {
    path: '/home/space',
    name: '空间总览',
    description: '查看短链数据和业务统计',
    icon: DataAnalysis
  },
  {
    path: '/home/recycleBin',
    name: '回收站',
    description: '恢复或彻底移除历史短链',
    icon: DeleteFilled
  },
  {
    path: '/home/account',
    name: '个人设置',
    description: '维护账号资料与安全信息',
    icon: UserFilled
  }
]

const username = ref('')

const currentTitle = computed(() => route.meta?.title || '工作台')

const navigate = (path) => {
  if (route.path !== path) {
    router.push(path)
  }
}

const toMine = () => {
  router.push('/home/account')
}

const toMySpace = () => {
  router.push('/home/space')
}

const truncateText = (text, maxLength) => {
  if (!text) {
    return ''
  }
  return text.length > maxLength ? text.slice(0, maxLength) + '...' : text
}

const logout = async () => {
  const token = getToken()
  const currentUsername = getUsername()
  await API.user.logout({ token, username: currentUsername })
  removeUsername()
  removeKey()
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  router.push('/login')
  ElMessage.success('已退出登录')
}

onMounted(async () => {
  const actualUsername = getUsername()
  const res = await API.user.queryUserInfo(actualUsername)
  username.value = truncateText(res?.data?.data?.username || actualUsername, 10)
})
</script>

<style lang="scss" scoped>
.dashboard-shell {
  min-height: 100vh;
  display: grid;
  grid-template-columns: 320px 1fr;
  background:
    radial-gradient(circle at top left, rgba(38, 102, 89, 0.2), transparent 35%),
    linear-gradient(135deg, #f5efe6 0%, #eef3ea 45%, #e6ece7 100%);
}

.sidebar {
  padding: 28px 22px;
  background: rgba(12, 30, 27, 0.96);
  color: #f6f1e8;
  display: flex;
  flex-direction: column;
  gap: 22px;
  box-shadow: inset -1px 0 0 rgba(255, 255, 255, 0.06);
}

.brand-card {
  border: 0;
  width: 100%;
  padding: 26px 24px;
  border-radius: 28px;
  text-align: left;
  cursor: pointer;
  color: inherit;
  background:
    linear-gradient(180deg, rgba(255, 255, 255, 0.08), rgba(255, 255, 255, 0.02)),
    linear-gradient(135deg, rgba(163, 127, 78, 0.28), rgba(31, 82, 72, 0.6));
  box-shadow: 0 20px 45px rgba(0, 0, 0, 0.18);
}

.brand-badge {
  display: inline-flex;
  align-items: center;
  padding: 6px 12px;
  border-radius: 999px;
  font-size: 11px;
  letter-spacing: 0.24em;
  color: #f2ddba;
  background: rgba(242, 221, 186, 0.12);
}

.brand-copy {
  margin-top: 18px;

  h1 {
    font-size: 34px;
    line-height: 1.1;
    font-family: Georgia, 'Times New Roman', 'Songti SC', serif;
    font-weight: 700;
    letter-spacing: 0.04em;
  }

  p {
    margin-top: 12px;
    color: rgba(246, 241, 232, 0.76);
    line-height: 1.75;
    font-size: 14px;
  }
}

.sidebar-nav {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.nav-item {
  border: 0;
  width: 100%;
  display: flex;
  align-items: center;
  gap: 14px;
  cursor: pointer;
  padding: 16px 18px;
  border-radius: 22px;
  text-align: left;
  color: rgba(246, 241, 232, 0.78);
  background: rgba(255, 255, 255, 0.03);
  transition: transform 0.2s ease, background 0.2s ease, color 0.2s ease;
}

.nav-item:hover,
.nav-item.active {
  color: #fff;
  transform: translateX(4px);
  background: linear-gradient(135deg, rgba(212, 167, 98, 0.2), rgba(39, 94, 81, 0.92));
}

.nav-icon {
  width: 42px;
  height: 42px;
  border-radius: 14px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background: rgba(255, 255, 255, 0.08);
  font-size: 18px;
}

.nav-copy {
  display: flex;
  flex-direction: column;
  gap: 4px;

  strong {
    font-size: 15px;
    font-weight: 600;
  }

  small {
    color: rgba(246, 241, 232, 0.62);
    line-height: 1.5;
  }
}

.sidebar-note {
  margin-top: auto;
  padding: 18px 18px 20px;
  border-radius: 22px;
  color: rgba(246, 241, 232, 0.7);
  background: rgba(255, 255, 255, 0.04);
  line-height: 1.8;
}

.workspace {
  padding: 28px 28px 22px;
  display: flex;
  flex-direction: column;
  gap: 22px;
}

.workspace-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 20px;
  padding: 28px 32px;
  border-radius: 30px;
  background: rgba(255, 251, 245, 0.82);
  box-shadow: 0 24px 50px rgba(61, 78, 66, 0.08);
  backdrop-filter: blur(12px);
}

.page-copy {
  max-width: 720px;

  .page-kicker {
    display: inline-flex;
    margin-bottom: 12px;
    font-size: 12px;
    letter-spacing: 0.24em;
    color: #7a6a4f;
    text-transform: uppercase;
  }

  h2 {
    font-size: 40px;
    line-height: 1.1;
    color: #12231f;
    font-family: Georgia, 'Times New Roman', 'Songti SC', serif;
    font-weight: 700;
  }

  p {
    margin-top: 12px;
    max-width: 620px;
    color: #5d695f;
    line-height: 1.8;
    font-size: 15px;
  }
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.profile-card,
.action-button {
  border: 0;
  cursor: pointer;
  border-radius: 18px;
}

.profile-card {
  min-width: 150px;
  padding: 14px 18px;
  text-align: left;
  color: #12231f;
  background: linear-gradient(180deg, #fffdf8 0%, #f0eadf 100%);
  box-shadow: inset 0 0 0 1px rgba(122, 106, 79, 0.12);

  .profile-label {
    display: block;
    font-size: 11px;
    letter-spacing: 0.18em;
    color: #8b7b61;
    text-transform: uppercase;
    margin-bottom: 6px;
  }

  strong {
    font-size: 18px;
    font-weight: 700;
  }
}

.action-button {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 14px 18px;
  color: #f6f1e8;
  background: #193b34;
}

.workspace-body {
  min-height: 0;
  flex: 1;
  padding: 18px;
  border-radius: 30px;
  background: rgba(255, 255, 255, 0.62);
  box-shadow: inset 0 0 0 1px rgba(18, 35, 31, 0.06);
}

.content-space {
  height: 100%;
}

@media (max-width: 1080px) {
  .dashboard-shell {
    grid-template-columns: 1fr;
  }

  .sidebar {
    padding-bottom: 0;
  }

  .workspace {
    padding-top: 0;
  }
}

@media (max-width: 768px) {
  .workspace-header {
    flex-direction: column;
  }

  .header-actions {
    width: 100%;
    flex-direction: column;
    align-items: stretch;
  }

  .page-copy h2 {
    font-size: 32px;
  }
}
</style>
