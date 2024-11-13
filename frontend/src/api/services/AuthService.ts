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

  static async _fetchUserInfo(): Promise<UserResponse> {
    const response = await api.get(`/api/users`)
    return response.data
  }

  static async _updateUserInfo(
    updatedUserInfo: UpdateUserInfoRequest,
  ): Promise<UpdateUserInfoResponse> {
    const response = await api.put(`/api/users`, updatedUserInfo)
    return response.data
  }

  static async _deleteUserInfo(): Promise<void> {
    await api.delete(`/api/users`)
  }

  // Public 메서드로 외부에서 사용할 수 있도록 제공
  static signUp(signUpInfo: SignUpRequest): Promise<SignUpResponse> {
    return this._signUp(signUpInfo)
  }

  static login(loginInfo: LoginRequest): Promise<LoginResponse> {
    return this._login(loginInfo)
  }

  static fetchUserInfo(): Promise<UserResponse> {
    return this._fetchUserInfo()
  }

  static updateUserInfo(
    updatedUserInfo: UpdateUserInfoRequest,
  ): Promise<UpdateUserInfoResponse> {
    return this._updateUserInfo(updatedUserInfo)
  }

  static deleteUserInfo(): Promise<void> {
    return this._deleteUserInfo()
  }
}

export default AuthService
