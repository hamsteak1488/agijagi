import { ReactNode } from 'react';

import * as s from './Title.css';

interface TitleProps {
  children: ReactNode;
}

const Title = ({ children }: TitleProps) => {
  return <s.Title>{children}</s.Title>;
};

export default Title;
