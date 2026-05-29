import { ref, computed } from 'vue'
import type { User } from '../types/user'
import { useUsers } from './useUsers'

/**
 * useUserForm accepts userActions (ReturnType<typeof useUsers>) as a parameter instead of calling useUsers internally.
 * This architecture guarantees a SINGLE SOURCE OF TRUTH. If useUsers was invoked internally within useUserForm, 
 * multiple separate instances of the users array and load statuses would be created across views, fracturing shared state 
 * and causing UI synchronization failures.
 */
export function useUserForm(userActions: ReturnType<typeof useUsers>) {
  const selectedUser = ref<User | null>(null)
  const formError = ref<string | null>(null)
  const formLoading = ref<boolean>(false)

  // Computed configuration resolving form flow target
  const formMode = computed<'create' | 'edit'>(() => {
    return selectedUser.value ? 'edit' : 'create'
  })

  const selectForEdit = (user: User) => {
    selectedUser.value = { ...user }
    formError.value = null
  }

  const clearSelection = () => {
    selectedUser.value = null
    formError.value = null
  }

  const handleSave = async (data: Omit<User, 'id'>) => {
    formLoading.value = true
    formError.value = null
    try {
      await userActions.createUser(data)
      clearSelection()
    } catch (err: any) {
      formError.value = err.message || 'Failed to register user.'
    } finally {
      formLoading.value = false
    }
  }

  const handleUpdate = async (data: User) => {
    formLoading.value = true
    formError.value = null
    try {
      await userActions.updateUser(data.id, data)
      clearSelection()
    } catch (err: any) {
      formError.value = err.message || 'Failed to modify user credentials.'
    } finally {
      formLoading.value = false
    }
  }

  const handleDelete = async (id: string) => {
    formLoading.value = true
    formError.value = null
    try {
      await userActions.deleteUser(id)
      if (selectedUser.value?.id === id) {
        clearSelection()
      }
    } catch (err: any) {
      formError.value = err.message || 'Failed to terminate user session.'
    } finally {
      formLoading.value = false
    }
  }

  return {
    selectedUser,
    formError,
    formLoading,
    formMode,
    selectForEdit,
    clearSelection,
    handleSave,
    handleUpdate,
    handleDelete
  }
}
