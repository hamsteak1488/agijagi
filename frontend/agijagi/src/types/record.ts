export interface Record {
  type: string;
  latestDateTime: string;
}

export interface RecordMenu extends Record {
  icon: string;
  color: string;
}
