import { ReactNode } from 'react';

import * as s from './List.style';

interface ListProps {
  children: ReactNode;
}

const List = ({ children }: ListProps) => {
  return <s.Container>{children}</s.Container>;
};

export default List;
