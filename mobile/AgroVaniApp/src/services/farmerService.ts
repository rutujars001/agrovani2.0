import {apiClient} from './apiClient';

export interface Farmer {
  id: number;
  fullName: string;
  phoneNumber: string;
  village: string | null;
  taluka: string | null;
  district: string | null;
  preferredLanguage: string;
  createdAt: string;
}

export interface RegisterFarmerInput {
  fullName: string;
  phoneNumber: string;
  village?: string;
  taluka?: string;
  district?: string;
  preferredLanguage?: string;
}

export interface Crop {
  id: number;
  code: string;
  nameEn: string;
  nameMr: string;
  category: string;
  isActive: boolean;
}

export interface FarmCrop {
  id: number;
  farmId: number;
  cropCode: string;
  cropNameEn: string;
  cropNameMr: string;
  sowingDate: string | null;
  expectedHarvestDate: string | null;
  status: string;
}

export const farmerService = {
  register: (input: RegisterFarmerInput) =>
    apiClient.post<Farmer>('/api/farmers/register', input),

  getByPhone: (phoneNumber: string) =>
    apiClient.get<Farmer>(`/api/farmers/phone/${phoneNumber}`),

  getCrops: () => apiClient.get<Crop[]>('/api/crops'),

  getActiveCropsForFarmer: (farmerId: number) =>
    apiClient.get<FarmCrop[]>(`/api/farmers/${farmerId}/crops`),
};