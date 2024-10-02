export interface UserData {
  email: string;
  password: string;
  nickname: string;
}

export type BabyGender = '남아' | '여아' | '알수없음';
export type Authority = 'WRITE' | 'READ';

export interface MemberResponse {
  email: string;
  nickname: string;
}

export interface BabyResponse {
  childId: number;
  name: string;
  nickname: string;
  gender: BabyGender;
  birthday: string;
  imageUrl: string | null;
  authority: Authority;
  followerNum: number;
}
