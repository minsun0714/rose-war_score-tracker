import { type ClassValue, clsx } from 'clsx'
import { twMerge } from 'tailwind-merge'

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export class LocalStorageTokenUtil {
  static setToken(token: string): void {
      localStorage.setItem("token", token);
  }

  static getToken<T>(): T | null {
      const serializedValue = localStorage.getItem("token");
      return serializedValue ? JSON.parse(serializedValue) as T : null;
  }

  static removeToken(): void {
      localStorage.removeItem("token");
  }

  static clear(): void {
      localStorage.clear();
  }

  static hasToken(): boolean {
    return localStorage.getItem("token") !== null;
  }
}
