import { ReactNode } from 'react';

import * as s from './Typhography.style';

import { TyphographyStyles } from './Typhography.types';

interface TyphographyProps extends TyphographyStyles {
  children: ReactNode;
}

const Typhography = ({ children, ...rest }: TyphographyProps) => {
  return <s.Typhography {...rest}>{children}</s.Typhography>;
};

export default Typhography;
