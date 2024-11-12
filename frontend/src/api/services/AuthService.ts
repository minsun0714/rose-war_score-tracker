import api from '../apiClient'

class AuthService {
  // Private 메서드로 API 호출 로직 관리
  static async _signUp(signUpInfo: SignUpRequest): Promise<SignUpResponse> {
    const response = await api.post('/api/users', signUpInfo)
    return response.data
  }

  static async _login(loginInfo: LoginRequest): Promise<LoginResponse> {
    const response = await api.post('/api/auth/login', loginInfo)
    return response.data
  }

  static async _fetchUserInfo(userId: string): Promise<UserResponse> {
    const response = await api.get(`/api/users/${userId}`, {})
    return response.data
  }

  static async _updateUserInfo(
    userId: string,
    updatedUserInfo: UpdateUserInfoRequest,
  ): Promise<UpdateUserInfoResponse> {
    const response = await api.put(`/api/users/${userId}`, updatedUserInfo)
    return response.data
  }

  static async _deleteUserInfo(userId: string): Promise<void> {
    await api.delete(`/api/users/${userId}`)
  }

  // Public 메서드로 외부에서 사용할 수 있도록 제공
  static signUp(signUpInfo: SignUpRequest): Promise<SignUpResponse> {
    return this._signUp(signUpInfo)
  }

  static login(loginInfo: LoginRequest): Promise<LoginResponse> {
    return this._login(loginInfo)
  }

  static fetchUserInfo(userId: string): Promise<UserResponse> {
    return this._fetchUserInfo(userId)
  }

  static updateUserInfo(
    userId: string,
    updatedUserInfo: UpdateUserInfoRequest,
  ): Promise<UpdateUserInfoResponse> {
    return this._updateUserInfo(userId, updatedUserInfo)
  }

  static deleteUserInfo(userId: string): Promise<void> {
    return this._deleteUserInfo(userId)
  }
}

export default AuthService
