import AsyncStorage from '@react-native-async-storage/async-storage';
import type {Farmer} from './farmerService';

const KEYS = {
  farmer: 'agrovani.farmer',
  language: 'agrovani.language',
  onboarded: 'agrovani.onboarded',
};

export const storage = {
  async saveFarmer(farmer: Farmer): Promise<void> {
    await AsyncStorage.setItem(KEYS.farmer, JSON.stringify(farmer));
  },

  async getFarmer(): Promise<Farmer | null> {
    try {
      const raw = await AsyncStorage.getItem(KEYS.farmer);
      return raw ? (JSON.parse(raw) as Farmer) : null;
    } catch {
      await AsyncStorage.removeItem(KEYS.farmer);
      return null;
    }
  },

  async clearFarmer(): Promise<void> {
    await AsyncStorage.removeItem(KEYS.farmer);
  },

  async setLanguage(code: string): Promise<void> {
    await AsyncStorage.setItem(KEYS.language, code);
  },

  async getLanguage(): Promise<string> {
    return (await AsyncStorage.getItem(KEYS.language)) ?? 'mr';
  },

  async setOnboarded(): Promise<void> {
    await AsyncStorage.setItem(KEYS.onboarded, 'true');
  },

  async isOnboarded(): Promise<boolean> {
    return (await AsyncStorage.getItem(KEYS.onboarded)) === 'true';
  },
};