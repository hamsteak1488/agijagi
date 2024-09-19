import { ReactNode } from 'react';

import CustomizedBorderContainer from '../../common/CustomizedBorderContainer';

import theme from '../../../styles/theme';

import Waves from '../../../assets/images/activityRecord/waves.svg';

interface ActivityRecordListProps {
  children?: ReactNode;
}

const ActivityRecordList = ({ children }: ActivityRecordListProps) => {
  return (
    <CustomizedBorderContainer
      backgroundColor={theme.color.primary[50]}
      border={Waves}
      borderHeight="24px"
    >
      {children}
    </CustomizedBorderContainer>
  );
};

export default ActivityRecordList;
