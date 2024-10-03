export interface DiaryResponse {
  id: number;
  childId: number;
  memberId: number;
  content: string;
  createdAt: string;
  mediaUrls: string[];
}

export interface DiaryRequest {
  childId: number;
  content: string;
  mediaList: File[];
}
