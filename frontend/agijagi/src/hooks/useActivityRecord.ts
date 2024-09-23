import { ActivityRecordMenu } from '../types/activityRecord';

import ShitIcon from '../assets/images/activityRecord/shit.png';
import DropIcon from '../assets/images/activityRecord/drop.png';
import MoonIcon from '../assets/images/activityRecord/moon.png';
import CutleryIcon from '../assets/images/activityRecord/cutlery.png';
import DrainageIcon from '../assets/images/activityRecord/drainage.png';
import MedicineIcon from '../assets/images/activityRecord/medicine.png';

type ActivityRecordMenuData = Omit<ActivityRecordMenu, 'updatedAt'>;

const menus: ActivityRecordMenuData[] = [
  {
    color: '#f8bb20',
    icon: ShitIcon,
    name: '대변',
  },
  {
    color: '#fbca35',
    icon: DropIcon,
    name: '소변',
  },
  {
    color: '#aba697',
    icon: MoonIcon,
    name: '수면',
  },
  {
    color: '#dbb78e',
    icon: CutleryIcon,
    name: '식사',
  },
  {
    color: '#e5b5b5',
    icon: DrainageIcon,
    name: '유축',
  },
  {
    color: '#a6e3af',
    icon: MedicineIcon,
    name: '약',
  },
];

const useActivityRecord = () => {
  const getMenu = (): ActivityRecordMenu[] => {
    return menus.map((menu: ActivityRecordMenuData): ActivityRecordMenu => {
      return { ...menu, updatedAt: new Date() };
    });
  };

  const findMenuByName = (name: string): ActivityRecordMenuData | undefined => {
    return menus.find((menu) => menu.name === name);
  };

  return { getMenu, findMenuByName };
};

export default useActivityRecord;
