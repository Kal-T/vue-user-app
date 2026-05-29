import { ref, computed } from 'vue'
import type { User } from '../types/user'
import http from '../api/http'

export function useUsers() {
  const users = ref<User[]>([])
  const isLoading = ref<boolean>(false)
  const error = ref<string | null>(null)

  // Computed overview metrics derived strictly from users ref
  const summary = computed(() => {
    const total = users.value.length
    const adminCount = users.value.filter((u) => u.role === 'A').length
    const userCount = users.value.filter((u) => u.role === 'U').length
    return { total, adminCount, userCount }
  })

  // GET /api/users
  const fetchUsers = async () => {
    isLoading.value = true
    error.value = null
    try {
      const data = await http.get<any, User[]>('/api/users')
      users.value = data
    } catch (err: any) {
      error.value = err.message || 'Failed to retrieve user registry.'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  // POST /api/users
  const createUser = async (data: Omit<User, 'id'>) => {
    isLoading.value = true
    error.value = null
    try {
      const newUser = await http.post<any, User>('/api/users', data)
      users.value.push(newUser)
      return newUser
    } catch (err: any) {
      error.value = err.message || 'Failed to register new user.'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  // PUT /api/users/{id}
  const updateUser = async (id: string, data: User) => {
    isLoading.value = true
    error.value = null
    try {
      const updatedUser = await http.put<any, User>(`/api/users/${id}`, data)
      const idx = users.value.findIndex((u) => u.id === id)
      if (idx !== -1) {
        users.value[idx] = updatedUser
      }
      return updatedUser
    } catch (err: any) {
      error.value = err.message || 'Failed to update user profile.'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  // DELETE /api/users/{id}
  const deleteUser = async (id: string) => {
    isLoading.value = true
    error.value = null
    try {
      await http.delete(`/api/users/${id}`)
      users.value = users.value.filter((u) => u.id !== id)
    } catch (err: any) {
      error.value = err.message || 'Failed to remove user account.'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  return {
    users,
    isLoading,
    error,
    summary,
    fetchUsers,
    createUser,
    updateUser,
    deleteUser
  }
}
