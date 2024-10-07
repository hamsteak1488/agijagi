import { BabyGender } from './user';

export interface ChildRequest {
  name: string;
  nickname: string;
  gender: BabyGender;
  birthday: string;
  image: File | null;
  birthWeight: string;
  birthHeight: string;
}
export interface EditChildInfoRequest {
  childId: number;
  name: string;
  nickname: string;
  gender: BabyGender;
  birthday: string;
}

export interface EditChildImageRequest {
  childId: number;
  image: File | null;
}

export interface DeleteChildRequest {
  childId: number;
}

export interface DeleteChildImageRequest {
  childId: number;
}
