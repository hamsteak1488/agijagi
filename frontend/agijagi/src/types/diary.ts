export interface DiaryResponse {
  id: number;
  childId: number;
  memberId: number;
  content: string;
  createdAt: string;
  wroteAt: string;
  mediaUrls: string[];
}

export interface DiaryRequest {
  childId: number;
  content: string;
  date: string;
  mediaList: File[];
}
