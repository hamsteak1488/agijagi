import type { Record, RecordMenu, RecordType } from '../types/record';

import ShitIcon from '../assets/images/record/shit.png';
import DropIcon from '../assets/images/record/drop.png';
import MoonIcon from '../assets/images/record/moon.png';
import CutleryIcon from '../assets/images/record/cutlery.png';
import DrainageIcon from '../assets/images/record/drainage.png';
import MedicineIcon from '../assets/images/record/medicine.png';

type RecordMenuData = Omit<RecordMenu, 'latestDateTime'>;

const menus: RecordMenuData[] = [
  {
    color: '#f8bb20',
    icon: ShitIcon,
    type: '대변',
  },
  {
    color: '#fbca35',
    icon: DropIcon,
    type: '소변',
  },
  {
    color: '#aba697',
    icon: MoonIcon,
    type: '수면',
  },
  {
    color: '#dbb78e',
    icon: CutleryIcon,
    type: '식사',
  },
  {
    color: '#e5b5b5',
    icon: DrainageIcon,
    type: '유축',
  },
  {
    color: '#a6e3af',
    icon: MedicineIcon,
    type: '약',
  },
];

const useRecord = () => {
  const getMenu = (records: Record[]): RecordMenu[] => {
    return menus.map((menu: RecordMenuData): RecordMenu => {
      return {
        ...menu,
        latestDateTime:
          records.find((record) => record.type === menu.type)?.latestDateTime ||
          null,
      };
    });
  };

  const findMenuByType = (type: RecordType): RecordMenuData | undefined => {
    return menus.find((menu) => menu.type === type);
  };

  return { getMenu, findMenuByType };
};

export default useRecord;
