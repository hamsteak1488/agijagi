import { ReactNode } from 'react';

import Typhography from '../../../common/Typography';

import * as s from './Item.style';

interface ItemProps {
  children: ReactNode;
}

const Item = ({ children }: ItemProps) => {
  return (
    <s.Item>
      <s.Icon></s.Icon>
      <s.Text>
        <Typhography color="primary" weight="bold">
          기저귀
        </Typhography>
        <Typhography color="greyScale" size="xs">
          대변
        </Typhography>
      </s.Text>
      <Typhography color="greyScale" size="sm">
        14:00
      </Typhography>
    </s.Item>
  );
};

export default Item;
