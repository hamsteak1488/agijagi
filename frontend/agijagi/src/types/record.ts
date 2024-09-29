export type RecordType = '대변' | '소변' | '수면' | '식사' | '유축' | '약';

export interface Record {
  type: RecordType;
  latestDateTime: string | null;
}

export interface RecordRequest {
  childId: number;
  type: RecordType;
  startDateTime: string;
  endDateTime: string | null;
}

export interface RecordMenu extends Record {
  icon: string;
  color: string;
}

export interface RecordResponse extends Omit<RecordRequest, 'childId'> {
  id: number;
}
