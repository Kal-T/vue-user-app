import axios from 'axios'

const http = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8081',
  timeout: 8000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// Response Interceptor: extract data payload directly
http.interceptors.response.use(
  (response) => {
    return response.data
  },
  (error) => {
    let normalizedError = {
      message: error.message || 'An unexpected error occurred.',
      fieldErrors: undefined as Record<string, string> | undefined
    }

    if (error.response) {
      const status = error.response.status
      const data = error.response.data

      if (status === 409) {
        // Map conflict status 409 specifically to "Email already exists."
        normalizedError.message = 'Email already exists.'
      } else if (status === 400 && data) {
        // Map validation 400 status field errors from Spring's BindingResult format
        normalizedError.message = 'Validation failed. Please correct the highlighted fields.'
        const errors: Record<string, string> = {}

        if (data.fieldErrors && typeof data.fieldErrors === 'object') {
          // BindingResult custom key-value mapping format
          Object.assign(errors, data.fieldErrors)
        } else if (Array.isArray(data.errors)) {
          // BindingResult standard nested field errors list format
          for (const err of data.errors) {
            if (err && typeof err === 'object' && err.field && err.defaultMessage) {
              errors[err.field] = err.defaultMessage
            }
          }
        } else if (typeof data === 'object') {
          // Flattened flat validation key-value fields format
          for (const key of Object.keys(data)) {
            if (typeof data[key] === 'string') {
              errors[key] = data[key]
            }
          }
        }

        if (Object.keys(errors).length > 0) {
          normalizedError.fieldErrors = errors
        }
      } else if (data && typeof data === 'object' && typeof data.message === 'string') {
        normalizedError.message = data.message
      }
    }

    return Promise.reject(normalizedError)
  }
)

export default http
