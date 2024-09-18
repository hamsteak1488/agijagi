import { ReactNode } from 'react';

import Typhography from '../../common/Typography';

import Item from './Item';

import * as s from './ActivityRecordList.style';

interface ActivityRecordListProps extends React.ComponentProps<'div'> {
  children: ReactNode;
}

const ActivityRecordList = ({ children, ...rest }: ActivityRecordListProps) => {
  return (
    <s.Container {...rest}>
      <s.Border></s.Border>
      <s.Body>
        <Typhography size="sm">6월 29일 (수요일)</Typhography>
        <Item>기저귀</Item>
        <Item>기저귀</Item>
        <Item>기저귀</Item>
      </s.Body>
    </s.Container>
  );
};

export default ActivityRecordList;
