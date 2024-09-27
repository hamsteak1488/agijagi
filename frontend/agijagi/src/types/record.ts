export interface Record {
  type: string;
  latestDateTime: string | null;
}

export interface RecordRequest {
  childId: number;
  type: string;
  startDateTime: string;
  endDateTime: string | null;
}

export interface RecordMenu extends Record {
  icon: string;
  color: string;
}
